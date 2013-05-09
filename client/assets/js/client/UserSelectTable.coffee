define ["wedges/Table", "wedges/Label", "domain/users", "./DomainTable", "./SelectButton"], (Table, Label, users, DomainTable, SelectButton) ->
	Column = Table.Column
	
	class UserSelectTable extends DomainTable 
		constructor: (onSelect) ->	
			super([
				new Column new Label("Id"), (user) -> new Label user.id
				new Column new Label("Name"), (user) -> new Label user.name
				new Column new Label("Username"), (user) -> new Label user.username
				new Column new Label("Email"), (user) -> new Label if user.email
				new Column new Label("Action"), (user) => new SelectButton -> onSelect user],
				users)