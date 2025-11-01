package parser

import ast.Term
import ast.Term.IfZero
import lexer.Lexer.nextToken
import lexer.Token
import lexer.Token.*
import ast.Op.*
import Term.*

object Parser:

  // Parse une expression simple ou composite
  def parse(token: Token): Term =
    token match
      case NUMBER(value) => Number(value)
      case LPAR => parseCompositeExp(nextToken())
      case _ => throw new Exception(s"Unexpected token: $token")


  def parseCompositeExp(token2: Token): Term =
    token2 match
      case PLUS =>
        val exp1 = parse(nextToken())
        val exp2 = parse(nextToken())
        val token = nextToken()
        if token == RPAR then
          BinaryExp(Plus, exp1, exp2)
        else
          throw new Exception("Expected ')' but found: " + token)

      case MINUS =>
        val exp1 = parse(nextToken())
        val exp2 = parse(nextToken())
        val token = nextToken()
        if token == RPAR then
          BinaryExp(Minus, exp1, exp2)
        else
          throw new Exception("Expected ')' but found: " + token)

      case MULTIPLY =>
        val exp1 = parse(nextToken())
        val exp2 = parse(nextToken())
        val token = nextToken()
        if token == RPAR then
          BinaryExp(Times, exp1, exp2)
        else
          throw new Exception("Expected ')' but found: " + token)

      case DIV =>
        val exp1 = parse(nextToken())
        val exp2 = parse(nextToken())
        val token = nextToken()
        if token == RPAR then
          BinaryExp(Div, exp1, exp2)
        else
          throw new Exception("Expected ')' but found: " + token)

      case IFZ =>
        val exp1 = parse(nextToken())
        val exp2 = parse(nextToken())
        val exp3 = parse(nextToken())
        val token = nextToken()
        if token == RPAR then
          IfZero(exp1, exp2, exp3)
        else
          throw new Exception("Expected ')' but found: " + token)

      case _ =>
        throw new Exception("Unexpected token: " + token2)
