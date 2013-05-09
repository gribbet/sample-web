(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/Button", "wedges/Label"], function(Template, Button, Label) {
    var Modal;
    return Modal = (function(_super) {

      __extends(Modal, _super);

      function Modal(title, content) {
        this.render = __bind(this.render, this);

        this.close = __bind(this.close, this);

        var _this = this;
        Modal.__super__.constructor.call(this, "/html/modal.xml", ".modal-container", {
          ".modal-header .title": new Label(title),
          ".modal-body .content": content,
          ".close": new Button(function() {
            return _this.close();
          }),
          ".ok": new Button(function() {
            return _this.close();
          })
        });
        this.update();
      }

      Modal.prototype.close = function() {
        return this.node.parentNode.removeChild(this.node);
      };

      Modal.prototype.render = function() {
        return document.body.appendChild(Modal.__super__.render.call(this, document.createTextNode("")));
      };

      return Modal;

    })(Template);
  });

}).call(this);
