<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="java.util.*,com.landray.kmss.util.StringUtil,net.sf.json.JSONObject,java.util.Iterator,java.net.URLDecoder,java.net.URLEncoder" %>
<%@page import="com.landray.kmss.dbcenter.echarts.forms.DbEchartsTabelInfo,com.landray.kmss.dbcenter.echarts.util.HelperUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>报表显示</title>
<link href="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<%-- <script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/js/jquery.min.js"></script> --%>
<script src="${LUI_ContextPath}/resource/js/jquery.js"></script>
<script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/js/jquery.jqprint-0.3.js"></script>
<script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/js/bootstrap.min.js"></script>
<script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
var Com_Parameter = {
	ContextPath:"${KMSS_Parameter_ContextPath}",
	ResPath:"${KMSS_Parameter_ResPath}",
	Style:"${KMSS_Parameter_Style}",
	JsFileList:new Array,
	StylePath:"${KMSS_Parameter_StylePath}",
	CurrentUserId:"${KMSS_Parameter_CurrentUserId}",
	Cache:'${ LUI_Cache }'
};
</script>
<script src="${LUI_ContextPath}/resource/js/common.js"></script>
<link rel="stylesheet" type="text/css" media="screen,print" href="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/css/rpt.css" />
</head>
<%
DbEchartsTabelInfo info = (DbEchartsTabelInfo)request.getAttribute("info");
%>
<%
	String dynamic = request.getParameter("db_dynamic");// 约定db_dynamic存储入参数据
	String db_dynamic = "";
	// 支持入参
	if(StringUtil.isNotNull(dynamic)){
		JSONObject dy = JSONObject.fromObject(URLDecoder.decode(dynamic,"UTF-8"));
		Iterator ite = dy.keys();
		while(ite.hasNext()){
			String key = (String)ite.next();
			db_dynamic += "&dy." + key + "=" + URLEncoder.encode(dy.getString(key),"UTF-8");
		}
	}
	String fdId = request.getParameter("fdId");
	String dbEchartsTableUrl = "/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=data&fdId="+fdId;
	dbEchartsTableUrl += db_dynamic;
%>

<script>
var hiddenSetting={};
var constHtml = [];

constHtml.push('<tr style="height:53px">');
constHtml.push('<td class="A1" colspan="10" style="height:53px;background-color:rgb(255,255,255)">${info.docSubject}</td>');
constHtml.push('</tr>');

constHtml.push('<tr style="height:31px">');
constHtml.push('<th class="A3 rpttitle" style="min-width:40px;width:40px;">序号</th>');
var align = []; 
var _index = 0;
<c:forEach items="${info.config.columns}" var="column">
	<c:if test="${column.hidden!='true'}">
			hiddenSetting["${column.key}"]=false;
			<c:if test="${empty column.template}">
				constHtml.push('<th class="A3 rpttitle" style="${empty column.width?'':'width:'}${column.width}">${column.name}</th>');	
			</c:if>
			<c:if test="${not empty column.template}">
				constHtml.push('<th class="A3 rpttitle" style="${empty column.width?'':'width:'}${column.width}">${column.name}</th>');
			</c:if>
			align[_index++]="${column.align}";
	</c:if>
	<c:if test="${column.hidden=='true'}">
			hiddenSetting["${column.key}"]=true;
	</c:if>
</c:forEach>
constHtml.push('</tr>');
console.log(align);
var contentHtml = [];
</script>

<body style="">
<form name="dbEchartsTableForm" method="post" target="_blank">
	<input type="hidden" name="fdId" value="${param.fdId}">
</form>

