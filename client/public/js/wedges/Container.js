(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["promise", "util", "./Wedge"], function(promise, util, Wedge) {
    var Container, flatten, value;
    promise = promise.promise;
    value = util.value;
    flatten = util.flatten;
    value = util.value;
    return Container = (function(_super) {

      __extends(Container, _super);

      function Container(children) {
        this.descendants = __bind(this.descendants, this);

        this.renderer = __bind(this.renderer, this);

        this.build = __bind(this.build, this);
        this.children = children || [];
      }

      Container.prototype.build = function() {
        var _this = this;
        return Container.__super__.build.apply(this, arguments).then(function() {
          _this.builtChildren = {};
          return (promise(value(_this.children))).then(function(children) {
            var child, selector, _results;
            if (children instanceof Wedge) {
              children = [children];
            }
            _results = [];
            for (selector in children) {
              child = children[selector];
              _results.push(promise(child).then(function(child) {
                child = value(child);
                if (!(child instanceof Wedge)) {
                  throw "Bad child for " + _this.constructor.name + ": " + child;
                }
                _this.builtChildren[selector] = child;
                return child.build();
              }));
            }
            return _results;
          });
        });
      };

      Container.prototype.renderer = function(node) {
        var child, rendered, selected, selector, _ref;
        _ref = this.builtChildren;
        for (selector in _ref) {
          child = _ref[selector];
          if (parseInt(selector).toString() === selector) {
            rendered = child.render(node);
            if (node.parentNode != null) {
              node.parentNode.replaceChild(rendered, node);
            }
            node = rendered;
          } else {
            selected = [].slice.call(node.querySelectorAll(selector));
            selected.forEach(function(node) {
              rendered = child.render(node);
              return node.parentNode.replaceChild(rendered, node);
            });
          }
        }
        return node;
      };

      Container.prototype.descendants = function() {
        var child, children, selector;
        children = (function() {
          var _ref, _results;
          _ref = this.builtChildren;
          _results = [];
          for (selector in _ref) {
            child = _ref[selector];
            _results.push(child);
          }
          return _results;
        }).call(this);
        return children.concat(flatten(children.map(function(child) {
          if (child instanceof Container) {
            return child.descendants();
          } else {
            return [];
          }
        })));
      };

      return Container;

    })(Wedge);
  });

}).call(this);
