package com.allocation.manager.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FormatDataUtil {

    public static String formatInstantDateTime(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
