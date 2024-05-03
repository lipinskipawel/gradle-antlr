package com.github.lipinskipawel.tutorial.expression;

import java.util.ArrayList;
import java.util.List;

public final class Program {
    public final List<Expression> expressions;

    public Program() {
        this.expressions = new ArrayList<>();
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }
}
