(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Wedge"], function(Wedge) {
    var Button;
    return Button = (function(_super) {

      __extends(Button, _super);

      function Button(handler) {
        this.renderer = __bind(this.renderer, this);
        this.handler = handler;
      }

      Button.prototype.renderer = function(node) {
        var _this = this;
        if (node.nodeType === Node.TEXT_NODE) {
          node = node.ownerDocument.createElement("a");
        }
        node.setAttribute("href", "");
        node.addEventListener("click", function(event) {
          _this.handler();
          return event.preventDefault();
        });
        return node;
      };

      return Button;

    })(Wedge);
  });

}).call(this);
