define ["common/Modal", "./UserSelectTable"], (Modal, UserSelectTable) ->
	class UserSelectModal extends Modal
		constructor: (setter) ->
			super "Select User", new UserSelectTable (user) =>
				setter user
				@close()