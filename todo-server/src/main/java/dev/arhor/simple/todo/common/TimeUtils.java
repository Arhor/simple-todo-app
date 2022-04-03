package dev.arhor.simple.todo.common;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class TimeUtils {

    public static final int MILLIS_IN_SECOND = 1000;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int DAYS_IN_WEEK = 7;

    public static final int SECOND = MILLIS_IN_SECOND;
    public static final int MINUTE = SECOND * SECONDS_IN_MINUTE;
    public static final int HOUR = MINUTE * MINUTES_IN_HOUR;
    public static final int DAY = HOUR * HOURS_IN_DAY;
    public static final int WEEK = DAY * DAYS_IN_WEEK;
    public static final ZoneId DEFAULT_ZONE_ID = ZoneOffset.UTC;
    public static final ChronoUnit TIME_MEASUREMENT_ACCURACY = ChronoUnit.MILLIS;

}
