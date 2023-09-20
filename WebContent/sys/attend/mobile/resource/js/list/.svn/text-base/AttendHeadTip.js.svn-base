define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-attr","dojo/dom-class","dojo/io-query",
        "dojo/query","dojo/touch","dojo/dom-construct","mui/util","dojo/on","dojo/request","mui/i18n/i18n!sys-attend:*"],
        function(declare,lang,WidgetBase,domAttr,domClass,ioq,query,touch,domConstruct,util,on,request,Msg){
		  return declare("sys.attend.AttendCategorySelect",[WidgetBase],{
			postCreate : function() {
				this.inherited(arguments);
				this.subscribe("/mui/list/loaded",'changeAttendBussInfo');
			},
			startup:function(){
				this.inherited(arguments);
				this._requestAttendBuss();
			},
			
			_requestAttendBuss : function(date){
				domConstruct.empty(this.domNode);
				var self = this;
				var url = util.formatUrl("/sys/attend/sys_attend_main/sysAttendMain.do?method=listAttendBuss");
				var options = {
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery({
						fdCategoryId : this.fdCategoryId,
						statDate : date
					})	
				};
				request(url,options).then(lang.hitch(this,function(result){
					if(result.status=='1'){
						return;
					}
					var datas = result.datas;
					if(datas.length==0){
						return;
					}
					domConstruct.empty(this.domNode);
					for(var i = 0 ; i < datas.length;i++){
						var record = datas[i];
						var statusTxt = Msg['sysAttendMain.fdStatus.askforleave'];
						if(record.fdType=='4'){
							statusTxt = Msg['sysAttendMain.fdStatus.business'];
						}
						if(record.fdType=='7'){
							statusTxt = Msg['sysAttendMain.fdStatus.outgoing'];
						}
						var time = '';
						if(record.fdStartTime && record.fdEndTime){
							time =record.fdStartTime + ' ~ ' + record.fdEndTime;
						}
						this.genBussItem(record.fdName,statusTxt,record.fdBusUrl,time);
					}
				}));
			},
			
			genBussItem : function(fdName,statusTxt,href,time){
				var bussNode = domConstruct.create("div",{className:"muiAttendBuss"},this.domNode);
				var leftNode = domConstruct.create("div",{className:"muiBussLeft"},bussNode);
				var centerNode = domConstruct.create("div",{className:"muiBussCenter"},bussNode);
				if(href){
					domConstruct.create("div",{className:"muiBussRight",innerHTML:'<i class="mui mui-forward"></i>'},bussNode);
				}
				var titleNode = domConstruct.create("div",{className:""},centerNode);
				var timeNode = domConstruct.create("div",{className:""},centerNode);
				domConstruct.create("span",{className:"muiSignInBtn muiBussStatus",innerHTML:statusTxt},leftNode);
				domConstruct.create("span",{className:"muiBussTitle",innerHTML:fdName},titleNode);
				domConstruct.create("span",{className:"muiBussTime",innerHTML:time},timeNode);
				
				this.connect(bussNode, touch.press, lang.hitch(this,function(){
					if(href){
						location.href = util.formatUrl(href);
					}
				}));
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