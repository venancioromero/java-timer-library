# venoty.timer

## Description

Micro Library that allows temporize your java methods with different thresholds. 
Furthermore, you can write results on a file in CSV format. 

## Installation

Add repository
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

```
Add dependency.
```xml
	<dependency>
	    <groupId>com.github.venancioromero</groupId>
	    <artifactId>java-timer-library</artifactId>
	    <version>0.1.0-RELEASE</version>
	</dependency>

```

## Documentation

Annotate your methods with **@timer** annotation and enjoy!!

## application.properties

timer.file.enable        : property that activate property of write into file.
timer.file.name          : Name of file. 
timer.file.csv.separator : Separator of fields.   

## Default Values

### Thresholds

By default the library have set two thresholds that are used for choice log level of trace.

```
LOG LEVEL --->  INFO         WARN          ERROR

TIME(ms) ---->   0     >      500     >    1000
```
### File

timer.file.enable        : false
timer.file.name          : timer.log
timer.file.csv.separator : ;

## Optional

You can set another thresholds, remember that are miliseconds and modify default values,

```java

public class MyClass {

    @Timer(warn = 1000,error=4000)
    public String hiWorld() {
        ·····
        ·····
    }
}

```