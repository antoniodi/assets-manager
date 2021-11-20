# assets-manager
App in scala with ZIO and play to manage your own assets

### To run the application in local environment:
sbt run

### To generate the uber jar, run from the terminal:
sbt assembly

### To run the generated jar with custom application config 
java "-Dconfig.resource=application.conf" -jar ms-assets-manager.jar

### To run the generated jar with custom port and ip address
java "-Dconfig.resource=application.conf" "-Dhttp.port=9001" "-Dhttp.address=127.0.0.1" -jar ms-assets-manager.jar

To find more information about the configuration go to:
https://www.playframework.com/documentation/2.5.x/ProductionConfiguration#Specifying-an-alternate-configuration-file