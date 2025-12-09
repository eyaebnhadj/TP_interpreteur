package vm

import generator.Ins
import generator.Ins.*
import scala.annotation.tailrec

enum Value:
case IntVal(n: Int)
case Closure(code: List[Ins], env: List[Value])
case RecClosure(code: List[Ins], var env: List[Value]) // Mutable pour le cycle

type Env = List[Value]
// La pile peut contenir des valeurs OU des environnements sauvegardés
type StackElt = Value | Env

object VM:
  import Value.*

  def execute(c: List[Ins]): Value =
    execute(IntVal(0), List(), List(), c)

  @tailrec
  def execute(a: Value, s: List[StackElt], e: Env, c: List[Ins]): Value = c match
  case Nil => a

  // --- Opérations de base ---
  case Ldi(n) :: rest =>
  execute(IntVal(n), s, e, rest)

  case Push :: rest =>
  execute(a, a :: s, e, rest)

  case (op: (Add.type | Sub.type | Mul.type | Div.type)) :: rest =>
  val v2 = a match { case IntVal(n) => n; case _ => 0 }
  val v1 = s.head.asInstanceOf[Value] match { case IntVal(n) => n; case _ => 0 }
  val res = op match
  case Add => v1 + v2
  case Sub => v1 - v2
  case Mul => v1 * v2
  case Div => v1 / v2
  execute(IntVal(res), s.tail, e, rest)

  case Test(thenC, elseC) :: rest =>
  val cond = a match { case IntVal(n) => n; case _ => 0 }
  val branch = if (cond == 0) thenC else elseC
  execute(a, s, e, branch ::: rest)

  // --- Piste Bleue (Environnement) ---
  case Lds(idx) :: rest =>
  // On va chercher la valeur dans l'environnement à l'indice idx
  execute(e(idx), s, e, rest)

  case Let :: rest =>
  // On sauvegarde l'environnement courant sur la pile
  // On ajoute la valeur 'a' (résultat du let binding) au nouvel environnement
  execute(a, e :: s, a :: e, rest)

  case EndLet :: rest =>
  // On restaure l'ancien environnement depuis la pile
  val oldEnv = s.head.asInstanceOf[Env]
  execute(a, s.tail, oldEnv, rest)

  // --- Piste Rouge (Fonctions) ---
  case MkClos(code) :: rest =>
  execute(Closure(code, e), s, e, rest)

  case App :: rest =>
  // 'a' est l'argument. Sommet de pile est la closure.
  val arg = a
  val clos = s.head.asInstanceOf[Value]
  clos match
  case Closure(code, savedEnv) =>
  // On sauvegarde l'env courant et le code de retour sur la pile (simplification ici : on inline)
  // Note : Pour une vraie récursivité terminale, il faudrait une pile de retour (Dump).
  // Ici, on simule l'appel en réinitialisant une sous-exécution ou en gérant le contexte.
  // L'approche "simple" demandée dans ce TP implique souvent de changer d'environnement courant :
  // Env de la fonction = savedEnv + arg.
  // Il faut aussi sauvegarder l'env de l'appelant.
  // Cependant, avec cette structure @tailrec plate, gérer le retour "EndApp" est complexe sans instruction dédiée.
  // Solution souvent attendue : Utiliser une instruction de retour implicite ou relancer execute.

  val retVal = execute(IntVal(0), List(), arg :: savedEnv, code)
  execute(retVal, s.tail, e, rest)

  case rc @ RecClosure(code, _) =>
  // Piste Noire : On injecte la closure elle-même dans son environnement
  val retVal = execute(IntVal(0), List(), arg :: (rc :: rc.env), code)
  execute(retVal, s.tail, e, rest)

  case _ => throw new Exception("Erreur : Application sur non-fonction")

  // --- Piste Noire (Fix) ---
  case FixClos(code) :: rest =>
  val rc = RecClosure(code, e)
  execute(rc, s, e, rest)