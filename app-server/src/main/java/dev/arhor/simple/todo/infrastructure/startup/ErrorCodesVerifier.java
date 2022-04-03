package dev.arhor.simple.todo.infrastructure.startup;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.arhor.simple.todo.common.Result;
import dev.arhor.simple.todo.exception.ErrorCode;

@Component
public class ErrorCodesVerifier implements Verifier {

    @Override
    public Result<String> verify() {
        var duplicates = new EnumMap<ErrorCode.Type, HashMap<Number, List<ErrorCode>>>(ErrorCode.Type.class);

        var errorCodesGroupedByType = Arrays.stream(ErrorCode.values())
            .collect(groupingBy(ErrorCode::getType));

        for (var errorCodesByTypeEntry : errorCodesGroupedByType.entrySet()) {
            var errorCodesType = errorCodesByTypeEntry.getKey();
            var errorCodesByType = errorCodesByTypeEntry.getValue();

            var errorCodesGroupedByNumericValue = errorCodesByType.stream()
                .collect(groupingBy(ErrorCode::getNumericValue));

            for (var errorCodesByNumericValueEntry : errorCodesGroupedByNumericValue.entrySet()) {
                var errorCodeNumericValue = errorCodesByNumericValueEntry.getKey();
                var errorCodesByNumericValue = errorCodesByNumericValueEntry.getValue();

                if (errorCodesByNumericValue.size() > 1) {
                    duplicates
                        .computeIfAbsent(errorCodesType, type -> new HashMap<>())
                        .computeIfAbsent(errorCodeNumericValue, number -> new ArrayList<>())
                        .addAll(errorCodesByNumericValue);
                }
            }
        }

        return duplicates.isEmpty()
            ? Result.success("Error codes verified")
            : Result.failure("Duplicate numeric values found for the same error-code type: " + duplicates);
    }
}
