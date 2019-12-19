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

    @Value("\${process.folder.historicaloptions.option.input}")
    lateinit var inputFolder : String

    @Value("\${process.folder.historicaloptions.option.output}")
    lateinit var outputFolder : String

    @Value("\${process.folder.historicaloptions.option.invalid}")
    lateinit var invalidFolder : String

    @Scheduled(fixedRate = 24*60*60*1000)
    fun processData() {
        println("Processing  Historical Options Data!")
        historicalOptionsService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Historical Options Processing Data!")
    }

}