(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Link", "wedges/Repeater", "wedges/Template", "./NavigationLink"], function(Link, Repeater, Template, NavigationLink) {
    var Navigation;
    return Navigation = (function(_super) {
      var entries;

      __extends(Navigation, _super);

      entries = {
        "Dashboard": "/dashboard",
        "Users": "/users",
        "Messages": "/messages"
      };

      function Navigation() {
        var path, title;
        Navigation.__super__.constructor.call(this, "/html/navigation.xml", ".navigation", {
          "ul": new Repeater((function() {
            var _results;
            _results = [];
            for (path in entries) {
              title = entries[path];
              _results.push(new NavigationLink(title, path));
            }
            return _results;
          })())
        });
      }

      return Navigation;

    })(Template);
  });

}).call(this);