<div class="container" id="container" style="margin-left: inherit;margin-top:10px;width:100%">
<%
List<Map<String, String>> conditions =  info.getCriteriaList();
List<Map<String, String>> inputConditions = new ArrayList<Map<String, String>>();
StringBuffer sbHid = new StringBuffer();
StringBuffer sbInput = new StringBuffer();
int _rowNum = 0;
if(conditions!=null && !conditions.isEmpty()){
	for(Map<String, String> condition :  conditions){
		if("true".equals(condition.get("hide"))){
			sbHid.append("<input type='hidden'  name='"+condition.get("key")+"' id='"+condition.get("key")+"' value='"+(StringUtil.isNotNull(condition.get("defaultValue"))?condition.get("defaultValue"):"")+"' fieldkey='"+condition.get("fieldkey")+"'>");
		}else{
			inputConditions.add(condition);
			if (condition.containsKey("showForm")&&"rangeQuery".equals(condition.get("showForm"))){
				//区间查询多做一次，需要用于占位
				Map<String, String> conditionPlaceHolder = new HashMap<String, String>();
				conditionPlaceHolder.put("conditionPlaceHolder","true");
				conditionPlaceHolder.putAll(condition);
				inputConditions.add(conditionPlaceHolder);
			}
		}
	}
	int _row = inputConditions.size()/3;
	_rowNum = _row;
	int _last = inputConditions.size()%3;
	for(int i = 0; i < _row; i++){
		if(i>0){
			sbInput.append("<p></p>");
		}
		sbInput.append("<div class='row'>");
		for(int j=i*3; j< (i+1)*3; j++){
			Map<String, String> condition = inputConditions.get(j);
			sbInput.append("<div class='col-xs-3'>");
			sbInput.append("<div class='input-group"+(HelperUtil.isDateHtml(condition)?" date":"")+"'>");
			sbInput.append(HelperUtil.getConditionHtml(condition));
			sbInput.append(" </div>");
			sbInput.append(" </div>");
		}

		if(i==_row-1 && _last==0){
			sbInput.append("<div class='col-xs-3'>");
			sbInput.append(HelperUtil.getButtonHtml(null,request,inputConditions.size()>0?true:false));
			sbInput.append("</div>");
		}else{
			sbInput.append("<div class='col-xs-3'></div>");
		}

		sbInput.append("</div>");
	}

	if(_last>0){
		_rowNum++;
		if(sbInput.length()>0){
			sbInput.append("<p></p>");
		}
		sbInput.append("<div class='row'>");
		for(int j=_row*3; j<_row*3+_last; j++){
			Map<String, String> condition = inputConditions.get(j);
			sbInput.append("<div class='col-xs-3'  id ='selected'>");
			sbInput.append("<div class='input-group"+(HelperUtil.isDateHtml(condition)?" date":"")+"'>");
			sbInput.append(HelperUtil.getConditionHtml(condition));
			sbInput.append(" </div>");
			sbInput.append(" </div>");
		}
		for(int j=0;j<3-_last;j++){
			sbInput.append("<div class='col-xs-3'></div>");
		}
		sbInput.append("<div class='col-xs-3'>");
		sbInput.append(HelperUtil.getButtonHtml(null,request,inputConditions.size()>0?true:false));
		sbInput.append("</div>");

		sbInput.append("</div>");
	}

}else{
		_rowNum++;
		sbInput.append("<div class='row'>");
		sbInput.append("<div class='col-xs-3'>");
		sbInput.append("</div>");
		sbInput.append("<div class='col-xs-3'>");
		sbInput.append("</div>");
		sbInput.append("<div class='col-xs-3'>");
		sbInput.append("</div>");
		sbInput.append("<div class='col-xs-3'>");
		sbInput.append(HelperUtil.getButtonHtml(null,request,false));
		sbInput.append("</div>");
		sbInput.append("</div>");
}

out.println(sbHid.toString());
out.println(sbInput.toString());

int  _pageLength = 35;
int _baseLen = 75;
int _conStep = 40;
int _hasPageH = _baseLen+_pageLength+_rowNum*_conStep;
int _hasntPageH = _baseLen+_rowNum*_conStep;
int _iframeLen = 776-_rowNum*_conStep;

%>

   <div class="row">
	   <div class="col-xs-12">
	   	<iframe name="contentFrame" id="contentFrame" src="${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/rpt/rpt_show.jsp" style="border: 1px solid rgb(221, 221, 221); width: 100%; margin-top: 10px; height: <%=_iframeLen%>px;"></iframe>
	   </div>
   </div>

   <div class="row">
	   <div class="col-xs-12">
		<div style="float:right;padding-right:10px;">
			<ul id="pagingContainer" class="pagination">
			</ul>
		</div>
	   </div>
   </div>

</div>

<script type="text/javascript">
var pageSize = "15";
var pageno = 1;
var _reportServerUrl="${LUI_ContextPath}<%=dbEchartsTableUrl%>";
var __first="&__f=1";

//获取分页信息
var _config = <%=info.getConfig()%>;
console.log(_config);
if(_config&&_config.listview&&_config.listview.rowSize){
	pageSize=_config.listview.rowSize;
}

$("input[condition='true']").keydown(function(event){ //给input绑定一个键盘点击事件
	event=event ||window.event;
	if(event.keyCode==13){ //判断点的是否是回车键
		doSearch();
	}
});
$("select[condition='true']").change(function(event){
	doSearch();
});

