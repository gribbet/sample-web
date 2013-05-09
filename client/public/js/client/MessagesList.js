(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["domain/messages", "wedges/Template", "wedges/Link", "./MessageTable", "./Pagination"], function(messages, Template, Link, MessageTable, Pagination) {
    var MessagesList;
    return MessagesList = (function(_super) {

      __extends(MessagesList, _super);

      function MessagesList() {
        var table;
        MessagesList.__super__.constructor.call(this, "/html/messages-list.xml", ".messages-list", {
          ".new": new Link("/messages/new"),
          ".table": table = new MessageTable
        });
      }

      return MessagesList;

    })(Template);
  });

}).call(this);
