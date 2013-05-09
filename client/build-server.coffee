connect = require "connect"
lessMiddleware = require "less-middleware" 
connectCoffeeScript = require "connect-coffee-script"
send = require "send"

publicDir = "#{__dirname}/build"

connect.createServer(
	connect.static publicDir
	(request, response, next) ->
		send(request, "index.xml").root(publicDir).pipe response
).listen 3000