<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false" sidebar="no">

	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("dialog.js|jquery.js");
	   		 Com_IncludeFile("sysUnitDialog.js", Com_Parameter.ContextPath+ "sys/unit/resource/js/", "js", true);
	   		
			var picExtNames = "jpeg;jpg;bmp;png";
			var highFidelityNames = "doc;docx;wps;ppt;pptx;pdf;rtf";
			var wpsExtNames = "doc;docx;wps;ppt;pptx;dps;pdf;rtf;xls;xlsx;ett;et";
			var picResolutionNames = "doc;docx;wps;pdf;rtf";
			var initialExtName = "${lfn:escapeJs(sysFileConvertConfigForm.fdFileExtName)}";
			var highFidelityValue="${lfn:escapeJs(sysFileConvertConfigForm.fdHighFidelity)}";
			var converterTypeValue="${lfn:escapeJs(sysFileConvertConfigForm.fdConverterType)}";
			
			var defaultFdFileExtName="";
			
			var selectHtml="";

			function submitForm(method) {
				var seleValue = chooseConverterKey();
				if("" == seleValue) {
					document.getElementById("fdConverterKey_validate").style.display = 'block';//目标扩展名
					return;
				}
				Com_Submit(document.sysFileConvertConfigForm, method);
			}

			function contains(string, substr, isIgnoreCase) {
				if (isIgnoreCase) {
					string = string.toLowerCase();
					substr = substr.toLowerCase();
				}
				var startChar = substr.substring(0, 1);
				var strLen = substr.length;
				for (var j = 0; j < string.length - strLen + 1; j++) {
					if (string.charAt(j) == startChar) { //如果匹配起始字符,开始查找
						if (string.substring(j, j + strLen) == substr) { //如果从j开始的字符与str匹配，那ok
							return true;
						}
					}
				}
				return false;
			}

			function fileExtNameChanged(extName) {
				seajs.use('lui/jquery', function($) {
					if (contains(picExtNames, extName, true)) {
						$(":input[name='_fdHighFidelity']").attr("checked",
								false);
						$("#tr_2").hide();
						$("#tr_1").hide();
						$("#tr_1_1").hide();
					} else {
						$("#tr_2").show();
						if (contains(highFidelityNames, extName, true)) {
							$("#td_highfidelity_name").show();
							$("#td_highfidelity_value").show();
							if(highFidelityValue=="1"){
								$(":input[name='_fdHighFidelity']").attr("checked",
										true);
							}else{
								$(":input[name='_fdHighFidelity']").attr("checked",
										false);
							}
						} else {
							$("#td_highfidelity_name").hide();
							$("#td_highfidelity_value").hide();
							$(":input[name='_fdHighFidelity']").attr("checked",
									false);
						}
						if(contains(picResolutionNames, extName, true)){
							$("#tr_1").show();
							$("#tr_1_1").show();
						}else{
							$("#tr_1").hide();
							$("#tr_1_1").hide();
						}
					}
				});
			}
			function converterTypeChanged(converterType) {
				seajs.use('lui/jquery', function($) {
					defaultFdFileExtName=$(".fdFileExtNameClass").val();
					if (converterType=="wps") {
						$(".fdFileExtNameClass option").each(function(){
							   if(wpsExtNames.indexOf($(this).val())==-1){
							    	$(this).remove();
							   }
						});
						$("#tr_1").hide();
						$("#tr_1_1").hide();
						$("#td_highfidelity_name").hide();
						$("#td_highfidelity_value").hide();
					} else {
						$(".fdFileExtNameClass").html(selectHtml);
						$("#tr_1_1").show();
						$("#tr_1").show();
						$("#td_highfidelity_name").show();
						$("#td_highfidelity_value").show();
					}
					$(".fdFileExtNameClass").val(defaultFdFileExtName);
				});
			}

			function initialConfig() {
			
				document.getElementById("fdConverterKey_validate").style.display = 'none';//不显示扩展名的提示
				document.getElementById("optionnumber_validate").style.display = 'none';//不显示扩展名的提示
				selectHtml=$(".fdFileExtNameClass").html();
				if (initialExtName != "") {
				//	fileExtNameChanged(initialExtName);
				//	converterTypeChanged(converterTypeValue);
				}
				$(".tempTB").removeAttr("style");
				$(".lui_form_path_frame").remove();
///				$("textarea").removeAttr("readonly");//textarea 可以编辑

				var getConverterType="${lfn:escapeJs(sysFileConvertConfigForm.fdConverterType)}"; //转换服务
				var getAllModuls = "${lfn:escapeJs(sysFileConvertConfigForm.allModuls)}"; //不限模块

				changeRequired(getAllModuls);
				chooseConverterType(getConverterType);
				
				var getConverterKey="${lfn:escapeJs(sysFileConvertConfigForm.fdConverterKey)}";
				if(getConverterKey != '') {
				  $("#fdConverterKey option[value='"+getConverterKey+"']").attr("selected", true);
				}
				
			}

			$(document).ready(initialConfig); //初始
			
			var sendConverterTye = "";
			
			//根据不同的转换类型，对应的下拉框值不一样
			function chooseConverterType(value) {
				$("#fdConverterKey").empty();
			   sendConverterTye = value;
			   document.getElementById("optionnumber_validate").style.display = 'none';
			   $("#tr_2").hide();
				$("#tr_1").hide();
				$("#tr_1_1").hide();

			  if(value == 'aspose') {
					var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
					          /*   +"<option value='toPDF'>PDF</option>" */
								+"<option value='toHTML'>HTML</option>"
								+"<option value='toJPG'>JPG</option>"
								+"<option value='videoToMp4'>MP4</option>";
				    $("#fdConverterKey").append(options);
				    $("#tr_1").show();
					$("#tr_1_1").show();
				    $("#tr_2").show();
				}
				else if(value == 'yozo') {
					var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
						+"<option value='toHTML'>HTML</option>"
						+"<option value='toJPG'>JPG</option>"
						+"<option value='toMP4'>MP4</option>";
		   			 $("#fdConverterKey").append(options);
				}
				else if(value == 'wps') {
					var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
			            	+"<option value='toOFD'>OFD</option>"
			            	+"<option value='toPDF'>PDF</option>";
			            
		   			  $("#fdConverterKey").append(options);
				}
				else if(value == 'wpsCenter') {
					var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
							+"<option value='toOFD'>OFD</option>"
							+"<option value='toPDF'>PDF</option>";
			            
		   			  $("#fdConverterKey").append(options);
				}
				else if(value == 'skofd') {
					var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
			            +"<option value='toOFD'>OFD</option>"
		   			  $("#fdConverterKey").append(options);
				}
			  else if(value == 'dianju') {
				  var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
						  +"<option value='toOFD'>OFD</option>"
						  +"<option value='toPDF'>PDF</option>";
				  $("#fdConverterKey").append(options);
			  }
			  else if(value == 'foxit') {
				  var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>"
						  +"<option value='toOFD'>OFD</option>"
						  +"<option value='toPDF'>PDF</option>";
				  $("#fdConverterKey").append(options);
			  } else {
					var options="<option value=''>${ lfn:message('sys-filestore:sysFilestore.conversion.please.select') }</option>";
		   			  $("#fdConverterKey").append(options);
				}
				 
			}
			
			//待扩展名选择
			function chooseFileExtName() {
			    var converterType = sendConverterTye;
			 //   var tagSuffix = chooseConverterKey();
			    var cKey=document.getElementById("fdConverterKey");
				var index=cKey.selectedIndex ;
				var tagSuffix = cKey.options[index].text;
			
				if("" == tagSuffix) {
					document.getElementById("fdConverterKey_validate").style.display = 'block';//目标扩展名
					return;
				} else {
					document.getElementById("fdConverterKey_validate").style.display = 'none';//目标扩展名
				}
				Dialog_TreeList(true, "fdFileExtNameId", "fdFileExtName", "、", "sysExtendFileNameTreeService&converterType="+converterType+"&tagSuffix="+tagSuffix, "${ lfn:message('sys-filestore:sysFilestore.conversion.extend.name.class') }", "sysExtendFileNameTreeService&parentId=!{value}&converterType="+converterType+"&tagSuffix="+tagSuffix);
			}
			
			//模块信息
			function chooseModulName() {
				
				Dialog_TreeList(true, "fdModelName", "fdModul",  "、", "sysModulsInfosTreeService&parent=", "${ lfn:message('sys-filestore:sysFilestore.conversion.modul.info') }", "sysModulsInfosTreeService&parent=!{value}",hooseModulNameCallback,'sysModulsInfosTreeService&type=search&key=!{keyword}');
			}

			function hooseModulNameCallback() {

			}
			
			//不限制模块
			function changeRequired(moduls) {
				var allModuls = $("input[name='allModuls']").val();
				if(moduls) {
					allModuls = moduls;
				}
				if(allModuls == 'true') {
					document.getElementsByClassName("txtstrong")[3].style.display="none";
					document.getElementsByName("fdModul")[0].setAttribute("validate","");
				}
				else {
					document.getElementsByClassName("txtstrong")[3].style.display="";
					document.getElementsByName("fdModul")[0].setAttribute("validate","required");
				}
			}
			
			//选择目标扩展名
			function chooseConverterKey() {
				var cKey=document.getElementById("fdConverterKey");
				var index=cKey.selectedIndex ;
				var selectValue = cKey.options[index].value;
				if(cKey.options.length == 1) {
					document.getElementById("optionnumber_validate").style.display = 'block';
				} else {
					document.getElementById("optionnumber_validate").style.display = 'none';
				}
				if("" != selectValue) {
					document.getElementById("fdConverterKey_validate").style.display = 'none';//目标扩展名
				}
				return selectValue;
			}
			
		</script>
	</template:replace>

	<template:replace name="content"  >
		<c:set var="sysFileConvertConfigForm"
			value="${sysFileConvertConfigForm}" scope="request" />
		<html:form
			action="/sys/filestore/sys_filestore/sysFileConvertConfig.do">
			<html:hidden property="fdId" />
			<p class="lui_form_subject">
				<c:out
					value="${ lfn:message('sys-filestore:sysFileConvertConfig.setting') }" />
			</p>
			<div class="lui_form_content_frame" style="padding-top: 0px" >
				<table class="tb_normal" style="width: 650px;height:150px">
				<tr>
						<td class="td_normal_title"  width="180px" align="center">
							<label> 
								<bean:message
									key='sysFilestore.conversion.select.converter' bundle='sys-filestore' />
							</label>
						</td>
							<td  colspan="3" > 
							<label>
						     	<xform:radio className="fdConverterType" property="fdConverterType" showStatus="edit" onValueChange="chooseConverterType" required="true" >
							         <c:if test="${'true' eq sysFileConvertConfigForm.converter_aspose}">
							         <xform:simpleDataSource value="aspose" >ASPOSE &nbsp;&nbsp;</xform:simpleDataSource>
							         </c:if>
									<c:if test="${'true' eq sysFileConvertConfigForm.converter_yozo}">
										<xform:simpleDataSource value="yozo" ><bean:message
												key="sysFilestore.conver.server.yozo"
												bundle="sys-filestore" /> &nbsp;&nbsp;</xform:simpleDataSource>
									</c:if>
									 <c:if test="${'true' eq sysFileConvertConfigForm.converter_wps}">
										 <xform:simpleDataSource value="wps" ><bean:message
												 key="sysFilestore.conver.server.wps"
												 bundle="sys-filestore" />&nbsp;&nbsp;</xform:simpleDataSource>
									 </c:if>
									 <c:if test="${'true' eq sysFileConvertConfigForm.converter_skofd}">
										 <xform:simpleDataSource value="skofd" ><bean:message
												 key="sysFilestore.conver.server.shuke"
												 bundle="sys-filestore" /> &nbsp;&nbsp;</xform:simpleDataSource>
									 </c:if>
									 <c:if test="${'true' eq sysFileConvertConfigForm.converter_wpsCenter}">
										 <xform:simpleDataSource value="wpsCenter" ><bean:message
												 key="sysFilestore.conversion.wps.center.ofd"
												 bundle="sys-filestore" /> &nbsp;&nbsp;</xform:simpleDataSource>
									 </c:if>
									 <c:if test="${'true' eq sysFileConvertConfigForm.converter_dianju}">
										 <xform:simpleDataSource value="dianju" ><bean:message
												 key="sysFilestore.conversion.dianju.ofd"
												 bundle="sys-filestore" /> &nbsp;&nbsp;</xform:simpleDataSource>
									 </c:if>
									 <c:if test="${'true' eq sysFileConvertConfigForm.converter_foxit}">
										 <xform:simpleDataSource value="foxit" ><bean:message
												 key="sysFilestore.conver.server.foxit"
												 bundle="sys-filestore" /> &nbsp;&nbsp;</xform:simpleDataSource>
									 </c:if>
							  </xform:radio>

							</label>
							</td>
						</tr>
						
				<tr>
						<td class="td_normal_title" width="120px" align="center"><label><bean:message
									key='sysFilestore.conversion.extend.name' bundle='sys-filestore' /></label></td>
						<td colspan="3" width="300px">
							     	<select  id="fdConverterKey" name="fdConverterKey"  className="fdFileExtNameClass" required="required" style="width:130px;" onclick="chooseConverterKey();">
							</select>
							<span class="txtstrong">*</span>
							<font size="0.5" color="#C0C0C0"><bean:message
									key='sysFilestore.conversion.tip.extend.name' bundle='sys-filestore' /></font>
							<div class="validation-advice" id="optionnumber_validate" _reminder="true" style="display: none">
							  <table class="validation-table">
							    <tbody>
								 <tr>
								  <td>
								   <div class="lui_icon_s lui_icon_s_icon_validator">
								   </div></td>
								  <td class="validation-advice-msg">
								   <span class="validation-advice-title"><bean:message
										   key='sysFilestore.conversion.select.converter' bundle='sys-filestore'/></span></td>
								   </tr></tbody></table>
							</div>
							<div class="validation-advice" id="fdConverterKey_validate" _reminder="true" style="display: none">
							  <table class="validation-table">
							    <tbody>
								 <tr>
								  <td>
								   <div class="lui_icon_s lui_icon_s_icon_validator">
								   </div></td>
								  <td class="validation-advice-msg">
								   <span class="validation-advice-title"><bean:message
									key='sysFilestore.conversion.extend.name' bundle='sys-filestore' /></span> <bean:message
									key='sysFilestore.conversion.notnull' bundle='sys-filestore' /></td>
								   </tr></tbody></table>
							</div>
							
						</td>
					</tr>
					<!-- start -->
					   <tr id="tr_1" style="display: none">
						<td class="td_normal_title" width="120px" align="center">${ lfn:message('sys-filestore:queueParam.picResolution') }</td>
						<td width="360px" colspan="3"><xform:text
								property="fdPicResolution" style="width:60px;"
								subject="${lfn:message('sys-filestore:queueParam.picResolution')}"
								showStatus="edit" validators="min(96) digits" /> <span class="message"><bean:message
									key='queueParam.picResolution.desc' bundle='sys-filestore' /></span></td>
					</tr>
					<tr id="tr_1_1" style="display: none">
						<td class="td_normal_title" width="120px" align="center">${ lfn:message('sys-filestore:queueParam.picRectangle') }</td>
						<td width="360px" colspan="3"><xform:text
								property="fdPicRectangle" style="width:60px;"
								subject="${lfn:message('sys-filestore:queueParam.picRectangle')}"
								showStatus="edit" /> <span class="message"><bean:message
									key='queueParam.picRectangle.desc' bundle='sys-filestore' /></span></td>
					</tr>
					<tr id="tr_2" style="display: none">
						<td  id="td_highfidelity_name"
							class="td_normal_title" width="120px" align="center">${ lfn:message('sys-filestore:sysFileConvertConfig.fdHighFidelity') }</td>
						<td id="td_highfidelity_value" width="200px" colspan="3"><xform:checkbox
								property="fdHighFidelity" dataType="boolean">
								<xform:simpleDataSource value="true">
									<bean:message key="message.yes" />
								</xform:simpleDataSource>
							</xform:checkbox></td>
					</tr>
					<!-- end -->
					<tr>
						<td class="td_normal_title" width="120px" align="center"><label><bean:message
									key='sysFilestore.conversion.extend.name.tobe' bundle='sys-filestore' /></label></td>
						<td colspan="2" width="300px" style="border-right-style:none">
							<xform:dialog   icon="false" required="true" showStatus="false"  propertyId="fdFileExtNameId" propertyName="fdFileExtName" style="width:98%" textarea="true"  useNewStyle="false"> 
							</xform:dialog>
						</td>
						<td style="border-left-style:none">
								<label>
								 <a href='javascript:chooseFileExtName();' style='color:#66CCFF;'><bean:message
									key='sysFilestore.conversion.select' bundle='sys-filestore' /></a>
								</label>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="120px" align="center"><label><bean:message
									key='sysFilestore.conversion.attechment.modul' bundle='sys-filestore' /></label></td>
						<td colspan="2" width="300px" style="border-right-style:none">
			
					     	<label>
								     	<xform:checkbox subject="" property="allModuls" dataType="boolean" showStatus ="edit" onValueChange="changeRequired">
										<xform:simpleDataSource value="true"><bean:message
									key='sysFilestore.conversion.attechment.modul.unlimited' bundle='sys-filestore' /></xform:simpleDataSource>
									</xform:checkbox>
								
							</label>
								<xform:dialog   icon="false" required="true"  showStatus="edit" propertyId="fdModelName" propertyName="fdModul" style="width:98%" textarea="true"  useNewStyle="false"> 
							</xform:dialog>

								
						</td>
						<td style="border-left-style:none">
							  <label>
							   <a href='javascript:chooseModulName();' style='color:#66CCFF;'><bean:message
									key='sysFilestore.conversion.select' bundle='sys-filestore' /></a>
							</label>
						</td>
					</tr>
				</table>
			</div>
			<center style="margin-top: 10px;">
					       <kmss:authShow roles="SYSROLE_ADMIN">
					    	 <ui:button text="${lfn:message('button.save') }" height="35" width="80" onclick="submitForm('save');"></ui:button>
							</kmss:authShow>
							<ui:button text="${lfn:message('button.close') }" height="35" width="80" onclick="Com_CloseWindow();"> </ui:button>
              		</center> 
		</html:form>
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysFileConvertConfigForm']);
		</script>
	</template:replace>
</template:include>