(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Wedge"], function(Wedge) {
    var ClassModifier;
    return ClassModifier = (function(_super) {

      __extends(ClassModifier, _super);

      function ClassModifier(modifier) {
        this.modifier = modifier;
      }

      ClassModifier.prototype.renderer = function(node) {
        var classes;
        if (!node.hasAttribute("class")) {
          classes = [];
        } else {
          classes = node.getAttribute("class").split(" ");
        }
        classes = classes.filter(function(name) {
          return name !== "";
        });
        classes = this.modifier(classes);
        if (classes.length === 0) {
          node.removeAttribute("class");
        } else {
          node.setAttribute("class", classes.join(" "));
        }
        return node;
      };

      return ClassModifier;

    })(Wedge);
  });

}).call(this);
