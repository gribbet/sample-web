define ["wedges/Template", "wedges/Route", "domain/messages", "./MessagesEdit", "./MessagesList", "./Error"], (Template, Route, messages, MessagesEdit, MessagesList, Error) ->
	class Messages extends Template
		constructor: ->
			super "/html/messages.xml", ".messages"
				".subcontent": new Route
					"": => new MessagesList()
					"*": (id) => 
						if id is "new"
							new MessagesEdit messages.new()
						else
							messages.find(id).then(
								(message) -> new MessagesEdit message
								(reason) -> new Error reason)