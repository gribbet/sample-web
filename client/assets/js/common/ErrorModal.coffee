define ["wedges/Label", "./MessageModal"], (Label, MessageModal) ->
	class ErrorModal extends MessageModal
		constructor: (reason) ->
			super "Error", reason
