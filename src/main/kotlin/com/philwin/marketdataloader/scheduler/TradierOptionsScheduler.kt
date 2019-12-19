package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.TradierOptionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class TradierOptionsScheduler {
    @Autowired
    lateinit var tradierOptionsService : TradierOptionsService

    @Value("\${process.folder.tradier.option.input}")
    lateinit var inputFolder : String

    @Value("\${process.folder.tradier.option.output}")
    lateinit var outputFolder : String

    @Value("\${process.folder.tradier.option.invalid}")
    lateinit var invalidFolder : String

    @Scheduled(fixedRate = 24*60*60*1000)
    fun processData() {
        println("Processing  Tradier Options Data!")
        tradierOptionsService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Tradier Options Processing Data!")
    }

}