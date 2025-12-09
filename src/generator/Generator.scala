package generator

import ast.{Op, Term}
import ast.ATerm
import ast.ATerm.*
import ast.Op.*
import Ins.*

type Code = List[Ins]

object Generator:
  def gen(term: Term): Code =
  // Étape 1 : Calcul des indices de De Bruijn
  val aterm = ATerm.annotate(term)
  // Étape 2 : Génération du code
  genAnnotated(aterm)

  def genAnnotated(term: ATerm): Code = term match
  case ALit(n) => List(Ldi(n))

  case ABinaryExp(op, u, v) =>
  genAnnotated(u) ::: (Push :: genAnnotated(v)) ::: List(gen_op(op))

  case AIfZero(cond, z, nz) =>
  genAnnotated(cond) ::: List(Test(genAnnotated(z), genAnnotated(nz)))

  // Piste Bleue
  case AVar(idx, _) =>
  List(Lds(idx)) // On charge la variable à l'indice calculé

  case ALet(_, u, v) =>
  // u est évalué, le résultat est sur l'accumulateur.
  // 'Let' transfère l'accumulateur vers l'environnement.
  genAnnotated(u) ::: (Let :: genAnnotated(v)) ::: List(EndLet)

  // Piste Rouge
  case AFun(_, body) =>
  List(MkClos(genAnnotated(body)))

  case AApp(t1, t2) =>
  // On prépare la fonction, on la Push, on prépare l'argument, puis App
  genAnnotated(t1) ::: (Push :: genAnnotated(t2)) ::: List(App)

  // Piste Noire
  case AFix(_, body) =>
  List(FixClos(genAnnotated(body)))

  def gen_op(op: Op) = op match
  case Plus => Add
  case Minus => Sub
  case Times => Mul
  case Divide => Div