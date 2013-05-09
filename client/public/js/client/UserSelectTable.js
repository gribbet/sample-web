(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Table", "wedges/Label", "domain/users", "./DomainTable", "./SelectButton"], function(Table, Label, users, DomainTable, SelectButton) {
    var Column, UserSelectTable;
    Column = Table.Column;
    return UserSelectTable = (function(_super) {

      __extends(UserSelectTable, _super);

      function UserSelectTable(onSelect) {
        var _this = this;
        UserSelectTable.__super__.constructor.call(this, [
          new Column(new Label("Id"), function(user) {
            return new Label(user.id);
          }), new Column(new Label("Name"), function(user) {
            return new Label(user.name);
          }), new Column(new Label("Username"), function(user) {
            return new Label(user.username);
          }), new Column(new Label("Email"), function(user) {
            if (user.email) {
              return new Label;
            }
          }), new Column(new Label("Action"), function(user) {
            return new SelectButton(function() {
              return onSelect(user);
            });
          })
        ], users);
      }

      return UserSelectTable;

    })(DomainTable);
  });

}).call(this);
