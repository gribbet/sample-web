(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Button"], function(Template, Button) {
    var SelectButton;
    return SelectButton = (function(_super) {

      __extends(SelectButton, _super);

      function SelectButton(onSelect) {
        SelectButton.__super__.constructor.call(this, "/html/common.xml", ".select-button", {
          ".select": new Button(function() {
            return onSelect();
          })
        });
      }

      return SelectButton;

    })(Template);
  });

}).call(this);
