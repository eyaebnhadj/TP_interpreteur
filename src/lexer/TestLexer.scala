package lexer

import java.io.{FileInputStream, InputStream}

@main
def main(args: String*): Unit =
  val in: InputStream =
    if args.isEmpty then
      System.in
    else
      FileInputStream(args(0))
  val lexer = new Lexer(in)
  val tokens = lexer.lex()
  println(tokens)
