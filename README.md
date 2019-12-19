This will be the central repository for taking raw data and storing it.
You will need volume mounts for the following locations:
/data/stocks/tradier/input
/data/stocks/yahoo/input
/data/options/tradier/input
/data/options/historicaloptions/input

/data/stocks/tradier/output
/data/stocks/yahoo/output
/data/options/tradier/output
/data/options/historicaloptions/output

/data/stocks/tradier/invalid
/data/stocks/yahoo/invalid
/data/options/tradier/invalid
/data/options/historicaloptions/invalid

The intent is to process all the various data sources into a mysql container on the network
options-apps

Change the following accordingly as they're defaulting to my internal database not exposed to the internet
# MySQL
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

#`hibernate_sequence' doesn't exist
spring.jpa.hibernate.use-new-id-generator-mappings=

# drop n create table, good for testing, comment this in production
spring.jpa.hibernate.ddl-auto=

My docker command to build :


My docker run command :
docker run -p 8756:8756 -e server.port=8756 -v d:/Docker/OptionsDataRaw/HistoricalOptions/input:/data/options/historicaloptions/input -v d:/Docker/OptionsDataRaw/HistoricalOptions/output:/data/options/historicaloptions/output -v d:/Docker/OptionsDataRaw/HistoricalOptions/invalid:/data/options/historicaloptions/invalid -v d:/Docker/daily_stock_data_landing_zone:/data/options/tradier/input -v d:/Docker/OptionsDataRaw/Tradier/invalid:/data/options/tradier/invalid -v d:/Docker/OptionsDataRaw/Tradier/output:/data/options/tradier/output -v d:/Docker/daily_stock_data_landing_zone:/data/stocks/tradier/input -v d:/Docker/StockDataRaw/Tradier/output:/data/stocks/tradier/output -v d:/Docker/StockDataRaw/Tradier/invalid:/data/stocks/tradier/invalid -v d:/Docker/StockDataRaw/Yahoo/input:/data/stocks/yahoo/input -v d:/Docker/StockDataRaw/Yahoo/output:/data/stocks/yahoo/output -v d:/Docker/StockDataRaw/Yahoo/invalid:/data/stocks/yahoo/invalid --net options-apps -d --name market_data_loader philwin/market_data_loader:latest