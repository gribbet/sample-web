define ["wedges/Template", "domain/activities", "./Graph"], (Template, activities, Graph) ->
	class Dashboard extends Template
		constructor: ->
			daily =  activities.list "API", false
			hourly = activities.list "API", true

			max = (activities) =>
				x = -1e99
				activities.forEach (a) =>
					x = if a.total > x then a.total else x
				base10 = Math.pow 10, Math.floor(Math.log(x)/Math.log 10)
				x = base10*Math.ceil x/base10
			timeToDate = (time) =>
				date = new Date 0
				date.setUTCSeconds time/1000
				date
			firstActivitiesDate = (activities) =>
				timeToDate activities[0].time
			lastActivitiesDate = (activities) =>
				timeToDate activities[activities.length - 1].time

			super "/html/dashboard.xml", ".dashboard"
				".hourly.graph": new Graph hourly.then (activities) =>
					maxTotal = max activities
					firstDate = firstActivitiesDate activities
					lastDate = lastActivitiesDate activities 
					date = firstDate
					date.setMilliseconds 0
					date.setSeconds 0
					date.setMinutes 0
					date.setHours 0
					next = ->
						value = new Date(date)
						date.setDate date.getDate() + 1
						value
					next()
					divisions = (next() while date <= lastDate)

					data: activities.map (a) =>
						x: a.time
						y: a.total
					xAxis: divisions.map (date) ->
						x: date.getTime()
						label: date.toDateString()
					yMin: 0
					yMax: maxTotal
				".daily.graph": new Graph daily.then (activities) =>
					maxTotal = max activities
					firstDate = firstActivitiesDate activities
					lastDate = lastActivitiesDate activities 
					date = firstDate
					date.setMilliseconds 0
					date.setSeconds 0
					date.setMinutes 0
					date.setHours 0
					date.setDate 1
					next = ->
						value = new Date(date)
						date.setMonth date.getMonth() + 1
						value
					next()
					divisions = (next() while date <= lastDate)

					data: activities.map (a) =>
						x: a.time
						y: a.total
					xAxis: divisions.map (date) ->
						x: date.getTime()
						label: date.toDateString()
					yMin: 0
					yMax: maxTotal