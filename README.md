## Build

```
$ # From the root dir
$ mvn package
```

## Run

```
$ # From the root dir
$ java -javaagent:timetravel-agent-mallesh/target/timetravel-agent-mallesh-0.1-SNAPSHOT.jar=<<argDaystoGoBack>> -jar other/target/other-0.1-SNAPSHOT.jar
```