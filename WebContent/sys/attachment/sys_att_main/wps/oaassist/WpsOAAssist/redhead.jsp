<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<template:include ref="config.edit" sidebar="no">
<template:replace name="content">
<%@ include file="/km/imissive/kmImissiveRedheadDataInit.jsp"%>
<html:form action="${HtmlParam.actionUrl}">
<script type="text/javascript" src='js/main.js'></script>
		<div id="main" width="100%" height="90%" align="left"  style="100%;">
			<table>
				&nbsp;&nbsp;
				<tr>
					<td>
						<div id=treeDiv class="treediv">
						</div>				        
					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<button type="button" id="" onClick="selectTemplate()">套红头</button>
					</td>
				</tr>			
			</table>
		 <br />
    	</div>
</html:form> 
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<script>
	var s_contextPath = Com_Parameter.ContextPath;
	if(s_contextPath.length>0){
		s_contextPath = s_contextPath.substring(0,s_contextPath.length-1);
	}
	<%
	    String modelName = request.getParameter("modelName");
	    if (StringUtil.isNotNull(modelName)
			&& SimpleCategoryUtil.isAdmin(modelName)) {
	%>
	function SimpleCategory_OnLoad() { 
		generateTree();
	}
	<%  } else {%>
	function NodeFunc_FetchChildrenByXML(){
		var nodesValue = new KMSSData().AddXMLData(Com_ReplaceParameter(this.XMLDataInfo.beanURL, this)).GetHashMapArray();
		for(var i=0; i<nodesValue.length; i++){
			if(SimpleCategory_CheckAuth(nodesValue[i]))
				this.FetchChildrenUseXMLNode(nodesValue[i]);
		}
	}
	function SimpleCategory_CheckAuth(nodeValue){
		if(nodeValue["isShowCheckBox"]!="0"){
			return true;
		}
		var value = nodeValue["value"];
		for(var i=0; i<SimpleCategory_AuthIds.length; i++){
			if(SimpleCategory_AuthIds[i]["v"]==value){
				return true;
			}
		}
		return false;
	}
	var SimpleCategory_AuthIds;
	function SimpleCategory_OnLoad() { // 过滤没权限或者没有模板的分类节点
		SimpleCategory_AuthIds = new KMSSData().AddBeanData("sysSimpleCategoryAuthList&modelName=${JsParam.modelName}&authType=02").GetHashMapArray();
		generateTree();
	}
	<% } %>

var LKSTree;
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message key="tree.sysSimpleCategory.title" bundle="sys-simplecategory"/>", document.getElementById("treeDiv"));
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=false;
	LKSTree.isAutoSelectChildren = true;
	var n1, n2;
	n1 = LKSTree.treeRoot;	
	var modelName = "${JsParam.modelName}";
	n1.authType = "02"
	n2 = n1.AppendSimpleCategoryData(modelName);
	LKSTree.Show();
}
</script>
<script type="text/javascript">
window.onload= function(){
	SimpleCategory_OnLoad();
	//loadWps();
};
function loadWps(){
	var doc = wps.WpsApplication().ActiveDocument;
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	var url = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=queryRedHeadJson';
    $.ajax({
        type:"post",
        url:url,
        data:{"fdModelName":fdModelName},
        dataType:"json",
        async:false,
        success:function(data){
        	var templates = document.getElementById("selectItem");
        	
        	var content = document.getElementById('content');
   		    var selectImg = document.getElementById('selectImg');
   		    var selectItem = document.getElementById('selectItem');
   		 	var ul = document.createElement('ul');
		    selectItem.appendChild(ul);
        	for ( var i in data) {
   		        var li = document.createElement('li');
   		        li.setAttribute('value',data[i].tempId);
   		        li.innerText = data[i].tempName;
   		        ul.appendChild(li);
			}
        	selectImg.onclick = function () {
        	    console.log(selectItem.style.display);
        	    if(selectItem.style.display == 'none' || selectItem.style.display == ''){
        	        selectItem.style.display = 'block';
        	    }else{
        	        selectItem.style.display = 'none';
        	    }

        	}

        	content.onclick = function () {
        	    if(selectItem.style.display == 'none' || selectItem.style.display == ''){
        	        selectItem.style.display = 'block';
        	    }else{
        	        selectItem.style.display = 'none';
        	    }
        	}

        	var lis = selectItem.getElementsByTagName('li');
        	for(var i = 0; i < lis.length; i++){
        	    lis[i].onclick = function () {
        	        console.log(this.innerHTML,this.getAttribute('value'));
        	        var selectVal = document.getElementById('selectVal');
        	        selectVal.value = this.getAttribute('value');
        	        content.innerText = this.innerHTML;
        	        selectItem.style.display = 'none';
        	    }
        	}
        }
    });
}

