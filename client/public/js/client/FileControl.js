(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(["wedges/Template", "wedges/FileInput", "wedges/Wedge"], function(Template, FileInput, Wedge) {
    var FileControl;
    return FileControl = (function(_super) {

      __extends(FileControl, _super);

      function FileControl(setter) {
        FileControl.__super__.constructor.call(this, "/html/common.xml", ".file-control", {
          "input": new FileInput(setter)
        });
      }

      FileControl.prototype.renderer = function(node) {
        var file, input, select;
        node = FileControl.__super__.renderer.call(this, node);
        input = node.querySelector("input");
        file = node.querySelector(".file");
        select = node.querySelector(".select");
        input.addEventListener("change", function() {
          return file.textContent = input.value.replace("C:\\fakepath\\", "");
        });
        select.addEventListener("click", function() {
          return input.click();
        });
        return node;
      };

      return FileControl;

    })(Template);
  });

}).call(this);
