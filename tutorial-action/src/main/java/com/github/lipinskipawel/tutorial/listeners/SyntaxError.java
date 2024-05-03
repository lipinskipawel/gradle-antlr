package com.github.lipinskipawel.tutorial.listeners;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import static java.util.Collections.reverse;

public final class SyntaxError extends BaseErrorListener {
    public static boolean HAS_ERROR = false;

    @Override
    public void syntaxError(
            Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e
    ) {
        HAS_ERROR = true;
        final var stack = ((Parser) recognizer).getRuleInvocationStack();
        reverse(stack);
        System.err.println("Syntax Error!");
        System.err.println("Token " + "\"" + ((Token) (offendingSymbol)).getText() + "\""
                + " (line " + line + ", column " + (charPositionInLine + 1) + ")"
                + ": " + msg);
        System.err.println("Rule stack: " + stack);
    }
}
