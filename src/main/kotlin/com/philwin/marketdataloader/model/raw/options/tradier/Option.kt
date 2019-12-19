package com.philwin.marketdataloader.model.raw.options.tradier

import java.lang.reflect.Constructor
import java.math.BigDecimal

data class Option(
        var ask: BigDecimal,
        var askDate: Any?,
        var askexch: String,
        var asksize: Long,
        var averageVolume: Long?,
        var bid: BigDecimal,
        var bidDate: Any?,
        var bidexch: String,
        var bidsize: Long,
        var change: BigDecimal?,
        var changePercentage: BigDecimal?,
        var close: BigDecimal?,
        var contractSize: Any?,
        var description: String,
        var exch: String,
        var expirationDate: Any?,
        var expirationType: Any?,
        var greeks: Greeks,
        var high: BigDecimal?,
        var last: BigDecimal?,
        var lastVolume: Long?,
        var low: BigDecimal?,
        var `open`: BigDecimal?,
        var openInterest: Long?,
        var optionType: String?,
        var prevclose: BigDecimal?,
        var rootSymbol: String?,
        var strike: BigDecimal,
        var symbol: String,
        var tradeDate: Any?,
        var type: String,
        var underlying: String,
        var volume: Long,
        var week52High: BigDecimal?,
        var week52Low: BigDecimal?
)