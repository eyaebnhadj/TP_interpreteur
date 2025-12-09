package generator

import ast.{Op, Term}
import ast.ATerm
import ast.ATerm.*
import ast.Op.*
import Ins.*

type Code = List[Ins]

object Generator:
  // Point d'entrée principal qui annote d'abord le terme
  def gen(term: Term): Code =
  val aterm = ATerm.annotate(term)
  genAnnotated(aterm)

  def genAnnotated(term: ATerm): Code = term match
  case ALit(n) => List(Ldi(n))

  case ABinaryExp(op, u, v) =>
  genAnnotated(u) ::: (Push :: genAnnotated(v)) ::: List(gen_op(op))

  case AIfZero(cond, z, nz) =>
  genAnnotated(cond) ::: List(Test(genAnnotated(z), genAnnotated(nz)))

  // Piste Bleue : Variables et Let
  case AVar(idx, _) =>
  List(Lds(idx)) // Charge la variable à l'indice de De Bruijn idx

  case ALet(_, u, v) =>
  // Évalue u, puis Let l'ajoute à l'env, on évalue v, puis EndLet nettoie
  genAnnotated(u) ::: (Let :: genAnnotated(v)) ::: List(EndLet)

  // Piste Rouge : Fonctions et App
  case AFun(_, body) =>
  // Le corps de la fonction est compilé et encapsulé dans MkClos
  // Ret n'est pas explicite car la VM s'arrête à la fin de la liste d'instructions
  List(MkClos(genAnnotated(body)))

  case AApp(t1, t2) =>
  // Évalue la fonction (Closure dans l'acc), Push (sauvegarde), Évalue arg (dans acc), App
  genAnnotated(t1) ::: (Push :: genAnnotated(t2)) ::: List(App)

  // Piste Noire : Fix
  case AFix(_, body) =>
  // Similaire à Fun mais crée une fermeture récursive
  List(FixClos(genAnnotated(body)))

  def gen_op(op: Op) = op match
  case Plus => Add
  case Minus => Sub
  case Times => Mul
  case Divide => Div