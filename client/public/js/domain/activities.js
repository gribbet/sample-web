(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./DomainService"], function(DomainService) {
    var Activities;
    Activities = (function(_super) {

      __extends(Activities, _super);

      function Activities() {
        this.list = __bind(this.list, this);
        return Activities.__super__.constructor.apply(this, arguments);
      }

      Activities.prototype.path = "activities";

      Activities.prototype.list = function(key, hourly) {
        return this.get("" + this.path + "?key=" + key + "&hourly=" + hourly);
      };

      return Activities;

    })(DomainService);
    return new Activities();
  });

}).call(this);
