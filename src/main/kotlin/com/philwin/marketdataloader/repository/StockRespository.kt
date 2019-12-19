package com.philwin.marketdataloader.repository

import com.philwin.marketdataloader.model.transformed.Stock
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StockRespository : CrudRepository<Stock, Long> {
    fun findBySymbol(symbol: String): List<Stock>

    fun findBySymbolAndDate(symbol: String, date: Date) : List<Stock>
}