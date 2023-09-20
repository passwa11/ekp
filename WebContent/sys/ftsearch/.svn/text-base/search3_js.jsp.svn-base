<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="com.landray.kmss.sys.ftsearch.util.PersonSearchConfigUtil" %>
<%@page import="java.net.URL"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%
	boolean relevancePerson = PersonSearchConfigUtil.relevancePerson();
	String modelName = "com.landray.kmss.third.intell.model.IntellConfig";
	if(com.landray.kmss.util.ModelUtil.isExisted(modelName)){
		Class clz = Class.forName(modelName);
		Object instance = clz.newInstance();
		String itEnabled = (String)clz.getMethod("getItEnabled", null).invoke(instance, null);
		if("true".equals(itEnabled)||"1".equals(itEnabled)){
			request.setAttribute("itEnabled",itEnabled);
			String itUrl = (String)clz.getMethod("getItUrl", null).invoke(instance, null);
			String searchUrl = "/labc-search/#/?q=";
					try{
						String tmpUrl =	(String)clz.getMethod("getSearchUrl", null).invoke(instance, null);
							if(StringUtil.isNotNull(tmpUrl)){
								searchUrl = tmpUrl;
							}
					
					}catch(Exception e){
						
					}
			if(StringUtil.isNotNull(itUrl)){
				itUrl = StringUtil.formatUrl(itUrl);
				request.setAttribute("itUrl",itUrl);
				String searchEnabled =  (String)clz.getMethod("getSearchEnabled", null).invoke(instance, null);
				if("true".equals(searchEnabled)||"1".equals(searchEnabled)){
					URL url = new URL(itUrl);
					String host = ("https".equalsIgnoreCase(url.getProtocol())?"https://":"http://")+url.getHost()+(url.getPort()==-1||url.getPort()==80||url.getPort()==443?"":":"+url.getPort());
					if(host != ""){
						host =host+searchUrl;
					}
					request.setAttribute("__searchHost__", host);
				}
			}
		}
	}
%>
<script>
	seajs.use(['theme!zone','sys/fans/sys_fans_main/style/view.css']);
