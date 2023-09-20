define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "dojo/dom-construct",
  "sys/mportal/module/mobile/elements/_CountMixin",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/iconUtils",
  "dojo/request",
  "mui/util",
  "dojo/Deferred",
  "dojo/_base/lang"
], function(
  declare,
  _ItemBase,
  domConstruct,
  CountMixin,
  OpenProxyMixin,
  iconUtils,
  request,
  util,
  Deferred,
  lang
) {
  return declare("", [_ItemBase, CountMixin, OpenProxyMixin], {
    text: "",
    desc: "",
    href: "",
    icon: "",
    img: "",
    count: "",
    countUrl: "",
    descUrl: "",

    baseClass: "muiModuleFuncListItem",
    // 渲染面板
    buildRendering: function() {
      this.inherited(arguments)

      this.buildIcon()
      this.buildMore()
      this.buildInfo()
      this.buildText()
      this.buildDesc()
    },

    buildInfo: function() {
      this.infoNode = domConstruct.create(
        "div",
        {className: "muiModuleFuncListInfo"},
        this.domNode
      )
    },

    buildIcon: function() {
      if (this.icon) {
        iconUtils.createIcon(this.icon, null, null, null, this.domNode);
      }else if(this.img){
        var url = this.img;
        if(url.indexOf("/") == 0){
          url = url.substring(1);
        }
        url = dojoConfig.baseUrl + url;
        this.moreNode = domConstruct.create(
            "img",
            {className: "mui",src:url},
            this.domNode
        );
      }
    },

    buildText: function() {
      domConstruct.create("p", {innerHTML: this.text}, this.infoNode)
    },

    buildDesc: function() {
      var deferred = new Deferred()
      if (this.desc) {
        deferred.resolve({desc: this.desc})
      } else if (this.descUrl) {
        request
          .get(util.formatUrl(this.descUrl), {
            handleAs: "json"
          })
          .then(function(data) {
            deferred.resolve(data)
          })
      }

      deferred.promise.then(
        lang.hitch(this, function(data) {
          domConstruct.create("span", {innerHTML: data.desc}, this.infoNode)
        })
      )
    },

    buildMore: function() {
      this.moreNode = domConstruct.create(
        "div",
        {className: "muiModuleFuncInfoMore"},
        this.domNode
      )

      if (this.count || this.countUrl) {
        this.counting(function(count) {
          if (count > 0) {
            domConstruct.create(
              "span",
              {
                innerHTML: count
              },
              this.moreNode,
              "first"
            )
          }
        })
      }

      if (this.href) {
        domConstruct.create(
          "i",
          {className: "fontmuis muis-to-right"},
          this.moreNode
        )
        this.proxyClick(this.domNode, this.href, "_blank")
      }
    }
  })
})
