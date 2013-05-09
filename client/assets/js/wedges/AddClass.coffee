define ["util", "./ClassModifier"], (util, ClassModifier) ->
	value = util.value
	
	class AddClass extends ClassModifier
		constructor: (add) -> 
			super (classes) -> 
				classes.push value add
				classes