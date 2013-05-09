define ["wedges/Container", "wedges/LabelLink", "wedges/Button", "./UserSelectModal"], (Container, LabelLink, Button, UserSelectModal) ->
	class UserInput extends Container
		constructor: (user, setter) ->
			super
				".link": link = new LabelLink(
					-> if user? then "/users/#{user.id}" else "", 
					-> if user? then user.name else "")
				".select": new Button =>
					new UserSelectModal (newUser) =>
						user = newUser
						setter newUser
						link.update()