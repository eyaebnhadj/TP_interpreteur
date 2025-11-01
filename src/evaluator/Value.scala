
package evaluator;

import ast.Term
import evaluator.Evaluator.Env

enum Value:
  case IntVal(n: Int)
  case Closure(name: String, t: Term, e: Env)

final case class IceCube(x: String, t: Term, e: Map[String, Value | IceCube])
