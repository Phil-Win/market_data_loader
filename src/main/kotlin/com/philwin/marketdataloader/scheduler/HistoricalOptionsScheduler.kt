package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.HistoricalOptionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class HistoricalOptionsScheduler : IScheduler{
    @Autowired
    lateinit var historicalOptionsService : HistoricalOptionsService

    @Value("\${process.output.base}/MarketData/Input/HistoricalOptions")
    override lateinit var inputFolder : String

    @Value("\${process.output.base}/MarketData/Archive/HistoricalOptions")
    override lateinit var outputFolder : String

    @Value("\${process.output.base}/MarketData/Invalid/HistoricalOptions")
    override lateinit var invalidFolder : String

    @Scheduled(fixedDelay = 4*60*60*1000)
    override fun scheduleJob() {
        println("Processing  Historical Options Data!")
        historicalOptionsService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Historical Options Processing Data!")
    }


}