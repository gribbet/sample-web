define ["require", "wedges/Template", "wedges/Repeater", "wedges/Button", "common/ErrorModal", "domain/messages", "./Field", "./TextControl", "./FileControl", "./UserControl" ], (require, Template, Repeater, Button, ErrorModal, messages, Field, TextControl, FileControl, UserControl) ->
	class MessagesEdit extends Template
		constructor: (message) ->
			super "/html/messages-edit.xml", ".messages-edit"
				".form": new Repeater [
					new Field "User", new UserControl(message.user, (user) -> message.user = user)
					new Field "Subject", new TextControl(message.subject, (subject) -> message.subject = subject)
					new Field "Message", new TextControl(message.message, (text) -> message.message = text)]
				".save": new Button ->
					messages.save(message).then(
						-> window.application.grouter.navigate "/messages"
						(reason) -> new ErrorModal reason)