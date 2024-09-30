lexer grammar CalculatorLexer;

// simple tokens
L_PAREN      : '('      ;
R_PAREN      : ')'      ;
L_BRACK      : '['      ;
R_BRACK      : ']'      ;
COMMA        : ','      ;
MUL          : '*'      ;
DIV          : '/'      ;
PLUS         : '+'      ;
MINUS        : '-'      ;

// Tokens
INTEGER      : ('-')?[0-9][0-9]*;   // maybe not allow numbers like '0123'

E            : ('E' | 'e') ;
POW          : ('POW' | 'pow') ;
SQRT         : ('SQRT' | 'sqrt') ;

CONST        : ([a-zA-Z]+'.' (([a-zA-Z]+'.')? | ([a-zA-Z]+)) )+ ; // because of IDENTIFIER this regex had to be a bit
                                                                 // longer. I should re-think custom functions and const
IDENTIFIER   : [a-zA-Z]+ ;
WS           : [ \t\r\n]+ -> skip ;
