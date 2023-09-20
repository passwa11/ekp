define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var rowsize = util.getUrlParameter(params, "rowsize")
    var expand = util.getUrlParameter(params, "expand")
    var menuId = util.getUrlParameter(params, "menuId")

    var html =
      '<div class="mui_ekp_portal_item" data-dojo-type="sys/mportal/mobile/Menu" ' +
      "data-dojo-props=\"rowsize:'!{rowsize}',expand:'!{expand}',baseClass:'muiPortalMenu muiPortalMenuTile',url:'/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=items&fdId=!{menuId}'\">" +
      "</div>"
    html = util.urlResolver(html, {
      rowsize: rowsize,
      expand: expand,
      menuId: menuId
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
