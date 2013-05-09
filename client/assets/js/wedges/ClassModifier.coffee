define ["./Wedge"], (Wedge) ->
	class ClassModifier extends Wedge
		constructor: (modifier) ->
			@modifier = modifier
		renderer: (node) ->
			if not node.hasAttribute "class"
				classes = []
			else
				classes = node.getAttribute("class").split " "
			classes = classes.filter (name) -> name isnt ""
			classes = @modifier classes
			if classes.length is 0
				node.removeAttribute "class"
			else
				node.setAttribute "class", classes.join " "
			node