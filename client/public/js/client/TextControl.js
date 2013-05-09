(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/TextInput"], function(Template, TextInput) {
    var TextControl;
    return TextControl = (function(_super) {

      __extends(TextControl, _super);

      function TextControl(value, setter) {
        TextControl.__super__.constructor.call(this, "/html/common.xml", ".text-control", {
          "input": new TextInput(value, setter)
        });
      }

      return TextControl;

    })(Template);
  });

}).call(this);
