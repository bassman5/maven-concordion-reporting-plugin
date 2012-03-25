Maven Concordion Site Report
============================

This is a simple maven reporting plugin that takes a directory structure with an html structure,
in this case Concordion reports, and add them to a maven site report.

Works with maven 2.2.1 and 3.0
------------------------------

*This project contains an example Concordion report with a navigable index from the mvn site:site*

In the pom.xml for the project that contains Concordion reports you will have an entry like this
------------------------------------------------------------------------------------------------

    <build>
        <plugins>
    ......
            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven-surefire-plugin.version}</version>
                <configuration>
                    <systemPropertyVariables>
                        <property>
                            <name>concordion.output.dir</name>
                            <value>target/concordion</value>
                        </property>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
    ......
        </plugins>
    </build>

This will create the Concordion files in target/concordion

Then just add the following in the reporting section
----------------------------------------------------
### Maven 2

    <reporting>
        <plugins>
            <plugin>
                <groupId>com.github.bassman5</groupId>
                <artifactId>maven-concordion-reporting-plugin</artifactId>
                <version>1.0.2</version>
                <configuration>
                    <concordionDir>${basedir}/target/concordion</concordionDir>
                    <concordionIndexFile>acceptanceTests/AcceptanceTests.html</concordionIndexFile>
                </configuration>
            </plugin>

        </plugins>
    </reporting>

### Maven 3

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-site-plugin</artifactId>
                <version>3.0</version>
                <configuration>
                <reportPlugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-project-info-reports-plugin</artifactId>
                        <version>2.2</version>
                        <configuration>
                            <dependencyDetailsEnabled>false</dependencyDetailsEnabled>
                            <dependencyLocationsEnabled>false</dependencyLocationsEnabled>
                        </configuration>
                    </plugin>
                    <plugin>
                        <groupId>com.github.bassman5</groupId>
                        <artifactId>maven-concordion-reporting-plugin</artifactId>
                        <version>1.0.2</version>
                        <configuration>
                            <concordionDir>${basedir}/target/concordion</concordionDir>
                            <concordionIndexFile>acceptanceTests/AcceptanceTests.html</concordionIndexFile>
                        </configuration>
                    </plugin>
                </reportPlugins>
                </configuration>
            </plugin>

        </plugins>



You can put the reporting section in the module that contains the Concordion report or in it's parent pom.
If the Concordion report directory or index files cannot be found, the report will be skipped, and an info log message tells you the report is skipped.

* If you don't see your expected report look for the line starting Skipped "Concordion-Report " report.

From release 1.0.3 this project includes a maven 3 example with a navigable set of acceptance tests that use concordion:run="concordion" commands to execute lists of tests

Configuration
-------------

    <concordionDir>${basedir}/target/concordion</concordionDir>
The directory defined in the build section, where the Concordion files can be found.

    <concordionIndexFile>acceptanceTests/AcceptanceTests.html</concordionIndexFile>
The top level html index file which will be the link you will be taken to from the Project Reports->Concordion menu


Other Uses
==========
You can use this plugin to copy any html content into a maven site report.
Just modify the menu name and change the source and output dirs.


Mick Dudley



Acknowledgments
===============

The maven-jxr-plugin has good examples of how this was done, could not have done this without seeing this code.
http://maven.apache.org/plugins/maven-jxr-plugin/

Links
=====

Concordion Site:

* http://concordion.org/

Apache Maven:

* http://maven.apache.org/

Maven Site Plugin:

* http://maven.apache.org/plugins/maven-site-plugin/


