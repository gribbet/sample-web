(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Button"], function(Template, Button) {
    var EditDeleteButtons;
    return EditDeleteButtons = (function(_super) {

      __extends(EditDeleteButtons, _super);

      function EditDeleteButtons(onEdit, onDelete) {
        EditDeleteButtons.__super__.constructor.call(this, "/html/common.xml", ".edit-delete-buttons", {
          ".edit": new Button(function() {
            return onEdit();
          }),
          ".delete": new Button(function() {
            return onDelete();
          })
        });
      }

      return EditDeleteButtons;

    })(Template);
  });

}).call(this);
