package vm

import generator.Ins
import generator.Ins.*
import scala.annotation.tailrec

// Définition des valeurs possibles dans la VM
enum Value:
case IntVal(n: Int)
case Closure(code: List[Ins], env: List[Value])
case RecClosure(code: List[Ins], var env: List[Value]) // Pour la piste noire (mutable)

// L'environnement est simplement une liste de valeurs (correspondant aux indices De Bruijn)
type Env = List[Value]

// La pile peut contenir des valeurs ou des environnements sauvegardés (pour Let)
// Note: Dans cette implémentation simplifiée, on gère l'environnement courant 'e'
// séparément et on le met à jour via Let/EndLet/App.

object VM:
  import Value.*

  def execute(c: List[Ins]): Value =
    execute(IntVal(0), List(), List(), c)

  @tailrec
  def execute(a: Value, s: List[Value], e: Env, c: List[Ins]): Value = (c) match
  case Nil => a // Fin du code, on retourne l'accumulateur

  // --- Piste Verte ---
  case Ldi(n) :: rest =>
  execute(IntVal(n), s, e, rest)

  case Push :: rest =>
  execute(a, a :: s, e, rest) // Empile l'accumulateur

  case (op: (Add.type | Sub.type | Mul.type | Div.type)) :: rest =>
  val v2 = a match { case IntVal(n) => n; case _ => 0 } // Valeur courante (droite)
  val v1 = s.head match { case IntVal(n) => n; case _ => 0 } // Valeur dépilée (gauche)
  val res = op match
  case Add => v1 + v2
  case Sub => v1 - v2
  case Mul => v1 * v2
  case Div => v1 / v2
  execute(IntVal(res), s.tail, e, rest)

  case Test(thenC, elseC) :: rest =>
  val cond = a match { case IntVal(n) => n; case _ => 0 }
  // On exécute la branche, puis on continue avec 'rest'
  val branch = if (cond == 0) thenC else elseC
  execute(a, s, e, branch ::: rest)

  // --- Piste Bleue (Variables & Let) ---
  case Lds(idx) :: rest =>
  // Accès variable par indice De Bruijn dans l'environnement 'e'
  execute(e(idx), s, e, rest)

  case Let :: rest =>
  // 'Let' prend la valeur dans 'a' (résultat de u) et l'ajoute à l'env
  execute(a, s, a :: e, rest)

  case EndLet :: rest =>
  // Sortie du scope : on retire la dernière variable ajoutée
  execute(a, s, e.tail, rest)

  // --- Piste Rouge (Fonctions & App) ---
  case MkClos(code) :: rest =>
  // Crée une fermeture capturant l'environnement courant 'e'
  execute(Closure(code, e), s, e, rest)

  case App :: rest =>
  // 'a' contient l'argument. Sommet pile 's' contient la Closure.
  val arg = a
  val clos = s.head
  clos match
  case Closure(code, savedEnv) =>
  // Appel : On exécute 'code' avec l'env de la closure étendu par l'argument
  // IMPORTANT: Quand la fonction termine, on doit revenir à 'rest' avec l'ancien 'e'.
  // Ici, comme c'est une VM récursive terminale simple, on "inline" le code.
  // Pour gérer le retour correctement dans une VM linéaire, il faudrait une pile de retour (Dump).
  // Avec cette structure @tailrec sur liste d'instructions, le 'App' est terminal ou nécessite une astuce.
  // Astuce simple ici : concaténer le code de la closure avec 'rest', mais restaurer l'environnement est dur sans 'Dump'.
  // => Modification pour supporter l'appel de fonction : appel récursif de VM (non tailrec) ou gestion de continuation.
  // Pour respecter la structure fournie : On lance une sous-exécution pour la fonction.
  val res = execute(IntVal(0), List(), arg :: savedEnv, code)
  execute(res, s.tail, e, rest)

  case rc @ RecClosure(code, _) => // Piste Noire
  // Pour la récursion, on ajoute la closure elle-même à l'environnement
  val res = execute(IntVal(0), List(), arg :: (rc :: rc.env), code)
  execute(res, s.tail, e, rest)

  case _ => throw new Exception("App sur non-fonction")

  // --- Piste Noire (Fix) ---
  case FixClos(code) :: rest =>
  // Création d'une closure récursive cyclique
  val rc = RecClosure(code, e)
  // Pas besoin de plus, la structure RecClosure gère la cyclicité logique au moment de l'App
  // (On triche un peu sur la mutabilité ou on l'interprète au moment du App)
  execute(rc, s, e, rest)

  case _ => throw new Exception(s"Instruction inconnue ou état invalide")