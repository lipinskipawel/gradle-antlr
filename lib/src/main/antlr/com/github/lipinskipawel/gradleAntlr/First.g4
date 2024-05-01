grammar First;

@header {
package com.github.lipinskipawel.gradleAntlr;
}

program : expression ;

expression : additiveExpr ;

additiveExpr : additiveExpr ('+' | '-') primaryExpr
       | primaryExpr
       ;

primaryExpr : NUM ;

NUM : [0-9]+ ;
SPACES : [ \t] -> skip;
