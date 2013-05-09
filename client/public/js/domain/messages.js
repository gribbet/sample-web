(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./DomainService"], function(DomainService) {
    var Messages;
    Messages = (function(_super) {

      __extends(Messages, _super);

      function Messages() {
        this["new"] = __bind(this["new"], this);
        return Messages.__super__.constructor.apply(this, arguments);
      }

      Messages.prototype.path = "messages";

      Messages.prototype["new"] = function() {
        var message;
        message = {};
        message.subject = "";
        message.message = "";
        return message;
      };

      return Messages;

    })(DomainService);
    return new Messages();
  });

}).call(this);
