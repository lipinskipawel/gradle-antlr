package com.github.lipinskipawel.gradleAntlr.calculator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Calculator Spec")
class CalculatorTest implements WithAssertions {

    @Test
    void should_add_two_numbers() {
        final var input = "2 + 4";
        final var charStream = CharStreams.fromString(input);
        final var lexer = new CalculatorLexer(charStream);
        final var commonTokenStream = new CommonTokenStream(lexer);
        final var parser = new CalculatorParser(commonTokenStream);

        final var calculator = new Calculator();

        final var result = calculator.visit(parser.program());

        assertThat(result).isEqualTo(6);
    }
}