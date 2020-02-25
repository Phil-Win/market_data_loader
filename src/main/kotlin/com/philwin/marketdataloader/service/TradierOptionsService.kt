package com.philwin.marketdataloader.service

import com.google.gson.Gson
import com.philwin.marketdata.common.repository.OptionsRespository
import com.philwin.marketdataloader.model.raw.options.tradier.TradierRawOption
import com.philwin.marketdataloader.util.FileUtil
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import com.philwin.marketdata.common.model.Options

@Component
class TradierOptionsService : IStockService {
    @Autowired
    lateinit var optionsRespository: OptionsRespository

    val dateFormat      =   SimpleDateFormat("dd_MM_yyy")
    val dateFormatOptionSymbol  =   SimpleDateFormat("yyMMdd")

    override fun loadData(inputFolder: String, outputFolder: String, invalidFolder: String): Boolean {
        val input      =   FileSystemResource(inputFolder).file
        val output    =   FileSystemResource(outputFolder).file
        val invalid   =   FileSystemResource(invalidFolder).file

        if (input.isFile) {
            loadAndMoveData(input, output, invalid)
        } else {
            var fileList    =   FileUtils.listFiles(input, null, true)
            for (file in fileList) {
                if (file.name.contains("_options_processed_on_")) {
                    loadAndMoveData(file, output, invalid)
                }
            }
        }
        return true
    }

    fun loadAndMoveData(file : File, outputFolder : File, invalidFolder : File) : Boolean {
        return FileUtil.moveFileToFolder(file, if (saveData(file)) outputFolder else invalidFolder)
    }

    fun saveData(file : File) : Boolean {
        val scanner =   Scanner(file);
        var lineOfInterest : String
        var gson    =   Gson()
        var tradierRawOptionArray : Array<TradierRawOption>
        var optionsList : List<Options>
        println("Processing TradierOptionsService Options File ${file.absolutePath}")
        try {
            while (scanner.hasNext()) {
                lineOfInterest  =   scanner.nextLine()
                tradierRawOptionArray =   gson.fromJson(lineOfInterest, Array<TradierRawOption>::class.java)
                for (tradierRawOption in tradierRawOptionArray) {
                    optionsList =   tradierOptionsTransformer(tradierRawOption, file)
                    optionsRespository.saveAll(optionsList)
                    println("TradierOptionsService : Successfully loaded all ${optionsList.size} options records!")
                }
            }
        } finally {
            scanner.close()
        }
        println("Finished Processing TradierOptionsService File ${file.absolutePath}")
        return true
    }

    fun tradierOptionsTransformer(tradierRawOption: TradierRawOption, file : File): List<Options> {
        var optionsList =   ArrayList<Options>()
//        var optionToAdd : Options
        var quoteDateOfInterest =   getQuoteDateFromFileName(file)
        for (option in tradierRawOption.options.option) {
//            optionToAdd =   Options()
//            optionToAdd.ask =   option.ask
//            optionToAdd.bid =   option.bid
//            optionToAdd.delta   =   option.greeks.delta
//            optionToAdd.expiration_date =   getExpirationFromSymbol(option.description)
//            optionToAdd.gamma   =   option.greeks.gamma
//            optionToAdd.implied_volatility  =   option.greeks.midIv
//            optionToAdd.open_interest   =   option.openInterest
//            optionToAdd.quote_date      =   getQuoteDateFromFileName(file)
//            optionToAdd.strike          =   option.strike
//            optionToAdd.symbol          =   option.symbol
//            optionToAdd.symbol_underlying   =   option.underlying
//            optionToAdd.theta           =   option.greeks.theta
//            optionToAdd.type            =   getOptionTypeFromDescription(option.description)
//            optionToAdd.volume          =   option.volume
//            optionToAdd.description     =   option.description
//            optionsList.add(optionToAdd)
            optionsList.add(
                Options(
                   option.underlying,
                   null,
                    getOptionTypeFromDescription(option.description),
                    quoteDateOfInterest,
                    getExpirationFromSymbol(option.description),
                    option.strike,
                    option.bid,
                    option.ask,
                    option.volume,
                    option.openInterest,
                    option.greeks?.midIv,
                    null,
                    option.greeks?.delta,
                    option.greeks?.gamma,
                    option.greeks?.theta,
                    null,
                    option.symbol,
                    option.description
                )
            )
        }
        println("TradierOptionsService: Found ${optionsList.size} quotes to process")
        return optionsList
    }

    fun getQuoteDateFromFileName(file : File) : Date? {
        val pattern = Pattern.compile(".*_options_processed_on_(.*)")
        var match   =   pattern.matcher(file.nameWithoutExtension)
        return if (match.find()) dateFormat.parse(match.group(1)) else null
    }

    fun getOptionTypeFromDescription(description : String) : String? {
        if (description.contains("Put")) {
            return "Put"
        } else if (description.contains("Call")) {
            return "Call"
        } else {
            return null;
        }
    }

    fun getExpirationFromSymbol(description : String) : Date? {
        val pattern = Pattern.compile("[a-zA-Z]*([\\d]{6})[CP][\\d]*")
        var match   =   pattern.matcher(description)
        return if (match.find()) dateFormatOptionSymbol.parse(match.group(1)) else null
    }

}