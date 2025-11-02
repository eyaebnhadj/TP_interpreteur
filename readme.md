# PCF - Interpréteur avec analyseur syntaxique, évaluation et typage

## Auteurs
- Eya BEN HADJ
- Othmane OUHADDOU

## Choix d'implémentation

### Grammaire
Nous avons utilisé une **grammaire plate** où toutes les constructions sont des alternatives de la règle `term`.

**Notre grammaire :**
```antlr
term: '(' term ')'                             #ParExp
   | NUMBER                                    #Number
   | ID                                        #Var
   | term term                                 #App
   | term OP1 term                             #BinaryExp1
   | term OP2 term                             #BinaryExp2
   | 'ifz' term 'then' term 'else' term        #IfZero
   | 'let' ID '=' term 'in' term               #Let
   | 'fun' ID '->' term                        #Fun
   | 'fix' ID term                             #Fix
```

**Gestion des priorités :**
Bien que cette grammaire soit "plate" (toutes les constructions au même niveau), ANTLR gère correctement les priorités grâce à son algorithme de parsing récursif descendant avec backtracking et précédence.

### AST (Arbre de Syntaxe Abstraite)
Utilisation de types algébriques (enum) en Scala 3 pour représenter les termes :
```scala
enum Term:
  case Number(value: Int)
  case Var(name: String)
  case Let(name: String, U: Term, V: Term)
  case IfZero(cond: Term, zBranch: Term, nzBranch: Term)
  case BinaryExp(op: Op, exp1: Term, exp2: Term)
  case Fun(name: String, t: Term)
  case App(t: Term, u: Term)
  case Fix(f: String, body: Term)
```

### Évaluation
- **Stratégie** : Évaluation par valeur
- **Environnements** : Utilisation de `Map[String, Value | IceCube]` pour supporter la récursion
- **Glaçons (IceCube)** : Technique d'évaluation paresseuse pour les points fixes
```scala
  case class IceCube(x: String, t: Term, e: Env)
```
- **Valeurs** :
```scala
  enum Value:
    case IntVal(n: Int)
    case Closure(param: String, body: Term, env: Env)
```
### Typage
- **Représentation des types** :
    - `INT` : type entier 
    - `FUNCTION(a, b)` : type fonction `a -> b`
    - `TVar()` : variable de type fraîche pour l'inférence
- **Inférence** : Types automatiquement inférés pour toutes les constructions
- **Vérification** : Phase de typage **avant** l'évaluation pour rejeter les programmes mal typés
## Ce qui fonctionne

