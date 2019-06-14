# City Graph

City graph takes location data (cities) and creates a fully connected graph using different distance metrics as edges. These metrics include as driving, walking and straight-line distances. The country data is taken from SimpleMaps, OpenRouteService is used to calculate distances.

### Prerequisites

* Internet connection is needed to download city data
* Java JRE or openJDK version 8 or later
* API key from [OpenRouteService](https://openrouteservice.org/plans/) (free)

### Installing

#### Linux
'sudo apt-get install openjdk-11-jdk' to install openjdk-11

#### Windows
Download Java JRE from [Oracle](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

1. 'java -version' to verify java is installed
2. Change to citygraph.jar directory
3. 'java -jar citygraph.jar -h' to display command line parameters

## Usage

The application can output 3 .csv files
* Locations.csv
* Straight.csv
* Routes.csv

The format for these 3 files are as follows:

**Routes**

|From City|To City|Driving Distance|Walking Distance|
|:-|:-|:-|:-|
|*name*|*name*|*kilometers*|*kilometers*|

**Locations**

|name|region|country|lat|lon|location_type|conflict_date|population|
|:-|:-|:-|:-|:-|:-|:-|:-|

**Straight**

|From City|To City|Straight Line Distance|
|:-|:-|:-|
|*name*|*name*|*kilometers*|


### Arguments

**API_KEY** is needed to access the [OpenRouteService](https://openrouteservice.org/plans/) API

**COUNTRY_CODE** is the 2 letter code for each country (currently there are 235 countries included)

**POPULATION_LIMIT** will set the limit to remove cities below this population, unless the population data is not available, then it will be included. 

**CITIES_LIMIT** will set the maximum number of cities. The cities will be removed after population has been removed and will be removed from lowest population first until the limit has been reached.

### Commands

| Command | Arguments                                             | Description  |
| :-----: |:---------:                                             | :---------   |
|-a       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv, Routes.csv, and StraightLine.csv with Walk, Drive and StraightLine distance metrics|
|-d       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv and Routes.csv with Drive distance metric|
|-w       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv and Routes.csv with Walk distance metric|
|-s       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports StraightLine.csv with StraightLine metric|

'-h' for help

## Built With

* [SimpleMaps](https://simplemaps.com/) - Provides country/city data (lat, lng, population)
* [OpenRouteService](http://openrouteservice.org/) - Provides API for distance metrics for driving & walking

## Authors

**Qusai Zakir** - [Portfolio](https://qusaizakir.uk)

## License

This project is licensed under the BSD 3-Clause license.
