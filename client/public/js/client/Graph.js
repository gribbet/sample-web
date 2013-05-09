(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["promise", "util", "wedges/Wedge"], function(promise, util, Wedge) {
    var Graph, value;
    value = util.value;
    promise = promise.promise;
    return Graph = (function(_super) {

      __extends(Graph, _super);

      function Graph(options) {
        this.build = __bind(this.build, this);
        this.options = options;
      }

      Graph.prototype.build = function() {
        var _this = this;
        this.built = null;
        return Graph.__super__.build.apply(this, arguments).then(function() {
          return (promise(value(_this.options))).then(function(options) {
            var a, xMax, xMin, yMax, yMin, _i, _j, _k, _len, _ref, _ref1, _ref2, _results, _results1;
            options.data = options.data || [];
            options.xDivisions = options.xDivisions || 4;
            options.yDivisions = options.yDivisions || 4;
            xMin = 1e99;
            xMax = -1e99;
            yMin = 1e99;
            yMax = -1e99;
            _ref = options.data;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              a = _ref[_i];
              xMin = a.x < xMin ? a.x : xMin;
              xMax = a.x > xMax ? a.x : xMax;
              yMin = a.y < yMin ? a.y : yMin;
              yMax = a.y > yMax ? a.y : yMax;
            }
            options.xMin = options.xMin != null ? options.xMin : xMin;
            options.xMax = options.xMax != null ? options.xMax : xMax;
            options.yMin = options.yMin != null ? options.yMin : yMin;
            options.yMax = options.yMax != null ? options.yMax : yMax;
            options.xAxis = options.xAxis || (function() {
              _results = [];
              for (var _j = 0, _ref1 = options.xDivisions; 0 <= _ref1 ? _j <= _ref1 : _j >= _ref1; 0 <= _ref1 ? _j++ : _j--){ _results.push(_j); }
              return _results;
            }).apply(this).map(function(i) {
              var x;
              return {
                x: x = 1.0 * (i / options.xDivisions) * (options.xMax - options.xMin) + options.xMin,
                label: (x.toPrecision(2) - 0).toString()
              };
            });
            options.yAxis = options.yAxis || (function() {
              _results1 = [];
              for (var _k = 0, _ref2 = options.yDivisions; 0 <= _ref2 ? _k <= _ref2 : _k >= _ref2; 0 <= _ref2 ? _k++ : _k--){ _results1.push(_k); }
              return _results1;
            }).apply(this).map(function(i) {
              var y;
              return {
                y: y = 1.0 * (i / options.yDivisions) * (options.yMax - options.yMin) + options.yMin,
                label: (y.toPrecision(2) - 0).toString()
              };
            });
            return _this.built = options;
          });
        });
      };

      Graph.prototype.renderer = function(node) {
        var border, context, height, renderAxis, renderGraph, screenX, screenY, width,
          _this = this;
        if (!(node.tagName === "canvas")) {
          throw "canvas required";
        }
        width = this.built.width || node.width;
        height = this.built.height || node.height;
        border = (this.built.border || 0.1) * node.width;
        context = node.getContext("2d");
        screenX = function(x) {
          return border + 1.0 * (width - 2 * border) * (x - _this.built.xMin) / (_this.built.xMax - _this.built.xMin);
        };
        screenY = function(y) {
          return border + 1.0 * (height - 2 * border) * (1.0 - (y - _this.built.yMin) / (_this.built.yMax - _this.built.yMin));
        };
        renderGraph = function() {
          var a, i, next, _i, _ref;
          context.lineWidth = 2;
          context.strokeStyle = "#fff";
          a = _this.built.data[0];
          context.beginPath();
          context.moveTo(screenX(a.x), screenY(a.y));
          next = function(a, b) {
            return context.quadraticCurveTo(screenX(a.x), screenY(a.y), screenX((a.x + b.x) / 2.0), screenY((a.y + b.y) / 2.0));
          };
          for (i = _i = 1, _ref = _this.built.data.length - 2; 1 <= _ref ? _i <= _ref : _i >= _ref; i = 1 <= _ref ? ++_i : --_i) {
            next(_this.built.data[i], _this.built.data[i + 1]);
          }
          a = _this.built.data[_this.built.data.length - 1];
          context.lineTo(screenX(a.x), screenY(a.y));
          return context.stroke();
        };
        renderAxis = function() {
          var line, x, y, _i, _j, _len, _len1, _ref, _ref1;
          context.font = "12px Rambla";
          context.textAlign = "center";
          context.textBaseline = "middle";
          context.fillStyle = "#999";
          context.lineWidth = 1;
          context.strokeStyle = "#333";
          context.beginPath();
          _ref = _this.built.xAxis;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            line = _ref[_i];
            x = screenX(line.x);
            context.moveTo(x, border);
            context.lineTo(x, height - border);
            context.fillText(line.label, x, height - border / 2.0);
          }
          _ref1 = _this.built.yAxis;
          for (_j = 0, _len1 = _ref1.length; _j < _len1; _j++) {
            line = _ref1[_j];
            y = screenY(line.y);
            context.moveTo(border, y);
            context.lineTo(width - border, y);
            context.fillText(line.label, width - border / 2.0, y);
          }
          return context.stroke();
        };
        renderAxis();
        renderGraph();
        return node;
      };

      return Graph;

    })(Wedge);
  });

}).call(this);