### PCF Vert 
- Constantes entières : `42`
- Opérations binaires (`+`, `-`, `*`, `/`) avec priorités correctes
- Conditionnelles `ifz ... then ... else ...`
- Typage : vérification que les opérations portent sur des entiers
- Gestion de la division par zéro (exception à l'exécution)

**Exemple :**
```scala
ifz (10 - 10) then 42 else 0
# Type: INT
# Result: IntVal(42) :: INT
```

### PCF Bleu
-  Variables 
-  Construction `let x = u in v`
-  Shadowing (redéfinition) de variables
-  Typage : environnements de types pour les variables
-  Détection des variables non définies

**Exemples :**
```scala
let x = 10 in x + 5
# Type: INT
# Result: IntVal(15) :: INT

let x = 5 in let x = 10 in x
# Type: INT
# Result: IntVal(10) :: INT (shadowing)
```

### PCF Rouge 
-  Fonctions anonymes `fun x -> body`
-  Application de fonctions avec évaluation par valeur
-  Fonctions currifiées (à plusieurs paramètres)
-  Fermetures (closures) capturant l'environnement
-  Typage : inférence avec variables de type fraîches
-  Détection des erreurs de type (application d'un non-fonction, etc.)

**Exemples :**
```scala
# Fonction simple
fun x -> x + 1
# Type: (INT -> INT)
# Result: Closure(...) :: (INT -> INT)

# Application
(fun x -> x + 1) 5
# Type: INT
# Result: IntVal(6) :: INT

# Currification
fun x -> fun y -> x + y
# Type: (INT -> (INT -> INT))

# Avec let
let add = fun x -> fun y -> x + y in ((add 10) 32)
# Type: INT
# Result: IntVal(42) :: INT
```

### PCF Noir 
-  Construction `fix f body` pour les points fixes
-  Fonctions récursives (factorielle, fibonacci, multiplication, etc.)
-  Glaçons (IceCube) pour l'évaluation paresseuse
-  Typage : vérification de cohérence des types dans fix
-  Détection des points fixes mal typés (ex: `fix f (f + 1)`)

**Exemples :**
```scala
# Factorielle
let fact = fix f fun n -> ifz n then 1 else n * f (n - 1) in fact 5
# Type: INT
# Result: IntVal(120) :: INT

# Compteur récursif
let count = fix f fun n -> ifz n then 0 else f (n - 1) in count 10
# Type: INT
# Result: IntVal(0) :: INT

# Multiplication récursive
let multiply = fix m fun a -> fun b -> 
  ifz a then 0 else b + ((m (a - 1)) b) 
in ((multiply 3) 4)
# Type: INT
# Result: IntVal(12) :: INT
```

###  Limitations 

1. **Parenthèses occasionnellement nécessaires** :
    - Dans certains contextes complexes, des parenthèses peuvent être nécessaires pour lever les ambiguïtés
    - En pratique, les cas d'usage courants fonctionnent bien

2. **Messages d'erreur** :
    - Les messages d'erreur de typage pourraient être plus détaillés

### Ce qui ne fonctionne pas 

Toutes les fonctionnalités demandées sont implémentées et fonctionnelles 


## Réponse à la question du défi (Section 5)

Notre implémentation utilise l'algorithme de Hindley-Milner avec unification de variables de type. Lorsqu'on définit une fonction identité `fun x -> x`, le système lui attribue un type polymorphe implicite.

**Cas 1 : Utilisation unique**
```scala
let id = fun x -> x in id 42
```
- Le type de `id` commence comme `V0 -> V0` (variable de type fraîche)
- Lors de l'application `id 42`, la variable `V0` est **unifiée** avec `INT`
- Type final : `INT`
- Résultat : `IntVal(42)`

**Cas 2 : Tentative d'utilisation polymorphe**
```scala
let id = fun x -> x in 
  let a = id 42 in 
    id (fun y -> y)
```

**Comportement observé** :
1. À la première application `id 42`, la variable de type `V0` est unifiée avec `INT`
2. Le type de `id` devient **concrètement** `INT -> INT` après cette unification
3. Lors de la deuxième application `id (fun y -> y)`, on essaie d'appliquer une fonction de type `INT -> INT` à un argument de type `(V1 -> V1)`
4. **Échec de l'unification** : impossible d'unifier `INT` avec `(V1 -> V1)`
5. **Résultat** : Erreur de typage

**Explication** :
Notre système implémente l'**unification simple** sans le mécanisme de **généralisation/instanciation** (let-polymorphism) du système de types de ML complet.

- Une fois qu'une variable de type est unifiée, elle conserve ce type pour toutes les utilisations suivantes
- Pour permettre le polymorphisme réel, il faudrait implémenter :
    - La **généralisation** au niveau du `let` : transformer `V0 -> V0` en `∀α. α -> α`
    - L'**instanciation** à chaque usage : créer de nouvelles variables de type fraîches

**Conclusion** :
Notre implémentation supporte l'inférence de types mais pas le polymorphisme paramétrique complet. Chaque fonction ne peut être utilisée qu'avec un seul type concret par scope.

## Utilisation des outils d'IA générative

### Outil utilisé
- **Claude**

### Usages détaillés

1. **Clarification du fonctionnement des glaçons (IceCube) pour la récursion**

2. **Aide au débogage** :
    - Résolution des problèmes de priorité dans la grammaire ANTLR
    - Diagnostic des erreurs de `StackOverflowError` liées à `fix`
    - Compréhension du comportement des grammaires récursives à gauche
3. **Aide pour structurer ce README**










