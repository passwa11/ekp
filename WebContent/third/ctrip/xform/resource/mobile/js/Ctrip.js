define([ "dojo/_base/declare", "mui/form/_FormBase","mui/util", "dojo/dom-construct",  "mui/dialog/Confirm","dojo/dom", "third/ctrip/xform/resource/mobile/js/CtripValidator",
		"dijit/registry","dojo/request","dojo/dom-attr","dojo/query","dojo/dom-class","sys/xform/mobile/controls/xformUtil","dojo/dom-style","dojo/ready"], 
		function(declare, _FormBase, util, domConstruct, Confirm, dom, CtripValidator, registry, request, domAttr, query, domClass, xUtil, domStyle, ready) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.Ctrip", [ _FormBase ], {
		
		controlId : '',
		
		ticketType : '', // hotel|plane
		
		bookTypeControlId: '',
		
		// 主文档的状态 “add|edit|view”
		mainDocStatus : 'view',
		
		modelName:'',
		
		docId:'',
		
		curViewWgt:null, // 当前主页面的视图
		
		ctripViewDom:null, // 携程视图
		
		iframeDom:null, // 携程页面的iframe
		
		loadingDom:null, // 加载图标
		
		validate : null, // 切换视图时需要校验

		startup : function() {
			var self = this;
			self.showStatus = self.showStatus == 'noShow' ? 'hidden' : self.showStatus;
			//self.edit = self.showStatus == 'edit';
			if(self.showStatus == 'edit'){
				if(typeof(self.curViewWgt) == 'string'){
					self.curViewWgt = registry.byId(self.curViewWgt);
				}
				
				if(typeof(self.ctripViewDom) == 'string'){
					self.ctripViewDom = dom.byId(self.ctripViewDom);
				}
				
				//加载图标 
				self.loadingDom = dom.byId("third_ctrip_"+ self.controlId +"_loading");
				
				var button = domConstruct.create("div",{
					className : "muiCtripEKP"
				});
				self.connect(button,"click",function(evt){
					self.rtnMainView();
				});
				button.innerHTML = "流程";
				domConstruct.place(button, self.ctripViewDom);
				
			}else{
				if(self.mainDocStatus != 'view'){
					// 添加校验
					// 由于校验框架只针对edit状态,跟携程控件的状态相反，为能使用到校验框架，故把edit设置为true
					self.edit = true;
					self.validate = "ctripRequired_" + self.controlId;
					ready(function(){
						//必须等到_ValidateMixin初始化完
						self.defer(function(){
							if(window.__validateInit == true){
								var relationDom = query("div[name*='sysXformBookTicketData']",self.domNode.parentNode);
								self.validation.Validators["ctripRequired_" + self.controlId] = new CtripValidator(xUtil.getXformWidgetBlur(null,self.bookTypeControlId),relationDom);
							}	
						},30);
					});	
				}
			}
			// 构建视图
			this.buildView();
		},
		
		// 返回主页面视图
		rtnMainView : function(){
			var self = this;
			var view = registry.byNode(self.ctripViewDom);
			// 清空iframe
			var iframe = query('iframe',self.ctripViewDom);
			domConstruct.destroy(iframe[0]);
			self.iframeDom = null;
			return view.performTransition(self.curViewWgt.id, -1, "slide");
		},
	
		buildView : function(){
			var self = this;
			var tickets = self.ticketType.split('|');
			self.btns = {};
			for(var i = 0;i < tickets.length;i++){
				var btn = self.buildButton(tickets[i]);
				self.btns[tickets[i]] = btn;
				domConstruct.place(btn, self.domNode);
			}
			// 添加监控
			var bookTypeControlId = self.bookTypeControlId;
			if(bookTypeControlId != ''){
				// 监控值改变事件
				self.subscribe("/mui/form/valueChanged","showButton",{'bookTypeControlId':bookTypeControlId});
				// 初始化按钮
				var wgt = xUtil.getXformWidgetBlur(null,bookTypeControlId);
				var val = wgt.value;
				if(val){
					var btns = self.btns;
					for(var key in btns){
						if(val.indexOf(key) > -1){
							domStyle.set(btns[key],'display', "inline-block");
						}else{
							domStyle.set(btns[key],'display', "none");
						}
					}
				}
			}
		},
		
		showButton : function(srcObj, arguContext){
			if(srcObj){
				var evtObjName =  xUtil.parseXformName(srcObj);
				if(evtObjName && this.bookTypeControlId == evtObjName){
					var val = srcObj.value;
					var btns = this.btns;
					for(var key in btns){
						if(val.indexOf(key) > -1){
							domStyle.set(btns[key],'display', "inline-block");
						}else{
							domStyle.set(btns[key],'display', "none");
						}
					}
				}
			}
		},
		
		buildButton : function(type){
			var self = this;
			var button = domConstruct.create("div",{
				style:{
					'margin-right':'10px'
				}
			});
			var msg = "";
			if(type == 'plane'){
				msg = "飞机";
			}else if(type == 'hotel'){
				msg = "酒店";
			}
			button.innerHTML = "预订" + msg; 
			domConstruct.place(domConstruct.create("i"),button,'first');
			if(self.showStatus == 'edit'){
				domClass.add(button,["third_ctrip_mobile_btn","third_ctrip_mobile_btn_" + type]);
				// 添加点击事件
				self.connect(button,"click",function(evt){
					self.openCtrip(type);
				});
			}else{
				domClass.add(button,["third_ctrip_disabled","third_ctrip_disabled_" + type]);
			}
			return button;
		},
		
		// 打开携程
		openCtrip : function(type){
			var self = this;
			var docId = self.docId;
			if(docId != ''){
				var url = Com_Parameter.ContextPath + "third/ctrip/ctripCommon.do?method=appovalCtripOrder&modelName="+ self.modelName +"&fdId="+ docId
				 			+ "&fdControlId=" + self.controlId + "&bookType=" + type;
				// 切换视图
				self.curViewWgt.performTransition(domAttr.get(self.ctripViewDom,'id'), 1, "slide");
				if(self.curViewWgt.domNode.parentNode == self.ctripViewDom.parentNode){
					self.ctripViewDom.style.display = 'block';
				}else{
					domConstruct.place(self.ctripViewDom,self.curViewWgt.domNode.parentNode,'last');
					self.ctripViewDom.style.display = 'block';
				}
				// 生成iframe，以装载携程方的页面
//				if(!self.iframeDom){
//					var divIframe = query('.third_ctrip_iframe',self.ctripViewDom)[0];
//					self.iframeDom = domConstruct.create("iframe",{
//									frameborder : '0',
//									scrolling : 'auto', 
//									style : {'height':'100%',
//											 'width':'100%',
//											 'border':'0px'
//											 }
//									},divIframe); 
//				}
					
				// 加载图标 
//				self.loadingDom.style.display = '';
				
				// 切换视图之后，让它慢慢加载
				// 提前审批
				request.post(url,{handleAs:'json'}).then(
						function(data){
							//debugger;
							//成功后返回
							if(data && data != null){
								//error为0即表明数据没问题
								if(data.errcode == '0'){
									//alert(self.location);
									// 单点
									var ssoMethodUrl = Com_Parameter.ContextPath+"third/ctrip/ctripCommon.do?method=ctripSsoAuth&type=mobile&orderId="+ docId +
										"&bookType="+ type +"&fdControlId="+ self.controlId +"&fdId="+ docId +"&modelName=" + self.modelName+"&location="+escape(self.location);
									window.open(ssoMethodUrl,"_self");
									//self.iframeDom.src = ssoMethodUrl;
									//self.iframeDom.onload = function(){
									//	self.loadingDom.style.display = 'none';
									//}
								}else{
									alert(data.errmsg);
								}
							}
						},
						function(error){
							//失败后返回
							alert(error);
						}
				);	
			}
		}
		
	});
	return claz;
});