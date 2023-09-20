define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/html",
  "mui/util",
  "dojo/query",
  "dojo/dom-attr",
  "sys/mportal/mobile/OpenProxyMixin",
  "dojo/parser"
], function(
  declare,
  domConstruct,
  html,
  util,
  query,
  domAttr,
  OpenProxyMixin,
  parser
) {
  var CardMixin = declare("sys.mportal.CardMixin", [OpenProxyMixin], {
    width: "100%",

    title: "",

    // 自定义链接需要处理link和图片的相对路径
    htmlUrl: function(config, contentNode) {
      if ("sys.mportal.url" == config.portletId) {
        var url = decodeURIComponent(config.vars.url)

        query("link", contentNode).forEach(function(node) {
          var href = domAttr.get(node, "href")
          domAttr.set(
            node,
            "href",
            util.formatUrl(util.urlParse(href, url).href)
          )
        })

        query("img", contentNode).forEach(function(node) {
          var src = domAttr.get(node, "src")
          domAttr.set(node, "src", util.formatUrl(util.urlParse(src, url).href))
        })
      }
    },

    buildContent: function(config) {
      var contentNode = domConstruct.create("div", {
        className: "mui_ekp_portal_tab_content"
      })

      domConstruct.place(contentNode, this.domNode, "last")
      var conNode = domConstruct.create("div", null)

      var self = this
      var _vars = config.vars

      if (config.jsUrl) {
        config.jsUrl = util.urlResolver(config.jsUrl, _vars)
        // 增加`!`，方便后面解析参数
        var urls = config.jsUrl.split("?")

        if (urls.length > 1) {
          urls[1] = "?" + urls[1]
        } else {
          urls[1] = '';
        }

        config.jsUrl = urls.join("!")

        require([util.formatUrl(config.jsUrl)], function(tmpl) {
          //带样式
          if (typeof tmpl == "object") {
            var cssUrls = tmpl.cssUrls
            if (cssUrls) {
              for (var i = 0; i < cssUrls.length; i++) {
                util.loadCSS(cssUrls[i])
              }
            }
            
            tmpl = tmpl.html
          }
          conNode.innerHTML = tmpl
          
          parser
            .parse({
              rootNode: conNode,
              noStart: false
            })
            .then(function() {
              domConstruct.place(conNode, contentNode, "last")
            })
        })

        return contentNode
      }

      config.url = util.urlResolver(config.url, _vars)

      // 处理全路径转义问题
      if (config.url.substring(0, 1) != "/") {
        config.url = decodeURIComponent(config.url)
      }

      var url =
        config.url.substring(0, 1) == "/" ? config.url.substring(1) : config.url

      require(["dojo/text!" + url], function(tmpl) {
        var dhs = new html._ContentSetter({
          node: conNode,
          parseContent: true,
          cleanContent: true
        })

        dhs.set(tmpl)
        dhs.tearDown()

        self.htmlUrl(config, conNode)

        domConstruct.place(conNode, contentNode, "last")
      })

      return contentNode
    },

    buildFooterNode: function() {
      this.footerNode = domConstruct.create(
        "div",
        {
          className: "mui_ekp_portal_footer_btns",
          href: "javascript:;"
        },
        this.domNode
      )
    },

    buildHeaderNode: function() {
      this.headerNode = domConstruct.create(
        "div",
        {
          className: "mui_ekp_portal_branch_title muiFontSizeXL clearfloat"
        },
        this.domNode
      )
    }
  })

  return CardMixin
})
