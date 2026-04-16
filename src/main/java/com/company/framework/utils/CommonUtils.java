package com.company.framework.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CommonUtils {
    private CommonUtils() {
    }

    public static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }
}
