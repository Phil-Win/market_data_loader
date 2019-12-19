package com.philwin.marketdataloader.model.transformed

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "options")
class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var symbol_underlying : String? = null
    var mark_underlying : BigDecimal? = null
    var type : String? = null
    @Temporal(TemporalType.DATE)
    var quote_date : Date? = null
    @Temporal(TemporalType.DATE)
    var expiration_date : Date? = null
    var strike : BigDecimal? = null
    var bid : BigDecimal? = null
    var ask : BigDecimal? = null
    var volume : Long? = null
    var open_interest : Long? = null
    var implied_volatility : BigDecimal? = null
    var implied_volatility_atm : BigDecimal? = null
    var delta : BigDecimal? = null
    var gamma : BigDecimal? = null
    var theta : BigDecimal? = null
    var vega : BigDecimal? = null
    var symbol : String? = null
    var description : String? = null
    constructor(){}
}