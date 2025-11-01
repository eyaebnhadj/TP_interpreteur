package parser

import lexer.Lexer

import java.io.{FileInputStream, InputStream}

@main
def main(args: String*): Unit =
  val in: InputStream =
    if args.isEmpty then
      System.in
    else
      FileInputStream(args(0))
  Lexer(in)
  val exp = Parser.parse(Lexer.nextToken())
  val token = Lexer.nextToken()
  if token != lexer.Token.EOF then
    throw new Exception(s"Unexpected token $token after parsing complete expression $exp")
  else println(exp)