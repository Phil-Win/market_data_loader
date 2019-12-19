package com.philwin.marketdataloader.service

import com.philwin.marketdataloader.model.transformed.Stock

interface IStockService {
    fun loadData(inputFolder: String, outputFolder: String, invalidFolder : String): Boolean
}