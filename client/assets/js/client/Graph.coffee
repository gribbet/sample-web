define ["promise", "util", "wedges/Wedge"], (promise, util, Wedge) ->
	value = util.value
	promise = promise.promise

	class Graph extends Wedge 
		constructor: (options) ->
			@options = options

		build: =>
			@built = null
			super.then => (promise value @options).then (options) =>
				options.data = options.data or []
				options.xDivisions = options.xDivisions or 4
				options.yDivisions = options.yDivisions or 4

				xMin = 1e99
				xMax = -1e99
				yMin = 1e99
				yMax = -1e99
				for a in options.data
					xMin = if a.x < xMin then a.x else xMin
					xMax = if a.x > xMax then a.x else xMax
					yMin = if a.y < yMin then a.y else yMin
					yMax = if a.y > yMax then a.y else yMax
				options.xMin = if options.xMin? then options.xMin else xMin
				options.xMax = if options.xMax? then options.xMax else xMax
				options.yMin = if options.yMin? then options.yMin else yMin
				options.yMax = if options.yMax? then options.yMax else yMax

				options.xAxis = options.xAxis or [0..options.xDivisions].map (i) => 
					x: x = 1.0*(i/options.xDivisions)*(options.xMax - options.xMin) + options.xMin
					label: (x.toPrecision(2) - 0).toString()
				options.yAxis = options.yAxis or [0..options.yDivisions].map (i) =>
					y: y = 1.0*(i/options.yDivisions)*(options.yMax - options.yMin) + options.yMin
					label: (y.toPrecision(2) - 0).toString()

				@built = options

		renderer: (node) ->
			if not (node.tagName is "canvas")
				throw "canvas required"
			width = @built.width or node.width
			height = @built.height or node.height
			border = (@built.border or 0.1)*node.width
			context = node.getContext "2d"

			screenX = (x) => border + 1.0*(width-2*border)*(x - @built.xMin)/(@built.xMax - @built.xMin)
			screenY = (y) => border + 1.0*(height-2*border)*(1.0 - (y - @built.yMin)/(@built.yMax - @built.yMin))

			renderGraph = =>
				context.lineWidth = 2
				context.strokeStyle = "#fff"

				a = @built.data[0]
				context.beginPath()
				context.moveTo screenX(a.x), screenY(a.y)
				next = (a, b) -> context.quadraticCurveTo(
					screenX a.x 
					screenY a.y
					screenX (a.x+b.x)/2.0
					screenY (a.y+b.y)/2.0)
				for i in [1..@built.data.length-2]
					next @built.data[i], @built.data[i+1]
				a = @built.data[@built.data.length-1]
				context.lineTo screenX(a.x), screenY(a.y) 
				context.stroke() 

			renderAxis = =>
				context.font = "12px Rambla"
				context.textAlign = "center"
				context.textBaseline = "middle"
				context.fillStyle = "#999"
				context.lineWidth = 1
				context.strokeStyle = "#333"

				context.beginPath()
				for line in @built.xAxis
					x = screenX line.x 
					context.moveTo x, border
					context.lineTo x, height-border
					context.fillText line.label, x, height-border/2.0
				for line in @built.yAxis
					y = screenY line.y
					context.moveTo border, y
					context.lineTo width-border, y
					context.fillText line.label, width-border/2.0, y

				context.stroke()

			renderAxis()
			renderGraph()


			node