(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Value"], function(Value) {
    var Image;
    return Image = (function(_super) {

      __extends(Image, _super);

      function Image(value) {
        this.renderer = __bind(this.renderer, this);
        Image.__super__.constructor.call(this, value);
      }

      Image.prototype.renderer = function(node) {
        var URL;
        if (node.nodeType === Node.TEXT_NODE) {
          node = node.ownerDocument.createElement("img");
        }
        if (typeof this.builtValue === "string") {
          node.setAttribute("src", this.builtValue);
        } else if (this.builtValue instanceof Blob) {
          URL = webkitURL;
          node.onload = function() {
            return URL.revokeObjectURL(node.getAttribute("src"));
          };
          node.setAttribute("src", URL.createObjectURL(this.builtValue));
        }
        return node;
      };

      return Image;

    })(Value);
  });

}).call(this);
