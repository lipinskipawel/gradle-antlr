package com.github.lipinskipawel.tutorial.transformers;

import com.github.lipinskipawel.tutorial.ExprBaseVisitor;
import com.github.lipinskipawel.tutorial.ExprParser;
import com.github.lipinskipawel.tutorial.Program;

import java.util.ArrayList;
import java.util.List;

public final class AntlrToProgram extends ExprBaseVisitor<Program> {

    public List<String> semanticErrors; // to be accessed by the main application program

    @Override
    public Program visitProgram(ExprParser.ProgramContext ctx) {
        final var program = new Program();

        semanticErrors = new ArrayList<>();
        final var expressionVisitor = new AntlrToExpression(semanticErrors);
        for (var i = 0; i < ctx.getChildCount(); i++) {
            if (i == ctx.getChildCount() - 1) {
                // last child of prog is EOF
                // do not convert it to Expression
            } else {
                program.addExpression(expressionVisitor.visit(ctx.getChild(i)));
            }
        }

        return program;
    }
}
