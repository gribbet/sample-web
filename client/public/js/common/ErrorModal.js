(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Label", "./MessageModal"], function(Label, MessageModal) {
    var ErrorModal;
    return ErrorModal = (function(_super) {

      __extends(ErrorModal, _super);

      function ErrorModal(reason) {
        ErrorModal.__super__.constructor.call(this, "Error", reason);
      }

      return ErrorModal;

    })(MessageModal);
  });

}).call(this);
