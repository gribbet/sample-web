define ["./Value"], (Value) ->
	class Image extends Value
		constructor: (value) ->
			super value
		renderer: (node) =>
			if node.nodeType is Node.TEXT_NODE
				node = node.ownerDocument.createElement "img"
			if typeof @builtValue is "string"
				node.setAttribute "src", @builtValue
			else if @builtValue instanceof Blob
				URL = webkitURL
				node.onload = -> URL.revokeObjectURL node.getAttribute "src"
				node.setAttribute "src", URL.createObjectURL @builtValue
			node