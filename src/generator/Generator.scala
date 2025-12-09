package generator
import ast.{Op, Term}
import Term.*
import Op.*
import Ins.*

type Code = List[Ins]

object Generator:
  def gen (term: Term): Code = term match
  case Number(n) => List(Ldi(n))

  case BinaryExp(op, u, v) =>
  val c_u = gen(u)
  val c_v = gen(v)
  c_u ::: (Push :: c_v) ::: List(gen_op(op))

  // Code MANQUANT à ajouter pour la conditionnelle
  case IfZero(cond, z, nz) =>
  gen(cond) ::: List(Test(gen(z), gen(nz)))

  // Pour les autres cas non supportés par la VM piste verte (Var, Let, Fun...),
  // vous pouvez laisser le compilateur lever une erreur ou mettre ???
  case _ => throw new Exception("Construction non supportée par le générateur piste verte")

  def gen_op(op: Op) = op match
  case Plus => Add
  case Minus => Sub
  case Times => Mul
  case Divide => Div