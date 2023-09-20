define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/readlog/mobile/js/list/AccessLogItemMixin",
	"dojo/dom-construct",
	"mui/dialog/Dialog",
	'dojo/_base/lang',
	'dojo/dom-style',
	 "dojo/html",
	 "dijit/registry",
    "dojo/topic",
	"mui/util",
	"dojo/query",
	"mui/i18n/i18n!sys-readlog:mui.dialog",
	"mui/i18n/i18n!sys-readlog:mui.panel"
	], function(declare, _TemplateItemListMixin, AccessLogItemMixin, domConstruct, Dialog, lang, domStyle, html, registry, topic,util,query, msg, msg1) {
	
	return declare("sys.readlog.mobile.js.list.AccessLogItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: AccessLogItemMixin,
		
		modelId:null,
		
		modelName:null,
		
		dialogTmpl:"<div data-dojo-type='mui/list/StoreElementScrollableView'><ul data-dojo-type='sys/readlog/mobile/js/list/AccessLogItemList' data-dojo-props='dialog:true,modelId:\"{categroy.modelId}\",modelName:\"{categroy.modelName}\"' data-dojo-mixins='sys/readlog/mobile/js/list/AccessLogItemListMixin'></ul></div>",

		postCreate:function(){
			this.inherited(arguments);
			this.subscribe("accesslog/nav/list/sizeupdate","updateReadLogSize");
		},

		updateReadLogSize:function(wgt,data){
			var size = data.size;
			this.readLogSize = size;
			if(query(".updateReadLogSize",this.domNode).length > 0){//已经构建过，直接更新内容
				query(".updateReadLogSize",this.domNode)[0].innerHTML = msg["mui.dialog.title"] +"("+size+")";
			}
		},

		doLoad:function(){
			if(this.url && util.getUrlParameter(this.url,"isNavList") == "true"){
				this.url = util.setUrlParameter(this.url,"method","viewReadLog4Mobile");
			}
			this.inherited(arguments);
		},
		
		onComplete: function(items) {
			var dataType = this.getDataType(items);
			if(dataType == "readLog" && !this.dialog && !this.titleNode){
				this.titleNode = this.buildTitleNode(items.datas || items);
				domConstruct.place(this.titleNode,this.domNode,"before");
			}
			this.inherited(arguments);
            topic.publish("/mui/navView/resize", this, {key:0});
		},
		
		getDataType:function(items){
			var dataType;
			if(this.url && util.getUrlParameter(this.url,"isNavList") == "true"){
				return "readLog";
			}
			if(items && items.datas && items.datas.length > 0){
				for (var i = 0; i < items.datas[0].length; i++) {
					var elem = items.datas[0][i];
					if(elem.col != 'dataType')
						continue;
					dataType = elem.value;
					break;
				}
			}
			return dataType;
		},
		
		buildTitleNode:function(datas){
			var titleNode = domConstruct.create("div",{className:"muiAccordionPanelTitle"});
			var leftNode = domConstruct.create("div",{className:"",innerHTML:msg1["mui.panel.title"]+"("+datas.length+")"},titleNode);
			var rightNode = domConstruct.create("div",{className:"panelTitileRight readLogListSize"},titleNode);
			domConstruct.create("i",{className:""},rightNode);
			if (typeof (this.readLogSize) == "undefined"){
				// 165210 移动端访问统计阅读记录计数显示undefined，需要清理缓存。在if这里做判断，当值不存在就等‘updateReadLogSize’执行后执行，因为这里会加载两次（不知原因），所以做个存在判断，使只执行一次。
				dojo.connect(this,"updateReadLogSize",this,function (){
					if(rightNode.innerHTML.indexOf(msg["mui.dialog.title"]) < 0){
						rightNode.innerHTML = rightNode.innerHTML + msg["mui.dialog.title"] +"("+this.readLogSize+")";
						this.connect(rightNode,"click","openLogDialog");
					}
				});
			}else{
				rightNode.innerHTML = rightNode.innerHTML + msg["mui.dialog.title"] +"("+this.readLogSize+")";
				this.connect(rightNode,"click","openLogDialog");
			}
			return titleNode;
		},
		
		_createItemProperties: function(item) {
			var props = this.inherited(arguments);
			props['dialog'] = this.dialog;
			return props;
		},
		
		//打开详细日志的窗口
		openLogDialog:function(){
			var _self = this;
			var dialogContentNode = domConstruct.create("div",{className:""});
			 var dhs = new html._ContentSetter({
				 node: dialogContentNode,
		          parseContent: true,
		          cleanContent: true,
		          onBegin: function() {
		        	 this.content = lang.replace(this.content,{categroy:_self});
		            this.inherited("onBegin", arguments)
		          }
		        })

		        dhs.set(_self.dialogTmpl);
		        dhs.parseDeferred.then(function(results) {
		        	_self.parseResults = results;
		        	var title = msg['mui.dialog.title'];
					this.dialog = Dialog.element({
						title:title || '',
						canClose : true,
						element : dialogContentNode,
						buttons : [],
						position:'bottom',
						scrollable : false,
						parseable : false,
						showClass : 'accessLogDialog',
						_addDialogEventListener:function(){},
						callback : lang.hitch(this, function(win,evt) {
							this.dialog = null;
							//销毁组件
							for(var index in results){
								if(results[index].destroy)
									results[index].destroy();
							}
							
						}),
						onDrawed:lang.hitch(this, function(evt) {
							evt.closeNode.innerHTML = msg['mui.dialog.button.close'];
							
							var contentHeight = document.documentElement.clientHeight*0.8;
							 if(evt.privateHeight){
								 contentHeight=evt.privateHeight
							 }
							 //减去头部高度
							 if(evt.divNode){
								contentHeight = contentHeight - evt.divNode.offsetHeight;
							 }
							 //减去按钮栏高度
							 if(evt.buttonsNode){
								contentHeight = contentHeight - evt.buttonsNode.offsetHeight;
							 }
							 domStyle.set(evt.contentNode, {
								   'height' : contentHeight + 'px'
							 });
							 
							 domStyle.set(results[0].domNode, {
								   'height' : contentHeight + 'px'
							 });
						})
					});
		        })
		        dhs.tearDown()
		}
	});
});