define ["wedges/Template", "wedges/Label"], (Template, Label) ->
	class Field extends Template 
		constructor: (name, control) ->	
			super "/html/common.xml", ".field"
				".control-label": new Label name
				".controls": control