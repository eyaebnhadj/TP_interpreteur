package pcf

import evaluator.Evaluator
import evaluator.Evaluator.eval
//import lexer.Lexer
//import parser.Parser
//import parser.AbstractParser
import typer.Typer
//import typer.TypingException
import parserANTLRv2.AbstractParser
import java.io.{FileInputStream, InputStream}

object PCF:
  def main(args: Array[String]): Unit =
    val in: InputStream =
      if args.isEmpty then
        System.in
      else
        FileInputStream(args(0))
//    Lexer(in)
//    val exp = Parser.parse(Lexer.nextToken())
//    val token = Lexer.nextToken()
//    if token != lexer.Token.EOF then
//      throw new Exception(s"Unexpected token $token after parsing complete expression $exp")
//

    val term = AbstractParser.analyze(in)
    println(s"==> ${eval(term, Map())}")
    val typ = Typer.eval(term, Map())
    println(s"Type: $typ")
    val result = Evaluator.eval(term, Map()) 
    println(s"==> $result: $typ") 


