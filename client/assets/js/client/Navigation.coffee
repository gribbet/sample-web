define ["wedges/Link", "wedges/Repeater", "wedges/Template", "./NavigationLink"], (Link, Repeater, Template, NavigationLink) ->
	class Navigation extends Template
		entries =
			"Dashboard": "/dashboard"
			"Users": "/users"
			"Messages": "/messages"
		constructor: ->
			super "/html/navigation.xml", ".navigation"
				"ul": new Repeater(new NavigationLink(title, path) for path, title of entries)