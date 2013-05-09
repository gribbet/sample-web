define ["./Value"], (Value) ->
	class Link extends Value
		constructor: (href) ->
			super href
		renderer: (node) ->
			if node.nodeType is Node.TEXT_NODE
				node = node.ownerDocument.createElement "a"
			node.setAttribute "href", @builtValue
			node