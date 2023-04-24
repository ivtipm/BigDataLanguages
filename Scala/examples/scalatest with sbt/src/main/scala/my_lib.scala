package my_math

def sum(x: Int, y:Int) = 
	x + y


def sigmoid(x:Double) =
	1 / (1 + Math.exp( - x))
