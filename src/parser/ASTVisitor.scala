package parserANTLRv2

import ast.*
import ast.Term.*

import scala.jdk.CollectionConverters.*
// modifier en fonction des labes dans calc.g4

class ASTVisitor[AST] extends PCFBaseVisitor[AST] :

  override def visitNumber(ctx: PCFParser.NumberContext): AST =
    Number(ctx.getText.toInt).asInstanceOf[AST]

  override def visitVar(ctx: PCFParser.VarContext): AST =      // analyseur syntaxique de la piste bleu
    Var(ctx.getText).asInstanceOf[AST]

  override def visitBinaryExp1(ctx: PCFParser.BinaryExp1Context): AST =
    val s = ctx.OP1().getText
    val op = Op.parse(s)
    // ctx.term is a Java list, it is translated in a Scala list
    // (initially, to an instance of Buffer, using a collection
    // converter, as Java lists are mutable)
    val concreteExps = ctx.term().asScala.toList
    val List(exp1, exp2) =
      for (concreteExp <- concreteExps) yield
        visit(concreteExp).asInstanceOf[Term]
    BinaryExp(op, exp1, exp2).asInstanceOf[AST]


  override def visitBinaryExp2(ctx: PCFParser.BinaryExp2Context): AST =
    val s = ctx.OP2().getText
    val op = Op.parse(s)
    // ctx.term is a Java list, it is translated in a Scala list
    // (initially, to an instance of Buffer, using a collection
    // converter, as Java lists are mutable)
    val concreteExps = ctx.term().asScala.toList
    val List(exp1, exp2) =
      for (concreteExp <- concreteExps) yield
        visit(concreteExp).asInstanceOf[Term]
    BinaryExp(op, exp1, exp2).asInstanceOf[AST]  

  override def visitIfZero(ctx: PCFParser.IfZeroContext): AST =
    val concreteExps = ctx.term.asScala.toList
    val List(exp1, exp2, exp3) =
      for (concreteExp <- concreteExps) yield
        visit(concreteExp).asInstanceOf[Term]
    IfZero(exp1, exp2, exp3).asInstanceOf[AST]

  override def visitLet(ctx: PCFParser.LetContext): AST =
    val name = ctx.ID().getText
    // ctx.term is a Java list, it is translated in a Scala list
    // (initially, to an instance of Buffer, using a collection
    // converter, as Java lists are mutable)
    val concreteExps = ctx.term().asScala.toList
    val List(exp1, exp2) =
      for (concreteExp <- concreteExps) yield
        visit(concreteExp).asInstanceOf[Term]
    Let(name, exp1, exp2).asInstanceOf[AST]
    
  override def visitParExp(ctx: PCFParser.ParExpContext): AST =
    val concreteExp = ctx.term
    val exp = visit(concreteExp).asInstanceOf[Term]
    exp.asInstanceOf[AST]

  override def visitFun(ctx: PCFParser.FunContext): AST =
    val param = ctx.ID().getText
    val body = visit(ctx.term()).asInstanceOf[Term]
    Fun(param, body).asInstanceOf[AST]

  override def visitApp(ctx: PCFParser.AppContext): AST =
    val concreteExps = ctx.term().asScala.toList
    val List(function, argument) =
      for (concreteExp <- concreteExps) yield
        visit(concreteExp).asInstanceOf[Term]
    App(function, argument).asInstanceOf[AST]

  override def visitFix(ctx: PCFParser.FixContext): AST =
    val f = ctx.ID().getText
    val body = visit(ctx.term()).asInstanceOf[Term]
    Fix(f, body).asInstanceOf[AST]
