# LogManager-CreditSuisse

Steps to run:

1- Goto Logmanager folder where pom.xml is located.
run mvn clean install
It wil generate a jar file in target/LogManager folder

2- Download hsqldb.jar from https://sourceforge.net/projects/hsqldb/files/hsqldb/hsqldb_2_5/
   Extract and copy the hsqldb-2.5.1.jar to the LogManager folder where the runHsqlDB.sh file located.
run ./runHsqlDB.sh
It will run the database

3- Copy logfile.txt to target/LogManager folder.

4- goto target/LogManager folder and run the below command
java -jar LogManager.jar logfile.txt

Then you will see the result
