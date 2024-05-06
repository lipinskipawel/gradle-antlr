grammar Calculator;

@header {
package com.github.lipinskipawel.calculator;
}

// Tokens
INTEGER     : ('-')?[0-9][0-9]*   // maybe not allow numbers like '0123'
            ;

POW       : ('POW' | 'pow') ;
SQRT      : ('SQRT' | 'sqrt') ;

WS        : [ \t\r\n]+ -> skip ;


// Rules
prog : expr EOF          # Program
     ;

expr : expr '*' expr                  # Multiply
     | expr '/' expr                  # Divide
     | expr '+' expr                  # Addition
     | expr '-' expr                  # Subtraction
     | INTEGER                        # Number
     | '(' expr ')'                   # Parenthesis
     | op=(POW | SQRT) '[' expr ']'   # Function
     ;