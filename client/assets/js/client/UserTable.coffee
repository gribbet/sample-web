define ["wedges/Table", "wedges/Label", "wedges/LabelLink", "wedges/Image", "common/ErrorModal", "domain/users", "./DomainTable", "./EditDeleteButtons"], (Table, Label, LabelLink, Image, ErrorModal, users, DomainTable, EditDeleteButtons) ->
	Column = Table.Column
	
	class UserTable extends DomainTable 
		constructor: ->	
			super([
				new Column new Label("Id"), (user) -> new Label user.id
				new Column new Label("Image"), (user) -> new Image users.image user, 48
				new Column new Label("Email"), (user) -> new Label user.email
				new Column new Label("Name"), (user) -> new Label user.name
				new Column new Label("Action"), (user) => new EditDeleteButtons(
					-> window.application.grouter.navigate "/users/#{user.id}"
					=> users.delete(user).then(
						=> @update()
						(reason) -> new ErrorModal reason))]
				users)