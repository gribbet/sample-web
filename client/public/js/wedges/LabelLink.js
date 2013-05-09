(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Container", "./Label", "./Link"], function(Container, Label, Link) {
    var LabelLink;
    return LabelLink = (function(_super) {

      __extends(LabelLink, _super);

      function LabelLink(href, title) {
        LabelLink.__super__.constructor.call(this, [new Link(href), new Label(title)]);
      }

      return LabelLink;

    })(Container);
  });

}).call(this);
