grammar Calculator;

@header {
package com.github.lipinskipawel.calculator;
}

// Tokens
INTEGER      : ('-')?[0-9][0-9]*   // maybe not allow numbers like '0123'
             ;

E            : ('E' | 'e') ;
POW          : ('POW' | 'pow') ;
SQRT         : ('SQRT' | 'sqrt') ;

CONST        : ([a-zA-Z]+'.' (([a-zA-Z]+'.')? | ([a-zA-Z]+)) )* ; // because of IDENTIFIER this regex had to be a bit
                                                                 // longer. I should re-think custom functions and const
IDENTIFIER   : [a-zA-Z]+ ;
WS           : [ \t\r\n]+ -> skip ;


// Rules
prog : expr EOF          # Program
     ;

expr : expr '*' expr                               # Multiply
     | expr '/' expr                               # Divide
     | expr '+' expr                               # Addition
     | expr '-' expr                               # Subtraction
     | INTEGER                                     # Number
     | '(' expr ')'                                # Parenthesis
     | op=(POW | SQRT) '[' expr ']'                # Function
     | IDENTIFIER '(' (expr)* (',' expr)*  ')'     # CustomFunction
     | E                                           # Const
     | CONST                                       # CustomConst
     ;
