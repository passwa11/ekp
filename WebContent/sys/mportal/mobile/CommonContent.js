define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "./Card",
  "./TabCard",
  "dojo/request",
  "mui/util"
], function(
  declare,
  lang,
  array,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  Card,
  TabCard,
  request,
  util
) {
  var content = declare(
    "sys.mportal.CommonContent",
    [WidgetBase, Container, Contained],
    {
      baseClass: "muiPortalContent",

      pageId: "",

      data: null,

      pluginUrl:
        "/sys/mportal/sys_mportal_page/sysMportalPage.do?method=loadPortlets&fdId=!{fdId}",

      buildRendering: function() {
        this.inherited(arguments)
        var self = this

        if (this.data) {
          self.render(this.data)
          return
        }

        var url = util.urlResolver(this.pluginUrl, {
          fdId: this.pageId
        })
        var promise = request.get(util.formatUrl(url), {
          headers: {
            Accept: "application/json"
          },
          handleAs: "json"
        })
        promise.response.then(function(data) {
          if (!data || !data.data) return
          if (data.data) self.render(data.data)
        },function () {
            window.location.reload();
        })
      },
      parseConfig: function(item) {
        return lang.mixin(
          {
            close: false
          },
          item
        )
      },
      render: function(data) {
        this.values = data

        array.forEach(
          this.values,
          function(item) {
            var _item = this.createListItem(this.parseConfig(item))

            if (_item) {
              this.addChild(_item)
            }
          },
          this
        )
        this.renderComplete()
      },

      createListItem: function(item) {
        if (item.configs.length <= 1) return new Card(item)
        else return new TabCard(item)
      },

      show: function(cb) {
        domStyle.set(this.domNode, "display", "block")
        if (cb) {
          cb()
        }
      },
      hide: function(cb) {
        domStyle.set(this.domNode, "display", "none")
        if (cb) {
          cb()
        }
      }
    }
  )

  return content
})
