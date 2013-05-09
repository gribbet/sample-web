(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Table", "wedges/Label", "wedges/LabelLink", "wedges/Image", "common/ErrorModal", "domain/users", "./DomainTable", "./EditDeleteButtons"], function(Table, Label, LabelLink, Image, ErrorModal, users, DomainTable, EditDeleteButtons) {
    var Column, UserTable;
    Column = Table.Column;
    return UserTable = (function(_super) {

      __extends(UserTable, _super);

      function UserTable() {
        var _this = this;
        UserTable.__super__.constructor.call(this, [
          new Column(new Label("Id"), function(user) {
            return new Label(user.id);
          }), new Column(new Label("Image"), function(user) {
            return new Image(users.image(user, 48));
          }), new Column(new Label("Email"), function(user) {
            return new Label(user.email);
          }), new Column(new Label("Name"), function(user) {
            return new Label(user.name);
          }), new Column(new Label("Action"), function(user) {
            return new EditDeleteButtons(function() {
              return window.application.grouter.navigate("/users/" + user.id);
            }, function() {
              return users["delete"](user).then(function() {
                return _this.update();
              }, function(reason) {
                return new ErrorModal(reason);
              });
            });
          })
        ], users);
      }

      return UserTable;

    })(DomainTable);
  });

}).call(this);
