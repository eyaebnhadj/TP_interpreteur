package typer

import ast.Term
import ast.Term.*

object Typer :
  type Env = Map[String, Type]
  def eval(t: Term, e: Env): Type = t match
    case Number(_) => INT
    case BinaryExp(op, exp1, exp2) =>
      val t1 = eval(exp1, e)
      val t2 = eval(exp2, e)
      if !(t1 === INT) then
        throw TypingException(s"Expected int, got $t1 in $exp1")
      if !(t2 === INT) then
        throw TypingException(s"Expected int, got $t2 in $exp2")

      INT
    case IfZero(cond, zBranch, nzBranch) =>
      val tCond = eval(cond, e)
      val tZero = eval(zBranch, e)
      val tNonZero = eval(nzBranch, e)
      if !(tCond === INT) then
        throw TypingException(s"Expected int in condition, got $tCond")
      if !(tZero === tNonZero) then
        throw TypingException(s"Branches have different types: $tZero and $tNonZero")

      tZero
    case Var(name) =>
        e.getOrElse(name, throw TypingException(s"Variable $name not defined"))

      // Let : let x = u in v
    case Let(name, u, v) =>
      val tu = eval(u, e)
      val newEnv = e + (name -> tu)
      eval(v, newEnv)

    case Fun(param, body) =>
      val tParam = unify.TVar()              
      val newEnv = e + (param -> tParam)     
      val tBody = eval(body, newEnv)         
      FUNCTION(tParam, tBody)                

    case App(t1, t2) =>
        val tFunc = eval(t1, e)                
        val tArg = eval(t2, e)                 
        val tResult = unify.TVar()             
        val expectedFunc = FUNCTION(tArg, tResult)
        if !(tFunc === expectedFunc) then
          throw TypingException(s"Type mismatch: expected function $expectedFunc, got $tFunc")

        tResult
    case _ => ???