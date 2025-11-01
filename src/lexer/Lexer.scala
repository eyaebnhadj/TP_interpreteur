package lexer

import lexer.Token.*

import java.io.InputStream

class Lexer(in: InputStream):
  private var currentChar: Int = -1
  nextChar()

  private def nextChar(): Unit =
    currentChar = in.read()

  def lex(): List[Token] =
    var tokens: List[Token] = List()
    var token = nextToken()
    while token != EOF do
      tokens = token :: tokens
      token = nextToken()
    end while
    tokens.reverse

  private def nextToken(): Token =
    currentChar match
      case -1 => in.close(); EOF
      case '0' => nextChar(); NUMBER(0)
      case '\n' | '\r' | '\t' | ' ' => nextChar(); nextToken()
      case '(' =>  nextChar(); LPAR
      case ')' => nextChar();RPAR
      case '+' => nextChar();PLUS
      case '-' =>  nextChar();MINUS
      case '*' => nextChar();MULTIPLY
      case '/' => nextChar();DIV
      case c if '0' <= c && c <= '9' =>
        val n = currentChar
        nextChar()
        number(n)
      case 'i' =>
        val buffer = new StringBuilder
        while currentChar != -1 && currentChar.toChar.isLetter do
          buffer.append(currentChar.toChar)
          nextChar()
        buffer.toString match
          case "ifz" => IFZ
          case other => throw new Exception(s"Unknown identifier: $other")


      case _ => throw new Exception(s"Unexpected character: ${currentChar.toChar}, ascii $currentChar")
    end match

  def number(n: Int): Token =
     var value = n - '0'
     while '0' <= currentChar & currentChar <= '9' do
       value = value * 10 + currentChar - '0'
       nextChar()
     NUMBER(value)


object Lexer:
  private var lexer: Option[Lexer] = None
  def apply(in: InputStream): Unit =
    lexer = Some(new Lexer(in))
  def nextToken(): Token = lexer.get.nextToken()