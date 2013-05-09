define ["./Wedge"], (Wedge) ->
	class FileInput extends Wedge
		constructor: (setter) ->
			super (node) ->
				node.addEventListener "change", -> setter if node.files? then node.files[0] else null
				node