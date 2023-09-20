define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var dialog = require('lui/dialog');
	var Editbox = require('./Editbox');
	var AsideWidget = require('./AsideWidget');
	
	var msg = require("lang!sys-portal:desgin.msg");
	
	var AsideEditable = Editbox.extend({
		
		initialize : function($super,config) {
			$super(config);
		},
		buildMenu : function($super) {
			var self = this;
			this.menuItems = [];	
			return $super(this.menuItems);
		},
		onShow : function($super){
			$super();
			//back hack
			this.editMenu.hide();
		},
		startup : function($super){
			$super(); 
			var hasWidget = $('[portal-type]',this.element).length > 0;
			var self = this;
			setTimeout(function(){
				self._initPersonHead();
				self._initNav();
			},1);
		},
		destroy : function(){			
			this.editMenu.remove();
			for(var i=this.children.length-1;i>=0;i=this.children.length-1){
				this.children[i].destroy();				
			}
			this.element.remove();
			//父容器中删除自己
			var x = $.inArray(this,this.parent.children);
			this.parent.children.splice(x,1);
			try{delete this;}catch(e){};
		},
		_initPersonHead : function(){
			var self = this,
				innerType = 'personHead',
				dom = $('[portal-innerType="'+ innerType +'"]',this.element);
			if(dom.length == 0){
				this.personHeadWidget = this.__renderWidget({
					portlet : [{
						title : '个人头像',
						format : 'sys.ui.html',
						sourceId : 'sys.person.head.tab.source',
						renderId : 'sys.ui.html.default'
					}]
				},{
					innerType : innerType,
					preview : PersonHeadPreview
				});
			}else{
				var designer = this.parent,
					widgetId = dom.attr('portal-key'),
					widget = designer.instances[widgetId];
				widget.preview = new PersonHeadPreview(widget);
				this.personHeadWidget = widget;
			}
//			this.personHeadWidget.rebuildMenu([{
//				icon : 'editMenuIconConfig',
//				text : '头像配置' ,
//				fn : function(value){
//					self._configPersonHead();
//				}
//			}]);
			
		},
		_configPersonHead : function(){
			
		},
		_initNav : function(){
			var self = this,
				innerType = 'nav',
				dom = $('[portal-innerType="'+ innerType +'"]',this.element);
			if(!dom || dom.length == 0){
				this.navWidget = this.__renderWidget({
					portlet : [{
						title : '系统导航',
						format : 'sys.ui.sysnav',
						sourceId : 'sys.portal.sysnav.source',
						sourceOpt : { fdId : {} },
						renderId : 'sys.ui.sysnav.default'
					}]
				},{	
					innerType : 'nav',
					preview : NavPreview
				});
			}else{
				var designer = this.parent,
					widgetId = dom.attr('portal-key'),
					widget = designer.instances[widgetId];
				widget.preview = new NavPreview(widget);
				this.navWidget = widget;
			}
			this.navWidget.rebuildMenu([{
				icon : 'editMenuIconConfig',
				text : '配置导航' ,
				fn : function(value){
					self._configNav(value);
				}
			}]);
		},
		_configNav : function(){
			var self = this;
			dialog.iframe('/sys/portal/varkind/selectAppNav.jsp','配置导航',function(value){
				if(!value){ return;}
				var cfg = self.__formatCfg({
					portlet : [{
						title : '',
						format : 'sys.ui.sysnav',
						sourceId : 'sys.portal.sysnav.source',
						sourceOpt : {
							fdId : { fdId : value.fdId,  fdName : value.fdName }
						},
						renderId : 'sys.ui.sysnav.default'
					}]
				});
				var w = self.navWidget,
					element = w.element;
				element.children("script[type='text/config']").remove();
				element.prepend("<script type='text/config'>"+JSON.stringify(cfg)+"</script>");
				w.setting = cfg;
				w.preview.render();
			},{width : "750",height : "500"});
		},
		__renderWidget : function(cfg, options){
			var cfg = this.__formatCfg(cfg);
			var temp = $('<div />');
			temp
				.append("<script type='text/config'>"+JSON.stringify(cfg)+"</script>")
				.attr('portal-key',this.uuid())
				.attr('portal-type','./AsideWidget')
				.attr('portal-innerType',options.innerType)
				.css({ 'padding' : '0','border' : '1px #97C1F2 solid' });
			var w = new AsideWidget({
				body : this.body,
				element : temp,
				preview : options.preview,
				opt : options.opt || []
			});
			w.setting = cfg;
			w.setParent(this);
			this.addChild(w);
			this.element.append(temp);
			w.startup();
			return w;
		},
		__formatCfg : function(cfg){
			return $.extend(cfg,{
				panel : 'panel',
				panelType : 'none',
				layoutId : 'sys.ui.nonepanel.default',
				layoutOpt : {},
				heightExt : 'auto'
			});
		}
		
	}); 
	
	var PersonHeadPreview = function(widget){
		this.widget = widget;
	};
	
	PersonHeadPreview.prototype = {
		render : function(){
			if(this.widget.infoView == null){
				if(this.widget.element.children(".preview").length > 0){
					this.widget.infoView = this.widget.element.children(".preview");
				}else{
					this.widget.infoView = $("<div class='preview'></div>").appendTo(this.widget.element);
				}
			}
			this.widget.infoView.empty();
			if(this.widget.setting == null){
				this.widget.infoView.append("<div>部件配置信息丢失</div>");
			}else{			
				this.__preview__(this.widget.infoView);
			}
		},
		__preview__:function(xdiv){
			var setting = this.widget.setting,
				portlet = setting.portlet && setting.portlet.length > 0 ? setting.portlet[0] : null;
			if(!portlet){
				return;
			}
			var url = Com_Parameter.ContextPath + 'sys/person/sys_person_zone/sysPersonZone.do?method=personHead&showLoginTime=true',
				defer = $.ajax(url);
			defer.then(function(html){
				xdiv.append(html);
			});
		}
	};
	
	var panel = require('sys/ui/extend/dataview/render/sysnav/panel');
	var NavPreview = function(widget){
		this.widget = widget;
	};
	NavPreview.prototype = {
		render : function(){
			if(this.widget.infoView == null){
				if(this.widget.element.children(".preview").length > 0){
					this.widget.infoView = this.widget.element.children(".preview");
				}else{
					this.widget.infoView = $("<div class='preview'></div>").appendTo(this.widget.element);
				}
			}
			this.widget.infoView.empty();
			if(this.widget.setting == null){
				this.widget.infoView.append("<div>部件配置信息丢失</div>");
			}else{			
				this.__preview__(this.widget.infoView);
			}
		},
		__preview__:function(xdiv){
			var setting = this.widget.setting,
				portlet = setting.portlet && setting.portlet.length > 0 ? setting.portlet[0] : null;
			if(!portlet){
				return;
			}
			var fdId = portlet.sourceOpt.fdId.fdId || '';
			if(fdId){
				var url = Com_Parameter.ContextPath + 'sys/portal/sys_portal_nav/sysPortalNav.do?method=portlet&fdId=' + fdId,
					defer = $.ajax(url);
				defer.then(function(datas){
					var accordionPanel = panel.buildAccordionPanel(datas);
					xdiv.append(accordionPanel.element);
				});
			}else{
				var xdivnav = $("<div class='preview_panel_nav'></div>");
				var xdivitems = $("<div class='preview_panel_nav_items'></div>");
				xdivnav.append(xdivitems);
				var xtitle = $("<span class='preview_panel_nav_item_2'></span>");
				xtitle.html(env.fn.formatText(this.widget.setting.portlet[0].title));
				xtitle.addClass("current");
				xdivitems.append("<span class='preview_panel_nav_item_f'></span>");
				xdivitems.append(xtitle);
				var xcontent = $("<div class='preview_panel_contnet'></div>");
				xcontent.append("<div>未配置</div>");
				xdiv.append(xdivnav);
				xdiv.append(xcontent);
			}
		}
	};
	
	module.exports = AsideEditable;
	
});