$(document).ready(function(){

	$("#container .input-group.date").datetimepicker({
			format:"yyyy-mm-dd",
			language:"zh-CN",
			clearBtn: true,
			todayBtn: true,
			minView: "month",
			autoclose: true
	});

	resetIFrameHeight();

	$("#contentFrame").load(  function(){
		<%
		if(info.isShowPage()){
		%>
		_loadReportData(1);
		<%}else{%>
		_loadReportData();
		<%}%>
		})

});

function resetIFrameHeight(){
	//#127657 统计列表高度获取失败
	<%
        if(info.isShowPage()){
        %>
	var height=<%=_iframeLen%>-<%=_hasPageH%>;
	$("#contentFrame").height(height);
	<%}else{%>
	var height=<%=_iframeLen%>-<%=_hasntPageH%>;
	$("#contentFrame").height(height);
	<%}%>
}

function getSearchParams(){
	var allInput = $("#container input");
	var params=[];
	for(var i=0;i<allInput.length;i++){
		var fieldkey = $(allInput[i]).attr("fieldkey");
		if(fieldkey){
			var pv = $(allInput[i]).val();
			if(pv!=""){
				var pn = $(allInput[i]).attr("name");
				params.push("q."+pn+"="+encodeURIComponent(pv));
			}
		}
	}
	var allSelect= $("#container select");
	for(var i=0;i<allSelect.length;i++){
		var fieldkey = $(allSelect[i]).attr("fieldkey");
		if(fieldkey){
			var pv = $(allSelect[i]).val();
			if(pv!=""){
				var pn = $(allSelect[i]).attr("name");
				params.push("q."+pn+"="+encodeURIComponent(pv));
			}
		}
	}

	//加上排序参数
	var url_href = window.location.href;
	if(url_href.indexOf("orderby")>-1){
		var _data = url_href.split("&");
		for(var i=0;i<_data.length;i++){
			if(_data[i].indexOf("orderby")>-1 || _data[i].indexOf("ordertype") > -1){
				params.push(_data[i]);
			}
		}
	}

	if(params.length>0){
		return "&"+params.join("&");
	}
	return "";
}

function doSearch(){
	__first="&__f=1";
	_loadCurrntReportData()
}

function doExcel(){
	var url = _reportServerUrl+getSearchParams();
	url = Com_SetUrlParameter(url,'method','exportInfo');
	url = Com_SetUrlParameter(url,'isRpt','true');
	console.log(url)
	document.dbEchartsTableForm.action = url;
	document.dbEchartsTableForm.submit();
}

function doPrint(){
	var container=$("#contentFrame").contents().find("body");
		container.jqprint({
	   debug: false, 
	   importCSS: true,
	   printContainer: true,
	   operaSupport: true
	});
}

function _loadCurrntReportData(){
	pageSize = $("#pageSize").val();
	pageno = $("#pageno").val();
	//__first=0;
	_loadReportData(parseInt(pageno));
}

