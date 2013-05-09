define ["./Container", "./Label", "./Link"], (Container, Label, Link) ->
	class LabelLink extends Container
		constructor: (href, title) ->
			super [new Link(href), new Label title]
			