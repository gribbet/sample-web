define ["util", "promise", "./Wedge"], (util, promise, Wedge) ->
	promise = promise.promise
	value = util.value

	class Value extends Wedge
		constructor: (value) ->
			@value = value or ""
		build: =>
			@builtValue = ""
			super.then => (promise value @value).then (v) =>
				@builtValue = v