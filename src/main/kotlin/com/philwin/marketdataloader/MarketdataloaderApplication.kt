package com.philwin.marketdataloader


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EntityScan(basePackages = ["com.philwin.marketdata.common.model"])
@EnableJpaRepositories(basePackages =  ["com.philwin.marketdata.common.repository"])
class MarketdataloaderApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(MarketdataloaderApplication::class.java, *args)
		}
	}
}

