define ["./DomainService"], (DomainService) ->
	class Users extends DomainService
		path: "users"

		new: =>
			user = {}
			user.name = ""
			user.username = ""
			user.email = ""
			user.password = ""
			user

		save: (user) =>
			image = user.image
			delete user.image

			formData = new FormData()
			formData.append "user", new Blob [JSON.stringify(user)], type: "application/json"
			if image?
				formData.append "image", image
			user.image = image
			if user.id?
				@put "#{@path}/#{user.id}", formData
			else
				@post "#{@path}", formData

		image: (user, size) =>
			@get "#{user.imageUri}?width=#{size}&height=#{size}", "blob"

	new Users()