function _loadReportData(pageIndex){
	var container=$("#contentFrame").contents().find("#reportContainer");
	var loading=$("#contentFrame").contents().find("#loading");
	var url=_reportServerUrl+getSearchParams();
	if(pageIndex){
		url+="&pageno="+pageIndex;
		url+="&rowsize="+pageSize;
		url+="&pagingtype=default";
		url+="&s_ajax=true";
		if(__first!=0){
			url+=__first;
			__first=0;
		}
	}
	$.ajax({
		url:url,
		type:'POST',
		success:function(data){
			container.append(getRptHtml(data));
			if(pageIndex){
				//"page":{"currentPage":1,"pageSize":2,"totalSize":4}}
				if(!data["page"]){
					loading.hide();
					return;
				}
				pageno = data["page"]["currentPage"] ;

				var pageCount=Math.ceil(data["page"]["totalSize"]/data["page"]["pageSize"]);
				var pageContainer=$("#pagingContainer");
				pageContainer.empty();
				var start=pageIndex,end=pageIndex,offset=3;
				while(offset>0){
					if((start-1)>0){
						start--;
						offset--;
					}else{
						break;
					}
				}
				offset+=3;
				while(offset>0){
					if((end+1)<=pageCount){
						end++;
						offset--;
					}else{
						break;
					}
				}
				
				if(start>1){
					pageContainer.append("<li><a href=\"javascript:_loadReportData(1)\">首页</a></li>");
					pageContainer.append("<li><a href=\"javascript:_loadReportData("+(start-1)+")\">&laquo;</a></li>");
				}
				for(var i=start;i<=end;i++){
					var li="<li";
					if(i==pageIndex){
						li+=" class=\"active\"";
					}
					li+="><a href=\"javascript:_loadReportData("+i+")\">"+i+"</a></li>";
					pageContainer.append(li);
				}
				if(end<pageCount){
					pageContainer.append("<li><a href=\"javascript:_loadReportData("+(end+1)+")\">&raquo;</a></li>");
					pageContainer.append("<li><a href=\"javascript:_loadReportData("+pageCount+")\">尾页</a></li>");
				}
				pageContainer.append("<li class='pull-left' style='width:90px;'><a>共"+pageCount+"页</a></li>");

				pageContainer.append("<li  class='pull-left'><span style='line-height:20px;'> 到第 </span> </li>");
				pageContainer.append("<li  class='pull-left'> <input type='text' style='width:60px;' class='form-control pull-right' id='pageno'  value='"+pageno+"'> </li>");
				pageContainer.append("<li  class='pull-left' style='width:60px;'><span style='line-height:20px;'> 页 </span> </li>");

				pageContainer.append("<li  class='pull-left'><span style='line-height:20px;'> 显示 </span> </li>");
				pageContainer.append("<li  class='pull-left'> <input type='text' style='width:60px;' class='form-control pull-right' id='pageSize'  value='"+pageSize+"'></li>");
				pageContainer.append("<li  class='pull-left'><span style='line-height:20px;'>条 </span> </li>");
				pageContainer.append("<li><a href=\"javascript:_loadCurrntReportData()\">GO</a></li>");

			}
			loading.hide();
		},
		error:function(){
			loading.hide();
			alert("报表计算出错！");
		},
		beforeSend:function(){
			contentHtml = [];
			container.empty();
			loading.show();
		},
		data:{}
	});
};

function getRptHtml(datas){
	var html = [];
	//#130855 统计列表没有受到自定义列宽的控制
	if( "true" == "${info.config.listview.isAdapterWidth}"){
		html.push('<table id="ureportTable" border="0" style="border-collapse:collapse;width:1450px;word-break: break-all">');
	}else{
		var listviewWidth = "${info.config.listview.width}";
		var width = 1450;
		if(listviewWidth){
			width=listviewWidth;
		}
		html.push('<table id="ureportTable" border="0" style="border-collapse:collapse;width:'+width+'px;word-break: break-all">');
	}
	html.push('<tbody>');
	html.push(constHtml.join(""));
	html.push(getContentHtml(datas));
	html.push('</tbody>');
	html.push('</table>');
	return html.join("");
}

function getValueByColName(cols,name){
	for(var i=0;i<cols.length;i++){
		if(cols[i]["col"]==name){
			return cols[i]["value"];
		}
	}
	return "";
}

var rowStatCols = <%=info.getRowStatCol().toString(2)%>;

function getRowStatColCss(colKey,clz){
	 for(var key in rowStatCols){
		if(key==colKey){
			return "rptsum";
		}
	 }
	 return clz
}

function getContentHtml(datas){
	if(!datas["columns"]){
		return "";
	}
	for(var i=0;i<datas["datas"].length;i++){
		
		var clz = "rptdata";
		<%
		if(	info.hasColStat()){
		%>
		if(i==datas["datas"].length-1){
			clz = "rptsum";
		}
		<%}%>

		contentHtml.push('<tr style="height:30px">');
		var data = datas["datas"][i];
		for(var j=0;j<datas["columns"].length;j++){
			var col = datas["columns"][j]["property"];
			if(!col){
				continue;
			}
			if(hiddenSetting[col]){
				continue;
			}
			var value = getValueByColName(data,col);
			clz = getRowStatColCss(col,clz);
			if(col=="index"){
				contentHtml.push('<td class="A4 '+clz+'" style="min-width:40px;width:40px;">'+value+'</td>');
			}else{
				//console.log("align:"+align[j-1])
				contentHtml.push('<td class="A4  '+clz+'" style="text-align:'+align[j-1]+';">'+value+'</td>');
			}
		}
		contentHtml.push('</tr>');
	}
	//console.log(contentHtml.join(""))
	return contentHtml.join("");
}

</script>
<%@ include file="/resource/jsp/watermarkPcCommon.jsp" %>
</body>
</html>