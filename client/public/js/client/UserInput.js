(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Container", "wedges/LabelLink", "wedges/Button", "./UserSelectModal"], function(Container, LabelLink, Button, UserSelectModal) {
    var UserInput;
    return UserInput = (function(_super) {

      __extends(UserInput, _super);

      function UserInput(user, setter) {
        var link,
          _this = this;
        UserInput.__super__.constructor.call(this, {
          ".link": link = new LabelLink(function() {
            if (user != null) {
              return "/users/" + user.id;
            } else {
              return "";
            }
          }, function() {
            if (user != null) {
              return user.name;
            } else {
              return "";
            }
          }),
          ".select": new Button(function() {
            return new UserSelectModal(function(newUser) {
              user = newUser;
              setter(newUser);
              return link.update();
            });
          })
        });
      }

      return UserInput;

    })(Container);
  });

}).call(this);
