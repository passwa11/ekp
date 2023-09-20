define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var menuId = util.getUrlParameter(params, "menuId")
    var rows = util.getUrlParameter(params, "rows")
    var columns = util.getUrlParameter(params, "columns")

    var html =
      '<div style="margin: 0 -1.25rem"><div class="mui_ekp_portal_item" data-dojo-type="sys/mportal/mobile/extend/MenuSlide" ' +
      'data-dojo-mixins="sys/mportal/mobile/extend/MenuItemMixin" ' +
      "data-dojo-props=\"height:'9rem',url:'/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=items&fdId=!{menuId}',rows:'!{rows}',columns:'!{columns}'\">" +
      "</div></div>"
    html = util.urlResolver(html, {
      columns: columns,
      menuId: menuId,
      rows: rows
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
