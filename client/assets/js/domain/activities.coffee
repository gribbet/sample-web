define ["./DomainService"], (DomainService) ->
	class Activities extends DomainService
		path: "activities"

		list: (key, hourly) => 
			@get "#{@path}?key=#{key}&hourly=#{hourly}" 

	new Activities()