define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	    var fdIds = util.getUrlParameter(params, "fdIds")

    var html = '<div class="muiPortalLecturerScoll"><div data-dojo-type="sys/mportal/mobile/extend/MenuSlide" ' +
      'data-dojo-mixins="sys/zone/mobile/mportal/js/PersonItemMixin" ' +
      "data-dojo-props=\"height:'10rem',url:'/sys/zone/sys_zone_mportal/sysZoneMportal.do?method=loadPerson&fdIds=!{fdIds}'\"></div></div>"

    html = util.urlResolver(html, {
    	fdIds : fdIds
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    // 声明依赖--非mui和sys/mportal下的依赖都要在此处声明
    dependences: [
          "/sys/zone/mobile/mportal/js/PersonItemMixin.js",
          "/sys/zone/mobile/mportal/js/PersonItem.js"
    ]
  }
})