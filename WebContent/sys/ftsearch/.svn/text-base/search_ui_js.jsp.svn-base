<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.landray.kmss.sys.ftsearch.config.LksField,
	java.util.List,
	java.util.ArrayList,
	java.util.Iterator,
	com.landray.kmss.sys.ftsearch.search.LksHit,
	java.util.Map,
	com.landray.kmss.util.ResourceUtil,
	com.landray.kmss.sys.config.dict.SysDataDict,
	com.landray.kmss.util.StringUtil,
	com.landray.kmss.sys.ftsearch.util.ResultModelName,
	com.landray.kmss.sys.ftsearch.search.AdvancedSearchIndex,
	com.sunbor.web.tag.Page,
	com.landray.kmss.sys.ftsearch.util.HtmlEscaper,
    java.util.regex.Matcher,
    java.util.regex.Pattern"%>
<script type="text/javascript"
	src="${KMSS_Parameter_ResPath}style/common/fileIcon/fileIcon.js"></script>
<%@ include file="/sys/ftsearch/autocomplete_include.jsp"%>
<link style="text/css" rel="stylesheet"
	href="${LUI_ContextPath}/sys/ftsearch/styles/search.css?id=20150804">
<link style="text/css" rel="stylesheet"
	href="${LUI_ContextPath}/sys/ftsearch/styles/multiple-select.css">
<link style="text/css" rel="stylesheet"
	href="${LUI_ContextPath}/sys/ftsearch/styles/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="js/jquery.ztree.core-3.4.js"></script>
