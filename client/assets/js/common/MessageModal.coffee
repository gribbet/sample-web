define ["wedges/Label", "./Modal"], (Label, Modal) ->
	class MessageModal extends Modal
		constructor: (title, message) ->
			super title, new Label message
