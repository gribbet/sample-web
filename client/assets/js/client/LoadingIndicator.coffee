define [], ->

	class LoadingIndicator
		constructor: ->
			node = document.createElement "div"
			node.setAttribute "id", "loading-indicator"
			document.body.appendChild node
			@node = node
			@loading = 0
		show: =>
			@loading += 1 
			if @loading is 1
				@node.className = "visible" 
		hide: =>
			@loading -= 1
			if @loading is 0
				@node.removeAttribute "class"

