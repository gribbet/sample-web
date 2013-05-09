define ["wedges/Template", "wedges/Label"], (Template, Label) ->
	class Error extends Template
		constructor: (message) ->
			super "/html/error.xml", ".error"
				".message": new Label message