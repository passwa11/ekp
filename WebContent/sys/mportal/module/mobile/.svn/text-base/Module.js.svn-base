/**
 * 模板文件基类
 */
define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/_base/array",
  "dojo/dom-construct",
  "dojo/parser",
  "./Tabbar",
  "./TabView",
  "./HashMixin",
  "dojo/_base/lang",
  "./Container",
  "./containers/Card",
  "./containers/TabsCard",
  "./elements/Button",
  "dojo/dom-class"
], function(
  declare,
  _WidgetBase,
  _Contained,
  _Container,
  array,
  domConstruct,
  parser,
  Tabbar,
  TabView,
  HashMixin,
  lang,
  Container,
  Card,
  TabsCard,
  Button,
  domClass
) {
  return declare(
    "sys.mportal.module.Module",
    [_WidgetBase, _Contained, _Container, HashMixin],
    {
      views: [],

      postMixInProperties: function() {
        this.inherited(arguments)
        array.forEach(
          this.views,
          function(view) {
            var tabbar = {
              icon: view.icon,
              text: view.text,
              view: view.createView()
            }
            this.tabbars.push(tabbar)
          },
          this
        )
      },
      // 视图切换事件
      VIEW_CHANGE: "mui.view.change",

      /**
       * 页签内容，数据格式如下：<br>
       * [{icon:'图标',text:'文字',view:'字符串'|[数组]}]
       */
      tabbars: [],

      containerTmpl:
        '<div data-dojo-type="sys/mportal/module/mobile/Container">{tmpl}<div fixed="bottom"></div></div>',

      // 解析视图
      parseView: function(index) {
        var view = this.getChildren()[index]
        // 解析过则跳过
        if (view.isParsed()) {
          return
        }

        view.parse()

        var contents = this.tabbars[index].view

        if (lang.isObject(contents)) {
          var container = new Container()
          view.addChild(container)

          // 解析头部
          var header = contents.header
          if (header) {
            this.parseComponent(header, container)
          }

          // 解析卡片
          var cards = contents.cards

          array.forEach(cards, function(card) {
        	  var cardContents = card.contents;
        	  
        	  if(cardContents.length == 1) { // 单一卡片
        		  this.parseCard(card, container)
        	  } else { // 多标签
        		  this.parseTabsCard(card, container)
        	  }
        	  
          }, this);

          // 自定义html片段
          var tmpl = contents.tmpl
          if (tmpl) {
            this.parseComponent({tmpl: tmpl}, container)
          }

          domConstruct.create("div", {fixed:"bottom"},container.domNode)
          
          // 解析按钮
          var button = contents.button
          if (button) {
            var btn = new Button(button)
            container.addChild(btn)
          }

          return
        }

        // 非数组则加上容器组件后一起解析
        var node = domConstruct.create("div", {
          innerHTML: this.containerTmpl.replace("{tmpl}", contents)
        })

        parser.parse(node, {noStart: true}).then(function(widgetList) {
          view.addChild(widgetList[0])
        })
      },

      // 解析部件
      parseComponent: function(content, container) {
        if (!content.tmpl) {
          return
        }
        var tmpl = content.tmpl
        this.parseTmpl(tmpl, container)
      },

      // 解析卡片，同时兼容门户现有卡片
      parseCard: function(content, container) {
        var card = new Card(content)
        container.addChild(card)
      },
      
      // 解析多标签
      parseTabsCard: function(content, container) {
    	  var tabs = new TabsCard(content);
    	  container.addChild(tabs);
      },

      parseTmpl: function(tmpl, container) {
        var conNode = domConstruct.create(
          "div",
          {innerHTML: tmpl},
          container.domNode
        )

        parser.parse({
          rootNode: conNode,
          noStart: false
        })
      },

      buildRendering: function() {
        this.inherited(arguments)
        this.subscribe(this.VIEW_CHANGE, "onViewChange")
        if (this.tabbars.length == 1) {
          domClass.add(this.domNode, "muiModuleSgl")
        }
        array.forEach(
          this.tabbars,
          function(tabbar, index) {
            var view = new TabView({index: index})
            this.addChild(view)
            if (this.index == index) {
              this.showView(index)
              this.defer(function() {
                view.resize()
              }, 1)
            }
          },
          this
        )
        // 渲染底部页签
        this.renderTabbar()
      },

      // 切换到对应的视图
      showView: function(index) {
        this.parseView(index)
        var self = this
        require(["dojox/mobile/TransitionEvent"], function(TransitionEvent) {
          setTimeout(function() {
            new TransitionEvent(document.body, {
              moveTo: self.getChildren()[index].id,
              transition: "slide"
            }).dispatch()
          }, 1)
        })
      },

      // 监听视图切换事件
      onViewChange: function(obj, evt) {
        if (!evt) {
          return
        }
        this.showView(evt.index)
      },

      // 渲染底部页签
      renderTabbar: function(tabbars) {
        if (this.tabbars && this.tabbars.length <= 1) {
          return
        }
        this.addChild(new Tabbar({datas: this.tabbars}))
      }
    }
  )
})
