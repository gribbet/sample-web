define ["wedges/Template", "wedges/Route", "domain/users", "./UsersEdit", "./UsersList", "./Error"], (Template, Route, users, UsersEdit, UsersList, Error) ->
	class Users extends Template
		constructor: ->
			super "/html/users.xml", ".users"
				".subcontent": new Route
					"": => new UsersList()
					"*": (id) => 
						if id is "new"
							new UsersEdit users.new()
						else
							users.find(id).then(
								(user) -> new UsersEdit user
								(reason) -> new Error reason)