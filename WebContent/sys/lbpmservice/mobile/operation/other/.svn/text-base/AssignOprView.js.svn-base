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
         "mui/i18n/i18n!sys-lbpmservice-support",
         "dojo/NodeList-dom"],
         function(declare, array, lang, domClass, ViewController, viewRegistry, query, 
        		 request, topic, registry, util, Dialog, Tip, device, adapter, ScrollableView, msg, Msg){
	var button = declare("sys.lbpmservice.mobile.operation.other.AssignOprView", [ScrollableView], {
		
		processId:"",
		
		operationType:0,			//0:回复分发，1:分发
		
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
			this.resetViewInfo();
			var oprLabel = "";
			if (srcObj.operationType == 0) {
				oprLabel = Msg["mui.lbpmAssign.operation.replyAssign"];
				query("#AssignOprView #assignOprArea").style({display:"none"});
				
			} else {
				oprLabel = Msg["mui.lbpmAssign.operation.assign"];
				query("#AssignOprView #assignOprArea").style({display:""});
			}
			query("#OperationMethodTable .detailNode").html(oprLabel);
		},
		
		resetViewInfo:function(){
			query("#AssignOprView .actionArea").style({display:""});
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
					var assignFormData = {};
					assignFormData["fdAssignItemId"] = lbpm.nowAssignItem.id;
					assignFormData["fdAssignOpinion"] = query("#AssignOprView textarea[name='ext_usageContent']").val();
					if (_self.operationType == 1) {
						assignFormData["toAssigneeIds"] = query("#assigneeIds").val();
						var widget = registry.byId("canMultiAssign");
						if (widget) {
							assignFormData["fdIsCanAssign"] = widget.get("checked");
						}
					}
					assignFormData["fdAuditNoteFrom"] = device.getClientType();
					var deferred = window.Com_BeforeSubmitDeferred();
					deferred.then(lang.hitch(this,function(){
						request.post(util.formatUrl("/sys/lbpmservice/support/lbpm_assign/lbpmAssign.do?method=doAssign4Mobile"),
								{data:assignFormData, headers: {'Accept': 'application/json'}, handleAs:"json"}).then(function(data){
							if(data.sucess){
								Tip.success({"text":lbpm.constant.OPRSUCCESS,callback:function(){
									history.back();
									
									_self.defer(function(){
										document.location.reload();
									},500);
									
									
								}});
							}else{
								Tip.fail({"text":lbpm.constant.OPRFAILURE});
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
		
		initCommonUsage:function(srcObj){
			var self = this;
			var kmssData = new KMSSData();
			kmssData.AddBeanData("lbpmUsageTarget&type=getUsagesInfo");
			var result = kmssData.GetHashMapArray();
			if(result && result[0]) {
				var names = result[0].usagesInfo ? decodeURIComponent(result[0].usagesInfo) : null;
				var usageContents = [];
				if (names != null && names != "") {
					usageContents = names.split("\n");
				}
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
					html = "<div class='muiFormSelectElement'>" + html.join("") + "</div>";
				}
				var dialog = null
				query("#" + self.id +" #commonExtUsages").on("touchend", function() {
					setTimeout(function(){
						dialog = Dialog.element({
							element : html,
							showClass: 'muiDialogSelect muiFormSelect',
							position:'bottom',
							'scrollable' : false,
							'parseable' : true,
							callback: function() {
								dialog = null;
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
					query("#" + self.id +" textarea[name='ext_usageContent']").val(data.value.replace(/\\\'/g, "&#39;") + "\r\n")
				});
			}
		}
	});
	return button;
});
