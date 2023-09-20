<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.*"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", -1);
%>

<script type="text/javascript">

var isCustom = "${JsParam['isCustom']}";
var method = "${JsParam['method']}";

//定义json数组，用于日期，时间，区域调用。
var area_date="[{area:'<bean:message bundle="sys-number" key="sysNumberMain.China"/>',data:[{name:'<bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/>',value:''},{name:'20120314',value:'yyyyMMdd'},{name:'201203',value:'yyyyMM'},{name:'2012-03-14',value:'yyyy-MM-dd'},{name:'2012-03',value:'yyyy-MM'},{name:'*2012-3-4',value:'yyyy-M-d'},{name:'*2012年3月4日',value:'yyyy&#39;年&#39;M&#39;月&#39;d&#39;日&#39;'},{name:'二〇一二年三月四日',value:'$CN$yyyy&#39;年&#39;M&#39;月&#39;d&#39;日&#39;'},{name:'二〇一二年三月',value:'$CN$yyyy&#39;年&#39;M&#39;月&#39;'},{name:'三月四日',value:'$CN$M&#39;月&#39;d&#39;日&#39;'},{name:'2012年3月4日',value:'yyyy&#39;年&#39;M&#39;月&#39;d&#39;日&#39;'},{name:'2012年3月',value:'yyyy&#39;年&#39;M&#39;月&#39;'},{name:'3月4日',value:'M&#39;月&#39;d&#39;日&#39;'},{name:'星期三',value:'E'},{name:'周三',value:'$CN$&#39;周&#39;E'},{name:'2012-3-14 1:30 PM',value:'yyyy-M-dd H:m a'},{name:'2012-3-14 13:30',value:'yyyy-M-dd HH:mm'},{name:'12-3-14',value:'yy-M-dd'},{name:'3-14',value:'M-dd'},{name:'3-14-12',value:'M-dd-yy'},{name:'14-Mar',value:'dd-MMM'},{name:'14-Mar-12',value:'d-MMM-yy'},{name:'04-Mar-12',value:'dd-MMM-yy'},{name:'Mar-12',value:'MMM-yy'},{name:'March-12',value:'MMMMM-yy'},{name:'M',value:'MMM(1)'},{name:'M-12',value:'MMM(1)-yy'},{name:'2012',value:'yyyy'},{name:'03',value:'MM'},{name:'14',value:'dd'}]},{area:'<bean:message bundle="sys-number" key="sysNumberMain.UK"/>',data:[{name:'<bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/>',value:''},{name:'14-03-2012',value:'dd-MM-yyyy'},{name:'14-03-12',value:'dd-MM-yy'},{name:'14-3-12',value:'dd-M-yy'},{name:'14.3.12',value:'dd.M.yy'},{name:'2012-03-14',value:'yyyy-MM-dd'},{name:'04 March 2012',value:'dd MMMMM yyyy'},{name:'4 March 2012',value:'d MMMMM yyyy'}]},{area:'<bean:message bundle="sys-number" key="sysNumberMain.America"/>',data:[{name:'<bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/>',value:''},{name:'3-4',value:'M-d'},{name:'3-4-12',value:'M-d-yy'},{name:'03-04-12',value:'MM-dd-yy'},{name:'14-Mar',value:'dd-MMM'},{name:'4-Mar-12',value:'d-MMM-yy'},{name:'04-Mar-12',value:'dd-MMM-yy'},{name:'Mar-12',value:'MMM-yy'},{name:'March-12',value:'MMMMM-yy'},{name:'March 14,2012',value:'MMMMM dd,yyyy'},{name:'3-14-12 1:30 PM',value:'M-dd-yy H:m a'},{name:'3-14-12 13:30',value:'M-dd-yy HH:mm'},{name:'M',value:'MMM(1)'},{name:'M-12',value:'MMM(1)-yy'},{name:'3-14-2012',value:'M-dd-yyyy'},{name:'14-Mar-2012',value:'dd-MMM-yyyy'}]}]";
var area_time="[{area:'<bean:message bundle="sys-number" key="sysNumberMain.China"/>',data:[{name:'<bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/>',value:''},{name:'*13:30:55',value:'HH:mm:ss'},{name:'*13:30',value:'HH:mm'},{name:'1:30 PM',value:'h:mm a'},{name:'13:30:55',value:'HH:mm:ss'},{name:'1:30:55 PM',value:'h:mm:ss a'},{name:'13时30分',value:'HH&#39;时&#39;mm&#39;分&#39;'},{name:'13时30分55秒',value:'HH&#39;时&#39;mm&#39;分&#39;ss&#39;秒&#39;'},{name:'下午1时30分',value:'ah&#39;时&#39;mm&#39;分&#39;'},{name:'下午1时30分55秒',value:'ah&#39;时&#39;mm&#39;分&#39;ss&#39;秒&#39;'},{name:'十三时三十分',value:'$CN$HH&#39;时&#39;mm&#39;分&#39;'},{name:'下午一时三十分',value:'$CN$ah&#39;时&#39;mm&#39;分&#39;'},{name:'133055',value:'HHmmss'},{name:'1330',value:'HHmm'},{name:'13',value:'HH'}]},{area:'<bean:message bundle="sys-number" key="sysNumberMain.UK"/>',data:[{name:'<bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/>',value:''},{name:'13:30:55',value:'H:mm:ss'},{name:'13:30:55',value:'HH:mm:ss'},{name:'01:30:55 PM',value:'hh:mm:ss a'},{name:'1:30:55 PM',value:'h:mm:ss a'}]},{area:'<bean:message bundle="sys-number" key="sysNumberMain.America"/>',data:[{name:'<bean:message bundle="sys-number" key="sysNumberMain.pleaseSelect"/>',value:''},{name:'13:30',value:'HH:mm'},{name:'1:30 PM',value:'h:mm a'},{name:'13:30:55',value:'HH:mm:ss'},{name:'1:30:55 PM',value:'h:mm:ss a'},{name:'30:55.2',value:'mm:ss.SSS'},{name:'3-14-01 1:30 PM',value:'M-dd-yy h:mm a'},{name:'3-14-01 13:30',value:'M-dd-yy HH:mm'}]}]";
var flowLenNotEmpty = '<bean:message bundle="sys-number" key="sysNumber.error.flowLenNotEmpty"/>';
var flowLenNotNumber = '<bean:message bundle="sys-number" key="sysNumber.error.flowLenNotNumber"/>';
var flowIsNotEmpty = '<bean:message bundle="sys-number" key="sysNumber.error.flowIsNotEmpty"/>';
var flowIsNotNumber = '<bean:message bundle="sys-number" key="sysNumber.error.flowIsNotNumber"/>';
var numberStartBiggerThanLength = '<bean:message bundle="sys-number" key="sysNumber.error.numberStartBiggerThanLength"/>';
var numberFirstStartBiggerThanLength = '<bean:message bundle="sys-number" key="sysNumber.error.numberFirstStartBiggerThanLength"/>';
var flowLenBiggerBit = '<bean:message bundle="sys-number" key="sysNumber.error.flowLenBiggerBit"/>';
var dateIsNotEmpty = '<bean:message bundle="sys-number" key="sysNumber.error.dateIsNotEmpty"/>';
var timeIsNotEmpty = '<bean:message bundle="sys-number" key="sysNumber.error.timeIsNotEmpty"/>';
var oneIsNotEmpty = '<bean:message bundle="sys-number" key="sysNumber.error.OneIsNotEmpty"/>';
var constIsNotNumber = '<bean:message bundle="sys-number" key="sysNumber.error.constIsNotNumber"/>';
var formulaIsNotNumber = '<bean:message bundle="sys-number" key="sysNumber.error.formulaIsNotNumber"/>';
var formulaShowNameIsNotNumber = '<bean:message bundle="sys-number" key="sysNumber.error.formulaShowNameIsNotNumber"/>';


