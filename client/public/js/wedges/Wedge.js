(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  define(["promise", "./configuration"], function(promise, configuration) {
    var Wedge, defer, lastUpdate;
    defer = promise.defer;
    promise = promise.promise;
    lastUpdate = promise();
    return Wedge = (function() {

      function Wedge(renderer) {
        this.update = __bind(this.update, this);

        this.render = __bind(this.render, this);
        this.renderer = renderer || function(node) {
          return node;
        };
      }

      Wedge.prototype.build = function() {
        return promise();
      };

      Wedge.prototype.render = function(node) {
        if (!(node instanceof Node)) {
          throw new Error("node must be Node");
        }
        this.source = node.cloneNode(true);
        node = this.renderer(node);
        if (!this.source.ownerDocument.isSameNode(node.ownerDocument)) {
          throw new Error("ownerDocument cannot change");
        }
        if (!(node instanceof Node)) {
          throw new Error("renderer must return a node");
        }
        return this.node = node;
      };

      Wedge.prototype.update = function() {
        var doUpdate,
          _this = this;
        doUpdate = function() {
          var deferred, p;
          deferred = defer();
          console.log("Update " + _this.constructor.name);
          configuration.onUpdateStart();
          p = _this.build();
          p = p.then(function() {
            var replace;
            replace = _this.node;
            _this.render(_this.source);
            if (replace != null) {
              return replace.parentNode.replaceChild(_this.node, replace);
            }
          });
          p = p.otherwise(function(reason) {
            return configuration.onError(reason);
          });
          p = p.always(function() {
            configuration.onUpdateEnd();
            return deferred.fulfill();
          });
          return deferred.promise;
        };
        return lastUpdate = lastUpdate.then(function() {
          return doUpdate();
        });
      };

      return Wedge;

    })();
  });

}).call(this);
