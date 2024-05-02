package com.github.lipinskipawel.tutorial.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExpressionProcessor {
    List<Expression> expressions;
    public Map<String, Integer> values; // symbol table for storing values of variables;

    public ExpressionProcessor(List<Expression> expressions) {
        this.expressions = expressions;
        this.values = new HashMap<>();
    }

    // use visitor pattern to avoid casting
    public List<String> evaluationResult() {
        final var evaluations = new ArrayList<String>();

        for (var expression : expressions) {
            if (expression instanceof VariableDeclaration variableDeclaration) {
                values.put(variableDeclaration.id, variableDeclaration.value);
            } else {
                // expression that we have to evaluate because it can be Number, Variable, Addition, Multiplication;
                String input = expression.toString();
                int result = evalResult(expression);
                evaluations.add(input + " is " + result);
            }
        }

        return evaluations;
    }

    private int evalResult(Expression expression) {
        int result = 0;

        if (expression instanceof Number number) {
            result = number.number;
        }
        if (expression instanceof Variable variable) {
            result = values.get(variable.id);
        }
        if (expression instanceof Addition addition) {
            final var left = evalResult(addition.left);
            final var right = evalResult(addition.right);
            result = left + right;
        }
        if (expression instanceof Multiplication multiplication) {
            final var left = evalResult(multiplication.left);
            final var right = evalResult(multiplication.right);
            result = left * right;
        }

        return result;
    }
}
