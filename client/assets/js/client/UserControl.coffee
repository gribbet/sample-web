define ["wedges/Template", "./UserInput"], (Template, UserInput) ->
	class UserControl extends Template 
		constructor: (value, setter) ->	
			super "/html/common.xml", ".user-control"
				".input": new UserInput value, setter