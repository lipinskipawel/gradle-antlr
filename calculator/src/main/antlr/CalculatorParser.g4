parser grammar CalculatorParser;

options { tokenVocab = CalculatorLexer; }

// Rules
prog : expr EOF          # Program
     ;

expr : expr MUL expr                                         # Multiply
     | expr DIV expr                                         # Divide
     | expr PLUS expr                                        # Addition
     | expr MINUS expr                                       # Subtraction
     | INTEGER                                               # Number
     | L_PAREN expr R_PAREN                                  # Parenthesis
     | op=(POW | SQRT) L_BRACK expr R_BRACK                  # Function
     | IDENTIFIER L_PAREN (expr)* (COMMA expr)*  R_PAREN     # CustomFunction
     | E                                                     # Const
     | CONST                                                 # CustomConst
     ;
