# eu-vat-rates-performance-tests

Performance test suite for the `eu-vat-rates`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Pre-requisites

### Services

Start Mongo Docker container as follows:

```bash
docker run --rm -d -p 27017:27017 --name mongo mongo:4.4
```

Start `EU_VAT_RATES_ALL` services as follows:

```bash
sm2 --start EU_VAT_RATES_ALL
```

### Logging

The default log level for all HTTP requests is set to `WARN`. Configure [logback.xml](src/test/resources/logback.xml) to update this if required.

### WARNING :warning:

Do **NOT** run a full performance test against staging from your local machine. Please [implement a new performance test job](https://confluence.tools.tax.service.gov.uk/display/DTRG/Practical+guide+to+performance+testing+a+digital+service#Practicalguidetoperformancetestingadigitalservice-SettingupabuildonJenkinstorunagainsttheStagingenvironment) and execute your job from the dashboard in [Performance Jenkins](https://performance.tools.staging.tax.service.gov.uk).

## Tests

Run smoke test (locally) as follows:

```bash
INTERNAL_AUTH_TOKEN=0dde8067-87d2-4bda-95a7-aa9557e6ae83 \
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test

Note: ensure there is no space after the backslash otherwise this will error
```

Run full performance test (locally) as follows:

```bash
INTERNAL_AUTH_TOKEN=0dde8067-87d2-4bda-95a7-aa9557e6ae83 \ 
sbt -DrunLocal=true gatling:test
```

Smoke tests and full runs can be accessed on Jenkins:
https://performance.tools.staging.tax.service.gov.uk/view/Import%20One%20Stop%20Shop%20performance%20tests/job/eu-vat-rates-performance-tests/


## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
