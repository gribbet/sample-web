define ["promise", "util", "./Wedge"], (promise, util, Wedge) ->
	promise = promise.promise
	value = util.value
	flatten = util.flatten
	value = util.value

	class Container extends Wedge
		constructor: (children) ->
			@children = children or []

		build: =>
			super.then =>
				@builtChildren = {}
				(promise value @children).then (children) =>
					if children instanceof Wedge
						children = [children]
					for selector, child of children
						promise(child).then (child) =>
							child = value child
							if not (child instanceof Wedge)
								throw "Bad child for #{@.constructor.name}: #{child}"
							@builtChildren[selector] = child
							child.build()

		renderer: (node) =>
			for selector, child of @builtChildren
				if parseInt(selector).toString() is selector
					rendered = child.render node
					if node.parentNode?
						node.parentNode.replaceChild rendered, node
					node = rendered
				else
					selected = [].slice.call node.querySelectorAll selector 
					selected.forEach (node) ->
						rendered = child.render node
						node.parentNode.replaceChild rendered, node
			node

		descendants: =>
			children = (child for selector, child of @builtChildren)
			children.concat flatten children.map (child) -> 
				if child instanceof Container then child.descendants() else []