</script>
<script type="text/javascript">

    //官方语言
    var officialLang="<%=SysLangUtil.getOfficialLang()%>";
	// 外部模块数量
	var outModelCount = ${fn:length(otherSysDesign)};

	// 异步请求时的浮层
	var waitDialog = null;
	// 异步请求浮层 是否打开
	var waitDialogIsOpen = false ;

	// 是否是因为点击 搜索范围树 才发出搜搜请求的---搜索结果不变将其设置为true
	var isClickTreeCategorySearch = false;

	// 搜索范围树
	var treeCategoryObject = null;
	
	var max_search_word_length = 200;
	
	var searchId;

	var menuDatas;

	var front;
	var searchModel;
	var searchPeople;
	var searchAll;
	var bondFlag="bond";
    var lang=null;
    
    //时间排序升序或降序
    var timeOrder =null;
 	function change(elem){
 		
 		var topKeyword = $("#topKeyword").val();
 		if(trim(topKeyword).length ==0||topKeyword == null || trim(topKeyword) == ""){
			seajs.use('lui/dialog', function (dialog) {
				// 请输入内容
				dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
					$("#topKeyword").focus();
				});
			});
 		}else{
 		var url;
 		var searchString=GetUrlParameter(location.search + location.hash,"searchAll");
 		var mktarget=GetUrlParameter(location.search + location.hash, "mktarget");
		if("searchAll" == elem.id){
			searchId="searchAll";
			url = Com_Parameter.ContextPath + 'sys/ftsearch/searchBuilder.do?method=search&';
			var searchHost = "${__searchHost__}"
			if(searchHost!=""){
				var url = '${__searchHost__}';
			}else{
				var url = Com_Parameter.ContextPath+"sys/ftsearch/searchBuilder.do?method=search&queryString=";
			}
			url = url + encodeURIComponent(trim(topKeyword));
			url = url + "&newLUI=true";
			url = url + "&searchAll=true";
			url = url + "&pageno=1";
			if(mktarget) {
				url = url + "&mktarget="+mktarget;
			}
			window.location.href=url;
		}
		if("searchPeople" == elem.id){
			searchId="searchPeople";
			url = Com_Parameter.ContextPath + 'sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getPersons';
			url = url + "&fdSearchName="+encodeURIComponent(trim(topKeyword))+"&searchPeople=true";
			if(mktarget) {
				url = url + "&mktarget="+mktarget;
			}
			window.location.href=url;
		}
		if("searchModel" == elem.id){
			searchId="searchModel";
			url = Com_Parameter.ContextPath + 'sys/common/searchModel.jsp?query='+encodeURIComponent(trim(topKeyword));
			url=url+"&searchModel=true";
			if(mktarget) {
				url = url + "&mktarget="+mktarget;
			}
			window.location.href=url;
		}
 		}
	} 
		
	// 获取子类对象
	function getChildCategory(pid) {
		if(null == treeCategoryObject) {
			return null;
		}
		var child = new Array();
		if(null == pid || undefined == pid) {
			$(treeCategoryObject).each(function (index, object){
				if(false == object.hasOwnProperty("mapPid")) {
					child.push(explainStr(object));
				}
			});
		} else {
			$(treeCategoryObject).each(function (index, object){
				if(object.hasOwnProperty("mapPid") && object.mapPid == pid) {
					child.push(explainStr(object));
				}
			});
		}
		return child;
	}

	// 根据ID查找 节点对象
	function getCategoryById(id) {
		if(null == treeCategoryObject) {
			return null;
		}
		if(null == id || undefined == id) {
			return null;
		}
		var result = null;
		$(treeCategoryObject).each(function (index, object){
			if(object.hasOwnProperty("mapkey") && object.mapkey == id) {
				result = object;
				return false;
			}
		});
		return result;
	}

	// 清除节点选中状态
	function clearCategoryStat() {
		if(null == treeCategoryObject) {
			return null;
		}
		$(treeCategoryObject).each(function (index, object){
			object.select = false;
		});
	}

	//  附件图标
	function getIconNameByFileName(fileName) {
		if (fileName == null || fileName == "") {
			return "documents.png";
		}
		var fileExt = fileName.substring(fileName.lastIndexOf("."));
		if (fileExt != "") {
			fileExt = fileExt.toLowerCase();
			fileExt = fileExt.replace(/<font>/,"");
			fileExt = fileExt.replace(/<\/font>/g,"");
		}
		switch (fileExt) {
		case ".doc":
		case ".docx":
			return "icon_word.png";
		case ".xls":
		case ".xlsx":
			return "icon_excel.png";
		case ".ppt":
		case ".pptx":
			return "icon_ppt.png";
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

	// 获取URL中的参数
	function GetUrlParameter(url, param){
		url=url.replace("#","&");
		var re = new RegExp();
		re.compile("[\\?&]"+param+"=([^&]*)", "i");
		var arr = re.exec(url);
		if(arr==null)
			return null;
		else
			return decodeURIComponent(arr[1]);
	}

	// 打开搜索结果文档
	function readDoc(fdDocSubject,fdModelName,fdCategory,fdUrl,fdSystemName,positionInPage) {
	    //fdDocSubject = fdDocSubject.replace("\'","'").replace("&quot;","\"");
		$("#fdDocSubject").val(fdDocSubject);
		$("#fdModelName").val(fdModelName);
		$("#fdCategory").val(fdCategory);
		$("#fdUrl").val(fdSystemName + "\u001D" + fdUrl);
		$("#fdModelId").val(GetUrlParameter(fdUrl, "fdId"));
		var params = getUrlParam();
		if(false == params.hasOwnProperty("queryString")) {
			params.queryString = "";
		}
		$("#fdSearchWord").val(params.queryString);
		if(false == params.hasOwnProperty("pageno")) {
			params.pageno = 1;
		}
		if(false == params.hasOwnProperty("rowsize")) {
			params.rowsize = 10;
		}
		var position = parseInt(params.pageno-1)*parseInt(params.rowsize) + parseInt(positionInPage) + 1;
		$("#fdHitPosition").val(position);
	    
	    /*var sysFtsearchReadLogForm = document.getElementById("sysFtsearchReadLogForm");  
	    sysFtsearchReadLogForm.submit();*/
	   
		var reg=/&/g;
		turl = $("#sysFtsearchReadLogForm").attr("action");
		turl = turl+"&fdDocSubject="+encodeURIComponent(fdDocSubject);
		turl = turl+"&fdModelName="+fdModelName;
		turl = turl+"&fdCategory="+fdCategory;
		turl = turl+"&fdModelId="+GetUrlParameter(fdUrl, "fdId");
		var nfdUrl=encodeURIComponent(fdSystemName + "\u001D" + fdUrl);//fdUrl.replace(reg,'%26');
		turl = turl+"&fdUrl="+ nfdUrl;
		turl = turl+"&fdSearchWord="+encodeURIComponent(params.queryString);
		turl = turl+"&fdHitPosition="+position;
		window.open(turl,"_blank");
	}

	// 展开搜索模块
	function modelSelect() {
		$("#search_range_table ul").each(function (index, element){
			if("none" == $(element).css("display")) { 
				$(element).css("display", "block");
				$("label[alt='selected']").css("display", "none");
				$("label[alt='Select All']").css("display", "inline");
			} else if("block" == $(element).css("display")){
				$(element).css("display", "none");
				$("label[alt='Select All']").css("display", "none");
				$("label[alt='selected']").css("display", "inline");
			}
		}); 
	}

	// 显示搜索信息错误
	function showMoreErrInfo(title) {
		seajs.use(['lui/dialog', 'lui/jquery'], function (dialog, $) {
			var obj = document.getElementById("EsErrorDiv");
			if (obj != null) {
				if (obj.style.display == "none") {
					dialog.build({
						config : {
							width : 700,
							height : 400,
							lock : true,
							cache : true,
							content : {
								type : "element",
								elem : obj
							},
							title : title
						},
						callback : function (value, dialog) {},
						actor : {
							type : "default"
						},
						trigger : {
							type : "default"
						}
					}).show();
				}
			}
		});
	}
	
	// 自动补全是否需要
	var autocompleteOpen = true;
	
	function iniAutoComlete() { 
		$("#topKeyword").autocomplete({//jquery.ui.js中的autocomplete插件经过修改，以便支持历史记录显示
			onesearch:true,
			buttonName:"${lfn:message('sys-ftsearch:sysFtsearch.delete')}",
			deleteHistoryFun:function(fdSearchWord){
				$.ajax({
					url : "${KMSS_Parameter_ContextPath}sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=deleteHistory",
					dataType : "json",
					data:{"fdSearchWord":fdSearchWord},
					success : function (data) {
					}
				});
			},
			source : function (request, response) {
				if($("#topKeyword").val() == ""){
					$.ajax({
						url : "${KMSS_Parameter_ContextPath}sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=queryHistorys",
						dataType : "json",
						data : request,
						success : function (data) {
							if(data.result == undefined || data.result == null){
								return;
							} else {
								response(data.result);
							}
						}
					});
				} else {
					$.ajax({
						url : "${KMSS_Parameter_ContextPath}sys/ftsearch/sysFtsearchAutoComplete.do?method=searchTip&q=" + encodeURIComponent(searchWordSubstring($("#topKeyword").val())),
						dataType : "json",
						data : request,
						success : function (data) {
							if(true == autocompleteOpen) {
								response(data);
							}
						}
					});
				}
				
			},
			select : function( event, ui ) {
				commitSearchByKeyword(ui.item.value);
			}
		});
		$("#bottomKeyword").autocomplete({
			source : function (request, response) {
				$.ajax({
					url : "${KMSS_Parameter_ContextPath}sys/ftsearch/sysFtsearchAutoComplete.do?method=searchTip&q=" + encodeURIComponent($("#bottomKeyword").val()),
					dataType : "json",
					data : request,
					success : function (data) {
						if(true == autocompleteOpen) {
							response(data);
						}
					}
				});
			},
			select : function( event, ui ) {
				commitSearchByKeyword(ui.item.value);
			}
		});
	}
	
	// 由页面上部的搜索框触发的搜索请求--上
	function commitSearchByTopKeywordInput() {
		commitSearch(function (params) {
			var outKeyword = $("#out_keyword").val();
			var topKeyword = $("#topKeyword").val();
			var url;
			
			if("searchPeople" == searchId){
				if(trim(topKeyword).length ==0||topKeyword == null || trim(topKeyword) == ""){
					seajs.use('lui/dialog', function (dialog) {
						// 请输入内容
						dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
							$("#topKeyword").focus();
						});
					});
				}else{
					url = Com_Parameter.ContextPath + 'sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getPersons';
					url = url + "&fdSearchName="+encodeURIComponent(trim(topKeyword))+"&searchPeople=true";
					window.location.href=url;
				}
			}else if("searchModel" == searchId){
				if(trim(topKeyword).length ==0||topKeyword == null || trim(topKeyword) == ""){
					seajs.use('lui/dialog', function (dialog) {
						// 请输入内容
						dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
							$("#topKeyword").focus();
						});
					});
				}else{
					url = Com_Parameter.ContextPath + 'sys/common/searchModel.jsp?query='+encodeURIComponent(trim(topKeyword));
					url=url+"&searchModel=true";
					window.location.href=url;

				}
			}else{//统一搜索
				if(front != "searchAll" && front != undefined){
					if(trim(topKeyword).length ==0||topKeyword == null || trim(topKeyword) == ""){
						seajs.use('lui/dialog', function (dialog) {
							// 请输入内容
							dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
								$("#topKeyword").focus();
							});
						});
					}else{
						url = Com_Parameter.ContextPath + 'sys/ftsearch/searchBuilder.do?method=search&';
						url = url + "queryString=" + encodeURIComponent(trim(topKeyword));
						url = url + "&newLUI=true";
						url = url + "&searchAll=true";
						url = url + "&pageno=1";
						var bond;
						if("bond" !=bondFlag){
							url = url+ "&bond="+bondFlag;
						}
						
						url = url+ "&outKeyword="+outKeyword;
						window.location.href=url;
					}
				}else{
					params.queryString = topKeyword; //更新关键字
					if (topKeyword == null || $.trim(topKeyword) == "") {
						seajs.use('lui/dialog', function (dialog) {
							// 请输入内容
							dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
								$("#topKeyword").focus();
							});
						});
						return false;
					}
					params.outKeyword = outKeyword;
					params.pageno = 1;
					// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
					if(params.hasOwnProperty("category")) {
					    delete params.category;
					}
					return true;
				}
			}
		});
	}

	// 由页面下部的搜索框触发的搜索请求-下
	function commitSearchByBottomKeywordInput() {
		commitSearch(function (params) {
			var bottomKeyword = $("#bottomKeyword").val();
			params.queryString = bottomKeyword; //更新关键字
			if (bottomKeyword == null || $.trim(bottomKeyword) == "") {
				seajs.use('lui/dialog', function (dialog) {
					// 请输入内容
					dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
						$("#bottomKeyword").focus();
					});
				});
				return false;
			}
			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			return true;
		});
	}

	// 在结果中搜索
	function commitSearchByResult() {
		var keyword = $("#bottomKeyword").val();
		if("" == $.trim(keyword)) {
			seajs.use('lui/dialog', function (dialog) {
				// 请输入内容
				dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
					$("#bottomKeyword").focus();
				});
			});
			return ;
		}
		
		commitSearch(function (params) {
			if(params.hasOwnProperty("queryString") && "" != $.trim(params.queryString)) {
				var str_old = params.queryString.replace("&"," ").split(' ');
				var str_new = keyword.split(' ');
			    for(var i = 0 ; i < str_old.length ; i++){
			        for(var j = 0 ; j < str_new.length ; j++){
			            if(str_old[i] == str_new[j] && "" != $.trim(str_old[i])){
				            // 已经按照“XXX”搜索过一遍啦，请尝试其他的搜索词  
			                seajs.use(['lui/util/str','lui/dialog'], function(str, dialog) {
				                var info = "${lfn:message('sys-ftsearch-db:search.ftsearch.existError1')}" + str.encodeHTML(str_new[j]) + "${lfn:message('sys-ftsearch-db:search.ftsearch.existError2')}";
			    				dialog.alert(info, function () {
			    					$("#bottomKeyword").focus();
			    				});
			                });
			                return false;
			            }
		            }
	            }
				params.queryString = $.trim(params.queryString) + "&" + $.trim(keyword);
				var outKeyword = $("#out_keyword").val();
				params.outKeyword = outKeyword;
				params.pageno = 1;
				// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
				/*if(params.hasOwnProperty("category")) {
				    delete params.category;
				}*/
			} else {
				return false;
			}
			return true;
		});
	}
	
	// 上一页搜索请求
	function commitSearchByPrePage() {
		isClickTreeCategorySearch = true;
		commitSearch(function (params) {
			if(params.hasOwnProperty("pageno")) {
				var pageno = parseInt(params.pageno) - 1;
				if(pageno < 1) {
					return false;
				}
				params.pageno = pageno;
			} else {
				return false;
			}
			return true;
		});
	}
	
	// 下一页搜索请求
	function commitSearchByNextPage() {
		isClickTreeCategorySearch = true;
		commitSearch(function (params) {
			if (params.hasOwnProperty("pageno")) {
				var pageno = parseInt(params.pageno) + 1;
				if (pageno > parseInt($("#page_total_span_top").text())) {
					return false;
				}
				params.pageno = pageno;
			} else {
				params.pageno = 2;
			}
			return true;
		});
	}

	// 搜索排序,搜索请求
	function commitSearchBySort(sortType) {
		isClickTreeCategorySearch = true;
		commitSearch(function (params) {
			params.sortType = sortType;
			if(sortType == "time"){
				if(timeOrder == "desc"){
					timeOrder = "asc";
				}else{
					timeOrder ="desc";
				}
				params.sortOrder=timeOrder;
			}else{
				params.sortOrder=null;
			}
			return true;
		});
	}

	// 按时间范围过滤
	function commitSearchByTimeScope(time) {
		hideTimefilter();
		commitSearch(function (params) {
			params.timeRange = time;

			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			if(params.hasOwnProperty("fromCreateTime")) {
			    delete params.fromCreateTime;
			}
			if(params.hasOwnProperty("toCreateTime")) {
			    delete params.toCreateTime;
			}
			return true;
		});
	}
	function commitSearchByDayStart(startTime) {
		
		commitSearch(function (params) {
			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			params.fromCreateTime=startTime;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			if(params.hasOwnProperty("timeRange")) {
			    delete params.timeRange;
			}
			return true;
		});
	}
	function commitSearchByDayEnd(endTime) {
		commitSearch(function (params) {
			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			params.toCreateTime=endTime;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			if(params.hasOwnProperty("timeRange")) {
			    delete params.timeRange;
			}
			return true;
		});
	}
	// 更新搜索关键字--相关搜索
	function commitSearchByKeyword(keyword) {
		commitSearch(function (params) {
			

			if("searchPeople" == searchId){
				url = Com_Parameter.ContextPath + 'sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getPersons';
				url = url + "&fdSearchName="+encodeURIComponent(keyword)+"&searchPeople=true";
				window.location.href=url;
			}else if("searchModel" == searchId){
				url = Com_Parameter.ContextPath + 'sys/common/searchModel.jsp?query='+encodeURIComponent(keyword);
				url=url+"&searchModel=true";
				window.location.href=url;
			}else{
				params.queryString = keyword;
				var outKeyword = $("#out_keyword").val();
				params.outKeyword = outKeyword;
				params.pageno = 1;
				// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
				if(params.hasOwnProperty("category")) {
				    delete params.category;
				}
				return true;
			}
		});
	}

	// 纠正关键字--还是使用之前的词搜索
	function commitSearchByOldKeyword(keyword) {
		commitSearch(function (params) {
			params.queryString = keyword;
			params.checkBeforeFlag="true";
			
			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			return true;
		});
	}
	
	// 关键字关系--包含全部的关键字、包含任意一个关键词
	function commitSearchByKeywordBond(bond) {
		hideAdvfilter();
		bondFlag=bond;
		commitSearch(function (params) {
			params.bond = bond;

			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			return true;
		});
	}

	// 不包括以下关键词
	function commitSearchByOutKeyword() {
		hideAdvfilter();
		var outKeyword = $("#out_keyword").val();
		commitSearch(function (params) {
			params.outKeyword = outKeyword;

			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			return true;
		});
	}

	// 分页栏跳转按钮
	function commitSearchByGo() {
		isClickTreeCategorySearch = true;
		var rowsize = parseInt($("#page_show_input").val());
		var pageno = parseInt($("#page_select_input").val());
		if($.isNumeric(rowsize) && $.isNumeric(pageno)) {
			commitSearch(function (params) {
				if(pageno <=1 ) {
					pageno = 1;
				}
				params.pageno = pageno;
				if(rowsize >= 150) {
					rowsize = 150;
				}
				params.rowsize = rowsize;
				return true;
			});
		}
	}
	
	// 分页栏输入页码到指定页
	function commitSearchByPageSelectInput() {
		isClickTreeCategorySearch = true;
		var pageno = parseInt($("#page_select_input").val());
		if(false == $.isNumeric($("#page_select_input").val()) || pageno < 1) {
			$("#page_select_input").val("");
		}
		
		var theEvent = this.event || arguments.callee.caller.arguments[0];
		var code = theEvent.keyCode || theEvent.which;
		if(code != 13){
			return ;
		}
		commitSearchByGo();
	}

	// 分页栏输入每页显示的数据量
	function commitSearchByPageShowInput() {
		isClickTreeCategorySearch = true;
		var rowsize = parseInt($("#page_show_input").val());
		if(false == $.isNumeric($("#page_show_input").val()) || rowsize < 1) {
			$("#page_show_input").val("");
		}
		var theEvent = this.event || arguments.callee.caller.arguments[0];
		var code = theEvent.keyCode || theEvent.which;
		if(code != 13){
			return ;
		}
		commitSearchByGo();
	}

	// 分页插件按钮
	function commitSearchByPageBtn() {
		isClickTreeCategorySearch = true;
		var theEvent = this.event || arguments.callee.caller.arguments[0];
		var obj = theEvent.target || theEvent.srcElement;
		var pageno = parseInt($(obj).text());
		if($.isNumeric(pageno)) {
			commitSearch(function (params) {
				if(pageno <= 1 ) {
					pageno = 1;
				}
				params.pageno = pageno;
				return true;
			});
		}
	}

	// 搜索域
	function commitSearchByFields() {
		var searchFields = "";
		
		$("#search_fields > a").each(function (index, element){
			var clazz = $(element).attr("class");
			var id = $(element).attr("id");
			if("item2" == $.trim(clazz) && null != id){
				var field = id.replace("search_field_","");
				if("" == $.trim(field)) {
					return true;
				}
				if("" == searchFields) {
					searchFields = field
				} else {
					searchFields = searchFields + ";" + field
				}
			}
		});
		
		// console.log(searchFields);
		//判断搜索范围是否有选择,如果有择刷新 搜索范围,没有则刷新
	   if($("#entries_design_top > a").hasClass("item2")==true){
		   commitSearch1(function (params) {
				if("" == $.trim(searchFields)) {
					// 默认搜索域
					delete params.searchFields;
					delete params.docFileType;
				} else {
					params.searchFields = searchFields;
				}

				var outKeyword = $("#out_keyword").val();
				params.outKeyword = outKeyword;
				params.pageno = 1;
				// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
				/*if(params.hasOwnProperty("category")) {
				    delete params.category;
				}*/
				return true;
			});
	   }else{
		   commitSearch(function (params) {
				if("" == $.trim(searchFields)) {
					// 默认搜索域
					delete params.searchFields;
					delete params.docFileType;
				} else {
					params.searchFields = searchFields;
				}

				var outKeyword = $("#out_keyword").val();
				params.outKeyword = outKeyword;
				params.pageno = 1;
				// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
				/*if(params.hasOwnProperty("category")) {
				    delete params.category;
				}*/
				return true;
			});
		   
	   }	
		
	}

	// 本系统搜索范围
	function commitSearchByModelName() {
		var modelName = "";
		$("#model_select input").each(function (index, element){
			var id = $(element).attr("id");
			if(true == element.checked && null != id){
				var model = id.replace("model_","");
				if("" == $.trim(model)) {
					return true;
				}
				if("" == modelName) {
					modelName = model
				} else {
					modelName = modelName + ";" + model
				}
			}
		});

		var modelNameByOut = "";
		for(var i = 0; i < outModelCount; i++) {
			$("#sysNamesBySelectAll_" + i + " input").each(function (index, element){
				var id = $(element).attr("id");
				if(true == element.checked && null != id){
					var model = id.replace("model_","");
					if("" == $.trim(model)) {
						return true;
					}
					if("" == modelNameByOut) {
						modelNameByOut = model
					} else {
						modelNameByOut = modelNameByOut + ";" + model
					}
				}
			});
		}

		var hasSelected = hasSelectedModel();
		if(!hasSelected){
			return
		}

		
		commitSearch(function (params) {
			if("" == $.trim(modelName)) {
				delete params.modelName;
			} else {
				params.modelName = modelName;
			}
			if("" == $.trim(modelNameByOut)) {
				delete params.outModel;
			} else {
				params.outModel = modelNameByOut;
			}
			
			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			return true;
		});
	}

	// 全选本系统搜索范围
	function commitSearchByModelNameAll() {
		var selectAll = $("#model_select_all").prop("checked");
		if(true != selectAll) {
			selectAll = false;
		}
		$("#model_select input").each(function (index, element){
			element.checked = selectAll;
		});

		var hasSeleted = hasSelectedModel();
		if(hasSeleted){
			commitSearchByModelName();
		}
	}

	function hasSelectedModel(){
		var hasSeleted = false;
		var modelChecks = $("input[alt='modelCheck']");
		
		for(var i=0;i<modelChecks.length;i++){
			if(modelChecks[i].checked){
				hasSeleted = true;
				break;
			}
		}
		return hasSeleted;
	}

	// 外部系统搜索范围
	function commitSearchByOutModel(size) {
		outModelCount = size;
		var modelName = "";
		for(var i = 0; i < size; i++) {
			$("#sysNamesBySelectAll_" + i + " input").each(function (index, element){
				var id = $(element).attr("id");
				if(true == element.checked && null != id){
					var model = id.replace("model_","");
					if("" == $.trim(model)) {
						return true;
					}
					if("" == modelName) {
						modelName = model
					} else {
						modelName = modelName + ";" + model
					}
				}
			});
		}

		var hasSelected = hasSelectedModel();
		if(!hasSelected){
			return
		}
		var modelNameByEkp = "";
		$("#model_select input").each(function (index, element){
			var id = $(element).attr("id");
			if(true == element.checked && null != id){
				var model = id.replace("model_","");
				if("" == $.trim(model)) {
					return true;
				}
				if("" == modelNameByEkp) {
					modelNameByEkp = model
				} else {
					modelNameByEkp = modelNameByEkp + ";" + model
				}
			}
		});

		
		commitSearch(function (params) {

			if("" == $.trim(modelNameByEkp)) {
				delete params.modelName;
			} else {
				params.modelName = modelNameByEkp;
			}

			if("" == $.trim(modelName)) {
				delete params.outModel;
			} else {
				params.outModel = modelName;
			}

			var outKeyword = $("#out_keyword").val();
			params.outKeyword = outKeyword;
			params.pageno = 1;
			// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
			if(params.hasOwnProperty("category")) {
			    delete params.category;
			}
			return true;
		});
	}
	
	// 全选外部统搜索范围
	function commitSearchByOtherModelNameAll(id) {
	    //var e = e || this.event;
	    var e = this.event || arguments.callee.caller.arguments[0];
	    var obj = e.target || e.srcElement;
		var selectAll = $(obj).prop("checked");
		if(true != selectAll) {
			selectAll = false;
		}
		$("#" + id + " input").each(function (index, element){
			element.checked = selectAll;
		});
		var hasSelected = hasSelectedModel();
		if(hasSelected){
			commitSearchByOutModel(outModelCount);
		}	
	}

	// 搜索模块范围过滤
	function commitSearchByModelFilter() {
		if(null == treeCategoryObject) {
			return ;
		}
		var category = "";
		$(treeCategoryObject).each(function (index, object){
			if(object.hasOwnProperty("select") && true == object.select) {
				category = category + object.mapkey + ",";
			}
		});
		$("#catagoryAll").removeClass("item1");
		$("#catagoryAll").removeClass("item2");
		if(category==""){
			$("#catagoryAll").addClass("item2");
		}else{
			$("#catagoryAll").addClass("item1");
		}
		commitSearch(function (params) {
			if("" == $.trim(category)) {
				delete params.category;
			} else {
				params.category = category;
			}
			params.pageno = 1;
			return true;
		});
	}
	
	// 用户提交搜索请求
	function commitSearch(modifyParams){
		if(false == $.isFunction(modifyParams)){
			return ;
		}
		// 通过浏览器地址栏获取当前参数
		var params = getUrlParam(); 
		// 通过用户选择更新参数
		if(false == modifyParams(params)){
			return ;
		}
		if(false == params.hasOwnProperty("queryString") || params.queryString == null || "" == $.trim(params.queryString)) {
			return ;
		}
		if(params.queryString != null && params.queryString.length > max_search_word_length){
			$("#maxLengthTipDiv").show();
		}
		else{
			$("#maxLengthTipDiv").hide();
		}
		params.queryString = searchWordSubstring(params.queryString);
		// 更新浏览器地址栏 
		var baseUrl = location.pathname;
		var hashStr = "";
		var showParams = [];
		for(var param in params){
			if( "method" == param || "checkBeforeFlag" == param || "resultType" == param || "mktarget" == param) {
				continue; 
			}
			if((new RegExp("^_show")).test(param)) {
				var item = {};
				item.key = param;
				item.value = params[param];
				showParams.push(item);
				continue;
			}
			hashStr = hashStr + "&" + param + "=" + encodeURIComponent(params[param]);
		}
		baseUrl += "?method=search";
		if(showParams.length > 0) {
			for(var i = 0; i < showParams.length; i ++) {
				baseUrl = Com_SetUrlParameter(baseUrl, showParams[i].key,  showParams[i].value);
			}
		}
		if(params && params.mktarget) {
			location.href = baseUrl + "&mktarget=" + params.mktarget + "#" + hashStr;
		} else if(params && params.formDing) {
			search(params);
			return ;
		}else {
			location.href = baseUrl + "#" + hashStr;
		}
		
		//发送搜索请求
		search(params);
	}
	
	//用户提交搜索请求、不对 搜索范围进行刷新
	function commitSearch1(modifyParams){
		if(false == $.isFunction(modifyParams)){
			return ;
		}
		// 通过浏览器地址栏获取当前参数
		var params = getUrlParam(); 
		// 通过用户选择更新参数
		if(false == modifyParams(params)){
			return ;
		}
		if(false == params.hasOwnProperty("queryString") || params.queryString == null || "" == $.trim(params.queryString)) {
			return ;
		}
		if(params.queryString != null && params.queryString.length > max_search_word_length){
			$("#maxLengthTipDiv").show();
		}
		else{
			$("#maxLengthTipDiv").hide();
		}
		params.queryString = searchWordSubstring(params.queryString);
		// 更新浏览器地址栏 
		var baseUrl = location.pathname;
		var hashStr = "";
		for(var param in params){
			if( "method" == param || "checkBeforeFlag" == param || "resultType" == param || "mktarget" == param) {
				continue;
			}
			hashStr = hashStr + "&" + param + "=" + encodeURIComponent(params[param]);
		}
		if(params && params.mktarget) {
			location.href = baseUrl + "?method=search&mktarget=" + params.mktarget + "#" + hashStr;
		} else {
			location.href = baseUrl + "?method=search#" + hashStr;
		}
		//发送搜索请求
		search1(params);
	}

	
	
	// 获取浏览器地址栏所有的参数(包括“#”后面的参数)
	function getUrlParam() {
		var params = {};
		params.queryString = "";
		var paramStr = location.search + location.hash;
		
		paramStr = paramStr.replace("?", "");
		if (paramStr.indexOf("=") > 0) {
			paramStr = paramStr.replace("#", "&");
			var strArray = paramStr.split("&");
			var count = 0;
			for (var i = 0; i < strArray.length; i++) {
				var param = strArray[i].split("=");
				if (param.length != 2) {
					continue;
				}
				params[param[0]] = decodeURIComponent(param[1]);
				count++;
			}
			//console.log(params);
		}
		params.method="search";
		return params;
	}

	//验证参数，每次请求前验证，参数不正确使用默认值
	function validateParams(searchParam) {
		
	}
	
	// 异步请求搜索
	function search(searchParam){
		// 关闭智能提示
		autocompleteOpen = false;
		$("#bottomKeyword").autocomplete("close");
		$("#topKeyword").autocomplete("close");

		seajs.use(["lui/dialog"],function(dialog){
			if((false == waitDialogIsOpen)) {
				waitDialog = dialog.loading();
				waitDialogIsOpen = true;
				//console.log("dialog.loading()");
			}
			
			validateParams(searchParam);
			if(searchParam == null) {
				return;
			}
			var baseUrl = location.pathname;
			searchParam.method="search";
			//console.log(baseUrl);
			//console.log(searchParam);
			//请求参数应用到界面
			paramsMappingUI(searchParam);
			searchParam.resultType="json";
			$.ajax({
				url : baseUrl,
				data : searchParam,
				cache : false,
				async : true,
				type : "GET",
				dataType : 'json',
				success : function (data) {
					//console.log(data);
					//搜索报错提示
					if(data.hasOwnProperty("EsError") && "" != $.trim(data.EsError)) {
						$("#EsErrorDiv").html("");
						var pre = $('<pre class="brush: bash;"/>').html(data.EsErrorStackTrace);
						$("#EsErrorDiv").append(pre);
						SyntaxHighlighter.highlight();
						seajs.use('lui/dialog', function(dialog) {
							var mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.search.info')}";
							if(data.EsErrorCode == "EEC000"){
							    mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.search.info')}";
							}
							else if (data.EsErrorCode == "EEC001"){
							    mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.disconnect.info')}";
							}
							else if (data.EsErrorCode == "EEC002"){
							    mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.no.initial.info')}";
							}
							dialog.alert("<img src='styles/images/plus.png' style='cursor: pointer;' onclick='showMoreErrInfo(\"" 
									+ data.EsError 
									+ "\");' />"+mess);// 
						});
						return ;
					}
					
					// 更新浏览器地址栏 -- 修正浏览器地址栏的pageno参数
					if(searchParam.hasOwnProperty("pageno") && data.hasOwnProperty("queryPage") && searchParam.pageno > data.queryPage.total) {
						searchParam.pageno = data.queryPage.total;
						var baseUrl = location.pathname;
						var hashStr = "";
						for(var param in searchParam){
							//console.log(param);
							if( "method" == param || "checkBeforeFlag" == param || "resultType" == param){
								continue;
							}
							hashStr = hashStr + "&" + param + "=" + encodeURIComponent(searchParam[param]);
						}
						location.href = baseUrl + "?method=search#" + hashStr;
					}
		
					// 清除之前返回的数据
					// 清除相关的人
					$("#relevance_person > li").each(function (index, data){
						$(data).remove();
					});
					// 隐藏相关人员区块
					$("#search_m_bodyR").css("display", "none");
					$("#search_m_bodyL").removeClass("search_m_bodyLwidth");
					var formatAttr=data.mapSet.format.split("-");
					lang=formatAttr[1];
					$("#searchUserTime").text(data.userTime); //设置搜索用时
					setHotwordList(data.hotwordList);//设置近期热门搜索
					setRelevantwordList(data.relevantwordList);//设置相关搜索
					setEntriesDesign(data.entriesDesign);//设置搜索范围--模块
					setOtherSysDesign(data.otherSysDesign); //设置搜索范围--外部模块
					setFieldList(data.fieldList);//设置搜索域
					setSearchCorrected(data, searchParam);//设置搜索纠正
					setQueryPage(data.queryPage, data, searchParam);//设置搜索结果
					setTreeCategory(data.treeCategory, data);// 搜索范围树
					setDocFileType(data.docFileType);// 设置附件类型
					setDocStatus(data.docStatus);
					var treeCategory=data.treeCategory;
					
					//搜索结束 isWaitDialogHide
					if (null != waitDialog && true == waitDialogIsOpen) {
						waitDialog.hide();
						waitDialogIsOpen = false;
						//console.log("waitDialog.hide()");
					}
					autocompleteOpen = true;
					// 得到Jquery版本
					//console.log("Jquery版本:" + $.fn.jquery);
				},
				complete : function (request, textStatus) {
					//console.log("ajaxSuccess");
					//搜索结束
					if (null != waitDialog && true == waitDialogIsOpen) {
						waitDialog.hide();
						waitDialogIsOpen = false;
						//console.log("waitDialog.hide()");
					}
				},
				error : function (request, textStatus, errorThrown) {
					//console.log("ajaxError");
					if(request.status == 200){
						location.href = "${KMSS_Parameter_ContextPath}";
					}
					
					if(request.status == 500){
						seajs.use('lui/dialog', function(dialog) {
							dialog.alert("服务端异常，请刷新当前页面获取异常信息！");
						});
					}
				}
			});
		});
		
	}

	//异步请求搜索、 不会对搜索范围进行刷新
	function search1(searchParam){
		// 关闭智能提示
		autocompleteOpen = false;
		$("#bottomKeyword").autocomplete("close");
		$("#topKeyword").autocomplete("close");

		seajs.use(["lui/dialog"],function(dialog){
			if((false == waitDialogIsOpen)) {
				waitDialog = dialog.loading();
				waitDialogIsOpen = true;
				//console.log("dialog.loading()");
			}
			
			validateParams(searchParam);
			if(searchParam == null) {
				return;
			}
			var baseUrl = location.pathname;
			searchParam.method="search";
			//console.log(baseUrl);
			//console.log(searchParam);
			//请求参数应用到界面
			paramsMappingUI(searchParam);
			searchParam.resultType="json";
			$.ajax({
				url : baseUrl,
				data : searchParam,
				cache : false,
				async : true,
				type : "GET",
				dataType : 'json',
				success : function (data) {
					//console.log(data);
					//搜索报错提示
					if(data.hasOwnProperty("EsError") && "" != $.trim(data.EsError)) {
						$("#EsErrorDiv").html("");
						var pre = $('<pre class="brush: bash;"/>').html(data.EsErrorStackTrace);
						$("#EsErrorDiv").append(pre);
						SyntaxHighlighter.highlight();
						seajs.use('lui/dialog', function(dialog) {
							var mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.search.info')}";
							if(data.EsErrorCode == "EEC000"){
							    mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.search.info')}";
							}
							else if (data.EsErrorCode == "EEC001"){
							    mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.disconnect.info')}";
							}
							else if (data.EsErrorCode == "EEC002"){
							    mess = "${lfn:message('sys-ftsearch-db:ftsearch.error.no.initial.info')}";
							}
							dialog.alert("<img src='styles/images/plus.png' style='cursor: pointer;' onclick='showMoreErrInfo(\"" 
									+ data.EsError 
									+ "\");' />"+mess);// 
						});
						return ;
					}
					
					// 更新浏览器地址栏 -- 修正浏览器地址栏的pageno参数
					if(searchParam.hasOwnProperty("pageno") && data.hasOwnProperty("queryPage") && searchParam.pageno > data.queryPage.total) {
						searchParam.pageno = data.queryPage.total;
						var baseUrl = location.pathname;
						var hashStr = "";
						for(var param in searchParam){
							//console.log(param);
							if( "method" == param || "checkBeforeFlag" == param || "resultType" == param){
								continue;
							}
							hashStr = hashStr + "&" + param + "=" + encodeURIComponent(searchParam[param]);
						}
						location.href = baseUrl + "?method=search#" + hashStr;
					}
		
					// 清除之前返回的数据
					// 清除相关的人
					$("#relevance_person > li").each(function (index, data){
						$(data).remove();
					});
					// 隐藏相关人员区块
					$("#search_m_bodyR").css("display", "none");
					$("#search_m_bodyL").removeClass("search_m_bodyLwidth").addClass('search_l_bodyLwidth');
					//$("#searchUserTime").text(data.userTime); //设置搜索用时
					setHotwordList(data.hotwordList);//设置近期热门搜索
					setRelevantwordList(data.relevantwordList);//设置相关搜索
					setEntriesDesign(data.entriesDesign);//设置搜索范围--模块
					setOtherSysDesign(data.otherSysDesign); //设置搜索范围--外部模块
					setFieldList(data.fieldList);//设置搜索域
					setSearchCorrected(data, searchParam);//设置搜索纠正
					setQueryPage(data.queryPage, data, searchParam);//设置搜索结果
					//setTreeCategory(data.treeCategory, data);// 搜索范围树
					setDocFileType(data.docFileType);// 设置附件类型
					setDocStatus(data.docStatus);
					
					//搜索结束 isWaitDialogHide
					if (null != waitDialog && true == waitDialogIsOpen) {
						waitDialog.hide();
						waitDialogIsOpen = false;
						//console.log("waitDialog.hide()");
					}
					autocompleteOpen = true;
					// 得到Jquery版本
					//console.log("Jquery版本:" + $.fn.jquery);
				},
				complete : function (request, textStatus) {
					//console.log("ajaxSuccess");
					//搜索结束
					if (null != waitDialog && true == waitDialogIsOpen) {
						waitDialog.hide();
						waitDialogIsOpen = false;
						//console.log("waitDialog.hide()");
					}
				},
				error : function (request, textStatus, errorThrown) {
					//console.log("ajaxError");
					if(request.status == 200){
						location.href = "${KMSS_Parameter_ContextPath}";
					}
					
					if(request.status == 500){
						seajs.use('lui/dialog', function(dialog) {
							dialog.alert("服务端异常，请刷新当前页面获取异常信息！");
						});
					}
				}
			});
		});
		
	}
	
	function searchWordSubstring(queryString){
		if(queryString.length > max_search_word_length){
			queryString = queryString.substring(0,max_search_word_length);
		}
		return queryString;
	}

	//请求成功之后，请求参数应用到界面
	function paramsMappingUI(searchParam){
		var queryString = searchParam.queryString;
		while(queryString.indexOf("&") >= 0) {
			queryString = queryString.replace("&", " ");
		}
		$("#topKeyword").val(queryString);
		$("#bottomKeyword").val(queryString);

		// 关键字关系UI
		$("#keyword_bond_or").prop("checked", false);
		$("#keyword_bond_and").prop("checked", false);
		if(searchParam.hasOwnProperty("bond")) {
			if("and" == searchParam.bond) {
				$("#keyword_bond_and").prop("checked", true);
			} else if("or" == searchParam.bond){
				$("#keyword_bond_or").prop("checked", true);
			} else if("like" == searchParam.bond){
				$("#keyword_bond_like").prop("checked", true);
			}
		} else {
			
			var url = "${KMSS_Parameter_ContextPath}sys/ftsearch/searchBuilder.do?method=getAjaxDefaultFtSearchModeConfig"
			$.ajax( {
	            url : url,
	            method : "post",
	            dataType : "text",
	            async: false,
	            success : function(data) {
	                if (data == 'or'){
	                	$("#keyword_bond_or").prop("checked", true);
	                }
	                else if (data == 'and'){
	                	$("#keyword_bond_and").prop("checked", true);
	                }
	                else if (data == 'like'){
	                	$("#keyword_bond_like").prop("checked", true);
	                }
	                else{
	                	$("#keyword_bond_or").prop("checked", true);
	                }
			}
			});
		}

		// 不包括以下关键词
		if(searchParam.hasOwnProperty("outKeyword")) {
			$("#out_keyword").val(searchParam.outKeyword);
		} else {
			$("#out_keyword").val("");
		}
		
		// 排序UI
		$("#sort_by_relevance").removeClass("on");
		$("#sort_by_read").removeClass("on");
		$("#sort_by_time").removeClass("on");
		$("#timeSort").removeClass("onDesc");
		$("#timeSort").removeClass("onAsc");
		$("#timeSort").addClass("onDefault");
		if(searchParam.hasOwnProperty("sortType")) {
			if("time" == searchParam.sortType) {
				$("#timeSort").removeClass("onDefault");
				$("#timeSort").addClass("onDesc");
				$("#sort_by_time").addClass("on");
				if(searchParam.hasOwnProperty("sortOrder")){
					if(searchParam.sortOrder == "asc"){
						$("#timeSort").removeClass("onDesc");
						$("#timeSort").addClass("onAsc");
					}
				}
			} else if("readCount" == searchParam.sortType) {
				$("#sort_by_read").addClass("on");
			} else {
				$("#sort_by_relevance").addClass("on");
			}
		} else {
			$("#sort_by_relevance").addClass("on");
			$("#timeSort").addClass("onDefault");
		}
		
		// 按时间过滤UI
		$("#time_range_day").removeClass("current");
		$("#time_range_week").removeClass("current");
		$("#time_range_month").removeClass("current");
		$("#time_range_year").removeClass("current");
		$("#time_range_all").removeClass("current");
		$("#startTime").val("");
		$("#endTime").val("");
		if(searchParam.hasOwnProperty("timeRange")) {
			if("day" == searchParam.timeRange) {
				$("#time_range_day").addClass("current");
			} else if("week" == searchParam.timeRange) {
				$("#time_range_week").addClass("current");
			} else if("month" == searchParam.timeRange) {
				$("#time_range_month").addClass("current");
			} else if("year" == searchParam.timeRange) {
				$("#time_range_year").addClass("current");
			} else {
				$("#time_range_all").addClass("current");
			}
		}
		//参数填入时间搜索框
		if(searchParam.hasOwnProperty("fromCreateTime")) {
			$("#startTime").val(searchParam.fromCreateTime);
		} 
		if(searchParam.hasOwnProperty("toCreateTime")) {
			$("#endTime").val(searchParam.toCreateTime);
		} 
	}
	
	// 设置近期热门搜索
	function setHotwordList(hotwordList) {
		if($.isArray(hotwordList)) {
			// 移除之前的数据
			$("#hotwordList > span").each(function (index, data){
				//console.log($(data).css("cursor"));
				if($(data).css("cursor") == "pointer"){
					$(data).remove();
				}
			});
			// 增加现在的数据
			$(hotwordList).each(function (index, data){
				var span = $("<span/>").html(data);
				span.css("cursor","pointer");
				span.click(function() {
					commitSearchByKeyword($(this).text());
				});
				$("#hotwordList").append(span);
			});
		} else {
			//console.log(hotwordList);
		}
	}

	//设置相关搜索
	function setRelevantwordList(relevantwordList){
		if($.isArray(relevantwordList)){
			// 移除之前的数据
			$("#relevantwordList > li").each(function (index, data){
				//console.log(data);
				$(data).remove();
			});
			// 增加现在的数据
			$(relevantwordList).each(function (index, data){
				var a = $("<a/>").html(data);
				a.attr("href","javascript:void(0)");
				a.attr("title", data);
				a.bind("click", function () {
					commitSearchByKeyword($(this).text());
				}); 
				var li = $("<li/>").append(a);
				$("#relevantwordList").append(li);
			});
			if(relevantwordList !=null && relevantwordList.length >0){
				$("#relevant_search").show();
			}else{
				$("#relevant_search").hide();
			}
			
		} else {
			$("#relevantwordList").hide();
			//console.log(relevantwordList);
		}
	}

	// 搜索范围--模块
	function setEntriesDesign(entriesDesign) {
		if($.isArray(entriesDesign)){
			// 移除之前的数据
			$("#model_view > li").each(function (index, data){
				//console.log(data);
				$(data).remove();
			});
			$("#model_select > li").each(function (index, data){
				//console.log(data);
				$(data).remove();
			});
			// 全部模块不选 等于 全选
			var selectAll = true ;
			$(entriesDesign).each(function (index, data) {
				if(true == data.flag) {
					selectAll = false;
					return false;
				}
			});
			selectAll = selectAll && (outModelCount <= 0);
			// 增加现有数据
			$(entriesDesign).each(function (index, data){ 
				var li = $('<li/>');
				li.html('<label for="' + ("model_" + data.modelName) 
						+ '" style="cursor: pointer;"><input alt="modelCheck" onclick="commitSearchByModelName();" style="margin-right: 3px; vertical-align: middle;" id="' + ("model_" + data.modelName) 
						+ '" type="checkbox" ' + (true == (data.flag || selectAll) ? 'checked' : '') + ' >' +  data.title + '</label>');
				$("#model_select").append(li);
				
				if(true == data.flag || selectAll) {
					var li = $("<li/>").html(data.title);
					$("#model_view").append(li);
				}
			});
		} else {
			
		}
	}
	
	// 搜索范围--外部模块
	function setOtherSysDesign(otherSysDesign) {
		if($.isArray(otherSysDesign)){
			$(otherSysDesign).each(function (i, items){
				// 移除之前的数据
				$("#sysNamesBySelectAll_" + i + "_view > li").each(function (index, data){
					$(data).remove();
				});
				
				$(items).each(function (j, item){
					var modelName = item.modelName.replace("@","\\@");
					while(modelName.indexOf(".") >= 0) {
						modelName = modelName.replace(".", "⊥");
					}
					while(modelName.indexOf("⊥") >= 0) {
						modelName = modelName.replace("⊥", "\\.");
					}
					if(item.hasOwnProperty("flag") && true == item.flag) {
						$("#model_" + modelName).prop("checked", true);
						
						var li = $("<li/>").html(item.title);
						$("#sysNamesBySelectAll_" + i + "_view").append(li);
						
					} else {
						$("#model_" + modelName).prop("checked", false);
					}
					// console.log($("#model_" + modelName));
				});
			});
		}
	}
	
	//设置搜索域
	function setFieldList(fieldList) {
		$("#search_field_").removeClass("item1");
		$("#search_field_").removeClass("item2");
		if($.isArray(fieldList)){
			$(fieldList).each(function (index, field){
					$("#search_fields > a").each(function (index, element){
						if ($(element).attr("id") == "search_field_" + field) {
							$(element).children(".checkbox").removeClass("checked");
							$(element).removeClass("item1");
							$(element).removeClass("item2");
							
							$(element).children(".checkbox").addClass("checked");
							$(element).addClass("item2");
						}
					});
				$("#search_field_").addClass("item1");
			});
		}else{
			$("#search_field_").addClass("item2");
		}
		if ("item2" == $("#search_field_attachment").attr("class")) {
			//启用
			$('#doc_file_type').multipleSelect('enable');
		} else {
			//禁用
			$('#doc_file_type').multipleSelect('disable');
		}
	}

	// 设置搜索纠正
	function setSearchCorrected(data, searchParam){
		$("#corrected_checkWord").unbind()
		$("#search_queryString").unbind()
		
		if(data.hasOwnProperty("checkWord") && null != data.checkWord) {
			var aCorrect = "";
			for(var i=0;i<data.checkWord.length;i++){
				var word = data.checkWord[i];
				aCorrect+='<a href="javascript:commitSearchByKeyword(\''+word+'\');" style="color: red;font-size:14px;font-weight:bold;">'+word+'</a>&nbsp&nbsp';
			}
			$("#corrected_checkWord").html(aCorrect);
			//$("#show_checkWord").html(data.checkWord);
			$("#search_queryString").html(searchParam.queryString); 

			/*$("#corrected_checkWord").bind("click", function(){
				commitSearchByKeyword(data.checkWord);
			});
			$("#search_queryString").bind("click", function(){
				commitSearchByOldKeyword(searchParam.queryString);
			});*/
			$("#search_corrected").css("display", "block");
		} else {
			$("#corrected_checkWord").html("");
			$("#show_checkWord").html("");
			$("#search_queryString").html(""); 
			
			$("#search_corrected").css("display", "none");
		}
	}
	
	// 设置搜索结果
	function setQueryPage(queryPage, allData, searchParam){
		// 初始化分页插件
		$("#page_pre_a").css("display", "none");
		$("#page_first_a").css("display", "none");
		$("#page_first_a").removeClass("selected");
		$("#page_left_span").css("display", "none");
		$("#page_pre_a_p").css("display", "none");
		$("#page_first_a_p").css("display", "none");
		$("#page_left_span_p").css("display", "none");
		$("#page_1_a").css("display", "none");
		$("#page_1_a").removeClass("selected");
		$("#page_2_a").css("display", "none");
		$("#page_2_a").removeClass("selected");
		$("#page_3_a").css("display", "none");
		$("#page_3_a").removeClass("selected");
		$("#page_4_a").css("display", "none");
		$("#page_4_a").removeClass("selected");
		$("#page_5_a").css("display", "none");
		$("#page_5_a").removeClass("selected");
		$("#page_right_span").css("display", "none");
		$("#page_last_a").css("display", "none");
		$("#page_last_a").removeClass("selected");
		$("#page_next_a").css("display", "none");
		
		if(queryPage == null){
			$("#resultCount").text(0); //设置搜索结果数量
			$("#search_none_span").text(searchParam.queryString);
			$("#search_none_div").css("display", "block");
		} else {
			$("#search_none_div").css("display", "none");//隐藏无结果提示DIV
			$("#resultCount").text(queryPage.totalrows); //设置搜索结果数量
			$("#page_pageno_span_top").text(queryPage.pageno);
			$("#page_total_span_top").text(queryPage.total);
			
			//更新分页控件显示=========================
			// 显示上一页、下一页
			if(queryPage.pageno > 1) {
				$("#page_pre_a").css("display", "block");
				$("#page_pre_a_p").css("display", "block");
			}
			if(queryPage.pageno < queryPage.total) {
				$("#page_next_a").css("display", "block");
			}

			// 没有首页按钮
			if(queryPage.pageno >= 1 && queryPage.pageno <= 3) {
				for(var i=1; i<=5 && i<=queryPage.total; i++) {
					$("#page_" + i + "_a").css("display", "block");
					$("#page_" + i + "_a").text(i);
				}
				$("#page_" + queryPage.pageno + "_a").addClass("selected");
				// 尾页按钮
				if(queryPage.total > 5) {
					$("#page_right_span").css("display", "block");
					$("#page_last_a").css("display", "block");
					$("#page_last_a").text(queryPage.total);
				}
			}

			// 没有尾页按钮
			if((queryPage.pageno + 2) >= queryPage.total && queryPage.total > 5) {
				for(var i=1; i<=5; i++) {
					$("#page_" + i + "_a").css("display", "block");
					$("#page_" + i + "_a").text(queryPage.total - 5 + i);
				}
				$("#page_" + (5 - (queryPage.total - queryPage.pageno)) + "_a").addClass("selected");
				// 首页按钮
				$("#page_left_span").css("display", "block");
				$("#page_left_span_p").css("display", "block");
				$("#page_first_a").css("display", "block");
				$("#page_first_a_p").css("display", "block");
				$("#page_first_a").text(1);
			}

			// 有首页和尾页按钮
			if(queryPage.pageno > 3 && (queryPage.pageno + 2) < queryPage.total && queryPage.total >= 7) {
				// 首页按钮
				$("#page_left_span").css("display", "block");
				$("#page_left_span_p").css("display", "block");
				$("#page_first_a").css("display", "block");
				$("#page_first_a_p").css("display", "block");
				$("#page_first_a").text(1);
				
				for(var i=1; i<=5; i++) {
					$("#page_" + i + "_a").css("display", "block");
					$("#page_" + i + "_a").text(queryPage.pageno - 3 + i);
				}
				$("#page_3_a").addClass("selected");

				// 尾页按钮
				$("#page_right_span").css("display", "block");
				$("#page_last_a").css("display", "block");
				$("#page_last_a").text(queryPage.total);
			} else {
				// 没有首页和尾页按钮
				if(5 >= queryPage.total){
					for(var i=1; i<=queryPage.total; i++) {
						$("#page_" + i + "_a").css("display", "block");
						$("#page_" + i + "_a").text(i);
					}
					$("#page_" + queryPage.pageno + "_a").addClass("selected");
				}
			}
			// 总页数
			$("#page_total_span").text(queryPage.total);
			// 显示当前页数
			$("#page_select_input").val(queryPage.pageno);
			// 显示当前页大小
			$("#page_show_input").val(queryPage.rowsize);

			
			// 设置搜索结果内容
			$("#searchResult > dl").each(function (index, data){
				//console.log(data);
				$(data).remove();
			});
			$("#search_result_personCard > div").each(function (index, data){
				//console.log(data);
				$(data).remove();
			});
			var exsitHisearch = $('#search_m_bodyL').data('hisearch');
			if(exsitHisearch){
				$('#search_box_right').show();
			}
			if($.isArray(queryPage.list)){
				$(queryPage.list).each(function (index, data){
					//console.log(data.lksFieldsMap); 
					// 增加结果，普通结果、知识问答、名片
					if(!exsitHisearch){
						$('#search_box_right').hide();
						$('#search_m_bodyL').css('width', "100%");
					}
					if(data.hasOwnProperty("existPeronName") && "true" == data.existPeronName) {
						// 名片
						addCardResultRow(data.lksFieldsMap, index, allData);
					} else if(data.lksFieldsMap.hasOwnProperty("modelName") 
							&& "com.landray.kmss.kms.ask.model.KmsAskTopic" == data.lksFieldsMap.modelName.value){
						// 知识问答
						addGeneralResultRow(data.lksFieldsMap, index, allData);
					} else {
						// 普通结果
						addGeneralResultRow(data.lksFieldsMap, index, allData);
					}
				});
			} else {
				$("#search_none_span").text(searchParam.queryString);
				$("#search_none_div").css("display", "block");
			}

			if($.isArray(queryPage.list) && queryPage.list.length <= 0) {
				$("#search_none_span").text(searchParam.queryString);
				$("#search_none_div").css("display", "block");
			}
		}
	}
	
	// 增加一条员工名片结果数据
	function addCardResultRow(rowObject, index, allData) {
		var personSearchs = null;
		if(allData.hasOwnProperty("personSearchs")) {
			personSearchs = allData.personSearchs;
		} else { 
			return ;
		}
		$(personSearchs).each(function (i, personSearch){ 
			var modelName = null;
			if(rowObject.hasOwnProperty("modelName")){
				modelName = rowObject.modelName;
			} else {
				return true;
			}
			if(modelName.value != personSearch.module) {
				return true;
			}
			var subject = null;
			if(rowObject.hasOwnProperty("subject")){
				subject = rowObject.subject;
			} else {
				return true;
			}
			var category = null;
			if(rowObject.hasOwnProperty("category")){
				category = rowObject.category;
			}
			var linkStr = null;
			if(rowObject.hasOwnProperty("linkStr")){
				linkStr = rowObject.linkStr;
			}
			var systemName = null;
			if(rowObject.hasOwnProperty("systemName")){
				systemName = rowObject.systemName;
			}
			var docKey = null;
			if(rowObject.hasOwnProperty("docKey")){
				docKey = rowObject.docKey;
			}
			var addField1 = null;//机构
			if(rowObject.hasOwnProperty("addField1")){
				addField1 = rowObject.addField1;
			}
			var addField2 = null;//电话
			if(rowObject.hasOwnProperty("addField2")){
				addField2 = rowObject.addField2;
			}
			var addField3 = null;//部门
			if(rowObject.hasOwnProperty("addField3")){
				addField3 = rowObject.addField3;
			}
			var addField4 = null;//手机
			if(rowObject.hasOwnProperty("addField4")){
				addField4 = rowObject.addField4;
			}
			var addField5 = null;//岗位
			if(rowObject.hasOwnProperty("addField5")){
				addField5 = rowObject.addField5;
			}
			var addField6 = null;//邮箱
			if(rowObject.hasOwnProperty("addField6")){
				addField6 = rowObject.addField6;
			}
			var addField7 = null;//个人资料
			if(rowObject.hasOwnProperty("addField7")){
				addField7 = rowObject.addField7;
			}
			
			var addFieldName1 = null;
			if(personSearch.hasOwnProperty("addFieldName1")){
				addFieldName1 = personSearch.addFieldName1;
			}
			var addFieldName2 = null;
			if(personSearch.hasOwnProperty("addFieldName2")){
				addFieldName2 = personSearch.addFieldName2;
			}
			var addFieldName3 = null;
			if(personSearch.hasOwnProperty("addFieldName3")){
				addFieldName3 = personSearch.addFieldName3;
			}
			var addFieldName4 = null;
			if(personSearch.hasOwnProperty("addFieldName4")){
				addFieldName4 = personSearch.addFieldName4;
			}
			var addFieldName5 = null;
			if(personSearch.hasOwnProperty("addFieldName5")){
				addFieldName5 = personSearch.addFieldName5;
			}
			var addFieldName6 = null;
			if(personSearch.hasOwnProperty("addFieldName6")){
				addFieldName6 = personSearch.addFieldName6;
			}
			var addFieldName7 = null;
			if(personSearch.hasOwnProperty("addFieldName7")){
				addFieldName7 = personSearch.addFieldName7;
			}
			var path = null;
			if(personSearch.hasOwnProperty("path")){
				path = personSearch.path;
			}
			
			//-------------头像
			var div_img = $('<div class="figure" />');
			var docId = "";
			if(null != docKey) {
				var tmp = docKey.value;
				docId = tmp.substring(tmp.lastIndexOf("_") + 1);
			}
			var rid=Math.floor(Math.random() * ( 100 + 1));
			div_img.html('<img src="${ LUI_ContextPath }' + path + '&size=b&fdId=' + docId + '&rid='+rid+'"/><div class="figure_bg"></div>');
			
			//-------------姓名
			var fdModelName = (modelName != null ? modelName.value : "");
			var fdCategory = (category != null ? category.value : "");
			var fdUrl = (linkStr != null ? linkStr.value : "");
			var fdSystemName = (systemName != null ? systemName.value : "");
			var fdDocSubject = (subject != null ? subject.value : "");
			while(fdDocSubject.indexOf("<font>") >= 0) {
				fdDocSubject = fdDocSubject.replace("<font>", "");
			}
			while(fdDocSubject.indexOf("</font>") >= 0) {
				fdDocSubject = fdDocSubject.replace("</font>", "");
			}
			while(fdDocSubject.indexOf("&#39;") >= 0) {
				fdDocSubject = fdDocSubject.replace("&#39;", "");
			}
			while(fdDocSubject.indexOf("'") >= 0) {
				fdDocSubject = fdDocSubject.replace("'", "");
			}
			while(fdDocSubject.indexOf("<font class='synonymHighLight'>") >= 0) {
				fdDocSubject = fdDocSubject.replace("<font class='synonymHighLight'>", "");
			}
			var h6_name = $('<div style="cursor: pointer;width: 108px;font-size: 18px;" class="subject" id="record_'+index+'" />');
			// onclick="readDoc(\'' + fdDocSubject + '\',\'' + fdModelName + '\',\'' + fdCategory + '\',\'' + fdUrl + '\',\'' + fdSystemName + '\',\'' + index + '\');"
			h6_name.html(subject != null ? subject.value : "");
			
			//-------------用户信息
			var table = $('<table/>');
			var tbody = $('<tbody />')
			var tr_1 = $('<tr/>');
			tr_1.html('<td class="title">' + addFieldName1 
					+ ':</td><td class="txt">' + (addField1 != null ? addField1.value : "") 
					+ '</td><td class="title">' + addFieldName2 
					+ ':</td><td class="txt">' + (addField2 != null ? addField2.value : "") + '</td>');
			
			var tr_2 = $('<tr/>');
			tr_2.html('<td class="title">' + addFieldName3 
					+ ':</td><td class="txt">' + (addField3 != null ? addField3.value : "") 
					+ '</td><td class="title">' + addFieldName4 
					+ ':</td><td class="txt">' + (addField4 != null ? addField4.value : "") + '</td>');

			var tr_3 = $('<tr/>');
			tr_3.html('<td class="title">' + addFieldName5 
					+ ':</td><td class="txt">' + (addField5 != null ? addField5.value : "") 
					+ '</td><td class="title">' + addFieldName6 
					+ ':</td><td class="txt">' + (addField6 != null ? addField6.value : "") + '</td>');
			
			var tr_4 = $('<tr/>');
			tr_4.html('<td class="title">' + addFieldName7 
					+ ':</td><td class="txt" colspan="3">' + (addField7 != null ? addField7.value : "") 
					+ '</td>');
			
			tbody.append(tr_1);
			tbody.append(tr_2);
			tbody.append(tr_3);
			tbody.append(tr_4);
			table.append(tbody);

			//--------------------------
			var div_card = $('<div class="search_view_personCard" />');
			div_card.append(div_img);
			div_card.append(h6_name);
			div_card.append(table);

			$("#search_result_personCard").append(div_card);
			<%if(relevancePerson){%>
				addPerson(docId);
			<%
				}
			%>
			$("#record_"+index).unbind("click").bind("click",function(){readDoc(fdDocSubject,fdModelName,fdCategory,fdUrl,fdSystemName,index);});
		});
	}
	
	

	// 增加相关的人
	function addPerson(orgId) {
		// 请求数据
		var params = {};
		params.method= "getTeam";
		params.type= "team";
		params.orgId = orgId;
		$.ajax({
			url : '${KMSS_Parameter_ContextPath}sys/zone/sys_zone_personInfo/sysZonePersonInfo.do',
			data : params,
			cache : false,
			async : true,
			type : "GET",
			dataType : 'json',
			success : function (data) {
				var exsitHisearch = $('#search_m_bodyL').data('hisearch');
				if ($.isArray(data)) {
					if(data.length > 0){
						if(!exsitHisearch){
							$('#search_box_right').show();
							$('#search_m_bodyL').css('width', "70%");
						}
					}
					$("#search_m_bodyR").css("display", "block");
					$("#search_m_bodyL").removeClass("search_l_bodyLwidth").addClass('search_m_bodyLwidth');
					$(data).each(function (index, person) {
						if (index >= 6) {
							return false;
						}
						var imgUrl = person.imgUrl;
						if(imgUrl.indexOf("http")!=0){
							if(imgUrl.indexOf("/")==0){
								imgUrl = imgUrl.substring(1);
							}
							imgUrl = '${KMSS_Parameter_ContextPath}'+imgUrl;
						}
						var li = $("<li/>");
						li.html('<a href="${KMSS_Parameter_ContextPath}sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=view&fdId=' + person.fdId
							 + '" target="_blank"><div class="figure"><img src="' + imgUrl + '" /><div class="figure_bg"></div></div><p>'
							 + person.fdName + '</p></a>');
						$("#relevance_person").append(li);
					});
				}else{
					if(!exsitHisearch){
						$('#search_box_right').hide();
						$('#search_m_bodyL').css('width', "100%");
					}
					$("#search_m_bodyR").css("display", "none");
					$("#search_m_bodyL").removeClass("search_m_bodyLwidth").addClass('search_l_bodyLwidth');
				}
			},
			beforeSend : function (request) {
				$("#search_m_bodyR").css("display", "none");
				$("#search_m_bodyL").removeClass("search_m_bodyLwidth").addClass('search_l_bodyLwidth');
				$("#relevance_person_wait").css("display", "block");
			},
			complete : function (request, textStatus) {
				$("#relevance_person_wait").css("display", "none");
			},
			error : function (request, textStatus, errorThrown) {
				$("#search_m_bodyR").css("display", "none");
				$("#search_m_bodyL").removeClass("search_m_bodyLwidth").addClass('search_l_bodyLwidth');
				$("#relevance_person_wait").css("display", "none");
			}
		});
	}
	
	// 增加一条普通的搜索结果数据
	function addGeneralResultRow(rowObject, index, allData){
		var linkStr = null;
		if(rowObject.hasOwnProperty("linkStr")){
			linkStr = rowObject.linkStr;
		}
		var docStatus = null;
		if(rowObject.hasOwnProperty("docStatus")){
			docStatus = rowObject.docStatus;
		}
		var title = null;
		var subject = null;
		if(rowObject.hasOwnProperty("subject")){
			subject = rowObject.subject;
		}
		var content = null;
		if(rowObject.hasOwnProperty("content")){
			content = rowObject.content;
		}
		var fileName = null;
		if(rowObject.hasOwnProperty("fileName")){
			fileName = rowObject.fileName;
		}
		var ekpDigest = null;
		if(rowObject.hasOwnProperty("ekpDigest")){
			ekpDigest = rowObject.ekpDigest;
		}
		var juniorSummary = null;
		var docKey = null;
		if(rowObject.hasOwnProperty("docKey")){
			docKey = rowObject.docKey;
		}
		var mimeType = null;
		var xmlcontent = null;
		if(rowObject.hasOwnProperty("xmlcontent")){
			xmlcontent = rowObject.xmlcontent;
		}
		var addField1 = null;
		var addField2 = null;
		var addField3 = null;
		var addField4 = null;
		var addField5 = null;
		var addField6 = null;
		var addField7 = null;
		var docReadCount = null;
		if(rowObject.hasOwnProperty("docReadCount")){
			docReadCount = rowObject.docReadCount;
		}
		var kmsAskPostList = null;
		var kmsAskPostListIDs = null;
		var keyword = null;
		if(rowObject.hasOwnProperty("keyword")){
			keyword = rowObject.keyword;
		}
		var modelName = null;
		if(rowObject.hasOwnProperty("modelName")){
			modelName = rowObject.modelName;
		}
		var modelName2 = null;
		if(rowObject.hasOwnProperty("modelName2")){
			modelName2 = explainNameStr(rowObject.modelName2);
		}
		var category = null;
		if(rowObject.hasOwnProperty("category")){
			category = rowObject.category;
		}
		var creator = null;
		if(rowObject.hasOwnProperty("creator")){
			creator = rowObject.creator;
		}
		var createTime = null;
		if(rowObject.hasOwnProperty("createTime")){
			createTime = rowObject.createTime;
		}
		var systemName = null;
		if(rowObject.hasOwnProperty("systemName")){
			systemName = rowObject.systemName;
		}
		var fullText = null;
		if(rowObject.hasOwnProperty("fullText")){
			fullText = rowObject.fullText;
		}
		var kmsAskPostList = null;
		if(rowObject.hasOwnProperty("kmsAskPostList")){
			kmsAskPostList = rowObject.kmsAskPostList;
		}
		var kmsAskPostListIDs = null;
		if(rowObject.hasOwnProperty("kmsAskPostListIDs")){
			kmsAskPostListIDs = rowObject.kmsAskPostListIDs;
		}

		if(rowObject.hasOwnProperty("addField1")){
			addField1 = rowObject.addField1;
		}
		if(rowObject.hasOwnProperty("addField2")){
			addField2 = rowObject.addField2;
		}
		if(rowObject.hasOwnProperty("addField3")){
			addField3 = rowObject.addField3;
		}
		if(rowObject.hasOwnProperty("addField4")){
			addField4 = rowObject.addField4;
		}
		if(rowObject.hasOwnProperty("addField5")){
			addField5 = rowObject.addField5;
		}
		if(rowObject.hasOwnProperty("addField6")){
			addField6 = rowObject.addField6;
		}
		if(rowObject.hasOwnProperty("addField7")){
			addField7 = rowObject.addField7;
		}

 		// 文档状态
		var row_1_1 = '<span></span>';
		if(docStatus!=null){
		 	if(docStatus.value =='10'){
		 		row_1_1 = '<span class="lui_icon_s lui_icon_s_icon_draft icon" title="${lfn:message('status.draft')}"></span>&nbsp;';
		 	}else if(docStatus.value =='00'){
		 		row_1_1 = '<span class="lui_icon_s lui_icon_s_icon_discard icon" title="${lfn:message('status.discard')}"></span>&nbsp;';
		 	}else if(docStatus.value =='11'){
		 		row_1_1 = '<span class="lui_icon_s lui_icon_s_icon_refuse icon" title="${lfn:message('status.refuse')}"></span>&nbsp;';
		 	}else if(docStatus.value =='40'){
		 		row_1_1 = '<span class="lui_icon_s lui_icon_s_icon_expire icon" title="${lfn:message('status.expire')}"></span>&nbsp;';
		 	}else if(docStatus.value =='20'){
		 		row_1_1 = '<span class="lui_icon_s lui_icon_s_icon_examine icon" title="${lfn:message('status.examine')}"></span>&nbsp;';
		 	}
		}
	/*	var row_1_1 = $('<span class="icon_collect"/>');
		row_1_1.bind("click", function () {
            $(this).toggleClass("on");
        });*/

		//-------------搜索结果标题
		var row_1_2 = $('<a href="javascript:void(0)" />');
		//row_1_2.attr("title",rowObject.subject);
		var fileIcon = null;
		if(null != fileName) {
			fileIcon = getIconNameByFileName(fileName.value);
			var row_1_2_1 = $('<img src="styles/images/'+fileIcon+'" height="16" width="16" border="0" />');
			row_1_2.append(row_1_2_1);
		}
		var fdModelName = (modelName != null ? modelName.value : "");
		var fdCategory = (category != null ? category.value : "");
		var mainDocLink = (linkStr != null ? linkStr.value : "");
		var fdUrl = (linkStr != null ? linkStr.value : "");
        if(null != fileName && docKey != null) {
            var tmp = docKey.value;
            var startHttp = false;
            if(fdUrl.length > 4 && fdUrl.substring(0, 4) == "http"){
            	startHttp = true;
            	if(rowObject.hasOwnProperty("mainDocLinkStr")){
            		mainDocLink = rowObject.mainDocLinkStr;
        		}else{
        			mainDocLink = null;
        		}
            }
            if(!startHttp){
	            tmp = tmp.substring(tmp.lastIndexOf("_") + 1);
	            fdUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + tmp;
	            if("image.png" == fileIcon || "txt.gif" == fileIcon || "htm.gif" == fileIcon){
	            	fdUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" + tmp +"&open=1";
	            }
            }
        }
		var fdSystemName = (systemName != null ? systemName.value : "");
		var fdDocSubject = (subject != null ? subject.value : (fileName != null ? fileName.value : ""));
		while(fdDocSubject.indexOf("<font>") >= 0) {
			fdDocSubject = fdDocSubject.replace("<font>", "");
		}
		while(fdDocSubject.indexOf("</font>") >= 0) {
			fdDocSubject = fdDocSubject.replace("</font>", "");
		}
		while(fdDocSubject.indexOf("&#39;") >= 0) {
			fdDocSubject = fdDocSubject.replace("&#39;", "");
		}
		while(fdDocSubject.indexOf("'") >= 0) {
			fdDocSubject = fdDocSubject.replace("'", "");
		}
		while(fdDocSubject.indexOf("<font class='synonymHighLight'>") >= 0) {
			fdDocSubject = fdDocSubject.replace("<font class='synonymHighLight'>", "");
		}
		if(null != fileName) {
			row_1_2.append('&nbsp;');
		}
		var row_1_2_2 = $('<span style="font-size: 18px;" id="record_'+index+'" />');
		row_1_2_2.html(subject != null ? subject.value : (fileName != null ? fileName.value : ""));
		row_1_2.append(row_1_2_2);

		if(null != fileName && mainDocLink != null) {
			var row_1_3 = $('<a class="view_doc" href="javascript:void(0)" id="record_main_'+index+'" />');
			row_1_3.text("${lfn:message('sys-ftsearch-db:search.ftsearch.viewMainDoc')}");// 查看主文档
		}
		var dt = $('<dt/>');
		dt.append(row_1_1);
		dt.append(row_1_2);
		if(null != fileName) {
			dt.append(row_1_3);
		}

		//-------------搜索结果内容
		var row_2_1 = null;
		var fdSummary = "";
		if(null == kmsAskPostList || null == kmsAskPostListIDs) {
			row_2_1 = $('<span />');
			fdSummary = (content != null ? content.value : "");
			fdSummary = fdSummary + " " + (xmlcontent != null ? xmlcontent.value : "");
			fdSummary = fdSummary + " " + (ekpDigest != null ? ekpDigest.value : "");
			fdSummary = fdSummary + " " + (fullText != null ? fullText.value : "");
			fdSummary = fdSummary + " " + (addField1 != null ? addField1.value : "");
			fdSummary = fdSummary + " " + (addField2 != null ? addField2.value : "");
			fdSummary = fdSummary + " " + (addField3 != null ? addField3.value : "");
			fdSummary = fdSummary + " " + (addField4 != null ? addField4.value : "");
			fdSummary = fdSummary + " " + (addField5 != null ? addField5.value : "");
			fdSummary = fdSummary + " " + (addField6 != null ? addField6.value : "");
			fdSummary = fdSummary + " " + (addField7 != null ? addField7.value : "");
			row_2_1.html(fdSummary);
		} else {
			// 爱问答案 
			var kmsAskPostArray = kmsAskPostList.value.split("\u001a");
			var kmsAskPostArrayID = kmsAskPostListIDs.value.split("\u001a");
			row_2_1 = $('<table border="0" style="margin-left:15px" />');
			var tbody = $('<tbody>');
			$(kmsAskPostArray).each(function (i, kmsAskPost){
				if(i > 3 || kmsAskPostArrayID.length <= i) {
					return false;
				}
				if("" == $.trim(kmsAskPost)) {
					return true;
				}
				var tmp_tr_td = $('<tr></tr>');
				// 回答 
				tmp_tr_td.html('<td width="50px" align="left" valign="top" style="color: #666;">${lfn:message("sys-ftsearch-db:search.search.Answer")}</td>' 
					+ '<td align="left" valign="top">' 
					+ '<a class="askPos" href="javascript:void(0)" id="record_askPos_'+index+'_'+i+'">' 
					// onclick="readDoc(\'' + fdDocSubject + '\',\'' + fdModelName + '\',\'' + fdCategory + '\',\'' + (fdUrl + "&answerId=" + kmsAskPostArrayID[i]) + '\',\'' + fdSystemName + '\',\'' + index + '\');"
					+ kmsAskPost 
					+ '</a></td>');
				tbody.append(tmp_tr_td);
				tmp_tr_td.find("#record_askPos_"+index+"_"+i).unbind("click").bind("click",function(){readDoc(fdDocSubject,fdModelName,fdCategory,fdUrl + "&answerId=" + kmsAskPostArrayID[i],fdSystemName,index);});

			});
			row_2_1.append(tbody);
		}
		var ddContent = $('<dd class="content"/>');
		ddContent.append(row_2_1);
		
		//-------------搜索结果其他信息 
		var fullModelName = modelName.value;
		if(rowObject.hasOwnProperty("outSystem")){
			fullModelName = rowObject.outSystem+"@"+fullModelName;
		}
		
		var modelUrl = "javascript:void(0)";
		if(rowObject.hasOwnProperty("modelUrl")
				&&allData.hasOwnProperty("getContextPath")){
			modelUrl = rowObject.modelUrl.value;
			if(modelUrl.indexOf("/") == 0) {
				modelUrl = allData.getContextPath + modelUrl;
			} else if(modelUrl.indexOf("http") != 0){
				modelUrl = allData.getContextPath + "/" + modelUrl;
			}
		}
		/*if(allData.hasOwnProperty("modelUrlMap") 
				&& modelName != null 
				&& allData.modelUrlMap.hasOwnProperty(fullModelName)
				&& allData.hasOwnProperty("getContextPath")) {
			modelUrl = allData.modelUrlMap[fullModelName];
			if(modelUrl.indexOf("/") == 0) {
				modelUrl = allData.getContextPath + modelUrl;
			} else if(modelUrl.indexOf("http") != 0){
				modelUrl = allData.getContextPath + "/" + modelUrl;
			}
		}*/
		
		var row_3_1 = $('<span class="first"/>');
		// 知识目录：
		row_3_1.html('${lfn:message("sys-ftsearch-db:search.ftsearch.knowledge.directory")}<a href="' + modelUrl + '" target="_blank" >' + (modelName2 != null ? modelName2 : "") + '</a>');
		var row_3_2 = $('<span/>');
		//作者：
		row_3_2.html('${lfn:message("sys-ftsearch-db:sysFtsearch.simdoc.author")}' + (creator != null ? creator.value : ""));
		var row_3_3 = $('<span/>');
		//创建时间：
		row_3_3.html("${lfn:message('sys-ftsearch-db:sysFtsearch.simdoc.createTime')}" + (createTime != null ? createTime.value : ""));
		if(null != docReadCount) {
			var row_3_4 = $('<span/>');
			// 阅读数：
			row_3_4.html("${lfn:message('sys-ftsearch-db:search.search.docReadCount')}" + (docReadCount != null ? docReadCount.value : ""));
		}
		if(null != keyword) {
			var row_3_5 = $('<span/>');
			// 标签：
			row_3_5.html("${lfn:message('sys-ftsearch-db:search.search.tags')}" + (keyword != null ? keyword.value : ""));
		}
		// 快照
		if($.trim(fdSummary) != "") {
			var snapshotUrl = "${KMSS_Parameter_ContextPath}sys/ftsearch/searchBuilder.do?method=getSnapshot&docKey=";
			if(docKey != null) {
				snapshotUrl += encodeURIComponent(docKey.value);
			}
			if(null != content){
				snapshotUrl += "&field=content";
			} else if(null != xmlcontent){
				snapshotUrl += "&field=xmlcontent";
			} else if(null != ekpDigest){
				snapshotUrl += "&field=ekpDigest";
			} else if(null != fullText) {
				snapshotUrl += "&field=fullText";
			}
			snapshotUrl += "&queryString=" + encodeURIComponent(searchWordSubstring($("#topKeyword").val()));
			snapshotUrl += "&fdDocSubject=" + encodeURIComponent(fdDocSubject);
			var row_3_6 = $('<span/>');
			row_3_6.html("<a class='snapshot' href='" + snapshotUrl + "' target='_blank'>${lfn:message('sys-ftsearch-db:search.ftsearch.lookSnapshot')}>></a>");
		}
		
		var ddInfo = $('<dd class="foot_info"/>');
		ddInfo.append(row_3_1);
		ddInfo.append(row_3_2);
		ddInfo.append(row_3_3);
		if(null != docReadCount) {
			ddInfo.append(row_3_4);
		}
		if(null != keyword) {
			ddInfo.append(row_3_5);
		}
		ddInfo.append(row_3_6);

		//-------------组合搜索结果：标题、内容、其他信息
		var dl = $('<dl class="lui_common_view_summary_content_box"/>');
		dl.append(dt);
		dl.append(ddContent);
		dl.append(ddInfo);
		
		$("#searchResult").append(dl);
		$("#record_"+index).unbind("click").bind("click",function(){readDoc(fdDocSubject,fdModelName,fdCategory,fdUrl,fdSystemName,index);});
		$("#record_main_"+index).unbind("click").bind("click",function(){readDoc(fdDocSubject,fdModelName,fdCategory,mainDocLink,fdSystemName,index);});
	}
 
	// 设置搜索范围树
	function setTreeCategory(treeCategory, allData) {
		if(true == isClickTreeCategorySearch) {
			isClickTreeCategorySearch = false;
			return ;
		}
		var hasSelected = false;
		if("" != $.trim(treeCategory)) {
			treeCategoryObject = $.parseJSON(treeCategory);
			// 设置选择的节点
			if(allData.hasOwnProperty("category") && null != allData.category) {
				var categoryArray = allData.category.split(",");
				$(categoryArray).each(function (index, categoryId) {
					if("" != categoryId) {
						var category = getCategoryById(categoryId);
						if(null != category) {
							category.select = true;
							hasSelected = true;
						}
					}
				});
			}
		} else {
			treeCategoryObject = null;
		}
		
		if(null == treeCategoryObject){
			// 删除之前节点
			$("#entries_design_top > a").each(function (index, element){
				if($(element).attr("id")!="catagoryAll"){
					$(element).remove();
				}
			});
			$("#catagoryAll").removeClass("item1");
			$("#catagoryAll").removeClass("item2");
			$("#catagoryAll").addClass("item2");
		} else {
			$("#catagoryAll").removeClass("item1");
			$("#catagoryAll").removeClass("item2");
			if(hasSelected){
				$("#catagoryAll").addClass("item1");
			}else{
				$("#catagoryAll").addClass("item2");
			}
			setEntriesDesignTop(null, getChildCategory(null));
		}
	}

	// 设置搜索范围树元素
	function setEntriesDesignTop(pCategory ,categoryArray) {
		// 删除之前节点
		$("#entries_design_top > a").each(function (index, element){
			if($(element).attr("id")!="catagoryAll"){
				$(element).remove();
			}
		});
		
		
		// 增加父节点
		if(null != pCategory && undefined != pCategory) {
			var select = false;
			if(pCategory.hasOwnProperty("select")) {
				select = pCategory.select;
			}
			var a = $("<a style='margin-left: 0px;' class='item1' id='" + pCategory.mapkey + "' />");
			a.html('<span onclick="categoryClickByParent(true);" >' 
					+ pCategory.mapName + '(' + pCategory.mapCount + ')' + '</span><span class="categoryNodeParent" onclick="categoryClickByParent(false);" />');
			$("#entries_design_top").append(a);
		}
		// 增加子节点
		if($.isArray(categoryArray)) 
			$(categoryArray).each(function (index, object) {
				var select = false;
				if(object.hasOwnProperty("select")) {
					select = object.select;
				}
			    var a = $("<a class='" + (true == select ? "item2" : "item1") + "' id='" + object.mapkey + "' />");
				a.html('<span class="checkbox' + (true == select ? ' checked' : '') + '" onclick="categoryClick();" ></span><span onclick="categoryClick();" >' 
						+ object.mapName + '(' + object.mapCount + ')' + '</span><span class="categoryNode" onclick="categoryClickByChild();" />');
				$("#entries_design_top").append(a);
			});
		}
	
	
	
	//此方法用来判断多语言来截取字符串
	function explainStr(object){
		//判断是否包含有空格
		var index=object.mapName.indexOf("\u001A");
		if(index>-1){
			var mapAttr=object.mapName.split("\u001A");
			var mapName="";
			//如果符合当条件就拿当的语言
			for(var i=0;i<mapAttr.length;i++){
				var strAttr=mapAttr[i].split("-");
				if(strAttr[0].toUpperCase()==lang.toUpperCase()){
					mapName=strAttr[1];
					object.mapName=mapName;
				}
			} 
			
			//如果等于空就拿官方语言
		    if(mapName=="" || mapName=="null"){
		    	var otherName="";
		    	for(var i=0;i<mapAttr.length;i++){
					var strAttr=mapAttr[i].split("-");
					if(officialLang != "null" && officialLang != "" && strAttr[0] != "" && officialLang==strAttr[0]){
						otherName=strAttr[1];
						object.mapName=otherName;
					}
				}
		    	//就拿默认的
		        if(otherName=="" ||  mapName=="null"){
		        	for(var i=0;i<mapAttr.length;i++){
						var strAttr=mapAttr[i].split("-");
						if(strAttr[0]=="me"){
			        		   otherName=strAttr[1];
			        		   object.mapName=otherName;
			        	 }
					}
		        }
		    }
		}
	    return object;
	}
	
	
   
	function explainNameStr(value){
		//判断是否包含有空格
		var index=value.indexOf("\u001A");
		if(index>-1){
			var mapAttr=value.split("\u001A");
			var mapName="";
			//如果符合当条件就拿当的语言
			for(var i=0;i<mapAttr.length;i++){
				var strAttr=mapAttr[i].split("-");
				if(strAttr[0].toUpperCase()==lang.toUpperCase()){
					mapName=strAttr[1];
				}
			}
			
			
			//如果等于空就拿官方语言
		    if(mapName=="" || mapName=="null"){
		    	var otherName="";
		    	for(var i=0;i<mapAttr.length;i++){
					var strAttr=mapAttr[i].split("-");
					if(officialLang != "null" && officialLang != "" && strAttr[0] != "" && officialLang==strAttr[0]){
						otherName=strAttr[1];
						mapName=otherName;
					}
				}
		    	
		    	//就拿默认的
		        if(otherName=="" ||  mapName=="null"){
		        	for(var i=0;i<mapAttr.length;i++){
						var strAttr=mapAttr[i].split("-");
						if(strAttr[0]=="me"){
			        		   otherName=strAttr[1];
			        		   mapName=otherName;
			        	}
					}
		        }
		    }
		    return mapName;
		}else{
			return value;
		}
	    
	}

	function clickCatagoryAll(){
		//若有限制模块范围，例如搜索本模块或者下方勾选其他模块范围，还原模块为默认范围
		   var modelflag = true;  //是否有限定模块
		   var cateflag = true;   //是否有做分类筛选
		   //此样式代表没有做过分类筛选
		   if($("#catagoryAll").attr("class") == "item2"){		
			   cateflag = false;
			}
			commitSearch(function (params) {
					if(params.modelName && params.modelName.length>0){
						delete params.modelName
					}else{
						modelflag = false;
					}
					if(cateflag){
						delete params.category;
					}
				//若没改变模块范围，并且没做分类筛选，则点击无效果
				if(!cateflag && !modelflag){
					return false;
				}
				params.pageno = 1;
				return true;
			});
	}

	function clickStatusAll(){
		if($("#doc_status_").attr("class") == "item2"){
			return;
		}
		commitSearch(function (params) {
			delete params.docStatus;
			params.pageno = 1;
			return true;
		});
	}

	function clickFieldsAll(){
		if($("#search_field_").attr("class") == "item2"){
			return;
		}

		$("#search_fields > a").each(function (index, element){
			$(element).children(".checkbox").removeClass("checked");
			$(element).removeClass("item1");
			$(element).removeClass("item2");
			$(element).addClass("item1");
		});
		$(this).removeClass("item1");
    	$(this).removeClass("item2");
    	$(this).addClass("item2");
    	$("#doc_file_type").multipleSelect("setSelects", [""]);
    	
		commitSearch(function (params) {
			delete params.searchFields;
			delete params.docFileType;
			params.pageno = 1;
			return true;
		});
	}

	// 搜索范围树元素--子节点选中效果
	function categoryClick() {
	    //var e = e || this.event;
	    var e = this.event || arguments.callee.caller.arguments[0];
	    var obj = e.target || e.srcElement;
	    var parent = $(obj).parent();
	    $(parent).toggleClass("item1");
	    $(parent).toggleClass("item2");
	    $(parent).children(".checkbox").toggleClass("checked");

	    var category = getCategoryById($(parent).attr("id"));
	    if(null == category) {
		    return ;
	    }
	    if("item2" == $(parent).attr("class")) {
	    	category.select = true;
	    } else {
	    	category.select = false;
	    }
	    isClickTreeCategorySearch = true;
	    commitSearchByModelFilter();
	}
	
	// 搜索范围树元素 -- 进入子节点
	function categoryClickByChild() {
	    //var e = e || this.event;
	    var e = this.event || arguments.callee.caller.arguments[0];
	    var obj = e.target || e.srcElement;
	    var parent = $(obj).parent();
	    var id = $(parent).attr("id");
	    clearCategoryStat();
	    setEntriesDesignTop(getCategoryById(id), getChildCategory(id));
	}

	// 搜索范围树元素 -- 返回父节点
	function categoryClickByParent(isSearch) {
	    //var e = e || this.event;
	    var e = this.event || arguments.callee.caller.arguments[0];
	    var obj = e.target || e.srcElement;
	    var parent = $(obj).parent();
	    var id = $(parent).attr("id");
	    var current = getCategoryById(id);
	    clearCategoryStat();
	    if(true == isSearch) {
	    	current.select = true;
	    }
		if(current.hasOwnProperty("mapPid")) {
			setEntriesDesignTop(getCategoryById(current.mapPid), getChildCategory(current.mapPid));
		} else  {
			setEntriesDesignTop(null, getChildCategory(null));
		}
	    if(true == isSearch) {
		    isClickTreeCategorySearch = true;
		    commitSearchByModelFilter();
	    }
	}

	// 设置附件类型
	function setDocFileType(docFileType) {
		if(null != docFileType) {
            var allTypes=new Array("pdf","doc;docx","xls;xlsx","ppt;pptx","txt","exe");
            var selectTypes=new Array();
            var count=0;
            $.each(allTypes, function(i,type){      
                if(docFileType.indexOf(type) >= 0 )  
                {
                    selectTypes[count] = type;
                    count++;
                }
            });
            if(docFileType!=""){
                $("#doc_file_type").multipleSelect("setSelects", selectTypes);
            } else {
                $("#doc_file_type").multipleSelect("setSelects", [""]);
            }
		}
	}

	// 设置文档状态
	function setDocStatus(docStatus) {
		$("#doc_status_").removeClass("item1");
    	$("#doc_status_").removeClass("item2");
		$("#doc_status_top > a").each(function (index, element){
			$(element).removeClass("item1");
			$(element).removeClass("item2");
			if($(element).attr("id")!="doc_status_"){
				$(element).children(".checkbox").removeClass("checked");
				$(element).addClass("item1");
			}
		});
		
		if(null != docStatus&&docStatus!="") {
			$("#doc_status_").addClass("item1");
			 var selectedStatus = docStatus.split(";");
			 $(selectedStatus).each(function (index, status){
					$("#doc_status_top > a").each(function (index, element){
						if ($(element).attr("id") == "doc_status_" + status) {
							$(element).children(".checkbox").removeClass("checked");
							$(element).removeClass("item1");
							$(element).removeClass("item2");
							
							$(element).children(".checkbox").addClass("checked");
							$(element).addClass("item2");
						}
					});
				});
        }else{
        	$("#doc_status_").addClass("item2");
        }
	}
	
	// 初始化操作
