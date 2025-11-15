package parserANTLRv2

import ast.Term

import java.io.InputStream
import parserANTLRv2.{ASTVisitor, ConcreteParser}

object AbstractParser :
  def analyze(in: InputStream): Term =
    val concreteTree = ConcreteParser.analyze(in)
    val visitor = new ASTVisitor
    val term = visitor.visit(concreteTree).asInstanceOf[Term]
    println(s"ASR: $term")
    term
 
