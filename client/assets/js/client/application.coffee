define ["promise", "Grouter", "wedges/configuration", "common/ErrorModal", "./Page", "./LoadingIndicator"], (promise, Grouter, configuration, ErrorModal, Page, LoadingIndicator) ->
	defer = promise.defer
	promise = promise.promise

	class Application
		constructor: ->
			@page = new Page()
			@indicator = new LoadingIndicator()
			@grouter = new Grouter (path) => @page.navigateUpdate path
		startLoading: => @indicator.show()
		stopLoading: => @indicator.hide()
	window.application = new Application()

	configuration.onError = (reason) -> new ErrorModal reason
	configuration.onUpdateStart = -> window.application.startLoading()
	configuration.onUpdateEnd = -> window.application.stopLoading()

	window.application