(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["./Container"], function(Container) {
    var Wrapper;
    return Wrapper = (function(_super) {

      __extends(Wrapper, _super);

      function Wrapper(children) {
        Wrapper.__super__.constructor.call(this, children);
      }

      Wrapper.prototype.renderer = function(node) {
        var child, childElements, rendered, _, _ref,
          _this = this;
        childElements = [].slice.call(node.childNodes).filter(function(childNode) {
          return childNode.nodeType === Node.ELEMENT_NODE;
        });
        if (childElements.length === 0) {
          rendered = node.ownerDocument.createTextNode("");
          _ref = this.builtChildren;
          for (_ in _ref) {
            child = _ref[_];
            rendered = child.render(rendered);
          }
          node.appendChild(rendered);
        } else {
          childElements.forEach(function(childElement) {
            var _ref1;
            rendered = childElement;
            _ref1 = _this.builtChildren;
            for (_ in _ref1) {
              child = _ref1[_];
              rendered = child.render(rendered);
            }
            return childElement.parentNode.replaceChild(rendered, childElement);
          });
        }
        return node;
      };

      return Wrapper;

    })(Container);
  });

}).call(this);
