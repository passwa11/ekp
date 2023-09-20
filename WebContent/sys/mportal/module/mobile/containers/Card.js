/**
 * 卡片
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "mui/util",
  "dojo/_base/lang",
  "dojo/dom-construct",
  "dojo/parser",
  "dojo/_base/array",
  "dojo/dom-class",
  "mui/i18n/i18n!sys-mportal:sysMportalPage.more"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  util,
  lang,
  domConstruct,
  parser,
  array,
  domClass,
  msg
) {
  return declare(
    "sys.mportal.module.Card",
    [_WidgetBase, _Contained, _Container],
    {
      title: {},
      contents: [],
      baseClass: "muiModuleCard",

      buildRendering: function() {
        this.inherited(arguments)
        this.parseCard()
      },

      parseCard: function() {
        this.parseTitle()
        this.parseContent()
      },

      parseTitle: function() {
        if((!this.title || !this.title.text) && (!this.contents || !this.contents[0].moreUrl)) {
        	return;
        }
        var title = '&nbsp;';
  	    if (this.title && this.title.text) {
  	    	title = this.title.text;
  	    }
        var header = domConstruct.create("div", {
        	className: "muiModuleCardTitle", 
        	innerHTML: title
        }, this.domNode);

        if(this.contents && this.contents[0].moreUrl) {
        	var more = domConstruct.create("div", {
        		className: "muiModuleCardMore", 
        		innerHTML: msg['sysMportalPage.more'] + '<i class="fontmuis muis-to-right muiFontSizeXS muiModuleCardMoreArrow"></i>'
        	}, header);
        	
        	this.connect(more, 'onclick', 'onClickMore');
        }
      },
      
      onClickMore: function (){
    	  window.location.href = util.formatUrl(this.contents[0].moreUrl);
      },

      parseTmpl: function(tmpl) {
        var conNode = domConstruct.create(
          "div",
          {innerHTML: tmpl},
          this.domNode
        )

        parser
          .parse({
            rootNode: conNode,
            noStart: false
          })
          .then(function(widgets) {
            if (widgets.length == 0) {
              return
            }
            if (!widgets[0].tile) {
              domClass.add(conNode, "muiModuleCardContent")
            }
          })
      },

      parseContent: function() {
        array.forEach(
          this.contents,
          function(content) {
            if (content.tmpl) {
              this.parseTmpl(content.tmpl)
              return
            }

            // 增加`!`，方便后面解析参数
            var urls = content.url.split("?")

            if (urls.length > 1) {
              urls[1] = "?" + urls[1]
            } else {
              urls[1] = ""
            }

            content.url = urls.join("!")

            require([util.formatUrl(content.url)], lang.hitch(this, function(
              tmpl
            ) {
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

              this.parseTmpl(tmpl)
            }))
          },
          this
        )
      }
    }
  )
})
