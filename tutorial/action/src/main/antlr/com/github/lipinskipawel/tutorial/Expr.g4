grammar Expr;

// comment

@header {
package com.github.lipinskipawel.tutorial;
import org.antlr.v4.runtime.*;
import java.io.*;
import java.util.*;
// model classes
import com.github.lipinskipawel.tutorial.expression.*;
}

// attributes or methods for generated parser class
@members {
// for error handling
public List<String> vars = new ArrayList<>();
public List<String> semanticErrors = new ArrayList<>();
// root of AST (of type Expression)
public Program program;
}

// Start variable
// EOF - it is important for error handling
prog returns [Program p]
@init{ // executed before the production takes effect
    $p = new com.github.lipinskipawel.tutorial.expression.Program();
    program = $p;
}
    : (
        d=decl {
            $p.addExpression($d.d);
        }
      |
        e=expr {
            $p.addExpression($e.e);
        }
      )+
    EOF
    ;

// tokens directly specified are in ''
// we allow initialization only once
decl returns [Expression d]
     : name=ID ':' type=INT_TYPE '=' val=NUM {
         int line = $name.getLine();
         int column = $name.getCharPositionInLine() + 1;

         String id = $name.text;
         if (vars.contains(id)) {
             semanticErrors.add("Error: variable '" + id + "' already declared (" + line + ", " + column + ")");
         } else {
             vars.add(id);
         }
         String type = $type.getText();
         int val = $val.int;
         $d = new com.github.lipinskipawel.tutorial.expression.VariableDeclaration(id, type, val);
     }
     ;

// higher in specification higher the precedence will be
expr returns [Expression e]
     : left=expr '*' right=expr {
         $e = new com.github.lipinskipawel.tutorial.expression.Multiplication($left.e, $right.e);
     }
     | left=expr '+' right=expr {
         $e = new com.github.lipinskipawel.tutorial.expression.Addition($left.e, $right.e);
     }
     | id=ID {
         int line = $id.getLine();
         int column = $id.getCharPositionInLine() + 1;
         if (!vars.contains($id.text)) {
             semanticErrors.add("Error: variable '" + $id.text + "' not declared (" + line + ", " + column + ")");
         }
         $e = new com.github.lipinskipawel.tutorial.expression.Variable($id.text);
     }
     | n=NUM { // while building the subtree of an expression node that contains NUM as the first child, we also build expression objects
         // can also be Integer.parseInt($n)
         $e = new com.github.lipinskipawel.tutorial.expression.Number($n.int);
     }
     ;

// tokens
ID : [a-z][a-zA-Z0-9_]* ; // identifier
NUM : '0' | '-'?[1-9][0-9]* ; // ? - means 0 or none
INT_TYPE : 'INT' ; // this is keyword
COMMENT : '--' ~[\r\n]* -> skip ; // ~ - means negation
WS : [ \t\n]* -> skip ; // white spaces
