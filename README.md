# Frontend Automation

The tool will automatically perform the deep check and test all functionalities of the website. This includes, for example: checking URLs, comparing the price of the first item to the last etc.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software

```
Installed JDK8
IDE (e.g.Intellij IDEA )
```

### Installing

A step by step series of examples that tell you how to get a development env running

```
Open your IDE (e.g. Intellij IDEA) and select "checkout from version control"
copy/paste the github repo url and press "clone"
```

wait until all files are cloned and the idea is finished with building the project and indexing the files (in case you get the question 'open project?' please select yes)

```
once everything is finished, open the 'Main' file under the directory src/main/java/crisscrosscrass/Main.java
run the Main.java 
```
if you get an Error Message like "Exception in thread "JavaFX Application Thread"
this can happen when not all dependencies from the Maven build are loaded into your Project Folder
(yes i could put them manually into that project, but you want to be up to date right? ;) )
-> just go to the pom.xml file and copy paste this the code below there and save (this should trigger your IDE to import the Maven Changes)
so please select "import maven changes" when this is triggert
<details><summary>show me pom xml code</summary>
<p>

#### yes, it is alot :)

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>groupId</groupId>
    <artifactId>FrontEndCheck</artifactId>
    <version>1.0-SNAPSHOT</version>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.2.1</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>java</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <mainClass>crisscrosscrass.Main</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>crisscrosscrass.Main</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <finalName>FrontEndCheck</finalName>
                    <appendAssemblyId>false</appendAssemblyId>
                </configuration>
            </plugin>

        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.14.0</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>11-ea+19</version>
        </dependency>
        <dependency>
            <groupId>com.jfoenix</groupId>
            <artifactId>jfoenix</artifactId>
            <version>9.0.4</version>
        </dependency>

        <dependency>
            <groupId>com.jfoenix</groupId>
            <artifactId>jfoenix</artifactId>
            <version>8.0.4</version>
        </dependency>
        <dependency>
            <groupId>de.jensd</groupId>
            <artifactId>fontawesomefx</artifactId>
            <version>8.1</version>
        </dependency>
        <dependency>
            <groupId>de.jensd</groupId>
            <artifactId>fontawesomefx</artifactId>
            <version>8.9</version>
        </dependency>
        <dependency>
            <groupId>de.jensd</groupId>
            <artifactId>fontawesomefx-commons</artifactId>
            <version>9.1.2</version>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>org.controlsfx</groupId>
            <artifactId>controlsfx</artifactId>
            <version>9.0.0</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>

    </dependencies>


</project>

```

</p>
</details>




## Built With

* [Java](http://www.dropwizard.io/1.0.2/docs/) - The core Code is written
* [Maven](https://maven.apache.org/) - Dependency Management
* [JavaFX](https://openjfx.io/) - Used to generate UserInterface
* [Selenium Webdriver](https://www.seleniumhq.org/projects/webdriver/) - the WebDriver API is driving a browser natively as a user would either locally or on a remote machine using the Selenium Server it marks a leap forward in terms of browser automation.

## Authors

* **Christopher Eckardt** - *Initial work* - [crisscrosscrass](https://github.com/crisscrosscrass)
* **Tamiris Diversi** - *Bug Testing / Test Writing* - [DataCollectingTami](https://github.com/DataCollectingTami)

See also the list of [contributors](https://github.com/crisscrosscrass/frontendcheckerqm/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
