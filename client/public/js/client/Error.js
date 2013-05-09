(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Label"], function(Template, Label) {
    var Error;
    return Error = (function(_super) {

      __extends(Error, _super);

      function Error(message) {
        Error.__super__.constructor.call(this, "/html/error.xml", ".error", {
          ".message": new Label(message)
        });
      }

      return Error;

    })(Template);
  });

}).call(this);
