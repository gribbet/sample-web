(function() {

  define(function() {
    var Grouter;
    return Grouter = (function() {

      function Grouter(handler) {
        var captureLinkClick,
          _this = this;
        this.handler = handler;
        captureLinkClick = function(element) {
          var path;
          if (!(element != null)) {
            return null;
          }
          if ((element.tagName != null) && element.tagName.toLowerCase() === "a" && (element.getAttribute("href") != null) && element.getAttribute("href").slice(0, 1) === "/") {
            path = element.getAttribute("href");
            return path;
          } else {
            return captureLinkClick(element.parentNode);
          }
        };
        document.addEventListener("click", function(event) {
          var path;
          path = captureLinkClick(event.target);
          if (path != null) {
            _this.navigate(path);
            return event.preventDefault();
          }
        });
        window.onpopstate = function(event) {
          _this.navigate(window.location.pathname);
          return event.preventDefault();
        };
        this.navigate(window.location.pathname);
      }

      Grouter.prototype.navigate = function(path, replace) {
        var _this = this;
        if (replace == null) {
          replace = false;
        }
        path = path || window.location.pathname;
        if (path !== window.location.pathname) {
          if (replace) {
            window.history.replaceState({}, "", path);
          } else {
            window.history.pushState({}, "", path);
          }
        }
        if (path !== this.currentPath) {
          console.log("Navigate: " + path);
          this.currentPath = path;
          return setTimeout((function() {
            return _this.handler(path);
          }), 0);
        }
      };

      return Grouter;

    })();
  });

}).call(this);
