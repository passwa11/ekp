"use strict";
var element = render.parent.element;
var _vars = render.vars;
var _style = Com_HtmlEscape(_vars.style) || 'padding:23px 0;';
var _css = '<style>\n' +
    '#' + render.dataview.cid + ' .lui_personal_simple_statisCard_box{' + _style + '}</style>';

function createItem(data, i, k) {
  var colorIdx = 4 * i + (k + 1);
  colorIdx = colorIdx % 9;
  if (colorIdx == 0) {
    colorIdx = 9;
  }

  var container = $("<li />").addClass("lui_personal_simple_statisCard_item lui_personal_simple_statisCard_item_" + colorIdx);
  var a = $('<a target="_blank" />').attr("href", env.fn.formatUrl(data.viewUrl)).appendTo(container);
  $('<span class="lui_personal_simple_statisCard_icon com_bgcolor_light_d"><i class="lui_text_primary iconfont_nav lui_iconfont_nav ' + (data.icon || "") + '"></i></span>').appendTo(a);
  var stattext = $("<div>").attr("class", "lui_personal_simple_statisCard_text").appendTo(a);
  $("<h4 />").text("").appendTo(stattext);
  $("<h5 />").text("").appendTo(stattext);
  return container;
}

function countStat(element, url) {
  $.ajax({
    url: env.fn.formatUrl(url),
    type: "GET",
    dataType: "json",
    error: function error(err) {
      element.hide();
      if (window.console) window.console.log(err);
    },
    success: function success(data) {
      if (data) {
        if (data.length > 0) {
          var record = data[0];
          element.find("a h4").text(record.count || 0);
          element.find("a h5").text(record.text);
        }
      }
    }
  });
}

function splitArray(arrays) {
  var size = arrays.length;
  var pageSize = 4;
  var page = parseInt((size + (pageSize - 1)) / pageSize);
  var result = [];
  for (var i = 0; i < page; i++) {
    var temp = [];
    for (var j = i * pageSize; j <= pageSize * (i + 1) - 1; j++) {
      if (j <= size - 1) {
        temp.push(arrays[j]);
      } else {
        break;
      }
    }
    result.push(temp);
  }
  return result;
}

var html = function () {
  if (data && data.length > 0) {
    var box = $(_css + '<div class="lui_personal_simple_statisCard_box" />');
    var arrays = splitArray(data);
    var ulNode = $('<ul class="lui_personal_simple_stat_ul" />');
    for (var i = 0; i < arrays.length; i++) {
      var records = arrays[i];
      for (var k = 0; k < records.length; k++) {
        var item = createItem(records[k], i, k);
        countStat(item, records[k].url);
        item.appendTo(ulNode);
      }
    }
    ulNode.appendTo(box);
  }
  return box;
}();

done(html);