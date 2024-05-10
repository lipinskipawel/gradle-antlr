package com.github.lipinskipawel.calculator;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.Map;

public final class Calculator {
    private final Evaluator evaluator;

    private Calculator(Builder builder) {
        this.evaluator = new Evaluator(builder.registeredFunctions, builder.registeredObjects);
    }

    public static Builder calculatorBuilder() {
        return new Builder();
    }

    public Number calculate(String input) {
        final var charStream = CharStreams.fromString(input);
        final var lexer = new CalculatorLexer(charStream);
        final var tokens = new CommonTokenStream(lexer);
        final var parser = new CalculatorParser(tokens);
        final var antlrProgram = parser.prog();

        final var result = evaluator.visit(antlrProgram);
        if (!evaluator.semanticErrors.isEmpty()) {
            final var msg = "Semantic errors detected\n" + evaluator.semanticErrors.get(0);
            throw new RuntimeException(msg);
        }
        return result;
    }

    public static class Builder {
        private Map<String, NumberInterface> registeredFunctions = Map.of();
        private List<ConstObject> registeredObjects = List.of();

        private Builder() {
        }

        public Builder registeredFunctions(Map<String, NumberInterface> registeredFunctions) {
            this.registeredFunctions = registeredFunctions;
            return this;
        }

        public Builder registeredObjects(List<ConstObject> registeredObjects) {
            this.registeredObjects = registeredObjects;
            return this;
        }

        public Calculator build() {
            return new Calculator(this);
        }
    }
}
