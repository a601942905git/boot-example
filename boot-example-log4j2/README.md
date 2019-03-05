# 为什么要集成Log4j2
Log4j2的实现使用了Disruptor高性能异步队列，从而通过异步来提升系统性能

# Log4j2配置
```
<?xml version="1.0" encoding="UTF-8"?>
<!-- Configuration后面的status，这个用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，
     你会看到log4j2内部各种详细输出。可以设置成OFF(关闭) 或 Error(只输出错误信息)。
     30s 刷新此配置
-->
<configuration status="WARN" monitorInterval="30">

    <!-- 日志文件目录、压缩文件目录、日志格式配置 {%F:%L}输出行号-->
    <properties>
        <Property name="fileName">${sys:user.home}/logs/boot-example</Property>
        <Property name="fileGz">${sys:user.home}/logs/boot-example/gz</Property>
        <Property name="PID">????</Property>
        <Property name="COLOR_LOG_PATTERN">%clr{%d{yyyy-MM-dd HH:mm:ss.SSS}}{faint} %clr{%5p} %clr{${sys:PID}}{magenta} %clr{---}%location%clr{---}{faint} %clr{[%15.15t]}{faint} %clr{%-40.40c{1.}}{cyan} %clr{:}{faint} %m%n%xwEx</Property>
        <Property name="LOG_PATTERN">%d{yyyy-MM-dd HH:mm:ss.SSS} %5p ${sys:PID} --- [%15.15t] %-40.40c{1.} : %m%n%xwEx</Property>
    </properties>

    <Appenders>
        <!-- 输出控制台日志的配置 -->
        <Console name="console" target="SYSTEM_OUT">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="debug" onMatch="ACCEPT" onMismatch="DENY"/>
            <!-- 输出日志的格式 -->
            <PatternLayout pattern="${COLOR_LOG_PATTERN}"/>
        </Console>

        <!-- 打印出所有的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档 -->
        <!-- immediateFlush代表不立马把日志写入磁盘，批量写入，有利于提升性能 -->
        <RollingRandomAccessFile name="infoFile" fileName="${fileName}/web-info.log" immediateFlush="false"
                                 filePattern="${fileGz}/$${date:yyyy-MM}/%d{yyyy-MM-dd}-%i.web-info.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>

            <Policies>
                <SizeBasedTriggeringPolicy size="20 MB"/>
            </Policies>

            <Filters>
                <!-- 只记录info和warn级别信息 -->
                <ThresholdFilter level="error" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>

            <!-- 指定每天的最大压缩包个数，默认7个，超过了会覆盖之前的 -->
            <DefaultRolloverStrategy max="50"/>
        </RollingRandomAccessFile>

        <!-- 存储所有error信息 -->
        <RollingRandomAccessFile name="errorFile" fileName="${fileName}/web-error.log" immediateFlush="false"
                                 filePattern="${fileGz}/$${date:yyyy-MM}/%d{yyyy-MM-dd}-%i.web-error.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>

            <Policies>
                <SizeBasedTriggeringPolicy size="50 MB"/>
            </Policies>

            <Filters>
                <!-- 只记录error级别信息 -->
                <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>

            <!-- 指定每天的最大压缩包个数，默认7个，超过了会覆盖之前的 -->
            <DefaultRolloverStrategy max="50"/>
        </RollingRandomAccessFile>
    </Appenders>

    <Loggers>
        <!-- 全异步 -->
        <AsyncRoot level="info" includeLocation="true">
            <AppenderRef ref="console"/>
            <AppenderRef ref="infoFile"/>
            <AppenderRef ref="errorFile"/>
        </AsyncRoot>
    </Loggers>

</configuration>
```

# 输出效果
```
2019-03-05 10:47:44.943  INFO 46175 ---com.boot.example.Log4j2Scheduled.logging(Log4j2Scheduled.java:29)--- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 10:47
2019-03-05 10:47:44.943  WARN 46175 ---com.boot.example.Log4j2Scheduled.logging(Log4j2Scheduled.java:31)--- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 10:47:44.943 ERROR 46175 ---com.boot.example.Log4j2Scheduled.logging(Log4j2Scheduled.java:32)--- [   scheduling-1] c.b.e.Log4j2Scheduled 
```

# 参数详解
immediateFlush：表示不立马写入磁盘，而是先写入缓存，然后再批量写入磁盘

%location：用来输出打印日志所在的位置，如上输出效果，打印出Log4j2Scheduled.java:31，该参数需要配合includeLocation="true"来使用，当然如果不需要输出详细的位置，可以在格式中去掉%location

# 异步、同步混合使用配置
```
<Loggers>
    <!-- 全异步 -->
    <Root level="info">
        <AppenderRef ref="console"/>
        <AppenderRef ref="infoFile"/>
        <AppenderRef ref="errorFile"/>
    </Root>

    <AsyncLogger name="com.boot.example" level="trace">
        <AppenderRef ref="console"/>
        <AppenderRef ref="infoFile"/>
        <AppenderRef ref="errorFile"/>
    </AsyncLogger>
</Loggers>
```

# 异步、同步输出如下
```
2019-03-05 11:14:18.120  INFO 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 11:14:18
2019-03-05 11:14:18.120  INFO 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 11:14:18
2019-03-05 11:14:18.120 DEBUG 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------DEBUG---------
2019-03-05 11:14:18.121  WARN 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 11:14:18.120 DEBUG 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------DEBUG---------
2019-03-05 11:14:18.121  WARN 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 11:14:18.121 ERROR 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 1551755658120
2019-03-05 11:14:18.121 ERROR 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 1551755658120
2019-03-05 11:14:20.122  INFO 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 11:14:20
2019-03-05 11:14:20.124 DEBUG 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------DEBUG---------
2019-03-05 11:14:20.122  INFO 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 11:14:20
2019-03-05 11:14:20.124 DEBUG 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------DEBUG---------
2019-03-05 11:14:20.125  WARN 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 11:14:20.126 ERROR 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 1551755660121
2019-03-05 11:14:20.125  WARN 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 11:14:20.126 ERROR 46441 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 1551755660121
```
从如上的输出我们可以看出，每条日志都打印了两遍，显然这是没必要的，而且多余的日志会导致磁盘IO，会有一定的性能影响，那么如何避免日志打印两遍呢？

# 解决重复打印
```
<AsyncLogger name="com.boot.example" level="trace" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="infoFile"/>
    <AppenderRef ref="errorFile"/>
</AsyncLogger>
```
设置additivity="false"，让其不再向root进行传递

# 解决后的输出效果
```
2019-03-05 11:29:30.556  INFO 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 11:29:30
2019-03-05 11:29:30.556 DEBUG 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------DEBUG---------
2019-03-05 11:29:30.556  WARN 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 11:29:30.556 ERROR 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 1551756570556
2019-03-05 11:29:32.551  INFO 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 2019-03-05 11:29:32
2019-03-05 11:29:32.551 DEBUG 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------DEBUG---------
2019-03-05 11:29:32.551  WARN 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : -------WARN---------
2019-03-05 11:29:32.551 ERROR 46529 --- [   scheduling-1] c.b.e.Log4j2Scheduled                    : 1551756572551
```

# 参考文献
- [Log4j2简介和异步日志梳理](https://www.jianshu.com/p/9f0c67facbe2)
- [异步、同步混合使用](https://logging.apache.org/log4j/2.x/manual/async.html#MixedSync-Async)