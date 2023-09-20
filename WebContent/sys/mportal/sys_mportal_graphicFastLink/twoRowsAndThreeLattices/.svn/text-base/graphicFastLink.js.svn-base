define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    // 主题风格ID
    var themeStyleId = util.getUrlParameter(params, "themeStyleId")

    // 第一个快捷链接的URL
    var firstMenuLink = util.getUrlParameter(params, "firstMenuLink")
    // 第一个快捷链接的标题
    var firstMenuTitle = util.getUrlParameter(params, "firstMenuTitle")
    // 第一个快捷链接的副标题
    var firstMenuSubtitle = util.getUrlParameter(params, "firstMenuSubtitle")

    // 第二个快捷链接的URL
    var secondMenuLink = util.getUrlParameter(params, "secondMenuLink")
    // 第二个快捷链接的标题
    var secondMenuTitle = util.getUrlParameter(params, "secondMenuTitle")
    // 第二个快捷链接的图标
    var secondMenuIcon = util.getUrlParameter(params, "secondMenuIcon")

    // 第三个快捷链接的URL
    var thirdMenuLink = util.getUrlParameter(params, "thirdMenuLink")
    // 第三个快捷链接的标题
    var thirdMenuTitle = util.getUrlParameter(params, "thirdMenuTitle")
    // 第三个快捷链接的图标
    var thirdMenuIcon = util.getUrlParameter(params, "thirdMenuIcon")

    var props =
      "themeStyleId:'!{themeStyleId}'" +
      ",firstMenuLink:'!{firstMenuLink}'" +
      ",firstMenuTitle:'!{firstMenuTitle}'" +
      ",firstMenuSubtitle:'!{firstMenuSubtitle}'" +
      ",secondMenuLink:'!{secondMenuLink}'" +
      ",secondMenuTitle:'!{secondMenuTitle}'" +
      ",secondMenuIcon:'!{secondMenuIcon}'" +
      ",thirdMenuLink:'!{thirdMenuLink}'" +
      ",thirdMenuTitle:'!{thirdMenuTitle}'" +
      ",thirdMenuIcon:'!{thirdMenuIcon}'"

    var html =
      '<div data-dojo-type="sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/twoRowsAndThreeLattices" ' +
      'data-dojo-props="' +
      props +
      '">' +
      "</div>"

    html = util.urlResolver(html, {
      themeStyleId: themeStyleId,
      firstMenuLink: firstMenuLink,
      firstMenuTitle: firstMenuTitle,
      firstMenuSubtitle: firstMenuSubtitle,
      secondMenuLink: secondMenuLink,
      secondMenuTitle: secondMenuTitle,
      secondMenuIcon: secondMenuIcon,
      thirdMenuLink: thirdMenuLink,
      thirdMenuTitle: thirdMenuTitle,
      thirdMenuIcon: thirdMenuIcon
    })

    load({
      html: html,
      cssUrls: [
        "/sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/css/twoRowsAndThreeLattices.css"
      ]
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [
      "/sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/twoRowsAndThreeLattices.js"
    ]
  }
})
