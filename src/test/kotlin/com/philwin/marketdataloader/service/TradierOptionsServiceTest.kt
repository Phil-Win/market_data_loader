package com.philwin.marketdataloader.service

import com.google.gson.Gson
import com.philwin.marketdataloader.model.raw.options.tradier.Option
import com.philwin.marketdataloader.model.raw.stocks.tradier.TradierRawStock
import com.philwin.marketdataloader.model.transformed.Stock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import java.io.File
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
class TradierOptionsServiceTest {
    @Autowired
    lateinit var tradierOptionsService: TradierOptionsService

    val dateFormatOptionSymbol  =   SimpleDateFormat("MM-dd-yyyy")

    @Test
    fun tradierOptionsTransformer() {
        //TODO: test the transformation
    }

    @Test
    fun getQuoteDateFromFileName() {
        val input   =   File("src\\test\\resources\\aapl_options_processed_on_01_11_2019.json")
        val expectedValue   =   dateFormatOptionSymbol.parse("11-01-2019")
        assertEquals(expectedValue, tradierOptionsService.getQuoteDateFromFileName(input))
    }

    @Test
    fun getOptionTypeFromDescription_Call() {
        val input   =   "AAPL Nov 8 2019 \$247.50 Call"
        val expectedValue   =   "Call"
        assertEquals(expectedValue, tradierOptionsService.getOptionTypeFromDescription(input))
    }

    @Test
    fun getOptionTypeFromDescription_Put() {
        val input   =   "AAPL Nov 8 2019 \$247.50 Put"
        val expectedValue   =   "Put"
        assertEquals(expectedValue, tradierOptionsService.getOptionTypeFromDescription(input))
    }

    @Test
    fun getOptionTypeFromDescription_Neither() {
        val input   =   "AAPL Nov 8 2019 \$247.50 randoommmm"
        val expectedValue   =   null
        assertEquals(expectedValue, tradierOptionsService.getOptionTypeFromDescription(input))
    }

    @Test
    fun getExpirationFromSymbol() {
        val input   =   "AAPL191108P00247500"
        val expectedValue   =   dateFormatOptionSymbol.parse("11-08-2019")
        assertEquals(expectedValue, tradierOptionsService.getExpirationFromSymbol(input))
    }
}