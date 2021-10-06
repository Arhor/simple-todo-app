package dev.arhor.simple.todo.service;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public Instant now() {
        return Clock.systemUTC().instant().truncatedTo(ChronoUnit.MILLIS);
    }

    @Override
    public Instant daysBeforeNow(int daysNumber) {
        return now().minus(daysNumber, ChronoUnit.DAYS);
    }
}
