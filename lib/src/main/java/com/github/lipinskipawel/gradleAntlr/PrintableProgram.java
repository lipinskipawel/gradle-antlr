package com.github.lipinskipawel.gradleAntlr;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;

public class PrintableProgram {
    private final ParseTree tree;

    public PrintableProgram(ParseTree tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return print(tree, "");
    }

    private String print(ParseTree tree, String prefix) {
        if (tree instanceof FirstParser.PrimaryExprContext node && node.getChildCount() == 1) {
            return visitPrimary(node);
        }

        if (tree instanceof TerminalNode node) {
            return visitTerminal(node);
        }
        if (!(tree instanceof RuleNode node)) {
            return "";
        }
        final var name = Arrays.stream(FirstParser.ruleNames).toList().get(((RuleNode) tree).getRuleContext().getRuleIndex());
        final var builder = new StringBuilder(name);

        for (var i = 0; i < node.getChildCount(); i++) {
            final var atEnd = (i == node.getChildCount() - 1);
            final var symbol = atEnd ? "└──" : "├──";

            final var child = node.getChild(i);
            final var childSymbol = atEnd ? " " : "│";
            final var childStr = print(child, prefix + childSymbol + "   ");

            builder.append("\n");
            builder.append(prefix);
            builder.append(symbol);
            builder.append(" ");
            builder.append(childStr);
        }

        return builder.toString();
//        System.out.println("------------------");
//        Arrays.stream(FirstLexer.ruleNames).forEach(System.out::println);
//        System.out.println("------------------");
//        Arrays.stream(FirstParser.ruleNames).forEach(System.out::println);
    }

    private String visitPrimary(FirstParser.PrimaryExprContext node) {
        final var name = FirstParser.ruleNames[node.getRuleContext().getRuleIndex()];
        final var childStr = visitTerminal((TerminalNode) node.getChild(0));
        return name + " ── " + childStr;
    }

    private String visitTerminal(TerminalNode node) {
        final var ruleName = FirstLexer
                .ruleNames[node.getSymbol().getType() - 1];

        final var id = id(ruleName);
        return id + "'" + node + "'";
    }

    private String id(String ruleName) {
        if (ruleName.equals("T__")) {
            return "P";
        } else {
            return ruleName.substring(0, 1);
        }
    }
}