<script type="text/javascript" src="js/jquery.ztree.excheck-3.4.js"></script>
<script type="text/javascript"><!--

	var sortType;
	var tagCubeOpen;
	var tagCubeLarge;
	var tagName="";
	var xml=""; 
	var isSearchByButton="false";
	var modelDivExpand;
	var newLUI="true";
	var facetClickPara="false";
	var selectPara="false";
	var checkBeforeFlag="false"
		
	if (!Array.prototype.indexOf) {
	    Array.prototype.indexOf = function (elt /*, from*/
	    ) {
	        var len = this.length >>> 0;
	        var from = Number(arguments[1]) || 0;
	        from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	        if (from < 0)
	            from += len;
	        for (; from < len; from++) {
	            if (from in this &&
	                this[from] === elt)
	                return from;
	        }
	        return -1;
	    };
	}
	
	 $(function () {
		    if (window.navigator.userAgent.indexOf("Firefox") > 0) {
		        var $E = function () {
		            var c = $E.caller;
		            while (c.caller)
		                c = c.caller;
		            return c.arguments[0]
		        };
		        __defineGetter__("event", $E);
		    }
		});
	
	//返回url参数
	function getUrlParameter(queryString) {
	    var UrlPara="";
	    checkBeforeFlag="false";
	    var modelName = document.getElementsByName("modelName")[0].value;
	    var srcModelName = document.getElementsByName("srcModelName")[0].value;
	    var srcQueryString  = $("#srcQueryString").val();
	    srcQueryString = encodeURIComponent(srcQueryString);
	  	
	    if(modelName == null || modelName ==""){
	        var checkbox_models = document.getElementsByName("checkbox_model");
	        //循环勾选
	        for(var i=0;i<checkbox_models.length;i++){
	            if( checkbox_models[i].checked){
	                modelName+=checkbox_models[i].value+";"; 
	            }
	        }
	    }
	    selectField();
	    var searchFields = document.getElementsByName("searchFields")[0].value;
	    if(modelName!=null && modelName!=""){
	        UrlPara += "&modelName=" + modelName; 
	    }
	
	    if(searchFields!=null && searchFields!="")
	        UrlPara += "&searchFields=" + searchFields;
	    if(tagCubeOpen!=null && tagCubeOpen!="")
	        UrlPara += "&tagCubeOpen=" + tagCubeOpen; 
	    if(tagCubeLarge!=null && tagCubeLarge!="")
	        UrlPara += "&tagCubeLarge=" + tagCubeLarge; 
	    if(modelDivExpand!=null && modelDivExpand!="")
	        UrlPara += "&modelDivExpand=" + modelDivExpand;
	    UrlPara += "&isSearchByButton=" + isSearchByButton;
	    UrlPara += "&newLUI=" + newLUI;
	    UrlPara += "&checkBeforeFlag=" + checkBeforeFlag;
	    if(facetClickPara){
	        UrlPara += "&facetClickPara=" + facetClickPara;
	    }
	
	    //高级搜索参数
	    var bond = $(":radio[name='bond']:checked").val(); 
	    UrlPara += "&bond=" + bond;
	    var outKeyword = $("#out_keyword")[0].value;
	    outKeyword = encodeURIComponent(outKeyword);
	    UrlPara += "&outKeyword=" + outKeyword;
	    var array=$("#doc_file_type").multipleSelect("getSelects")
	    var docFileType="";
	    $.each(array, function(i,val){
	        if(val!=""){
	            docFileType += val + ";";
	        }
	    }); 
	    UrlPara+="&docFileType="+docFileType;
	    if(srcQueryString == "" || srcQueryString == queryString){
		   	if(srcModelName == "" || modelName ===srcModelName){
			   	if(categoryFlag){
			        var category = getTreeData();
			        if(category){
			            UrlPara += "&category=" + category;
			        } else {
			            var zTree = $.fn.zTree.getZTreeObj("categoryTree");
			            if(zTree){
			                var nodes = zTree.getNodes();
			                if(nodes.length == 0){
			                    UrlPara += "&category=" + document.getElementsByName("category")[0].value;
			                }
			            }
			        }
			   	}
		    }
		}
	    return UrlPara;
	}

	// 获取URL中的参数，处理URL中的“#”
	function GetUrlParameter(url, param){
		 url=url.replace("#","&");
		 return Com_GetUrlParameter(url, param);
	}

	// 设置URL参数，若参数不存在则添加一个，否则覆盖原有参数，处理URL中的“#”
	function SetUrlParameter(url, param, value){
		 url=url.replace("#","&");
		 return Com_SetUrlParameter(url, param, value);
	}
	
	//点击搜索结果，跳转到数据出处
	function readDoc(fdModelName,fdCategory,fdUrl,fdSystemName,positionInPage) {
	    var e = e || this.event;
	    var obj = e.target || e.srcElement;
	    var fdDocSubject = null;
	    var tagName =  obj.tagName;
	    var id = obj.id;
	    if("FONT" == tagName){
	        var parentId = obj.parentNode.id;
	        fdDocSubject = parentId;
	    } else {
	        fdDocSubject = id; 
	    }
	    fdDocSubject = fdDocSubject.replace("\'","'").replace("&quot;","\"");
	    document.getElementById("fdDocSubject").value=fdDocSubject;
	    document.getElementById("fdModelName").value=fdModelName;
	    document.getElementById("fdCategory").value=fdCategory;
	    document.getElementById("fdUrl").value=fdSystemName+"\u001D"+fdUrl;
	    document.getElementById("fdModelId").value=GetUrlParameter(fdUrl, "fdId");
	    var queryString = GetUrlParameter(window.location.href, "queryString");
	    document.getElementById("fdSearchWord").value= queryString;
	    var pageno = GetUrlParameter(window.location.href, "pageno");
	    var rowsize = GetUrlParameter(window.location.href, "rowsize");
	    if(pageno==null || pageno=="")
	        pageno=1;
	    if(rowsize==null || rowsize=="")
	        rowsize=10;
	    var position = parseInt(pageno-1)*parseInt(rowsize) + parseInt(positionInPage) + 1;
	    document.getElementById("fdHitPosition").value=position;
	    var sysFtsearchReadLogForm = document.getElementById("sysFtsearchReadLogForm");
	
	    sysFtsearchReadLogForm.submit();
	    return null;	
	}
	
	//搜索提交--会刷新整个页面
	function CommitSearch(queryStringPos){
	    var outmodel = selectOutModelInfo();
	    var modelGroupChecked = document.getElementsByName("modelGroupChecked")[0].value;
	    if(queryStringPos==0 || queryStringPos==1) {
	        isSearchByButton="true";
	        //搜索内容 
	        var queryStringObj =document.getElementsByName("queryString")[queryStringPos];
	        if(!checkQueryString(queryStringObj)){
	            //如果搜索内容为空 则不进行搜索
	            return null;
	        }
	        var queryString = encodeURIComponent(queryStringObj.value);
	        var facetClickParaTemp=GetUrlParameter(window.location.href, "facetClickPara");
	        if(facetClickParaTemp=="true"){
	            facetClickPara="true";
	        }
	        var url = "<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString + getUrlParameter(queryString)+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked;  
	        //refreshBrowser(url);
	        return url;
	    } else {
	        if(queryStringPos==3&&(${fn:length(modelList)}>1)){
	            facetClickPara="true";
	        }
	        if(queryStringPos ==4){
	            document.getElementsByName("modelName")[0].value ="";
	        }
	        var queryString=GetUrlParameter(window.location.href, "queryString");
	        if(queryString==null || queryString=="") 
	            queryString = document.getElementsByName("queryString")[1].value;
	        if(queryString==null || queryString=="") 
	            queryString = document.getElementsByName("queryString")[0].value;
	        if(queryString==null || queryString==""){ 
	            return null;
	        } else {
	            queryString = encodeURIComponent(queryString);
	            if(queryStringPos !=3){
	            	var url = "<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString + getUrlParameter(queryString)+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked;
	                //refreshBrowser(url);
	                return url;
	            } else {
	            	var url = "<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString + getUrlParameter(queryString)+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked; 
	            	//refreshBrowser(url);
	            	return url;
	            }		
	        }
	    }
	}
	
	//选择搜索字段
	function selectField(){
	    var fields="";  
	    if(document.getElementById("type_title").checked) {
	        fields+="title;";
	    }
	    if((document.getElementById("type_content")).checked) {
	        fields+="content;";
	    }
	    if(document.getElementById("type_attachment").checked) {
	        fields+="attachment;";
	    }
	    if(document.getElementById("type_creator").checked) {
	        fields+="creator;";
	    }
	    if(document.getElementById("type_tag").checked) {
	        fields+="tag;";
	    }
	    if(fields!=""){
	        fields=fields.substring(0,fields.length-1);
	    }
	    document.getElementsByName("searchFields")[0].value= fields;
	}
	
	//检测输入内容不许为空
	function checkQueryString(obj)
	{
	    if(obj.value==""){
	        //请输入内容
	        seajs.use('lui/dialog', function(dialog) {
	            dialog.alert("${ lfn:message('sys-tag:sysTag.inputContent')}",function(){
	                obj.focus();
	            });
	        });
	        return false;
	    }
	    if(obj.value.length > 100){
	        seajs.use('lui/dialog', function(dialog) {
	            dialog.alert("${ lfn:message('sys-ftsearch-db:search.ftsearch.overlength')}",function(){
	                obj.focus();
	            });
	        });
	        return false;
	    }
	    return true;
	}
	
	//返回外部模块信息
	function selectOutModelInfo(){
	    var outValue=document.getElementsByName("multisSysModel")[0].value;
	    if(outValue==null||outValue==""&&selectPara=="false"){
	        outValue=GetUrlParameter(window.location.href, "outModel");
	    }
	    if(outValue==null){
	        outValue="";
	    }
	    return outValue;
	}
	
	//按时间搜索，设置搜索时间范围
	function searchByTime(parameter){
	    var timeRange="";
	    var queryString=document.getElementsByName("queryString")[0];//搜索内容  
	    if(!checkQueryString(queryString)){//如果搜索内容为空 则不进行搜索
	        return false;
	    } 
	    queryString=encodeURIComponent(queryString.value);//中文处理
	    if(parameter=="day"){//一天内 
	        timeRange='day';
	    }
	    else if(parameter=="week"){//一周内 
	        timeRange='week';
	    }
	    else if(parameter=="month"){//一月内 
	        timeRange='month';
	    }
	    else if(parameter=="year"){//一年内 
	        timeRange='year';
	    }
		    
	    var outmodel = selectOutModelInfo();
	    var modelGroupChecked = document.getElementsByName("modelGroupChecked")[0].value;
	    var facetClickParaTemp=GetUrlParameter(window.location.href, "facetClickPara");
	    if(facetClickParaTemp=="true"){
	    	facetClickPara="true";
	    }
	    //window.location.href="<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />" + queryString +"&type=1&timeRange="+timeRange+getUrlParameter()+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked; 
		
		var url="<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />" + queryString +"&type=1&timeRange="+timeRange+getUrlParameter(queryString)+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked; 
	    searchResult(url);
	}
	
	//按字段(搜索域)，设置搜索匹配的字段(域)
	function searchByField(){ 
	    var queryString=document.getElementsByName("queryString")[0];//搜索内容  
	    if(!checkQueryString(queryString)){
	        //如果搜索内容为空 则不进行搜索
	        $("#search_by_field input").attr("checked",true);
	        return false;
	    } 
	    queryString=encodeURIComponent(queryString.value);//中文处理
	    var fields="";  
	    if(document.getElementById("type_title").checked) {
	        fields+="title;";
	    }
	    if((document.getElementById("type_content")).checked) {
	        fields+="content;";
	    }
	    if(document.getElementById("type_attachment").checked) {
	        fields+="attachment;";
	    }
	    if(document.getElementById("type_creator").checked) {
	        fields+="creator;";
	    }
	    if(document.getElementById("type_tag").checked) {
	        fields+="tag;";
	    }
	    if(fields!=""){
	        fields=fields.substring(0,fields.length-1);
	    }
	    document.getElementsByName("searchFields")[0].value= fields;
	    var outmodel = selectOutModelInfo();
	    var modelGroupChecked = document.getElementsByName("modelGroupChecked")[0].value;
	    var facetClickParaTemp=GetUrlParameter(window.location.href, "facetClickPara");
	    if(facetClickParaTemp=="true"){
	        facetClickPara="true";
	    }
	    window.location.href="<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString +"&type=1"+getUrlParameter(queryString)+
	    "&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked; 
	}
	
	function getTreeData() {
	    var result = '';
	    var zTree = $.fn.zTree.getZTreeObj("categoryTree");
	    var arry = new Array();
	    if (zTree != null) {
	        var nodes = zTree.getCheckedNodes(true);
	        for(var i = 0;i<nodes.length ;i++){
	            var categoryLine = nodes[i].id;
				if(nodes[i].check_Child_State == 2){
					arry.push(categoryLine);
				}
				if(nodes[i].getParentNode() && arry.indexOf(nodes[i].getParentNode().id)>-1){
					continue;
				}
	            result += categoryLine+",";
	        }
	    }
	    return result;
	}
	
	//判断对象是否存在
	function docEle(id) {
	    if(document.getElementById(id)!=null)
	        return true;
	    return false;
	}
	
	//获取随机数
	function getRand(){
	    var Num=Math.floor(Math.random()*1000000);
	    return Num;
	}
	
	//相关搜索词
	function relatedSearchWord(){
	    var obj = this.event.target || this.event.srcElement;
	    
	    searchWord(obj.id);
	}
	
	//初始化摘要的展示
	function initSummary() {
	    var rowsize = GetUrlParameter(window.location.href, "rowsize");
	    if(rowsize==null || rowsize=="")
	        rowsize = "10";
	    var rows = parseInt(rowsize);
	    for(var i=0; i<rows; i++ ) {
	        var summary = document.getElementById("summary_"+i);
	        if(summary==null)
	            break;
	        if(summary.offsetHeight > 40) {
	            summary.style.height = "40px"; 
	            summary.style.overflow ="hidden";
	            summary.style.cursor ="default";
	            summary.setAttribute("ondblclick","expand()");
	        }
	    }
	}
	
	//摘要展开或收缩
	function expand() {
	    var divObject = event.srcElement;
	    if(divObject.style.overflow =="visible" ) {
	        divObject.style.height="40px";
	        divObject.style.overflow ="hidden";
	        divObject.style.cursor ="default";
	    } else if(divObject.style.overflow =="hidden" ) {
	        divObject.style.height="auto";
	        divObject.style.overflow ="visible";
	        divObject.style.cursor ="text";
	    }
	}
	
	//选择模块
	function selectModel(element){
	    var modelName="";  
	    var flag=true;
	    var entriesDesignCount=document.getElementsByName("entriesDesignCount")[0].value;
	    //循环勾选
	    for(var i=0;i<entriesDesignCount;i++){
	        if( document.getElementsByName("element"+i)[0].checked){
	            modelName+=document.getElementsByName("element"+i)[0].value+";"; 
	        }
	    }	 
	    if(modelName!=""){
	        modelName=modelName.substring(0,modelName.length-1);
	    }
	    document.getElementsByName("modelName")[0].value= modelName;
	}
	
	//模块全选
	function selectAllModel(allModelObj,system) {
	    var isChecked = allModelObj.checked;
	    var check;
	    if(isChecked)
	        check = 'checked'; 
	    else
	        check = ''; 
	    var checkbox_models = document.getElementsByName(system);
	    for(var i=0; i<checkbox_models.length; i++) {
	        checkbox_models[i].checked = check;
	    }
	    selectOutSystemModel(system);
	}

	// 模块全选--级联
	function isSelectAllModel() {
		var check=true;
	    var checkbox_models = document.getElementsByName("checkbox_model");
	    for(var i=0; i<checkbox_models.length; i++) {
		    if(!(checkbox_models[i].checked)){
		    	 check = false;
		    	 break;
			}
	    }
	    $("#selectAll_id").prop("checked", check);
	}
	
	//获取所有已选择的模块
	function getAllSelectedModel() {
	    var checkbox_models = document.getElementsByName("checkbox_model");
	    var modelTitleString = new Array();
	    for(var i=0,j=0; i<checkbox_models.length; i++) {
	        if(checkbox_models[i].checked) {
	            modelTitleString[j] = checkbox_models[i].parentNode.innerText||checkbox_models[i].parentNode.textContent;
	            j++;
	        }
	    }
	    return modelTitleString;
	}
	
	//多系统模块分类
	function selectModelGroup(obj,model){
	    var isChecked = obj.checked;
	    var check;
	    if(isChecked)
	        check = 'checked';
	    else
	        check = '';
	
	    var arrs=model.split("%");
	    for(var i = 0 ; i < arrs.length ; i++){
	        var modelInfo = new Array();
	        if(arrs[i].indexOf(":")!= -1)	{
	            modelInfo = arrs[i].split(":");
	        } else {
	            modelInfo[0] = 'checkbox_model';
	            modelInfo[1] = arrs[i];
	        }
	        var checkbox_models = document.getElementsByName(modelInfo[0]);
	        for(var j=0; j<checkbox_models.length; j++) {
	            var start = checkbox_models[j].value.indexOf("@");
	            var checkValue;
	            if(start == -1){
	                checkValue=checkbox_models[j].value;
	            } else {
	                checkValue=checkbox_models[j].value.substring(start+1,checkbox_models[j].value.length);
	            }
	            if(checkValue == modelInfo[1]){
	                checkbox_models[j].checked = check;
	            }
	        }
	        selectOutSystemModel(modelInfo[0]);
	    }
	    modelGroupChecked();
	    view_select_model();
	}
	
	function modelGroupChecked() {
		var modelGroupChecked = "";
		var modelGroups = document.getElementsByName("modelGroups");
		for (var i = 0; i < modelGroups.length; i++) {
			if (modelGroups[i].checked) {
				modelGroupChecked += modelGroups[i].value + ";";
			}
		}
		document.getElementsByName("modelGroupChecked")[0].value = modelGroupChecked;
	}
	
	//是否是IE浏览器
	function isIE(){ 
	    //ie?
	    if (window.navigator.userAgent.toLowerCase().indexOf("msie")>=1)
	        return true;
	    else
	        return false;
	}
	
	if (!isIE()) { 
		//firefox innerText define
		HTMLElement.prototype.__defineGetter__("innerText",
			function () {
			var anyString = "";
			var childS = this.childNodes;
			for (var i = 0; i < childS.length; i++) {
				if (childS[i].nodeType == 1)
					anyString += childS[i].tagName == "BR" ? '\n' : childS[i].textContent;
				else if (childS[i].nodeType == 3)
					anyString += childS[i].nodeValue;
			}
			return anyString;
		});
		HTMLElement.prototype.__defineSetter__("innerText",
			function (sText) {
			this.textContent = sText;
		});
	}
	
	//切换模块选择栏的视图
	function view_select_model() {
		var selectLi = document.getElementById("selectLi");
		var model_view = document.getElementById("model_view");
		var model_select = document.getElementById("model_select");
	
		var hiddenDiv = document.getElementById("hidden_div");
		var allUl = hiddenDiv.getElementsByTagName("ul");
	
		if (selectLi.style.display == "none") {
			modelDivExpand = "true";
			selectLi.style.display = "";
			model_view.style.display = "none";
			model_select.style.display = "";
	
			for (var i = 0; i < allUl.length; i++) {
				if (allUl[i].getAttribute("name") == "multiSysmodel_select") {
					allUl[i].style.display = "";
				}
				if (allUl[i].getAttribute("name") == "multiSysmodel_view") {
					allUl[i].style.display = "none";
				}
			}
		} else {
			modelDivExpand = "false";
			selectLi.style.display = "none";
			model_select.style.display = "none";
	
			var modelTitleString = getAllSelectedModel("checkbox_model");
			if (modelTitleString.length > 0) {
				var liObj;
				var labelObj;
				while (model_view.hasChildNodes()) {
					model_view.removeChild(model_view.firstChild);
				}
	
				for (var i = 0; i < modelTitleString.length; i++) {
					liObj = document.createElement("li"); //li创建成功
					labelObj = document.createElement("label");
					$(labelObj).text(modelTitleString[i]);
					liObj.appendChild(labelObj);
					model_view.appendChild(liObj);
				}
				model_view.style.display = "";
			}
	
			for (var i = 0; i < allUl.length; i++) {
	
				if (allUl[i].getAttribute("name") == "multiSysmodel_select") {
					allUl[i].style.display = "none";
	
					var inputModel = allUl[i].getElementsByTagName("input");
					var sysModelTitleString = new Array();
					for (var j = 0, k = 0; j < inputModel.length; j++) {
						if (inputModel[j].checked) {
							sysModelTitleString[k] = inputModel[j].parentNode.innerText || inputModel[j].parentNode.textContent;
							k++;
						}
					}
					if (sysModelTitleString.length > 0) {
						var sysliObj;
						var syslabelObj;
						while (allUl[i - 1].hasChildNodes()) {
							allUl[i - 1].removeChild(allUl[i - 1].firstChild);
						}
						for (var n = 0; n < sysModelTitleString.length; n++) {
							sysliObj = document.createElement("li"); //li创建成功
							syslabelObj = document.createElement("label");
							$(syslabelObj).text(sysModelTitleString[n]);
							sysliObj.appendChild(syslabelObj);
							allUl[i - 1].appendChild(sysliObj);
						}
						allUl[i - 1].style.display = "";
					}
				}
			}
		}
	}
	
	function pagingShow() {
		var spans = document.getElementById("pagingTools");
		if (spans != null) {
			var aTags = spans.getElementsByTagName("a");
			if (aTags != null && aTags.length > 0) {
				var status = aTags[aTags.length - 2].innerHTML.indexOf("...");
				if (status == 0) {
					aTags[aTags.length - 2].style.display = "none";
				}
			}
		}
	}
	
	window.onload = function initCube() {
		initSummary();
		modelDivExpand = GetUrlParameter(window.location.href, "modelDivExpand");
		if (modelDivExpand == "true") {
			view_select_model();
		}
		//pagingShow();
	}
	
	//分页
	LUI.ready(function () {
		seajs.use('lui/topic', function (topic) {
			var evt = {
				page : {
					currentPage : '<c:out value="${queryPage.pageno}"/>',
					pageSize : '<c:out value="${queryPage.rowsize}"/>',
					totalSize : '<c:out value="${queryPage.totalrows}"/>'
				}
			};
			topic.subscribe('paging.changed', function (evt) {
				var arr = evt.page;
				var pageno = arr[0].value;
				var rowsize = arr[1].value;
				var url = SetUrlParameter(window.location.href, "pageno", pageno);
				url = SetUrlParameter(url, "rowsize", rowsize);

				//searchResult(url);
				var newUrl = urlProcess(url);
				window.location.href=newUrl;
				replaceElement(url, ["search_list_id"]);
				$('html, body, .content').animate({scrollTop : 150}, 0);
				//window.location.href = SetUrlParameter(url, "rowsize", rowsize);
			});
		});
	})
	
	//类别点击搜索
	function facetSearch(parm) {
		if (typeof(parm) == "object") {
			parm = parm.id;
		}
		if ($("#ftsearch_facet img").length) {
			facetClickPara = "true";
		}
		var data = parm.split(".");
		var value = data[data.length - 1];
		$("#model_select input").each(function () {
			if ($.trim($(this).val()) == $.trim(value)) {
				$("#model_select input").prop("checked", false);
				$(this).prop("checked", true);
				selectOutSystemModel('checkbox_model');
			}
		});
		$("#multiSysmodel_select_id input").each(function () {
			var selectValueArr = $(this).val().split(".");
			var selectValue = selectValueArr[selectValueArr.length - 1];
			$("#multiSysmodel_select_id input").prop("checked", false);
			if ($.trim(selectValue) == $.trim(value)) {
				$(this).prop("checked", true);
			}
			selectOutSystemModel($(this).attr('name'));
		});
		if (parm.indexOf(".") == -1) {
			CommitSearch(parm);
		} else {
			CommitSearch(3);
		}
	}
	
	function showCheckModel() {
		var searchRange = document.getElementById("search_range");
		if (searchRange.style.display == "none") {
			document.getElementById("search_range").style.display = "";
		} else {
			document.getElementById("search_range").style.display = "none";
		}
	
	}
	
	function hiddenCheckModel() {
		var searchRange = document.getElementById("search_range");
		if (searchRange.style.display != "none") {
			document.getElementById("search_range").style.display = "none";
		}
	}
	
	//后退并刷新
	function backAndFresh() {
		$("#model_select input").prop("checked", false);
		selectOutSystemModel('checkbox_model');
		$("#multiSysmodel_select_id input").each(function () {
			$("#multiSysmodel_select_id input").prop("checked", false);
			selectOutSystemModel($(this).attr('name'));
		});
		CommitSearch(3);
	}
	
	LUI.ready(function () {
		$(".lui_portal_footer").show();
		var timeType = GetUrlParameter(window.location.href, "timeRange");
		$("#" + timeType).css("color", "red");
	})
	
	function getIconNameByFileName(fileName) {
		if (fileName == null || fileName == "")
			return "documents.png";
		var fileExt = fileName.substring(fileName.lastIndexOf("."));
		if (fileExt != "")
			fileExt = fileExt.toLowerCase();
		switch (fileExt) {
		case ".doc":
		case ".docx":
			return "word.png";
		case ".xls":
		case ".xlsx":
			return "excel.png";
		case ".ppt":
		case ".pptx":
			return "ppt.gif";
		case ".pdf":
			return "pdf.png";
		case ".txt":
			return "txt.gif";
		case ".jpg":
			return "image.png";
		case ".ico":
			return "image.png";
		case ".bmp":
			return "image.png";
		case ".gif":
			return "image.png";
		case ".png":
			return "image.png";
		case ".zip":
		case ".rar":
			return "zip.gif";
		case ".htm":
		case ".html":
			return "htm.gif";
		default:
			return "documents.png";
		}
	}
	
	function selectSearchField() {
		var target = this.event.target || this.event.srcElement;
		var searchField = $("input[name='search_field']");
		if (target.checked) {
			searchField.prop("checked", true);
		} else {
			searchField.prop("checked", false);
		}
	
	}
	
	function iniAutoComlete() {
		$("#q5").autocomplete({
			source : function (request, response) {
				$.ajax({
					url : "${KMSS_Parameter_ContextPath}sys/ftsearch/expand/sys_ftsearch_chinese_legend/sysFtsearchChineseLegend.do?method=searchTip&q=" + encodeURI($("#q5").val()),
					dataType : "json",
					data : request,
					success : function (data) {
						response(data);
					}
				});
			}
		});

		$("#q6").autocomplete({
			source : function (request, response) {
				$.ajax({
					url : "${KMSS_Parameter_ContextPath}sys/ftsearch/expand/sys_ftsearch_chinese_legend/sysFtsearchChineseLegend.do?method=searchTip&q=" + encodeURI($("#q6").val()),
					dataType : "json",
					data : request,
					success : function (data) {
						response(data);
					}
				});
			}

		});
	}

	function beforeDblClick(treeId, treeNode) {
		return false;
	}
	var categoryFlag = false;
	function zTreeOnClick(event, treeId, treeNode) {
		if(event.type=="click"){
			var zTree = $.fn.zTree.getZTreeObj("categoryTree");
			console.log(treeNode.getCheckStatus());
			if (treeNode.checked == true) {
				zTree.checkNode(treeNode, false, true);
			} else {
				zTree.checkNode(treeNode, true, true);
			}
		}
		categoryFlag = true;
		checkAllParent();
		searchResult(CommitSearch(0));
	}
	
	function checkAllParent(){
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		if (zTree != null) {
	        var nodes = zTree.getCheckedNodes(true);
	        for(var i = 0;i<nodes.length ;i++){
	            if(nodes[i].check_Child_State ==1){
	            	zTree.checkNode(nodes[i], false, false);
	            }
	        }
	    }
	}
	
	var setting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			nameIsHTML : true,
			showTitle : false,
			showIcon : true
		},
		callback : {
			onCheck : zTreeOnClick,
			onClick : zTreeOnClick,
			beforeDblClick : beforeDblClick
		}
	};
	
	function formatTreeData() {
		var srcCategory = $("#srcCategory").val();
		if (!srcCategory) {
			return null;
		}
		var json = $.parseJSON(srcCategory);
		var json_arr = [],
		json_len = json.length;
		for (var i = 0; i < json_len; i++) {
			var j_arr = [];
			//j_arr
			j_arr['id'] = json[i]['mapkey'];
			j_arr['name'] = json[i]['mapName'] + "<font style='color:#999;'>(" + json[i]['mapCount'] + ")</font>";
			if (json[i]['mapPid']) {
				j_arr['pId'] = json[i]['mapPid'];
				j_arr['modelName'] = json[i]['modelName'];
			} else {
				j_arr['pId'] = '0';
				j_arr['modelName'] = json[i]['key'];
			}
			j_arr['open'] = isTrueCategory(j_arr['id']);
			json_arr.push(j_arr);
		}
	
		var jsonstr = "[";
		for (i = 0; i < json_arr.length; i++) {
			jsonstr += "{\"id\":\"" + json_arr[i]['id'] + "\","
			 + "\"modelName\":\"" + json_arr[i]['modelName'] + "\","
			 + "\"pId\":\"" + json_arr[i]['pId'] + "\"" + ",\"name\":"
			 + "\"" + json_arr[i]['name']
			 + "\",\"open\":\"" + json_arr[i]['open'] + "\""
			 + ",\"checked\":\"" + "false"
			 + "\"\},";
		}
		jsonstr = jsonstr.substring(0, jsonstr.lastIndexOf(','));
		jsonstr += "]";
	
		return $.parseJSON(jsonstr);
	}
	function isTrueCategory(key){
		var category = $("#category").val();
		if (category) {
			var cates = category.split(",");
			for (var i in cates) {
				if(key == cates[i]){
					return true;
				}
			}
		}
		return false;

	}

	// 生成搜索结果类别树
	function generateCategoryTree(){
		var categorys = formatTreeData();
		if (typeof(categorys) != "undefined") {
			$.fn.zTree.init($("#categoryTree"), setting, categorys);
			var zTree = $.fn.zTree.getZTreeObj("categoryTree");
			if (zTree != null) {
				var nodes = zTree.getNodes();
				var category = $("#category").val();
				if (category) {
					var cates = category.split(",");
					for (var i in cates) {
						var checkNode = zTree.getNodeByParam("id", cates[i], null);
						if (checkNode) {
							zTree.checkNode(checkNode, true, true);
						}
					}
				}
				checkAllParent();
				traversalNode(nodes, zTree);
			}
		}else {
			$.fn.zTree.init($("#categoryTree"), setting, "[]");
		}
	}
	
	$(document).ready(function () {
		generateCategoryTree();
		iniAutoComlete();
	});
	
	function traversalNode(nodes, zTree) {
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].check_Child_State == '1' || nodes[i].check_Child_State == '2') {
				if (nodes[i].checked == false) {
					zTree.expandNode(nodes[i]);
				} else {
					zTree.expandNode(nodes[i], false);
				}
			}
	
			var children = nodes[i].children;
			if (children)
				traversalNode(children, zTree);
		}
	}
	
	//结果排序
	function sortResult(sortType) {
		//移除URL最后一个“#”字符串
		var url=window.location.href;
	    url = SetUrlParameter(url,"pageno",1);
	    //window.location.href = Com_SetUrlParameter(url,"sortType",sortType);
	    url = SetUrlParameter(url,"sortType",sortType);
	    return url;
	}
	
	//在结果中搜索
	function search_on_result() {
	    var queryString=document.getElementsByName("queryString")[1];
	    if(!checkQueryString(queryString)){
	        //如果搜索内容为空 则不进行搜索
	        return false;
	    }
	    var outmodel = selectOutModelInfo();
	    var modelGroupChecked = document.getElementsByName("modelGroupChecked")[0].value;
	    var old_queryString = GetUrlParameter(window.location.href, "queryString");
	    var str_old = old_queryString.replace("&"," ").split(' ');
	    var str_new = queryString.value.split(' ');
	    for(var i = 0 ; i < str_old.length ; i++){
	        for(var j = 0 ; j < str_new.length ; j++){
	            if(str_old[i]==str_new[j]){
	                seajs.use('lui/dialog', function(dialog) {
	                    dialog.alert("${ lfn:message('sys-ftsearch-db:search.ftsearch.existError1')}"+str_new[j]+ "${ lfn:message('sys-ftsearch-db:search.ftsearch.existError2')}");
	                });
	                return false;
	            }
	        }
	    }
	    $('#q5').focus();
	    queryString = old_queryString + "&" + queryString.value;
	    var url = SetUrlParameter(window.location.href,"pageno",1)+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked;
	    var checkBeforeFlag=GetUrlParameter(url, "checkBeforeFlag");
	    if(checkBeforeFlag==""||checkBeforeFlag==null){
	        //结果中搜索不纠错
	        url=url;
	    } else {
	        checkBeforeFlag="true";
	        url=SetUrlParameter(url,"checkBeforeFlag",checkBeforeFlag);
	    }
	    window.location.href = SetUrlParameter(url,"queryString",queryString);
	}
	
	//点击热词或相关搜索词查询
	function searchWord(queryString) {
		if(queryString){
			var queryInput = $("#q5");
			queryInput.val(queryString);
			$("#q6").val(queryString);
			queryInput.focus(); 
		}
	    queryString = encodeURIComponent(queryString);
	    var UrlPara="";
	    var modelName = document.getElementsByName("modelName")[0].value;
	    var searchFields = document.getElementsByName("searchFields")[0].value;
	    if(modelName!=null && modelName!="")
	        UrlPara += "&modelName=" + modelName; 
	    if(searchFields!=null && searchFields!="")
	        UrlPara += "&searchFields=" + searchFields;
	    if(modelDivExpand!=null && modelDivExpand!="")
	        UrlPara += "&modelDivExpand=" + modelDivExpand;
	    UrlPara += "&newLUI=" + newLUI;
	    var outmodel = selectOutModelInfo();
	    var facetClickParaTemp=GetUrlParameter(window.location.href, "facetClickPara");
	    if(facetClickParaTemp=="true"){
	        UrlPara += "&facetClickPara=true";
	    }
	    
	    var modelGroupChecked = document.getElementsByName("modelGroupChecked")[0].value;
	    //window.location.href="<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString + UrlPara+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked;

		var url="<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString + UrlPara+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked;
		searchResult(url);
	}
	
	//点击纠错中原错词（依然搜索）
	function searchOldWord(queryString) {
	    checkBeforeFlag="true";
	    queryString = encodeURIComponent(queryString);
	    var UrlPara="";
	    var modelName = document.getElementsByName("modelName")[0].value;
	    var searchFields = document.getElementsByName("searchFields")[0].value;
	    if(modelName!=null && modelName!="")
	        UrlPara += "&modelName=" + modelName; 
	    if(searchFields!=null && searchFields!="")
	        UrlPara += "&searchFields=" + searchFields;
	    if(modelDivExpand!=null && modelDivExpand!="")
	        UrlPara += "&modelDivExpand=" + modelDivExpand;
	    UrlPara += "&newLUI=" + newLUI;
	    UrlPara += "&checkBeforeFlag=" + checkBeforeFlag;
	    var outmodel = selectOutModelInfo();
	    var modelGroupChecked = document.getElementsByName("modelGroupChecked")[0].value;
	    window.location.href="<c:url value='/sys/ftsearch/searchBuilder.do?method=search&queryString=' />"+queryString + UrlPara+"&outModel="+outmodel+"&modelGroupChecked="+modelGroupChecked; 
	}
	
	//外部系统模块
	function selectOutSystemModel(system){
	    var modelName="";  
	    var flag=true;
	    var checkbox_models = document.getElementsByName(system);
	    //循环勾选
	    for(var i=0;i<checkbox_models.length;i++){
	        if( checkbox_models[i].checked){
	            modelName+=checkbox_models[i].value+";"; 
	        }
	    }
	    if(modelName!=""){
	        modelName=modelName.substring(0,modelName.length-1);
	    }
	    if(system == 'checkbox_model'){
	        document.getElementsByName("modelName")[0].value= modelName;
	    } else {
	        selectPara="true";
	        document.getElementById(system+'_systemName').value = modelName;
	        var modelStr = "";
	        var hiddenDiv = document.getElementById("hidden_div");
	        var allUl = hiddenDiv.getElementsByTagName("ul");
	        for(var i = 0 ; i < allUl.length;i++){
	            if(allUl[i].getAttribute("name") == "multiSysmodel_select"){
	                var inputModel = allUl[i].getElementsByTagName("input");
	                for(var j=0,k=0; j<inputModel.length; j++) {
	                    if(inputModel[j].checked) {
	                        modelStr += inputModel[j].value+";";
	                    }
	                }
	            }	
	        }
	        if(modelStr!=""){
	            modelStr=modelStr.substring(0,modelStr.length-1);
	        }
	        document.getElementsByName("multisSysModel")[0].value= modelStr;
	    }
	}

	// 使用URL 刷新浏览器
	function refreshBrowser(url,isScrollToBottom){
		//window.location.href = url;
		searchResult(url,isScrollToBottom);
	}
	
	//提交搜索，在当前页面异步搜索返回
	function searchResult(url,isScrollToBottom) {
		//移除URL最后一个“#”字符串
		//var url=window.location.href;
		//alert(url);
		//url = url.replace("#", "");
		//重新设置参数
		//url = Com_SetUrlParameter(url, "pageno", 1);
		//url = Com_SetUrlParameter(url, param, value);
		//记录浏览器历史
		//if(window.history.pushState)
		//{
		//	var state = {
		//			title : document.title,
		//			url : url
		//	};
		//	window.history.pushState(state, document.title, url);
		//}
		//发送请求并替换搜索结果
		
		//URL的"#"处理
		var oldUrl = location.search;
		var newUrl = urlProcess(url);
		window.location.href=newUrl;

		if(getUrlPath(newUrl) == getUrlPath(oldUrl)){
			replaceElement(url, ["inputData_id","search_list_id","related_searches_id","timeRangeSelect_id"],isScrollToBottom);
		}
	}

	//获取URL ?之后 #号前的部分 
	function getUrlPath(url){
		var index1 = url.indexOf("?");
		var index2 = url.indexOf("#");
		if(index1 >= 0 && index2 >= 0){
			return url.substring(index1,index2);
		}
		return url;
	}

	
	// 处理URL，为了使得改变浏览器的URL地址而不刷新数据，在URL里加“#”号
	function urlProcess(url){
		var path = location.pathname;
		var search = "";
		var params = getParamsToArray(url);
		for (var i = 0; i < params.length; i++) {
			if("method" == params[i][0]){
				search += "method=" + params[i][1] + "#";
			}
		}
		
		for (var i = 0; i < params.length; i++) {
			if("queryString" == params[i][0]){
				search += "queryString=" + params[i][1] + "&";
			}
			if("newLUI" == params[i][0]){
				search += "newLUI=" + params[i][1] + "&";
			}
		}
		
		for (var i = 0; i < params.length; i++) {
			if("method" == params[i][0]){
				continue;
			}
			if("queryString" == params[i][0]){
				continue;
			}
			if("newLUI" == params[i][0]){
				continue;
			}
			search += params[i][0] + "=" + params[i][1];
			if(i < (params.length-1)){
				search += "&";
			}
		}
		
		return path + "?" + search;
	}

	// 获取url请求参数  location.search
	function getParamsToArray(url) {
		url=url.replace("#","&");
		var params = new Array();
		if(url.indexOf("?") < 0)
		{
			return theRequest;
		}
		var search = url.substring(url.indexOf("?"));
	    if (search.indexOf("?") != -1) {
	        var str = search.substr(1);
	        strs = str.split("&");
	        var count=0;
	        for (var i = 0; i < strs.length; i++) {
				if(strs[i].split("=")[0]==""){
					continue;
				}
	        	params[count] = new Array();
	        	params[count][0]=strs[i].split("=")[0];
	        	params[count][1]=strs[i].split("=")[1];
	        	count ++;
	        }
	    }
	    return params;
	}
	
	//使用不同的参数重新发送请求，替换页面原来的元素
	function replaceElement(url, elementIDs,isScrollToBottom) {
		var waitDialog ;
		// 是否调用了waitDialog.hide()
		var isWaitDialogHide = false ;
		seajs.use(["lui/dialog"],function(dialog,$){
			if((isWaitDialogHide == false)) {
				waitDialog = dialog.loading();
			}
		});
		
		$.get(url, function (data) {
			//仅提取主体内容
			var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
			var matches = pattern.exec(data);
			if (matches) {
				data = matches[1];
			}
			var tmp = $('<div></div>').html(data);
			//tmp.find("script").remove();
			//替换元素
			if ((typeof elementIDs) == 'object' && elementIDs.constructor == Array) {
				//数组
				for (var i = 0; i < elementIDs.length; i++) {
					data = tmp.find("#" + elementIDs[i]).html();
					$("#" + elementIDs[i]).html(data);
					//alert(elementIDs[i]);
				}
			}
			if ((typeof elementIDs) == 'string' && elementIDs.constructor == String) {
				data = tmp.find("#" + elementIDs).html();
				$("#" + elementIDs).html(data);
			}
			
			//移除临时数据
			tmp.remove();
			
			var categoryFlag1 = $("#categoryFlag").val();
			if (categoryFlag1) {
				generateCategoryTree();
			} else if (!categoryFlag) {
				generateCategoryTree();
			}
			iniAutoComlete();
			categoryFlag = false;
			$("#search_list_id").css("visibility", "visible");
			$("#search_left_id").css("visibility", "visible");
			seajs.use(["lui/parser"], function (parser, $) {
				parser.parse();
			});

			if (waitDialog) {
				waitDialog.hide();
			}
			isWaitDialogHide = true;

			if(isScrollToBottom){
				scrollToBottom();
			}
			
			var bond = Com_GetUrlParameter(url, "bond");
			if (bond == "and") {
				$("#bond_and_id").prop("checked", true);
			} else {
				$("#bond_or_id").prop("checked", true);
			}
		});
	};
	
	//选择附件，或不选附件时
	function selectAttachment() {
		if (document.getElementById("type_attachment").checked) {
			//启用
			$('#doc_file_type').multipleSelect('enable');
		} else {
			//禁用
			$("#doc_file_type").multipleSelect("setSelects", new Array());
			$('#doc_file_type').multipleSelect('disable');
		}
	};
	
	$(document).ready(function () {
		// 获取“#”后面的参数
        var str = location.hash;
        if(str.indexOf("=") > 0){
    		var url=location.href.replace("#","&");
    		location.href=urlProcess(url);
    		replaceElement(url, ["search_wrapper","inputData_id"]);
        } else {
			$("#search_list_id").css("visibility","visible");
			$("#search_left_id").css("visibility","visible");
		}
	});

	// 页面移到最底部
	function scrollToBottom(){
    	$('html, body, .content').animate({
    		scrollTop : $(document).height()
    	}, 0);
	}
	
	// 搜索域全选级联效果
	function isSelectAllField(){
		var typeTag = $("#type_tag").is(':checked');
		var typeTitle = $("#type_title").is(':checked');
		var typeContent = $("#type_content").is(':checked');
		var typeAttachment = $("#type_attachment").is(':checked');
		var typeCreator = $("#type_creator").is(':checked');
		if(typeTag && typeTitle && typeContent && typeAttachment && typeCreator){
			$("#type_allselect").prop("checked", true);
		} else {
			$("#type_allselect").prop("checked", false);
		}
	}
--></script>









