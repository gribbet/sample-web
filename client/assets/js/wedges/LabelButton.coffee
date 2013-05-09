define ["./Container", "./Label", "./Button"], (Container, Label, Button) ->
	class LabelButton extends Container
		constructor: (handler, title) ->
			super [new Button(handler), new Label title]
			