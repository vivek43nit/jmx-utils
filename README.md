# jmx-utils [![Clojars Project](https://img.shields.io/clojars/v/com.geek-vivek.jmx/jmx-utils.svg)](https://clojars.org/com.geek-vivek.jmx/jmx-utils)

This library will help you in getting all the registered metrics in the JMX MbeanServer.

If you are looking for getting all the metrics from JMX MbeanServer and do some stuffs with that,
like writing a metric reporter, then this is the library you are looking for.

### Maven Dependency
Use the following repository
```xml
<repository>
    <id>clojars</id>
    <name>Clojars repository</name>
    <url>https://clojars.org/repo</url>
</repository>
```

Use the following maven dependency
```xml
<dependency>
    <groupId>com.geek-vivek.jmx</groupId>
    <artifactId>jmx-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### How to Use ?
First you need to create a class by implementing JmxMetricsListener interface.
```java
    import com.geekvivek.jmx.utils.JmxMetric;
    import com.geekvivek.jmx.utils.interfaces.JmxMetricsListener;

    class MetricReporter1 implements JmxMetricsListener {
        @Override
        public void metricChange(JmxMetric metric){ /*do stuffs on receiving new metrics*/ }
        @Override
        public void metricRemoval(JmxMetric metric){ /* do stuffs when a metrics is removed from JMX */}
        @Override
        public void close(){ /* do stuffs when application is closing */}
    }
```

Now create an instance of MetricReporter1 and pass it to JmxMetricProviderService
and start the JmxMetricProviderService

```java
    import com.geekvivek.jmx.utils.service.JmxMetricProviderService;
    ...
    new JmxMetricProviderService(Collections.singletonList(
        new MetricReporter1()
    )).start(10, 60, TimeUnit.SECONDS);
```

Note :
Check the javadoc of classes for more info.
 
