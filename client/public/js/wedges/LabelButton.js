(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Container", "./Label", "./Button"], function(Container, Label, Button) {
    var LabelButton;
    return LabelButton = (function(_super) {

      __extends(LabelButton, _super);

      function LabelButton(handler, title) {
        LabelButton.__super__.constructor.call(this, [new Button(handler), new Label(title)]);
      }

      return LabelButton;

    })(Container);
  });

}).call(this);
