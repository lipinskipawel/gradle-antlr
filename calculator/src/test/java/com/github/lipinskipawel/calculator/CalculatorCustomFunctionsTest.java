package com.github.lipinskipawel.calculator;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.lipinskipawel.calculator.Calculator.calculatorBuilder;
import static org.junit.jupiter.params.provider.Arguments.of;

@DisplayName("Calculator with custom function spec")
final class CalculatorCustomFunctionsTest implements WithAssertions {

    @Test
    @DisplayName("register custom function with zero arguments")
    void allow_functions_with_zero_args() {
        final var input = "size()";
        final var calculate = calculatorBuilder()
                .registeredFunctions(Map.of(
                        "size", List::size
                ))
                .build();

        final var result = calculate.calculate(input);

        assertThat(result.intValue()).isEqualTo(0);
    }

    private static Stream<Arguments> customFunctions() {
        return Stream.of(
                of("size(1)", 1),
                of("size(1, 3)", 2),
                of("size(1, 4, 5)", 3),
                of("size(1, 3, 4, 5)", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("customFunctions")
    @DisplayName("register custom function with many arguments")
    void register_custom_function(String input, int expectedResult) {
        final var calculate = calculatorBuilder()
                .registeredFunctions(Map.of(
                        "size", List::size
                ))
                .build();

        final var result = calculate.calculate(input);

        assertThat(result.intValue()).isEqualTo(expectedResult);
    }
}
