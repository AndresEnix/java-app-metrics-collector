# Java Metrics Collector

This project is in charge of generate logs based on queries executed into different data sources. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

To run the project the following programs mut be installed:

* Docker
* A datasource to connect with.

### Installing


To install a local java-app-metrics-collector you must perform the following steps:

1. Go to the root path of the project and execute the build command:
    ```
    ./gradlew clean build
    ```
2. Build the java-app-metrics-collector image:
    ```
    docker image build --tag andres_enix/metrics-collector .
    ```

3. Run the java-app-metrics-collector container with the mandatory environment variables:
    ```
    docker container run --publish 8080:8080 --name metrics-collector -e metric_collector_profile='default' -e metric_collector_blacklist='database_objects' -e jdbc_url='localhost' -e db_port='1200' -e db_name='postgresql_db' -e db_user='user' -e db_password='pwd123' -e db_max_pool_size='50' andres_enix/metrics-collector
    ```

#### Mandatory Environment Variables

* CustomerSegment: The customer segment is used as the spring profile, this parameter indicates which file will load the application in order to register metrics.  
* metric_collector_blacklist: The list of application names that are going to be disabled and will not log data. 

    \* In the case that this parameter is empty all of the services declared in ```Application.yml``` will log data.
* DB_TYPE: The database type is used as the spring profile. this parameter indicated which database datasource will be instantiated by spring.
* ClusterDBURL: Database string connection.
* DBPort: Database port.
* DBName: Database name.
* AWS_REGION & CSS_DB_SECRET: If both parameters are provided the application will retrieve the database username and password from the AWS secret manager service.
* CSS_DB_USER: CSS database user name.

#### Configuration files

The project is composed of several configuration files that support schedulers execution.

* Metrics collector configuration - ```Application.yml``` which is located on  ```service/src/main/resources ``` and contains configuration related to the database connection, active profile, blacklist services and metrics variables. 
* In the case of a database type metric there is another configuration file ```xxxxxx.sql``` located on ```service/src/main/resources/database``` that contains the specific sql statement of the metric declared in ```Application.yml``. There should exist one sql file for each declared metric.
  
## Running the tests

* In order to perform the execution of the project test you have to run:
    ```
    ./gradlew test 
    ```
    
### Break down into end to end tests

The tests validate the following collector app operations.

* Job generation according to the crontab parametrization.
* Db execution of well and poorly constructed metrics.
* Enabling and disabling of collector metrics.
* Blacklist operation including and excluding some service names.


## Deployment

Collector metric application should be deployed using the defined pipeline on jenkins.

## Built With

* [Gradle](https://gradle.org/) - Build automation system.
* [Spring Boot](https://spring.io/projects/spring-boot) - Project framework.
* [Docker](https://www.docker.com/) - Virtualization system.

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Andres Perez**, **Guido Buchely**, **Harold Murcia** - *Initial work*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

