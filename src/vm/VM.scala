package vm

<<<<<<< HEAD
import ast.Term
import ast.Term.*
import evaluator.Value.*
import evaluator.Evaluator

object VM:

  enum Instr:
    case PUSH(n: Int)
    case ADD
    case SUB
    case MUL
    case DIV
    case ACCESS(i: Int)       // De Bruijn index access
    case CLOS(code: List[Instr])
    case CLOS_REC(code: List[Instr])
    case APPLY
    case IFZERO(thenCode: List[Instr], elseCode: List[Instr])
    case RET
    case STOP

  sealed trait RV
  case class IntV(n: Int) extends RV
  case class ClosureV(code: List[Instr], var env: List[RV]) extends RV

  // Compile a Term to instructions using De Bruijn indices (ctx: List of bound names)
  def compile(t: Term): List[Instr] = compile(t, Nil)

  private def idxOf(name: String, ctx: List[String]): Int =
    ctx.indexOf(name) match
      case -1 => throw new Exception(s"Unbound variable $name during compilation")
      case i  => i

  private def compile(t: Term, ctx: List[String]): List[Instr] = t match
    case Number(n) => List(Instr.PUSH(n))
    case Var(x)    => List(Instr.ACCESS(idxOf(x, ctx)))
    case BinaryExp(op, a, b) =>
      val ca = compile(a, ctx)
      val cb = compile(b, ctx)
      val opi = op match
        case ast.Op.Plus  => Instr.ADD
        case ast.Op.Minus => Instr.SUB
        case ast.Op.Times => Instr.MUL
        case ast.Op.Div   => Instr.DIV
      ca ++ cb ++ List(opi)
    case IfZero(cond, z, nz) =>
      val c = compile(cond, ctx)
      val cz = compile(z, ctx) ++ List(Instr.RET)
      val cnz = compile(nz, ctx) ++ List(Instr.RET)
      c ++ List(Instr.IFZERO(cz, cnz))
    case Let(name, u, v) =>
      // transform let x = u in v  ==>  (fun x -> v) u
      compile(App(Fun(name, v), u), ctx)
    case Fun(name, body) =>
      val bodyCode = compile(body, name :: ctx) ++ List(Instr.RET)
      List(Instr.CLOS(bodyCode))
    case App(t, u) =>
      compile(t, ctx) ++ compile(u, ctx) ++ List(Instr.APPLY)
    case Fix(f, body) =>
      // create a recursive closure: CLOS_REC will create a closure whose env contains itself
      val bodyCode = compile(body, f :: ctx) ++ List(Instr.RET)
      List(Instr.CLOS_REC(bodyCode))

  // Run a program (list of instructions) and return an RV
  def run(program: List[Instr]): RV =
    // execution stacks
    var stack: List[RV] = Nil
    var contStack: List[(List[Instr], List[RV])] = Nil // (code, env)

    def exec(code: List[Instr], env: List[RV]): RV =
      var pc = code
      var currentEnv = env
      while pc.nonEmpty do
        pc.head match
          case Instr.PUSH(n) =>
            stack = IntV(n) :: stack
            pc = pc.tail
          case Instr.ADD =>
            val a = stack.head.asInstanceOf[IntV]; stack = stack.tail
            val b = stack.head.asInstanceOf[IntV]; stack = stack.tail
            stack = IntV(b.n + a.n) :: stack
            pc = pc.tail
          case Instr.SUB =>
            val a = stack.head.asInstanceOf[IntV]; stack = stack.tail
            val b = stack.head.asInstanceOf[IntV]; stack = stack.tail
            stack = IntV(b.n - a.n) :: stack
            pc = pc.tail
          case Instr.MUL =>
            val a = stack.head.asInstanceOf[IntV]; stack = stack.tail
            val b = stack.head.asInstanceOf[IntV]; stack = stack.tail
            stack = IntV(b.n * a.n) :: stack
            pc = pc.tail
          case Instr.DIV =>
            val a = stack.head.asInstanceOf[IntV]; stack = stack.tail
            val b = stack.head.asInstanceOf[IntV]; stack = stack.tail
            stack = IntV(b.n / a.n) :: stack
            pc = pc.tail
          case Instr.ACCESS(i) =>
            // De Bruijn index: 0 => nearest binder
            if i < currentEnv.length then
              stack = currentEnv(i) :: stack
              pc = pc.tail
            else
              throw new Exception(s"ACCESS out of bounds: $i env=${currentEnv}")
          case Instr.CLOS(codeC) =>
            val clo = ClosureV(codeC, currentEnv)
            stack = clo :: stack
            pc = pc.tail
          case Instr.CLOS_REC(codeC) =>
            // create closure and insert itself as first element of its env
            val clo = ClosureV(codeC, Nil)
            clo.env = clo :: currentEnv
            stack = clo :: stack
            pc = pc.tail
          case Instr.APPLY =>
            val arg = stack.head; stack = stack.tail
            val fv = stack.head; stack = stack.tail
            fv match
              case ClosureV(codeC, envC) =>
                // save continuation
                contStack = (pc.tail, currentEnv) :: contStack
                // start executing function body with new env (arg :: envC)
                pc = codeC
                currentEnv = arg :: envC
              case _ => throw new Exception(s"Apply on non-function $fv")
          case Instr.IFZERO(thenCode, elseCode) =>
            val v = stack.head.asInstanceOf[IntV]; stack = stack.tail
            if v.n == 0 then
              // save continuation and run thenCode
              contStack = (pc.tail, currentEnv) :: contStack
              pc = thenCode
            else
              contStack = (pc.tail, currentEnv) :: contStack
              pc = elseCode
          case Instr.RET =>
            // return: the top of stack is the return value
            val retv = stack.head; stack = stack.tail
            contStack match
              case Nil => return retv
              case (savedCode, savedEnv) :: rest =>
                pc = savedCode
                currentEnv = savedEnv
                contStack = rest
                stack = retv :: stack
          case Instr.STOP =>
            return stack.head
      // if we exit loop without RET, return top of stack
      stack.head

    exec(program, Nil)
