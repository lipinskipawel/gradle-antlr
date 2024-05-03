package com.github.lipinskipawel.tutorial.app;

import com.github.lipinskipawel.tutorial.ExprLexer;
import com.github.lipinskipawel.tutorial.ExprParser;
import com.github.lipinskipawel.tutorial.expression.ExpressionProcessor;
import com.github.lipinskipawel.tutorial.listeners.SyntaxError;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public final class Application {

    // ./gradlew run --args='/../gradle-antlr/tutorial-visitor/src/test/resources/test0.txt'
    // ./gradlew run --args='/../gradle-antlr/tutorial-visitor/src/test/resources/test1.txt'
    // ./gradlew run --args='/../gradle-antlr/tutorial-visitor/src/test/resources/test2.txt'
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: file name");
            return;
        }
        final var fileName = args[0];
        final var parser = exprParser(fileName);

        // tell ANTLR to build a parse tree
        // parse from the start symbol
        parser.prog();
        if (SyntaxError.HAS_ERROR) {
            return;
        }

        // create a visitor for converting the parsed tree into the expression object
        final var program = parser.program;

        if (parser.semanticErrors.isEmpty()) {
            final var expressionProcessor = new ExpressionProcessor(program.expressions);
            for (var evaluation : expressionProcessor.evaluationResult()) {
                System.out.println(evaluation);
            }
        } else {
            for (var error : parser.semanticErrors) {
                System.out.println(error);
            }
        }
    }

    /**
     * Here are the types of parser and lexer are specified to the grammar name Expr.g4
     */
    private static ExprParser exprParser(String fileName) {
        ExprParser parser = null;
        try {
            final var input = CharStreams.fromFileName(fileName);
            final var lexer = new ExprLexer(input);
            final var tokens = new CommonTokenStream(lexer);
            parser = new ExprParser(tokens);

            // syntax error handling
            parser.removeErrorListeners();
            parser.addErrorListener(new SyntaxError());
        } catch (IOException e) {
            System.err.println(e);
        }
        return parser;
    }
}
