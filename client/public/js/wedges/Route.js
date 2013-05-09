(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["util", "promise", "./Container"], function(util, promise, Container) {
    var Route, flatten, lastNavigateUpdate;
    flatten = util.flatten;
    promise = promise.promise;
    lastNavigateUpdate = promise();
    return Route = (function(_super) {
      var childrenFromRoutes, headSegment, routeMatchesSegment, tailPath;

      __extends(Route, _super);

      headSegment = function(path) {
        if (!(path != null)) {
          return null;
        }
        if (path.indexOf("/") === -1) {
          return path;
        } else {
          return path.substring(0, path.indexOf("/"));
        }
      };

      tailPath = function(path) {
        if (!(path != null)) {
          return null;
        } else if (path.indexOf("/") === -1) {
          return "";
        } else {
          return path.substring(path.indexOf("/"));
        }
      };

      routeMatchesSegment = function(route, segment) {
        return route === segment || route === "*" && (segment != null) && segment !== "";
      };

      childrenFromRoutes = function(routes, path) {
        var child, children, route;
        children = [];
        for (route in routes) {
          child = routes[route];
          if (routeMatchesSegment(route, headSegment(path))) {
            if (typeof child === "function") {
              children.push(child(headSegment(path)));
            } else {
              children.push(child);
            }
            break;
          }
        }
        return children;
      };

      function Route(routes) {
        this._firstDescendantRoutes = __bind(this._firstDescendantRoutes, this);

        this.navigateUpdate = __bind(this.navigateUpdate, this);

        this.navigate = __bind(this.navigate, this);

        var _this = this;
        Route.__super__.constructor.call(this, function() {
          return childrenFromRoutes(routes, _this.path);
        });
      }

      Route.prototype.navigate = function(path) {
        var descendantUpdates, oldPath;
        if (path.startsWith("/")) {
          path = path.substring(1);
        }
        oldPath = this.path;
        this.path = path;
        descendantUpdates = flatten(this._firstDescendantRoutes().map(function(route) {
          return route.navigate(tailPath(path));
        }));
        if (headSegment(path) === headSegment(oldPath)) {
          return descendantUpdates;
        } else {
          return [this];
        }
      };

      Route.prototype.navigateUpdate = function(path) {
        var doNavigateUpdate,
          _this = this;
        doNavigateUpdate = function() {
          var p, updates;
          updates = _this.navigate(path);
          if (updates.length === 0) {
            return promise();
          }
          p = promise(updates.map(function(wedge) {
            return wedge.update();
          }));
          return p.then(function() {
            return doNavigateUpdate();
          });
        };
        return lastNavigateUpdate = lastNavigateUpdate.then(function() {
          return doNavigateUpdate();
        });
      };

      Route.prototype._firstDescendantRoutes = function() {
        var descendants, secondaryDescendants;
        descendants = this.descendants().filter(function(descendant) {
          return descendant instanceof Route;
        });
        secondaryDescendants = flatten(descendants.map(function(descendant) {
          return descendant.descendants();
        }));
        return descendants.filter(function(descendant) {
          return secondaryDescendants.indexOf(descendant === -1);
        });
      };

      return Route;

    })(Container);
  });

}).call(this);
