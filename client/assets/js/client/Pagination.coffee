define ["promise", "util", "wedges/Wedge", "wedges/Wrapper", "wedges/Label", "wedges/Repeater", "wedges/Container", "wedges/LabelButton", "wedges/AddClass"], (promise, util, Wedge, Wrapper, Label, Repeater, Container, LabelButton, AddClass) ->
	value = util.value
	promise = promise.promise

	class Pagination extends Repeater
		constructor: (pages, current, onSelect) ->
			onSelect = onSelect or =>
			@page = current
			super => (promise value pages).then (pages) =>
				pageLink = (label, page) =>
					children = []
					children.push new Wrapper new LabelButton (=> 
						if page > 0 and page <= pages
							@page = page
							onSelect page
						), label
					if @page is page
						children.push new AddClass "active"
					if page < 1 or page > pages
						children.push new AddClass "disabled"
					new Container children

				links = []
				links.push pageLink "<", @page - 1
				links = links.concat [1..pages].map (page) =>
					pageLink page, page
				links.push pageLink ">", @page + 1
				links