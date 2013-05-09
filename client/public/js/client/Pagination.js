(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["promise", "util", "wedges/Wedge", "wedges/Wrapper", "wedges/Label", "wedges/Repeater", "wedges/Container", "wedges/LabelButton", "wedges/AddClass"], function(promise, util, Wedge, Wrapper, Label, Repeater, Container, LabelButton, AddClass) {
    var Pagination, value;
    value = util.value;
    promise = promise.promise;
    return Pagination = (function(_super) {

      __extends(Pagination, _super);

      function Pagination(pages, current, onSelect) {
        var _this = this;
        onSelect = onSelect || function() {};
        this.page = current;
        Pagination.__super__.constructor.call(this, function() {
          return (promise(value(pages))).then(function(pages) {
            var links, pageLink, _i, _results;
            pageLink = function(label, page) {
              var children;
              children = [];
              children.push(new Wrapper(new LabelButton((function() {
                if (page > 0 && page <= pages) {
                  _this.page = page;
                  return onSelect(page);
                }
              }), label)));
              if (_this.page === page) {
                children.push(new AddClass("active"));
              }
              if (page < 1 || page > pages) {
                children.push(new AddClass("disabled"));
              }
              return new Container(children);
            };
            links = [];
            links.push(pageLink("<", _this.page - 1));
            links = links.concat((function() {
              _results = [];
              for (var _i = 1; 1 <= pages ? _i <= pages : _i >= pages; 1 <= pages ? _i++ : _i--){ _results.push(_i); }
              return _results;
            }).apply(this).map(function(page) {
              return pageLink(page, page);
            }));
            links.push(pageLink(">", _this.page + 1));
            return links;
          });
        });
      }

      return Pagination;

    })(Repeater);
  });

}).call(this);
