(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Route", "domain/messages", "./MessagesEdit", "./MessagesList", "./Error"], function(Template, Route, messages, MessagesEdit, MessagesList, Error) {
    var Messages;
    return Messages = (function(_super) {

      __extends(Messages, _super);

      function Messages() {
        var _this = this;
        Messages.__super__.constructor.call(this, "/html/messages.xml", ".messages", {
          ".subcontent": new Route({
            "": function() {
              return new MessagesList();
            },
            "*": function(id) {
              if (id === "new") {
                return new MessagesEdit(messages["new"]());
              } else {
                return messages.find(id).then(function(message) {
                  return new MessagesEdit(message);
                }, function(reason) {
                  return new Error(reason);
                });
              }
            }
          })
        });
      }

      return Messages;

    })(Template);
  });

}).call(this);
