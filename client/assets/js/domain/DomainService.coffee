define ["configuration", "promise", "sha1", "oauth"], (configuration, promise, sha1, oauth) ->
	defer = promise.defer

	class DomainService 
		request = (type, path, data, response = "json") ->
			window.application.startLoading()

			console.log "#{type} #{path}"

			uri = if path.startsWith configuration.baseUri then path else configuration.baseUri + path
			message = 
				method: type
				action: uri
			OAuth.completeRequest message,
            	consumerKey: configuration.consumerKey
            	consumerSecret: configuration.consumerSecret

			if not (data instanceof FormData)
				data = JSON.stringify data
			
			deferred = defer()
			client = new XMLHttpRequest()
			client.onreadystatechange = ->
				if client.readyState isnt 4
					return
				window.application.stopLoading()
				if client.status >= 200 and client.status < 300
					if not client.response
						deferred.fulfill null
					else if response is "json"
						deferred.fulfill JSON.parse client.response
					else
						deferred.fulfill client.response
				else 
					if client.response
						if response is "json"
							deferred.reject JSON.parse(client.response).message
						else
							deferred.reject null
					else
						deferred.reject "Request failed: #{message.method} #{message.action} #{client.statusText}"
			if response isnt "json"
				client.responseType = response
			client.open message.method, message.action, true
			if not (data instanceof FormData)
				client.setRequestHeader "Content-Type", "application/json"
				client.setRequestHeader "Accept", "application/json"
			client.setRequestHeader "Authorization", OAuth.getAuthorizationHeader "", message.parameters
			client.send data
			deferred.promise
			

		get: (path, response) -> 
			request "GET", path, null, response
		put: (path, data, response) ->
			request "PUT", path, data, response
		post: (path, data, response) ->
			request "POST", path, data, response
		del: (path, response) ->
			request "DELETE", path, response

		list: (start, count) =>
			start = start or 0
			count = count or 10
			@get "#{@path}?start=#{start}&count=#{count}" 
		count: =>
			@get "#{@path}/count"
		find: (id) =>
			@get "#{@path}/#{id}"
		delete: (object) =>
			@del "#{@path}/#{object.id}"
		modify: (object) =>
			@put "#{@path}/#{object.id}", object
		create: (object) =>
			@post "#{@path}", object
		save: (object) =>
			if object.id? then @modify object else @create object

