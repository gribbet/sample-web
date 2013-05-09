(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  define(["configuration", "promise", "sha1", "oauth"], function(configuration, promise, sha1, oauth) {
    var DomainService, defer;
    defer = promise.defer;
    return DomainService = (function() {
      var request;

      function DomainService() {
        this.save = __bind(this.save, this);

        this.create = __bind(this.create, this);

        this.modify = __bind(this.modify, this);

        this["delete"] = __bind(this["delete"], this);

        this.find = __bind(this.find, this);

        this.count = __bind(this.count, this);

        this.list = __bind(this.list, this);

      }

      request = function(type, path, data, response) {
        var client, deferred, message, uri;
        if (response == null) {
          response = "json";
        }
        window.application.startLoading();
        console.log("" + type + " " + path);
        uri = path.startsWith(configuration.baseUri) ? path : configuration.baseUri + path;
        message = {
          method: type,
          action: uri
        };
        OAuth.completeRequest(message, {
          consumerKey: configuration.consumerKey,
          consumerSecret: configuration.consumerSecret
        });
        if (!(data instanceof FormData)) {
          data = JSON.stringify(data);
        }
        deferred = defer();
        client = new XMLHttpRequest();
        client.onreadystatechange = function() {
          if (client.readyState !== 4) {
            return;
          }
          window.application.stopLoading();
          if (client.status >= 200 && client.status < 300) {
            if (!client.response) {
              return deferred.fulfill(null);
            } else if (response === "json") {
              return deferred.fulfill(JSON.parse(client.response));
            } else {
              return deferred.fulfill(client.response);
            }
          } else {
            if (client.response) {
              return deferred.reject(JSON.parse(client.response).message);
            } else {
              return deferred.reject("Request failed: " + message.method + " " + message.action + " " + client.statusText);
            }
          }
        };
        if (response !== "json") {
          client.responseType = response;
        }
        client.open(message.method, message.action, true);
        if (!(data instanceof FormData)) {
          client.setRequestHeader("Content-Type", "application/json");
          client.setRequestHeader("Accept", "application/json");
        }
        client.setRequestHeader("Authorization", OAuth.getAuthorizationHeader("", message.parameters));
        client.send(data);
        return deferred.promise;
      };

      DomainService.prototype.get = function(path, response) {
        return request("GET", path, null, response);
      };

      DomainService.prototype.put = function(path, data, response) {
        return request("PUT", path, data, response);
      };

      DomainService.prototype.post = function(path, data, response) {
        return request("POST", path, data, response);
      };

      DomainService.prototype.del = function(path, response) {
        return request("DELETE", path, response);
      };

      DomainService.prototype.list = function(start, count) {
        start = start || 0;
        count = count || 10;
        return this.get("" + this.path + "?start=" + start + "&count=" + count);
      };

      DomainService.prototype.count = function() {
        return this.get("" + this.path + "/count");
      };

      DomainService.prototype.find = function(id) {
        return this.get("" + this.path + "/" + id);
      };

      DomainService.prototype["delete"] = function(object) {
        return this.del("" + this.path + "/" + object.id);
      };

      DomainService.prototype.modify = function(object) {
        return this.put("" + this.path + "/" + object.id, object);
      };

      DomainService.prototype.create = function(object) {
        return this.post("" + this.path, object);
      };

      DomainService.prototype.save = function(object) {
        if (object.id != null) {
          return this.modify(object);
        } else {
          return this.create(object);
        }
      };

      return DomainService;

    })();
  });

}).call(this);
