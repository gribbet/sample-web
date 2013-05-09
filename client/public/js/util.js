(function() {

  define(function() {
    String.prototype.startsWith = function(string) {
      return this.indexOf(string) === 0;
    };
    return {
      value: function(value) {
        if (typeof value === "function" && value.length === 0) {
          return value();
        } else {
          return value;
        }
      },
      extend: function(obj, mixin) {
        var method, name;
        for (name in mixin) {
          method = mixin[name];
          obj[name] = method;
        }
        return obj;
      },
      flatten: function(arrays) {
        return arrays.reduce((function(previousValue, currentValue) {
          return previousValue.concat(currentValue);
        }), []);
      }
    };
  });

}).call(this);
