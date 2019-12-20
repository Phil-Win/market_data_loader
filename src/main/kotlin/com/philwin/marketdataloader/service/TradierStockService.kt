package com.philwin.marketdataloader.service

import com.google.gson.Gson
import com.philwin.marketdata.common.model.Stock
import com.philwin.marketdata.common.repository.StockRespository
import com.philwin.marketdataloader.model.raw.stocks.tradier.TradierRawStock
import com.philwin.marketdataloader.util.FileUtil
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component
import java.io.File
import java.util.*

@Component
class TradierStockService : IStockService {
    @Autowired
    lateinit var stockRespository: StockRespository

    override fun loadData(inputFolder: String, outputFolder: String, invalidFolder: String): Boolean {
        val inputFolder      =   FileSystemResource(inputFolder).file;
        val outputFolder    =   FileSystemResource(outputFolder).file;
        val invalidFolder   =   FileSystemResource(invalidFolder).file

        if (inputFolder.isFile) {
            loadAndMoveData(inputFolder, outputFolder, invalidFolder)
        } else {
            var fileList    =   FileUtils.listFiles(inputFolder, null, true)
            for (file in fileList) {
                if (file.name.contains("all_stock_data")) {
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
        var tradierRawStock : TradierRawStock
        var counter     =   0
        var stockList : List<Stock>
        println("Processing TradierStockService File: TradierStockService ${file.absolutePath}")
        try {
            while (scanner.hasNext()) {
                lineOfInterest  =   scanner.nextLine()
                tradierRawStock =   gson.fromJson(lineOfInterest, TradierRawStock::class.java)
                stockList   =   tradierStockTransformer(tradierRawStock)
                stockRespository.saveAll(stockList)
                println("TradierStockService : Successfully loaded all ${stockList.size} stock records!")
            }
        } finally {
            scanner.close()
        }
        println("Finished Processing TradierStockService File ${file.absolutePath}")
        return true
    }

    fun tradierStockTransformer(rawStock : TradierRawStock) : List<Stock> {
//        var stockOfInterest : Stock
        var stockList   :   MutableList<Stock> =   ArrayList<Stock>()
        for (quote in rawStock.quotes!!.quote!!.iterator()) {
//            stockOfInterest =   Stock()
//            stockOfInterest.ask     =   quote.ask
//            stockOfInterest.bid     =   quote.bid
//            stockOfInterest.close   =   quote.prevclose
//            stockOfInterest.date    =   Date(quote.trade_date)
//            stockOfInterest.high    =   quote.high
//            stockOfInterest.high_52_week    =   quote.week_52_high
//            stockOfInterest.low             =   quote.low
//            stockOfInterest.low_52_week     =   quote.week_52_low
//            stockOfInterest.open            =   quote.open
//            stockOfInterest.percent_change  =   quote.change_percentage
//            stockOfInterest.symbol          =   quote.symbol
//            stockOfInterest.volume          =   quote.volume
//            stockList.add(stockOfInterest)
            stockList.add(
                Stock (
                    quote.symbol,
                    Date(quote.trade_date),
                    quote.open,
                    quote.prevclose,
                    quote.high,
                    quote.low,
                    quote.bid,
                    quote.ask,
                    quote.change_percentage,
                    quote.volume,
                    quote.week_52_high,
                    quote.week_52_low
                )
            )
        }
        println("TradierStockService: Found ${stockList.size} quotes to process")
        return stockList
    }

}