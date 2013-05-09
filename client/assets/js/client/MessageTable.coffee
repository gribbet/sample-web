define ["wedges/Table", "wedges/Label", "wedges/LabelLink", "wedges/Image", "common/ErrorModal", "domain/messages", "./DomainTable", "./EditDeleteButtons"], (Table, Label, LabelLink, Image, ErrorModal, messages, DomainTable, EditDeleteButtons) ->
	Column = Table.Column
	
	class MessageTable extends DomainTable 
		constructor: ->	
			super([
				new Column new Label("Id"), (message) -> new Label message.id
				new Column new Label("User"), (message) -> new LabelLink "/users/#{message.user.id}", message.user.name
				new Column new Label("Subject"), (message) -> new Label message.subject
				new Column new Label("Message"), (message) -> new Label message.message
				new Column new Label("Action"), (message) => new EditDeleteButtons(
					-> window.application.grouter.navigate "/messages/#{message.id}"
					=> messages.delete(message).then(
						=> @update()
						(reason) -> new ErrorModal reason))]
				messages)