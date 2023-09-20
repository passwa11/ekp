<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ sysXformAuditshowForm.method_GET == 'edit' }">
				 <bean:message key="button.edit"/> - <bean:message bundle="sys-xform-base" key="table.sysXformAuditshow"/>
			</c:when>
			<c:when test="${ sysXformAuditshowForm.method_GET == 'add' }">	
				${lfn:message('button.add')} - <bean:message bundle="sys-xform-base" key="table.sysXformAuditshow"/>
			</c:when>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
	<script>
		function confirmAdd(msg){
			var add = confirm("<bean:message bundle='sys-xform-base' key='sysXformAuditshow.add_msg'/>");
			return add;
		}
		function confirmedit(){
			var area = document.getElementsByName('fdContent')[0];
			var text = area.value;
			var info = ["{msg}","{person}","{dept}","{post}","{width}","{handwrite}","{attachment}","{time}","{date}","{picPath}"];
			for(var i = 0;i<info.length;i++){
				var index1 = text.indexOf(info[i]);
				var index2 = text.lastIndexOf(info[i]);
				if(index1!==index2) {
					var tip1 = "<bean:message bundle='sys-xform-base' key='sysXformAuditshow.check_msg2'/>";
					var tip2 = "<bean:message bundle='sys-xform-base' key='sysXformAuditshow.check_msg3'/>";
					alert(tip1 + ",$" + info[i] + tip2);
					return false;
				}
			}
			return true;
		}
	</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysXformAuditshowForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="if(!confirmedit())return;Com_Submit(document.sysXformAuditshowForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysXformAuditshowForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="if(!confirmedit()||!confirmAdd())return;Com_Submit(document.sysXformAuditshowForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="if(!confirmedit()||!confirmAdd())return;Com_Submit(document.sysXformAuditshowForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do">
 
 <br/>
<p class="txttitle"><bean:message bundle="sys-xform-base" key="table.sysXformAuditshow"/></p>

<center>

<table class="tb_simple" width=95% style="table-layout: fixed;"> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-base" key="sysXformAuditshow.fdName"/>
		</td><td width="85%" align="left">
			<xform:text property="fdName" style="width:85%" validators="checkName"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-base" key="sysXformAuditshow.fdOrder"/>
		</td><td width="85%" align="left">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-base" key="sysXformAuditshow.fdContent"/>
		</td><td width="85%" align="left">
			<table width="85.6%">
			<tr>
				<td style="width:240px;vertical-align:top;border-top:1px solid #b4b4b4;border-bottom:1px solid #b4b4b4;border-left:1px solid #b4b4b4;">
					<div id="audtishowtree" style="width:240px;overflow:auto;" class="treediv"></div>
				</td>
				<td style="width:40%;border-bottom:1px solid #b4b4b4;">
					<xform:textarea property="fdContent" style="width:100%;height:370px;word-break:break-all;font-size:20px;border-bottom:hidden;" htmlElementProperties='wrap="off"'></xform:textarea>
				</td>
				<td onclick="preview()" style="border-top:1px solid #b4b4b4;border-bottom:1px solid #b4b4b4;border-right:1px solid #b4b4b4;">
					<div align="center" id="previewDiv" style="margin-left: 8px;width:97%;overflow:auto;"><br/><br/><br/><br/><br/><br/><br/><br/><bean:message bundle="sys-xform-base" key="sysXformAuditshow.preview_msg"/></div>
					<div align="center" id="refreshDiv" style="background-image: url('${LUI_ContextPath}/sys/xform/designer/auditshow/css/top_bar_bg.png');">
						<ui:button text="${ lfn:message('button.refresh') }" style="width:70px" onclick="preview();"></ui:button>
					</div>
				</td>
			</tr>
			</table>
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="fdIsenabled" />
<html:hidden property="method_GET" />
<script>
	var _validation = $KMSSValidation();
	_validation.addValidator('checkName',
		"<bean:message bundle='sys-xform-base' key='sysXformAuditshow.check_msg'/>",
		function(v,e,o){
		var bool = true;
		if(v=='${sysXformAuditshowForm.fdName }'){
			return bool;
		}
		$.ajax({async:false//要设置为同步的，要不CheckUserName的返回值永远为false
	        ,url:'<c:url value="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do?method=valiName"/>',data:{name:v}
	        ,success:function(data){
	        	if(data=='false'){
	    			bool = false;
	    		}
	        }});
		return bool;
	});
	
	Com_IncludeFile("treeview.js");
	$(document).ready(function(){
		var h = window.screen.height;
		$("#audtishowtree").css("height",(0.5*h+8)+"px");
		$("#previewDiv").css("height",(0.5*h-12)+"px");
		$("#refreshDiv").css("height",30+"px");
		var area = document.getElementsByName('fdContent')[0];
		area.style.height = (0.5*h+17)+'px';
		LKSTree = new TreeView("LKSTree", "<bean:message bundle='sys-xform-base' key='sysXformAuditshow.style'/>", document.getElementById("audtishowtree"));
		var n1,n2,n3;
		n1 = LKSTree.treeRoot;
		n2 = n1.AppendChild(
				"<bean:message bundle='sys-xform-base' key='sysXformAuditshow.vars'/>"
			);
		n2.FetchChildrenNode = getNodes;
		n3 = n1.AppendChild(
				"<bean:message bundle='sys-xform-base' key='sysXformAuditshow.model'/>"
			);
		n3.FetchChildrenNode = getModel;
		LKSTree.ExpandNode(n2);
		LKSTree.EnableRightMenu();
		LKSTree.Show();
	});
	
	function getNodes(){
		var nodes = setNodes();
		for(var i=0; i < nodes.length; i++){
			var pNode = this;
			var node;
			node = Tree_GetChildByText(pNode, nodes[i].text);
			if(node==null){
				node = pNode.AppendChild(nodes[i].text);
			}	
			node.action = setSelect;
			node.value =nodes[i].value;
			node.title = nodes[i].title;
		}
	}

	function setNodes(){
		var nodes = [{'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.msg"/>",'value':'\${msg}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.msg"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.person"/>",'value':'\${person}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.person"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.dept"/>",'value':'\${dept}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.dept"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.post"/>",'value':'\${post}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.post"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.width"/>",'value':'\${width}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.width"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.handwrite"/>",'value':'\${handwrite}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.handwrite"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.attachment"/>",'value':'\${attachment}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.attachment"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.time"/>",'value':'\${time}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.time"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.date"/>",'value':'\${date}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.date"/>"},
		            {'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.operation"/>",'value':'\${operation}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.operation"/>"}];
		
		<kmss:ifModuleExist path="/km/signature">
			nodes.push({'text':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.picPath"/>",'value':'\${picPath}','title':"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.picPath"/>"});
		</kmss:ifModuleExist>
		return nodes;
	}
	
	function getModel(){
		var models = setModel();
		for(var i=0; i < models.length; i++){
			var pNode = this;
			var node;
			node = Tree_GetChildByText(pNode, models[i].viewName);
			if(node==null){
				node = pNode.AppendChild(models[i].viewName);
			}	
			node.action = setSelect;
			node.value =models[i].viewValue;
			node.title = models[i].viewName;
		}
	}
	
	function setModel(){
		var extStyle;
		$.ajax({
			  url: "${LUI_ContextPath}/sys/xform/designer/auditshow/sys_xform_auditshow/sysXformAuditshow_style.jsp",
			  type:'GET',
			  async:false,//同步请求
			  success: function(json){
			     extStyle=json;
			  },
			  dataType: 'json'
			});
		return extStyle;
	}
	
	function setSelect(){
		var area = document.getElementsByName("fdContent")[0];
		var text = area.value;
		var info = this.value;
		var index1 = text.indexOf(info);
		if(index1>-1) {
			alert("<bean:message bundle='sys-xform-base' key='sysXformAuditshow.check_msg2'/>");
			return;
		}
		insertText(area, this);
	}

	function insertText(obj, node) {
		obj.focus();
		if (document.selection) {
			var sel = document.selection.createRange();
			sel.text = node.value;
		} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd ==='number') {
			var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;   
			obj.value = tmpStr.substring(0, startPos) + node.value + tmpStr.substring(endPos, tmpStr.length);
			cursorPos += node.value.length;
			obj.selectionStart = obj.selectionEnd = cursorPos;
		} else {
			obj.value += node.value;
		}
	}
	
	function preview(){
		/* event.cancelBubble = true;
		if (event.stopPropagation) {event.stopPropagation();} */
		var domElement = document.getElementById('previewDiv');
		domElement.align = '';
		var area = document.getElementsByName('fdContent')[0];
		var map={"msg":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.msg2"/>",
				"person":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.person2"/>",
				"dept":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.dept2"/>",
				"post":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.post2"/>",
				"width":'',
				"handwrite":'<img height="30px" width="40px" src="../../designer/style/img/auditshow/handwrite.png">',
				"attachment":'<img src="../../designer/style/img/auditshow/attachment.png">',
				"time":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.time2"/>",
				"date":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.date2"/>",
				"picPath":'<img src="../../designer/style/img/auditshow/defaultSig2.png">',
				"operation":'<bean:message bundle="sys-xform-base" key="sysXformAuditshow.operation2"/>'};
		var text = area.value.replace(/\\$\{(\w+)\}/g,function($1,$2){
			return map[$2];
		});
		domElement.innerHTML = text;
		/* document.body.onclick = function(){
			event.cancelBubble = true;
			if (event.stopPropagation) {event.stopPropagation();}
			domElement.align = 'center';
			domElement.innerHTML = '<bean:message bundle="sys-xform-base" key="sysXformAuditshow.preview_msg"/>';
		}; */
	}
</script>
</html:form>

	</template:replace>
</template:include>