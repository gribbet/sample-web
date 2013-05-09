(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Wedge", "wedges/Container", "wedges/Template", "./Navigation", "./Pagination"], function(Wedge, Container, Template, Navigation, Pagination) {
    var Layout;
    return Layout = (function(_super) {

      __extends(Layout, _super);

      function Layout(body) {
        Layout.__super__.constructor.call(this, {
          ".container": new Template("/html/layout.xml", ".container", {
            ".navigation": new Navigation,
            ".content": body || new Wedge
          })
        });
      }

      return Layout;

    })(Container);
  });

}).call(this);
