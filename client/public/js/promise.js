(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(function() {
    var ArrayPromise, DeferredPromise, FulfilledPromise, Promise, PromiseDefer, RejectedPromise, defer, promise, reject;
    promise = function(valueOrPromise) {
      var array, promises, thePromise, value;
      if (valueOrPromise instanceof Promise) {
        return thePromise = valueOrPromise;
      } else if (valueOrPromise instanceof Array) {
        array = valueOrPromise;
        if (array.some(function(valueOrPromise) {
          return valueOrPromise instanceof Promise;
        })) {
          return new ArrayPromise(promises = valueOrPromise);
        } else {
          return new FulfilledPromise(value = array);
        }
      } else {
        return new FulfilledPromise(value = valueOrPromise);
      }
    };
    reject = function(reasonOrPromise) {
      var reason, thePromise;
      if (reasonOrPromise instanceof Promise) {
        return thePromise = reasonOrPromise;
      } else {
        return new RejectedPromise(reason = reasonOrPromise);
      }
    };
    defer = function() {
      return new PromiseDefer();
    };
    Promise = (function() {

      function Promise() {
        this.otherwise = __bind(this.otherwise, this);

        this.always = __bind(this.always, this);

        this.then = __bind(this.then, this);

      }

      Promise.prototype.then = function(onFulfilled, onRejected) {
        throw "No implementation";
      };

      Promise.prototype.always = function(onComplete) {
        return this.then(onComplete, onComplete);
      };

      Promise.prototype.otherwise = function(onRejected) {
        return this.then(void 0, onRejected);
      };

      return Promise;

    })();
    FulfilledPromise = (function(_super) {

      __extends(FulfilledPromise, _super);

      function FulfilledPromise(value) {
        this.then = __bind(this.then, this);
        this.value = value;
      }

      FulfilledPromise.prototype.then = function(onFulfilled, onRejected) {
        try {
          return promise((onFulfilled || function(x) {
            return x;
          })(this.value));
        } catch (e) {
          console.log("Promise exception: " + (e.stack ? e.stack : e));
          return reject(e).then(void 0, onRejected);
        }
      };

      return FulfilledPromise;

    })(Promise);
    RejectedPromise = (function(_super) {

      __extends(RejectedPromise, _super);

      function RejectedPromise(reason) {
        this.then = __bind(this.then, this);
        this.reason = reason;
      }

      RejectedPromise.prototype.then = function(_, onRejected) {
        try {
          if (onRejected != null) {
            return promise(onRejected(this.reason));
          }
          return reject(this.reason);
        } catch (e) {
          console.log("Promise exception: " + e.stack);
          return reject(e).then(void 0, onRejected);
        }
      };

      return RejectedPromise;

    })(Promise);
    DeferredPromise = (function(_super) {

      __extends(DeferredPromise, _super);

      function DeferredPromise(defer) {
        this.then = __bind(this.then, this);
        this.defer = defer;
      }

      DeferredPromise.prototype.then = function(onFulfilled, onRejected) {
        var next;
        if (!(this.defer.promise instanceof DeferredPromise)) {
          return this.defer.promise.then(onFulfilled, onRejected);
        }
        next = new PromiseDefer();
        this.defer.handlers.push(function(promise) {
          promise = promise.then(onFulfilled || function(x) {
            return x;
          }, onRejected);
          return promise.then(next.fulfill, next.reject);
        });
        return next.promise;
      };

      return DeferredPromise;

    })(Promise);
    ArrayPromise = (function(_super) {

      __extends(ArrayPromise, _super);

      function ArrayPromise(valuesOrPromises) {
        this.promises = valuesOrPromises.map(function(valueOrPromise) {
          return promise(valueOrPromise);
        });
      }

      ArrayPromise.prototype.then = function(onFulfilled, onRejected) {
        var completed, deferred, rejectOnce, rejected, values,
          _this = this;
        if (this.promises.length === 0) {
          return promise([]);
        }
        deferred = new PromiseDefer();
        values = [];
        rejected = false;
        rejectOnce = function(reason) {
          if (rejected) {
            return;
          }
          rejected = true;
          return deferred.reject(reason);
        };
        completed = 0;
        promise(this.promises.map(function(p1, index) {
          p1 = p1.then(function(value) {
            var p2;
            p2 = promise(value).then(function(value) {
              values[index] = value;
              completed += 1;
              if (completed === _this.promises.length) {
                return deferred.fulfill(values);
              }
            });
            return p2.otherwise(rejectOnce);
          });
          return p1.otherwise(rejectOnce);
        }));
        return deferred.promise.then(onFulfilled, onRejected);
      };

      return ArrayPromise;

    })(Promise);
    PromiseDefer = (function() {

      function PromiseDefer() {
        this.reject = __bind(this.reject, this);

        this.fulfill = __bind(this.fulfill, this);
        this.handlers = [];
        this.promise = new DeferredPromise(this);
      }

      PromiseDefer.prototype.fulfill = function(value) {
        var _this = this;
        this.promise = promise(value);
        return this.handlers.forEach(function(handler) {
          return handler(_this.promise);
        });
      };

      PromiseDefer.prototype.reject = function(reason) {
        var _this = this;
        this.promise = reject(reason);
        return this.handlers.forEach(function(handler) {
          return handler(_this.promise);
        });
      };

      return PromiseDefer;

    })();
    return {
      promise: promise,
      reject: reject,
      defer: defer
    };
  });

}).call(this);
