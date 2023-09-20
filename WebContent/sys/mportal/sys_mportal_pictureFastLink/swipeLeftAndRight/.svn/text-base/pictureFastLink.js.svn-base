define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	  
    // 图片数据源ID (以逗号间隔开的ID字符串)
    var fdIds = util.getUrlParameter(params, "fdIds")
    // 图片宽度
    var pictureWidth = util.getUrlParameter(params, "pictureWidth")
    // 图片高度
    var pictureHeight = util.getUrlParameter(params, "pictureHeight")


    var props =
      "fdIds:'!{fdIds}'" +
      ",pictureWidth:'!{pictureWidth}'" +
      ",pictureHeight:'!{pictureHeight}'"

    var html =
      '<div data-dojo-type="sys/mportal/sys_mportal_pictureFastLink/swipeLeftAndRight/swipeLeftAndRight" ' +
      'data-dojo-props="'+ props +'">' +
      "</div>"
      

    html = util.urlResolver(html, {
    	fdIds: fdIds,
    	pictureWidth: pictureWidth,
    	pictureHeight: pictureHeight
    })

    load({
      html: html,
      cssUrls: [
        "/sys/mportal/sys_mportal_pictureFastLink/swipeLeftAndRight/css/swipeLeftAndRight.css"
      ]
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [
      "/sys/mportal/sys_mportal_pictureFastLink/swipeLeftAndRight/swipeLeftAndRight.js"
    ]
  }
})
