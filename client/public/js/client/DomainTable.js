(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Table", "wedges/Template", "wedges/Clearer", "./Pagination"], function(Table, Template, Clearer, Pagination) {
    var DomainTable;
    return DomainTable = (function(_super) {

      __extends(DomainTable, _super);

      function DomainTable(columns, service) {
        var page, pages, perPage,
          _this = this;
        perPage = 10;
        page = 1;
        pages = function() {
          return service.count().then(function(count) {
            return Math.floor((count - 1) / perPage) + 1;
          });
        };
        DomainTable.__super__.constructor.call(this, "/html/domain-table.xml", ".domain-table", function() {
          return pages().then(function(pages) {
            var table;
            if (page > pages || page < 1) {
              page = 1;
            }
            return {
              ".table": table = new Table(columns, function() {
                return service.list((page - 1) * perPage, perPage);
              }),
              ".pagination ul": pages > 1 ? new Pagination(pages, page, function(p) {
                page = p;
                return _this.update();
              }) : new Clearer
            };
          });
        });
      }

      return DomainTable;

    })(Template);
  });

}).call(this);
