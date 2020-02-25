package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.TradierOptionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class TradierOptionsScheduler : IScheduler{
    @Autowired
    lateinit var tradierOptionsService : TradierOptionsService

    @Value("\${process.output.base}/MarketData/Input/TradierOptions")
    override lateinit var inputFolder : String

    @Value("\${process.output.base}/MarketData/Archive/TradierOptions")
    override lateinit var outputFolder : String

    @Value("\${process.output.base}/MarketData/Invalid/TradierOptions")
    override lateinit var invalidFolder : String


    @Scheduled(fixedDelay = 4*60*60*1000)
    override fun scheduleJob() {
        println("Processing  Tradier Options Data!")
        tradierOptionsService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Tradier Options Processing Data!")
    }


}