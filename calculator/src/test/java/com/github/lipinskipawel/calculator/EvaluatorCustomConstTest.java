package com.github.lipinskipawel.calculator;

import com.github.lipinskipawel.calculator.transformers.ConstObject;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.github.lipinskipawel.calculator.transformers.ConstObject.constObject;
import static com.github.lipinskipawel.calculator.transformers.Evaluator.evaluator;

@DisplayName("Calculator with custom const spec")
final class EvaluatorCustomConstTest implements WithAssertions {

    @Test
    @DisplayName("register 2 level deep custom objects")
    void register_2_level_deep_custom_objects() {
        final var input = "MATH.PI";

        final var result = evaluate(input, List.of(constObject("MATH", 0, constObject("PI", 3.14))));

        assertThat(result.doubleValue()).isEqualTo(3.14);
    }

    @Test
    @DisplayName("register 5 level deep custom objects")
    void register_5_level_deep_custom_objects() {
        final var input = "a.b.c.d.e";

        final var result = evaluate(input, List.of(
                constObject("a", 1,
                        constObject("b", 2,
                                constObject("c", 3,
                                        constObject("d", 4,
                                                constObject("e", 5)))))));

        assertThat(result.intValue()).isEqualTo(5);
    }

    private Number evaluate(String input, List<ConstObject> objects) {
        final var charStream = CharStreams.fromString(input);
        final var lexer = new CalculatorLexer(charStream);
        final var tokens = new CommonTokenStream(lexer);
        final var parser = new CalculatorParser(tokens);
        final var antlrProgram = parser.prog();

        final var calculator = evaluator(Map.of(), objects);
        return calculator.visit(antlrProgram);
    }
}
