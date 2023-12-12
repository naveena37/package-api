## Parcel Application ##
The parcel application recommends a package size and associated cost for a set of package dimensions.

The application is written as an API with one endpoint to recommend package type and cost based on the input dimensions.
Swagger integration is done to provide Javadoc for the API, so it is easy to invoke the API 
once the SpringBoot application is up and running.

### Software versions
* JDK: 17
* Gradle: 8.4
* SpringBoot: 3.1.5

### Host the API
#### Option 1
Build and run the Main class ParcelApplication using a run configuration in a Java compatible IDE
```bash
./gradlew build
```
Can see that the application has started and is hosted at port 8081
```bash
[main] o.s.b.w.embedded.tomcat.TomcatWebServer: Tomcat started on port(s): 8081 (http) with context path ''
[main] nz.co.trademe.parcel.ParcelApplication: Started ParcelApplication in 1.151 seconds (process running for 1.4)
```

### Option 2
#### Build and run the executable jar
```bash
./gradlew bootJar
java -jar build/libs/parcel-0.0.1-SNAPSHOT.jar
```
Can see the following in console..
```bash
 :: Spring Boot ::                (v3.1.5)

[main] nz.co.trademe.parcel.ParcelApplication   : Starting ParcelApplication v0.0.1-SNAPSHOT using Java 17.0.4.1 with PID 79469
[main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
[main] nz.co.trademe.parcel.ParcelApplication   : Started ParcelApplication in 1.56 seconds (process running for 1.794)
```

### Access the API using bundled Swagger UI
Swagger UI location:
* http://localhost:8081/swagger-ui/
* http://localhost:8081/swagger-ui/index.html#/Parcel/recommendPackage

### Unit & Integration tests
```bash
./gradlew test
```
#### Test Results
Test results can be found in `[project directory]/build/reports/tests`

To see all available tasks `./gradlew tasks`