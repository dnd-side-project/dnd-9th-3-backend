package com.dnd.gooding.util;

import static com.dnd.gooding.common.constants.Constant.*;
import static java.util.Objects.isNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LocalTimeUtil {

    private LocalTimeUtil() {
        throw new IllegalStateException("Util class.");
    }

    /**
     * {@link LocalTime} 변환
     *
     * @param source 원천문자열
     * @return "HH:mm:ss" 형식으로 변환된 {@link LocalTime}
     */
    public static LocalTime toLocalTime(String source) {
        return toLocalTime(source, FORMAT_LOCAL_TIME);
    }

    /**
     * {@link LocalTime} 변환
     *
     * @param source 원천문자열
     * @param timeFormat 변환시간형식
     * @return null or "HH:mm:ss" 형식으로 변환된 {@link LocalTime}
     */
    public static LocalTime toLocalTime(String source, String timeFormat) {
        if (isNull(source) || source.isEmpty()) {
            return null;
        }

        return LocalTime.parse(source, DateTimeFormatter.ofPattern(timeFormat));
    }

    /**
     * 문자열 변환
     *
     * @param source 원천
     * @return "HH:mm:ss" 형식으로 변환된 문자열
     */
    public static String toString(LocalTime source) {
        return toString(source, FORMAT_LOCAL_TIME);
    }

    /**
     * 문자열 변환
     *
     * @param source 원천
     * @param timeFormat 변환시간형식
     * @return '변환시간형식'으로 변환된 문자열 or null
     */
    public static String toString(LocalTime source, String timeFormat) {
        if (Objects.isNull(source)) {
            return null;
        }
        return source.format(DateTimeFormatter.ofPattern(timeFormat));
    }
}
