(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Route", "domain/users", "./UsersEdit", "./UsersList", "./Error"], function(Template, Route, users, UsersEdit, UsersList, Error) {
    var Users;
    return Users = (function(_super) {

      __extends(Users, _super);

      function Users() {
        var _this = this;
        Users.__super__.constructor.call(this, "/html/users.xml", ".users", {
          ".subcontent": new Route({
            "": function() {
              return new UsersList();
            },
            "*": function(id) {
              if (id === "new") {
                return new UsersEdit(users["new"]());
              } else {
                return users.find(id).then(function(user) {
                  return new UsersEdit(user);
                }, function(reason) {
                  return new Error(reason);
                });
              }
            }
          })
        });
      }

      return Users;

    })(Template);
  });

}).call(this);
