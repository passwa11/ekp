define(["dojo/_base/declare",
        "dojo/query", 
        "dojo/touch",
        "mui/util",
        "dojo/_base/lang",
        "dojo/_base/array",
        "dojo/on",
        "sys/lbpmservice/mobile/audit_note_ext/_CanvasView", 
        "mui/i18n/i18n!sys-lbpmservice",
        "mui/device/adapter",
        "dojo/topic", 
        "mui/device/device",
        "dojo/dom-class"], 
        function(declare, query, touch, util, lang, array, on, _CanvasView, msg, adapter,topic,device,domClass) {

	return declare("sys.lbpmservice.mobile.audit_note_ext.hand.AuditNoteHandlerView", [_CanvasView], {
		
		descriptionDiv: null,
		
		buttonDiv: null,
		
		startup : function() {
			this.inherited(arguments);
			this.createBtn();
			this.bindEvents();
			var _self=this;
			window.addEventListener('resize',function(){
				_self.resize();
			},false);
		},
		
		createBtn: function() {
			var obj = query(this.buttonDiv);
			var html = '<div class="handingWay" id="handwriting">';
			html +='<div class="iconArea"><i class="mui mui-handwrite"></i></div><span class="iconTitle">'+msg['mui.lbpmNode.usage.handwrite']+'</span></div>';
			obj.append(html);
			query("#handwriting").on("click", lang.hitch(this, this.showAuditNoteHandlerView));
		},
		
		showAuditNoteHandlerView:function(){
			this._canvasKey = "auditNote";
			var deviceType = device.getClientType();
			if(deviceType==9 || deviceType==10){
				var ret = adapter.getSignImage({options:this});
				if(ret){
					return;
				}
			}
			if(dojoConfig.dingXForm == "true"){
    			$(this.fixedHeader).css('display','none'); 			
			}
			topic.publish("/lbpm/operation/hideDialog",this);
			this.inherited(arguments);
		},
		
		bindEvents: function() {
			lbpm.events.addListener(lbpm.constant.EVENT_validateMustSignYourSuggestion,function() {
				var imageDiv = query(".auditNoteHandlerImgUl");
				return (imageDiv.children().length > 0 );
			});
			on(query(this.descriptionDiv).parent()[0], on.selector("#imgUl li .btn_canncel_img", touch.press), lang.hitch(this, this.auditNoteViewDelete));
			this.inherited(arguments);
		},
		
		auditNoteViewDelete : function(evt){
			if (evt.target) {
				var obj = evt.target.tagName == "SPAN" ? evt.target : query(evt.target).parent("span")[0];
				if (obj == null)
					return;
				this.fileIds = array.filter(this.fileIds, function(fileId) {
					return fileId != obj.id;
				});
		        query("#li_" + obj.id).remove();
		        var imageDiv = query("#imgUl");
		        var liDiv = query("li","imgUl");
		        if(liDiv.length == 1){
		        	query("li","imgUl").forEach(function(domObj){
						 domClass.remove(domObj,"many");
					 })
		        }else if(liDiv.length == 0){
		        	topic.publish("/lbpm/ext/auditNoteHandler/change",this);
		        }
		        //e人e本清除事件
		        topic.publish("attachmentObject_delete_ereb", this ,{});
			}
		},
		
		/**
		 * 手写附件上传成功事件处理函数
		 */
		
		AttUploadSuccess:function(srcObj , evt){
		/*	if (this._canvasKey === "auditNote"){*/
				this.inherited(arguments);
				this._canvasKey = "";
		/*	}*/
		},
		
		/**
		 * 画笔选择事件
		 */
		selectPenChange:function(evt){
			if(evt.name=='selectPenWidth' && this._canvasKey == "auditNote"){
				this.dowidthchange();
			}
			if(evt.name=='selectPenColor' && this._canvasKey == "auditNote"){
				this.docolorchange();
			}
		},
		
		hideAuditNoteHandlerView : function() {
			if(dojoConfig.dingXForm == "true"){
     			$(this.fixedHeader).css('display','');			
			}
			if (this._canvasKey === "auditNote"){
				this.inherited(arguments);
				this._canvasKey = null;
			}
			topic.publish("/lbpm/operation/showDialog",this,{"handwriting":true});
		},
		
		auditNoteViewClear: function() {
			if (this._canvasKey === "auditNote"){
				this.canvas.clear();
			}
		},
		
		UploadSuccessed:function(srcObj , evt){
			var file = evt.file;
			if(device && (device.getClientType() == 7 || device.getClientType() == 8|| device.getClientType() == 9 || device.getClientType() == 10)){
				this.curDisplayHref = file.href;//kk客户端还是走原来的逻辑
			}else{//非kk客户端走新的逻辑
				var displayCanvas = this.fitCanvasY();//获取用于显示的画布
				if(displayCanvas){
					this.curDisplayHref = displayCanvas.toDataURL("image/png");
					var displayFiles = this.displayFiles || {};
					displayFiles[file.filekey] = this.curDisplayHref;
				}
			}
			if(!this.curDisplayHref){
				this.curDisplayHref = file.href;
			}
			var imageDiv = query("#imgUl");
			var html = '<li class="imgLi" id="li_'+file.filekey+'"><div class="img_wrapper">';
			    html +='<span class="btn_canncel_img fontmuis muis-epid-close" id="'+file.filekey+'"></span>';
				html +='<div class="img_content"><img width="100" height="auto" src="'+this.curDisplayHref+'"></div></div></li>';
			if(imageDiv.length > 0){
				if(imageDiv.innerHTML() == ""){
					 imageDiv.innerHTML(html);
					 query("li","imgUl").forEach(function(domObj){
						 domClass.remove(domObj,"many");
					 })
				}else{
					 imageDiv.append(html);
					 query("li","imgUl").forEach(function(domObj){
						 domClass.add(domObj,"many");
					 })
				}
		    }else{
			    var rowhtml = '<div class="tab_img"><ul id="imgUl" class="auditNoteHandlerImgUl">'+html+'</tr></ul></div>';
			    query(this.descriptionDiv).after(rowhtml);
		    }
			topic.publish("/lbpm/ext/auditNoteHandler/change",this);
		},
		
		onAfterTransitionOut:function(){
			this.inherited(arguments);
			if(this.showType != 'dialog'){
				//滚动定位到手写位置
				var domOffsetTop = this._getDomOffsetTop(query("#handwriting")[0]);
		        var target = window;
		        this._scrollTo(target,{ y: 0 - domOffsetTop + 110 });
			}
		},
		
		_scrollTo: function (obj, evt) {
	    	var y = 0;
	    	if (evt) {
	    		y = evt.y || 0;
	    	}

	    	var end = -y;
	    	var start = this.getPos(obj).y;
	    	var diff = end - start; // 差值
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