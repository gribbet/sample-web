define ["require", "wedges/Template", "wedges/Repeater", "wedges/Button", "common/ErrorModal", "domain/users", "./Field", "./TextControl", "./FileControl" ], (require, Template, Repeater, Button, ErrorModal, users, Field, TextControl, FileControl) ->
	class UsersEdit extends Template
		constructor: (user) ->
			super "/html/users-edit.xml", ".users-edit"
				".form": new Repeater [
					new Field "Name", new TextControl(user.name, (name) -> user.name = name)
					new Field "Username", new TextControl(user.username, (username) -> user.username = username)
					new Field "Email", new TextControl(user.email, (email) -> user.email = email)
					new Field "Password", new TextControl(user.password or "", (password) -> user.password = password)
					new Field "Image", new FileControl((image) -> user.image = image)]
				".save": new Button ->
					users.save(user).then(
						-> window.application.grouter.navigate "/users"
						(reason) -> new ErrorModal reason)