package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.YahooStockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class YahooStockScheduler : IScheduler{
    @Autowired
    lateinit var yahooStockService : YahooStockService

    @Value("\${process.output.base}/stocks/yahoo/input")
    override lateinit var inputFolder : String

    @Value("\${process.output.base}/stocks/yahoo/output")
    override lateinit var outputFolder : String

    @Value("\${process.output.base}/stocks/yahoo/invalid")
    override lateinit var invalidFolder : String

    @Scheduled(fixedDelay = 4*60*60*1000)
    override fun scheduleJob() {
        println("Processing Yahoo Stock Data!")
        yahooStockService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished Yahoo Stock Processing Data!")
    }

}