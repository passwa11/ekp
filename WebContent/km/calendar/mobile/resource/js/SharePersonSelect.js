define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom","dojo/dom-class","dojo/io-query","mui/dialog/Modal",
        "dojo/query","dojo/touch","dojo/dom-construct","mui/util","dijit/registry","dojo/request",'dojo/topic',"mui/i18n/i18n!km-calendar:kmCalendarMain"],
        function(declare,lang,WidgetBase,dom,domClass,ioq,Modal,query,touch,domConstruct,util,registry,request,topic,Msg){
		  return declare("km.calendar.SharePersonSelect",[WidgetBase],{
			postCreate : function() {
				this.inherited(arguments);
//				this.subscribe("/mui/list/loaded",'changeAttendBussInfo');
			},
			startup:function(){
				this.inherited(arguments);
				this._requestSharePersons();
			},
			
			_requestSharePersons : function(date){
				var self = this;
				var url = util.formatUrl("/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listGroupSearch&groupId="+ this.groupId+"&loadAll=true&checkType=read");
				var options = {
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery({})	
				};
				request(url,options).then(lang.hitch(this,function(result){
					if(result && result.length>0){
						var btnTxt = Msg["kmCalendarMain.group.sharePersonSelect"];// '筛选共享人员';
						if(this.personIds){
							btnTxt = Msg["kmCalendarMain.group.sharePersonSelect.cancel"];//'取消筛选';
						}
						var selectNode = domConstruct.create("span",{className:"",innerHTML:btnTxt},this.domNode);
						this.connect(selectNode,touch.press,this.onSelectPersons);
					}
				}));
			},
			
			onSelectPersons : function(){
				var params = util.getUrlParameter(location.href,'personIds');
				if(params){
					topic.publish('/calendar/group/resetShareperson', this,{personIds:''});
					return;
				}
				var contentDom = dom.byId("sharePersonScrollView");
				
			    var title = "";
			    var modalButtons = [ 
			        {
						title : Msg["kmCalendarMain.calendar.group.docOwner.clear"],//"清空",
						fn : function(dialog) {
							console.log("点击了Modal[取消]按钮");
							dialog.hide(); // 关闭弹窗
							topic.publish('/calendar/group/resetShareperson', this,{personIds:''});
						}
			        }, 				                        
			        {
						title : Msg["kmCalendarMain.btn.OK"],//"确定",
						fn : function(dialog) {
							var nodes = query('.muiSharePersonCard.mblListItemSelected',dialog.element);
						
							var ids = [];
							for(var i=0;i<nodes.length;i++){
								ids.push(nodes[i].id);
							}
							dialog.hide(); // 关闭弹窗
							topic.publish('/calendar/group/resetShareperson', this, {personIds:ids.join(',')});
						}
			        } 
			    ]; 
			    Modal(contentDom,title,modalButtons);
				
			},
			changeAttendBussInfo : function(evt,datas,a){
				if(evt && evt.id == 'groupCalendarList'){
					domConstruct.empty(this.domNode);
					if(datas && datas.length>0){
						this._requestAttendBuss(datas[0].fdSignedTime);
					}
				}
			}
			
			
		});
});