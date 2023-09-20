<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/formLang/css/formLang.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/modeling/base/formLang.do">
			<div class="lang">
				<div class="lang-head" style="display:none;">
					<div class="lang-head-tab-container"></div>
					<div class="lang-head-btn">
						<div class="head-btn btn-update" onclick="submitForm('updateMultiLang');">${ lfn:message('button.update') }</div>
					</div>
				</div>
				<div class="lang-main">
					<div class="lang-content-container" style="display:none;">
					</div>
				</div>
			</div>
			<html:hidden property="fdId"/>
			<html:hidden property="fdModelId"/>
			<html:hidden property="fdMultiLangContent"/>
		</html:form>
		<script>
		
			// lang:{curLang:xxx,lang:{}}
			function getSysLangInfo(){
				var lang = {};
				/********* 当前系统的语种 start **********/
				var uu_lang_arr = '<%=ResourceUtil.getKmssConfigString("kmss.multi.lang")%>';
				var defaultLang = '<%=LangUtil.Default_Lang%>';
				var _currentLang = '<%=UserUtil.getKMSSUser().getLocale().toString()%>'.replace("_","-");
				 
				if(_currentLang == 'null'){
					_currentLang = defaultLang;
				}
				lang.curLang = _currentLang;
				
				if(uu_lang_arr.charAt(uu_lang_arr.length - 1) == ','){
					uu_lang_arr = uu_lang_arr.substr(0, uu_lang_arr.length - 1);
				}
				//key
				var _langLanguage = uu_lang_arr.split(",");
		
				//value
				var _langLanguage_value = eval('<%=LangUtil.getLangValue()%>');
				lang.lang = {};
				for(var i = 0;i < _langLanguage.length;i++){
					var key = _langLanguage[i];
					lang.lang[key] = _langLanguage_value[i];
				}
				
				// 官方语言
				var officialLang = '<%=ResourceUtil.getKmssConfigString("kmss.lang.official")%>';
				if(!officialLang || officialLang == 'null'){
					officialLang = defaultLang;
				}
				if(officialLang.indexOf("|") > -1){
					officialLang = officialLang.substring(officialLang.indexOf("|") + 1);
				}
				lang.officialLang = officialLang;
				/********* 当前系统的语种 end **********/
				return lang;
			}
			
			seajs.use(['lui/jquery',"sys/modeling/base/formLang/js/langViewContainer"],function($, langViewContainer){
				var langEnv = getSysLangInfo();
				// 初始化头部页签
				var $tabContainer = initHeaderTab();
				// 处理双引号
				var storedDatas = $("[name='fdMultiLangContent']").val() || "[]";
				storedDatas = storedDatas.replace(/\\"/g,'&quot;')
				
				var viewContainerWgt = new langViewContainer.LangViewContainer({
					id : "viewContainer",
					element : $(".lang-content-container"),
					vars : {
						langEnv : langEnv,
						storedDatas : eval(storedDatas)
					}
				});
				viewContainerWgt.startup();
				viewContainerWgt.draw();
				
				// 默认第一个页签选中s
				$tabContainer.find("[data-tab-key]").first().trigger($.Event("click"));
				
				function initHeaderTab(){
					var tabContainer = $(".lang-head-tab-container");
					var getTabItem = function(name, value){
						var html = "";
						html += "<div class='lang-head-tab' data-tab-key='"+ value +"'>";
						html += "<span>"+ name +"</span>";
						html += "</div>";
						return html;
					}
					var curLang = langEnv.curLang;
					var lang = langEnv.lang;
					for(var key in lang){
						if(key === curLang){
							continue;
						}
						tabContainer.append(getTabItem(lang[key], key));
					}
					tabContainer.on("click", "[data-tab-key]", function(){
						$(this).siblings().removeClass("active");
						$(this).addClass("active");
						LUI("viewContainer").switchViewByKey($(this).attr("data-tab-key"));
					});
					return tabContainer;
				}
			
			});
				
			window.submitForm = function(method){
				var keyData = LUI("viewContainer").getKeyData();
				$("[name='fdMultiLangContent']").val(JSON.stringify(keyData).replace(/&quot;/g,"\\\""));
				Com_Submit(document.sysFormTemplateForm,method);
			}
			
		</script>
	</template:replace>
</template:include>