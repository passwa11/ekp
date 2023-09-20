define(
		[ "dojo/_base/declare","mui/tabbar/TabBarButton","mui/util","dojo/topic","dojo/request","mui/dialog/Tip","dijit/registry","mui/i18n/i18n!sys-lbpmperson"],
		function(declare,TabBarButton,util,topic,request,Tip,registry,msg) {
			var freeflowNodeOkBtn = declare("sys.lbpmperson.mobile.FastReviewTodoBtns",
					[TabBarButton], {
				
						type:"pass",
				
						onClick : function() {
							//修复iPhoneX点击穿透问题
							if(!this._CLICK_FLAG) {
								return;
							}
							var textAreaNode = registry.byId("fdUsageContent");
							if(!textAreaNode || !textAreaNode.value){
								Tip.warn({text: msg['mui.lbpmperson.fastreview.interAuditnote'], time: 1500});
								return;
							}
							var headNode = registry.byId("fastReviewTodoHead");
							
							if(headNode && headNode.processIds){
								var self = this;
								var url = util.formatUrl('/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=approveAll');
								url += "&processIds="+headNode.processIds+"&oprGroup="+self.type+"&fdUsageContent="+encodeURIComponent(textAreaNode.value);
								request.get(url, {handleAs:'text',sync:true}).then(function(responseText) {
									Tip.success({"text":msg['mui.lbpmperson.fastreview.reviewSuccess'],callback:function(){
										topic.publish("/sys/lbpmperson/mobile/FastReviewTodo/reviewSuccess");
									}});
								});
							}
						}
					});
			return freeflowNodeOkBtn;
		});