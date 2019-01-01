# Systemd Maven Plugin

## Overview

This plugin for [Apache Maven](https://maven.apache.org/) can be used to generate files needed for running a Java 
application (preferrably a Spring Boot Service) as a [Systemd Unit](https://www.freedesktop.org/software/systemd/man/systemd.service.html).

|File|Purpose|
|---|---|
|`<service>.sh` | Bash script to manage - e.g. start, stop, install - a unit/service. |
|`<service>.service` | Systemd Service Unit configuration. | 
|`environment.cfg`| Environment variables used to run the unit. | 
|`<service>.zip`| Bundled maven artifact and above files. | 
|`install.sh` | Bash script to install the files, *not* the service. |

Source code can be found at the [projects home](https://github.com/mamu7211/systemd-maven-plugin).

### Limitations

1. Currently this plugin has a big flaw, it does not create a user for it's service in the default install script, nor
does it change the file permissions of it's default working/install directory at `/var/<project.artifactId>/<project.version>`.
2. Only one JAR Artifact will be added to the created zip file, this is due to the fact that this plugin is primarily used 
by the Author for [Spring Boot](https://spring.io/projects/spring-boot) projects.
3. Empty `environment.cfg` files will be created, the project currently contains code to fetch unassigned/unreferenced
properties in `application.properties` of the project using this plugin, but does not evaluate or write them into 
the generated `environment.cfg`` . 
4. Not well integrated into the build process and not tested with [Maven Modules](https://maven.apache.org/guides/mini/guide-multiple-modules.html).
5. Documentation not detailed enough.

## Usage

```xml
    ...
    <build>
        ...
        <plugins>
            ...
            <plugin>
                <groupId>io.murrer</groupId>
                <artifactId>systemd-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <unit>
                        <!-- systemd unit directives -->
                    </unit>
                    <run>
                        <!-- service configuration -->
                    </run>
                    <install>
                        <!-- install configuration -->
                    </install>
                    <environment>
                        <!-- environment.cfg file configuration -->
                    </environment>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>bundle</goal>
                        </goals>
                        <phase>package</phase>
                    </execution>
                </executions>
            </plugin>
            ...
        </plugins>
    </build>
```

See [Wiki](https://github.com/mamu7211/systemd-maven-plugin/wiki/Usage) for examples.

### Properties

See [Wiki](https://github.com/mamu7211/systemd-maven-plugin/wiki/Properties) for examples.

### Files

#### Unit File

#### Serivce File

#### Environments File

#### Install File

## Templating

See [Wiki](https://github.com/mamu7211/systemd-maven-plugin/wiki/Templating) for more information.

### Unit File

### Serivce File

### Environments File

### Install File

## Project

### Roadmap

Theres a lot to do, e.g. documentation, making this project available in the central repo, enhance and fix 
environment file generation, but currently this project has no clear direction.

### Contribute

If you wish to contribute, fork this project from https://github.com/mamu7211/systemd-maven-plugin and create a 
pull request. Currently no coding guidlines are present, feel free to produce clean code.

### Issues

Any issues can be reported to the [projects home](https://github.com/mamu7211/systemd-maven-plugin/issues)
directly.

### License and Apache Maven Trademark

This Project is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0), 
see [LICENSE](./LICENSE) for the complete license of this project. **This project is not an official maven plugin**
nor is it affiliated or connected to [Apache Maven](https://maven.apache.org/) in any way other than it uses 
[Apache Mavens](https://maven.apache.org/) plugin capabilities and libraries. **Any issues regarding this plugin should
be reported to the [projects home](https://github.com/mamu7211/systemd-maven-plugin/issues) directly**.

