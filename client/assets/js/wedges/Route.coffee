define ["util", "promise", "./Container"], (util, promise, Container) ->
	flatten = util.flatten
	promise = promise.promise

	lastNavigateUpdate = promise()

	class Route extends Container

		headSegment = (path) -> 
			if not path? then return null
			if path.indexOf("/") is -1 then path
			else path.substring 0, path.indexOf "/"

		tailPath = (path) ->
			if not path? then return null
			else if path.indexOf("/") is -1 then ""
			else path.substring path.indexOf "/"

		routeMatchesSegment = (route, segment) -> 
			route is segment or route is "*" and segment? and segment isnt ""

		childrenFromRoutes = (routes, path) ->
			children = []
			for route, child of routes
				if routeMatchesSegment route, headSegment path
					if typeof child is "function"
						children.push child headSegment path
					else
						children.push child
					break;
			children

		constructor: (routes) ->
			super => childrenFromRoutes routes, @path	

		navigate: (path) =>
			if path.startsWith "/" then path = path.substring 1
			oldPath = @path
			@path = path
			descendantUpdates = flatten @_firstDescendantRoutes().map (route) -> route.navigate tailPath path
			if headSegment(path) is headSegment oldPath
				descendantUpdates
			else
				[@]

		navigateUpdate: (path) =>
			doNavigateUpdate = =>
				updates = @navigate path
				if updates.length is 0
					return promise()
				p = promise updates.map (wedge) => wedge.update()
				p.then => doNavigateUpdate()
			lastNavigateUpdate = lastNavigateUpdate.then -> doNavigateUpdate()

		_firstDescendantRoutes: =>
			descendants = @descendants().filter (descendant) -> descendant instanceof Route
			secondaryDescendants = flatten descendants.map (descendant) -> descendant.descendants()
			return descendants.filter (descendant) -> secondaryDescendants.indexOf descendant is -1