define([
    "dojo/_base/declare",'dojo/date/locale','mui/i18n/i18n!sys-mobile',"dojo/query",
    'dojo/topic',"dojox/mobile/viewRegistry",'dijit/registry',
    'dojo/_base/lang','mui/util',
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/stat/AttendChartItemMixin"
	], function(declare,locale,muiMsg,query, topic,viewRegistry,registry, lang,util, _TemplateItemListMixin, AttendChartItemMixin) {
	
	return declare("sys.attend.SysAttendItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: AttendChartItemMixin,
		
		startup: function() {
			this.inherited(arguments);
			topic.subscribe('/mui/form/datetime/change',lang.hitch(this,'__onStatDateChange'));
		},
		
		__onStatDateChange : function(obj,evt){
			if(obj.id=='chartList_statDate'){
				var statDate = locale.parse(obj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
				var scrollView = viewRegistry.getEnclosingScrollable(this.domNode);
				this.url = util.setUrlParameter(this.url,"fdDate",statDate.getTime());
				//不统计当天数据
				if(new Date().toDateString() ==statDate.toDateString()){
					var items = [];
					this.generateList(items);
					this.totalSize=0;
					topic.publish('/mui/list/loaded', this,items);
					return;
				}
				var fdDept = registry.byNode(query('.muiStatCriterion .muiAddressForm .muiCategory')[0]);
				this.url = util.setUrlParameter(this.url,"fdDeptId",fdDept.get('value'));
				topic.publish('/mui/list/onReload',scrollView);
			}
		},
		
		//不分页
		resolveItems : function(items) {
			this._loadOver = false;
			var page = {};
			if (items) {
				if (items['datas']){//分页数据
					this.listDatas = this.formatDatas(items['datas']);
					page = items['page'];
					if (page) {
						this.pageno = 1;
						this.rowsize = parseInt(page.pageSize, 10);
						this.totalSize = parseInt(page.totalSize, 10);
						this._loadOver = true;
					}
				}else{//直接数据,不分页
					this.listDatas = items;
					this.totalSize = items.length;
					this.pageno = 1;
					this._loadOver = true;
				}
			}
			
			if (this._loadOver) {
				topic.publish('/mui/list/pushDomHide',this);
			} else {
				topic.publish('/mui/list/pushDomShow',this);
			}
				
			return this.listDatas;
		}
	});
});