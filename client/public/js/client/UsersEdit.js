(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["require", "wedges/Template", "wedges/Repeater", "wedges/Button", "common/ErrorModal", "domain/users", "./Field", "./TextControl", "./FileControl"], function(require, Template, Repeater, Button, ErrorModal, users, Field, TextControl, FileControl) {
    var UsersEdit;
    return UsersEdit = (function(_super) {

      __extends(UsersEdit, _super);

      function UsersEdit(user) {
        UsersEdit.__super__.constructor.call(this, "/html/users-edit.xml", ".users-edit", {
          ".form": new Repeater([
            new Field("Name", new TextControl(user.name, function(name) {
              return user.name = name;
            })), new Field("Username", new TextControl(user.username, function(username) {
              return user.username = username;
            })), new Field("Email", new TextControl(user.email, function(email) {
              return user.email = email;
            })), new Field("Password", new TextControl(user.password || "", function(password) {
              return user.password = password;
            })), new Field("Image", new FileControl(function(image) {
              return user.image = image;
            }))
          ]),
          ".save": new Button(function() {
            return users.save(user).then(function() {
              return window.application.grouter.navigate("/users");
            }, function(reason) {
              return new ErrorModal(reason);
            });
          })
        });
      }

      return UsersEdit;

    })(Template);
  });

}).call(this);
