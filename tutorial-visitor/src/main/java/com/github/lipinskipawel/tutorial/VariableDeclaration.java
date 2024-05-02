package com.github.lipinskipawel.tutorial;

public final class VariableDeclaration extends Expression {
    public String id;
    public String type; // keyword, like INT
    public int value; // initial value

    public VariableDeclaration(String id, String type, int value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }
}
