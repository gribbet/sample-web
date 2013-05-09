connect = require "connect"
lessMiddleware = require "less-middleware" 
connectCoffeeScript = require "connect-coffee-script"
send = require "send"

assetsDir =  "#{__dirname}/assets"
publicDir = "#{__dirname}/public"

connect.createServer(
	require("less-middleware")
		src: assetsDir
		dest: publicDir
		compress: true 
	require("connect-coffee-script")
		src: assetsDir
		dest: publicDir
	connect.static publicDir
	(request, response, next) ->
		send(request, "index.xml").root(publicDir).pipe response
).listen 3000