seajs.use(['lui/jquery'],function($){
	window.jQuery = $;
	seajs.use(['sys/ftsearch/js/jquery-ui/jquery.ui.js',
	           'sys/ftsearch/js/jquery.multiple.select.js',
	           'sys/ftsearch/js/jquery-ui/src/jquery.ui.core.js',
	           'sys/ftsearch/js/jquery-ui/src/jquery.ui.datepicker.js'	           
	           ],
	  function(){
		$(document).ready(function() {
			//SyntaxHighlighter.config.clipboardSwf = '${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/clipboard.swf';
			//SyntaxHighlighter.all();
			//console.log("Jquery版本:" + $.fn.jquery);
			 
			// 搜索域
		    /*$(".search_view_listbar_dropbox >li a").bind("click", function () {
		        $(this).find("span.checkbox").toggleClass("checked");
		    });*/ 
		    $("#search_fields > a").bind("click", function () {
			    
			    $(this).toggleClass("item1");
			    $(this).toggleClass("item2");
		    	$(this).children(".checkbox").toggleClass("checked");
			   

				if ("item2" == $("#search_field_attachment").attr("class")) {
					//启用
					$('#doc_file_type').multipleSelect('enable');
				} else {
					//禁用
					$('#doc_file_type').multipleSelect('disable');
				}
				
		    	commitSearchByFields();
		    });
		    $( "#startTime" ).datepicker({
			       defaultDate: "+1w",
			          changeMonth: true,
			          numberOfMonths: 1,
			          onSelect: function(dateText, inst) {
			        	  commitSearchByDayStart(dateText);
			        	  }
			       
			    });

			  $( "#endTime" ).datepicker({
			       defaultDate: "+1w",
			          changeMonth: true,
			          numberOfMonths: 1,
			          onSelect: function(dateText, inst) {
			        	  commitSearchByDayEnd(dateText);
			        	  }
			    });
		    // 附件类型下拉列表
			$("#doc_file_type").multipleSelect({
				placeholder : "${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType')}",
				selectAll : false,
				selectAllText : "${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.all')}",
				allSelected : "${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.all')}",
				keepOpen : false,
				minimumCountSelected : 2,
				countSelected : "${lfn:message('sys-ftsearch-db:search.ftsearch.select2')}",//已选择#项
				onClick : function (view) {
					// console.log(view);
	                if( (view.value==null || view.value=="") && view.checked ){
	                    //选择 所有格式 时清除其他选项
	                    $("#doc_file_type").multipleSelect("setSelects", [""]);
	                } else if(view.checked) {
	                    // 未点击 所有格式 ，但选择了其他类型 清除 所有格式
	                    var selects=$("#doc_file_type").multipleSelect("getSelects");
	                    selects.push(view.value);
	                    selects.splice($.inArray("",selects),1);
	                    $("#doc_file_type").multipleSelect("setSelects", selects);
	                }
	                if( (view.value==null || view.value=="") && !view.checked ){
	                    //不允许取消 所有格式 
	                    $("#doc_file_type").multipleSelect("setSelects", [""]);
	                }
	                if(view.value!="" && !view.checked){
	                    var selects=$("#doc_file_type").multipleSelect("getSelects");
	                    if(selects.length <= 0){
	                        $("#doc_file_type").multipleSelect("setSelects", [""]);
	                    }
	                }
					
					var typeArray = $("#doc_file_type").multipleSelect("getSelects");
				    var docFileType="";
				    $.each(typeArray, function(i,val){
				        if(val!=""){
				            docFileType += val + ";";
				        }
				    }); 
				    // console.log(typeArray);
				    // console.log(docFileType);
				    //判断搜索范围是否有选择,如果有择刷新 搜索范围,没有则刷新
				    if($("#entries_design_top > a").hasClass("item2")){
				    	commitSearch1(function (params) {
							params.docFileType = docFileType;
							if("" == docFileType) {
								delete params.docFileType;
							}

							var outKeyword = $("#out_keyword").val();
							params.outKeyword = outKeyword;
							params.pageno = 1;
							// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
							/*if(params.hasOwnProperty("category")) {
							    delete params.category;
							}*/
							return true;
						});
				    }else{
				    	commitSearch(function (params) {
							params.docFileType = docFileType;
							if("" == docFileType) {
								delete params.docFileType;
							}

							var outKeyword = $("#out_keyword").val();
							params.outKeyword = outKeyword;
							params.pageno = 1;
							// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
							/*if(params.hasOwnProperty("category")) {
							    delete params.category;
							}*/
							return true;
						});
				    }
					
				}
			});
			$('#doc_file_type').multipleSelect('disable');

			// 文档状态  
			 $("#doc_status_top > a").bind("click", function () {
				 if($(this).attr("id")!="doc_status_"){
					$(this).toggleClass("item1");
				    $(this).toggleClass("item2");
				 }else{
					if($.trim($(this).attr("class"))=="item2"){
						return;
					}
				 }

			    var selectedStatus = "";
			    
				if($(this).attr("id")!="doc_status_"){
		    		$(this).children(".checkbox").toggleClass("checked");
				
			    	$("#doc_status_top > a").each(function (index, element){
				    	var clazz = $(element).attr("class");
						var id = $(element).attr("id");
						if("item2" == $.trim(clazz) && null != id){
							var status = id.replace("doc_status_","");
							if("" == $.trim(status)) {
								return true;
							}
							if("" == selectedStatus) {
								selectedStatus = status
							} else {
								selectedStatus = selectedStatus + ";" + status
							}
						}
			    	 });
				}
			    // console.log(typeArray);
			    // console.log(docFileType);
			    
			   //判断搜索范围是否有选择,如果有择刷新 搜索范围,没有则刷新
			   if($("#entries_design_top > a").hasClass("item2")==true){
				   commitSearch1(function (params) {
						params.docStatus = selectedStatus;
						if("" == selectedStatus) {
							delete params.docStatus;
						}

						var outKeyword = $("#out_keyword").val();
						params.outKeyword = outKeyword;
						params.pageno = 1;
						// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
					    if(params.hasOwnProperty("category")) {
						    delete params.category;
						} 
						return true;
					});
			    }else{
			    	commitSearch(function (params) {
						params.docStatus = selectedStatus;
						if("" == selectedStatus) {
							delete params.docStatus;
						}

						var outKeyword = $("#out_keyword").val();
						params.outKeyword = outKeyword;
						params.pageno = 1;
						// 搜索结果发生变化 发起的请求 就删除 搜索范围 过滤参数
					    /*if(params.hasOwnProperty("category")) {
						    delete params.category;
						}*/ 
						return true;
					});
			    	
			    }
				
		    });

			
			$("#search_fields .ms-parent").css("float","left");
			$("#search_fields .ms-parent").css("margin-right", "22px");
			$("#search_fields .ms-parent").css("margin-top", "0px");

		    // 初始化自动补全
			iniAutoComlete();
		    
		    // 首次加载
		    commitSearch(function (params) {
				return true;
			});
			
			$(".lui_portal_footer").show();
			//海乂知知识原图加载
			<c:if test="${existHisearch}">
			var params = getUrlParam();
			var hisearchGraphUrl = '${hisearchGraphUrl}'+params.queryString;
			$('#hisearch_yuntu').attr('src',hisearchGraphUrl)
			</c:if>
		});
		})
	});
	
