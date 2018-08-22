# SpringBoot, SpringDataJPA, jABCI based Tendermint Demo Application

## Prerequisite
```
- OpenJDK 1.8 : http://openjdk.java.net/install/
- Tendermint : https://tendermint.com/docs/introduction/install.html
- Maven : https://maven.apache.org/
```

## Run
#### Run Tendermint
```
# tendermint init
# tendermint node
```

#### Run the ABCI Server 
```
1) mvn clean && package
2) Run main method on TendermintApp.java
```

## Run Option
#### Tendermint Automatically Running by Spring (for Development)
```
-Dtendermint.embedded=true
Spring will execute tendermint where /usr/local/bin/tendermint
```
 
## Test Request
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"databaseType":"Oracle","connectionType":"NET","dbHost":"test-db.sqlgate.com", "dbPort":1521, "dbConnectionMode":"SYSDBA", "dbUser":"system", "orgId":1, "userId":1,"ip":"192.168.0.1"}' \
  http://localhost:8080/api/v1/abci/connectionLog
```
 
## Dependencies
- Java 8
- Spring Boot 2.0.4
- QueryDSL
- Tendermint 0.23.0-013b9cef
- jABCI 0.12.3
- gRPC
- Protocol Buffer
- H2