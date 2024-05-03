package com.github.lipinskipawel.tutorial;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

final class ExprTest implements WithAssertions {

    /**
     * grun build/grnerated-src/antlr/main/com.github.lipinskipawel.tutorial.Expr prog \
     * src/test/resources/test0.txt \
     * -gui &
     */
    @Test
    void semantic_error_with_syntax_error() throws IOException {
        final var fileStream = getClass().getClassLoader().getResourceAsStream("test0.txt");
        final var charStream = CharStreams.fromStream(fileStream);
        final var lexer = new ExprLexer(charStream);
        final var commonTokenStream = new CommonTokenStream(lexer);
        new ExprParser(commonTokenStream);
    }

    /**
     * semantic error because there is assignment twice, and we don't allow this in the language
     */
    @Test
    void semantic_error_without_syntax_error() throws IOException {
        final var fileStream = getClass().getClassLoader().getResourceAsStream("test1.txt");
        final var charStream = CharStreams.fromStream(fileStream);
        final var lexer = new ExprLexer(charStream);
        final var commonTokenStream = new CommonTokenStream(lexer);
        new ExprParser(commonTokenStream);
    }

    /**
     * grammatical error = syntax is wrong
     */
    @Test
    void no_semantic_and_syntax_errors_thus_correct_grammar() throws IOException {
        final var fileStream = getClass().getClassLoader().getResourceAsStream("test2.txt");
        final var charStream = CharStreams.fromStream(fileStream);
        final var lexer = new ExprLexer(charStream);
        final var commonTokenStream = new CommonTokenStream(lexer);
        new ExprParser(commonTokenStream);
    }
}
