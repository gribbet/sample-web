define ->
	String.prototype.startsWith = (string) -> @indexOf(string) is 0

	value: (value) ->
		if typeof value is "function" and value.length is 0 then value() else value
	
	extend:	(obj, mixin) ->
		obj[name] = method for name, method of mixin        
		obj

	flatten: (arrays) ->
		arrays.reduce ((previousValue, currentValue) -> previousValue.concat currentValue), []
