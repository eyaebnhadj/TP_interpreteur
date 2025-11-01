package typer

import ast.Term
import ast.Term.*
import ast.Op.*

class TypingException(msg: String) extends Exception(msg)
