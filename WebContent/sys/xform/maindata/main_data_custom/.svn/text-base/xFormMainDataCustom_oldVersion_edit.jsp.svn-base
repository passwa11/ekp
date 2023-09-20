<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<title>
	<c:out value="${ lfn:message('sys-xform-maindata:tree.relation.jdbc.root') } - ${ lfn:message('sys-xform-maindata:tree.relation.jdbc.custom') }"></c:out>
</title>

<html:form action="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do">
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
<table class="tb_normal" width=95%>
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
	<!-- 自定义数据 -->
	<tr>
		<td colspan="4">
			<div class="xform_main_data_custom_tableWrap">
				<!-- 有上级 -->
				<table id="xform_main_data_custom_extendDataTable_hasSuper" width="100%" style="display:none;">
					<tr class="tr_normal_title">
						<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.index"/></td>
						<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.super"/></td>
						<td width="35%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
						<td width="22.5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
						<td width="10%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
						<td width="5%"><a href="javascript:void(0);" onclick="xform_main_data_custom_addItem('true',this);" style="color:#1b83d8;"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.add"/></a></td>
					</tr>
				</table>
				<!-- 无上级 -->
				<table id="xform_main_data_custom_extendDataTable" width="100%">
					<tr class="tr_normal_title">
						<td width="5%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.index"/></td>
						<td width="45%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.showValue"/></td>
						<td width="30%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.realValue"/></td>
						<td width="15%"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.order"/></td>
						<td width="5%"><a href="javascript:void(0);" onclick="xform_main_data_custom_addItem('false',this);" style="color:#1b83d8;"><bean:message bundle="sys-xform-maindata" key="sysFormMainDataCustom.add"/></a></td>
					</tr>
				</table>
			</div>	
			<input type="hidden" name="fdExtendData" />
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
	<html:hidden property="isOldVersion"/>
</table>
</center>

