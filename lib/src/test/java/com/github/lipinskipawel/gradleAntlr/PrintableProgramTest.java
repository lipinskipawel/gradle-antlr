package com.github.lipinskipawel.gradleAntlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

class PrintableProgramTest {

    @Test
    void print_first_program() {
        final var input = "10 + 2 + 4 - 6";
        final var inputStream = CharStreams.fromString(input);
        final var lexer = new FirstLexer(inputStream);
        final var tokens = new CommonTokenStream(lexer);

        final var parser = new FirstParser(tokens);
        final var tree = parser.program();

        final var printableProgram = new PrintableProgram(tree);
        System.out.println(printableProgram);
    }
}
