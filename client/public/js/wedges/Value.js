(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["util", "promise", "./Wedge"], function(util, promise, Wedge) {
    var Value, value;
    promise = promise.promise;
    value = util.value;
    return Value = (function(_super) {

      __extends(Value, _super);

      function Value(value) {
        this.build = __bind(this.build, this);
        this.value = value || "";
      }

      Value.prototype.build = function() {
        var _this = this;
        this.builtValue = "";
        return Value.__super__.build.apply(this, arguments).then(function() {
          return (promise(value(_this.value))).then(function(v) {
            return _this.builtValue = v;
          });
        });
      };

      return Value;

    })(Wedge);
  });

}).call(this);
