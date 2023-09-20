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
        "dojo/dom-construct",
        "dojo/dom-style",
        "dijit/registry",
        "mui/i18n/i18n!sys-xform-base",
        "dojo/ready",
        "dojo/dom-class",
        "dojo/NodeList-manipulate"], 
        function(declare, query, touch, util, lang, array, on, _CanvasView, msg, adapter,topic,device,domConstruct,domStyle,registry,msg,ready,domClass) {

	return declare("sys.xform.mobile.controls.newAuditNote.NewAuditNote", [_CanvasView], {
		
		//控件id
		fdId:'',
		
		//选择类型
		mould:'',
		
		//节点或者处理人
		value:'',
		
		//当前处理人是否在处理人配置中
		resValue:null,

		descriptionDiv: null,
		
		fdType : "newAuditNote",
		
		buttonDiv: null,
		
		isXformAuditNote:true,
		
		fromView:"lbpmView",
		
		toView:"scrollView",
		
		startup : function() {
			this.inherited(arguments);
			this.buttonDiv = "#" + this.fdId;
			this.createBtn();
			this.descriptionDiv = "#handwriting_" + this.fdId;
			if (this.isDingUI()) {
				this.descriptionDiv = "#handwritingwrap_" + this.fdId;
				domClass.add(query(this.buttonDiv)[0], 'dingNewAuditNote');
 			}
			this.bindEvents();
		},
		
		/**
		 *手写图标
		 */
		createBtn: function() {
			var obj = query(this.buttonDiv);
			var html = "";
			if (this.isDingUI()) {
				html += '<div class="handingWayWrap" id="handwritingwrap_' + this.fdId + '">';
			}
			html += '<div class="handingWay" id="handwriting_' + this.fdId + '">';
			html +='<div class="iconArea">+ ' +msg['mui.newAuditNote.handwrite'] +'</div></div>';//<i class="mui mui-handwrite"></i>
			if (this.isDingUI()) {
				html += '</div>';
			}
			obj.append(html);
			query("#handwriting_" + this.fdId).on("click", lang.hitch(this, this.showAuditNoteHandlerView));
		},
		
		isDingUI: function(){
			return dojoConfig.dingXForm === "true";
		},
		
		/**
		 * 显示画板
		 */
		showAuditNoteHandlerView:function(){
			//先移除校验，否则，校验不通过，不会切换
			this.view = registry.byId(this.toView);
			if (this.view){
				this.view.validateNext = false;
			}
			this._canvasKey = this.fdId;
			var deviceType = device.getClientType();
			if(deviceType==9 || deviceType==10){
				var ret = adapter.getSignImage({options:this});
				if(ret){
					return;
				}
			}
			this.inherited(arguments);
			//this.resize();
		},
		
		bindEvents: function() {
			/*lbpm.events.addListener(lbpm.constant.EVENT_validateMustSignYourSuggestion,function() {
				var imageDiv = query(".auditNoteHandlerImgUl");
				return (imageDiv.children().length > 0 );
			});*/
			this.subscribe("initComplete", lang.hitch(this, this._isShow));
			on(query(this.descriptionDiv).parent()[0], on.selector("#imgUl_" + this.fdId + " li .btn_canncel_img", touch.press), lang.hitch(this, this.auditNoteViewDelete));
			this.inherited(arguments);
			//kk入口进去的，很奇怪，控件上传的手写会丢失，所以这里由控件自己监听提交事件,浏览器进去的
			//手写控件不用监听提交事件，统一由原来的流程中的手写监听
			var deviceType = device.getClientType();
			if(deviceType==9 ){//|| deviceType==10
				ready(lang.hitch(this,function(){
					Com_Parameter.event["submit"].push(lang.hitch(this, this.submitEvent));
				}));
			}
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
		        //调用流程的手写清除事件,不然，因为同步，在手写控件这边删掉的手写还是会被保存
		        var widget = registry.byId("auditNoteHandlerView");
		        widget.fileIds = array.filter(widget.fileIds, function(fileId) {
					return fileId != obj.id;
				});
		        //e人e本清除事件
		        topic.publish("attachmentObject_delete_ereb", this ,{});
			}
		},
		
		/**
		 * 隐藏手写板,跳到指定view
		 */
		hideAuditNoteHandlerView : function() {
			//因为父类此方法会默认切回到lbpmView,所以这里先指定切回到scrollView
			this.lbpmViewName = this.toView;
			this.inherited(arguments);
			this.lbpmViewName = this.formView;
			this._canvasKey = null;
			if (this.view){
				this.view.validateNext = true;
			}
		},
		
		/**
		 * 手写附件上传成功事件处理函数，重写父类此方法,否则，所有的手写组件都会调用此方法
		 */
		
		AttUploadSuccess:function(srcObj , evt){
			if (this._canvasKey == this.fdId){
				this.inherited(arguments);
				this._canvasKey = null;
				this.defer(function(){
					topic.publish("/mui/list/resize");
				}, 0);
			}
		},
		
		/**
		 * e人e本
		 */
		AttUploadSuccessEreb:function(srcObj , evt){
			/*if (this._canvasKey == this.fdId){*/
				query("#imgUl_" + this.fdId + " li").remove();
				this.fileIds = [];
				
				if (evt == null || evt.file==null) {
					this.workDone();
					return;
				}
				/*var file = evt.file;*/
				/*this.fileIds.push(file.filekey);*/
				this.UploadSuccessed(srcObj , evt);
				topic.publish("/mui/list/resize");
				/*this.hideAuditNoteHandlerView();
				this.auditNoteViewClear();*/
				this.workDone();
				
			/*}*/
		},
		
		/**
		 * 画笔选择事件
		 */
		selectPenChange:function(evt){
			if(evt.name=='selectPenWidth' && this._canvasKey == this.fdId){
				this.dowidthchange();
			}
			if(evt.name=='selectPenColor' && this._canvasKey == this.fdId){
				this.docolorchange();
			}
		},
		
		/**
		 * 根据模板配置显示此控件
		 * 
		 */
		_isShow:function(){
			var canShow = false;
			//按节点
			if(this.mould=='21'){
				var setNodes=this.value.split(";");
				var hasIn=false;
				for(var i=0;i<setNodes.length;i++){
					if(lbpm['nowNodeId']==setNodes[i]){
						hasIn=true;
						break;
					}
				}
				canShow = hasIn;
			}else{//按人
				canShow = (this.resValue == 'true');
			}
			if (canShow){
				//由于_CanvasView的startup方法把this.domNode放在view下面,所以重新调整下this.domNode的位置
				//可能是this.lbpmViewName所指向的dom还没加载，这行代码放在startup里面会报错，所以放在此处
				query("#"+this.fromView).parent().append(this.domNode);
				domStyle.set(this.fdId,"display","inline-block");
			}
		},
		
		UploadSuccessed:function(srcObj , evt){
			var file = evt.file;
			var imageDiv = query("#imgUl_" + this.fdId);
			var __width = 100;
			var __height = "auto";
			if (this.isDingUI()) {
				__width = 157;
				__height = 157;
			}
			var html = '<li id="li_'+file.filekey+'"><div class="img_wrapper">';
			    html +='<span class="btn_canncel_img" id="'+file.filekey+'"><i class="mui mui-close"></i></span>';
				html +='<div class="img_content"><img width="'+ __width +'" height="'+ __height +'" src="'+file.href+'"></div></div></li>';
			if(imageDiv.length > 0){
				if(imageDiv.innerHTML() == ""){
					 imageDiv.innerHTML(html);
				}else{
					 imageDiv.append(html);
				}
		    }else{
			    var rowhtml = '<div class="tab_img"><ul id="imgUl_' + this.fdId + '" class="auditNoteHandlerImgUl">'+html+'</tr></ul></div>';
			    query(this.descriptionDiv).after(rowhtml);
		    }
			//kk入口进去的不知道为啥，流程中手写监听不到附件上传成功事件，所以得手动同步
			var deviceType = device.getClientType();
			if(deviceType==9 ){//|| deviceType==10
				this._synchronusAuditView(file.filekey);
			}
		},
		
		/**
		 * 同步到流程的手写展示中
		 */
		_synchronusAuditView:function(key){
			//新审批操作上传的手写附件li
			var file = query("#li_" + key);
			if (file.length < 0){
				return ;
			}
			//流程中的图片Ul
			var AuditNoteImgUl = query("#imgUl");
			if(AuditNoteImgUl.length > 0){
				if(AuditNoteImgUl.innerHTML() == ""){
					AuditNoteImgUl.innerHTML(file[0].outerHTML);
				}else{
					AuditNoteImgUl.append(file[0].outerHTML);
				}
		    }else{
			    var rowhtml = '<div class="tab_img"><ul id="imgUl" class="auditNoteHandlerImgUl">'+file[0].outerHTML+'</tr></ul></div>';
			    query("#descriptionDiv").after(rowhtml);
		    }
		}
	});
});