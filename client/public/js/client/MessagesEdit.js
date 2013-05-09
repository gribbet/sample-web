(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["require", "wedges/Template", "wedges/Repeater", "wedges/Button", "common/ErrorModal", "domain/messages", "./Field", "./TextControl", "./FileControl", "./UserControl"], function(require, Template, Repeater, Button, ErrorModal, messages, Field, TextControl, FileControl, UserControl) {
    var MessagesEdit;
    return MessagesEdit = (function(_super) {

      __extends(MessagesEdit, _super);

      function MessagesEdit(message) {
        MessagesEdit.__super__.constructor.call(this, "/html/messages-edit.xml", ".messages-edit", {
          ".form": new Repeater([
            new Field("User", new UserControl(message.user, function(user) {
              return message.user = user;
            })), new Field("Subject", new TextControl(message.subject, function(subject) {
              return message.subject = subject;
            })), new Field("Message", new TextControl(message.message, function(text) {
              return message.message = text;
            }))
          ]),
          ".save": new Button(function() {
            return messages.save(message).then(function() {
              return window.application.grouter.navigate("/messages");
            }, function(reason) {
              return new ErrorModal(reason);
            });
          })
        });
      }

      return MessagesEdit;

    })(Template);
  });

}).call(this);