<script>
	
	var xform_main_data_custom_validation = $KMSSValidation();
	
	//自定义校验方法
	xform_main_data_custom_validation.addValidator('myAlphanum','${lfn:message("sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring") }',function(v, e, o){
		return this.getValidator('isEmpty').test(v) || !/\W/.test(v);
	});

	var xform_main_data_custom_cascadeCustomDict;
	
	// 是否多语言
	var _xform_main_data_custom_isLangEnabled = <%=SysLangUtil.isLangEnabled()%>;
	// 官方语言
	var _xform_main_data_custom_officialLang = "<%=ResourceUtil.getKmssConfigString("kmss.lang.official")%>";
	// 当前语言
	var _xform_main_data_custom_currentLang = Com_Parameter.Lang.toUpperCase ();
	// 所有语言
	var _xform_main_data_custom_supportLang = "<%=ResourceUtil.getKmssConfigString("kmss.lang.support")%>"; 
	
	// 支持多语言的显示值
	function xform_main_data_custom_buildValueTextHtml(hasValue,value){
		var html = "<td>";
		var official = "";
		var hasOffical = true;
		//无论是否支持多语言，都需要官方语言
		if(_xform_main_data_custom_officialLang){
			var officialArray = _xform_main_data_custom_officialLang.trim().split("|");
			if(officialArray.length == 2){
				official = officialArray[1].toUpperCase();
				var officialLang = "${lfn:message('sys-xform-maindata:sysFormMainDataCustom.officialLang')}";
				html += "<input class='inputsgl' type='text' name='extendDataValueText_"+ official +"' data-lang='"+ official +"' validate='required' subject='"+ officialLang +"' title='"+ officialLang +"' value='"+ (hasValue?value.valueText[official]:'') +"'/><span class='txtstrong'>*</span>";
				html += "<span>("+ officialArray[0] +")</span>";	
			}else{
				hasOffical = false;
				console.warn("admin.do里面配置的官方语言格式存在问题！");
			}
		}else{
			hasOffical = false;
		}
		// 如果没有官方语言
		if(hasOffical == false){
			var langUpper = _xform_main_data_custom_currentLang.toUpperCase();
			if(langUpper){
				html += "<input class='inputsgl' type='text' name='extendDataValueText_"+ langUpper +"' data-lang='"+ langUpper +"' value='"+ (hasValue?value.valueText[langUpper]:'') +"'/>";
			}	
		}
		//判断是否是多语言
		if(_xform_main_data_custom_isLangEnabled && _xform_main_data_custom_isLangEnabled == true){
			if(_xform_main_data_custom_supportLang && _xform_main_data_custom_supportLang != ''){
				var supportLangArray = _xform_main_data_custom_supportLang.split(";");
				for(var i = 0;i < supportLangArray.length;i++){
					var supportLang = supportLangArray[i];
					var lang = supportLang.split("|");
					if(lang.length == 2){
						var langUpper = lang[1].toUpperCase();
						if(langUpper != official){
							html += "<input class='inputsgl' type='text' name='extendDataValueText_"+ langUpper +"' data-lang='"+ langUpper +"' value='"+ (hasValue && value.valueText[langUpper] ? value.valueText[langUpper]:'') +"'/>";
							html += "<span>("+ lang[0] +")</span>";	
						}	
					}
				}
			}
		}		
		html += "</td>";
		return html;
	}
	
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
		/* Dialog_Tree(false, 'cascadeCustomId', 'cascadeCustomSubject', ',', 
				'SysFormMainDataCustomTreeService&parentId=!{value}', 
				"${lfn:message('sys-xform-maindata:sysFormMainDataCustom.superObject')}", null, xform_main_data_custom_setCascadeCustomDict, null, null, null, 
				"${lfn:message('sys-xform-maindata:sysFormMainDataCustom.superObject')}"); */
		Dialog_TreeList(
				false,
				'cascadeCustomId',
				'cascadeCustomSubject',
				',',
				'sysFormMainDataCustomControlTreeBean&type=cate&selectId=!{value}&serviceBean=sysFormMainDataCustomControlTreeInfo',
				"${lfn:message('sys-xform-maindata:sysFormMainDataCustom.superObject')}",
				'SysFormMainDataCustomTreeService&parentId=!{value}',xform_main_data_custom_setCascadeCustomDict);
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
							xform_main_data_custom_cascadeCustomDict = $.parseJSON(result);
							//清空已选的内容
							xform_main_data_custom_delAllTr();
						}
					}
				});
			}
		}
	}
	
	//是否是级联，级联则显示含有上级的table
	function xform_main_data_custom_showCascadeSelect(dom,isCascade){
		var value;
		if(isCascade && isCascade != ''){
			value = isCascade;
		}else{
			value = $(dom).val();
		}
	
		//value 为true则有级联，为false则不是级联
		if(value){
			if(value == 'true'){
				$("#xform_main_data_custom_extendDataTable_hasSuper").show();
				$("#xform_main_data_custom_extendDataTable").hide();
				//显示级联对象选择
				$("#xform_main_data_custom_cascadeCustomWrap").css('display','inline-block');
			}else if(value == 'false'){
				$("#xform_main_data_custom_extendDataTable_hasSuper").hide();
				$("#xform_main_data_custom_extendDataTable").show();
				$("#xform_main_data_custom_cascadeCustomWrap").hide();
			}
		}
	}
	
	//增加行
	function xform_main_data_custom_addItem(type,dom,table,value){
		//type true为级联，含有上级；false为不是级联，不含上级
		var $table;
		if(table){
			$table = table;
		}else{
			$table = $(dom).closest('table');	
		}		
		
		if(type){
			if(type == 'true'){
				var newTr = $table[0].insertRow($table[0].rows.length);
				$(newTr).addClass("xform_main_data_custom_contentTr");
				//行序号
				var rowIndex = newTr.rowIndex.toString();
				var html = "";
				//序号
				html += "<td style='text-align:center;'><span class='xform_main_data_custom_tdIndex'>"+ rowIndex +"</span></td>";
				
				//上级
				html += "<td style='text-align:center;'>";
				if(xform_main_data_custom_cascadeCustomDict){
					html += "<select name='extendDataSelect'>";
					var cascadeCustomDict = xform_main_data_custom_cascadeCustomDict;
					for(var i = 0;i < cascadeCustomDict.length;i++){
						html += "<option value='"+ cascadeCustomDict[i].value +"' ";
						if(value && value.cascade && value.cascade == cascadeCustomDict[i].value){
							html += " selected='selected'";
						}
						html += ">";
						var valueText = cascadeCustomDict[i].valueText;
						//如果当前语言存在，则去当前语言
						if(valueText.hasOwnProperty(_xform_main_data_custom_currentLang)){
							html += valueText[_xform_main_data_custom_currentLang];
						}else{
							//否则选择官方语言
							var officialArray = _xform_main_data_custom_officialLang.trim().split("|");
							var official = officialArray[1].toUpperCase();
							html += valueText[official];
						}
						html += "</option>";
					}
					html += "</select>";
				}else{
					alert("${lfn:message('sys-xform-maindata:sysFormMainDataCustom.warningNullCascade')}");
					return;
				}
				html += "</td>";
				html += xform_main_data_custom_buildCommonTrHtml(value);
				
				$(newTr).html(html);
			}else if(type == 'false'){
				var newTr = $table[0].insertRow($table[0].rows.length);
				$(newTr).addClass("xform_main_data_custom_contentTr");
				//行序号
				var rowIndex = newTr.rowIndex.toString();
				var html = "";
				//序号
				html += "<td style='text-align:center;'><span class='xform_main_data_custom_tdIndex'>"+ rowIndex +"</span></td>";
				html += xform_main_data_custom_buildCommonTrHtml(value);
				$(newTr).html(html);
			}
		}
	}
	
	function xform_main_data_custom_buildCommonTrHtml(value){
		var html = "";
		var hasValue = false;
		if(value){
			hasValue = true
		}
		//显示值
		html += xform_main_data_custom_buildValueTextHtml(hasValue,value);
		//实际值
		html += "<td><input class='inputsgl' type='text' name='extendDataValue' subject='实际值'  validate='required' value='"+ (hasValue?value.value:'') +"'/><span class='txtstrong'>*</span></td>";
		//排序号
		html += "<td><input class='inputsgl' type='text' name='extendDataOrder' value='"+ (hasValue?value.order:'') +"'/></td>";
		var delVar = "${lfn:message('sys-xform-maindata:sysFormMainDataCustom.del')}";
		//删除
		html += "<td style='text-align:center;'><a href='javascript:void(0);' onclick='xform_main_data_custom_delTrItem(this);' style='color:#1b83d8;'>"+ delVar +"</a></td>";
		return html;
	}
	
	//删除一行
	function xform_main_data_custom_delTrItem(dom){
		var $table = $(dom).closest('table');
		$(dom).closest("tr").remove();
		//更新序号
		xform_main_data_custom_updateIndex($table);
	}
	
	function xform_main_data_custom_delAllTr(){
		//清空级联对象的内容
		$('#xform_main_data_custom_extendDataTable_hasSuper tr:not(:first)').remove();
	}
	
	//更新table的序号
	function xform_main_data_custom_updateIndex($table){
		var rows = $table[0].rows;
		for(var i = 0;i < rows.length;i++){
			var row = rows[i];
			$(row).find("span.xform_main_data_custom_tdIndex").html(row.rowIndex);
		}
	}
	
	Com_Parameter.event["submit"].push(xform_main_data_custom_beforeSubmitValidate);
	
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
	
	//提交之前处理显示值的多语言
	function xform_main_data_custom_detailWithValueText($row){
		var valueTextInputs = $row.find("input[name^='extendDataValueText_']");
		var valueText = {};
		for(var i = 0;i < valueTextInputs.length;i++){			
			var valueTextInput = valueTextInputs[i];
			valueText[$(valueTextInput).attr("data-lang")] = $(valueTextInput).val();
		}
		return valueText;
	}
	
	//提交前校验
	function xform_main_data_custom_beforeSubmitValidate(){
		if(!validateKeyUnique()){
			return false;
		}
		//解析合并数据
		var isCascade = $("input[name='isCascade']:checked").val();
		var extendDataArray = [];
		if(isCascade == 'true'){
			var rows = $('#xform_main_data_custom_extendDataTable_hasSuper tr:not(:first)');			
			for(var i = 0;i < rows.length;i++){
				var extendData = {};
				extendData.cascade = $(rows[i]).find("select[name='extendDataSelect'] option:selected").val();
				extendData.valueText = xform_main_data_custom_detailWithValueText($(rows[i]));
				extendData.value = $(rows[i]).find("input[name='extendDataValue']").val();
				extendData.order = $(rows[i]).find("input[name='extendDataOrder']").val();
				extendDataArray.push(extendData);
			}
		}else if(isCascade == 'false'){
			//清空级联对象
			$("input[name='cascadeCustomId']").val("");
			var rows = $('#xform_main_data_custom_extendDataTable tr:not(:first)');			
			for(var i = 0;i < rows.length;i++){
				var extendData = {};
				extendData.valueText = xform_main_data_custom_detailWithValueText($(rows[i]));
				extendData.value = $(rows[i]).find("input[name='extendDataValue']").val();
				extendData.order = $(rows[i]).find("input[name='extendDataOrder']").val();
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
		//数组排序
		extendDataArray.sort(function(a,b){
			if(a.order == '' || b.order == ''){
				return 1;
			}
			return a.order - b.order;
		});
		/* //兼容多语言
		var extendDataJSON = {};
		
		//中文
		extendDataJSON[_xform_main_data_custom_language] = extendDataArray; */
		$("input[name='fdExtendData']").val(JSON.stringify(extendDataArray));
		
		return true;
	}
	
	function xform_main_data_custom_submit(method){
		Com_Submit(document.sysFormMainDataCustomForm, method);
	}
	
	Com_AddEventListener(window,'load',xform_main_data_custom_initVar);
	
	//初始化数据，主要用于edit编辑页面
	function xform_main_data_custom_initVar(){
		var isOldVersion = '${sysFormMainDataCustomForm.isOldVersion}';
		if(!isOldVersion || isOldVersion == 'true'){
			seajs.use(['lui/dialog'], function(dialog) {
				/*跳转的时候就对数据字典进行处理，以字符串的形式存放在弹出框里面，以免每次查询的时候需要查数据字典*/
				var url = '/sys/xform/maindata/main_data_custom/xFormMainDataCustom_oldVersionTip.jsp';
				var height = document.documentElement.clientHeight * 0.33;
				var width = document.documentElement.clientWidth * 0.55;
				var dialog = dialog.iframe(url,"警告",null,{width:width,height : height});
			});
		}
		var isCascade = '${sysFormMainDataCustomForm.isCascade}';
		xform_main_data_custom_showCascadeSelect(null,isCascade);
		var $table;
		if(isCascade == 'true'){
			$table = $("#xform_main_data_custom_extendDataTable_hasSuper");
			xform_main_data_custom_cascadeCustomDict = $.parseJSON('${sysFormMainDataCustomForm.cascadeCustomExtendData}'); 
		}else if(isCascade == 'false'){
			$table = $("#xform_main_data_custom_extendDataTable");
		}
		var fdExtendData = '${sysFormMainDataCustomForm.fdExtendData}';
		if(fdExtendData){
			var extendDataArray = $.parseJSON(fdExtendData); 
			for(var i = 0;i < extendDataArray.length;i++){
				xform_main_data_custom_addItem(isCascade,null,$table,extendDataArray[i]);
			}
		}
		
	}
	
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>