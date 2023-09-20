define([ "dojo/_base/declare",
         "dojo/_base/lang",
         "dojo/dom-construct",
         "dojo/query",
         "dojo/request",
         "dojo/dom-style",
         "dojo/topic",
         "dojo/parser",
         "dijit/registry",
         "mui/util",
         "mui/dialog/Dialog",
         "mui/dialog/Tip",
         "mui/tabbar/TabBarButton",
         "mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile"
         ],
         function(declare, lang, domConstruct, query, request, domStyle, topic, parser, registry, util, Dialog, Tip, TabBarButton, Msg){
	var button = declare("km.review.mobile.km_review_feedback_info.js.FeedbackInfoOperationButton", [TabBarButton], {

		fdType: null,

		checkboxGroupTmpl :
             "<div id='!{id}' data-dojo-type=\"mui/form/CheckBoxGroup\" data-dojo-props='name:\"!{name}\",mul:true,alignment:\"V\",showStatus:\"edit\",store:!{store}' class=\"muiField\"></div>"
			+'	 <div data-dojo-type="mui/tabbar/TabBar" fixed="bottom">'
			+'		<li class="muiCirSelAll"><div data-dojo-type="mui/form/CheckBox" data-dojo-props="name:\'cirSelAll\',text:\'全选\'"></div></li>'
			+'	 	<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" class="muiBtnDefault "'
			+'			data-dojo-props="fdType:\'optOk\'">'
			+           Msg['sysCirculationMain.mobile.ok']
			+'		</li>'
			+'		<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" class="muiBtnDefault "'
			+'			data-dojo-props="fdType:\'optCancel\'">'
			+           Msg['sysCirculationMain.mobile.cancel']
			+'		</li>'
			+'	 </div>',

		_onClick: function(){
			if(this.fdType=="cancel"){
				topic.publish("/km/review/km_review_feedback_info/feedback",this);
			}else if(this.fdType=="ok"){
				var validorObj = registry.byId('feedbackScrollView');
				if(!validorObj.validate()){
					return;
				}
				Com_Submit.refresh = true;
				var sEvent = Com_Parameter.event;
				Com_Parameter.event["submit"] = [];
				Com_Parameter.event["confirm"] = [];
				Com_Parameter.event["submit_failure_callback"] = [];
				var Att = registry.byId('feedbackAtt');
				Com_Parameter.event["confirm"].push(function(){
					return Att.checkAttRules();
				})
				if(Com_Submit(document.kmReviewFeedbackInfoForm)){
					topic.publish("/km/review/km_review_feedback_info/feedback",this);
				}else{
					Com_Parameter.event = sEvent;
				}
			}else if(this.fdType=="recall" || this.fdType=="remind"){
				if(!this.order){
					this.order = 0;
				}
				this.groupId = "cirCheckboxGroup_"+this.fdType+this.order;
				this.order++;
				var list = registry.byId("opinionList")
				var store = [];
				var url = util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion&fdMainId="+this.fdMainId+"&docStatus=10");
				request(url, {handleAs:'json',sync:true}).then(lang.hitch(this, function(results) {
					if(results && results.datas){
						var getValByName = function(obj,key){
							for(var j=0;j<obj.length;j++){
								if(obj[j].col==key){
									return obj[j].value;
								}
							}
							return "";
						}
						for(var i =0;i<results.datas.length;i++){
							var obj = results.datas[i];
							store.push({text:getValByName(obj,"fdBelongPersonDept")+"-"+getValByName(obj,"fdBelongPerson"),value:getValByName(obj,"fdId")})
						}
					}
				}));
				store = JSON.stringify(store);
				var tmpl = this.checkboxGroupTmpl.replace("!{name}", "cirSelGroup")
					.replace("!{id}", this.groupId)
					.replace("!{store}", store);
				var dom = domConstruct.toDom(tmpl);
				this.select(dom);
			}else if(this.fdType=="optOk"){
				topic.publish("/sys/circulation/optOk",this);
			}else if(this.fdType=="optCancel"){
				topic.publish("/sys/circulation/optCancel",this);
			}else if(this.fdType=="replySubmit"){
				var validorObj = registry.byId('replyCirculationScrollView');
				if(!validorObj.validate()){
					return;
				}
				request(util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=checkWrite"), {
					 handleAs : 'json',
					 method : 'post',
					 data : {"fdOpinionId":this.fdOpinionId}
				}).then(lang.hitch(this, function(results) {
					if(results["repeat"] == 'true'){
						Tip.tip({icon:'mui mui-warn', text:Msg['sysCirculationMain.mobile.repeatTip'],width:'260',height:'60'});
					}else{
						Com_Submit.refresh = true;
						var sEvent = Com_Parameter.event;
						Com_Parameter.event["submit"] = [];
						Com_Parameter.event["confirm"] = [];
						Com_Parameter.event["submit_failure_callback"] = [];
						//添加回复附件confirm
						var replyAtt = registry.byId('replyCirculationAtt');
						Com_Parameter.event["confirm"].push(function(){
							return replyAtt.checkAttRules();
						})
						if(Com_Submit(document.sysCirculationOpinionForm)){
							topic.publish("/sys/circulation/replyCancel",this);
						}else{
							Com_Parameter.event = sEvent;
						}
					}
				}));

			}else if(this.fdType=="replyCancel"){
				topic.publish("/sys/circulation/replyCancel",this);
			}
		},
		select:function(dom){
			var self = this;
			var buttons = [];
			this.dialog = Dialog.element({
				title: Msg['sysCirculationMain.mobile.select']+(this.fdType=="recall"? Msg['sysCirculationMain.mobile.recall']: Msg['sysCirculationMain.mobile.remind'])+ Msg['sysCirculationMain.mobile.person'],
				canClose : false,
				element : dom,
				buttons : buttons,
				position:'bottom',
				'scrollable' : false,
				'parseable' : false,
				showClass : 'muiCirSelect',
				callback : lang.hitch(this, function(win,evt) {
					domStyle.set(query("body")[0],{
						"overflow-y":""
					})
					this.lastDialog = this.dialog;
					this.dialog = null;
				}),
				onDrawed:lang.hitch(this, function(evt) {
					parser.parse(evt.contentNode);
					domStyle.set(query("body")[0],{
						"overflow-y":"hidden"
					})
					var contentHeight = document.documentElement.clientHeight*0.9;
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
						   'max-height' : contentHeight + 'px',
						   "overflow-x":"hidden"
					 });
					 evt.scrollViewNode = evt.contentNode;
				})
			});
		},
		_destroyDialog:function(evt){
			if (this.dialog && this.dialog.callback)
				this.dialog.callback(window, this.dialog);
			if(this.lastDialog){
				this.lastDialog.hide();
				this.lastDialog = null;
			}
		},
		valueChange:function(evt){
			// 只有一条数据时勾选则全选也选上
			var list = registry.byId("opinionList");
			if(this.dialog && evt && (evt.name == 'cirSelAll' || list.listDatas.length == 1)){
				topic.publish("mui/form/checkbox/set",evt.checked);
			}
		},
		callOperation:function(evt,val){
			if(this.dialog){
				var group = registry.byId(this.groupId);
				if(group){
					var value = group.get('value');
					if(value){
						var self = this;
						var url = util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=deleteBacks");
						if(this.fdType == "remind"){
							url = util.setUrlParameter(url, "method", "addReminds");
						}
						request(url, {
								method : 'post',
								data : {"List_Selected":value.split(";")}
						}).then(lang.hitch(this, function(results) {
							self.refush();
							self._destroyDialog();
							Tip.success({
								text : self.fdType=="recall" ? Msg['sysCirculationMain.mobile.recall.success'] : Msg['sysCirculationMain.mobile.remind.success']
							});
						}));
					}else{
						Tip.tip({icon:'mui mui-warn', text:Msg['sysCirculationMain.mobile.select'],width:'180',height:'120'});
					}
				}
			}
		},
		refush:function(){
			if(self.fdType=="recall"){
				request(util.formatUrl("/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=findByStatusCount"), {
					method : 'post',
					data : {"fdMainId":this.fdMainId}
				}).then(lang.hitch(this, function(results) {
					if(results){
						var html = "";
						var data = JSON.parse(results);
						for(var key in data){
					　　　　var text = data[key].text;
						   var value = data[key].value;
						   var id = data[key].id;
						   html+= '<div class="status" onclick="changeStatus(\''+id+'\');" id="status_'+id+'"><span class="status-count">'+value+'</span><span class="status-text">'+text+'</span></div>'
					　　}
						document.getElementById("statusContainer").innerHTML =html;
					}
				}))
			}
			registry.byId("opinionList").reload();
		},
		postCreate: function() {
			this.inherited(arguments);
			if(this.fdType=="recall" || this.fdType=="remind"){
				this.subscribe("/sys/circulation/optOk","callOperation");
				this.subscribe("/sys/circulation/optCancel","_destroyDialog");
				this.subscribe("mui/form/checkbox/valueChange","valueChange");
			}
		},
		startup: function() {
			this.inherited(arguments);
		},
		buildRendering : function() {
			this.inherited(arguments);
		}
	});
	return button;
});
