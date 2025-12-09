package test

import java.io.FileInputStream
import parserANTLRv2.AbstractParser
import evaluator.Evaluator
import vm.VM
import ast.DBTerm
import transform.FixToZ

@main
def testVM(): Unit =
  // small demonstration: read a file provided as first arg or use stdin
  val args = scala.util.Properties.envOrElse("ARGS", "").split(' ').filter(_.nonEmpty)
  val in =
    if args.isEmpty then System.in
    else FileInputStream(args(0))

  val term = AbstractParser.analyze(in)
  println(s"Term: $term")

  // annotate with De Bruijn indices (piste bleue)
  val db = DBTerm.annotate(term)
  println(s"De Bruijn annotated term: $db")

  // Piste noire: transform Fix into an applicative fixed-point (Z combinator)
  val transformed = FixToZ.transform(term)
  println(s"Transformed (Fix->Z) term: $transformed")

  // evaluate with interpreter (original term)
  val v1 = Evaluator.eval(term, Map.empty)
  println(s"Evaluator result: $v1")

  // compile and run on VM (using transformed term)
  val code = VM.compile(transformed)
  println(s"Compiled code: $code")
  val v2 = VM.run(code)
  println(s"VM result: $v2")
