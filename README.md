# ServiceMIx_ESB
Apache service mix -Java
Steps

1.Install service mix and run servicemix.bat in command prompt
           
2.Listing the existing bundles using bundle:list command
        
3.Listing camel related bundles 
 
4.Displaying the logs by log:display
 
5 Set the log level as DEBUG and displaying DEBUG level logs 
 
6.Set the log level back to  INFO
 
7.Display the logs of exceptions
 
8.Create a blueprint file called testfile.xml and put into deploy folder of servicmix.This will create camel/input folder inside servicemix directory
 
9.Copy the file from deploy folder to camel/input folder. The file will move automatically to output folder 
 
10.The log:display command shows that testfile.xml has moved to output directory
 
11.bundle:list command displays the file testfile.xml is converted to bundle .The bundle can be started and stopped by bundle id
 
12.Create a testfile2.xml and create camel route for moving files from activemq/input to activemq/output.Put the file in deploy folder and activemq/input.Now the files will move to activemq/output
 
13.log:display displays the file testfile2.xml has moved to output folder with timestamp
 
14.bundle:list command displays the list of bundles with active,resolved state 

15.feature:list | grep camel displays the features list related to camel 
 
16.Install the web console and check whether it has been installed using feature:list|grep webconsole
 
17.Log into karaf webconsole by opening localhost:8181 and view the list of bundles

18.Install apachemq and start the activemq by navigating to bin folder in command prompt . The apachemq will start on localhost port 61616

19.Create a maven project in eclipse and write a multiple producer consumer java messaging service application. Running the application displays the produced HelloWorld threads getting consumed by consumers 

20.Create a jar file of the project by running mvn clean install and drop the jar into deploy folder .This will install the file as bundle 
 
21.Now login into activemq and navigate to queues to find TEST.FOO queue created
 
22.Click TEST.FOO and hello world threads are displayed
 
23.Displaying the Helloworld threads on karaf console with details
 




