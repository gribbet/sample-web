define ["promise", "./configuration"], (promise, configuration) ->
	defer = promise.defer
	promise = promise.promise

	lastUpdate = promise()

	class Wedge 
		constructor: (renderer) ->
			@renderer = renderer or (node) -> node

		build: -> 
			#console.log "Build #{@constructor.name}"
			promise()

		render: (node) =>
			if not (node instanceof Node)
				throw new Error "node must be Node"
			#console.log "Render #{@constructor.name}"
			@source = node.cloneNode true
			node = @renderer node
			if not @source.ownerDocument.isSameNode node.ownerDocument
				throw new Error "ownerDocument cannot change"
			if not (node instanceof Node)
				throw new Error "renderer must return a node"
			@node = node

		update: =>
			doUpdate = =>
				deferred = defer()
				console.log "Update #{@constructor.name}"
				configuration.onUpdateStart()
				p = @build()
				p = p.then =>
					replace = @node
					@render @source
					if replace? 
						replace.parentNode.replaceChild @node, replace
				p = p.otherwise (reason) => configuration.onError reason
				p = p.always => 
					configuration.onUpdateEnd()
					deferred.fulfill()
				deferred.promise
			lastUpdate = lastUpdate.then -> doUpdate()
