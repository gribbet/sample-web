(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Value"], function(Value) {
    var Label;
    return Label = (function(_super) {

      __extends(Label, _super);

      function Label(value) {
        this.renderer = __bind(this.renderer, this);
        Label.__super__.constructor.call(this, value);
      }

      Label.prototype.renderer = function(node) {
        node.textContent = this.builtValue;
        return node;
      };

      return Label;

    })(Value);
  });

}).call(this);
