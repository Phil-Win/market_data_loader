package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.TradierStockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class TradierStockScheduler {
    @Autowired
    lateinit var tradierStockService : TradierStockService

    @Value("\${process.folder.tradier.stock.input}")
    lateinit var inputFolder : String

    @Value("\${process.folder.tradier.stock.output}")
    lateinit var outputFolder : String

    @Value("\${process.folder.tradier.stock.invalid}")
    lateinit var invalidFolder : String

    @Scheduled(fixedRate = 24*60*60*1000)
    fun processData() {
        println("Processing  Tradier Stock Data!")
        tradierStockService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Tradier Stock Processing Data!")
    }

}