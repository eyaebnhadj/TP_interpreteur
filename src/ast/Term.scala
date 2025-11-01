package ast

enum Term:
  case Number(value: Int)
  case Var(name:String)
  case Let(name:String, U:Term, V:Term)  // let name= U in V
  case IfZero(cond: Term, zBranch: Term, nzBranch: Term)
  case BinaryExp(op: Op, exp1: Term, exp2: Term)
  case Fun(name: String, t: Term)
  case App(t: Term, u: Term)
  case Fix(f: String, body:Term)

enum Op:
  case Plus
  case Minus
  case Times
  case Div

object Op:
  def parse(s: String): Op =
    s match
      case "+" => Plus
      case "-" => Minus
      case "*" => Times
      case "/" => Div
      case _ => ???


