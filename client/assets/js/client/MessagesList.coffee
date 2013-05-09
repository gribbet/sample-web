define ["domain/messages", "wedges/Template", "wedges/Link", "./MessageTable", "./Pagination"], (messages, Template, Link, MessageTable, Pagination) ->
	class MessagesList extends Template 
		constructor: ->
			super "/html/messages-list.xml", ".messages-list"
				".new": new Link "/messages/new"
				".table": table = new MessageTable