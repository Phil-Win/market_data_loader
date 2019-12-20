package com.philwin.marketdataloader.service

interface IStockService {
    fun loadData(inputFolder: String, outputFolder: String, invalidFolder : String): Boolean
}