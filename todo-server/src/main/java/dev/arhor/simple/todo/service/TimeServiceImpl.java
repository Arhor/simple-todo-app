package dev.arhor.simple.todo.service;

import static dev.arhor.simple.todo.common.TimeUtils.DAYS_IN_WEEK;
import static dev.arhor.simple.todo.common.TimeUtils.DEFAULT_ZONE_ID;
import static dev.arhor.simple.todo.common.TimeUtils.TIME_MEASUREMENT_ACCURACY;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public Instant now() {
        return currentInstant(DEFAULT_ZONE_ID);
    }

    @Override
    public Instant now(final TimeZone timeZone) {
        return currentInstant((timeZone != null) ? timeZone.toZoneId() : DEFAULT_ZONE_ID);
    }

    @Override
    public Instant weekAgo() {
        return now().minus(Duration.ofDays(DAYS_IN_WEEK));
    }

    private Instant currentInstant(final ZoneId zoneId) {
        return Clock.system(zoneId).instant().truncatedTo(TIME_MEASUREMENT_ACCURACY);
    }
}
