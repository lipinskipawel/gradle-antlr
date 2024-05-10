package com.github.lipinskipawel.calculator;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.lipinskipawel.calculator.Calculator.calculatorBuilder;
import static com.github.lipinskipawel.calculator.ConstObject.constObject;

@DisplayName("Calculator with custom const spec")
final class CalculatorCustomConstTest implements WithAssertions {

    @Test
    @DisplayName("register 2 level deep custom objects")
    void register_2_level_deep_custom_objects() {
        final var input = "MATH.PI";
        final var calculate = calculatorBuilder()
                .registeredObjects(List.of(constObject("MATH", 0, constObject("PI", 3.14))))
                .build();

        final var result = calculate.calculate(input);

        assertThat(result.doubleValue()).isEqualTo(3.14);
    }

    @Test
    @DisplayName("register 5 level deep custom objects")
    void register_5_level_deep_custom_objects() {
        final var input = "a.b.c.d.e";
        final var calculate = calculatorBuilder()
                .registeredObjects(List.of(
                        constObject("a", 1,
                                constObject("b", 2,
                                        constObject("c", 3,
                                                constObject("d", 4,
                                                        constObject("e", 5)))))))
                .build();

        final var result = calculate.calculate(input);

        assertThat(result.intValue()).isEqualTo(5);
    }
}
