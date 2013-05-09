(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Wedge"], function(Wedge) {
    var Redirect;
    return Redirect = (function(_super) {

      __extends(Redirect, _super);

      function Redirect(path) {
        this.build = __bind(this.build, this);
        Redirect.__super__.constructor.call(this);
        this.path = path;
      }

      Redirect.prototype.build = function() {
        var _this = this;
        return Redirect.__super__.build.apply(this, arguments).then(function() {
          return window.application.grouter.navigate(_this.path, true);
        });
      };

      return Redirect;

    })(Wedge);
  });

}).call(this);
