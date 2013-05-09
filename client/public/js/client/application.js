(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  define(["promise", "Grouter", "wedges/configuration", "common/ErrorModal", "./Page", "./LoadingIndicator"], function(promise, Grouter, configuration, ErrorModal, Page, LoadingIndicator) {
    var Application, defer;
    defer = promise.defer;
    promise = promise.promise;
    Application = (function() {

      function Application() {
        this.stopLoading = __bind(this.stopLoading, this);

        this.startLoading = __bind(this.startLoading, this);

        var _this = this;
        this.page = new Page();
        this.indicator = new LoadingIndicator();
        this.grouter = new Grouter(function(path) {
          return _this.page.navigateUpdate(path);
        });
      }

      Application.prototype.startLoading = function() {
        return this.indicator.show();
      };

      Application.prototype.stopLoading = function() {
        return this.indicator.hide();
      };

      return Application;

    })();
    window.application = new Application();
    configuration.onError = function(reason) {
      return new ErrorModal(reason);
    };
    configuration.onUpdateStart = function() {
      return window.application.startLoading();
    };
    configuration.onUpdateEnd = function() {
      return window.application.stopLoading();
    };
    return window.application;
  });

}).call(this);
