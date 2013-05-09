(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "domain/activities", "./Graph"], function(Template, activities, Graph) {
    var Dashboard;
    return Dashboard = (function(_super) {

      __extends(Dashboard, _super);

      function Dashboard() {
        var daily, firstActivitiesDate, hourly, lastActivitiesDate, max, timeToDate,
          _this = this;
        daily = activities.list("API", false);
        hourly = activities.list("API", true);
        max = function(activities) {
          var base10, x;
          x = -1e99;
          activities.forEach(function(a) {
            return x = a.total > x ? a.total : x;
          });
          base10 = Math.pow(10, Math.floor(Math.log(x) / Math.log(10)));
          if (base10 <= 0) {
            base10 = 1;
          }
          return x = base10 * Math.ceil(x / base10);
        };
        timeToDate = function(time) {
          var date;
          date = new Date(0);
          date.setUTCSeconds(time / 1000);
          return date;
        };
        firstActivitiesDate = function(activities) {
          return timeToDate(activities[0].time);
        };
        lastActivitiesDate = function(activities) {
          return timeToDate(activities[activities.length - 1].time);
        };
        Dashboard.__super__.constructor.call(this, "/html/dashboard.xml", ".dashboard", {
          ".hourly.graph": new Graph(hourly.then(function(activities) {
            var date, divisions, firstDate, lastDate, maxTotal, next;
            maxTotal = max(activities) || 1;
            firstDate = firstActivitiesDate(activities);
            lastDate = lastActivitiesDate(activities);
            date = firstDate;
            date.setMilliseconds(0);
            date.setSeconds(0);
            date.setMinutes(0);
            date.setHours(0);
            next = function() {
              var value;
              value = new Date(date);
              date.setDate(date.getDate() + 1);
              return value;
            };
            next();
            divisions = ((function() {
              var _results;
              _results = [];
              while (date <= lastDate) {
                _results.push(next());
              }
              return _results;
            })());
            return {
              data: activities.map(function(a) {
                return {
                  x: a.time,
                  y: a.total
                };
              }),
              xAxis: divisions.map(function(date) {
                return {
                  x: date.getTime(),
                  label: date.toDateString()
                };
              }),
              yMin: 0,
              yMax: maxTotal
            };
          })),
          ".daily.graph": new Graph(daily.then(function(activities) {
            var date, divisions, firstDate, lastDate, maxTotal, next;
            maxTotal = max(activities) || 1;
            firstDate = firstActivitiesDate(activities);
            lastDate = lastActivitiesDate(activities);
            date = firstDate;
            date.setMilliseconds(0);
            date.setSeconds(0);
            date.setMinutes(0);
            date.setHours(0);
            date.setDate(1);
            next = function() {
              var value;
              value = new Date(date);
              date.setMonth(date.getMonth() + 1);
              return value;
            };
            next();
            divisions = ((function() {
              var _results;
              _results = [];
              while (date <= lastDate) {
                _results.push(next());
              }
              return _results;
            })());
            return {
              data: activities.map(function(a) {
                return {
                  x: a.time,
                  y: a.total
                };
              }),
              xAxis: divisions.map(function(date) {
                return {
                  x: date.getTime(),
                  label: date.toDateString()
                };
              }),
              yMin: 0,
              yMax: maxTotal
            };
          }))
        });
      }

      return Dashboard;

    })(Template);
  });

}).call(this);
