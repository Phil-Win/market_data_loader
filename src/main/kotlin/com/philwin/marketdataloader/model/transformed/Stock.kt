package com.philwin.marketdataloader.model.transformed

import java.math.BigDecimal
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "stock")
class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var symbol: String? = null
    @Temporal(TemporalType.DATE)
    var date: Date? = null
    var open: BigDecimal? = null
    var close: BigDecimal? = null
    var high: BigDecimal? = null
    var low: BigDecimal? = null
    var bid: BigDecimal? = null
    var ask: BigDecimal? = null
    var percent_change: BigDecimal? = null
    var volume: Long? = null
    var high_52_week: BigDecimal? = null
    var low_52_week: BigDecimal? = null

    constructor(){}
}