function openByUrl(url){
	window.open("${LUI_ContextPath}/"+url, "_blank");
}
	
	var dateStart;
	var dateEnd;
	var useTime;
	var searchStringAll;
	var searchStringPeople;
	var searchStringModel;
	LUI.ready(function() {
	searchStringAll=GetUrlParameter(location.search + location.hash,"searchAll");
	searchStringPeople=GetUrlParameter(location.search + location.hash,"searchPeople");
	searchStringModel=GetUrlParameter(location.search + location.hash,"searchModel");
	if(searchStringAll){
		front="searchAll";
		searchId="searchAll";
	}else if(searchStringPeople){
		var fdSearchName=GetQueryString("fdSearchName");
		$("#topKeyword").val(fdSearchName);
		front="searchPeople";
		searchId="searchPeople";
	}else if(searchStringModel){
		getModel();
		var query=GetQueryString("query");
		$("#topKeyword").val(query);
		front="searchModel";
		searchId="searchModel";
	}else{
		$("searchAll").addClass("current");
	}
	buildModels();
		
	}); 
	
	function buildModels(){
		
		if(searchStringModel){
			dateStart=new Date();
			var query=GetQueryString("query");
			var menuData=openSearchModel(query);
			if(menuData.length>0){

			var divModel=$('#search_result_model');
			var gridContentDiv = $('<div class="lui_profile_listview_content gridContent" />').appendTo(divModel);
			var listviewGridDiv = $('<div class="lui_profile_listview_grid" />').appendTo(gridContentDiv);
			var listviewGridBdDiv = $('<div class="lui_profile_listview_grid_bd" />').appendTo(listviewGridDiv);
			var listviewGridPageUl = $('<ul class="lui_profile_listview_card_page" />').appendTo(listviewGridBdDiv);
			
			$.each(menuData, function(i, n) {
				var _data = n;
				var gridItem = $('<li class="lui_profile_block_grid_item" />');
				var	appMenuItemBlock = $('<div class="appMenu_item_block" />').appendTo(gridItem);
					
				var	appMenuIconBar = $('<div class="appMenu_iconBar" />').appendTo(appMenuItemBlock);
				var	gridIcon = $('<i class="lui_profile_listview_l_icon"/>').appendTo(appMenuIconBar);
					gridIcon.addClass(_data.icon);
				
				var dataDescription = _data.description ? _data.description : _data.messageKey;
				var	gridDescription = $('<p class="appMenu_brief" />').text(dataDescription).attr("title",dataDescription).appendTo(appMenuItemBlock);
				var gridTitleWrapper = $('<div class="appMenu_title" />').appendTo(appMenuItemBlock);
				var trigIcon = $('<i class="trig" />').appendTo(gridTitleWrapper);
				var gridTitle = $('<span class="textEllipsis"/>').text(_data.messageKey).appendTo(gridTitleWrapper);
				
				var	appMenuMask = $('<div class="appMenu_mask" style="display: none;" />').appendTo(gridItem);
				var	appMenuBtnGroup = $('<div class="appMenu_btnGroup" />').appendTo(appMenuMask);
				
				appMenuItemBlock.on('click',function(){
					openByUrl(n.key+".index");
				});	
				
				gridItem.addClass('itemStyle_'+ (i%6+1));
				gridItem.appendTo(listviewGridPageUl);
			});
			
			dateEnd=new Date();
			useTime=dateEnd.getTime()-dateStart.getTime();
			}else{
				$("#search_none_span_model").text(query);
				$("#search_none_div_model").css("display", "block");
				dateEnd=new Date();
				useTime=dateEnd.getTime()-dateStart.getTime();
			} 
			document.getElementById("resultCount").innerHTML = menuData.length;
			document.getElementById("searchUserTime").innerHTML = useTime/1000;
		}else if(searchStringPeople){
			if(document.getElementById("resultCount").innerHTML=="0"){
				var fdSearchName=GetQueryString("fdSearchName");
				$("#search_none_span_people").text(fdSearchName);
				$("#search_none_div_people").css("display", "block");
			}
			addEvent();
			
		}
		
	}
	
	function changeURLPar(destiny, par, par_value) 
	{ 
	var pattern = par+'=([^&]*)'; 
	var replaceText = par+'='+par_value; 
	if (destiny.match(pattern)) 
	{ 
	var tmp = '/\\'+par+'=[^&]*/'; 
	tmp = destiny.replace(eval(tmp), replaceText); 
	return (tmp); 
	} 
	else 
	{ 
	if (destiny.match('[\?]')) 
	{ 
	return destiny+'&'+ replaceText; 
	} 
	else 
	{ 
	return destiny+'?'+replaceText; 
	} 
	} 
	return destiny+'\n'+par+'\n'+par_value; 
	} 
	
	function GetQueryString(name)
	{
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)
	    	 return r?decodeURIComponent(r[2]):null;
	}
	
		function getModel(){
			$.ajax({
				url : '${KMSS_Parameter_ContextPath}sys/profile/sys_profile_main/sysCfgProfileConfig.do?method=getAllData',
				cache : false,
				async : false,
				type : "post",
				dataType : 'text',
				success : function (data) {
					if(data != false){
						menuDatas = eval("("+data+")");
					}
				},
			
			})
			
		}
		
		
   		function openSearchModel(val){
   			var menus = []; // 保存菜单HTML
   			$.each(menuDatas, function(i, n) {
   				if(n.messageKey.toLowerCase().indexOf(val.toLowerCase()) > -1){
   					var dataJson={"key":"","messageKey":"","icon":"","description":""};
   					dataJson.key=n.key;
   					dataJson.messageKey=n.messageKey;
   					dataJson.icon=n.icon;
   					dataJson.description=n.description;
   					menus.push(dataJson);
   				}
   			})
   			return  menus; 
   		}
   		var timeout= window.setInterval(addEvent,300); 
   		
   		
		function trim(str){ //删除左右两端的空格
		　　     return str.replace(/(^\s*)|(\s*$)/g, "");
		　　 }
   		
   	function addEvent(){
   		if(searchStringPeople){
   			$(function () {
				$(".lui_paging_num_center").each(function () {
					$(this).click(function () {
						 var num=$(this).find("a").html();
						 if(GetQueryString("pageno")==null){
								window.location.href=window.location+"&pageno="+num;
							}else{
								var oldUrl=window.location.href;
								var url= changeURLPar(oldUrl,"pageno",num);
								window.location.href=url;
							} 
					});
				
				});	
			});	
			
			$(".lui_paging_next").click(function () {
				
				 if(GetQueryString("pageno")==null){
						window.location.href=window.location+"&pageno=2";
					}else{
						var num=parseInt(GetQueryString("pageno"))+1;
						var oldUrl=window.location.href;
						var url= changeURLPar(oldUrl,"pageno",num);
						window.location.href=url;
					} 
				
			});
			
			$(".lui_paging_pre").click(function () {
				 if(GetQueryString("pageno")!=null){
						var num=parseInt(GetQueryString("pageno"))-1;
						var oldUrl=window.location.href;
						var url= changeURLPar(oldUrl,"pageno",num);
						window.location.href=url;
					} 
			});
			
			$(".lui_paging_btn").click(function () {
				var num =parseInt($(".lui_paging_jump_center").find("input").val());
				var row =parseInt($(".lui_paging_amount_center").find("input").val());
				if(GetQueryString("pageno")==null){
					if(GetQueryString("rowsize")==null){
					window.location.href=window.location+"&pageno="+num+"&rowsize="+row;
					}else{
						var url=window.location+"&pageno="+num;
						var newUrl= changeURLPar(url,"rowsize",row);
						window.location.href=newUrl;
					}
				}else{
					if(GetQueryString("rowsize")==null){
						var oldUrl=window.location.href;
						var url= changeURLPar(oldUrl,"pageno",num);
						window.location.href=url+"&rowsize="+row;
					}else{
						var topKeyword = $("#topKeyword").val();
						var url = Com_Parameter.ContextPath + 'sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getPersons';
						url = url + "&fdSearchName="+encodeURIComponent(trim(topKeyword))+"&searchPeople=true";
						url = url +"&pageno="+num;
						url = url +"&rowsize="+row;
						window.location.href=url;
					}
					
				} 
			});
   		}
   		
   	}
   	
   function	openElement(url){
	   
	   window.open(url, "_blank");
   }
   
   if(!window._layer_zone_follow_after) {
		window._layer_zone_follow_after =  function (data, fdUserId, isFollowed,
				 $element, config, isFollowPerson, attentModelName, fansModelName) {
			var outer = $element.parents("[data-lui-mark='follow_btn']")[0];
			if(outer &&  data.result == "success") {
			   if(data.relation == 0){
				   var html = '<a class="sys_zone_btn_focus icon_focusAdd" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
		               'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+
	                   'is-follow-person="true" fans-action-type="unfollowed" fans-action-id="' + fdUserId +'"'+ 
	                   'href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">'+
	                   '<span><i></i><span>${lfn:message("sys-zone:sysZonePerson.cared1")}<span></span></a>';
				    $(outer).html(html);
	           }else if(data.relation == 1){
	        	  var html = '<a class="sys_zone_btn_focus icon_focused"><span><i></i><span>${lfn:message("sys-zone:sysZonePerson.cancelCared1")}</span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
			               'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"is-follow-person="true"'+ 
			               'fans-action-type="followed" fans-action-id="'+ fdUserId +'" href="javascript:void(0);"onclick="_layer_zone_follow_action(this);">${lfn:message("sys-zone:sysZonePerson.cancelCared")}</em></a>';
			      $(outer).html(html);
	           }else if( data.relation == 2){
	             var html = '<a class="sys_zone_btn_focus icon_unfocus" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
		                    'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true"'+
		                    'fans-action-type="unfollowed" fans-action-id="'+ fdUserId +'"  href="javascript:void(0);"  onclick="_layer_zone_follow_action(this);">'+
	                        '<span><i></i><span>${lfn:message("sys-zone:sysZonePerson.cared1")}</span></span></a>';
	                $(outer).html(html);
	           }else if(data.relation == 3){
	        	   var html = '<a class="sys_zone_btn_focus icon_eachFocus"><span><i></i><span>${lfn:message("sys-zone:sysZonePerson.follow.each")}</span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
		                      'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true" fans-action-type="followed"'+
		                      'fans-action-id="'+ fdUserId +'"  href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">${lfn:message("sys-zone:sysZonePerson.cancelCared")}</em></a>';
	        	   $(outer).html(html);
			   }
			}
		}; 
	}
	if(!window._layer_zone_follow_action) {
		window._layer_zone_follow_action =  function (target) {
			seajs.use(['sys/fans/resource/sys_fans','lui/jquery'], function(follow, $) {
				var $this = $(target);
				var isFollowed = $this.attr("fans-action-type");
				var isFollowPerson = $this.attr("is-follow-person");
				var attentModelName = $this.attr("attent-model-name");
				var fansModelName = $this.attr("fans-model-name");
				if(isFollowed) {
					var userId = $this.attr("fans-action-id");
					follow.fansFollow(userId , isFollowed, isFollowPerson, attentModelName, fansModelName, 
							$this, _layer_zone_follow_after,$("#sys_zone_card_frame"));
				}
			});
		};
	}
	
	
		/*
		BEGIN:VCARD
		N:dfdf;dfdf;;;
		FN: dfdf  dfdf
		TITLE:23424234
		ADR;HOME:;;hahahahhaha;;;;
		ORG:ahhfhahfhah
		TEL;WORK,VOICE:13433443322
		TEL;HOME,VOICE:0722-33344544
		URL;WORK:www.baidu.com
		EMAIL;INTERNET,HOME:234533444@qq.com
		END:VCARD
		*/
		
	function toUtf8(str) {    
		    var out, i, len, c;    
		    out = "";    
		    len = str.length;    
		    for(i = 0; i < len; i++) {    
		        c = str.charCodeAt(i);    
		        if ((c >= 0x0001) && (c <= 0x007F)) {    
		            out += str.charAt(i);    
		        } else if (c > 0x07FF) {    
		            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
		            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
		            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
		        } else {    
		            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
		            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
		        }    
		    }    
		    return out;    
		} 
	
		function showQrCode(obj,fdId){
			var isBitch = navigator.userAgent.indexOf("MSIE") > -1
			&& document.documentMode == null || document.documentMode <= 8;
			if (isBitch) // 直接去除对ie8浏览器的支持
				return;
			$(obj).addClass("staff_dropbox_on");
			var aa = $(obj).attr("data-url")
			var xx = aa.split("#");
			var str = "BEGIN:VCARD\nVERSION:3.0";
			var fdId;
			if(xx[0]){
				str +="\r\nN: "+toUtf8($.trim(xx[0]))+";;;";
				str +="\r\nFN: "+toUtf8($.trim(xx[0]));
			}
			if(xx[1]){
				str +="\r\nTITLE:"+toUtf8($.trim(xx[1])).replace(/;/g,",");
			}
			if(xx[2]){
				str +="\r\nTEL;CELL,VOICE:"+toUtf8($.trim(xx[2]));
			}
			if(xx[7]){
				str +="\r\nTEL;WORK,VOICE:"+toUtf8($.trim(xx[7]));
			}
			if(xx[3]){
				str +="\r\nEMAIL;INTERNET,HOME:"+toUtf8($.trim(xx[3]));
			}
			if(xx[6]){
				if(xx[1]=='[信息未公开]'){
					str +='\r\nORG:'+toUtf8("[信息未公开]");	
				}else{
					str +="\r\nORG:"+toUtf8($.trim(xx[6]));	
				}
				
			}
			
			if(xx[5]){
				str +="\r\nROLE:"+toUtf8(xx[5]);
			}

			if(xx[4]){
				fdId=xx[4];
			}
			
			str += "\r\nEND:VCARD";
			seajs.use(['lui/qrcode'], function(qrcode) {
				qrcode.Qrcode({
					text :str,
					element : $("#personQrCode_"+fdId),
					render :  'canvas'
				});
			});
			console.log(str);
			$("#"+fdId).show();
		}
	
	function hideQrCode(obj){
		var isBitch = navigator.userAgent.indexOf("MSIE") > -1
		&& document.documentMode == null || document.documentMode <= 8;
		if (isBitch) // 直接去除对ie8浏览器的支持
			return;
		var aa = $(obj).attr("data-url")
		var xx = aa.split("#");
		var fdId;
		
		if(xx[4]){
			fdId=xx[4];
		}
		$(obj).removeClass("staff_dropbox_on");
		$("#personQrCode_"+fdId).html("");
		$("#"+fdId).hide();
	}
	function showSearchfilter(){
		if($("#modelfilter").is(":hidden")){
			$("#modelfilter").show();
			$("#modelfilter").focus(); 
			$("#oper_more_close").show();
			$("#oper_more").hide();
			hideTimefilter();
			hideAdvfilter();
			}else{
				hideSearchfilter();
			}
	}
	function hideSearchfilter(){		
		$("#modelfilter").hide(); 
		$("#oper_more_close").hide();
		$("#oper_more").show();
	}
	function showTimefilter(){
		if($("#timeFilter").is(":hidden")){
			$("#timeFilter").show();
			$("#timeFilter").focus(); 
			$("#oper_time_close").show();
			$("#oper_time").hide();
			hideSearchfilter();
			hideAdvfilter();
			}else{
				hideTimefilter();
			}
	}
	function hideTimefilter(){		
		$("#timeFilter").hide();
		$("#oper_time_close").hide();
		$("#oper_time").show();
	}
	function showAdvfilter(){
		if($("#advFilter").is(":hidden")){
			$("#advFilter").show();
			$("#advFilter").focus(); 
			$("#oper_adv_close").show();
			$("#oper_adv").hide();
			hideSearchfilter();
			hideTimefilter();
			}else{
				hideAdvfilter();
			}
	}
	function hideAdvfilter(){		
		$("#advFilter").hide();
		$("#oper_adv_close").hide();
		$("#oper_adv").show();
	}
	
	
	domain.autoResize();
</script>