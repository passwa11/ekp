define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojox/mobile/_StoreListMixin",
  "sys/mportal/mobile/menu/MenuItem",
  "mui/store/JsonRest",
  "mui/util",
  "dojo/dom-class",
  "dojo/_base/array",
  "mui/loadingMixin",
  "dojo/dom-construct",
  "mui/i18n/i18n!sys-mobile"
], function(
  declare,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  _StoreListMixin,
  MenuItem,
  JsonRest,
  util,
  domClass,
  array,
  loadingMixin,
  domConstruct,
  msg
) {
  var menu = declare(
    "sys.mportal.Menu",
    [WidgetBase, Container, Contained, _StoreListMixin, loadingMixin],
    {
      width: "100%",

      height: "",

      rowsize: 4,

      expand: false,

      expond: "no",

      maxHeight: "10rem",

      baseClass: "muiPortalMenu muiPortalContent",

      buildRendering: function() {
        this.inherited(arguments)
        this.source()
      },

      itemRenderer: MenuItem,

      source: function() {
        this.store = new JsonRest({
          idProperty: "fdId",
          target: util.formatUrl(this.url)
        })
      },

      startup: function() {
        this.inherited(arguments)
        if (this.store) {
          this.buildLoading(this.domNode)
          this.setQuery({}, {})
        }
      },

      icon1: "mui-nextStep",

      icon2: "mui-preStep",

      moreShow: function(evt) {
        var target = evt.target
        if (!domClass.contains(target, "mui")) return
        this.toggleIcon(target)
        if (this.showed) {
          domStyle.set(this.domNode, "max-height", this.maxHeight)
          this.showed = false
          return
        }
        domStyle.set(this.domNode, "max-height", this.domNode.scrollHeight+'px')
        this.showed = true
      },

      toggleIcon: function(node) {
        domClass.toggle(node, this.icon1 + " " + this.icon2)
      },

      generateList: function(items) {
        this.destroyLoading(this.domNode)
        var self = this
        if (this.expand == "yes") {
          domStyle.set(this.domNode, "max-height", 'inherit')
        } else {
          if (items.length > this.rowsize) {
            items.splice(this.rowsize - 1, 0, {
              icon: "mui " + self.icon1,
              text : msg["mui.button.more"],
              click: function(evt) {
                self.moreShow(evt)
              }
            })
          }
        }

        array.forEach(items, function(item) {
            item.width = (1 / this.rowsize) * 100 + "%"
        }, this)

        this.containerNode =  domConstruct.create('ul');
        
        this.inherited(arguments);
        
        domConstruct.place(this.containerNode, this.domNode, 'last');
      }
    }
  )
  return menu
})
