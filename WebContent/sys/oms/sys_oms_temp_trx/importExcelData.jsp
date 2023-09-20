<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.util.LicenseUtil,com.landray.kmss.util.*" %>
<%@ page import="com.landray.kmss.constant.SysOrgConstant,com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService" %>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ page import="com.landray.kmss.sys.oms.temp.SysOmsExcelUtil" %>

<%@ include file="/sys/ui/jsp/common.jsp"%>

<%

%>
<style type="text/css">
	.profile_orgIO_content .title.title01 {
     	margin-bottom: 0px !important; 
	}
	
	.import_check{
		height: 28px;
    	width: 55px;
    	border: 1px solid #9e9e9e70;
    	background-color: #37a8f5fc;
    	color: #fff;
	}
	
	.table tr td{
		padding: 4px 8px;
	}
	.table-first >tbody >tr >td{
		white-space: nowrap;
    	padding: 0 10px;
	}

</style>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/org/io/import.css"/>
		<script type="text/javascript">
			Com_IncludeFile("data.js");
		</script>
	</template:replace>
	<template:replace name="content">
		<form name="templet" action="${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsExcelImport.do?method=downloadTemplet" method="post">
		</form>
		<div class="profile_orgIO_wrapper">
			<!-- <h2 class="profile_orgIO_tips"></h2> -->
			<div class="profile_orgIO_content" style="padding-left: 50px;">
			<table width="100%" class="table-first">
				<tr style="height: 60px;">
					<td class="td_normal_title">下载标准Excel：</td>
					<td >
						<p class="title title01">
							<bean:message bundle="sys-oms" key="sys.oms.excel.download.info1" />
							<a href="javascript:void(0)" class="profile_orgIO_link" onclick="downloadTemplet();"><bean:message bundle="sys-oms" key="sys.oms.excel.download.info2" /></a>，
							<bean:message bundle="sys-oms" key="sys.oms.excel.download.info3" />
						</p>
					</td>	
				</tr>
				<tr>
				<td>上传Excel：</td>
				<td>
				<form id="sysOmsExcelImportForm" name="sysOmsExcelImportForm" action="${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsExcelImport.do?method=importData" method="post" enctype="multipart/form-data">
					<!--上传文件 开始-->
					<div class="profile_orgIO_fileUpload_item">
						<div class="fileUpload">
							<div class="file_wrap">
								<input class="input_file" id="input_file" type="file" name="file" accept=".xls,.xlsx"  onChange="fileChange(this);"/>
								<label class="profile_orgIO_uploadBtn" for="input_file">
									<bean:message bundle="sys-oms" key="sys.oms.excel.select.file" />
								</label>
								<span class="profile_orgIO_fileTitle"><bean:message bundle="sys-oms" key="sys.oms.excel.select" /></span>
							</div>
						</div>
					</div>
					<div id="check" style="display: inline-table;position: absolute;margin-left: 20px; display:none;">
						 <input class="import_check" type="button" value="上传" onclick="checkData();">
					</div>
					<!--上传文件 结束-->
					<tr>
					<td style="padding-top: 25px;">人员排序方式：</td>
					<td style="padding-top: 25px;">
						<xform:radio property="fdPersonIsAsc" showStatus="edit" >
							<xform:simpleDataSource value="1">顺序</xform:simpleDataSource>
							<xform:simpleDataSource value="0">逆序</xform:simpleDataSource>	
						</xform:radio>
						<div style="display: contents;color:#9e9e9e;">(顺序为：序号小的优先，逆序为：序号大的优先)</div>
					</td>
				</tr>
				<tr>
					<td style="padding-top: 10px;">部门排序方式：</td>
					<td style="padding-top: 10px;">
						<xform:radio property="fdDeptIsAsc" showStatus="edit">
							<xform:simpleDataSource value="1">顺序</xform:simpleDataSource>
							<xform:simpleDataSource value="0">逆序</xform:simpleDataSource>	
						</xform:radio>
						<div style="display: contents;color:#9e9e9e;">(顺序为：序号小的优先，逆序为：序号大的优先)</div>
					</td>
				</tr>
				<tr id="personIsMain" style="display: none;">
					<td style="padding-top: 10px;">人员所属部门是否生效：</td>
					<td style="padding-top: 10px;">
						<xform:radio property="fdPersonIsMainDept" showStatus="edit">
							<xform:simpleDataSource value="1">是</xform:simpleDataSource>
							<xform:simpleDataSource value="0">否</xform:simpleDataSource>	
						</xform:radio>
						<div style="color:#9e9e9e;">(是：人员信息中的人员所属部门字段生效；否：从人员部门关系中自动选择一个部门作为人员主部门。)</div>
					</td>
				</tr>
				<tr id="dpIsFull" style="display: none;">
					<td style="padding-top: 10px;">人员部门关系是否为全量：</td>
					<td style="padding-top: 10px;">
						<xform:radio property="fdPersonDeptIsFull" showStatus="edit">
							<xform:simpleDataSource value="1">是</xform:simpleDataSource>
							<xform:simpleDataSource value="0">否</xform:simpleDataSource>	
						</xform:radio>
						<div style="color:#9e9e9e;">(是：导入的人员部门关系会覆盖系统中已存在的关系；否：如果人员部门关系信息中的是否有效字段为0，则系统会删除该条关系，如果为1，则新增该条关系，系统原有的关系不变。)</div>
					</td>
				</tr>
				<tr id="ppIsFull" style="display: none;">
					<td style="padding-top: 10px;">人员岗位关系是否为全量：</td>
					<td style="padding-top: 10px;">
						<xform:radio property="fdPersonPostIsFull" showStatus="edit">
							<xform:simpleDataSource value="1">是</xform:simpleDataSource>
							<xform:simpleDataSource value="0">否</xform:simpleDataSource>	
						</xform:radio>
						<div style="color:#9e9e9e;">(是：导入的人员岗位关系会覆盖系统中已存在的关系；否：如果人员岗位关系信息中的是否有效字段为0，则系统会删除该条关系，如果为1，则新增该条关系，系统原有的关系不变。)</div>
					</td>
				</tr>
				</form>
				</td>
				</tr>
				<tr>
				<td style="padding-top: 25px;">
				 	数据校验结果:
				</td>
				<td id="fileData" style="padding-top: 25px;">
					<div id="fileDataDiv" style="width: 1000px; overflow-x: auto; overflow-y: hidden;">
					<table id="deptFileData" class="table" style="text-align: center;width: 100%; display: none;white-space: nowrap;">
					</table>
					<table id="personFileData" class="table" style="text-align: center;margin-top: 20px; display: none; width: 100%;white-space: nowrap;">
						
					</table>
					<table id="postFileData" class="table" style="text-align: center;margin-top: 20px;display: none;width: 100%;">
						
					</table>
					<table id="postPersonFileData" class="table" style="text-align: center;margin-top: 20px;display: none;width: 100%;">
						
					</table>
					<table id="deptPersonFileData" class="table" style="text-align: center;margin-top: 20px;display: none;width: 100%;">
						
					</table>
					</div>	
				</td>
				</tr>
				<tr class="import" style="display: none;">
					<td></td>
					<td>
					<div class="profile_orgIO_btn_wrap">
						<a href="javascript:void(0)" id="importOrg" class="profile_orgIO_btn btn_def" onclick="importOrgdept();"><bean:message bundle="sys-profile" key="sys.profile.orgImport.import" /></a>
						<a href="javascript:void(0)" class="profile_orgIO_btn btn_gray" onclick="reset();"><bean:message key="button.cancel" /></a>
					</div>
					</td>
				</tr>
				<tr>
					<td style="padding-top: 25px;">导入结果：</td>
					<td style="padding-top: 25px;">
						<p class="profile_orgIO_result">
							${resultMsg}
						</p>
					</td>
				</tr>
				 
				</table>
			</div>
		</div>

	 	<script type="text/javascript">
		 	seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				window.importOrgdept = function() {
					
					
					var file = $('#sysOmsExcelImportForm input[name=file]');
					if (file.val() == "") {
						dialog.alert('<bean:message bundle="sys-profile" key="sys.profile.orgImport.empty.file" />');
					} else {
						alert("进行数据导入，在导入过程中请勿做其他操作！");
						$("#importOrg").css("pointer-events","none");//防止重复点击
						$("#sysOmsExcelImportForm").submit();
						// 开启进度条
						window.progress = dialog.progress(true);
						window._progress();
					}
					
				}
				window._progress = function () {
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("sysOrgImportXMLDataService");
					var rtn = data.GetHashMapArray()[0];
					
					if(window.progress) {
						var currentCount = parseInt(rtn.currentCount || 0);
						var allCount = parseInt(rtn.totalCount || 0);
						if(rtn.importState == 1 || currentCount >= allCount) {
							window.progress.hide();
						}
						// 设置进度值
						if(allCount == -1 || allCount == 0) {
							window.progress.setProgress(0);
						} else {
							window.progress.setProgressText('<bean:message bundle="sys-profile" key="sys.profile.orgImport.progress" />' + currentCount + '/' + allCount);
							window.progress.setProgress(currentCount, allCount);
						}
					}
					if(rtn.importState != 1) {
						setTimeout("window._progress()", 1000);
					}
				} 
				
				function fileChange(file) {
					var index = file.value.lastIndexOf("\\");
					var fileName = file.value.substring(index + 1);
					$("#check").hide();
					if(fileName !='' && fileName != null){
						$("#check").css('display','inline-table');
					}
					$(".profile_orgIO_fileTitle").text("");
					$(".profile_orgIO_fileTitle").text(fileName);
					//先清空之前table中的数据
					$("#msg").hide();
					$("#deptFileData").empty();
					$("#personFileData").empty();
					$("#postFileData").empty();
					$("#postPersonFileData").empty();
					$("#deptPersonFileData").empty();
					
				}
					
				//校验数据
				function checkData(){
					alert("开始检查数据，请等待，在等待过程中请勿做其他操作！");
					//先清空之前table中的数据
					$("#deptFileData").empty();
					$("#personFileData").empty();
					$("#postFileData").empty();
					$("#postPersonFileData").empty();
					$("#deptPersonFileData").empty();
					$("#msg").hide();
					$("#dpIsFull").hide();
					$("#ppIsFull").hide();
					$("#personIsMain").hide();
						var form = new FormData(document.getElementById("sysOmsExcelImportForm")); 
						$.ajax({
			    			url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsExcelImport.do?method=checkData",
			    			async:false,
			    			type:'POST',
			    			processData : false,
			    			contentType : false,
			    			data:form,
			    			dataType: "json",
			    			success : function(data) {
			    				if (data.success) {
									var modelType = data.modelType;
									var depttatle = "<caption align='top' style='color: red;text-align: left;'>部门不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>部门ID</td><td style='width: 120px;'>部门名称</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>是否有效</td><td style='width: 120px;'>上级部门ID</td><td style='width: 70px;'>部门排序号</td> </tr>"
									var posttatle ="<caption align='top' style='color: red;text-align: left;'>岗位不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>岗位ID</td><td style='width: 120px;'>岗位名称</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>是否有效</td><td style='width: 120px;'>所属部门ID</td><td style='width: 140px;'>岗位排序号</td></tr>";
									var persondepttatle="<caption align='top' style='color: red;text-align: left;'>部门人员关系不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>人员ID</td><td style='width: 120px;'>部门ID</td><td style='width: 70px;'>是否有效</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>排序号</td></tr>";
									var personposttatle="<caption align='top' style='color: red;text-align: left;'>岗位人员关系不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>人员ID</td><td style='width: 120px;'>岗位ID</td><td style='width: 70px;'>是否有效</td><td style='width: 140px;'>修改时间</td></tr>";
									if(modelType == '100' ){
										if(data.deptFileData.length < 1 && data.personFileData.length < 1){
											var html =$("<tr id='msg'><td>"+'Excel数据检测正常，请执行导入操作!'+"</td></tr>");
											$("#fileDataDiv").append(html);
											$(".import").show();
											$(".importResult").show();
										}else{ 
											if(data.deptFileData.length > 0){ //部门异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#deptFileData").show();
												$("#deptFileData").append(depttatle);
												for(var i=0; i< data.deptFileData.length ; i++){
													var deptObj = data.deptFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+deptObj.lineNum+"</td>"
													if(deptObj.fdDeptId.flag == true){
														html += "<td>"+deptObj.fdDeptId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdDeptId.errMsg+"</td>";
													}
													if(deptObj.fdName.flag == true){
														html += "<td>"+deptObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdName.errMsg+"</td>";
													}
													if(deptObj.fdAlterTime.flag == true){
														html += "<td>"+deptObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdAlterTime.errMsg+"</td>";
													}
													if(deptObj.fdIsAvailable.flag == true){
														html += "<td>"+deptObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdIsAvailable.errMsg+"</td>";
													}
								
													html += "<td>"+deptObj.fdParentid.value+"</td><td>"+deptObj.fdOrder.value+"</td>";
													html += "</tr>";
													$("#deptFileData").append(html);
													if(i > 48){ //太多（页面只支持展示前50条），更多数据
														$("#deptFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：部门信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personFileData.length > 0){ //人员异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#personFileData").show();
												var personTatle="<caption align='top' style='color: red;text-align: left;'>人员不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>人员ID</td><td style='width: 110px;'>人员名称</td><td style='width: 120px;'>登录名</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>是否有效</td><td style='width: 120px;'>主部门ID</td><td style='width: 190px;'>人员在主部门的排序号</td><td style='width: 120px;'>手机号</td><td style='width: 120px;'>邮箱</td><td style='width: 70px;'>性别</td>";
												if(data.personFileData[0].extandFieldarr.length >0){ //有拓展字段
													for(var j=0; j< data.personFileData[0].extandFieldarr.length ; j++){
														personTatle = personTatle+"<td style='width: 140px;'>"+data.personFileData[0].extandFieldarr[j].name+"</td>"
													}
												}
												personTatle = personTatle+"</tr>"
												$("#personFileData").append(personTatle);
												for(var i=0; i< data.personFileData.length ; i++){
													var personObj = data.personFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+personObj.lineNum+"</td>"
													if(personObj.fdPersonId.flag == true){
														html += "<td>"+personObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdPersonId.errMsg+"</td>";
													}
													if(personObj.fdName.flag == true){
														html += "<td>"+personObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdName.errMsg+"</td>";
													}
													if(personObj.fdLoginName.flag == true){
														html += "<td>"+personObj.fdLoginName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdLoginName.errMsg+"</td>";
													}
													if(personObj.fdAlterTime.flag == true){
														html += "<td>"+personObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdAlterTime.errMsg+"</td>";
													}
													if(personObj.fdIsAvailable.flag == true){
														html += "<td>"+personObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+personObj.fdParentid.value+"</td><td>"+personObj.fdOrder.value+"</td><td>"+personObj.fdMobileNo.value+"</td><td>"+personObj.fdEmail.value+"</td>";
													if(personObj.fdSex.flag == true){
														html += "<td>"+personObj.fdSex.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdSex.errMsg+"</td>";
													}
													//拓展字段
													for(var j=0; j< data.personFileData[i].extandFieldarr.length ; j++){
														if(personObj.extandFieldarr[j].flag == true){
															html += "<td>"+personObj.extandFieldarr[j].value+"</td>";
														}else{
															html += "<td style='color: red;'>"+personObj.extandFieldarr[j].errMsg+"</td>";
														}
													}
													html += "</tr>";
													$("#personFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#personFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：人员信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
										}
									}
									if(modelType == '200'){
										if(data.deptFileData.length < 1 && data.personFileData.length < 1 && data.personDeptFileData.length < 1){
											$("#dpIsFull").show();
											$("#personIsMain").show();
											var html =$("<tr id='msg'><td>"+'Excel数据检测正常，请执行导入操作!'+"</td></tr>");
											$("#fileDataDiv").append(html);
											$(".import").show();
											$(".importResult").show();
										}else{
											if(data.deptFileData.length > 0){ //部门异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#deptFileData").show();
												$("#deptFileData").append(depttatle);
												for(var i=0; i< data.deptFileData.length ; i++){
													var deptObj = data.deptFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+deptObj.lineNum+"</td>"
													if(deptObj.fdDeptId.flag == true){
														html += "<td>"+deptObj.fdDeptId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdDeptId.errMsg+"</td>";
													}
													if(deptObj.fdName.flag == true){
														html += "<td>"+deptObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdName.errMsg+"</td>";
													}
													if(deptObj.fdAlterTime.flag == true){
														html += "<td>"+deptObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdAlterTime.errMsg+"</td>";
													}
													if(deptObj.fdIsAvailable.flag == true){
														html += "<td>"+deptObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdIsAvailable.errMsg+"</td>";
													}
								
													html += "<td>"+deptObj.fdParentid.value+"</td><td>"+deptObj.fdOrder.value+"</td>";
													html += "</tr>";
												
													$("#deptFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#deptFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：部门信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personFileData.length > 0){ //人员异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#personFileData").show();
												var personTatle="<caption align='top' style='color: red;text-align: left;'>人员不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>人员ID</td><td style='width: 110px;'>人员名称</td><td style='width: 120px;'>登录名</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>是否有效</td><td style='width: 120px;'>主部门ID</td><td style='width: 190px;'>人员在主部门的排序号</td><td style='width: 120px;'>手机号</td><td style='width: 120px;'>邮箱</td><td style='width: 70px;'>性别</td>";
												if(data.personFileData[0].extandFieldarr.length >0){ //有拓展字段
													for(var j=0; j< data.personFileData[0].extandFieldarr.length ; j++){
														personTatle = personTatle+"<td style='width: 140px;'>"+data.personFileData[0].extandFieldarr[j].name+"</td>"
													}
												}
												personTatle = personTatle+"</tr>"
												$("#personFileData").append(personTatle);
												for(var i=0; i< data.personFileData.length ; i++){
													var personObj = data.personFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+personObj.lineNum+"</td>"
													if(personObj.fdPersonId.flag == true){
														html += "<td>"+personObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdPersonId.errMsg+"</td>";
													}
													if(personObj.fdName.flag == true){
														html += "<td>"+personObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdName.errMsg+"</td>";
													}
													if(personObj.fdLoginName.flag == true){
														html += "<td>"+personObj.fdLoginName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdLoginName.errMsg+"</td>";
													}
													if(personObj.fdAlterTime.flag == true){
														html += "<td>"+personObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdAlterTime.errMsg+"</td>";
													}
													if(personObj.fdIsAvailable.flag == true){
														html += "<td>"+personObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+personObj.fdParentid.value+"</td><td>"+personObj.fdOrder.value+"</td><td>"+personObj.fdMobileNo.value+"</td><td>"+personObj.fdEmail.value+"</td>";
													if(personObj.fdSex.flag == true){
														html += "<td>"+personObj.fdSex.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdSex.errMsg+"</td>";
													}
													//拓展字段
													for(var j=0; j< data.personFileData[i].extandFieldarr.length ; j++){
														if(personObj.extandFieldarr[j].flag == true){
															html += "<td>"+personObj.extandFieldarr[j].value+"</td>";
														}else{
															html += "<td style='color: red;'>"+personObj.extandFieldarr[j].errMsg+"</td>";
														}
													}
													
													html += "</tr>";
													$("#personFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#personFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：人员信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personDeptFileData.length > 0){ //部门人员关系
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#deptPersonFileData").show();
												$("#deptPersonFileData").append(persondepttatle);
												for(var i=0; i< data.personDeptFileData.length ; i++){
													var personDeptObj = data.personDeptFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+personDeptObj.lineNum+"</td>"
													if(personDeptObj.fdPersonId.flag == true){
														html += "<td>"+personDeptObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personDeptObj.fdPersonId.errMsg+"</td>";
													}
													if(personDeptObj.fdDeptId.flag == true){
														html += "<td>"+personDeptObj.fdDeptId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personDeptObj.fdDeptId.errMsg+"</td>";
													}
													if(personDeptObj.fdIsAvailable.flag == true){
														html += "<td>"+personDeptObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personDeptObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+personDeptObj.fdAlterTime.value+"</td><td>"+personDeptObj.fdOrder.value+"</td>";
													html += "</tr>";
													$("#deptPersonFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#deptPersonFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：部门人员关系信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											
										}	
									}
									if(modelType == '300'){
										if(data.deptFileData.length < 1 && data.personFileData.length < 1 && data.postFileData.length < 1 && data.personPostFileData.length < 1){
											$("#ppIsFull").show();
											var html =$("<tr id='msg'><td>"+'Excel数据检测正常，请执行导入操作!'+"</td></tr>");
											$("#fileDataDiv").append(html);
											$(".import").show();
											$(".importResult").show();
										}else{
											if(data.deptFileData.length > 0){ //部门异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#deptFileData").show();
												$("#deptFileData").append(depttatle);
												for(var i=0; i< data.deptFileData.length ; i++){
													var deptObj = data.deptFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+deptObj.lineNum+"</td>"
													if(deptObj.fdDeptId.flag == true){
														html += "<td>"+deptObj.fdDeptId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdDeptId.errMsg+"</td>";
													}
													if(deptObj.fdName.flag == true){
														html += "<td>"+deptObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdName.errMsg+"</td>";
													}
													if(deptObj.fdAlterTime.flag == true){
														html += "<td>"+deptObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdAlterTime.errMsg+"</td>";
													}
													if(deptObj.fdIsAvailable.flag == true){
														html += "<td>"+deptObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdIsAvailable.errMsg+"</td>";
													}
								
													html += "<td>"+deptObj.fdParentid.value+"</td><td>"+deptObj.fdOrder.value+"</td>";
													html += "</tr>";
												
													$("#deptFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#deptFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：部门信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personFileData.length > 0){ //人员异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#personFileData").show();
												var personTatle="<caption align='top' style='color: red;text-align: left;'>人员不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>人员ID</td><td style='width: 110px;'>人员名称</td><td style='width: 120px;'>登录名</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>是否有效</td><td style='width: 120px;'>主部门ID</td><td style='width: 190px;'>人员在主部门的排序号</td><td style='width: 120px;'>手机号</td><td style='width: 120px;'>邮箱</td><td style='width: 70px;'>性别</td>";
												if(data.personFileData[0].extandFieldarr.length >0){ //有拓展字段
													for(var j=0; j< data.personFileData[0].extandFieldarr.length ; j++){
														personTatle = personTatle+"<td style='width: 140px;'>"+data.personFileData[0].extandFieldarr[j].name+"</td>"
													}
												}
												personTatle = personTatle+"</tr>"
												$("#personFileData").append(personTatle);
												for(var i=0; i< data.personFileData.length ; i++){
													var personObj = data.personFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+personObj.lineNum+"</td>"
													if(personObj.fdPersonId.flag == true){
														html += "<td>"+personObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdPersonId.errMsg+"</td>";
													}
													if(personObj.fdName.flag == true){
														html += "<td>"+personObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdName.errMsg+"</td>";
													}
													if(personObj.fdLoginName.flag == true){
														html += "<td>"+personObj.fdLoginName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdLoginName.errMsg+"</td>";
													}
													if(personObj.fdAlterTime.flag == true){
														html += "<td>"+personObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdAlterTime.errMsg+"</td>";
													}
													if(personObj.fdIsAvailable.flag == true){
														html += "<td>"+personObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+personObj.fdParentid.value+"</td><td>"+personObj.fdOrder.value+"</td><td>"+personObj.fdMobileNo.value+"</td><td>"+personObj.fdEmail.value+"</td>";
													if(personObj.fdSex.flag == true){
														html += "<td>"+personObj.fdSex.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdSex.errMsg+"</td>";
													}
													//拓展字段
													for(var j=0; j< data.personFileData[i].extandFieldarr.length ; j++){
														if(personObj.extandFieldarr[j].flag == true){
															html += "<td>"+personObj.extandFieldarr[j].value+"</td>";
														}else{
															html += "<td style='color: red;'>"+personObj.extandFieldarr[j].errMsg+"</td>";
														}
													}
													
													html += "</tr>";
													$("#personFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#personFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：人员信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.postFileData.length > 0){ //岗位异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#postFileData").show();
												$("#postFileData").append(posttatle);
												for(var i=0; i< data.postFileData.length ; i++){
													var postObj = data.postFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+postObj.lineNum+"</td>"
													if(postObj.fdPostId.flag == true){
														html += "<td>"+postObj.fdPostId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdPostId.errMsg+"</td>";
													}
													if(postObj.fdName.flag == true){
														html += "<td>"+postObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdName.errMsg+"</td>";
													}
													if(postObj.fdAlterTime.flag == true){
														html += "<td>"+postObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdAlterTime.errMsg+"</td>";
													}
													if(postObj.fdIsAvailable.flag == true){
														html += "<td>"+postObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+postObj.fdParentid.value+"</td><td>"+postObj.fdOrder.value+"</td>";
													html += "</tr>";
													$("#postFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#postFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：岗位信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personPostFileData.length > 0){ //岗位人员关系异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#postPersonFileData").show();
												$("#postPersonFileData").append(personposttatle);
												for(var i=0; i< data.personPostFileData.length ; i++){
													var postPersonObj = data.personPostFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+postPersonObj.lineNum+"</td>"
													if(postPersonObj.fdPersonId.flag == true){
														html += "<td>"+postPersonObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postPersonObj.fdPersonId.errMsg+"</td>";
													}
													if(postPersonObj.fdPostId.flag == true){
														html += "<td>"+postPersonObj.fdPostId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postPersonObj.fdPostId.errMsg+"</td>";
													}
													if(postPersonObj.fdIsAvailable.flag == true){
														html += "<td>"+postPersonObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postPersonObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+postPersonObj.fdAlterTime.value+"</td>";
													html += "</tr>";
													$("#postPersonFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#postPersonFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：岗位人员关系信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
										}
									}
									if(modelType == '400'){
										if(data.deptFileData.length < 1 && data.personFileData.length < 1 && data.postFileData.length < 1 && data.personPostFileData.length < 1 && data.personDeptFileData.length < 1){
											$("#dpIsFull").show();
											$("#ppIsFull").show();
											var html =$("<tr id='msg'><td>"+'Excel数据检测正常，请执行导入操作!'+"</td></tr>");
											$("#fileDataDiv").append(html);
											$(".import").show();
											$(".importResult").show();
										}else{
											if(data.deptFileData.length > 0){ //部门异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#deptFileData").show();
												personTatle = personTatle+"</tr>"
												$("#deptFileData").append(depttatle);
												for(var i=0; i< data.deptFileData.length ; i++){
													var deptObj = data.deptFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+deptObj.lineNum+"</td>"
													if(deptObj.fdDeptId.flag == true){
														html += "<td>"+deptObj.fdDeptId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdDeptId.errMsg+"</td>";
													}
													if(deptObj.fdName.flag == true){
														html += "<td>"+deptObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdName.errMsg+"</td>";
													}
													if(deptObj.fdAlterTime.flag == true){
														html += "<td>"+deptObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdAlterTime.errMsg+"</td>";
													}
													if(deptObj.fdIsAvailable.flag == true){
														html += "<td>"+deptObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+deptObj.fdIsAvailable.errMsg+"</td>";
													}
								
													html += "<td>"+deptObj.fdParentid.value+"</td><td>"+deptObj.fdOrder.value+"</td>";
													html += "</tr>";
													$("#deptFileData").append(html);
													if(i > 48){ //超过50条数据
														$("#deptFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：部门信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personFileData.length > 0){ //人员异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#personFileData").show();
												var personTatle="<caption align='top' style='color: red;text-align: left;'>人员不合法数据列表</caption><tr><td style='width: 70px;'>行数</td><td style='width: 120px;'>人员ID</td><td style='width: 110px;'>人员名称</td><td style='width: 120px;'>登录名</td><td style='width: 140px;'>修改时间</td><td style='width: 70px;'>是否有效</td><td style='width: 120px;'>主部门ID</td><td style='width: 190px;'>人员在主部门的排序号</td><td style='width: 120px;'>手机号</td><td style='width: 120px;'>邮箱</td><td style='width: 70px;'>性别</td>";
												if(data.personFileData[0].extandFieldarr.length >0){ //有拓展字段
													for(var j=0; j< data.personFileData[0].extandFieldarr.length ; j++){
														personTatle = personTatle+"<td style='width: 140px;'>"+data.personFileData[0].extandFieldarr[j].name+"</td>"
													}
												}
												$("#personFileData").append(personTatle);
												for(var i=0; i< data.personFileData.length ; i++){
													var personObj = data.personFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+personObj.lineNum+"</td>"
													if(personObj.fdPersonId.flag == true){
														html += "<td>"+personObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdPersonId.errMsg+"</td>";
													}
													if(personObj.fdName.flag == true){
														html += "<td>"+personObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdName.errMsg+"</td>";
													}
													if(personObj.fdLoginName.flag == true){
														html += "<td>"+personObj.fdLoginName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdLoginName.errMsg+"</td>";
													}
													if(personObj.fdAlterTime.flag == true){
														html += "<td>"+personObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdAlterTime.errMsg+"</td>";
													}
													if(personObj.fdIsAvailable.flag == true){
														html += "<td>"+personObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+personObj.fdParentid.value+"</td><td>"+personObj.fdOrder.value+"</td><td>"+personObj.fdMobileNo.value+"</td><td>"+personObj.fdEmail.value+"</td>";
													if(personObj.fdSex.flag == true){
														html += "<td>"+personObj.fdSex.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personObj.fdSex.errMsg+"</td>";
													}
													//拓展字段
													for(var j=0; j< data.personFileData[i].extandFieldarr.length ; j++){
														if(personObj.extandFieldarr[j].flag == true){
															html += "<td>"+personObj.extandFieldarr[j].value+"</td>";
														}else{
															html += "<td style='color: red;'>"+personObj.extandFieldarr[j].errMsg+"</td>";
														}
													}
													
													html += "</tr>";
													$("#personFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#personFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：人员信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.postFileData.length > 0){ //岗位异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#postFileData").show();
												$("#postFileData").append(posttatle);
												for(var i=0; i< data.postFileData.length ; i++){
													var postObj = data.postFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+postObj.lineNum+"</td>"
													if(postObj.fdPostId.flag == true){
														html += "<td>"+postObj.fdPostId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdPostId.errMsg+"</td>";
													}
													if(postObj.fdName.flag == true){
														html += "<td>"+postObj.fdName.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdName.errMsg+"</td>";
													}
													if(postObj.fdAlterTime.flag == true){
														html += "<td>"+postObj.fdAlterTime.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdAlterTime.errMsg+"</td>";
													}
													if(postObj.fdIsAvailable.flag == true){
														html += "<td>"+postObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+postObj.fdParentid.value+"</td><td>"+postObj.fdOrder.value+"</td>";
													html += "</tr>";
													$("#postFileData").append(html);
													if(i > 48){ //超过50条数据
														$("#postFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：岗位信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personPostFileData.length > 0){ //岗位人员关系异常数据
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#postPersonFileData").show();
												$("#postPersonFileData").append(personposttatle);
												for(var i=0; i< data.personPostFileData.length ; i++){
													var postPersonObj = data.personPostFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+postPersonObj.lineNum+"</td>"
													if(postPersonObj.fdPersonId.flag == true){
														html += "<td>"+postPersonObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postPersonObj.fdPersonId.errMsg+"</td>";
													}
													if(postPersonObj.fdPostId.flag == true){
														html += "<td>"+postPersonObj.fdPostId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postPersonObj.fdPostId.errMsg+"</td>";
													}
													if(postPersonObj.fdIsAvailable.flag == true){
														html += "<td>"+postPersonObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+postPersonObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+postPersonObj.fdAlterTime.value+"</td>";
													html += "</tr>";
													$("#postPersonFileData").append(html);
													if(i > 48){ //超过50条数据
														$("#postPersonFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：岗位人员关系信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
											if(data.personDeptFileData.length > 0){ //部门人员关系
												var file = $("#input_file"); 
												file.after(file.clone().val("")); 
												file.remove();
												$("#deptPersonFileData").show();
												$("#deptPersonFileData").append(persondepttatle);
												for(var i=0; i< data.personDeptFileData.length ; i++){
													var personDeptObj = data.personDeptFileData[i];
													var html ="<tr style='border-bottom: 1px solid #9e9e9e3d;'><td>"+personDeptObj.lineNum+"</td>"
													if(personDeptObj.fdPersonId.flag == true){
														html += "<td>"+personDeptObj.fdPersonId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personDeptObj.fdPersonId.errMsg+"</td>";
													}
													if(personDeptObj.fdDeptId.flag == true){
														html += "<td>"+personDeptObj.fdDeptId.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personDeptObj.fdDeptId.errMsg+"</td>";
													}
													if(personDeptObj.fdIsAvailable.flag == true){
														html += "<td>"+personDeptObj.fdIsAvailable.value+"</td>";
													}else{
														html += "<td style='color: red;'>"+personDeptObj.fdIsAvailable.errMsg+"</td>";
													}
													html += "<td>"+personDeptObj.fdAlterTime.value+"</td><td>"+personDeptObj.fdOrder.value+"</td>";
													html += "</tr>";
													$("#deptPersonFileData").append(html);
													if(i > 48){ //太多（页面只展示前50条），更多数据
														$("#deptPersonFileData").append("<caption align='bottom' style='color: red;text-align: left;'>注意：部门人员关系信息异常数据太多（页面只展示前50条），更多请在日志中查看具体异常数据</caption>");
														break;
													}
												}
											}
										}	
									}
									
								} else {
									var file = $("#input_file"); 
									file.after(file.clone().val("")); 
									file.remove();
									dialog.alert(data.msg);
								}
			    			}
			    		});
				}
				window.fileChange=fileChange;
				window.checkData =checkData;
			});

			function reset() {
				window.location.reload();
			}
		
			

			function downloadTemplet() {
				document.templet.submit();
			}
			LUI.ready( function() {
				$("[name='fdPersonIsAsc']").each(function () {
	    			if($(this).val()=="1"){
	    		  		$(this).attr("checked",true);
	    		  }
	     		});
				$("[name='fdDeptIsAsc']").each(function () {
	    			if($(this).val()=="1"){
	    		  		$(this).attr("checked",true);
	    		  }
	     		});
				$("[name='fdPersonIsMainDept']").each(function () {
	    			if($(this).val()=="1"){
	    		  		$(this).attr("checked",true);
	    		  }
	     		});
				$("[name='fdPersonDeptIsFull']").each(function () {
	    			if($(this).val()=="1"){
	    		  		$(this).attr("checked",true);
	    		  }
	     		});
				$("[name='fdPersonPostIsFull']").each(function () {
	    			if($(this).val()=="1"){
	    		  		$(this).attr("checked",true);
	    		  }
	     		});
	    	});
		</script>
	</template:replace>
</template:include>
