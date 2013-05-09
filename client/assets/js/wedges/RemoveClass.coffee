define ["./ClassModifier"], (ClassModifier) ->
	class RemoveClass extends ClassModifier
		constructor: (remove) -> 
			super (classes) -> 
				classes.filter (c) -> c isnt remove