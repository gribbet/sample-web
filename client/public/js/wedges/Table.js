(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["util", "promise", "./Container", "./Repeater", "./Wrapper"], function(util, promise, Container, Repeater, Wrapper) {
    var Column, Table, value;
    value = util.value;
    promise = promise.promise;
    Table = (function(_super) {

      __extends(Table, _super);

      function Table(columns, rows) {
        var _this = this;
        this.columns = columns || [];
        this.rows = rows || [];
        Table.__super__.constructor.call(this, function() {
          columns = promise(value(_this.columns));
          columns = columns.then(function(columns) {
            return columns.map(function(column) {
              return value(column);
            });
          });
          rows = promise(value(_this.rows));
          rows = rows.then(function(rows) {
            return rows.map(function(row) {
              return value(row);
            });
          });
          return {
            "thead tr": new Repeater(columns.then(function(columns) {
              return columns.map(function(column) {
                return new Wrapper([column.header]);
              });
            })),
            "tbody": new Repeater(rows.then(function(rows) {
              return rows.map(function(row) {
                return new Repeater(columns.then(function(columns) {
                  return columns.map(function(column) {
                    return new Wrapper([column.value(row)]);
                  });
                }));
              });
            }))
          };
        });
      }

      return Table;

    })(Container);
    Table.Column = Column = (function() {

      function Column(header, value) {
        this.header = header;
        this.value = value;
      }

      return Column;

    })();
    return Table;
  });

}).call(this);
