define ["./Wedge"], (Wedge) ->
	class Clearer extends Wedge
		renderer: (node) ->
			[].slice.call(node.childNodes).forEach (childNode) ->
				node.removeChild childNode
			node