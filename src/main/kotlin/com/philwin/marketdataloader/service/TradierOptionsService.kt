package com.philwin.marketdataloader.service

import com.google.gson.Gson
import com.philwin.marketdataloader.model.raw.options.tradier.Option
import com.philwin.marketdataloader.model.raw.options.tradier.TradierRawOption
import com.philwin.marketdataloader.model.transformed.Options
import com.philwin.marketdataloader.repository.OptionsRespository
import com.philwin.marketdataloader.util.FileUtil
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@Component
class TradierOptionsService : IStockService {
    @Autowired
    lateinit var optionsRespository: OptionsRespository

    val dateFormat      =   SimpleDateFormat("dd_MM_yyy")
    val dateFormatOptionSymbol  =   SimpleDateFormat("yyMMdd")

    override fun loadData(inputFolder: String, outputFolder: String, invalidFolder: String): Boolean {
        val inputFolder      =   FileSystemResource(inputFolder).file
        val outputFolder    =   FileSystemResource(outputFolder).file
        val invalidFolder   =   FileSystemResource(invalidFolder).file

        if (inputFolder.isFile) {
            loadAndMoveData(inputFolder, outputFolder, invalidFolder)
        } else {
            var fileList    =   FileUtils.listFiles(inputFolder, null, true)
            for (file in fileList) {
                if (file.name.contains("_options_processed_on_")) {
                    loadAndMoveData(file, outputFolder, invalidFolder)
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
        println("Processing Tradier Options File ${file.absolutePath}")
        try {
            while (scanner.hasNext()) {
                lineOfInterest  =   scanner.nextLine()
                tradierRawOptionArray =   gson.fromJson(lineOfInterest, Array<TradierRawOption>::class.java)
                for (tradierRawOption in tradierRawOptionArray) {
                    for (option in tradierOptionsTransformer(tradierRawOption, file)) {
                        optionsRespository!!.save(option)
                    }
                }
            }
        } finally {
            scanner.close()
        }
        println("Finished Processing Tradier File ${file.absolutePath}")
        return true
    }

    fun tradierOptionsTransformer(tradierRawOption: TradierRawOption, file : File): List<Options> {
        var optionsList =   ArrayList<Options>()
        var optionToAdd : Options
        for (option in tradierRawOption.options.option) {
            optionToAdd =   Options()
            optionToAdd.ask =   option.ask
            optionToAdd.bid =   option.bid
            optionToAdd.delta   =   option.greeks.delta
            optionToAdd.expiration_date =   getExpirationFromSymbol(option.description)
            optionToAdd.gamma   =   option.greeks.gamma
            optionToAdd.implied_volatility  =   option.greeks.midIv
//            optionToAdd.implied_volatility_atm
//            optionToAdd.mark_underlying
            optionToAdd.open_interest   =   option.openInterest
            optionToAdd.quote_date      =   getQuoteDateFromFileName(file)
            optionToAdd.strike          =   option.strike
            optionToAdd.symbol          =   option.symbol
            optionToAdd.symbol_underlying   =   option.underlying
            optionToAdd.theta           =   option.greeks.theta
            optionToAdd.type            =   getOptionTypeFromDescription(option.description)
            optionToAdd.volume          =   option.volume
            optionToAdd.description     =   option.description
            optionsList.add(optionToAdd)
        }
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