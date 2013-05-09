(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Wedge"], function(Wedge) {
    var FileInput;
    return FileInput = (function(_super) {

      __extends(FileInput, _super);

      function FileInput(setter) {
        FileInput.__super__.constructor.call(this, function(node) {
          node.addEventListener("change", function() {
            return setter(node.files != null ? node.files[0] : null);
          });
          return node;
        });
      }

      return FileInput;

    })(Wedge);
  });

}).call(this);
