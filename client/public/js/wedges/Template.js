(function() {
  var __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; },
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["promise", "./Container"], function(promise, Container) {
    var Template, TemplateProvider, defer, templateProvider;
    defer = promise.defer;
    promise = promise.promise;
    TemplateProvider = (function() {
      var loaded;

      function TemplateProvider() {}

      loaded = {};

      TemplateProvider.prototype.get = function(path) {
        var client, deferred;
        if (__indexOf.call(loaded, path) >= 0) {
          return promise(loaded[path].cloneNode(true));
        }
        deferred = defer();
        client = new XMLHttpRequest();
        client.onreadystatechange = function() {
          if (client.readyState !== 4) {
            return;
          }
          if (client.status === 200 && (client.response != null)) {
            return deferred.fulfill(client.response);
          } else {
            return deferred.reject(new Error("Could not load template: '" + path + "'"));
          }
        };
        client.open("GET", path, true);
        client.responseType = "document";
        client.send();
        return deferred.promise;
      };

      return TemplateProvider;

    })();
    templateProvider = new TemplateProvider;
    return Template = (function(_super) {

      __extends(Template, _super);

      function Template(name, selector, children) {
        this.renderer = __bind(this.renderer, this);

        this.build = __bind(this.build, this);
        Template.__super__.constructor.call(this, children);
        this.name = name;
        this.selector = selector;
      }

      Template.prototype.build = function() {
        var p,
          _this = this;
        p = Template.__super__.build.apply(this, arguments).then(function() {
          return templateProvider.get(_this.name);
        });
        return p.then(function(document) {
          _this.template = document.querySelector(_this.selector);
          if (!_this.template) {
            throw new Error("Template selection failed. '" + _this.selector + "' in '" + _this.name + "'");
          }
        });
      };

      Template.prototype.renderer = function(node) {
        return Template.__super__.renderer.call(this, node.ownerDocument.importNode(this.template, true));
      };

      return Template;

    })(Container);
  });

}).call(this);
