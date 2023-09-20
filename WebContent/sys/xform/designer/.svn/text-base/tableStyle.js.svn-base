/**********************************************************
功能：表格样式
使用：表格样式图标
作者：欧宇
**********************************************************/
Designer_Config.operations['tableStyle'] = {
	lab : "1",
	imgIndex : 24,
	title : Designer_Lang.tableStyle,//提示语
	icon_s:true,//小图标class
	order: 22,
	line_splits_end:true,//后分割线
	select: false,
	run : Designer_OptionRun_OpenTableStylePanel
};

function Designer_OptionRun_OpenTableStylePanel(designer, obj) {
	if (designer.controls == null||designer.controls==''||designer.controls==undefined) {
		Designer_OptionRun_Alert(Designer_Lang.buttonsSelectOptControlForm_tip);
		return;
	}
	var sourceFrom = Com_GetUrlParameter(parent.location.href,"sourceFrom");
	if(sourceFrom=='ding'||sourceFrom=='Reuse'){
		Designer_OptionRun_Alert(Designer_Lang.tableStyle_sourceForm_tip);
		return;
	}
	var control = designer.control;
	var data=new KMSSData().AddBeanData("sysStandardTableStyleExt").GetHashMapArray();
	var paramObj={};
	// 移动端过滤掉表格内外无边框，互联网风格轻量化的样式
	if(Designer.instance.isMobile){
		for(var i=0;i<data.length;i++){
			if(typeof data[i].name!= "undefined" && data[i].name === Designer_Lang.tb_normal_lightweight){
				data.splice(i,1);
				break;
			}
		}

	}
	paramObj.data=data;

	var values=control.options.values;
	//加载表格样式
	if(values.tableStyle){
		var tableStyle=JSON.parse(values.tableStyle.replace(/quot;/g,"\""));
		paramObj.chooseStyle=tableStyle.tbClassName;
	}

	var dialog=new StandardTable_ModelDialog_Show(Com_Parameter.ContextPath+"sys/xform/designer/standardtable/stylePreview.jsp",paramObj,function(rtnValue){
		if(!rtnValue){
			return;
		}
		if (Designer.instance.isMobile) {
			$(control.options.domElement).attr("setTableStyle",true);
		}
		$("#"+control.options.domElement.getAttribute("id")).prev("[name='dynamicTableStyle']").remove();
		$("#"+control.options.domElement.getAttribute("id")).before("<link rel='stylesheet' name='dynamicTableStyle' type='text/css' href='"+Com_Parameter.ContextPath+rtnValue['pathProfix']+"/standardtable.css'/>");
		var __tbClassName = rtnValue["tbClassName"];
		if (__tbClassName === "tb_normal_noanyborder") {
			__tbClassName = "tb_normal " + __tbClassName;
		}
		$("#"+control.options.domElement.getAttribute("id")).attr("class",__tbClassName);
		control.options.values.tableStyle=JSON.stringify(jQuery.extend({}, rtnValue)).replace(/"/g,"quot;");
		$(control.options.domElement).attr("tableStyle",control.options.values.tableStyle)
	});
	dialog.setWidth(window.screen.width*520/1366+"");
	dialog.show();
}

Designer_Config.buttons.head.push("tableStyle");
