(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Wedge"], function(Wedge) {
    var Clearer;
    return Clearer = (function(_super) {

      __extends(Clearer, _super);

      function Clearer() {
        return Clearer.__super__.constructor.apply(this, arguments);
      }

      Clearer.prototype.renderer = function(node) {
        [].slice.call(node.childNodes).forEach(function(childNode) {
          return node.removeChild(childNode);
        });
        return node;
      };

      return Clearer;

    })(Wedge);
  });

}).call(this);
