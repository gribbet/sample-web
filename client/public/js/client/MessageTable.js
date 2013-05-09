(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Table", "wedges/Label", "wedges/LabelLink", "wedges/Image", "common/ErrorModal", "domain/messages", "./DomainTable", "./EditDeleteButtons"], function(Table, Label, LabelLink, Image, ErrorModal, messages, DomainTable, EditDeleteButtons) {
    var Column, MessageTable;
    Column = Table.Column;
    return MessageTable = (function(_super) {

      __extends(MessageTable, _super);

      function MessageTable() {
        var _this = this;
        MessageTable.__super__.constructor.call(this, [
          new Column(new Label("Id"), function(message) {
            return new Label(message.id);
          }), new Column(new Label("User"), function(message) {
            return new LabelLink("/users/" + message.user.id, message.user.name);
          }), new Column(new Label("Subject"), function(message) {
            return new Label(message.subject);
          }), new Column(new Label("Message"), function(message) {
            return new Label(message.message);
          }), new Column(new Label("Action"), function(message) {
            return new EditDeleteButtons(function() {
              return window.application.grouter.navigate("/messages/" + message.id);
            }, function() {
              return messages["delete"](message).then(function() {
                return _this.update();
              }, function(reason) {
                return new ErrorModal(reason);
              });
            });
          })
        ], messages);
      }

      return MessageTable;

    })(DomainTable);
  });

}).call(this);
