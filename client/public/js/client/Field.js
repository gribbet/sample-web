(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Label"], function(Template, Label) {
    var Field;
    return Field = (function(_super) {

      __extends(Field, _super);

      function Field(name, control) {
        Field.__super__.constructor.call(this, "/html/common.xml", ".field", {
          ".control-label": new Label(name),
          ".controls": control
        });
      }

      return Field;

    })(Template);
  });

}).call(this);
