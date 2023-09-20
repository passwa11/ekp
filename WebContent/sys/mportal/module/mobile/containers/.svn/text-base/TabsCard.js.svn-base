/**
 * 多标签
 */
define(["dojo/_base/declare", "dijit/_WidgetBase", "dijit/_Contained", "dijit/_Container",
  "mui/util", "dojo/_base/lang", "dojo/dom-construct", "dojo/parser", "dojo/_base/array",
  "dojo/dom-class", "dojo/dom-style", "dojo/on", "mui/i18n/i18n!sys-mportal:sysMportalPage.more"], 
  function(declare, _WidgetBase, _Contained, _Container, util,
  lang, domConstruct, parser, array, domClass, domStyle, on, msg) {
  return declare("sys.mportal.module.TabsCard", [_WidgetBase, _Contained, _Container], {
	  
	  title: {},
	  
      contents: [],
      
      selected: "selected",
      
      tabsArr : null,
      
      conNodeArr : null,

      buildRendering: function() {
    	  this.inherited(arguments);
    	  this.parseTabs();
      },

      parseTabs: function() {
    	  // 卡片头
    	  this.parseTitle();
    	  // 内容
    	  this.parseContent(0);
      },
      
      parseTitle: function() {
    	  
    	  var title = '';
    	  if (this.title && this.title.text) {
    		  title = this.title.text;
    	  }
    	  
    	  var titleDomNode = domConstruct.create('div', {
    		  className: 'muiModuleCardTitle', 
    		  innerHTML: title
    	  }, this.domNode);
    	  
    	  // 多标签按钮
    	  var muiModuleTabsContainer = domConstruct.create('div', {
    		  className: 'muiModuleTabsContainer'
    	  }, titleDomNode);
    	  
    	  var self = this;
    	  array.forEach(this.contents, function(content, index){
    		  
    		  var tabBtn = domConstruct.create('div', {
    			  className: 'muiFontSizeS',
        		  innerHTML: content.name
        	  }, muiModuleTabsContainer);
    		  
    		  if(self.tabsArr == null)
    			  self.tabsArr = new Array();
    		  self.tabsArr.push(tabBtn);

    		  if(index == 0) {
    			  self.selectedIndex = 0;
    			  domClass.add(tabBtn, self.selected);
    		  }
    		  
    		  on(tabBtn, 'click', function(index){
    			  return function(){
    				  self.onClick(index);
    			  }
    		  }(index));
    	  });
      },
      
      onClick: function(index) {

    	  if(this.selectedIndex == index) 
    		  return;
    	  
    	  this.changeConNode(index);
    	  
    	  var oldSelected = this.tabsArr[this.selectedIndex];
    	  domClass.remove(oldSelected, this.selected);
    	  
    	  this.selectedIndex = index;
    	  var newSelected = this.tabsArr[this.selectedIndex];
    	  domClass.add(newSelected, this.selected);
    	  
      },
      
      onClickMore: function(moreUrl){
    	  window.location.href = util.formatUrl(moreUrl);
      },
      
      parseTmpl: function(tmpl, moreUrl, index) {
    	  var conNode = domConstruct.create('div', {
    		  innerHTML: tmpl
    	  }, this.domNode);
    	  
    	  if(moreUrl) {
    		  var moreContainer = domConstruct.create('div', {
    			  className: 'muiModuleTabsMoreContainer',
    		  }, conNode);
    		  
    		  var more = domConstruct.create('div', {
    			  className: 'muiModuleTabsMore',
    			  innerHTML: '<i class="fontmuis muis-more"></i>' + msg['sysMportalPage.more']
    		  }, moreContainer);
    		  
    		  var self = this;
    		  on(more, 'click', function(moreUrl){
    			  return function(){
    				  self.onClickMore(moreUrl);
    			  }
    		  }(moreUrl));
    	  }

    	  if(this.conNodeArr == null)
    		  this.conNodeArr = new Array();
    	  this.conNodeArr[index] = conNode;
    	  
    	  parser.parse({
    		  rootNode: conNode,
    		  noStart: false
          }).then(function(widgets) {
        	  if (widgets.length == 0) 
        		  return;
        	  
        	  if (!widgets[0].tile) 
        		  domClass.add(conNode, "muiModuleCardContent");
          });
      },
      
      parseContent: function(index) {
    	  
    	  var content = this.contents[index];
    	  if(content) {
    		  if (content.tmpl) {
    			  this.parseTmpl(content.tmpl, content.moreUrl, index);
    			  return;
    		  }
    		  
    		  // 增加`!`，方便后面解析参数
    		  var urls = content.url.split("?");

    		  if (urls.length > 1) {
    			  urls[1] = "?" + urls[1];
    		  } else {
    			  urls[1] = "";
    		  }
    		  
    		  content.url = urls.join("!");
    		  
    		  require([util.formatUrl(content.url)], lang.hitch(this, function(tmpl) {
    			  //带样式
    			  if (typeof tmpl == "object") {
    				  var cssUrls = tmpl.cssUrls;
    				  if (cssUrls) {
    					  for (var i = 0; i < cssUrls.length; i++) {
    						  util.loadCSS(cssUrls[i]);
    					  }
    				  }
    				  tmpl = tmpl.html;
    			  }
    			  this.parseTmpl(tmpl, content.moreUrl, index);
    		  }));
    	  }
    	  
      },
      
      changeConNode : function(index){
    	  
    	  var oldNode = this.conNodeArr[this.selectedIndex];
    	  domStyle.set(oldNode, 'display', 'none');
    	  
    	  var newNode = this.conNodeArr[index];
    	  if(!newNode) {
    		  this.parseContent(index);
    	  } else {
    		  domStyle.set(newNode, 'display', 'block');
    	  }
      }
    })
})
