# City Graph

City graph takes location data (cities) and creates a fully connected graph using different distance metrics as edges. These metrics include as driving, walking and straight-line distances. The country data is taken from GeoNames, OpenRouteService is used to calculate distances.

### Prerequisites

* Internet connection is needed for API access
* Java JRE or openJDK version 8 or later
* API key from [OpenRouteService](https://openrouteservice.org/plans/) (free)

### Installing

#### Linux
```sudo apt-get install openjdk-11-jdk``` to install openjdk-11

#### Windows
Download Java JRE from [Oracle](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

1. [Download](https://github.com/qusaizakir/CityGraph/releases) and extract .zip (both citygraph.jar and worldcities.csv must be in the same directory) 
2. ```java -version``` to verify java is installed
3. Change to citygraph.jar directory
4. ```java -jar citygraph.jar -h``` to display command line parameters

## Usage
The application uses the worldcities.csv file to find cities from a particular country based off its country code. The 235 country codes that can be used are in [**this file**](https://github.com/qusaizakir/CityGraph/blob/master/listofcountrycodes.csv)

There is also a python script included in the release folder that uses Overpass API (OpenStreetMaps) to find cities either by [**country code**](https://github.com/qusaizakir/CityGraph/blob/master/listofcountrycodes.csv) or by a bounding box defined using GPS coordinates. The file will output a cities.csv that can be used either in conjunction with the offline worldcities.csv database or on its own.

The application can output 3 .csv files
* Locations.csv
* Straight.csv
* Routes.csv

The format for these 3 files are as follows (where cc is countrycode):

**cc_routes.csv** 

|From City|To City|Driving Distance|Walking Distance|
|:-|:-|:-|:-|
|*string*|*string*|*kilometers*|*kilometers*|

**cc_locations.csv**

|name|region|country|lat|lon|location_type|conflict_date|population|
|:-|:-|:-|:-|:-|:-|:-|:-|
|*string*|*string*|*string*|*double*|*double*|*string*|*string*|*integer*|

**cc_straight.csv**

|From City|To City|Straight Line Distance|
|:-|:-|:-|
|*string*|*string*|*kilometers*|


### Arguments

**API_KEY** is needed to access the [OpenRouteService](https://openrouteservice.org/plans/) API

[**COUNTRY_CODE**](https://github.com/qusaizakir/CityGraph/blob/master/listofcountrycodes.csv) is the 2 letter code for each country.

**POPULATION_LIMIT** will set the limit to remove cities below this population, unless the population data is not available, then it will be included. 

**CITIES_LIMIT** will set the maximum number of cities. The cities will be removed after population has been removed and will be removed from lowest population first until the limit has been reached.

**Please note:** The maximum number of cities in one request is limited to 50 cities. The API request will fail otherwise due to limits from OpenRouteService. (2500 locations, 50x50) 

### Commands

| Command | Arguments                                             | Description  |
| :-----: |:---------:                                             | :---------   |
|-a       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv, Routes.csv, and StraightLine.csv with Walk, Drive and StraightLine distance metrics|
|-al      |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv, Routes.csv, and StraightLine.csv with Walk, Drive and StraightLine distance metrics using ONLY cities.csv file as input|
|-d       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv and Routes.csv with Drive distance metric|
|-w       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv and Routes.csv with Walk distance metric|
|-s       |*API_KEY COUNTRY_CODE POPULATION_LIMIT CITIES_LIMIT*   | Exports Locations.csv and StraightLine.csv with StraightLine metric|

'-h' for help

**cities.csv**

Additonal cities can also be included to override the city data from the worldcities.csv. This data must be included in the same location as the .JAR file and be named "cities.csv" only. 

The cities with the same name (or within 10km straight line by lat/lon) will be counted as duplicates and removed. The cities from "cities.csv" will take priority.

The format for "locations.csv" is:

|name|country|lat|lon|location_type|conflict_date|population|
|:-|:-|:-|:-|:-|:-|:-|
|*string*|*string*|*double*|*double*|*string*|*string*|*integer*|

'name', 'lat' & 'lon' are the only mandatory fields.


**_Example_** 

``` java -jar citygraph.jar -a API_KEY us 5000 25 ```

This would output Locations.csv, Routes.csv and StraightLine.csv for the top 25 cities from the United states with at least 5000 population.

## Upcoming features

* Option to prune graph (remove edges)
* More control over specific filters for cities

## Built With

* [GeoNames](geonames.org) - Provides offline csv of country/name data (lat, lon, population)
* [OpenRouteService](http://openrouteservice.org/) - Provides API for distance metrics for driving & walking
* [Overpass API](http://overpass-api.de/) - Provides API to access city data (lat, lon, population)

## Authors

**Qusai Zakir** - [Portfolio](https://qusaizakir.uk)
**Christoph Schweimer** - Overpass API python script
**Elke Schlager** - Overpass API python script

## License

This project is licensed under the BSD 3-Clause license.
