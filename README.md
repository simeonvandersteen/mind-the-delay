## Mind The Delay
Find delayed journeys in your travel history eligible for a service delay refund.

### Getting started

- Make sure you've got Java 8 installed
- Compile using `./gradlew installDist` (this will install Gradle if you don't already have it)
- Run using `./build/install/mind-the-delay/bin/mind-the-delay`

For command line options use `./build/install/mind-the-delay/bin/mind-the-delay --help`

For more build options see `./gradlew tasks`

### How it works

The application uses your travel history, which you can download in CSV form from the "Journey History" section 
in your TfL account (your Oyster card needs to be registered for this to work).

The application takes into the journeys in your history and then checks which ones are at least 15 minutes late 
compared to the fastest journey in your history.

If you know that a certain journey can be done faster than you've been able to in your travel history,
you can provide a JSON file with journey times (in minutes) which well then be used instead. Use `-c some-file.json` as
command line option with a file that looks like this:

```
[
  {
    "origin": "Oxford Circus",
    "destination": "White City",
    "journey_time": 15
  },
  {
    ...
  }
]
```

### Known issues

_I get `Exception in thread "main" javax.net.ssl.SSLHandshakeException: ...` when running Gradle_

This is a problem with OpenJDK and Gradle. Either upgrade OpenJDK or use Oracle JDK.