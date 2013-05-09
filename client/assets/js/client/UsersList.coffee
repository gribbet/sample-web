define ["domain/users", "wedges/Template", "wedges/Link", "./UserTable", "./Pagination"], (users, Template, Link, UserTable, Pagination) ->
	class UsersList extends Template 
		constructor: ->
			super "/html/users-list.xml", ".users-list"
				".new": new Link "/users/new"
				".table": table = new UserTable