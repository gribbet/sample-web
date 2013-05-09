(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Value"], function(Value) {
    var Link;
    return Link = (function(_super) {

      __extends(Link, _super);

      function Link(href) {
        Link.__super__.constructor.call(this, href);
      }

      Link.prototype.renderer = function(node) {
        if (node.nodeType === Node.TEXT_NODE) {
          node = node.ownerDocument.createElement("a");
        }
        node.setAttribute("href", this.builtValue);
        return node;
      };

      return Link;

    })(Value);
  });

}).call(this);
