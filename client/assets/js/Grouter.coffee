define ->
	class Grouter
		constructor: (handler) ->
			@handler = handler

			captureLinkClick = (element) ->
				if not element?
					return null
				if element.tagName? and element.tagName.toLowerCase() is "a" and element.getAttribute("href")? and element.getAttribute("href").slice(0, 1) is "/"
					path = element.getAttribute "href"
					return path
				else return captureLinkClick element.parentNode

			document.addEventListener "click", (event) =>
				path = captureLinkClick event.target
				if path?
					@navigate path
					event.preventDefault()

			window.onpopstate = (event) =>
				@navigate window.location.pathname
				event.preventDefault()

			@navigate window.location.pathname

		navigate: (path, replace = false) ->
			path = path or window.location.pathname
			if path isnt window.location.pathname
				if replace
					window.history.replaceState {}, "", path
				else
					window.history.pushState {}, "", path
			if path isnt @currentPath
				console.log "Navigate: #{path}"
				@currentPath = path
				setTimeout (=> @handler path), 0