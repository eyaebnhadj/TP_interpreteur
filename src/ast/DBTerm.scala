package ast

// De Bruijn annotated terms
enum DBTerm:
  case DNumber(value: Int)
  case DBound(index: Int)            // variable as De Bruijn index
  case DLet(u: DBTerm, v: DBTerm)
  case DIfZero(cond: DBTerm, zBranch: DBTerm, nzBranch: DBTerm)
  case DBinaryExp(op: Op, a: DBTerm, b: DBTerm)
  case DFun(body: DBTerm)
  case DApp(t: DBTerm, u: DBTerm)
  case DFix(body: DBTerm)

object DBTerm:
  // annotate a Term using De Bruijn indices
  def annotate(t: Term): DBTerm = annotate(t, Nil)

  private def idxOf(name: String, ctx: List[String]): Int =
    ctx.indexOf(name) match
      case -1 => throw new Exception(s"Unbound variable $name during annotation")
      case i  => i

  private def annotate(t: Term, ctx: List[String]): DBTerm = t match
    case Term.Number(n) => DNumber(n)
    case Term.Var(x)    => DBound(idxOf(x, ctx))
    case Term.Let(name, u, v) =>
      val du = annotate(u, ctx)
      val dv = annotate(v, name :: ctx)
      DLet(du, dv)
    case Term.IfZero(cond, z, nz) =>
      DIfZero(annotate(cond, ctx), annotate(z, ctx), annotate(nz, ctx))
    case Term.BinaryExp(op, a, b) => DBinaryExp(op, annotate(a, ctx), annotate(b, ctx))
    case Term.Fun(name, body) => DFun(annotate(body, name :: ctx))
    case Term.App(t1, t2) => DApp(annotate(t1, ctx), annotate(t2, ctx))
    case Term.Fix(f, body) => DFix(annotate(body, f :: ctx))
