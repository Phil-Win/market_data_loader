package com.philwin.marketdataloader.model.raw.stocks.tradier

import java.math.BigDecimal

data class Quote(
        var ask: BigDecimal,
        var ask_date: Long,
        var askexch: String,
        var asksize: Long,
        var average_volume: Long,
        var bid: BigDecimal,
        var bid_date: Long,
        var bidexch: String,
        var bidsize: Long,
        var change: BigDecimal,
        var change_percentage: BigDecimal,
        var close: BigDecimal,
        var description: String,
        var exch: String,
        var high: BigDecimal,
        var last: BigDecimal,
        var last_volume: Long,
        var low: BigDecimal,
        var `open`: BigDecimal,
        var prevclose: BigDecimal,
        var root_symbols: String,
        var symbol: String,
        var trade_date: Long,
        var type: String,
        var volume: Long,
        var week_52_high: BigDecimal,
        var week_52_low: BigDecimal
)