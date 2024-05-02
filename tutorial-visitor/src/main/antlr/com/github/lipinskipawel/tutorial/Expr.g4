grammar Expr;

// comment

@header {
package com.github.lipinskipawel.tutorial;
}

// Start variable
// EOF - it is important for error handling
prog: (decl | expr)+ EOF             # Program
    ;

// tokens directly specified are in ''
// we allow initialization only once
decl : ID ':' INT_TYPE '=' NUM       # Declaration
     ;

// higher in specification higher the precedence will be
expr : expr '*' expr                 # Multiplication
     | expr '+' expr                 # Addition
     | ID                            # Variable
     | NUM                           # Number
     ;

// tokens
ID : [a-z][a-zA-Z0-9_]* ; // identifier
NUM : '0' | '-'?[1-9][0-9]* ; // ? - means 0 or none
INT_TYPE : 'INT' ; // this is keyword
COMMENT : '--' ~[\r\n]* -> skip ; // ~ - means negation
WS : [ \t\n]* -> skip ; // white spaces
