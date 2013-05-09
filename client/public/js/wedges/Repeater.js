(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["promise", "./Container"], function(promise, Container) {
    var Repeater;
    promise = promise.promise;
    return Repeater = (function(_super) {

      __extends(Repeater, _super);

      function Repeater(repeated) {
        Repeater.__super__.constructor.call(this, repeated);
      }

      Repeater.prototype.renderer = function(node) {
        var child, template, _, _ref;
        template = [].slice.call(node.childNodes).filter(function(childNode) {
          return childNode.nodeType === Node.ELEMENT_NODE;
        });
        [].slice.call(node.childNodes).forEach(function(childNode) {
          return node.removeChild(childNode);
        });
        if (template.length === 0) {
          template.push(node.ownerDocument.createTextNode(""));
        }
        _ref = this.builtChildren;
        for (_ in _ref) {
          child = _ref[_];
          template.forEach(function(childNode) {
            return node.appendChild(child.render(childNode.cloneNode(true)));
          });
        }
        return node;
      };

      return Repeater;

    })(Container);
  });

}).call(this);
