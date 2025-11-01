package parserANTLR

import scala.jdk.CollectionConverters.*
import ast.*
import Term.*


class ASTVisitor[AST] extends PCFBaseVisitor[AST] :

  override def visitNumber(ctx: PCFParser.NumberContext): AST =
    Number(ctx.getText.toInt).asInstanceOf[AST]

  override def visitBinaryTerm(ctx: PCFParser.BinaryTermContext): AST =
    val s = ctx.OP().getText
    val op = Op.parse(s)
    // ctx.term is a Java list, it is translated in a Scala list
    // (initially, to an instance of Buffer, using a collection
    // converter, as Java lists are mutable)
    val concreteTerms = ctx.term().asScala.toList
    val List(exp1, exp2) =
      for (concreteTerm <- concreteTerms) yield
        visit(concreteTerm).asInstanceOf[Term]
    BinaryExp(op, exp1, exp2).asInstanceOf[AST]

  override def visitIfZero(ctx: PCFParser.IfZeroContext): AST =
    val concreteTerms = ctx.term.asScala.toList
    val List(exp1, exp2, exp3) =
      for (concreteTerm <- concreteTerms) yield
        visit(concreteTerm).asInstanceOf[Term]
    IfZero(exp1, exp2, exp3).asInstanceOf[AST]
