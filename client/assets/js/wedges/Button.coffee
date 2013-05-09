define ["./Wedge"], (Wedge) ->
	class Button extends Wedge
		constructor: (handler) ->
			@handler = handler
		renderer: (node) =>
			if node.nodeType is Node.TEXT_NODE
				node = node.ownerDocument.createElement "a"
			node.setAttribute "href", ""
			node.addEventListener "click", (event) =>
				@handler()
				event.preventDefault()
			node