function selectTemplate() {
	var wpsApp = wps.WpsApplication();
	var doc = wps.WpsApplication().ActiveDocument;
	var nodevalue = GetDocParamsValue(doc, "nodevalue");
	var modelId = GetDocParamsValue(doc, "ekpModelId");
	var fdKey = GetDocParamsValue(doc, "ekpAttMainKey");
	var token = GetDocParamsValue(doc, "wpsoaassistToken");
	var prefix = GetDocParamsValue(doc, "ekpServerPrefix");
	var bookMarks = GetDocParamsValue(doc, "bookMarks");
	var fdModelName = GetDocParamsValue(doc, "ekpModelName");
	
	var redId = $("input[name='List_Selected']:checked").val();
    if (redId == -1 || redId=="") { //添加未选中数据时的异常处理
        alert("请先选择红头文件后再进行套红头！");
        return;
    }
    var activeDoc = wpsApp.ActiveDocument;
    var base = wps.PluginStorage.getItem("getRedHeadPath") || OA_DOOR.getRedHeadPath;
    
    //获取套红模板路径
    var tempUrl=null;
    var bookmark=[];
    var url = prefix+'/km/imissive/km_imissive_redhead_template/kmImissiveRedHeadTemplate.do?method=queryRedHeadUrl';
    $.ajax({
        type:"post",
        url:url,
        data:{"redId":redId,"modelId":modelId,"nodevalue":nodevalue,"bookMarks":bookMarks,"fdKey":fdKey,"fdModelName":fdModelName},
        dataType:"json",
        async:false,
        success:function(data){
        	for ( var i in data) {
        		if (data[i].tempUrl!=null && data[i].tempUrl!=undefined && data[i].tempUrl!="") {
        			tempUrl=data[i].tempUrl;
				}
        		if (data[i].bookmark!=null && data[i].bookmark!=undefined && data[i].bookmark!="") {
        			bookmark.push(data[i].bookmark);
				}
			}
		}
     });
    var path = tempUrl;
    SetDocParamsValue(activeDoc, "insertFileUrl", path);
    if (bookmark!=null && bookmark!=undefined && bookmark!="") {
    	SetDocParamsValue(activeDoc, "bkInsertFile", bookmark);
	}else{
		SetDocParamsValue(activeDoc, "bkInsertFile", "redhead");
	}
    
    InsertRedHeadDoc(activeDoc);
    window.opener = null;
    window.open('', '_self', '');
    //window.close();
}
//判断是否是word文档
function isWord(suffix) {
    var suffixArray = ["doc", "dot", "wps", "wpt", "docx", "docm", "dotm"];
    for (var f1 in suffixArray) {
        if (suffixArray[f1].indexOf(suffix) > -1) {
            return true;
        }
    }
    return false;
}

