/**
 * 
 */
define([ "dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct","mui/table/ScrollableHView","dojo/_base/event","dojo/dom-style"], 
		function(declare, WidgetBase,domConstruct, ScrollableHView, event, domStyle) {
	var claz = declare("sys.xform.mobile.controls.massdata.template.MassDataTable", [ScrollableHView], {

		// evt : {pageno:xx,rowsize:xx,totalSize:xx,columns:xxx,datas:xxx}
		render : function(evt){
			domStyle.set(this.containerNode,"min-width","100%");
			this.containerNode.innerHTML = "";
			var table = domConstruct.create("table",{
				width: "100%",
				className : "massdata-table"
			},this.containerNode);
			var thead = domConstruct.create("thead",{
				className : "massdata-table-thead"
			},table);
			var thTr = domConstruct.create("tr",{},thead);
			// thead tr
			var length = Object.keys(evt.columns).length;
			if(length > 0){
				var width = parseInt(100/length) + "%";
				for(var key in evt.columns){
					var column = evt.columns[key];
					var th = domConstruct.create("th",{
						width : width
					},thTr);
					th.innerHTML = column.title;
				}			
			}
			// tbody
			var tbody = domConstruct.create("tbody",{},table);
			var records = evt.datas;
			if(records.length && records.length > 0){
				// 遍历所有行记录
				for(var i = 0;i < records.length;i++){
					var tr = domConstruct.create("tr",{},tbody);
					// 每行记录都是一个json，key为字段名
					var record = records[i];
					for(var key in evt.columns){
						var innerHtml = "";
						if(record.hasOwnProperty(key)){
							var valueInfo = record[key];
							innerHtml = valueInfo.value;
						}
						var td = domConstruct.create("td",{},tr);
						td.innerHTML = innerHtml;
					}
				}
			}else{
				// 无数据
				tbody.innerHTML = "";
				var tr = domConstruct.create("tr",{},tbody);
				var td = domConstruct.create("td",{
					colspan : length
				},tr);
				td.innerHTML = "暂无数据";
			}
		}
		
	});
	return claz;
});