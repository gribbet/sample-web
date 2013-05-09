(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Wedge", "wedges/Route", "./Layout", "./Redirect", "./Dashboard", "./Users", "./Messages"], function(Wedge, Route, Layout, Redirect, Dashboard, Users, Messages) {
    var Page;
    return Page = (function(_super) {

      __extends(Page, _super);

      function Page() {
        this.render = __bind(this.render, this);

        var _this = this;
        Page.__super__.constructor.call(this, {
          "": function() {
            return new Redirect("/users");
          },
          "dashboard": function() {
            return new Layout(new Dashboard);
          },
          "users": function() {
            return new Layout(new Users);
          },
          "messages": function() {
            return new Layout(new Messages);
          },
          "*": function() {
            return new Wedge;
          }
        });
      }

      Page.prototype.render = function(node) {
        return Page.__super__.render.call(this, node || document.getElementById("page"));
      };

      return Page;

    })(Route);
  });

}).call(this);
