<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.*"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath }/sys/attachment/viewer/resource/watermark/spectrum.css?s_cache=${LUI_Cache }"
			type="text/css" />
		<script type="text/javascript">
			var colorChooserHintInfo={
				chooseText : '<%= ResourceUtil.getString("watermark.hint.markWord.fontColor.choose", "sys-attachment") %>',
				cancelText : '<%= ResourceUtil.getString("watermark.hint.markWord.fontColor.restore", "sys-attachment") %>'
			};
			var waterMarkConfig=${waterMarkConfig};
			$(document).ready(function(){
				seajs.use(['lui/jquery','${LUI_ContextPath }/sys/attachment/viewer/resource/watermark/spectrum.js'], function($) {
					
					setTimeout("configWaterMarkFuncs.initialConfig()", 300);
				});
			});
		</script>
		<script type="text/javascript"
			src="${LUI_ContextPath }/sys/attachment/viewer/resource/watermark/config.js?s_cache=${LUI_Cache }"></script>	
	</template:replace>
	<template:replace name="content">
		<html:form
			action="/sys/attachment/sys_att_watermark/sysAttWaterMark.do"
			method="post" enctype="multipart/form-data">
			<center>
				<table class="tb_normal" width="100%">
					<tr width="100%">
						<td class="td_normal_title" colspan="3""><b> <label>
									<%-- <html:checkbox property="showWaterMark"
										onchange="configWaterMarkFuncs.showWaterMarkChanged();">
										<bean:message key="watermark.hint.showWaterMark"
											bundle="sys-attachment" />
									</html:checkbox>  --%>
									<xform:checkbox property="showWaterMark" showStatus="edit" onValueChange="configWaterMarkFuncs.showWaterMarkChanged()">
										<xform:simpleDataSource value="true">
											<bean:message key="watermark.hint.showWaterMark" bundle="sys-attachment" />
										</xform:simpleDataSource>
									</xform:checkbox> 
							</label>
						</b></td>
					</tr>
				</table>
				<table class="tb_normal" width="100%" id="waterMarkConfig" style="display: none">
					<tr>
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.markType" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:radio property="markType"
								showStatus="edit" required="true"
								onValueChange="configWaterMarkFuncs.markTypeChanged">
								<xform:simpleDataSource value="word">
									<bean:message key="watermark.hint.markType.word"
										bundle="sys-attachment" />
								</xform:simpleDataSource>
								<xform:simpleDataSource value="pic">
									<bean:message key="watermark.hint.markType.pic"
										bundle="sys-attachment" />
								</xform:simpleDataSource>
							</xform:radio></td>
					</tr>
					<tr class="tr_word_type_config">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.markWord" bundle="sys-attachment" /></td>
						<td colspan="2">  <span id="markWordVarSpan"> <xform:text
									property="markWordVar" style="width:500px;" showStatus="edit"
									onValueChange="configWaterMarkFuncs.markWordChanged" /><br />
								<br /> <bean:message key="watermark.hint.markWord.var.1"
									bundle="sys-attachment" /><br /> <br /> <bean:message
									key="watermark.hint.markWord.var.2" bundle="sys-attachment" /><br />
								<br /> <bean:message key="watermark.hint.markWord.var.3"
									bundle="sys-attachment" /><br />
								<br /> <bean:message key="watermark.hint.markWord.var.4"
									bundle="sys-attachment" />
								<br />
								<br /> <bean:message key="watermark.hint.markWord.var.5"
									bundle="sys-attachment" />
									<br />
								<br /> <bean:message key="watermark.hint.markWord.var.6"
									bundle="sys-attachment" />
									<br />
								<br /> <bean:message key="watermark.hint.markWord.var.7"
									bundle="sys-attachment" />
									<br />
								<br /> <font color="red"><bean:message key="watermark.hint.markWord.var.8"
									bundle="sys-attachment" /></font>
								<br />
								<br /> <font color="red"><bean:message key="watermark.hint.markWord.var.9"
									bundle="sys-attachment" /></font>
								<br />
								<br /> <font color="red"><bean:message key="watermark.hint.markWord.var.10"
									bundle="sys-attachment" /></font>
								<br />
								<br /> <font color="red"><bean:message key="watermark.hint.markWord.var.11"
									bundle="sys-attachment" />	</font>
								<br />
								<br /> <font color="red"><bean:message key="watermark.hint.markWord.var.12"
									bundle="sys-attachment" />	</font>
						</span></td>
					</tr>
					<tr class="tr_word_type_config">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.markWord.fontFamily" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:select required="true"
								showPleaseSelect="false" property="markWordFontFamily"
								showStatus="edit"
								onValueChange="configWaterMarkFuncs.markWordFontFamilyChanged">
								<xform:customizeDataSource
									className="com.landray.kmss.sys.attachment.service.spring.SysWaterMarkFontDataSource"></xform:customizeDataSource>
							</xform:select></td>
					</tr>
					<tr class="tr_word_type_config">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.markWord.fontSize" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:select property="markWordFontSize"
								showPleaseSelect="false" required="true" showStatus="edit"
								onValueChange="configWaterMarkFuncs.markWordFontSizeChanged">
								<xform:simpleDataSource value="16">16</xform:simpleDataSource>
								<xform:simpleDataSource value="26">26</xform:simpleDataSource>
								<xform:simpleDataSource value="36">36</xform:simpleDataSource>
								<xform:simpleDataSource value="46">46</xform:simpleDataSource>
								<xform:simpleDataSource value="56">56</xform:simpleDataSource>
							</xform:select><span> px</span></td>
					</tr>
					<tr class="tr_pic_type_config">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.markPic" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:text property="markPicFileName"
								showStatus="readOnly" required="true"></xform:text> <html:file
								property="markPicFile" accept="image/*"
								onchange="configWaterMarkFuncs.markPicChanged(this);"></html:file>
						</td>
					</tr>
					<tr class="tr_rotate_config" height="40px">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.mark.rotateType" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:radio property="markRotateType"
								showStatus="edit" required="true"
								onValueChange="configWaterMarkFuncs.markRotateTypeChanged">
								<xform:simpleDataSource value="declining">
									<bean:message
										key="watermark.hint.mark.rotateType.declining"
										bundle="sys-attachment" />
								</xform:simpleDataSource>
								<xform:simpleDataSource value="horizontal">
									<bean:message
										key="watermark.hint.mark.rotateType.horizontal"
										bundle="sys-attachment" />
								</xform:simpleDataSource>
							</xform:radio> <xform:select showPleaseSelect="false"
								onValueChange="configWaterMarkFuncs.markRotateAngelChanged"
								htmlElementProperties="id='markRotateAngel'"
								property="markRotateAngel" showStatus="edit">
								<xform:simpleDataSource value="30">30</xform:simpleDataSource>
								<xform:simpleDataSource value="45">45</xform:simpleDataSource>
								<xform:simpleDataSource value="60">60</xform:simpleDataSource>
								<xform:simpleDataSource value="120">120</xform:simpleDataSource>
								<xform:simpleDataSource value="135">135</xform:simpleDataSource>
								<xform:simpleDataSource value="150">150</xform:simpleDataSource>
								<xform:simpleDataSource value="210">210</xform:simpleDataSource>
								<xform:simpleDataSource value="225">225</xform:simpleDataSource>
								<xform:simpleDataSource value="240">240</xform:simpleDataSource>
								<xform:simpleDataSource value="300">300</xform:simpleDataSource>
								<xform:simpleDataSource value="315">315</xform:simpleDataSource>
								<xform:simpleDataSource value="330">330</xform:simpleDataSource>
							</xform:select></td>
					</tr>
					<tr class="tr_col_space">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.mark.colspace" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:select required="true"
								showPleaseSelect="false"
								onValueChange="configWaterMarkFuncs.markColSpaceChanged"
								htmlElementProperties="id='markColSpace'"
								property="markColSpace" showStatus="edit">
								<xform:simpleDataSource value="20">20</xform:simpleDataSource>
								<xform:simpleDataSource value="30">30</xform:simpleDataSource>
								<xform:simpleDataSource value="40">40</xform:simpleDataSource>
								<xform:simpleDataSource value="50">50</xform:simpleDataSource>
								<xform:simpleDataSource value="60">60</xform:simpleDataSource>
								<xform:simpleDataSource value="70">70</xform:simpleDataSource>
								<xform:simpleDataSource value="80">80</xform:simpleDataSource>
							</xform:select><span> px</span></td>
					</tr>
					<tr class="tr_row_space">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.mark.rowspace" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:select required="true"
								showPleaseSelect="false"
								onValueChange="configWaterMarkFuncs.markRowSpaceChanged"
								htmlElementProperties="id='markRowSpace'"
								property="markRowSpace" showStatus="edit">
								<xform:simpleDataSource value="20">20</xform:simpleDataSource>
								<xform:simpleDataSource value="30">30</xform:simpleDataSource>
								<xform:simpleDataSource value="40">40</xform:simpleDataSource>
								<xform:simpleDataSource value="50">50</xform:simpleDataSource>
								<xform:simpleDataSource value="60">60</xform:simpleDataSource>
								<xform:simpleDataSource value="70">70</xform:simpleDataSource>
								<xform:simpleDataSource value="80">80</xform:simpleDataSource>
								<xform:simpleDataSource value="90">90</xform:simpleDataSource>
								<xform:simpleDataSource value="100">100</xform:simpleDataSource>
								<xform:simpleDataSource value="110">110</xform:simpleDataSource>
								<xform:simpleDataSource value="120">120</xform:simpleDataSource>
							</xform:select><span> px</span></td>
					</tr>
					<tr class="tr_opacity_config">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.opacity" bundle="sys-attachment" /></td>
						<td colspan="2"><xform:select property="markOpacity"
								showPleaseSelect="false" required="true" showStatus="edit"
								onValueChange="configWaterMarkFuncs.markOpacityChanged">
								<xform:simpleDataSource value="0.1">0.1</xform:simpleDataSource>
								<xform:simpleDataSource value="0.15">0.15</xform:simpleDataSource>
								<xform:simpleDataSource value="0.2">0.2</xform:simpleDataSource>
								<xform:simpleDataSource value="0.25">0.25</xform:simpleDataSource>
								<xform:simpleDataSource value="0.3">0.3</xform:simpleDataSource>
								<xform:simpleDataSource value="0.35">0.35</xform:simpleDataSource>
								<xform:simpleDataSource value="0.4">0.4</xform:simpleDataSource>
								<xform:simpleDataSource value="0.45">0.45</xform:simpleDataSource>
								<xform:simpleDataSource value="0.5">0.5</xform:simpleDataSource>
								<xform:simpleDataSource value="0.55">0.55</xform:simpleDataSource>
								<xform:simpleDataSource value="0.6">0.6</xform:simpleDataSource>
								<xform:simpleDataSource value="0.65">0.65</xform:simpleDataSource>
								<xform:simpleDataSource value="0.7">0.7</xform:simpleDataSource>
								<xform:simpleDataSource value="0.75">0.75</xform:simpleDataSource>
								<xform:simpleDataSource value="0.8">0.8</xform:simpleDataSource>
								<xform:simpleDataSource value="0.85">0.85</xform:simpleDataSource>
								<xform:simpleDataSource value="0.9">0.9</xform:simpleDataSource>
								<xform:simpleDataSource value="0.95">0.95</xform:simpleDataSource>
								<xform:simpleDataSource value="1">1</xform:simpleDataSource>
							</xform:select></td>
					</tr>
					<tr class="tr_word_type_config">
						<td class="td_normal_title" width="15%"><bean:message
								key="watermark.hint.markWord.fontColor" bundle="sys-attachment" /></td>
						<td colspan="2"><input type="text" id="colorText"
							name="markWordFontColor"
							onchange="configWaterMarkFuncs.markWordFontColorChanged(this.value,this);"></td>
					</tr>
					<tr class="tr_demo_config">
						<td class="td_normal_title" width="100%" colspan="3"
							align="center">
							<iframe scrolling="no" style="border:0px;" id="demoFrame" src="../viewer/resource/watermark/demoWaterMark.jsp" width="800px" height="600px"></iframe>
						</td>
					</tr>
				</table>
				<div style="margin-bottom: 10px;margin-top:25px;">
					<ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="configWaterMarkFuncs.saveConfig();" order="1" ></ui:button>
				</div>
			</center>
			<script>
				$KMSSValidation();
			</script>
		</html:form>
	</template:replace>
</template:include>