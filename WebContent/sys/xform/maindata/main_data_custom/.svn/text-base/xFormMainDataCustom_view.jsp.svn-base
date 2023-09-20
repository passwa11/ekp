<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<title>
	<c:out value="${ lfn:message('sys-xform-maindata:tree.relation.jdbc.root') } - ${ lfn:message('sys-xform-maindata:tree.relation.jdbc.custom') }"></c:out>
</title>
<script>
var _isLangSuportEnabled = <%=MultiLangTextGroupTag.isLangSuportEnabled()%> ;
var _langJson = <%=MultiLangTextGroupTag.getLangsJsonStr()%>;
var _userLang = "<%=MultiLangTextGroupTag.getUserLangKey()%>";

	function confirmDelete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	Com_IncludeFile('doclist.js');
	Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp", null, "js");
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDataCustom_view.css">
<div id="optBarDiv">

	<input type="button"
		value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysFormMainDataCustom.do?method=edit&from=${JsParam.from}&fdId=${JsParam.fdId}','_self');">

	<input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysFormMainDataCustom.do?method=delete&fdId=${JsParam.fdId}','_self');">
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.custom"/></p>

<center>
<html:form action="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do">
	<table id="Label_Tabel" width=95%>
		<!-- 基本信息 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-maindata' key='sysFormMainData.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
				
				<c:if test="${param.from ne 'modeling'}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.docCategory"/>
						</td><td width="35%">
							<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="docCategoryId" style="width:90%"
									propertyName="docCategoryName" dialogJs="XForm_treeDialog()">
							</xform:dialog>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdOrder"/>
						</td><td width="35%">
							<!-- <xform:text property="fdOrder" style="width:85%" /> -->
							<xform:text property="fdNewOrder" style="width:85%;" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
						</td>
						<td width=35%>
							<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
						</td>
						<td width=35%>
							<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:15%" validators="alphanum"/>&nbsp;
						</td>
					</tr>
					<!-- 是否级联 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade"/>
						</td>
						<td colspan="3">
							<div class="xform_main_data_custom_cascade">
								<xform:radio showStatus="readOnly" property="isCascade" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.isCascade')}" onValueChange="xform_main_data_custom_showCascadeSelect(this);">
									<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade.yes"/></xform:simpleDataSource>
									<xform:simpleDataSource value="false"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade.no"/></xform:simpleDataSource>
								</xform:radio>
								<c:if test="${sysFormMainDataCustomForm.cascadeCustomId ne null}">
									<a href="javascript:void(0);" onclick="Com_OpenWindow('sysFormMainDataCustom.do?method=view&fdId=${sysFormMainDataCustomForm.cascadeCustomId}','_target');" style="color:#1b83d8;"><c:out value="${sysFormMainDataCustomForm.cascadeCustomSubject}"></c:out></a>
								</c:if>
								
							</div>
						</td>
					</tr>
		</c:if>

		<c:if test="${param.from eq 'modeling'}">
			<tr>
				<!-- 标题 -->
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
				</td>
				<td width=35%>
					<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
					<html:hidden property="docCategoryId" />
					<html:hidden property="docCategoryName" />
				</td>
				<!-- 关键字 -->
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKey"/>
				</td>
				<td width=35%>
					<xform:text property="fdKey" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKey')}" style="width:85%" validators="myAlphanum"/></br>
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSet.fdKeyTip"/>
				</td>
			</tr>
			<!-- 是否级联 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade"/>
				</td>
				<td>
					<div class="xform_main_data_custom_cascade">
						<xform:radio property="isCascade" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.isCascade')}" onValueChange="xform_main_data_custom_showCascadeSelect(this);">
							<xform:simpleDataSource value="true"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade.yes"/></xform:simpleDataSource>
							<xform:simpleDataSource value="false"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.cascade.no"/></xform:simpleDataSource>
						</xform:radio>

						<div id="xform_main_data_custom_cascadeCustomWrap" style="display:none;width:30%;">
							<xform:dialog subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.cascadeCustom') }" propertyId="cascadeCustomId" style="width:90%;"
								propertyName="cascadeCustomSubject"  dialogJs="XForm_customDialog();">
							</xform:dialog>
						</div>
					</div>
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message key="model.fdOrder"/>
				</td><td width="35%">
					<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
					<xform:text property="fdNewOrder" style="width:85%;" validators="digits min(0)" />
				</td>		
			</tr>
		</c:if>

					<!-- 自定义数据 -->
					<tr>
						<td colspan="4">
							<div class="xform_main_data_custom_excelOpt">
								<iframe name="file_frame" style="display:none;"></iframe>
								<div class="xform_main_data_custom_search" style="display:inline-block;float:left;padding-bottom:6px;">
									<input type='text' class='xform_main_data_custom_search_input' title='<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.search"/>' onkeyup='enterTrigleSelect(event);' placeholder='<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.searchTip"/>'></input>
									<a href="javascript:xform_main_data_custom_exeSearch();"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.search"/></a>
								</div>
								<div class="xform_main_data_custom_excel" style="display:inline-block;float:right;">
									<input type="button" class="lui_form_button" name="custom_exportExcel" style="cursor:pointer;" value='<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.exportExcel"/>' onclick="exportExcel();" />
									<input type="button" class="lui_form_button" name="custom_exportExcel" style="cursor:pointer;" value='<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.importExcel"/>' onclick="importExcelDialog();" />
								</div>
							</div>
							<div class="xform_main_data_custom_errorlog"></div>
							<div class="xform_main_data_custom_tableWrap">
								<table id='TABLE_DocList_custom_table' class="tb_normal" width=100% align="center">
									<c:set var="com.landray.kmss.web.taglib.FormBean" value="${sysFormMainDataCustomForm }" scope="request" />
									<c:import url="/sys/xform/maindata/main_data_custom/xFormMainDataCustom_view_tableInPage.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="sysFormMainDataCustomForm" />
									</c:import>
								</table>
								<!-- 上下翻页 -->
								<div id="pageOperation">
									<ul class="xform_main_data_custom_pageUl">
										<li style="float:left;" id="lastPageNum" onclick="xform_main_data_custom_skipPage('1');"><bean:message key="page.thePrev"/></li>
										<li id="nextPageNum" onclick="xform_main_data_custom_skipPage('2');"><bean:message key="page.theNext"/></li>
									</ul>
								</div>
							</div>	
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdCreator"/>
						</td>
						<td width="35%">
							<c:out value="${sysFormMainDataCustomForm.docCreatorName }"></c:out>				
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdCreateTime"/>
						</td>
						<td width="35%">
							<c:out value="${sysFormMainDataCustomForm.docCreateTime }"></c:out>
						</td>
					</tr>
					<c:if test="${not empty sysFormMainDataCustomForm.docAlterorName}">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.docAlteror"/>
							</td>
							<td width="35%">
								<c:out value="${sysFormMainDataCustomForm.docAlterorName }"></c:out>
							</td>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.fdAlterTime"/>
							</td>
							<td width="35%">
								<c:out value="${sysFormMainDataCustomForm.docAlterTime }"></c:out>
							</td>
						</tr>
					</c:if>
				</table>
			</td>
		</tr>
		<!-- 被引用表单模板 -->
		<c:import url="/sys/xform/maindata/xFormMainDataRef_view.jsp" charEncoding="UTF-8">
			<c:param name="fdModelName" value="com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustom"></c:param>
			<c:param name="fdId" value="${sysFormMainDataCustomForm.fdId }"></c:param>
		</c:import>
	</table>
	<!-- 编辑弹出的div 用iframe可能会好点-->
	<div class="xform_main_data_custom_editDiv">
		<div class="xform_main_data_custom_editDiv_bg" style="position: fixed"></div>
		<div class="xform_main_data_custom_editDiv_contentWrap" style="position: fixed">
			<div class="xform_main_data_custom_editDiv_content" style="margin-top:15px;">
				<table class="tb_normal" width=100% align="center">
					<c:if test="${sysFormMainDataCustomForm.isCascade eq 'true' }">
						<tr>
							<td width="15%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.super"/></td>
							<td><select name="_editDiv_fdCascadeName">
							</select></td>
						</tr>	
					</c:if>
					<tr>
						<td width="15%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
						<td><xform:text showStatus="edit" property="_editDiv_fdValueText" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }"  style="width:80%;" required="true"></xform:text></td>
					</tr>
					<tr>
						<td width="15%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
						<td><xform:text showStatus="edit" property="_editDiv_fdValue" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }"  style="width:80%;" required="true"></xform:text></td>
					</tr>
					<tr>
						<td width="15%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
						<td><xform:text showStatus="edit" property="_editDiv_fdOrder" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.order') }"  style="width:80%;" validators="digits min(0)"></xform:text></td>
					</tr>
				</table>
			</div>
			<div class="xform_main_data_custom_editDiv_button" style="margin-top:15px;">
				<ui:button text='${lfn:message("button.ok")}' style="width:72px;padding-right:8px;"
						onclick="xform_main_data_custom_editDiv_OK();">
				</ui:button>
				<ui:button text='${lfn:message("button.cancel")}' style="width:72px"
						onclick="xform_main_data_custom_editDiv_hide();">
				</ui:button>
			</div>
		</div>
	</div>
