define ["./Wedge"], (Wedge) ->
	class TextAreaInput extends Wedge
		constructor: (value, setter) ->
			super (node) ->
				node.textContent = value
				node.addEventListener "change", -> setter node.value
				node