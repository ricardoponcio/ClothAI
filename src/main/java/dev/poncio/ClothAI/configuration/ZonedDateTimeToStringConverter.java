package dev.poncio.ClothAI.configuration;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {
    @Override
    public String convert(MappingContext<ZonedDateTime, String> context) {
        if (context.getSource() == null) return null;
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(context.getSource());
    }
}