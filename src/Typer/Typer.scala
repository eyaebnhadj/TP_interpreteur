package Typer

package typer
import ast.Term
import Term.*
import unify.TVar

object Typer :
  type Env = Map[String, Type]
  def eval(t:Term, e: Env) :Type = t match
    case Number(_) => INT
    case _ => ???