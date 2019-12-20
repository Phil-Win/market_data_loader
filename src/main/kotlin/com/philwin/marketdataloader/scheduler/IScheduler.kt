package com.philwin.marketdataloader.scheduler


interface IScheduler {
    val inputFolder : String
    val outputFolder : String
    val invalidFolder : String
    fun scheduleJob()

}