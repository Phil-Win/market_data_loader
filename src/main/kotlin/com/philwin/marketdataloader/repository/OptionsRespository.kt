package com.philwin.marketdataloader.repository

import com.philwin.marketdataloader.model.transformed.Options
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OptionsRespository : CrudRepository<Options, Long> {
    fun findBySymbol(symbol: String): List<Options>
}