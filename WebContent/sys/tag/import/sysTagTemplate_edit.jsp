<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>

<c:set var="sysTagTemplate_Key" value="${param.fdKey}" />
<c:set var="sysTagTemplateFormPrefix"
	value="sysTagTemplateForms.${param.fdKey}." />
<c:set var="sysTagTemplateForm"
	value="${requestScope[param.formName].sysTagTemplateForms[param.fdKey]}" />
<Script type="text/javascript">
	function afterSelectValue(rtnVal){
		if(rtnVal==null)
			return;
		var names=document.getElementsByName("${lfn:escapeJs(sysTagTemplateFormPrefix)}fdTagNames")[0].value;
		var ids=document.getElementsByName("${lfn:escapeJs(sysTagTemplateFormPrefix)}fdTagIds")[0].value;	
		var nameStr=new Array();
		nameStr=names.split(" ");
		var name='';
		var id='';
		for(var i=0;i<rtnVal.GetHashMapArray().length;i++){
			var newName=rtnVal.GetHashMapArray()[i]['name'];
			var newId=rtnVal.GetHashMapArray()[i]['id'];
			if(i==0){
				if(names==''){
					name=newName;
					id=newId;
				}else{
					//如果有原值，则判断原值中是否已存在此标签名，如果不存在，则添加
					var isExist=1;
					for(var j=0;j<nameStr.length;j++){
						var oldName=nameStr[j];
						if(newName==oldName){
							isExist=0;
						}
					}
					if(isExist==1){
						name=newName;
						id=newId;
					}
				}
				
			}else{
				if(names==''){
					name=name+' '+newName;
					id=id+' '+newId;
				}else{
					var isExist=1;
					for(var j=0;j<nameStr.length;j++){
						var oldName=nameStr[j];
						if(newName==oldName){
							isExist=0;
						}
					}
					if(isExist==1){
						name=name+' '+newName;
						id=id+' '+newId;
					}
				}
				
 			}
		}
		document.getElementsByName("${lfn:escapeJs(sysTagTemplateFormPrefix)}fdTagNames")[0].value=names+' '+name;
		document.getElementsByName("${lfn:escapeJs(sysTagTemplateFormPrefix)}fdTagIds")[0].value=ids+' '+id;
	}
	//处理标签
	function modifyTag(textTag){
		if(textTag){//标签不为空时做处理
			var addTag=textTag.split(/;|；/);
			var newTag = [];
			for(var i=0;i<addTag.length;i++){
				//将新标签做判空和除重复处理   后放入newTag中
				if($.trim(addTag[i])!=""&&$.inArray($.trim(addTag[i]), newTag) ==-1){
					newTag.push(addTag[i]);
				}
			}
		}
		return textTag;
	}
	
	//选择标签
	function choiceTag(){
		var key = "${lfn:escapeHtml(sysTagTemplateFormPrefix)}";
		var url = "/sys/tag/import/addTag.jsp?mulSelect=true&addTagSign=1";	
		var textTag = $("input[name='" + key +"fdTagNames']").val();
		
		var top = Com_Parameter.top || window.top;
		top.window.selectTagNames = modifyTag(textTag);//标签处理
		
		seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
		dialog.iframe(url,
				lang["sysTag.choiceTag"], null, {
			width : 900,
			height : 550,
			buttons : [
				{
					name : ui_lang["ui.dialog.button.ok"],
					value : true,
					focus : true,
					fn : function(value,_dialog) {
						if(_dialog.frame && _dialog.frame.length > 0){
							var _frame = _dialog.frame[0];
							var contentWindow = $(_frame).find("iframe")[0].contentWindow;
							if(contentWindow.onSubmit()) {
								var datas = contentWindow.onSubmit().slice(0);
								if(datas.length>=0){
									selectWordCallBack(datas,key);	

								setTimeout(function() {
									_dialog.hide(value);
									}, 200);
									
								}
							}
						}
						
					}
				}
				,{
					name :ui_lang["ui.dialog.button.cancel"],
					value : false,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				}
			]	
		});
		});
	}
	//处理接收的长字符串，分割成各个标签
    // #137040 id也要处理，不然重新加载会有问题
	function selectWordCallBack(datas,key) {
	    // console.log("datas=>",datas);
        // console.log("key=>",key);
        if (datas != null && typeof (datas) != "undefined") {
			var item;
			var idstr="";
			var str="";
			for(var i=0; i<datas.length; i++){
				item=datas[i];
                idstr=idstr+item.fdId+";";
				str=str+item.fdName+";";
			}
            idstr = idstr.substring(0,idstr.length-1);
            str = str.substring(0,str.length-1);
            $("input[name='" + key +"fdTagIds']").val(idstr);
            $("input[name='" + key +"fdTagNames']").val(str);
		}
	}
</Script>
<c:set var="useTab" value="true"></c:set>
<c:if test="${param.useTab!=null && param.useTab==false}">
	<c:set var="useTab" value="false" />
</c:if>
<c:if test="${ useTab=='true'}">
	<tr>
		<td class="td_normal_title" width=15% nowrap><bean:message bundle="sys-tag" key="table.sysTagTags" />
		</td>
		<td colspan="3">
</c:if>
	<html:hidden property="${lfn:escapeHtml(sysTagTemplateFormPrefix)}fdId" />
	<html:hidden property="${lfn:escapeHtml(sysTagTemplateFormPrefix)}fdKey" value="${HtmlParam.fdKey}" />
	<html:hidden property="${lfn:escapeHtml(sysTagTemplateFormPrefix)}fdModelName" />
	<html:hidden property="${lfn:escapeHtml(sysTagTemplateFormPrefix)}fdModelId" />
	<input name="${lfn:escapeHtml(sysTagTemplateFormPrefix)}fdTagIds"
		type="hidden" value="<c:out value='${sysTagTemplateForm.fdTagNames}'/>" /> 
	<html:text property="${lfn:escapeHtml(sysTagTemplateFormPrefix)}fdTagNames" readonly="${param.readonly?'true':'false'}"
	styleClass="inputsgl" style="width:85%" />
<%-- <a href="#"
	onclick='Dialog_TreeList(true,"sysTagTemplateForms.${param.fdKey}.fdTagIds","sysTagTemplateForms.${param.fdKey}.fdTagNames"," ","sysTagCategorTreeService&type=1&fdCategoryId=!{value}","<bean:message key='sysTagTag.tree' bundle='sys-tag'/>","sysTagByCategoryDatabean&type=getTag&fdCategoryId=!{value}",afterSelectValue, "sysTagByCategoryDatabean&key=!{keyword}&type=search")'>
	<bean:message key="dialog.selectOther" /></a> --%>
<a href="#" onclick="choiceTag()">
	<bean:message key="dialog.selectOther" />
</a>
<c:if test="${ useTab=='true'}">
		</td>
	</tr>
</c:if>

