(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Label", "./Modal"], function(Label, Modal) {
    var MessageModal;
    return MessageModal = (function(_super) {

      __extends(MessageModal, _super);

      function MessageModal(title, message) {
        MessageModal.__super__.constructor.call(this, title, new Label(message));
      }

      return MessageModal;

    })(Modal);
  });

}).call(this);
