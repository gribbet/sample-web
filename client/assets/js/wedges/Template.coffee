define ["promise", "./Container"], (promise, Container) ->
	defer = promise.defer
	promise = promise.promise

	class TemplateProvider
		loaded = {}

		get: (path) ->
			if path in loaded
				return promise loaded[path].cloneNode true

			deferred = defer()

			client = new XMLHttpRequest()
			client.onreadystatechange = ->
				if client.readyState isnt 4
					return
				if client.status is 200 and client.response?
					#loaded[path] = client.response #TODO: Enable caching
					deferred.fulfill client.response
				else
					deferred.reject new Error "Could not load template: '#{path}'"

			client.open "GET", path, true
			client.responseType = "document"
			client.send()

			deferred.promise

	templateProvider = new TemplateProvider

	class Template extends Container
		constructor: (name, selector, children) ->
			super children
			@name = name
			@selector = selector
		build: =>
			p = super.then => templateProvider.get @name
			p.then (document) =>
				@template = document.querySelector @selector  #TODO: Document can be null?
				if not @template
					throw new Error "Template selection failed. '#{@selector}' in '#{@name}'"
		renderer: (node) => 
			super node.ownerDocument.importNode @template, true