(function() {

  define([], function() {
    return {
      onError: function(reason) {
        return alert(reason);
      },
      onUpdateStart: function() {},
      onUpdateEnd: function() {}
    };
  });

}).call(this);
