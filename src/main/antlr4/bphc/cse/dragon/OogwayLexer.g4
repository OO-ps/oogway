lexer grammar OogwayLexer;

@lexer::header {
import java.util.*;
}

@lexer::members {
}

KEYWORD_MAIN: 'main';
KEYWORD_RETURN: 'noret' | 'ret';
KEYWORD_FUNC: 'func';
KEYWORD_VAR: 'var';
KEYWORD_WHILE: 'while';
KEYWORD_IF: 'if';
KEYWORD_ELSE: 'else';
KEYWORD_INPUT: 'input';
KEYWORD_OUTPUT: 'output';
KEYWORD_INT: 'int';
KEYWORD_STRING: 'string';

STRING: '"' .*? '"';

L_PAREN: '(';
R_PAREN: ')';
L_CURLY: '{';
R_CURLY: '}';
L_BRACK: '[';
R_BRACK: ']';

MUL: '*';
DIV: '/';

PLUS: '+';
MINUS: '-';

GT: '>';
LT: '<';
GTE: '>=';
LTE: '<=';

EQUAL: '==';
NE: '!=';

AND : '&&' ;
OR :  '||' ;
NOT : '!' ;

ASSIGN: '=';
SEMI: ';';
COMMA: ',';

INT: DIGIT+;
FLOAT: INT '.' INT;

ID: LETTER (LETTER_OR_DIGIT)*;

NEWLINE:'\r'? '\n' -> skip ;
WS : [ \t\n\r]+ -> skip ;


fragment LETTER: [A-Za-z_$];
fragment LETTER_OR_DIGIT: [0-9A-Za-z_$];
fragment DIGIT: [0-9];
