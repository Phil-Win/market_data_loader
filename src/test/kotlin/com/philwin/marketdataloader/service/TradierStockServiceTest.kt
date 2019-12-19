package com.philwin.marketdataloader.service

import com.google.gson.Gson
import com.philwin.marketdataloader.model.raw.stocks.tradier.TradierRawStock
import com.philwin.marketdataloader.model.transformed.Stock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
class TradierStockServiceTest {
    @Autowired
    lateinit var tradierStockService: TradierStockService

    val dateFormat      =   SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun tradierStockTransformer() {
        var gson    =   Gson()
        var exampleString   =   "{\"quotes\":{\"quote\":[{\"ask\":263.75,\"askexch\":\"N\",\"asksize\":2,\"bid\":263.71,\"bidexch\":\"Y\",\"bidsize\":1,\"change\":-3.54,\"close\":null,\"description\":\"Apple Inc\",\"exch\":\"Q\",\"high\":268.25,\"last\":263.7189,\"low\":263.55,\"open\":267.27,\"prevclose\":267.25,\"symbol\":\"AAPL\",\"type\":\"stock\",\"volume\":10253121,\"ask_date\":1575303014000,\"average_volume\":25556167,\"bid_date\":1575303014000,\"change_percentage\":-1.33,\"last_volume\":400,\"root_symbols\":\"AAPL\",\"trade_date\":1575303014104,\"week_52_high\":268.0,\"week_52_low\":142},{\"ask\":1766.71,\"askexch\":\"Q\",\"asksize\":1,\"bid\":1766.0,\"bidexch\":\"Q\",\"bidsize\":2,\"change\":-34.35,\"close\":null,\"description\":\"Amazon.com Inc\",\"exch\":\"Q\",\"high\":1805.55,\"last\":1766.4501,\"low\":1764.0,\"open\":1804.4,\"prevclose\":1800.8,\"symbol\":\"AMZN\",\"type\":\"stock\",\"volume\":1520698,\"ask_date\":1575303012000,\"average_volume\":2958649,\"bid_date\":1575303012000,\"change_percentage\":-1.91,\"last_volume\":100,\"root_symbols\":\"AMZN\",\"trade_date\":1575303009191,\"week_52_high\":2035.8,\"week_52_low\":1307}]}}"
        var actualList  =   tradierStockService.tradierStockTransformer(gson.fromJson(exampleString, TradierRawStock::class.java))
        var expectedList    =   generateExpectedStock()
        assertEquals(expectedList.get(0).ask ,actualList.get(0).ask )
        assertEquals(expectedList.get(0).bid ,actualList.get(0).bid )
        assertEquals(expectedList.get(0).close ,actualList.get(0).close )
        assertEquals(expectedList.get(0).date ,actualList.get(0).date )
        assertEquals(expectedList.get(0).high ,actualList.get(0).high )
        assertEquals(expectedList.get(0).high_52_week ,actualList.get(0).high_52_week )
        assertEquals(expectedList.get(0).low ,actualList.get(0).low )
        assertEquals(expectedList.get(0).low_52_week ,actualList.get(0).low_52_week )
        assertEquals(expectedList.get(0).open ,actualList.get(0).open )
        assertEquals(expectedList.get(0).percent_change ,actualList.get(0).percent_change )
        assertEquals(expectedList.get(0).symbol ,actualList.get(0).symbol )
        assertEquals(expectedList.get(0).volume ,actualList.get(0).volume )

        assertEquals(expectedList.get(1).ask ,actualList.get(1).ask )
        assertEquals(expectedList.get(1).bid ,actualList.get(1).bid )
        assertEquals(expectedList.get(1).close ,actualList.get(1).close )
        assertEquals(expectedList.get(1).date ,actualList.get(1).date )
        assertEquals(expectedList.get(1).high ,actualList.get(1).high )
        assertEquals(expectedList.get(1).high_52_week ,actualList.get(1).high_52_week )
        assertEquals(expectedList.get(1).low ,actualList.get(1).low )
        assertEquals(expectedList.get(1).low_52_week ,actualList.get(1).low_52_week )
        assertEquals(expectedList.get(1).open ,actualList.get(1).open )
        assertEquals(expectedList.get(1).percent_change ,actualList.get(1).percent_change )
        assertEquals(expectedList.get(1).symbol ,actualList.get(1).symbol )
        assertEquals(expectedList.get(1).volume ,actualList.get(1).volume )
    }


    fun generateExpectedStock() : List<Stock> {
        var stockList   =   ArrayList<Stock>()
        var stock   =   Stock()
        stock.ask       =   BigDecimal("263.75")
        stock.bid       =   BigDecimal("263.71")
        stock.close     =   BigDecimal("267.25")
        stock.date      =   Date(1575303014104)
        stock.high      =   BigDecimal("268.25")
        stock.high_52_week  =   BigDecimal("268.0")
        stock.low       =   BigDecimal("263.55")
        stock.low_52_week   =   BigDecimal("142")
        stock.open      =   BigDecimal("267.27")
        stock.percent_change    =   BigDecimal("-1.33")
        stock.symbol    =   "AAPL"
        stock.volume    =   "10253121".toLong()
        stockList.add(stock)

        stock   =   Stock()
        stock.ask       =   BigDecimal("1766.71")
        stock.bid       =   BigDecimal("1766.0")
        stock.close     =   BigDecimal("1800.8")
        stock.date      =   Date(1575303009191)
        stock.high      =   BigDecimal("1805.55")
        stock.high_52_week  =   BigDecimal("2035.8")
        stock.low       =   BigDecimal("1764.0")
        stock.low_52_week   =   BigDecimal("1307")
        stock.open      =   BigDecimal("1804.4")
        stock.percent_change    =   BigDecimal("-1.91")
        stock.symbol    =   "AMZN"
        stock.volume    =   "1520698".toLong()
        stockList.add(stock)

        return stockList
    }
}