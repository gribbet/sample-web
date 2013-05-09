define ["wedges/Template", "wedges/Button"], (Template, Button) ->
	class SelectButton extends Template
		constructor: (onSelect) ->
			super "/html/common.xml", ".select-button"
				".select": new Button -> onSelect()