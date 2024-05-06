package com.github.lipinskipawel.calculator.transformers;

import com.github.lipinskipawel.calculator.CalculatorBaseVisitor;
import com.github.lipinskipawel.calculator.CalculatorParser;

import static com.github.lipinskipawel.calculator.CalculatorParser.POW;
import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public final class Evaluator extends CalculatorBaseVisitor<Number> {

    @Override
    public Number visitProgram(CalculatorParser.ProgramContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Number visitMultiply(CalculatorParser.MultiplyContext ctx) {
        final var left = visit(ctx.expr(0));
        final var right = visit(ctx.expr(1));
        return left.intValue() * right.intValue();
    }

    @Override
    public Number visitDivide(CalculatorParser.DivideContext ctx) {
        final var left = visit(ctx.expr(0));
        final var right = visit(ctx.expr(1));
        return left.intValue() / right.intValue();
    }

    @Override
    public Number visitAddition(CalculatorParser.AdditionContext ctx) {
        final var left = visit(ctx.expr(0));
        final var right = visit(ctx.expr(1));
        return left.intValue() + right.intValue();
    }

    @Override
    public Number visitSubtraction(CalculatorParser.SubtractionContext ctx) {
        final var left = visit(ctx.expr(0));
        final var right = visit(ctx.expr(1));
        return left.intValue() - right.intValue();
    }

    @Override
    public Number visitFunction(CalculatorParser.FunctionContext ctx) {
        if (ctx.op.getType() == POW) {
            final var number = visit(ctx.expr()).doubleValue();
            return pow(number, 2);
        }
        return sqrt(visit(ctx.expr()).doubleValue());
    }

    @Override
    public Number visitNumber(CalculatorParser.NumberContext ctx) {
        return parseInt(ctx.INTEGER().getText());
    }

    @Override
    public Number visitParenthesis(CalculatorParser.ParenthesisContext ctx) {
        return visit(ctx.expr());
    }
}
