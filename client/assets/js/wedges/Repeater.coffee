define ["promise", "./Container"], (promise, Container) ->
	promise = promise.promise

	class Repeater extends Container
		constructor: (repeated) ->
			super repeated
		renderer: (node) ->
			template = [].slice.call(node.childNodes)
				.filter((childNode) -> childNode.nodeType is Node.ELEMENT_NODE)
			[].slice.call(node.childNodes).forEach (childNode) ->
				node.removeChild childNode
			if template.length is 0
				template.push node.ownerDocument.createTextNode ""
			for _, child of @builtChildren
				template.forEach (childNode) ->
					node.appendChild child.render childNode.cloneNode true
			node