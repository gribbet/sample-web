define ["./Wedge"], (Wedge) ->
	class TextInput extends Wedge
		constructor: (value, setter) ->
			super (node) ->
				node.value = value
				node.addEventListener "change", -> setter node.value
				node