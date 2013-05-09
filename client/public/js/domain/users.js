(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./DomainService"], function(DomainService) {
    var Users;
    Users = (function(_super) {

      __extends(Users, _super);

      function Users() {
        this.image = __bind(this.image, this);

        this.save = __bind(this.save, this);

        this["new"] = __bind(this["new"], this);
        return Users.__super__.constructor.apply(this, arguments);
      }

      Users.prototype.path = "users";

      Users.prototype["new"] = function() {
        var user;
        user = {};
        user.name = "";
        user.username = "";
        user.email = "";
        user.password = "";
        return user;
      };

      Users.prototype.save = function(user) {
        var formData, image;
        image = user.image;
        delete user.image;
        formData = new FormData();
        formData.append("user", new Blob([JSON.stringify(user)], {
          type: "application/json"
        }));
        if (image != null) {
          formData.append("image", image);
        }
        user.image = image;
        if (user.id != null) {
          return this.put("" + this.path + "/" + user.id, formData);
        } else {
          return this.post("" + this.path, formData);
        }
      };

      Users.prototype.image = function(user, size) {
        return this.get("" + user.imageUri + "?width=" + size + "&height=" + size, "blob");
      };

      return Users;

    })(DomainService);
    return new Users();
  });

}).call(this);
