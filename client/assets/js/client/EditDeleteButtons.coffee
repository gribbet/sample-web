define ["wedges/Template", "wedges/Button"], (Template, Button) ->
	class EditDeleteButtons extends Template
		constructor: (onEdit, onDelete) ->
			super "/html/common.xml", ".edit-delete-buttons"
				".edit": new Button -> onEdit()
				".delete": new Button -> onDelete()