(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["util", "./ClassModifier"], function(util, ClassModifier) {
    var AddClass, value;
    value = util.value;
    return AddClass = (function(_super) {

      __extends(AddClass, _super);

      function AddClass(add) {
        AddClass.__super__.constructor.call(this, function(classes) {
          classes.push(value(add));
          return classes;
        });
      }

      return AddClass;

    })(ClassModifier);
  });

}).call(this);
