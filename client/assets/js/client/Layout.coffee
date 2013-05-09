define ["wedges/Wedge", "wedges/Container", "wedges/Template", "./Navigation", "./Pagination"], (Wedge, Container, Template, Navigation, Pagination) ->
	class Layout extends Container
		constructor: (body) ->
			super 
				".container": new Template "/html/layout.xml", ".container"
					".navigation": new Navigation
					".content": body or new Wedge