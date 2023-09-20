<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
		<style>.TVN_TreeNode_Current{background-color:#FFFFCC; padding:2px; border:1px solid #999999;}</style>
		<script>
			Com_IncludeFile("treeview.js|data.js");
		</script>
		<br/>
		<p class="txttitle">
			${lfn:message("eop-basedata:tips.selectCity")}
		</p>
		<table class="tb_normal" width="98%" style="height:450px;">
			<tr>
				<%--资源列表--%>
				<td colspan="1" style="vertical-align:top;" width="25%">
					<div id="treeDiv" style="height:480px;overflow-y:scroll;"></div>
					<script>
						function generateTree(){
							LKSTree = new TreeView(
								"LKSTree",
								'${lfn:message("eop-basedata:table.eopBasedataArea")}',
								document.getElementById("treeDiv")
							);
							LKSTree.ClickNode = function(id){
								node = Tree_GetNodeByID(LKSTree.treeRoot, id);
								var id = node.parameter[0];
								LUI('listview1').source.setUrl('/eop/basedata/eop_basedata_city/eopBasedataCity.do?method=listCity&fdCompanyId=${param.fdCompanyId}&fdId='+id);
								LUI('listview1').source.get();
								$("#listview1_").show();
								LKSTree.SetCurrentNode(node);
							};
							var n1 = LKSTree.treeRoot;
							n1.parameter = [''];
							var data = new KMSSData();
							data.AddBeanData("eopBasedataAreaService&fdCompanyId=${param.fdCompanyId}");
							data = data.GetHashMapArray();
							for(var i=0;i<data.length;i++){
								n1.AppendURLChild(data[i].text,data[i].value);
							}
							LKSTree.EnableRightMenu();
							LKSTree.Show();
						};
						generateTree();
					</script>
				</td>
				<td colspan="3" width="75%">
						<div data-lui-type="lui/search_box!SearchBox">
							<script type="text/config">
								{
									placeholder: "${lfn:message('eop-basedata:tips.selectCityTip')}",
									width: '90%'
								}
							</script>
							<ui:event event="search.changed" args="evt">
								LUI('listview1').tableRefresh({criterions:[{key:"fdName", value: [evt.searchText]}]});
							</ui:event>
						</div>
						<div id="listview1_">
						<list:listview id="listview1" style="height:380px;overflow-y:scroll;">
						<ui:source type="AjaxJson" >
							{url:'/eop/basedata/eop_basedata_city/eopBasedataCity.do?method=listCity&fdCompanyId=${param.fdCompanyId }'}
						</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.listtable"  name="columntable"  onRowClick="selectSubmit('!{fdId}','!{fdArea}','!{fdCity}');">
							<list:col-serial title="NO"></list:col-serial>
							<list:col-auto props="fdArea;fdType;fdCity" ></list:col-auto>
						</list:colTable>
					</list:listview>
					<list:paging></list:paging>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><ui:button text="${lfn:message('button.close') }" onclick="selectCancel()"/></td>
			</tr>
		</table>
		<script>
			//提交
			window.selectSubmit=function(fdId,fdArea,fdCity){
				$dialog.hide({id:fdId,name:fdCity+"("+fdArea+")"});
			};
			//取消
			window.selectCancel=function(){
				$dialog.hide(null);
			};
		</script>
	</template:replace>
</template:include>
