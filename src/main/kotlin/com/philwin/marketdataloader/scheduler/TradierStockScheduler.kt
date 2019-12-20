package com.philwin.marketdataloader.scheduler

import com.philwin.marketdataloader.service.TradierStockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class TradierStockScheduler :IScheduler{
    @Autowired
    lateinit var tradierStockService : TradierStockService

    @Value("\${process.output.base}/stocks/tradier/input")
    override lateinit var inputFolder : String

    @Value("\${process.output.base}/stocks/tradier/output")
    override lateinit var outputFolder : String

    @Value("\${process.output.base}/stocks/tradier/invalid")
    override lateinit var invalidFolder : String

    @Scheduled(fixedDelay = 4 * 60 * 60 * 1000)
    override fun scheduleJob() {
        println("Processing  Tradier Stock Data!")
        tradierStockService.loadData(inputFolder, outputFolder, invalidFolder)
        println("Finished  Tradier Stock Processing Data!")
    }

}