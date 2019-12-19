package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.YahooStockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class YahooStockScheduler {
    @Autowired
    lateinit var yahooStockService : YahooStockService

    @Value("\${process.folder.yahoo.input}")
    lateinit var inputFolder : String

    @Value("\${process.folder.yahoo.output}")
    lateinit var outputFolder : String

    @Value("\${process.folder.yahoo.invalid}")
    lateinit var invalidFolder : String

    @Scheduled(fixedRate = 24*60*60*1000)
    fun processData() {
        println("Processing Yahoo Stock Data!")
        yahooStockService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished Yahoo Stock Processing Data!")
    }

}