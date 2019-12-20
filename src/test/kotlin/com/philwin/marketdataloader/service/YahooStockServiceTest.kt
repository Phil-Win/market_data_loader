package com.philwin.marketdataloader.service

import com.philwin.marketdata.common.model.Stock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat


@SpringBootTest
class YahooStockServiceTest {
    @Autowired
    lateinit var yahooStockService: YahooStockService

    val dateFormat      =   SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun transformRawCsvToStockList() {
        var file    =   File("src\\test\\resources\\CAT.csv")
        assertTrue(file.exists())
        var expectedList    =   generateExpectedStockList()
        var actualList      =   yahooStockService.transformRawCsvToStockList(file)
        assertTrue(expectedList.size == actualList.size)

        assertEquals(expectedList.get(0).symbol, actualList.get(0).symbol)
        assertEquals(expectedList.get(0).bid, actualList.get(0).bid)
        assertEquals(expectedList.get(0).close, actualList.get(0).close)
        assertEquals(expectedList.get(0).date, actualList.get(0).date)
        assertEquals(expectedList.get(0).high, actualList.get(0).high)
        assertEquals(expectedList.get(0).high_52_week, actualList.get(0).high_52_week)
        assertEquals(expectedList.get(0).low, actualList.get(0).low)
        assertEquals(expectedList.get(0).low_52_week, actualList.get(0).low_52_week)
        assertEquals(expectedList.get(0).open, actualList.get(0).open)
        assertEquals(expectedList.get(0).percent_change, actualList.get(0).percent_change)
        assertEquals(expectedList.get(0).volume, actualList.get(0).volume)
        assertEquals(expectedList.get(1).symbol, actualList.get(1).symbol)
        assertEquals(expectedList.get(1).bid, actualList.get(1).bid)
        assertEquals(expectedList.get(1).close, actualList.get(1).close)
        assertEquals(expectedList.get(1).date, actualList.get(1).date)
        assertEquals(expectedList.get(1).high, actualList.get(1).high)
        assertEquals(expectedList.get(1).high_52_week, actualList.get(1).high_52_week)
        assertEquals(expectedList.get(1).low, actualList.get(1).low)
        assertEquals(expectedList.get(1).low_52_week, actualList.get(1).low_52_week)
        assertEquals(expectedList.get(1).open, actualList.get(1).open)
        assertEquals(expectedList.get(1).percent_change, actualList.get(1).percent_change)
        assertEquals(expectedList.get(1).volume, actualList.get(1).volume)
    }

    @Test
    fun transformRawCsvToStockList_BadCSV() {
        var file    =   File("src\\test\\resources\\InvalidCAT.csv")
        assertTrue(file.exists())
        var expectedList    =   ArrayList<Stock>()
        var actualList      =   yahooStockService.transformRawCsvToStockList(file)
        assertTrue(expectedList.size == actualList.size)

    }
    @Test
    fun transformRawCsvToStockList_NotRealFile() {
        var file    =   File("src\\test\\resources\\notRealFile.csv")
        var expectedList    =   ArrayList<Stock>()
        var actualList      =   yahooStockService.transformRawCsvToStockList(file)
        assertTrue(expectedList.size == actualList.size)

    }

    fun generateExpectedStockList() : List<Stock> {
        var stockList   =   ArrayList<Stock>()
        var stock   =   Stock()
        stock.symbol    =   "CAT"
        stock.date      =   dateFormat.parse("1962-01-02")
        stock.open      =   BigDecimal("1.604167")
        stock.high      =   BigDecimal("1.619792")
        stock.low       =   BigDecimal("1.588542")
        stock.close     =   BigDecimal("1.604167")
        stock.volume    =   "163200".toLong()
        stockList.add(stock)

        stock   =   Stock()
        stock.symbol    =   "CAT"
        stock.date      =   dateFormat.parse("1962-01-03")
        stock.open      =   BigDecimal("1.604167")
        stock.high      =   BigDecimal("1.619792")
        stock.low       =   BigDecimal("1.588542")
        stock.close     =   BigDecimal("1.619792")
        stock.volume    =   "156000".toLong()
        stockList.add(stock)

        return stockList
    }
}