=======
import generator.Ins
import Ins.*
import Value.*

import scala.annotation.tailrec

enum Value:
  case IntVal(n: Int)

type Env = List[Value]
case class VMState(a: Value, s:List[Value|Env], e: Env, c: List[Ins])

object VM:
  def execute(c: List[Ins]): Value =
    execute(IntVal(0), List(), List(), c)

  @tailrec
  def execute(a: Value, s:List[Value|Env], e: Env, c: List[Ins]): Value = (a, s, e, c) match
    case (_, _, _, List()) => a
    case (_, _, _, Push::c) => execute(a, a::s, e, c)
    case (_, _, _, Ldi(n)::c) => execute(IntVal(n), s, e, c)
    case (IntVal(n), IntVal(m)::s, _, Add::c) => execute(IntVal(m+n), s, e, c)
    case (IntVal(n), IntVal(m)::s, _, Sub::c) => execute(IntVal(m-n), s, e, c)
    case (IntVal(n), IntVal(m)::s, _, Mul::c) => execute(IntVal(m*n), s, e, c)
    case (IntVal(n), IntVal(m)::s, _, Div::c) => execute(IntVal(m/n), s, e, c)
    case (IntVal(0), _, _, Test(i, _)::c) => execute(a, s, e, i:::c)
    case (_, _, _, Test(_, j)::c) => execute(a, s, e, j:::c)
    case state => throw Exception(s"unexpected VM state $state")

@main
def test(): Unit =
  println(VM.execute(List(Ldi(1), Push, Ldi(2), Add, Test(List(Ldi(1)),List(Ldi(2))))))



>>>>>>> f2e33f815c8ac54690e9689af895a3985702606d
