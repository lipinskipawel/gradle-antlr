package com.github.lipinskipawel.tutorial;

public final class Number extends Expression {
    private final int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
