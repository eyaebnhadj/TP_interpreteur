package pcf

import evaluator.Evaluator
import typer.{Typer, Type}
import parserANTLRv2.AbstractParser
import ast.Term
import generator.{Generator, Code} // Assurez-vous d'importer le générateur
import java.io.{FileInputStream, InputStream}

object PCF:
  def main(args: Array[String]): Unit =
  // Gestion du flux d'entrée (fichier ou entrée standard)
  val in: InputStream =
    if args.isEmpty || (args.length == 1 && args(0) == "-i") then
      System.in
    else
  val filename = if args(0) == "-i" then args(1) else args(0)
  FileInputStream(filename)

  // Logique de choix entre interpréteur et compilateur
  if args.contains("-i") then
    println(s"==> ${interpret(in)}")
  else
    println(compile(in))

  // Front-end : Analyse syntaxique et typage
  def analyze(in: InputStream): (Term, Type) =
  val term = AbstractParser.analyze(in)
  (term, Typer.eval(term, Map()))

  // Interpréteur : Combine front-end et évaluation
  def interpret(in: InputStream): String =
  val (term, typ) = analyze(in)
  val value = Evaluator.eval(term, Map())
  s"$value :: $typ"

  // Compilateur : Combine front-end et génération de code
  def compile(in: InputStream): Code =
  val (term, _) = analyze(in)
  Generator.gen(term)