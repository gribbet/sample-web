define ["wedges/Wedge"], (Wedge) ->
	class Redirect extends Wedge
		constructor: (path) ->
			super()
			@path = path
		build: =>
			super.then =>
				window.application.grouter.navigate @path, true