package ast

import ast.Term
import ast.Term.*

// Arbre de syntaxe abstraite annoté (indices de De Bruijn)
enum ATerm:
case ALit(n: Int)
case AVar(index: Int, name: String) // index = distance dans l'environnement
case ALet(name: String, u: ATerm, v: ATerm)
case AIfZero(cond: ATerm, z: ATerm, nz: ATerm)
case ABinaryExp(op: Op, t1: ATerm, t2: ATerm)
case AFun(name: String, body: ATerm)
case AApp(t1: ATerm, t2: ATerm)
case AFix(name: String, body: ATerm) // Piste Noire

object ATerm:
  /**
   * Transforme un Term (avec noms) en ATerm (avec indices).
   * L'appel 1.indexOf(e) donne l'indice de l'élément e de la liste 1.
   */
  def annotate(t: Term): ATerm = annotate(t, List())

  private def annotate(t: Term, env: List[String]): ATerm = t match
  case Number(n) => ALit(n)

  case Var(name) =>
  val idx = env.indexOf(name)
  if (idx == -1) throw new Exception(s"Variable non définie: $name")
  AVar(idx, name)

  case Let(name, u, v) =>
  // Dans v, 'name' est ajouté en tête de l'environnement (indice 0)
  ALet(name, annotate(u, env), annotate(v, name :: env))

  case IfZero(cond, z, nz) =>
  AIfZero(annotate(cond, env), annotate(z, env), annotate(nz, env))

  case BinaryExp(op, t1, t2) =>
  ABinaryExp(op, annotate(t1, env), annotate(t2, env))

  case Fun(name, body) =>
  // Dans le corps de la fonction, le paramètre est à l'indice 0
  AFun(name, annotate(body, name :: env))

  case App(t1, t2) =>
  AApp(annotate(t1, env), annotate(t2, env))

  case Fix(name, body) =>
  // Pour la récursion, 'name' doit être visible dans 'body'
  AFix(name, annotate(body, name :: env))