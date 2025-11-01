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

  // Vérifier que t1 est int
      if !(t1 === INT) then
        throw TypingException(s"Expected int, got $t1 in $exp1")

      // Vérifier que t2 est int
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
    case _ => ???