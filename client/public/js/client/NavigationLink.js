(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/AddClass", "wedges/Container", "wedges/Wrapper", "wedges/LabelLink"], function(AddClass, Container, Wrapper, LabelLink) {
    var NavigationLink;
    return NavigationLink = (function(_super) {

      __extends(NavigationLink, _super);

      function NavigationLink(path, title) {
        NavigationLink.__super__.constructor.call(this, [
          new AddClass(function() {
            if (window.location.pathname.startsWith(path)) {
              return "active";
            } else {
              return "";
            }
          }), new Container({
            "a": new LabelLink(path, title)
          })
        ]);
      }

      return NavigationLink;

    })(Container);
  });

}).call(this);
