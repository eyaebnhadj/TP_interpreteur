package generator

enum Ins:
// Instructions de base
case Ldi(n: Int)
case Add, Sub, Mul, Div
case Push
case Test(i: List[Ins], j: List[Ins])

// Piste Bleue (Environnement)
case Lds(n: Int)    // Chargement depuis l'environnement à l'indice n
case Let            // Extension de l'environnement
case EndLet         // Réduction de l'environnement

// Piste Rouge (Fermetures)
case MkClos(code: List[Ins])
case App

// Piste Noire (Récursion)
case FixClos(code: List[Ins])