define ["wedges/Template", "wedges/TextInput"], (Template, TextInput) ->
	class TextControl extends Template 
		constructor: (value, setter) ->	
			super "/html/common.xml", ".text-control"
				"input": new TextInput value, setter