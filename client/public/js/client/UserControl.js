(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "./UserInput"], function(Template, UserInput) {
    var UserControl;
    return UserControl = (function(_super) {

      __extends(UserControl, _super);

      function UserControl(value, setter) {
        UserControl.__super__.constructor.call(this, "/html/common.xml", ".user-control", {
          ".input": new UserInput(value, setter)
        });
      }

      return UserControl;

    })(Template);
  });

}).call(this);
