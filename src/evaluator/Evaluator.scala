package evaluator

import ast.Term
import ast.Term.*
import ast.Op.*
import evaluator.Value.*
import evaluator.*

object Evaluator :
  type Env = Map[String, Value | IceCube]
  given int2Val: Conversion[Int, Value] = n => IntVal(n)
  given val2Int: Conversion[Value, Int] = {
    case IntVal(n) => n
    case Closure(_, _, _) =>
      throw EvaluationException("Expected an integer, got a function")
  }
  def eval(exp: Term, e: Env): Value = exp match  
    case Number(value) => value
    case Var(name) =>
      e.getOrElse(name, throw EvaluationException(s"variable $name not defined")) match
          case v: Value => v
          case ice: IceCube => eval(ice.t, e + (ice.x -> ice))
    case IfZero(cond, zBranch, nzBranch) => 
      if eval(cond, e) == IntVal(0) then eval(zBranch, e) else eval(nzBranch, e)

    case BinaryExp(op, exp1, exp2) =>
      val v1 = eval(exp1, e)
      val v2 = eval(exp2, e)
      op match {
        case Plus => v1 + v2
        case Minus => v1 - v2
        case Times => v1 * v2
        case Div => v1 / v2
      }
    case Let(name, u, v)  =>
      val v1 = eval(u, e)
      val newEnv = e + (name -> v1)
      eval(v , newEnv)

    case Fun(name,u) =>
      Closure(name, u, e)

    case App(t, u) =>
        val w = eval(u, e)
        val f = eval(t, e)
        f match
          case Closure(name, u1, e1) =>
            eval(u1, e1 + (name -> w))
          case _ =>
            throw EvaluationException(s"Expected a function, got $f")

    case Fix(f, body) =>
      val ice = IceCube(f, body, e)
      eval(body, e + (f -> ice))
        
