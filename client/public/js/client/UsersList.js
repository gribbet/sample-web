(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["domain/users", "wedges/Template", "wedges/Link", "./UserTable", "./Pagination"], function(users, Template, Link, UserTable, Pagination) {
    var UsersList;
    return UsersList = (function(_super) {

      __extends(UsersList, _super);

      function UsersList() {
        var table;
        UsersList.__super__.constructor.call(this, "/html/users-list.xml", ".users-list", {
          ".new": new Link("/users/new"),
          ".table": table = new UserTable
        });
      }

      return UsersList;

    })(Template);
  });

}).call(this);
