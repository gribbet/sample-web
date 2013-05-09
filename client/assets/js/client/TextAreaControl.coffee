define ["wedges/Template", "wedges/TextAreaInput"], (Template, TextAreaInput) ->
	class TextAreaControl extends Template 
		constructor: (value, setter) ->	
			super "/html/common.xml", ".textarea-control"
				"textarea": new TextAreaInput value, setter