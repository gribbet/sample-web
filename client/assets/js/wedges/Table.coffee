define ["util", "promise", "./Container", "./Repeater", "./Wrapper"], (util, promise, Container, Repeater, Wrapper) ->
	value = util.value
	promise = promise.promise

	class Table extends Container
		constructor: (columns, rows) ->
			@columns = columns or []
			@rows = rows or []
			super =>
				columns = promise value @columns
				columns = columns.then (columns) ->
					columns.map (column) -> value column
				rows = promise value @rows
				rows = rows.then (rows) ->
					rows.map (row) -> value row
				"thead tr": new Repeater columns.then (columns) ->
					columns.map (column) -> new Wrapper [column.header]
				"tbody": new Repeater rows.then (rows) ->
					rows.map (row) -> new Repeater columns.then (columns) ->
						columns.map (column) -> new Wrapper [column.value row]
	Table.Column = class Column 
		constructor: (header, value) ->
			@header = header
			@value = value

	Table