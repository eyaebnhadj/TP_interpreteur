package generator

enum Ins:
case Ldi(n: Int)
case Add, Sub, Mul, Div
case Push
case Test(i: List[Ins], j: List[Ins])

// Piste Bleue
case Lds(n: Int)    // Chargement depuis l'environnement (stack/env)
case Let            // Début de bloc Let (ajoute variable à l'env)
case EndLet         // Fin de bloc Let (retire variable de l'env)

// Piste Rouge
case MkClos(code: List[Ins]) // Création de fermeture
case App                     // Application de fonction

// Piste Noire
case FixClos(code: List[Ins]) // Création de fermeture récursive