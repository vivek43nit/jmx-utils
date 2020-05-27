# jmx-utils

This library will help you in getting all the registered metrics in the JMX MbeanServer.

If you are looking for getting all the metrics from JMX MbeanServer and do some stuffs with that,
like writing a metric reporter, then this is the library you are looking for.

###maven import
````
<dependency>
    <groupId>com.geek-vivek.jmx</groupId>
    <artifactId>jmx-utils</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
````
### How to Use ?

First you need to create a class by implementing JmxMetricsListener interface.
````
    class MetricReporter1 implements JmxMetricsListener {
        ...
    }
````

Now create an instance of MetricReporter1 and pass it to JmxMetricProviderService
and start the JmxMetricProviderService

````
    new JmxMetricProviderService(Collections.singletonList(
        new MetricReporter1()
    )).start(10, 60, TimeUnit.SECONDS);
````

Note :
Check the javadoc of classes for more info.
 
