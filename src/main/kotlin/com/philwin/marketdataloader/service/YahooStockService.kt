package com.philwin.marketdataloader.service

import com.philwin.marketdataloader.model.transformed.Stock
import com.philwin.marketdataloader.repository.StockRespository
import com.philwin.marketdataloader.util.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@Configuration
class YahooStockService : IStockService {
    @Autowired
    lateinit var stockRespository: StockRespository

    val dateFormat      =   SimpleDateFormat("yyyy-MM-dd")
    val headerLine      =   "Date,Open,High,Low,Close,Adj Close,Volume"

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
            var counter     =   0
            var stockList = transformRawCsvToStockList(file)
            for (stock in stockList) {
                counter++
                if (counter % 100 == 0) {
                    println("(Only Prints every 100) YahooStockService Successfully added record to database #${counter} ")
                }
                stockRespository.save(stock)
            }
            return (stockList.size > 0)
        } catch (e: Exception) {
            return false
        }
    }

    fun transformRawCsvToStockList(file : File) : List<Stock> {
        if (!file.exists()) {
            return ArrayList<Stock>()
        }
        val scanner =   Scanner(file)
        var lineOfInterest : String
        var lineAsList : List<String>
        var stockOfInterest : Stock
        var stockList   =   ArrayList<Stock>()
        var counter     =   0
        try {
            lineOfInterest = scanner.nextLine()
            println("Processing the file YahooStockService : ${file.name}")

            if (!lineOfInterest.equals(headerLine)) {
                println("The header of ${lineOfInterest} does not match the expected header : ${headerLine}")
                return stockList
            }

            while (scanner.hasNext()) {
                counter++
                if (counter % 100 == 0) {
                    println("(Only Prints every 100) Processing line number : ${counter} from file ${file.name}")
                }
                stockOfInterest = Stock()
                lineOfInterest = scanner.nextLine();
                lineAsList = lineOfInterest.split(",");
                if (lineAsList.size == 7) {
                    if (counter % 100 == 0) {
                        println("List contains 7 elements! Adding to database from file ${file.name}")
                    }
                    stockOfInterest.symbol = file.nameWithoutExtension
                    stockOfInterest.date = dateFormat.parse(lineAsList.get(0))
                    stockOfInterest.open = BigDecimal(lineAsList.get(1))
                    stockOfInterest.high = BigDecimal(lineAsList.get(2))
                    stockOfInterest.low = BigDecimal(lineAsList.get(3))
                    stockOfInterest.close = BigDecimal(lineAsList.get(4))
                    stockOfInterest.volume = lineAsList.get(6).toLong()
                    stockList.add(stockOfInterest)
                } else {
                    if (counter % 100 == 0) {
                        println("List only contained ${lineAsList.size} elements... skipping")
                    }
                    continue
                }
            }
            println("End of file reached! My job here is done! YahooStockService : ${file.name}")
        } finally {
            scanner.close()
        }
        return stockList
    }


}