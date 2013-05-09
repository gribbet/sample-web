define ["wedges/Table", "wedges/Template", "wedges/Clearer", "./Pagination"], (Table, Template, Clearer, Pagination) ->
	class DomainTable extends Template 
		constructor: (columns, service) ->
			perPage = 10
			page = 1	
			pages = -> service.count().then (count) -> Math.floor((count-1)/perPage) + 1
			super "/html/domain-table.xml", ".domain-table", => pages().then (pages) =>
				if page > pages or page < 1
					page = 1
				".table": table = new Table(columns, => service.list (page-1)*perPage, perPage)
				".pagination ul": 
					if pages > 1
						new Pagination(
							pages
							page
							(p) =>
								page = p
								@update())
					else
						new Clearer