# Tax Calculator

## Running the application
Make sure to build the project (See below)

To run the application execute `java -jar tax-calculator-backend/target/tax-calculator-backend-0.0.1-SNAPSHOT.jar`
Then navigate to `http://localhost:8080/`

## Local Development

While developing locally, it can be useful to have an angular dev server running so that your changes get applied in realtime. 
You can simply run the backend as well as angular `ng serve --proxy-config proxy.conf.json`, the proxy will divert all traffic to the backend.

## Build
Run `npm clean package` to build the project. The build packages will be located in `./tax-calculator-backend/target/`
The angular frontend will be built by maven which delegates to a npm plugin.


## Running unit tests

Run `mvn test` to execute the unit tests

