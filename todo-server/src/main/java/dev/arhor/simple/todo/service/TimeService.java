package dev.arhor.simple.todo.service;

import java.time.Instant;

public interface TimeService {

    Instant now();

    Instant daysBeforeNow(int days);
}
