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
docker run -p 8756:8756 -e server.port=8756 -v d:/Docker/Volume:/volume --net options-apps -d --name market_data_loader philwin/market_data_loader:latest
