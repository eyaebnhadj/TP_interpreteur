package generator

enum Ins :      //des instructions
  case Add, Sub, Mul, Div, Push
  case Ldi(n: Int)
  case Test(i: List[Ins], j: List[Ins])
//  case Seq(seq: List[Code])