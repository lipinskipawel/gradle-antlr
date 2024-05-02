package com.github.lipinskipawel.tutorial.expression;

public final class Variable extends Expression {
    public String id;

    public Variable(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
