define ["wedges/Template", "wedges/TextInput"], (Template, TextInput) ->
	class DateControl extends Template 
		constructor: (value, setter) ->	
			super "/html/common.xml", ".date-control"
				"input": new TextInput value, setter