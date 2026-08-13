package com.lavalliere.danielspring.spring7openntelemetry.component;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/*
 * Not currently working because unable to load resources
 * Exception in thread "BatchLogRecordProcessor_WorkerThread-1" java.lang.NoClassDefFoundError: io/opentelemetry/api/incubator/common/ExtendedAttributes
	at io.opentelemetry.sdk.logs.ExtendedSdkReadWriteLogRecord.getImmutableExtendedAttributes(ExtendedSdkReadWriteLogRecord.java:117)
	at io.opentelemetry.sdk.logs.ExtendedSdkReadWriteLogRecord.toLogRecordData(ExtendedSdkReadWriteLogRecord.java:136)
	at io.opentelemetry.sdk.logs.ExtendedSdkReadWriteLogRecord.toLogRecordData(ExtendedSdkReadWriteLogRecord.java:24)
	at io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor$Worker.run(BatchLogRecordProcessor.java:207)
	at java.base/java.lang.Thread.run(Thread.java:1474)
Caused by: java.lang.ClassNotFoundException: io.opentelemetry.api.incubator.common.ExtendedAttributes
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:580)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:490)
	... 5 more
 */
@RequiredArgsConstructor
// @Component
public class InstallOpenTelemetryAppender implements InitializingBean {
    private final OpenTelemetry openTelemetry;

    @Override
    public void afterPropertiesSet() throws Exception {
        OpenTelemetryAppender.install(this.openTelemetry);
    }
}
