package com.philwin.marketdataloader.model.raw.options.tradier

import java.math.BigDecimal

data class Greeks(
        var askIv: BigDecimal?,
        var bidIv: BigDecimal?,
        var delta: BigDecimal,
        var gamma: BigDecimal,
        var midIv: BigDecimal?,
        var phi: BigDecimal,
        var rho: BigDecimal,
        var smvVol: BigDecimal?,
        var theta: BigDecimal,
        var updatedAt: Any?,
        var vega: BigDecimal
)