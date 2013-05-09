(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Wedge"], function(Wedge) {
    var TextInput;
    return TextInput = (function(_super) {

      __extends(TextInput, _super);

      function TextInput(value, setter) {
        TextInput.__super__.constructor.call(this, function(node) {
          node.value = value;
          node.addEventListener("change", function() {
            return setter(node.value);
          });
          return node;
        });
      }

      return TextInput;

    })(Wedge);
  });

}).call(this);
