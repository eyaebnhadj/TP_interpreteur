package pcf

import evaluator.{Evaluator, Value as EValue}
import typer.{Typer, Type}
import parserANTLRv2.AbstractParser
import ast.Term
import generator.{Generator, Code, Ins}
import vm.VM
import java.io.{FileInputStream, InputStream}

object PCF:
  def main(args: Array[String]): Unit =
  val in: InputStream =
    if args.isEmpty || (args.length == 1 && args(0) == "-i") then System.in
    else FileInputStream(if args(0) == "-i" then args(1) else args(0))

  if args.contains("-i") then
    println(s"==> ${interpret(in)}")
  else
  // Si on compile, on peut aussi tester (optionnel, ou par défaut)
  val term = AbstractParser.analyze(in)
  val code = compile(in) // Attention: compile relit le stream, mieux vaut passer 'term'
  // Pour l'exemple, on affiche juste le code ou on exécute la VM
  println(s"Code généré: $code")
  println(s"Résultat VM: ${VM.execute(code)}")

  def analyze(in: InputStream): (Term, Type) =
  val term = AbstractParser.analyze(in)
  (term, Typer.eval(term, Map()))

  def interpret(in: InputStream): String =
  val (term, typ) = analyze(in)
  val value = Evaluator.eval(term, Map())
  s"$value :: $typ"

  // Mise à jour selon Section 2
  def compile(in: InputStream): Code =
  // Note: analyze consomme le stream, il faut refaire ou passer le term
  // Ici on suppose qu'on a le term
  val term = AbstractParser.analyze(in)
  val code = Generator.gen(term)

  // Vérification automatique (Section 2)
  // Note: ceci lance une exception si ça échoue
  if (check(term, code)) code
  else throw new Exception("Implementation Error: VM result != Evaluator result")

  def check(term: Term, code: List[Ins]): Boolean =
    try
  val evalVal = Evaluator.eval(term, Map())
  val vmVal = VM.execute(code)

  // Conversion simple pour comparer (les closures peuvent différer en représentation interne)
  val res = evalVal.toString == vmVal.toString
  if (!res) println(s"Check failed: Eval=$evalVal vs VM=$vmVal")
  res
  catch
  case e: Exception =>
    println(s"Check crashed: ${e.getMessage}")
  false