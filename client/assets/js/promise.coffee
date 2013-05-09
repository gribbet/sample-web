define ->
	promise = (valueOrPromise) ->
		if valueOrPromise instanceof Promise
			thePromise = valueOrPromise
		else if valueOrPromise instanceof Array
			array = valueOrPromise
			if array.some((valueOrPromise) -> valueOrPromise instanceof Promise)
				new ArrayPromise promises = valueOrPromise
			else
				new FulfilledPromise value = array
		else
			new FulfilledPromise value = valueOrPromise


	reject = (reasonOrPromise) ->
		if reasonOrPromise instanceof Promise
			thePromise = reasonOrPromise
		else
			new RejectedPromise reason = reasonOrPromise

	defer = -> new PromiseDefer()

	class Promise
		then: (onFulfilled, onRejected) =>
			throw "No implementation"
		always: (onComplete) => @then onComplete, onComplete
		otherwise: (onRejected) => @then undefined, onRejected


	class FulfilledPromise extends Promise
		constructor: (value) ->
			@value = value
		then: (onFulfilled, onRejected) =>
			try
				return promise (onFulfilled or (x) -> x) @value
			catch e
				console.log "Promise exception: #{if e.stack then e.stack else e}"
				return reject(e).then undefined, onRejected
			

	class RejectedPromise extends Promise
		constructor: (reason) ->
			@reason = reason
		then: (_, onRejected) =>
			try
				return promise onRejected @reason if onRejected?
				return reject @reason
			catch e
				console.log "Promise exception: #{e.stack}"
				return reject(e).then undefined, onRejected

	class DeferredPromise extends Promise
		constructor: (defer) ->
			@defer = defer
		then: (onFulfilled, onRejected) =>
			if not (@defer.promise instanceof DeferredPromise)
				return @defer.promise.then onFulfilled, onRejected
			next = new PromiseDefer()
			@defer.handlers.push (promise) -> 
				promise = promise.then (onFulfilled or (x) -> x), onRejected
				promise.then next.fulfill, next.reject
			next.promise

	class ArrayPromise extends Promise
		constructor: (valuesOrPromises) ->
			@promises = valuesOrPromises.map (valueOrPromise) ->
				promise valueOrPromise
		then: (onFulfilled, onRejected) ->
			if @promises.length is 0
				return promise []
			deferred = new PromiseDefer()
			values = []

			rejected = false
			rejectOnce = (reason) ->
				return if rejected
				rejected = true
				deferred.reject reason

			completed = 0
			promise @promises.map (p1, index) => 
				p1 = p1.then (value) =>
					p2 = promise(value).then (value) =>
						values[index] = value
						completed += 1
						if completed is @promises.length
							deferred.fulfill values
					p2.otherwise rejectOnce
				p1.otherwise rejectOnce
			deferred.promise.then onFulfilled, onRejected
			
	class PromiseDefer
		constructor: ->
			@handlers = []
			@promise = new DeferredPromise @
		fulfill: (value) =>
			@promise = promise value
			@handlers.forEach (handler) => handler @promise
		reject: (reason) => 
			@promise = reject reason
			@handlers.forEach (handler) => handler @promise

	promise: promise
	reject: reject
	defer: defer