</html:form>
<script>
	Com_IncludeFile("xFormMainDataCustom_view_script.js",Com_Parameter.ContextPath+'sys/xform/maindata/main_data_custom/','js',true);
	DocList_Info.push('TABLE_DocList_custom_table');
	var _cascadeCustomExtendData = '${sysFormMainDataCustomForm.cascadeCustomExtendData}';
	_cascadeCustomExtendData = _cascadeCustomExtendData.replace(/&#39;/g,"\'");
	var xform_main_data_custom_currentPageNum = 0;
	var _xform_main_data_custom_rowSize = 15;
	
	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event){
		if (event && event.keyCode == '13') {
			xform_main_data_custom_exeSearch();
		}
	}
	
	function xform_main_data_custom_exeSearch(){
		var $searchInput = $(".xform_main_data_custom_search_input");
		var searchVal = encodeURIComponent($searchInput.val());
		xform_main_data_custom_search(0,searchVal);
	}
	
	// 获取所有下拉选项
	function xform_main_data_custom_getSelectOptions(){
		var cascadeCustomExtendData = '${sysFormMainDataCustomForm.cascadeCustomExtendData}';
		if(cascadeCustomExtendData && cascadeCustomExtendData != ''){
			cascadeCustomExtendData = cascadeCustomExtendData.replace(/&#39;/g,"\'");
			cascadeCustomExtendData = $.parseJSON(cascadeCustomExtendData);
			var html = [];
			for(var i = 0;i < cascadeCustomExtendData.length;i++){
				var data = cascadeCustomExtendData[i];
				html.push("<option value='"+ data['fdId'] +"'>");
				html.push(data['text']);
				html.push("</option>");
			}
			return html.join('');
		}
	}
	
	// 上下页翻页查询
	function xform_main_data_custom_search(pageno,searchVal){
		if(pageno == null){
			pageno = 0;
		}
		if(pageno == 0){
			xform_main_data_custom_currentPageNum = 1;
		}
		var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=getPageData&fdId=${param.fdId}&rowsize="+ _xform_main_data_custom_rowSize +"&orderby=fdId&pageno="+pageno;
		if(searchVal){
			url += "&search=" + searchVal;
		}
		$.ajax({
			url: url,
			type:"GET",
			async:false,
			success:function(data){
				if(data){
					var result = {};
					try{
						result = $.parseJSON(data);					 
					}catch(e){
						result.dataArray = [];
						console.warn("查询出错:具体请查看后台信息！");
					}
					xform_main_data_custom_fillTableContent(result);
				}
			}
		});
	}
	
	//excel导出
	function exportExcel(){
		var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=exportExcel&fdId=${param.fdId}";
		Com_OpenWindow(url,"_parent");
	}

	// 打开对话框
	function importExcelDialog(){
		if(typeof(seajs) != 'undefined'){
			seajs.use(['lui/dialog'], function(dialog) {
				var url = "/sys/xform/maindata/main_data_custom/xFormMainDataCustom_view_excelDialog.jsp?isCascade=${sysFormMainDataCustomForm.isCascade}&fdId=${sysFormMainDataCustomForm.fdId}";
				var height = document.documentElement.clientHeight * 0.5;
				var width = document.documentElement.clientWidth * 0.7;
				var dialog = dialog.iframe(url,'<bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.importExcel"/>',null,{width:width,height : height,close:false});
			});	
		}
	}
	
	function initListDatas(){
		setTimeout(xform_main_data_custom_search,100);
	}
	
	$(function(){
		var bindEvent = function(context) {
			$("input[name$='fdValue']",context).blur(function(event){
				var value = $(this).val();
				if (!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(value)){
					var message = "${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdValueWarning')}";
					var $tip = $('<div>');
					$tip.attr("id",'');
					$tip.css({"border":"1px solid #fff0e1","color":"#ff6910","background":"#fff0e1 url('info.png') no-repeat 15px center","cursor":"pointer","padding":"3px","border-radius":"4px","margin-top":"5px"});
					$tip.html(message).css("display","inline-block").delay(3000).fadeOut();
					$(this).closest("td").append($tip);	
					$tip.click(function(){
						$(this).hide();
					});
				}
			})
			
		};
		bindEvent(document);
		$(document).on("table-add",function(event,source){
			bindEvent(source);
		});
	})
	
	Com_AddEventListener(window,'load',initListDatas);
	$KMSSValidation();
</script>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>