package dev.arhor.simple.todo.service;

import java.time.Instant;
import java.util.TimeZone;

public interface TimeService {

    Instant now();

    Instant now(TimeZone timeZone);

    Instant weekAgo();
}