var fdCustomTxt = '<bean:message bundle="sys-number" key="sysNumberMain.fdCustom"/>';

var Page_CanCommit = false;

//调用公式定义器
function formulaClick() {
	var modelName = $("input[name='fdModelName']").val();
	if (modelName == "") {
		modelName = "${JsParam.modelName}";
	}
	var modelVarInfo="";
	//当父窗口中有该方法则调用父窗口中的方法，避免项目重写该方法出现调用问题
	if(parent.Formula_GetVarInfoByModelName_New){
		modelVarInfo = parent.Formula_GetVarInfoByModelName_New(modelName);
	}else if(parent.Formula_GetVarInfoByModelName){
		modelVarInfo = parent.XForm_getXFormDesignerObj_${JsParam.fdKey} ? parent.XForm_getXFormDesignerObj_${JsParam.fdKey}() : parent.Formula_GetVarInfoByModelName(modelName);
	}else{
		modelVarInfo = parent.XForm_getXFormDesignerObj_${JsParam.fdKey} ? parent.XForm_getXFormDesignerObj_${JsParam.fdKey}() : Formula_GetVarInfoByModelName(modelName);
	}
	Formula_Dialog('textCustomID', 'textCustomName',modelVarInfo,'String',null,null,modelName);
}

//判断是否为正整数
function isNotNum(str) {
	var regu = /^[0-9]{1,}$/;
	return !regu.test(str);
}

//判断字符串是否为空
function isNull(str) {
	if (str == null || $.trim(str) == "")
		return true;
	else
		return false;
}


//去除辅助参数
function filterfdContent(fdContentJson){
	var newArr = [];
	for(var i = 0;i < fdContentJson.length;i++){
		var item = fdContentJson[i];
		var newJson = {};
		newJson.id = item.id;
		newJson.type = item.type;
		newJson.name = item.name;
		newJson.ruleData = item.ruleData;
		newArr.push(newJson);
	}
	return JSON.stringify(newArr);
}

var temp_control_commit=0;

