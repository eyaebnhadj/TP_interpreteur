grammar PCF;

term: '(' term ')'                             #ParExp
   | NUMBER                                    #Number
   | ID                                        #Var
   | term term                                 #App
   | term OP1 term                             #BinaryExp1
   | term OP2 term                             #BinaryExp2
   | 'ifz' term 'then' term 'else' term        #IfZero
   | 'let' ID '=' term 'in' term               #Let
   | 'fun' ID '->' term                        #Fun
   | 'fix' ID term                             #Fix
   ;

NUMBER: '0' | [1-9][0-9]* ;
ID: [a-z][a-z0-9]*;
OP1: '*' | '/' ;
OP2: '+' | '-'  ;
WS: ('\n' | '\r' | '\t' | ' ') -> skip;

