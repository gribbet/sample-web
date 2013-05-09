define ["wedges/Template", "wedges/FileInput", "wedges/Wedge"], (Template, FileInput, Wedge) ->
	class FileControl extends Template 
		constructor: (setter) ->	
			super "/html/common.xml", ".file-control"
				"input": new FileInput setter
		renderer: (node) ->
			node = super node

			input = node.querySelector "input"
			file = node.querySelector ".file"
			select = node.querySelector ".select"

			input.addEventListener "change", ->
				file.textContent = input.value.replace "C:\\fakepath\\", ""
			select.addEventListener "click", ->
				input.click()

			node
