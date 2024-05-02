package com.github.lipinskipawel.gradleAntlr.calculator;

public final class Calculator extends CalculatorBaseVisitor<Integer> {

    @Override
    public Integer visitMulDiv(CalculatorParser.MulDivContext ctx) {
        final var left = visit(ctx.expr(0));
        final var right = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.MULTIPLY) {
            return left * right;
        }
        return left / right;
    }

    @Override
    public Integer visitAddSub(CalculatorParser.AddSubContext ctx) {
        final var left = visit(ctx.expr(0));
        final var right = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.PLUS) {
            return left + right;
        }
        return left - right;
    }

    @Override
    public Integer visitNum(CalculatorParser.NumContext ctx) {
        return Integer.valueOf(ctx.NUM().getText());
    }

    @Override
    public Integer visitParens(CalculatorParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}