//检查是否存在相同规则
function isExists(str){
	var modelName = $("input[name='fdModelName']").val();
	if (modelName == "") {
		modelName = "${JsParam.modelName}";
	}
	var modelId = $("input[name='fdId']").val();
	var fdContent = encodeURIComponent(str);
	var datas = {fdContent:fdContent,modelName:modelName,modelId:modelId};
	jQuery.ajax({
        type: "post", 
        url: "<%=request.getContextPath() %>/sys/number/sys_number_main/sysNumberMain.do?method=isExistsNumberRule", 
        dataType: "text", 
        data:datas,
        async:false,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        success: function (data) {
            if(data==null||data===undefined||data=='null' ||data==''){
            	temp_control_commit=1;
                return;
            }
            if(confirm('<bean:message bundle="sys-number" key="sysNumber.sysConfirmInfo"/><bean:message bundle="sys-number" key="sysNumber.isContinue"/>')){
            	temp_control_commit=1;
            }else{
            	temp_control_commit=2;
            }
        }
	});
}

//调整流水号起始值可能导致编号重复提示
function onFlowStartChange(value){
	var method = "${JsParam.method}";
	var mainId = $("input[name='fdId']").val();
	if(method =='edit' && value){
		var datas = {mainId:mainId,flowStart:value};
		jQuery.ajax({
            type: "post", 
            url: "<%=request.getContextPath() %>/sys/number/sys_number_main/sysNumberMain.do?method=validateFlowStart", 
            dataType: "json",
            data:datas,
            success: function (data) {
                if(data.isOk){
	                var $flowStartTip = $('#flowStartTip');
	                $flowStartTip.empty();
	                $flowStartTip.html('<bean:message bundle="sys-number" key="sysNumberMain.flowStart.warn"/>');	
    				setTimeout(function(){
    					$flowStartTip.empty();
    				},8000);
                    return;
                }
            }
		});
	}
}

//判断流水号计算范围可能会重复提示
function onFlowLimitsChange(src){
	var limit = src.name;
	var method = "${JsParam.method}";
	var mainId = $("input[name='fdId']").val();
	var selected = $(src).prop('checked');
	if(!selected){
		var datas = {mainId:mainId,limit:limit};
		jQuery.ajax({
            type: "post", 
            url: "<%=request.getContextPath() %>/sys/number/sys_number_main/sysNumberMain.do?method=validateFlowLimits", 
            dataType: "json",
            data:datas,
            success: function (data) {
                if(data.isOk){
                	//提示编号计算范围
                	$('#limits .limits_tip').remove();
                	$('#limits').append('<div class="limits_tip" style="color:red;"><bean:message bundle="sys-number" key="sysNumberMain.fdLimits.warn"/></div>');
                	setTimeout(function(){
                		$('#limits .limits_tip').remove();
    				},5000);
                    return;
                }
            }
		});
	}
}

function cls() {
	$("#showError").empty();
}

function addError(str) {
	cls();
	$("#showError").append(str);
}

function addFlowLenError(desc){
	var $flowLenTip = $('#flowLenTip');
    $flowLenTip.empty();
    $flowLenTip.html(desc);	
}

function addFlowStartError(desc){
	var $flowStartTip = $('#flowStartTip');
    $flowStartTip.empty();
    $flowStartTip.html(desc);	
}


//提交时编号元素初始化
function eleInit() {
	try{
		Page_CanCommit = false;
		var _fdContentJson = new Array();
		//过滤name和ruleData为空的元素
		$.each(fdContentJson,function(idx,item){
			if(!isNull(item.name)&&!$.isEmptyObject(item.ruleData)){
				var newsItem = {'id':item.id,'name':item.name,'type':item.type,'ruleData':item.ruleData};
				_fdContentJson.push(newsItem);
			}else{
				Page_CanCommit = true;
				return '[]'
			}
		});
		$("input[name='fdContent']").val(JSON.stringify(_fdContentJson));
		return $("input[name='fdContent']").val();
	}catch(e){
		if(window.console){
			window.console.log(e);
		}
		return '[]';
	}
}


function setFlowContent(flowId,isZeroFill,isAutoElevation){
	
	var fdFlowContent = {'id':flowId,'isZeroFill':isZeroFill+'','isAutoElevation':isAutoElevation+''};
	var fdFlowContentJson = filterFlow();
	var isUpdate = false;
	$.each(fdFlowContentJson,function(idx,item){
		if(item.id==flowId){
			item.isZeroFill=isZeroFill+"";
			item.isAutoElevation=isAutoElevation+"";
			fdFlowContentJson[idx] = item;
			isUpdate = true;
		}
	});
	if(!isUpdate){
		fdFlowContentJson.push(fdFlowContent);
	}
	
	$('#fdFlowContent').attr('value', JSON.stringify(fdFlowContentJson));
}

//去除不在当前流程元素的
function filterFlow(){
	var newArr = [];
	var _fdFlowContent = $('#fdFlowContent').val();
	if(isNull(_fdFlowContent)){
		return newArr;
	}
	try {
		var fdFlowContentJson =  eval(_fdFlowContent);
		$.each(fdContentJson,function(idx,item){
			for(var i = 0;i < fdFlowContentJson.length;i++){
				if(fdFlowContentJson[i].id==item.id){
					newArr.push(fdFlowContentJson[i]);
				}
			}
		});
	} catch (e) {
		return new Array();
	}
	return newArr;
} 




</script>

