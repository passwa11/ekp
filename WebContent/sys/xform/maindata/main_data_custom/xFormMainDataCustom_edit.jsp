<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<kmss:windowTitle moduleKey="sys-xform-maindata:tree.relation.jdbc.root"  subjectKey="sys-xform-maindata:tree.relation.jdbc.custom" subject="${sysFormMainDataCustomForm.docSubject}" />
<html:form action="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do" enctype="multipart/form-data">
<script type="text/javascript">
	Com_IncludeFile('doclist.js');
</script>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<div id="optBarDiv">
	<c:if test="${sysFormMainDataCustomForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="xform_main_data_custom_submit('update');">
	</c:if>
	<c:if test="${sysFormMainDataCustomForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="xform_main_data_custom_submit('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="xform_main_data_custom_submit('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${ lfn:message('sys-xform-maindata:tree.relation.jdbc.custom') }</p>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDataCustom.css">
<center>
<style>
.xform_main_data_custom_excelOpt input{
	cursor:pointer;
}
.xform_main_data_custom_errorlog span{
	color:red;
}
</style>
<table class="tb_normal" width=95%>
<c:if test="${param.from ne 'modeling'}">
	<!-- 排序号、所属分类 -->
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
			<%-- <xform:text property="fdOrder" style="width:85%" /> --%>
			<xform:text property="fdNewOrder" style="width:85%;" validators="digits min(0)" />
		</td>		
	</tr>
	<tr>
		<!-- 标题 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-maindata" key="sysFormMainDataInsystem.docSubject"/>
		</td>
		<td width=35%>
			<xform:text property="docSubject" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.docSubject') }" style="width:85%" />
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
		<td colspan="3">
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
			<c:choose>
				<c:when test="${sysFormMainDataCustomForm.isPage != 'true'}">
					<div class="xform_main_data_custom_tableWrap">
						<!-- 有上级 -->
						<c:import url="/sys/xform/maindata/main_data_custom/xFormMainDataCustomList_edit.jsp" charEncoding="UTF-8">
							<c:param name="tableKey" value="TABLE_DocList_hasSuper" />
							<c:param name="formName" value="sysFormMainDataCustomForm" />
							<c:param name="hasSuper" value="true" />
							<c:param name="listData" value="${sysFormMainDataCustomForm.isCascade eq 'true'}" />
						</c:import>
						<!-- 无上级 -->
						<c:import url="/sys/xform/maindata/main_data_custom/xFormMainDataCustomList_edit.jsp" charEncoding="UTF-8">
							<c:param name="tableKey" value="TABLE_DocList_noSuper" />
							<c:param name="formName" value="sysFormMainDataCustomForm" />
							<c:param name="hasSuper" value="false" />
							<c:param name="listData" value="${sysFormMainDataCustomForm.isCascade eq 'false'}" />
						</c:import>
					</div>	
				</c:when>
				<c:otherwise>
					<span style="color:red;">注意：由于自定义的数据项过多，全部列举出来会有性能问题，请到查看页面进行编辑！</span>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	
	<!-- 创建者、修改者 -->
	<c:if test="${sysFormMainDataCustomForm.method_GET=='edit'}">
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
	</c:if>
</table>
<html:hidden property="isPage" />
<!-- 设置分页的最大阈值，当明细表的选项总数大于该值，则用另外一种样式显示，默认100 -->
<input type='hidden' name='maxNum' value='100' />
</center>

<script>
	Com_IncludeFile("xFormMainDataCustom_edit_script.js",Com_Parameter.ContextPath+'sys/xform/maindata/main_data_custom/','js',true);
	DocList_Info.push("TABLE_DocList_hasSuper");
	DocList_Info.push("TABLE_DocList_noSuper");
	var xform_main_data_custom_validation = $KMSSValidation();
	
	// 自定义数据的全局变量
	var xform_main_data_custom_parameter = {};
	
	xform_main_data_custom_parameter.cascadeCustomDict;
	
	//所属分类的弹框
	function XForm_treeDialog() {
		Dialog_Tree(false, 'docCategoryId', 'docCategoryName', ',', 
				'sysFormJdbcDataSetCategoryTreeService&parentId=!{value}', 
				"${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory')}", 
				null, null, null, null, null, 
				"${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory')}");
	}
	
	//级联对象弹出框
	function XForm_customDialog(){
		var fdId = $("[name='fdId']").val();
		Dialog_TreeList(
				false,
				'cascadeCustomId',
				'cascadeCustomSubject',
				',',
				'sysFormMainDataCustomControlTreeBean&type=cate&selectId=!{value}&serviceBean=sysFormMainDataCustomControlTreeInfo',
				"${lfn:message('sys-xform-maindata:sysFormMainDataCustom.superObject')}",
				'SysFormMainDataCustomTreeService&parentId=!{value}&fdId=' + fdId,xform_main_data_custom_setCascadeCustomDict);
	}
	
	//级联弹出框的回调函数，设置数据
	function xform_main_data_custom_setCascadeCustomDict(result){
		if(result && result.data && result.data.length > 0){
			var cascadeCustomId = result.data[0].id;
			if(cascadeCustomId){
				var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=findExtendDataByFdId&fdId="+cascadeCustomId;
				$.ajax({
					url:url,
					type:"GET",
					async:false,
					success:function(result){
						if(result != null){
							result = result.replace(/&#39;/g,"\'");
							xform_main_data_custom_parameter.cascadeCustomDict = $.parseJSON(result);
							//清空已选的内容
							xform_main_data_custom_delAllTr();
						}
					}
				});
			}
		}
	}
	
	function xform_main_data_custom_delAllTr(){
		var table = xform_main_data_custom_getCurrentTable();
		$(table).find("tr[invalidrow!='true']").each(function(){
			DocList_DeleteRow_ClearLast(this);
		});			
	}
	
	Com_Parameter.event["submit"].push(xform_main_data_custom_beforeSubmitValidate);
	
	//提交前校验
	function xform_main_data_custom_beforeSubmitValidate(){
		if(!validateKeyUnique()){
			return false;
		}
		//解析合并数据
		var isCascade = $("input[name='isCascade']:checked").val();
		var extendDataArray = [];
		if(isCascade == 'true'){
			var rows = $('#TABLE_DocList_hasSuper tr:not(:first)');			
			for(var i = 0;i < rows.length;i++){
				var extendData = {};
				extendData.value = $(rows[i]).find("input[name$='fdValue']").val();
				extendDataArray.push(extendData);
			}
		}else if(isCascade == 'false'){
			//清空级联对象
			$("input[name='cascadeCustomId']").val("");
			var rows = $('#TABLE_DocList_noSuper tr:not(:first)');			
			for(var i = 0;i < rows.length;i++){
				var extendData = {};
				extendData.value = $(rows[i]).find("input[name$='fdValue']").val();
				extendDataArray.push(extendData);
			}
		}
		//校验实际值不能重复
		var temp = {};
		for(var i in extendDataArray){
			if(temp[extendDataArray[i].value]){
				alert("${lfn:message('sys-xform-maindata:sysFormMainDataCustom.warnCopyValue')}" + extendDataArray[i].value);
				return false;
			}
			temp[extendDataArray[i].value] = true;
		}
		
		return true;
	}
	
	//自定义校验方法
	xform_main_data_custom_validation.addValidator('myAlphanum','${lfn:message("sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring") }',function(v, e, o){
		return this.getValidator('isEmpty').test(v) || !/\W/.test(v);
	});
	
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
	
	function xform_main_data_custom_submit(method){
		Com_Submit(document.sysFormMainDataCustomForm, method);
	}
	
	//校验关键字的唯一性
	function validateKeyUnique(){
		var fdKey = document.getElementsByName("fdKey")[0];
		var isUnique = true;
		if(fdKey && fdKey.value != ''){
			var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=isUnique&fdKey=" + fdKey.value + "&fdId=${param.fdId}";
			$.ajax({ url: url, async: false, dataType: "json", cache: false, success: function(rtn){
				if("true" != rtn.isUnique){
					isUnique = false;
					alert("${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKeyNotUniqueWarning')}");
				}
			}});		
		}
		return isUnique;
	}
	
	//初始化表格
	function xform_main_data_custom_initVar(){
		var isCascade = '${sysFormMainDataCustomForm.isCascade}';
		xform_main_data_custom_showCascadeSelect(null,isCascade);
		if(isCascade && isCascade == 'true'){
			var cascadeCustomExtendData = '${sysFormMainDataCustomForm.cascadeCustomExtendData}';
			if(cascadeCustomExtendData && cascadeCustomExtendData != ''){
				cascadeCustomExtendData = cascadeCustomExtendData.replace(/&#39;/g,"\'");
				xform_main_data_custom_parameter.cascadeCustomDict = $.parseJSON(cascadeCustomExtendData);	
			}
		}
	}
	
	Com_AddEventListener(window,'load',xform_main_data_custom_initVar);
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>