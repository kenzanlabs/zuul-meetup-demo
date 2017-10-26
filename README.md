# Zuul Meetup Demos

## Getting setup
* Clone this repository
* install npm https://nodejs.org/en/download/package-manager/
* install sdkman http://sdkman.io/install.html
* https://medium.com/@czerwinb/how-to-install-a-specific-gradle-version-on-your-mac-beab35051ee8
* We want version 3.5.1

## Demo 1
Start Zuul 
* `cd zuul-simple-webapp`
* `gradle jettyRun`
* Browse to http://localhost:8080/

Making Our Filters (We will just replace the files currently existing and I will explain)
* Add routeConfig.json to the resources directory
* In src/main/java add FilterConstants to util
* In src/main/java add RouteConfig.java to model
* Replace the PreDecorationFilter in src/main/groovy/filters/pre with RouteLookup from Demo-Files
* Replace SimpleHostRoutingFilter in src/main/groovy/filters/route with SimpleHostRoutingFilter from Demo-Files
* `cd basic-service-a`
* `npm install`
* `npm start`
* `cd basic-service-b`
* `npm install`
* `npm start`
* Start Zuul
* Browse to http://localhost:8080/A
* Browse to http://localhost:8080/B

## Demo 2

* Keep zuul running
* `curl -v localhost:8080/B -H 'X-BAD_HEADER: header'`
* Add the HeaderBlacklist.groovy file to pre filters
* `curl -v localhost:8080/B -H 'X-BAD_HEADER: header'`
* Stop Zuul

## Demo 3
* update the routeConfig in resources to match routeConfig2.json
* Add DynamicRouting pre filter
* start zuul
* Browse to http://localhost:8080/A
* Browse to http://localhost:8080/AV2
* add "newPath": "/AV2" to routeConfig for A
* change port to "8092" 
* restart zuul
* Browse to http://localhost:8080/A
* Stop Zuul

## Demo 4
* update the routeConfig in resources to match routeConfig3.json
* Add CanaryRelease pre filter
* Start Zuul
* `curl -v localhost:8080/B -H 'X-LOCATION: West'`
* `curl -v localhost:8080/B -H 'X-LOCATION: East'`


 
