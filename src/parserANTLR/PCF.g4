grammar PCF;

term: NUMBER                                 # Number
   | '(' OP term term ')'                     # BinaryExp
   | '(' 'ifz' term term term ')'              # IfZero
   ;

NUMBER: '0' | [1-9][0-9]* ;
OP: '+' | '-' | '*' | '/' ;
WS: ('\n' | '\r' | '\t' | ' ') -> skip;
