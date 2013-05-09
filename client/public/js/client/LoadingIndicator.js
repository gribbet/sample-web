(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  define([], function() {
    var LoadingIndicator;
    return LoadingIndicator = (function() {

      function LoadingIndicator() {
        this.hide = __bind(this.hide, this);

        this.show = __bind(this.show, this);

        var node;
        node = document.createElement("div");
        node.setAttribute("id", "loading-indicator");
        document.body.appendChild(node);
        this.node = node;
        this.loading = 0;
      }

      LoadingIndicator.prototype.show = function() {
        this.loading += 1;
        if (this.loading === 1) {
          return this.node.className = "visible";
        }
      };

      LoadingIndicator.prototype.hide = function() {
        this.loading -= 1;
        if (this.loading === 0) {
          return this.node.removeAttribute("class");
        }
      };

      return LoadingIndicator;

    })();
  });

}).call(this);
