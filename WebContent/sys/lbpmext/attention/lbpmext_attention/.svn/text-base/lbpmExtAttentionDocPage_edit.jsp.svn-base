<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/lbpmext/attention/resource/css/rela_edit.css">
		<script src="${KMSS_Parameter_ContextPath}resource/js/common.js"></script>
		<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
		<script type="text/javascript">
		var Selected_Data = new KMSSData().UniqueTrimByKey("id");
		var LKSTree,n1,n2;
		var isCheckAllFlag = false;//是否全选flag
		Tree_IncludeCSSFile();
		function generateTree(){
			LKSTree = new TreeView(
					"LKSTree",
					"",
					document.getElementById("treeDiv")
				);
			n1 = LKSTree.treeRoot;
			n2 = n1.AppendBeanData("lbpmExtAttentionScopeTreeService&top=true");/* sysattentionDocService */
			LKSTree.ClickNode = TreeFunc_ClickNode;
			LKSTree.DrawNode = TreeFunc_DrawNode;
			LKSTree.DrawNodeIndentHTML = TreeFunc_DrawNodeIndentHTML;
			LKSTree.DrawNodeOuterHTML = TreeFunc_DrawNodeOuterHTML;
			LKSTree.DrawNodeInnerHTML = TreeFunc_DrawNodeInnerHTML;
			LKSTree.ExpandNode = TreeFunc_ExpandNode;
			LKSTree.GetCheckedNode = TreeFunc_GetCheckedNode;
			LKSTree.SelectNode = TreeFunc_SelectNode;
			LKSTree.SetCurrentNode = TreeFunc_SetCurrentNode;
			LKSTree.SetNodeChecked = TreeFunc_SetNodeChecked;
			LKSTree.SetTreeRoot = TreeFunc_SetTreeRoot;
			LKSTree.OnNodeQueryClick = Dialog_OnNodeQueryClick;
			LKSTree.OnNodeCheckedPostChange = Dialog_OnNodeCheckedPostChange;
			LKSTree.Show = TreeFunc_Show;
			LKSTree.isShowCheckBox = true;
			LKSTree.isMultSel = true;
		}
		function modifyNodeInfo(root, refreshRoot){
			if(refreshRoot){
				if(root.value!=null && Selected_Data.IndexOf("id", root.value)>-1)
					root.isChecked = true;
				if(root.FetchChildrenNode!=null){
					if(root.FetchChildrenNode==Dialog_FetchChildrenNode){
						root.Dialog_FetchChildrenNode = root.parent.Dialog_FetchChildrenNode;
					}else{
						root.Dialog_FetchChildrenNode = root.FetchChildrenNode;
						root.FetchChildrenNode = Dialog_FetchChildrenNode;
					}
				}
			}
			for(var node = root.firstChild; node!=null; node=node.nextSibling)
				modifyNodeInfo(node, true);
		}
		function Dialog_FetchChildrenNode(){
			this.Dialog_FetchChildrenNode();
			modifyNodeInfo(this, false);
		}
		function Com_DialogReturnValue(){
			var rtnVal = Selected_Data.GetHashMapArray();
			if(rtnVal.length==0 && dialogObject.notNull)
				alert(Com_Parameter.DialogLang.requiredSelect);
			else
				parent.Com_DialogReturn(rtnVal);
		}
		function Dialog_OnNodeQueryClick(node){
			node.action = null;
			node.parameter = null;
		}
		function Dialog_OnNodeCheckedPostChange(node){
			var i = Selected_Data.IndexOf("id", node.value);
			if(node.isChecked){
				if(i==-1){
					Selected_Data.AddHashMap({id:node.value, name:node.text});
					refreshSelectedList();
					cancelParentNodeChecked(node);
					cancelChildrenNodeChecked(node);
				}
			}else{
				if(i>-1){
					Selected_Data.Delete(i);
					refreshSelectedList();
				}
			}
			//判断是否全部选中
			if(!isCheckAllFlag){
				isSelectAll();
			}
		}
		//选中子节点，将子节点选中的父节点取消选中
		function cancelParentNodeChecked(node){
			var parent = node.parent;
			if(parent != LKSTree.treeRoot){
				if(parent.isChecked){
					LKSTree.SetNodeChecked(parent, false);
				}
				cancelParentNodeChecked(parent);
			}
		}
		//选中父节点，将选中的子节点取消选中
		function cancelChildrenNodeChecked(node){
			for(var now=node.firstChild; now!=null; now=now.nextSibling){
				if(now.isChecked){
					LKSTree.SetNodeChecked(now, false);
				}
				cancelChildrenNodeChecked(now);
			}
		}
		function refreshSelectedList(){
			try{
				putToUl(Selected_Data,document.getElementsByName("F_SelectedUl")[0], "id", "name");
			}catch(e){
			}
		}
		function onSelectedDataDelete(){
			var nodes = LKSTree.GetCheckedNode();
			for(var i=0; i<nodes.length; i++){
				if(Selected_Data.IndexOf("id", nodes[i].value)==-1){
					LKSTree.SetNodeChecked(nodes[i], false);
				}
			}
		}
		
		function putToUl(data,fieldName, valueKey, nameKey){
			function addSpan(li,text,flag){
				 var span=document.createElement("span");
		         span.innerHTML=text;
		         if(flag){
		        	span.setAttribute("class","ul_cells_li_id_span");
		         }else{
		        	 span.setAttribute("class","ul_cells_li_name_span");
		         }
		         li.appendChild(span);
			}
			function addDelBtn(li){
				var span=document.createElement("span");
				span.setAttribute("class","ul_cells_li_btn_span");
				span.setAttribute("onclick","optionCancel(this,false)");
				li.appendChild(span);
			}
			data.Parse();
			if(nameKey==null)
				nameKey = valueKey;
			data.UniqueTrimByKey(valueKey, valueKey+":"+nameKey);
			var obj = typeof(fieldName)=="string"?document.getElementsByName(fieldName)[0]:fieldName;
			
			while(obj.hasChildNodes()){
				obj.removeChild(obj.firstChild);
		    }
			
			for(var i=0;i<data.data.length;i++){
				var li = document.createElement("li");
				addSpan(li,data.data[i][valueKey],true);
				addSpan(li,data.data[i][nameKey],false);
				addDelBtn(li);
				li.setAttribute("class","ul_cells_li");
				obj.appendChild(li);
			}
		}
		</script>
		   
		
		<div id="div_attention_Container" class="div_attention_Container">
			<div class="div_attention_wrapper div_attention_wrapper_clear">
				<div class="div_attention_wrapper_tree">
					<input type="hidden" name="fdIsTemplate" value="true" >
					<div class="div_attention_wrapper_tree_head">
						<div class="div_attention_wrapper_tree_head_can_selected">
							<span class="div_attention_wrapper_tree_head_can_selected_span">
								<bean:message key="dialog.optList"/>
							</span>
						</div>
						<div class="div_attention_wrapper_tree_head_checkAll">
							<label>
								<input id="checkAll" type="checkbox" onclick="selectAll(this)" >
								<span id='checkBoxAll' class="div_attention_wrapper_tree_head_checkAll_span">
									<bean:message key="dialog.addAll"/>
								</span>
							</label>
				 		</div>
					</div>
					<div class="div_attention_wrapper_tree_data">
						<div id="treeDiv" class="treediv"></div>
					</div>
					<script>generateTree();</script>
				</div>
				
				<div class="div_attention_wrapper_selected_list">
					<div class="div_attention_wrapper_selected_list_head">
						<div class="div_attention_wrapper_selected_list_head_selected">
							<span class="div_attention_wrapper_selected_list_head_selected_span">
								<bean:message  key="dialog.selList"/>
							</span>
						</div>
						<div class="div_attention_wrapper_selected_list_head_cancelAll">
							<label>
								<span id='cancelAll' class="div_attention_wrapper_selected_list_head_cancelAll_span" onclick="optionCancel(this,true);">
									<bean:message  key="button.cancel"/>
								</span>
							</label>
					 	</div>
					</div>
					
					<div>
						<ul name="F_SelectedUl" class="ul_cells">
						</ul>
					</div>
				</div>
			</div>
			
			<div>
				<center class="div_attention_opt_button">
					<input type="button" class="btnopt" value="<bean:message key="button.ok"/>" onclick="doOK();" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" class="btnopt" value="<bean:message key="button.close" />" onclick="Com_CloseWindow();" />
				</center>
			</div>
		</div>
		<%@ include file="lbpmExtAttentionDocPage_edit_script.jsp"%>
		<br>
	</template:replace>
</template:include>