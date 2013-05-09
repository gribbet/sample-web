define ["wedges/Wedge", "wedges/Route", "./Layout", "./Redirect", "./Dashboard", "./Users", "./Messages"], (Wedge, Route, Layout, Redirect, Dashboard, Users, Messages) ->
	class Page extends Route
		constructor: ->
			super
				"": => new Redirect "/dashboard"
				"dashboard": => new Layout new Dashboard
				"users": => new Layout new Users
				"messages": => new Layout new Messages
				"*": => new Wedge
		render: (node) =>
			super node or document.getElementById "page"