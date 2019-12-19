package com.philwin.marketdataloader

import com.philwin.marketdataloader.model.transformed.Stock
import com.philwin.marketdataloader.repository.StockRespository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
class MarketdataloaderApplication {//: CommandLineRunner{
//	@Autowired
//	private val stockRespository: StockRespository? = null



	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(MarketdataloaderApplication::class.java, *args)
		}
	}

//	override fun run(vararg args: String?) {
//		println("Hello Application from the Runner!")
//		var inputFile	=	FileSystemResource("D:\\Docker\\StockDataRaw\\input\\QQQ.csv")
//		var scanner	=	Scanner(inputFile.file)
//		var line : String
//		var lineAsList : List<String>
//		var dateFormat	=	SimpleDateFormat("yyyy-MM-dd")
//		var stockOfInterest : Stock
//		scanner.nextLine();
//		while (scanner.hasNext()) {
//			stockOfInterest	=	Stock();
//			line	=	scanner.nextLine();
//			lineAsList	=	line.split(",");
//			if (lineAsList.size == 7) {
//				println("List contains 7 elements! Adding to database : ${line}")
//				stockOfInterest.symbol	=	"QQQ"
//				stockOfInterest.date	=	dateFormat.parse(lineAsList.get(0))
//				stockOfInterest.open	=	BigDecimal(lineAsList.get(1))
//				stockOfInterest.high	=	BigDecimal(lineAsList.get(2))
//				stockOfInterest.low		=	BigDecimal(lineAsList.get(3))
//				stockOfInterest.close	=	BigDecimal(lineAsList.get(4))
//				stockOfInterest.volume	=	lineAsList.get(6).toInt()
//				stockRespository!!.save(stockOfInterest)
//			} else {
//				println("List only contained ${lineAsList.size} elements... skipping")
//			}
//		}
//		stockRespository!!.save(Stock("SPY", GregorianCalendar(2019, Calendar.APRIL, 1).time, BigDecimal.valueOf(75.33)))
////		stockRespository.save(Stock("SPY", GregorianCalendar(2015, Calendar.MAY, 12).time, BigDecimal.valueOf(32.33)))
//		stockRespository.save(Stock("QQQ", GregorianCalendar(1970, Calendar.JULY, 5).time, BigDecimal.valueOf(201.55)))
//		var iterator : MutableIterable<Stock>	=	stockRespository.findAll()
//		for (stock in iterator) {
//			println("Printing the stock : ${stock.symbol}, ${stock.date}, ${stock.open}")
//		}
//	}
}

