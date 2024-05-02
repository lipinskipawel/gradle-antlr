package com.github.lipinskipawel.tutorial.transformers;

import com.github.lipinskipawel.tutorial.Addition;
import com.github.lipinskipawel.tutorial.ExprBaseVisitor;
import com.github.lipinskipawel.tutorial.ExprParser;
import com.github.lipinskipawel.tutorial.Expression;
import com.github.lipinskipawel.tutorial.Multiplication;
import com.github.lipinskipawel.tutorial.Number;
import com.github.lipinskipawel.tutorial.Variable;
import com.github.lipinskipawel.tutorial.VariableDeclaration;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public final class AntlrToExpression extends ExprBaseVisitor<Expression> {
    /**
     * Given that all visit_* methods are called in a top-down fashion,
     * we can be sure that the order in which we add declared variables in the `vars` is
     * identical to how they are declared in the input program.
     * <p>
     * Top-down here means top-down in the input file which make sense (every program works like that).
     * The way we are going to execute visit_* will be top-down.
     */
    private final List<String> vars; // to store all the variables that have been declared

    // duplicate declaration
    // reference to undeclared variable
    // semantics errors are different from syntax errors
    private final List<String> semanticErrors;

    public AntlrToExpression(List<String> semanticErrors) {
        this.vars = new ArrayList<>();
        this.semanticErrors = semanticErrors;
    }

    @Override
    public Expression visitDeclaration(ExprParser.DeclarationContext ctx) {
        final var identifier = ctx.getChild(0).getText();
        if (vars.contains(identifier)) {
            // ID() comes from the source grammar
            final var idToken = ctx.ID().getSymbol(); // equivalent to ctx.getChild(0).getSymbol()
            final var line = idToken.getLine();
            final var column = idToken.getCharPositionInLine() + 1;
            semanticErrors.add("Error: variable " + identifier + " already declared (" + line + ", " + column + ")");
        } else {
            vars.add(identifier);
        }
        final var type = ctx.getChild(2).getText();
        final var value = parseInt(ctx.NUM().getText());
        return new VariableDeclaration(identifier, type, value);
    }

    @Override
    public Expression visitMultiplication(ExprParser.MultiplicationContext ctx) {
        final var left = visit(ctx.expr(0)); // recursively visit the left subtree
        final var right = visit(ctx.expr(1));
        return new Multiplication(left, right);
    }

    @Override
    public Expression visitAddition(ExprParser.AdditionContext ctx) {
        final var left = visit(ctx.expr(0)); // recursively visit the left subtree
        final var right = visit(ctx.expr(1));
        return new Addition(left, right);
    }

    @Override
    public Expression visitVariable(ExprParser.VariableContext ctx) {
        final var identifier = ctx.getChild(0).getText();
        if (!vars.contains(identifier)) {
            final var idToken = ctx.ID().getSymbol();
            final var line = idToken.getLine();
            final var column = idToken.getCharPositionInLine() + 1;
            semanticErrors.add("Error: variable " + identifier + " not declared (" + line + ", " + column + ")");
        }
        return new Variable(identifier);
    }

    @Override
    public Expression visitNumber(ExprParser.NumberContext ctx) {
        // ctx is just an expression parent from the g4
        // getChild(0) is just the first (and only) child in the parent expression
        //            expr
        //      |             |
        //    expr           expr
        //      |             |
        //      i            expr
        //                |        |
        //                j        3
        // we are here at expr -> 3
        // parent (expr) have only one child (3)
        final var numberText = ctx.getChild(0).getText();
        final var integer = parseInt(numberText);
        return new Number(integer);
    }
}