function getAllTemplelists() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        //当接受到响应时回调该方法
        if (xmlhttp.readyState == 4 && (xmlhttp.status == 200 || xmlhttp.status == 0)) {
            var text = xmlhttp.responseText; //使用接口返回内容，响应内容
            var resultJson = JSON.parse(text) //将json字符串转换成对象    
            for (var i = 0; i < resultJson.length; i++) {
                var element = resultJson[i]
                var myOption = document.createElement("option"); //动态创建option标签
                var suffix = element.tempName.split('.')[1];
                if (isWord(suffix)) {
                    myOption.value = element.tempId; //红头文档id
                    myOption.text = element.tempName; //红头文档名称
                    templates.add(myOption);
                }
            }
        }
    }

    var redHeadsPath = wps.PluginStorage.getItem("redHeadsPath") || OA_DOOR.redHeadsPath
    if (redHeadsPath == undefined) { //未配置则模拟服务端返回
        var strData =
            '[{"tempId":23,"tempName":"广东省xx局.docx"},{"tempId":24,"tempName":"广东省xx局 - 副本.docx"},{"tempId":25,"tempName":"红头.docx"}]'
        resultJson = JSON.parse(strData);
        for (var i = 0; i < resultJson.length; i++) {
            var element = resultJson[i]
            var myOption = document.createElement("option"); //动态创建option标签
            var suffix = element.tempName.split('.')[1];
            if (isWord(suffix)) {
                myOption.value = element.tempId; //红头文档id
                myOption.text = element.tempName; //红头文档名称
                templates.add(myOption);
            }
        }
        document.getElementById("search").style.display = "none";
        return
    }
    xmlhttp.open("POST", redHeadsPath, true); //以POST方式请求该接口
    xmlhttp.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded;charset=UTF-16LE"); //添加Content-type
    xmlhttp.send(); //发送请求参数间用&分割
    if (!wps.PluginStorage.getItem("searchRedHeadPath")) {
        document.getElementById("search").style.display = "none";
    }
}

function search() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        //当接受到响应时回调该方法
        if (xmlhttp.readyState == 4 && (xmlhttp.status == 200 || xmlhttp.status == 0)) {
            var text = xmlhttp.responseText; //使用接口返回内容，响应内容
            var resultJson = JSON.parse(text) //将json字符串转换成对象
            templates.options.length = 0;
            for (var i = 0; i < resultJson.length; i++) {
                var element = resultJson[i]
                var myOption = document.createElement("option"); //动态创建option标签
                myOption.value = element.tempId; //红头文档id
                myOption.text = element.tempName; //红头文档名称
                templates.add(myOption);
            }
        }
    }

    var searchPath = wps.PluginStorage.getItem("searchRedHeadPath") || OA_DOOR.redHeadsPath

    var totalPath = searchPath + "?content=" + document.getElementById("content").value;

    xmlhttp.open("get", totalPath, true); //以POST方式请求该接口
    xmlhttp.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded;charset=UTF-16LE"); //添加Content-type
    xmlhttp.send(); //发送请求参数间用&分割
}

</script>
<style>
    *{
        margin: 0;
        padding: 0;
    }
    #main{
        position: relative;
        width: 280px;
        height: 42px;
    }
    #content{
        width: 280px;
        height: 42px;
        line-height: 42px;
        padding-left: 10px;
        background: rgb(255, 255, 255);
        border-radius: 2px;
        border: 1px solid rgb(221, 221, 221);
        font-size: 16px;
        font-family: MicrosoftYaHei;
        color: rgb(51, 51, 51);
        cursor: pointer;
    }
    #selectImg{
        position: absolute;
        top:38px;
        right: 10px;
        cursor: pointer;
        padding-left: 10px;
    }
    #selectItem{
        display: none;
        border: 1px solid #eee;
        width: 290px;
    }
    #selectItem ul{
        list-style: none;
    }
    #selectItem ul li{
        height: 30px;
        line-height: 30px;
        padding-left: 10px;
        cursor: pointer;
    }
    #selectItem ul li:hover{
        background-color:#f5f7fa;
    }
    #selectBtn{
    	dmargin: 0 auto;
	    position: absolute;
	    bottom: 30px;
	    margin: auto;
	    right: 10;
    }
</style>
</template:replace>
</template:include>