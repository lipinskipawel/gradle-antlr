package com.github.lipinskipawel.calculator.transformers;

import com.github.lipinskipawel.calculator.CalculatorBaseVisitor;
import com.github.lipinskipawel.calculator.CalculatorParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.lipinskipawel.calculator.CalculatorParser.POW;
import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public final class Evaluator extends CalculatorBaseVisitor<Number> {
    private final Map<String, NumberInterface> registeredFunctions;
    private final List<String> semanticErrors;

    private Evaluator(Map<String, NumberInterface> registeredFunctions) {
        this.registeredFunctions = registeredFunctions;
        this.semanticErrors = new ArrayList<>();
    }

    public static Evaluator evaluator(Map<String, NumberInterface> registeredFunctions) {
        return new Evaluator(registeredFunctions);
    }

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
    public Number visitCustomFunction(CalculatorParser.CustomFunctionContext ctx) {
        if (!registeredFunctions.containsKey(ctx.IDENTIFIER().getText())) {
            semanticErrors.add("Error: function [%s] does not exists".formatted(ctx.IDENTIFIER().getText()));
            return 0;
        }
        final var arguments = arguments(ctx);
        return registeredFunctions.get(ctx.IDENTIFIER().getText()).run(arguments);
    }

    private List<Number> arguments(CalculatorParser.CustomFunctionContext ctx) {
        final var childCount = ctx.getChildCount();
        if (childCount < 4) {
            return List.of();
        }
        if (childCount == 4) {
            return List.of(visit(ctx.getChild(2)));
        }
        final var result = new ArrayList<Number>();
        for (var i = 2; i < ctx.getChildCount() - 1; i += 2) {
            result.add(visit(ctx.getChild(i)));
        }
        return result;
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
