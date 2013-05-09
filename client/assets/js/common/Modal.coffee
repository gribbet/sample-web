define ["wedges/Template", "wedges/Button", "wedges/Label"], (Template, Button, Label) ->
	class Modal extends Template
		constructor: (title, content) ->
			super "/html/modal.xml", ".modal-container"
				".modal-header .title": new Label title
				".modal-body .content": content
				".close": new Button => @close()
				".ok": new Button => @close()
			@update()
		close: =>
			@node.parentNode.removeChild @node
		render: =>
			document.body.appendChild super document.createTextNode ""