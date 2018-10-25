# venoty.timer

## Description

Micro Library that allows temporize your java methods with different threshold. 

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

Annote your methods with **@timer** annotation and enjoy!!

## Default Values

By default the library have setted two thresholds that are used for choice log level of trace.

```
LOG LEVEL --->         INFO             WARN                  ERROR

TIME(ms)  --->   0 ------------ 500 ------------ 1000 ----------------------
```

## Optional

You can set another thresholds, remember that are in microseconds.

```java

public class MyClass {

    @Timer(warn = 1000,error=4000)
    public String hiWorld() {
        ·····
        ·····
    }
}

```
