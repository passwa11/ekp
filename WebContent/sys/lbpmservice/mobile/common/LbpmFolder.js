define(["dojo/_base/declare",
	"dijit/_WidgetBase", 
	"dojo/query",
	"dojo/dom",
	"dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-construct",
	"dojox/mobile/_css3",
	"dojo/fx",
	"mui/folder/_Folder",
	"dojo/topic",
	"dojo/_base/lang",
	"dojo/Deferred",
	"mui/i18n/i18n!sys-lbpmservice:lbpmservice.view.openMorenActionView",
	"mui/i18n/i18n!sys-lbpmservice:lbpmservice.view.closeMorenActionView"],
		function(declare,WidgetBase,query,dom,domClass,domStyle, domConstruct,css3,fx,_Folder,topic,lang,Deferred,msg1,msg2){
	
	return declare("sys/lbpmservice/mobile/common/LbpmFolder",[WidgetBase,_Folder],{
		expandDomId:null,//需要操作的对象domid
		
		isExpand : false,
		
		//tempUrl:null,
		
		//jsUrl:null,
		
		hideStyle:{},
		
		showStyle:{},
		
		showType:null,
		
		buildRendering:function(){//构建dom
			this.inherited(arguments);
			this.boxNode = domConstruct.create(
					"div",
					{
						className:"mui-lbpm-foldOrUnfold-box"
					},
					this.domNode
			);
			this.textNode = domConstruct.create(
					"span",
					{
						className:"mui-lbpm-foldOrUnfold-box-text",
						innerHTML:msg1["lbpmservice.view.openMorenActionView"]
					},
					this.boxNode
			);
			this.downIconNode = domConstruct.create(
					"i",
					{
						className:"mui-lbpm-foldOrUnfold-box-icon icon-drop-down"
					},
					this.boxNode
			);
			this.upIconNode = domConstruct.create(
					"i",
					{
						className:"mui-lbpm-foldOrUnfold-box-icon icon-drop-up"
					},
					this.boxNode
			);
			domStyle.set(this.upIconNode,{
				"display":"none"
			})
		},
		
		startup : function() {
			this.inherited(arguments);
		},
		
		show: function(evt){
			if(this.isExpand){
				//时间为10是为了避免有些设备闪屏问题（未真正解决）
				fx.wipeOut({  
					node: dom.byId(this.expandDomId),
					duration: 10
				}).play();
				domStyle.set(this.upIconNode,{
					"display":"none"
				})
				domStyle.set(this.downIconNode,{
					"display":""
				})
				this.textNode.innerHTML = msg1["lbpmservice.view.openMorenActionView"];
			}else{
				var deferred = new Deferred();
				deferred.then(lang.hitch(this,function(){
					var domOffsetTop = this._getDomOffsetTop(this.domNode);
			        var target = window;
			        if(this.showType == 'dialog'){
			        	target = query(".muiLbpmDialog .muiDialogElementContent_bottom")[0];
			        }
			        this._scrollTo(target,{ y: 0 - domOffsetTop + 110 });
				}));
				fx.wipeIn({  
					node: dom.byId(this.expandDomId),
					duration: 100
				}).play();
				domStyle.set(this.downIconNode,{
					"display":"none"
				})
				domStyle.set(this.upIconNode,{
					"display":""
				})
				this.textNode.innerHTML = msg2["lbpmservice.view.closeMorenActionView"];
				this.defer(function(){
					deferred.resolve();
				},150);
			}
			this.isExpand = !this.isExpand;
		},
		
	    _scrollTo: function (obj, evt) {
	    	var y = 0;
	    	if (evt) {
	    		y = evt.y || 0;
	    	}

	    	var end = -y;
	    	var start = this.getPos(obj).y;
	    	var diff = end - start; // 差值
	    	if(diff <= 20){
	    		return;
	    	}
	    	var t = 300; // 时长 300ms
	    	var rate = 30; // 周期 30ms
	    	var count = 10; // 次数
	    	var step = diff / count; // 步长
	    	var i = 0; // 计数
	    	var timer = window.setInterval(function () {
	    		if (i <= count - 1) {
	    			start += step;
	    			i++;
	    			obj.scrollTo(0, start);
	    		} else {
	    			window.clearInterval(timer);
	    		}	
	    	}, rate);
	    },
	    
	    // 获取当前滚动位置
	    getPos: function (obj) {
	    	if(obj == window){
	    		return { y: document.documentElement.scrollTop || document.body.scrollTop };
	    	}else{
	    		return { y: obj.scrollTop };
	    	}
	    },
		
		_getDomOffsetTop: function (node) {
		    var offsetParent = node;
		    var nTp = 0;
		    while (offsetParent != null && offsetParent != document.body) {
		      nTp += offsetParent.offsetTop;
		      offsetParent = offsetParent.offsetParent;
		    }
		    return nTp;
	    }
	});
});