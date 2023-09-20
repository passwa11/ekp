define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var url = util.getUrlParameter(params, "url");
    var name = util.getUrlParameter(params, "name");
    var height = util.getUrlParameter(params, "height");
    var html =
      '<div data-dojo-type="sys/mportal/mobile/extend/Mpiframe" ' +
      "data-dojo-props=\"url:'!{url}',name:'!{name}',height:'!{height}'\" >" +
      "</div>";
    html = util.urlResolver(html, {
      url: url,
      name: name,
      height: height
    });
    load(html);
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load);
    }
  }
})
