package com.github.lipinskipawel.calculator;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.lipinskipawel.calculator.Calculator.calculatorBuilder;
import static org.junit.jupiter.params.provider.Arguments.of;

@DisplayName("Calculator Spec")
class CalculatorTest implements WithAssertions {

    private final Calculator calculate = calculatorBuilder().build();

    @Test
    @DisplayName("throws error on any semantic error reported")
    void throws_error_when_semantic_errors_present() {
        final var input = "size(5)";

        final var throwable = catchThrowable(() -> calculate.calculate(input));

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("""
                        Semantic errors detected
                        Error: function [size] does not exists""");
    }

    @Nested
    @DisplayName("Adding always add numbers")
    class Addition {

        private static Stream<Arguments> zeroTests() {
            return Stream.of(
                    of("0 + 0", 0),
                    of("0 + 1", 1),
                    of("1 + 0", 1)
            );
        }

        @ParameterizedTest
        @MethodSource("zeroTests")
        @DisplayName("zero will not add anything")
        void zero_will_not_add_anything(String input, int expectedResult) {
            assertThat(calculate.calculate(input).intValue()).isEqualTo(expectedResult);
        }

        private static Stream<Arguments> twoNumbers() {
            return Stream.of(
                    of("2 + 3", 5),
                    of("2 + 6", 8),
                    of("11 + 12", 23)
            );
        }

        @ParameterizedTest
        @MethodSource("twoNumbers")
        @DisplayName("two numbers will result with the sum of then")
        void two_numbers_added_then_result_is_sum(String input, int expectedResult) {
            final var result = calculate.calculate(input);

            assertThat(result.intValue()).isEqualTo(expectedResult);
        }

        private static Stream<Arguments> threeNumbers() {
            return Stream.of(
                    of("2 + 3 + 1", 6),
                    of("2 + 6 + 10", 18),
                    of("11 + 12 + 2", 25)
            );
        }

        @ParameterizedTest
        @MethodSource("threeNumbers")
        @DisplayName("three numbers will result with the sum of then")
        void three_numbers_added_then_result_is_sum(String input, int expectedResult) {
            final var result = calculate.calculate(input);

            assertThat(result.intValue()).isEqualTo(expectedResult);
        }

        private static Stream<Arguments> negativeNumbers() {
            return Stream.of(
                    of("-2 + 2", 0),
                    of("-2 + 1", -1)
            );
        }

        @ParameterizedTest
        @MethodSource("negativeNumbers")
        @DisplayName("negative numbers will be added correctly")
        void negative_numbers_added_correctly(String input, int expectedResult) {
            assertThat(calculate.calculate(input).intValue()).isEqualTo(expectedResult);
        }
    }

    private static Stream<Arguments> order() {
        return Stream.of(
                of("(1 + 1) * 8", 16),
                of("1 + (1 * 8)", 9)
        );
    }

    @ParameterizedTest
    @MethodSource("order")
    @DisplayName("Follow math order for parentheses")
    void follow_math_order_of_parentheses(String input, int expectedResult) {
        assertThat(calculate.calculate(input).intValue()).isEqualTo(expectedResult);
    }

    @Nested
    @DisplayName("Build in functions work")
    class Functions {

        private static Stream<Arguments> functions() {
            return Stream.of(
                    of("pow[3]", 9),
                    of("sqrt[9]", 3)
            );
        }

        @ParameterizedTest
        @MethodSource("functions")
        @DisplayName("Single invocation of functions")
        void single_invocation(String input, int expectedResult) {
            assertThat(calculate.calculate(input).intValue()).isEqualTo(expectedResult);
        }

        private static Stream<Arguments> functionsWithExpression() {
            return Stream.of(
                    of("pow[3] + 2", 11),
                    of("pow[3] - 9", 0),
                    of("pow[1 + 1]", 4),
                    of("sqrt[9] + 1", 4),
                    of("sqrt[9] - 1", 2),
                    of("sqrt[5 + 4]", 3)
            );
        }

        @ParameterizedTest
        @MethodSource("functionsWithExpression")
        @DisplayName("with additional expression")
        void function_with_expression(String input, int expectedResult) {
            assertThat(calculate.calculate(input).intValue()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Build in const work")
    class Const {

        @Test
        @DisplayName("E is working")
        void const_value() {
            final var input = "e";

            final var result = calculate.calculate(input);

            assertThat(result.doubleValue()).isEqualTo(2.71);
        }

        @Test
        @DisplayName("E is working surrounded with other math operations")
        void const_value_with_() {
            final var input = "2 + e - 1";

            final var result = calculate.calculate(input);

            assertThat(result.doubleValue()).isEqualTo(3.71);
        }
    }
}
