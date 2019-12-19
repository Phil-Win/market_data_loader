package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.HistoricalOptionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class HistoricalOptionsScheduler {
    @Autowired
    lateinit var historicalOptionsService : HistoricalOptionsService

    @Value("\${process.output.base}/options/historicaloptions/input")
    lateinit var inputFolder : String

    @Value("\${process.output.base}/options/historicaloptions/output")
    lateinit var outputFolder : String

    @Value("\${process.output.base}/options/historicaloptions/invalid")
    lateinit var invalidFolder : String

    @Scheduled(fixedDelay = 4*60*60*1000)
    fun processData() {
        println("Processing  Historical Options Data!")
        historicalOptionsService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Historical Options Processing Data!")
    }

}