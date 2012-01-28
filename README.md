Maven Concordion Site Report
============================

This is a simple maven reporting plugin that takes a directory structure with an html structure,
in this case Concordion reports, and add them to a maven site report.

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

You can put the reporting section in the module that contains the Concordion report or in it's parent pom.
If the Concordion report directory or index files cannot be found, the report will be skipped, and an info log message tells you the report is skipped.

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


