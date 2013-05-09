define ["./Container"], (Container) ->
	class Wrapper extends Container
		constructor: (children) ->
			super children
		renderer: (node) ->
			childElements = [].slice.call(node.childNodes)
				.filter((childNode) -> childNode.nodeType is Node.ELEMENT_NODE)
			if childElements.length is 0
				rendered = node.ownerDocument.createTextNode ""
				for _, child of @builtChildren
					rendered = child.render rendered
				node.appendChild rendered
			else
				childElements.forEach (childElement) =>
					rendered = childElement
					for _, child of @builtChildren
						rendered = child.render rendered
					childElement.parentNode.replaceChild rendered, childElement
			node