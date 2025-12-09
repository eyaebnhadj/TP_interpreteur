package pcf

import evaluator.Evaluator
import typer.{Typer, Type}
import parserANTLRv2.AbstractParser
import ast.Term
import generator.{Generator, Code, Ins}
import vm.VM
import java.io.{FileInputStream, InputStream}

object PCF:
  def main(args: Array[String]): Unit =
  // Récupération du fichier ou stdin
  val in: InputStream =
    if args.isEmpty || (args.length == 1 && args(0) == "-i") then System.in
    else FileInputStream(if args(0) == "-i" then args(1) else args(0))

  if args.contains("-i") then
    println(s"==> ${interpret(in)}")
  else
  // Mode compilation
  val term = AbstractParser.analyze(in)
  val code = compile(term)
  println(s"Code généré: $code")
  // Exécution VM pour vérifier le résultat
  println(s"Résultat VM: ${VM.execute(code)}")

  def analyze(in: InputStream): (Term, Type) =
  val term = AbstractParser.analyze(in)
  (term, Typer.eval(term, Map()))

  def interpret(in: InputStream): String =
  val (term, typ) = analyze(in)
  val value = Evaluator.eval(term, Map())
  s"$value :: $typ"

  def compile(term: Term): Code =
  val code = Generator.gen(term)
  // Vérification de cohérence (Section 2)
  if (check(term, code)) code
  else throw new Exception("Implementation Error: VM result != Evaluator result")

  def check(term: Term, code: List[Ins]): Boolean =
    try
  val evalVal = Evaluator.eval(term, Map())
  val vmVal = VM.execute(code)
  // Comparaison simple via toString pour éviter les problèmes de structure interne des closures
  val res = evalVal.toString == vmVal.toString
  if (!res) println(s"Check failed: Eval=$evalVal vs VM=$vmVal")
  res
  catch
  case e: Exception =>
    println(s"Check crashed: ${e.getMessage}")
  false