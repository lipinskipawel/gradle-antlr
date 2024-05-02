grammar Calculator;

@header {
package com.github.lipinskipawel.gradleAntlr.calculator;
}

// lexical rules
NUM : [0-9]+ ;
SPACES : [ \t]+ -> skip ;

PLUS : '+' ;
MINUS : '-' ;
MULTIPLY : '*' ;
DIVIDE : '/' ;

// parser rules
program : expr ;

// the '#' means adding labels so then the CalculatorBaseVisitor will contain proper methods
expr : expr op=(MULTIPLY | DIVIDE) expr    # MulDiv
     | expr op=(PLUS | MINUS) expr         # AddSub
     | NUM                                 # Num
     | '(' expr ')'                        # Parens
     ;
