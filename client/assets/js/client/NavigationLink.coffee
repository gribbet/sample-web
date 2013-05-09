define ["wedges/AddClass", "wedges/Container", "wedges/Wrapper", "wedges/LabelLink"], (AddClass, Container, Wrapper, LabelLink) ->
	class NavigationLink extends Container
		constructor: (path, title) -> 
			super [
				new AddClass -> if window.location.pathname.startsWith path then "active" else ""
				new Container
					"a": new LabelLink path, title]
