define ["./Value"], (Value) ->
	class Label extends Value
		constructor: (value) ->
			super value
		renderer: (node) =>
			node.textContent = @builtValue
			node
			