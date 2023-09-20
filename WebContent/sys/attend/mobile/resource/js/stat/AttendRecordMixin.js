define(['dojo/_base/declare','mui/util','dojo/request','dojo/dom-construct',
        'dijit/registry','dojo/date/locale','mui/i18n/i18n!sys-mobile','dojo/query',"mui/i18n/i18n!sys-attend"], 
		function(declare,util,request,domConstruct,registry,locale,muiMsg,query,Msg ) {

	return declare('sys.attend.mobile.AttendRecordMixin', [], {
		
		buildRendering : function() {
			this.inherited(arguments);
			this.subscribe("/mui/attendRecord/list/show",'onGetDatas');
		},
		onGetDatas:function(obj,evt){
			var self = this;
			var url = "/sys/attend/sys_attend_stat_detail/sysAttendStatDetail.do?method=listAttendRecord";
			url = util.setUrlParameterMap(url,evt);
			request.post(util.formatUrl(url), {
				handleAs : 'json'
			}).then(function(data) {
				var nodes = query(" .muiAttendRecordList ul",self.domNode);
				domConstruct.empty(nodes[0]);
				if(data.length>0){
					for(var i = 0;i < data.length;i++){
						var record = data[i];
						var signDate = locale.format(new Date(record.signTime),{selector : 'date',datePattern : dojoConfig.Date_format });
						var signTime = locale.format(new Date(record.signTime),{selector : 'time',timePattern : 'HH:mm' });
						var statusTxt = '';
						if(record.docStatus=='2'){
							statusTxt = Msg['mui.fdWorkType.onwork'] + Msg['mui.fdStatus.late'];
						}
						if(record.docStatus=='3'){
							statusTxt = Msg['mui.fdWorkType.offwork'] + Msg['mui.fdStatus.left'];
						}
						if(record.docStatus=='0'){
							statusTxt = Msg['mui.fdStatus.unSign'];
						}
						if(record.fdOutside==true){
							statusTxt = Msg['mui.outside'];
						}
						var liNode = domConstruct.create('li',{className:'muiListItem'},nodes[0]);
						domConstruct.create('div',{innerHTML:signDate,className:'muiSignDate'},liNode);
						domConstruct.create('div',{innerHTML:statusTxt + "("+signTime+")",className:'muiStatusTxt'},liNode);
					}
				}else{
					
				}
			}, function(err) {
				console.log(err);
			});
		}

	});
});