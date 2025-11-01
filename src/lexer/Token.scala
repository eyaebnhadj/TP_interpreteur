package lexer

enum Token:
  case NUMBER(value: Int)
  case LPAR
  case RPAR
  case PLUS
  case MINUS
  case MULTIPLY
  case DIV
  case IFZ
  case IDENT(name: String)          // [a-z][a-z0-9]*
  case EQ                           // =
  case ARROW                        // ->
  case THEN                         // then
  case ELSE                         // else
  case LET                          // let
  case IN                           // in
  case FUN                          // fun
  case FIX                          // fix
  case EOF
