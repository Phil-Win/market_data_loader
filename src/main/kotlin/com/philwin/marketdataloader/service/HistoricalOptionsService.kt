package com.philwin.marketdataloader.service

import com.philwin.marketdataloader.model.transformed.Options
import com.philwin.marketdataloader.repository.OptionsRespository
import com.philwin.marketdataloader.util.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@Component
class HistoricalOptionsService : IStockService {
    @Autowired
    lateinit var optionsRespository: OptionsRespository

    val dateFormat      =   SimpleDateFormat("MM/dd/yyyy")
    val headerLine      =   "underlying,underlying_last, exchange,optionroot,optionext,type,expiration,quotedate,strike,last,bid,ask,volume,openinterest,impliedvol,delta,gamma,theta,vega,optionalias,IVBid,IVAsk"

    override fun loadData(inputFolder: String, outputFolder: String, invalidFolder : String): Boolean{
        val inputFolder      =   FileSystemResource(inputFolder).file;
        val outputFolder    =   FileSystemResource(outputFolder).file;
        val invalidFolder   =   FileSystemResource(invalidFolder).file

        if (inputFolder.isFile) {
            loadAndMoveData(inputFolder, outputFolder, invalidFolder)
        } else {
            val fileList    =   inputFolder.listFiles()
            if (fileList == null || fileList.size == 0) {
                println("No files in folder ${inputFolder.absolutePath}, job is complete")
                return true
            } else {
                for (fileOfInterest in fileList) {
                    loadAndMoveData(fileOfInterest, outputFolder, invalidFolder)
                }
            }
        }

        return true
    }

    fun loadAndMoveData(file : File, outputFolder : File, invalidFolder : File) : Boolean {
        return FileUtil.moveFileToFolder(file, if (saveData(file)) outputFolder else invalidFolder)
    }

    fun saveData(file : File) : Boolean {
        try {
            var optionsList = transformRawCsvToOptionsList(file)
            for (option in optionsList) {
                optionsRespository.save(option)
            }
            return (optionsList.size > 0)
        } catch (e: Exception) {
            return false
        }
    }

    fun transformRawCsvToOptionsList(file : File) : List<Options> {
        if (!file.exists()) {
            return ArrayList<Options>()
        }
        val scanner =   Scanner(file)
        var lineOfInterest : String
        var lineAsList : List<String>
        var optionOfInterest : Options
        var optionsList   =   ArrayList<Options>()
        var counter     =   2
        try {
            lineOfInterest = scanner.nextLine()
            println("Processing the file : ${file.name}")

            if (!lineOfInterest.equals(headerLine)) {
                println("The header of ${lineOfInterest} does not match the expected header : ${headerLine}")
                return optionsList
            }

            while (scanner.hasNext()) {
                println("Processing line number : ${counter} : ${lineOfInterest}")
                counter++
                optionOfInterest = Options()
                lineOfInterest = scanner.nextLine();
                lineAsList = lineOfInterest.split(",");
                if (lineAsList.size == 22) {
                    println("List contains 22 elements! Adding to database : ${lineOfInterest}")
                    optionOfInterest.ask =   BigDecimal(lineAsList.get(11))
                    optionOfInterest.bid =   BigDecimal(lineAsList.get(10))
                    optionOfInterest.delta   =   BigDecimal(lineAsList.get(15))
                    optionOfInterest.expiration_date =   dateFormat.parse(lineAsList.get(6))
                    optionOfInterest.gamma   =   BigDecimal(lineAsList.get(16))
                    optionOfInterest.implied_volatility  =   BigDecimal(lineAsList.get(14))
//            optionToAdd.implied_volatility_atm
                    optionOfInterest.mark_underlying    =   BigDecimal(lineAsList.get(1))
                    optionOfInterest.open_interest   =   lineAsList.get(13).toLong()
                    optionOfInterest.quote_date      =   dateFormat.parse(lineAsList.get(7))
                    optionOfInterest.strike          =   BigDecimal(lineAsList.get(8))
                    optionOfInterest.symbol          =   lineAsList.get(3)
                    optionOfInterest.symbol_underlying   =   lineAsList.get(0)
                    optionOfInterest.theta           =   BigDecimal(lineAsList.get(17))
                    optionOfInterest.type            =   lineAsList.get(5)
                    optionOfInterest.volume          =   lineAsList.get(12).toLong()
//                    optionOfInterest.description
                    optionsList.add(optionOfInterest)
                } else {
                println("List only contained ${lineAsList.size} elements... skipping")
                    continue
                }
            }
            println("End of file reached! My job here is done!")
        } finally {
            scanner.close()
        }
        return optionsList
    }


}