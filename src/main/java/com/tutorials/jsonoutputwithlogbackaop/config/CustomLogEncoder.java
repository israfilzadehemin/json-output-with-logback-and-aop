package com.tutorials.jsonoutputwithlogbackaop.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorials.jsonoutputwithlogbackaop.model.LogOutput;
import io.opentelemetry.api.trace.Span;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomLogEncoder extends LayoutWrappingEncoder<ILoggingEvent> {
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public byte[] encode(ILoggingEvent event) {
        try {

            var spanContext = Span.current().getSpanContext();

            var logOutput = new LogOutput(
                    event.getInstant(), event.getLoggerName(), event.getThreadName(),
                    spanContext.getTraceId(), spanContext.getSpanId(),
                    event.getLevel(), event.getMessage(), event.getArgumentArray());

            return "%s%s" .formatted(objectMapper.writeValueAsString(logOutput), "\n").getBytes();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }


}
