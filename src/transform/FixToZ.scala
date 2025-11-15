package transform

import ast.Term
import ast.Term.*

object FixToZ:
  /**
   * Z combinator (applicative-order fixed-point combinator) written in our Term AST.
   * Z = λf. (λx. f (λv. x x v)) (λx. f (λv. x x v))
   */
  def Zcomb(): Term =
    val inner = Fun("x",
      App(Var("f"), Fun("v", App(App(Var("x"), Var("x")), Var("v")))))
    Fun("f", App(inner, inner))

  /** Replace Fix(f, body) with App(Z, Fun(f, body)) recursively. */
  def transform(t: Term): Term = t match
    case Number(n) => Number(n)
    case Var(x) => Var(x)
    case Let(name, u, v) => Let(name, transform(u), transform(v))
    case IfZero(cond, z, nz) => IfZero(transform(cond), transform(z), transform(nz))
    case BinaryExp(op, a, b) => BinaryExp(op, transform(a), transform(b))
    case Fun(name, body) => Fun(name, transform(body))
    case App(t1, t2) => App(transform(t1), transform(t2))
    case Fix(f, body) =>
      // replace Fix(f, body) with App(Z, Fun(f, body))
      App(Zcomb(), Fun(f, transform(body)))
