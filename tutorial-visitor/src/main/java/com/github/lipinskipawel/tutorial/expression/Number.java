package com.github.lipinskipawel.tutorial.expression;

public final class Number extends Expression {
    public final int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
