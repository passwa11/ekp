define([ "dojo/_base/declare",
         "dojo/_base/array",
         "dojo/_base/lang",
         "dojo/dom-class",
         "dojox/mobile/ViewController",
         "dojox/mobile/viewRegistry",
         "dojo/query",
         "dojo/request",
         "dojo/topic",
         "dijit/registry",
         "mui/util",
         "mui/dialog/Dialog",
         "mui/dialog/Tip",
         "mui/device/device",
         "mui/device/adapter",
         "dojox/mobile/ScrollableView",
         "mui/i18n/i18n!sys-lbpmservice",
         "dojo/dom-style",
         "dojo/parser",
         "dojo/dom-construct"],
         function(declare, array, lang, domClass, ViewController, viewRegistry, query, 
        		 request, topic, registry, util, Dialog, Tip, device, adapter, ScrollableView, msg, domStyle,
        		 parser,domConstruct){
	var button = declare("sys.lbpmservice.mobile.operation.ExtOperationView", [ScrollableView], {
		processId:"",
		
		buildRendering: function() {
			this.inherited(arguments);
			window.showFlowChartView = this.showFlowChartView;
		},
		
		showFlowChartView: function(process){
			location = util.formatUrl("/sys/lbpmservice/mobile/lbpm_audit_note/flowchart.jsp?processId=" + process);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		startup: function() {
			this.inherited(arguments);
		},
		
		initExtOpertion:function(srcObj){
			this.initOtherInfo();
			if(srcObj.selOperation){
				var opts = srcObj.selOperation.split(":");
				if(this.curTaskId==opts[0] && this.curOperationType==opts[1]){
					return;
				}
				if(this.curOperationMod){		//之前的选项
					if(this.curOperationMod.blur)
						this.curOperationMod.blur();
				}
				this.curTaskId =  opts[0];
				this.curOperationType =  opts[1];
				var self = this;
				this.resetViewInfo();
				array.forEach(window.LBPM_CurOperationCfg,function(task,idx){
					if(task.taskId ==self.curTaskId){
						self.curTask = task;
						array.forEach(task.operations,function(operation, i){
							if(operation.operationType==self.curOperationType){
								self.curOperation = operation;
							}
						});
					}
				});
				var desc = this.curOperation.operationHandlerType=='drafter'?"起草人:":(this.curOperation.operationHandlerType=='branchadmin'?"分支特权人:":"已处理:");
				desc = desc + this.curOperation.operationName
				query("#OperationMethodTable .detailNode").html(desc);
				var jsFiles = null;
				if(self.curOperation.operationHandlerType=="drafter"){
					jsFiles = window.LBPM_DrafterJs;
				}else if(self.curOperation.operationHandlerType=="branchadmin"){
					jsFiles = window.LBPM_BranchAdminJs;
				}else{
					jsFiles = window.LBPM_HistoryJs;
				}
				jsFiles = array.map(jsFiles,function(fileStr){
					if(fileStr.substr(0, 1) == '/') {
						fileStr = fileStr.substr(1);
					}
					if(fileStr.substr(fileStr.length-3,fileStr.lenghth) == '.js') {
						fileStr = fileStr.substr(0,fileStr.length-3);
					}
					return fileStr;
				});
				require(jsFiles,function(){
					for(var i=0; i<arguments.length; i++){
						var tmpMod = arguments[i];
						if(tmpMod[self.curOperationType]){
							if(tmpMod.init){
								tmpMod.init();
							}else{
								console.warn(jsFiles[i] + " 没有init方法 ");
							}
							var curModule = tmpMod[self.curOperationType];
							self.curOperationMod = curModule;
							if(curModule.click){
								if(self.curOperation.operationHandlerType=="branchadmin"){
									var param = {};
									param['curTask'] = self.curTask;
									curModule.click(param);
								}else{
									curModule.click();
								}
							}
						}
					}
				});
			}
		},
		
		resetViewInfo:function(){
			query("#OperationView .actionArea").style({display:""});
			//当前处理人行-开关控制显示
		 	if(Lbpm_SettingInfo.isShowCurrentHandlers == "false"){
		 		query("#curNodeInfoArea .actionView").style({display:"none"});
		 	}
		},
		
		initOtherInfo:function(srcObj){
			if(!this.initEvent){
				//初始化提交按钮
				this.initSubmitButton(srcObj);
				
				//初始化常用意见
				this.initCommonUsage(srcObj);
				this.initEvent = true;
			}
		},
		
		initSubmitButton:function(srcObj){
			var _self = this;
			var submitBtn = registry.byId("OperationSubmit");
			lang.mixin(submitBtn,{
				_onClick:function(){
					var curTime = new Date().getTime();
					if(_self._curTime && curTime - _self._curTime<500){//防止iphone双击
						return;
					}
					_self._curTime = curTime;
					
					if(!_self.curOperationMod.check()){
						return;
					}
					var params={"taskId" : _self.curTaskId,
								"processId" : _self.processId,
								"activityType" : _self.curTask.activityType,
								"operationType":_self.curOperationType,
								};
					params['param'] = {
							"operationName": _self.curOperation.operationName,
						    //"notifyType": "todo",
						    "notifyOnFinish": true,
							"auditNoteFrom":('' + device.getClientType())
					};
					if(_self.curOperationMod.setOperationParam)
						_self.curOperationMod.setOperationParam(params['param']);
					var ajaxParam ={};
					query("#updateInfo input", _self.domNode).forEach(function(domObj){
						ajaxParam[domObj.name] = domObj.value;
					});
					ajaxParam["param"] = JSON.stringify(params);
					var deferred = window.Com_BeforeSubmitDeferred();
					deferred.then(lang.hitch(this,function(){
						request.post(util.formatUrl("/sys/lbpmservice/mobile/extOperation.do?method=update"),
								{data:ajaxParam, handleAs:"json"}).then(function(data){
							if(data.sucess){
								Tip.success({"text":lbpm.constant.OPRSUCCESS,callback:function(){
									var rtn = adapter.goBack();
									if(rtn == null){
										history.back();
									}
									//若起草人撤回后500毫秒后页面没有跳转，则刷新当前页面，#107815
									_self.defer(function(){
										if(_self.curOperationType == 'drafter_return'){
											document.location.reload();
										}
									},500);
								}});
							}else{
								if(data.msg!=""||data.msg!=='处理出错.'){
									Tip.fail({"text":data.msg});
								}else{
									Tip.fail({"text":lbpm.constant.OPRFAILURE});
								}
							}
						},function(error){
							Tip.fail({"text":lbpm.constant.OPRFAILURE});
						});
					}),lang.hitch(this,function(){
						Tip.fail({"text":lbpm.constant.OPRFAILURE});
					}));
				}
			});
		},
		
		buildContentHtml:function(usageContents){
			var temp = '<input type="checkbox" '
				+ 'data-dojo-type="mui/form/CheckBox" name="_ext_box_commonUsageObjName" '
				+ 'value="!{value}" data-dojo-props="mul:false,text:\'!{text}\'">';
			var contents = array.map(usageContents, function(usageContent) {
				while (usageContent.indexOf("nbsp;") != -1) {
					usageContent = usageContent.replace("&nbsp;", " ");
				}
				usageContent = usageContent.replace(/\'/g,"\\\'").replace(/\"/g, "&quot;");
				return {text: usageContent, value: usageContent};
			});
			var html = "";
			if (contents.length == 0) {
				html = "<p>" + msg['mui.operation.commonUsage.none'] +"</p>";
			}else{
				html = array.map(contents, function(props) {
					return temp.replace(
							'!{text}', props.text).replace(
									'!{value}', props.value);
				});
				html = "<div class='muiFormSelectElement commonUsageSlectElement'>" + html.join("") + "</div>";
				//获取当前视图id
				var view = viewRegistry.getEnclosingView(query("#commonUsagesArea")[0]);
				var pView = viewRegistry.getParentView(view);
				var viewId=null;
				if(!pView && window.backToViewId){
					//同级别的主文档view
					viewId = window.backToViewId;
				}else{
					view = pView || view;
					viewId = view.id;
				}
				//构建按钮区域
				var buttons = [];
				buttons.push({id:"addUsageButton",label:"添加审批语",icon1:"mui mui-plus",backTo:viewId,viewId:'commonUsageCreateView'});
				buttons.push({id:"manageUsageButton",label:"管理",icon1:"mui mui-file-rar",backTo:viewId,viewId:"commonUsageManageView"});
				buttons = JSON.stringify(buttons);
				html += "<ul fixed='bottom' class='muiViewBottom' data-dojo-type='sys/lbpmservice/mobile/workitem/CommonUsageTabbar' data-dojo-props='buttons:"+buttons+"'></ul>";
			}
			return html;
		},
		
		getNewestCommonUsages:function(){
			var usageContents = [];
			var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=getUsagesInfo';
			request.get(url, {handleAs:'text',sync:true}).then(function(responseText) {
				var names = responseText ? decodeURIComponent(responseText) : null;
				if (names != null && names != "") {
					usageContents = names.split("\n");
				}
				window.currentUsageContents = usageContents;
			});
			return usageContents;
		},
		
		initCommonUsage:function(srcObj){
			var self = this;
			var kmssData = new KMSSData();
			kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesInfo");
			var result = kmssData.GetHashMapArray();
			if(result && result[0]){
				var names = result[0].usagesInfo ? decodeURIComponent(result[0].usagesInfo) : null;
				var usageContents = [];
				if (names != null && names != "") {
					usageContents = names.split("\n");
				}
				var html = self.buildContentHtml(usageContents);
				var dialog = null
				query("#" + self.id +" #commonExtUsages").on("touchend", function() {
					setTimeout(function(){
						dialog = Dialog.element({
							element : html,
							showClass: 'muiDialogSelect muiFormSelect commonUsageDialog',
							position:'bottom',
							'scrollable' : false,
							'parseable' : true,
							callback: function() {
								dialog = null;
							},
							onDrawed:function(evt) {
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
								domStyle.set(evt.scrollViewNode, {
									'max-height' : contentHeight + 'px',
									"overflow-x":"hidden"
								});
								topic.subscribe("/workitem/commonUsage/hideDialog",function(){
									if(dialog){
										domStyle.set(dialog.domNode,{
											"display":"none"
										})
									}
								});
								topic.subscribe("/workitem/commonUsage/showDialog",function(){
									if(dialog){
										//更新弹窗的内容
										var currentUsageContents = self.getNewestCommonUsages();
										html = self.buildContentHtml(currentUsageContents || [])
										//先销毁原先的组件
										array.forEach(dialog.htmlWdgts,
											function(wdt) {
												if (wdt && wdt.destroy) {
													wdt.destroy();
												}
											});
										//构建新的内容
										dialog.element = html;
										domConstruct.empty(dialog.contentNode);
										var _container = dialog.contentNode;
										if (dialog.scrollable) {
											_container = domConstruct.create('div', {
												'className':'muiDialogScrollView',
												'data-dojo-type' : 'mui/NativeView'
											}, dialog.contentNode);
											isParse = true;
											dialog.scrollViewNode = _container;
										}
										domConstruct.place(html, _container);
										parser.parse(dialog.contentNode).then(function(widgetList) {
											dialog.htmlWdgts = widgetList;
											dialog.loaded();
										});
										domStyle.set(dialog.domNode,{
											"display":""
										});
										domStyle.set(dialog.scrollViewNode, {
											'max-height' : contentHeight + 'px',
											"overflow-x":"hidden"
										});
									}
								});
							}
						});
					},300);
				});
				topic.subscribe("mui/form/checkbox/change", function(box, data) {
					if (data.name != '_ext_box_commonUsageObjName') {
						return;
					}
					if (dialog)
						dialog.hide();
					dialog = null;
					if(typeof lbpm.workitem.constant.COMMONUSAGES_ISAPPEND == "undefined") {
						var kmssData = new KMSSData();
						kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesIsAppend");
						var result = kmssData.GetHashMapArray();
						if(result && result[0]){
							var isAppend = result[0].isAppend ? result[0].isAppend : null;
							if (isAppend != null && isAppend != "") {
								lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = isAppend;
							} else {
								lbpm.workitem.constant.COMMONUSAGES_ISAPPEND = true;
							}
						}
					}
					var fdUsageContent = query("#" + self.id +" textarea[name='ext_usageContent']");
					if(lbpm.workitem.constant.COMMONUSAGES_ISAPPEND=="true"){
						fdUsageContent.val(fdUsageContent.val() + data.value.replace(/\\\'/g, "'"));
					} else {
						fdUsageContent.val(data.value.replace(/\\\'/g, "'") + "\r\n");
					}
				});
			}
		}
	});
	return button;
});
