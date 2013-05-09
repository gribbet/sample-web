(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["common/Modal", "./UserSelectTable"], function(Modal, UserSelectTable) {
    var UserSelectModal;
    return UserSelectModal = (function(_super) {

      __extends(UserSelectModal, _super);

      function UserSelectModal(setter) {
        var _this = this;
        UserSelectModal.__super__.constructor.call(this, "Select User", new UserSelectTable(function(user) {
          setter(user);
          return _this.close();
        }));
      }

      return UserSelectModal;

    })(Modal);
  });

}).call(this);
