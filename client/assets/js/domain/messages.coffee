define ["./DomainService"], (DomainService) ->
	class Messages extends DomainService
		path: "messages"

		new: =>
			message = {}
			message.subject = ""
			message.message = ""
			message

	new Messages()