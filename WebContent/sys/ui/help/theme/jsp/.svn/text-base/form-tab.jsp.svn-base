<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>

<template:replace name="content">
	<div class="lui_form_title_frame">
		<!-- 文档标题 -->
		<div class="lui_form_subject">
			协同协作Word插件配置
		</div>
		<script>
			function showAuthorBox(e) {
				var el = $("#showAuthorDIV");
				var bel = $("#authorBox");
				var tel = $(".tb_simple");
				if (e != true) {
					var tleft = tel.offset().left;
					var tw = tel.width();
					var left = bel.offset().left - 5;
					var top = bel.offset().top + 20;
					var parent = tw - (left - tleft) + 10;
					el.css({
						"top": top,
						"left": left,
						"max-width": parent
					});
				}
				el.stop(1, 1).show();
			}

			function hideAuthorBox() {
				var el = $("#showAuthorDIV");
				el.fadeOut(300);
			}
		</script>
		<!-- <div id="showAuthorDIV" onmouseover="showAuthorBox(true)" onmouseout="hideAuthorBox()">
			<a class="com_author" href="javascript:void(0) "
				ajax-href="/ekp/sys/zone/resource/zoneInfo.jsp?fdId=1708492377949409d66096d4db38c71b"
				onmouseover="if(window.LUI &amp;&amp; window.LUI.person)window.LUI.person(event,this);">郑颖玉</a>
		</div> -->
		<div class="lui_form_baseinfo">
			作者:
			<span id="authorBox" class="unfoldOrRetract" onmouseover="showAuthorBox()" onmouseout="hideAuthorBox()">
				<!-- 文档作者 -->
				<a class="com_author" href="javascript:void(0) "
					ajax-href="/ekp/sys/zone/resource/zoneInfo.jsp?fdId=1708492377949409d66096d4db38c71b"
					onmouseover="if(window.LUI &amp;&amp; window.LUI.person)window.LUI.person(event,this);">郑颖玉</a>
				<!-- 外部作者 -->
				<span class="com_author" style="display: none;">
				</span>
			</span>
			<span style="margin-right: 8px;">
				2019-08-23
			</span>
			<!-- 点评 -->
			点评<span data-lui-mark="sys.evaluation.fdEvaluateCount" class="com_number">(0)</span>
			<!-- 推荐 -->
			推荐<span data-lui-mark="sys.introduce.fdIntroduceCount" class="com_number">(0)</span>
			<!-- 阅读信息 -->
			阅读信息<span data-lui-mark="sys.readlog.fdReadCount" class="com_number">(18)</span>
		</div>
		<table class="tb_simple" width="100%">
			<tr>
				<td width="15%" class="td_normal_title" valign="top">
					文档附件
				</td>
				<td width="85%">
					<input type='hidden' name='fdTimeType_7' value='7' />
					<div class="inputselectsgl"
						onclick="selectDate('fdStartDate_7',null,setCurrTimeVal,setCurrTimeVal);" style="width:20%">
						<div class="input"><input type="text" name="fdStartDate_7" value="${queryForm.fdStartDate }">
						</div>
						<div class="inputdatetime"></div>
					</div>
					<span style="position:relative;top:-7px;">
						<bean:message bundle="km-imeeting" key="kmImeetingStat.fdDateType.to" /></span>
					<div class="inputselectsgl" onclick="selectDate('fdEndDate_7',null,setCurrTimeVal,setCurrTimeVal);"
						style="width:20%">
						<div class="input"><input type="text" name="fdEndDate_7" value="${queryForm.fdEndDate }"></div>
						<div class="inputdatetime"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title" valign="top">标签</td>
				<td width="85%">
					<xform:address required="false" validators=""
						subject="${ lfn:message('kms-multidoc:kmsMultidoc.form.main.docDeptId') }" style="width:95%"
						propertyId="docDeptId" propertyName="docDeptName" orgType='ORG_TYPE_ORGORDEPT'>
					</xform:address>
					<div class="inputselectsgl" onclick="modifySecondCate(true);" style="width:96%"><input
							name="docSecondCategoriesIds" value="16c2e033f064574c18a14c04943a1342" type="hidden">
						<div class="input"><input name="docSecondCategoriesNames" value="江远涛" type="text" readonly="">
						</div>
						<div class="selectitem"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title" valign="top">所属部门</td>
				<td width="85%">
					<div class="inputselectsgl" onclick="modifySecondCate(true);" style="width:96%"><input
							name="docSecondCategoriesIds" value="16c2e033f064574c18a14c04943a1342" type="hidden">
						<div class="input"><input name="docSecondCategoriesNames" value="江远涛" type="text" readonly="">
						</div>
						<div class="orgelement"></div>
					</div>
				</td>
			</tr>

			</tr>
		</table>
		<ui:tabpage expand="false" var-navwidth="90%">
			<ui:content title="基本信息">
				<table class="tb_normal" width="100%">
					<style>
						.div_img {}

						.div_img span {
							position: relative;
							top: 6px
						}
					</style>
					<tbody>
						<tr>
							<td class="td_normal_title" width="15%">
								可阅读者
							</td>
							<td width="85%">所有内部人员</td>
						</tr>
						<tr>
							<td class="td_normal_title">可编辑者</td>
							<td>
								只有相关人员可以编辑
							</td>
						</tr>

						<tr>
							<td class="td_normal_title" width="15%">附件权限</td>
							<td width="85%">

								可拷贝者
								：

								<input type="hidden" name="authAttNocopy" value="false">



								&lt;所有内部人员&gt;



								<br>




								可下载者
								：

								<input type="hidden" name="authAttNodownload" value="false">



								&lt;所有内部人员&gt;



								<br>




								可打印者
								：

								<input type="hidden" name="authAttNoprint" value="false">



								&lt;所有内部人员&gt;




								<div class="div_img">
									支持的附件类型：
									<span><img src="/ekp/sys/right/resource/images/Word.png" height="26" width="26"
											title="MS Word "></span>
									<span><img src="/ekp/sys/right/resource/images/Excel.png" height="26" width="26"
											title="MS Excel"></span>
									<span><img src="/ekp/sys/right/resource/images/PowerPoint.png" height="26"
											width="26" title="MS PowerPoint"></span>
									<span><img src="/ekp/sys/right/resource/images/Project.png" height="26" width="26"
											title="MS Project"></span>
									<span><img src="/ekp/sys/right/resource/images/Visio.png" height="26" width="26"
											title="MS Visio"></span>

								</div>
							</td>
						</tr>


					</tbody>
				</table>
			</ui:content>
			<ui:content title="点评">
				基本信息
			</ui:content>
			<ui:content title="纠错记录">
				基本信息
			</ui:content>
			<ui:content title="访问统计">
				访问统计
			</ui:content>
			<ui:content title="权限">
				权限
			</ui:content>
		</ui:tabpage>


		<div class="lui_tabpage_frame" style="display:none">
			<div class="lui_tabpage_float_contents">
				<div class="lui_tabpage_float_content">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">
										<div id="eval_label_title">点评</div>
									</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-30"
											class="lui-component" data-lui-cid="lui-id-30" data-lui-parse-init="24">























											<div class="eval_EditMain" id="eval_EditMain">
												<input type="hidden" name="fdEvaluationTime">
												<input type="hidden" name="fdKey">
												<input type="hidden" name="fdModelId"
													value="16cbcbab019d6f75788c5af41b880edb">
												<input type="hidden" name="fdModelName"
													value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge">
												<select name="fdEvaluationScore" class="eval_hidden">
													<option value="0">非常好</option>
													<option value="1" selected="">很好</option>
													<option value="2">好</option>
													<option value="3">一般</option>
													<option value="4">差</option>
												</select>
												<table class="tb_simple eval_opt_table" width="100%" border="0"
													cellspacing="0" cellpadding="0">
													<tbody>
														<tr id="eval_EditMain_new_msg" style="display:table-row">
															<td colspan="2">
																<span class="eval_title eval_title_color">我来点评</span>
																<ul class="eval_star">
																	<li id="eval_star_4" star="4"
																		class="lui_icon_s_stargood"></li>
																	<li id="eval_star_3" star="3"
																		class="lui_icon_s_stargood"></li>
																	<li id="eval_star_2" star="2"
																		class="lui_icon_s_stargood"></li>
																	<li id="eval_star_1" star="1"
																		class="lui_icon_s_stargood"></li>
																	<li id="eval_star_0" star="0"
																		class="lui_icon_s_starbad"></li>
																</ul>
																<span id="eval_level" class="eval_level">很好</span>
															</td>
														</tr>
														<tr id="eval_EditMain_add_msg" style="display:none">
															<td colspan="2">
																<span class="eval_title eval_title_color">追加评论</span>
															</td>
														</tr>
														<tr>
															<td valign="top" class="lui_eval_add_content">
																<!-- 
							<textarea name="fdEvaluationContent" class="eval_content">
							 </textarea>
						  -->
																<iframe name="fdEvaluationContent" id="mainIframe"
																	class="eval_content_iframe"
																	src="/ekp/sys/evaluation/import/sysEvaluationMain_content_area.jsp?iframeType=eval&amp;iframeId=mainIframe"
																	frameborder="0">
																</iframe>
															</td>
															<td align="center" valign="top" class="lui_eval_add_btn">
																<input id="eval_button" class="eval_button"
																	type="button" value="提交">
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<label>
																	<span class="eval_icons"
																		data-iframe-uid="mainIframe">
																		表情
																	</span>
																	<div class="eval_biaoqing"></div>
																</label>


																&nbsp;&nbsp;&nbsp;&nbsp;
																<label class="eval_notify eval_summary_color">
																	<input name="isNotify" type="checkbox" value=""
																		onclick="var isNotify = document.getElementsByName('isNotify');if(isNotify[0].checked){isNotify[0].value='yes'}else{isNotify[0].value=''}">
																	通知创建者
																</label>
																<label class="eval_notify eval_summary_color">

																	<input name="notifyOther" type="checkbox" value=""
																		onclick="var notifyOther = document.getElementsByName('notifyOther');if(notifyOther[0].checked){notifyOther[0].value='fdDocAuthorList'}else{notifyOther[0].value=''}">
																	通知作者

																</label>
																&nbsp;&nbsp;&nbsp;&nbsp;
																<label class="eval_notify eval_summary_color">
																	通知方式：<input
																		id="__notify_type_173bdccbf4bed7b6034309e4cff9ecd1_fdNotifyType"
																		type="hidden" name="fdNotifyType" value="todo">
																	<label><input type="checkbox"
																			name="__notify_type_173bdccbf4bed7b6034309e4cff9ecd1"
																			value="todo" checked=""
																			onclick="var fields=document.getElementsByName('__notify_type_173bdccbf4bed7b6034309e4cff9ecd1');var values='';for(var i=0; i<fields.length; i++) if(fields[i].checked) values+=';'+fields[i].value;if(values!='') document.getElementById('__notify_type_173bdccbf4bed7b6034309e4cff9ecd1_fdNotifyType').value=values.substring(1);else document.getElementById('__notify_type_173bdccbf4bed7b6034309e4cff9ecd1_fdNotifyType').value='';">待办</label>

																</label>


																<span class="eval_prompt" style="display: none;">还可以输入
																	<font
																		style="font-family: Constantia, Georgia; font-size: 20px;">
																		989</font>字</span>
															</td>
														</tr>


														<tr>
															<td colspan="3">
																<div id="lui-id-133" class="lui-component"
																	data-lui-cid="lui-id-133">
																	<div
																		id="_List_eval_173bdccbf4649d788718b5043af8636a_Attachment_Table">
																		<input type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.fdModelId"><input
																			type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.fdModelName"><input
																			type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.fdKey"
																			value="eval_173bdccbf4649d788718b5043af8636a"><input
																			type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.fdAttType"><input
																			type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.fdMulti"><input
																			type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.deletedAttachmentIds"><input
																			type="hidden"
																			name="attachmentForms.eval_173bdccbf4649d788718b5043af8636a.attachmentIds">
																	</div>
																	<div
																		id="attachmentObject_eval_173bdccbf4649d788718b5043af8636a_content_div">
																		<table
																			id="uploader_eval_173bdccbf4649d788718b5043af8636a"
																			class="tb_noborder" width="100%" border="0"
																			cellspacing="0" cellpadding="0">
																			<tbody>
																				<tr class="uploader">
																					<td>
																						<div id="uploader_eval_173bdccbf4649d788718b5043af8636a_queueList"
																							class="lui_queueList lui_queueList_s lui_queueList_block"
																							style="width:100%;">
																							<div id="uploader_eval_173bdccbf4649d788718b5043af8636a_container"
																								class="lui_upload_container webuploader-container">
																								<div
																									class="webuploader-pick">
																									<i></i>上传文件，请<span
																										id="upload_eval_173bdccbf4649d788718b5043af8636a_div_buttom"
																										class="lui_text_primary">点击上传</span>
																								</div>
																								<div id="rt_rt_1eeuspjqbmlnk9da9g1p1ddmk1"
																									style="position: absolute; top: 0px; left: 0px; width: 947px; height: 24px; overflow: hidden; bottom: auto; right: auto;">
																									<input type="file"
																										name="__landray_fileeval_173bdccbf4649d788718b5043af8636a"
																										class="webuploader-element-invisible"
																										multiple="multiple"
																										accept="*"><label
																										style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
																								</div>
																							</div>
																						</div>
																					</td>
																				</tr>
																				<tr>
																					<td data-lui-mark="attachmentlist">
																						<div
																							id="att_xtable_eval_173bdccbf4649d788718b5043af8636a">
																							<div
																								class="lui_upload_tip tip_info upload_item_hide">
																								<i></i>提示：拖动内容可排序</div>
																						</div>
																					</td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</div>

											<!-- 是否有段落点评权限 -->
											<input type="hidden" name="hasNotesRight" value="true">

											<!-- 是否有点评回复权限 -->
											<input type="hidden" name="hasReplyRight" value="true">
											<!-- 是否有删除全文点评回复权限 -->
											<input type="hidden" name="delReplyRight" value="true">
											<input type="hidden" name="showReplyInfo" value="">
											<div id="eval_ViewMain">


												<!-- 不开启段落点评  -->
												<div id="eval_main" style="height: auto;">
													<div class="eval_title eval_title_color">
														点评信息
													</div>


























													<!-- 点赞 -->

























													<script type="text/javascript">
														// 一个页面多次引入这个，粗暴解决
														if (!praOpened) {
															Com_AddEventListener(window, "load", initLoad);
														}

														var praOpened = "false"; //人员列表框是否显示

														function initPraise() {

															$("span[praise-data-modelid]").on("mouseenter", function (praiseObj) {
																closeListBox(praiseObj);
																if (praOpened == "false") {
																	praOpened = "true";
																	var _praiseId = $(praiseObj.currentTarget).attr("praise-data-modelid");
																	$("#aid_" + _praiseId + " #praisedPerson_list").css("height", "0px");
																	var hasPraiser = getPraisers(_praiseId, 1, false, false);
																	if (hasPraiser == true) {
																		$(praiseObj.currentTarget).parent().find("div[class='lui_praise_layer']").show();
																	}
																}
															});

															$("span[praise-data-modelid]").on("mouseleave", function (obj) {
																if (praOpened == "true") {
																	praOpened = "false";
																}
																setTimeout(function () {
																	if (praOpened == "false") {
																		$(obj.currentTarget).parent().find("div[class='lui_praise_layer']")
																			.hide();
																	}
																}, 1000);
															});
														}

														function initLoad() {
															initPraise();

															//关闭人员列表框
															$("#praise_close").delegate('.lui_praise_close_s', 'click', function (evt) {
																closeListBox(evt);
															});

															$(document).on("click", function (evt) {
																closeListBox(evt);
															});
														}

														//点赞
														function sysPraise(praiseId) {
															var praiseObj = $("#aid_" + praiseId);
															var fdModelId = praiseObj.attr("praise-data-modelid");
															var fdModelName = praiseObj.attr("praise-data-modelname");
															LUI.$.ajax({
																type: "POST",
																url: "/ekp/sys/praise/sys_praise_main/sysPraiseMain.do?method=executePraise",
																data: {
																	fdModelId: fdModelId,
																	fdModelName: fdModelName
																},
																dataType: 'text',
																async: false,
																success: function (data) {
																	var praiseCount = parseInt($("#aid_" + praiseId + ' #praise_count')[0].innerHTML);
																	var isPraised = $("#check_" + praiseId).val();
																	var totalCount = 0;
																	if ("true" == isPraised) {
																		$("#check_" + praiseId).val("false");
																		totalCount = praiseCount - 1;
																		if (totalCount < 0) {
																			totalCount = 0;
																		}
																		$("#aid_" + praiseId + ' #praise_count')[0].innerHTML = totalCount;

																		$("#aid_" + praiseId + ' #praise_icon').removeClass("sys_unpraise").addClass(
																			"sys_praise");
																		$("span[data-lui-id='" + fdModelId + "']").attr("title", "赞");
																	} else {
																		$("#check_" + praiseId).val("true");
																		totalCount = praiseCount + 1;
																		if (totalCount < 0) {
																			totalCount = 0;
																		}
																		$("#aid_" + praiseId + ' #praise_count')[0].innerHTML = totalCount;

																		$("#aid_" + praiseId + ' #praise_icon').removeClass("sys_praise").addClass(
																			"sys_unpraise");
																		$("span[data-lui-id='" + fdModelId + "']").attr("title", "取消赞");
																	}

																	seajs.use('lui/topic', function (topic) {
																		topic.publish('praise.change', {
																			count: totalCount
																		});
																	});

																},
																error: function () {

																}
															});
														}

														//列出所有点赞人
														function listAllPraiser(praiseId) {
															var personListBox = $("#aid_" + praiseId + " #praisedPerson_list");
															praOpened = ""; //不为true也不为false
															personListBox.empty();
															personListBox.animate({
																height: "80px"
															}, "normal", function () {
																$("#aid_" + praiseId + " #praise_page_list").show();
																$("#aid_" + praiseId + " #praise_close").show();
																getPraisers(praiseId, 1, true, true);
															});
														}

														//根据当前事件，选择性关闭人员列表框
														function closeListBox(evt) {
															if ($(evt.target).length > 0) {
																var targetId = $(evt.target)[0].id; //“更多”按钮
																var closeBtn = $(evt.target)[0].className; //人员列表框关闭按钮
																var clickObj = $(evt.target).parents(".lui_praise_layer").length;
																//当点击的区域为人员列表框外，则隐藏列表框
																if ((targetId != "show_more" && clickObj <= 0) ||
																	closeBtn == "lui_praise_close_s") {
																	praOpened = "false";
																	$(".lui_praise_layer").hide();
																	//$('.person_list').attr({style:"height:35px;"});
																	$(".praise_page_list").hide(); //翻页
																	$(".lui_praise_close_d").hide(); //关闭按钮
																}
															}
														}

														//翻页列出点赞人
														function getPraisers(praiseId, _pageno, _isShowAll, _isShowPage) {
															var _praiseObj = $("#aid_" + praiseId);
															var fdModelId = _praiseObj.attr("praise-data-modelid");
															var fdModelName = _praiseObj.attr("praise-data-modelname");
															var _showPraiserCount = $("input[name='showPraiserCount']").val();

															var hasPraiser = true;
															if (_showPraiserCount == "") {
																_showPraiserCount = '3';
															}
															LUI.$
																.ajax({
																	type: "POST",
																	url: "/ekp/sys/praise/sys_praise_main/sysPraiseMain.do?method=getPraisedPersons",
																	data: {
																		fdModelId: fdModelId,
																		fdModelName: fdModelName,
																		pageno: _pageno,
																		isShowAll: _isShowAll,
																		isShowPage: _isShowPage,
																		showPraiserCount: _showPraiserCount
																	},
																	dataType: 'json',
																	async: false,
																	success: function (data) {
																		$("#praisedPerson_list").empty();
																		//翻页
																		if (_isShowPage) {
																			var nextPage = data['nextPage'];
																			var prePage = data['prePage'];
																			if (prePage) {
																				$("#aid_" + praiseId + " #btn_preno").parent()
																					.show();
																				$("#aid_" + praiseId + " #btn_nextno").parent()
																					.show();
																				if (nextPage) {
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"class", "praise_icon_l");
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"data-praise-mark", "1");

																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("class", "praise_icon_r");
																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("data-praise-mark", "1");
																				} else {
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"class", "praise_icon_l");
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"data-praise-mark", "1");

																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("class", "praise_icon_r_gray");
																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("data-praise-mark", "0");
																				}
																			} else {
																				if (nextPage) {
																					$("#aid_" + praiseId + " #btn_preno")
																						.parent().show();
																					$("#aid_" + praiseId + " #btn_nextno")
																						.parent().show();

																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"class", "praise_icon_l_gray");
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"data-praise-mark", "0");

																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("class", "praise_icon_r");
																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("data-praise-mark", "1");
																				} else {
																					$("#aid_" + praiseId + " #praise_page_list")
																						.hide();
																				}
																			}

																		}

																		var personList = data['personsList'];
																		$("#aid_" + praiseId + " #praisedPerson_list").empty();
																		var _plist = "";
																		for (var i = 0; i < personList.length; i++) {
																			_plist += "<li><a href='" +
																				Com_Parameter.ContextPath +
																				"sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" +
																				personList[i].personId +
																				"' target='_blank'><img title='" + personList[i].personName +
																				"' width='30' height='30' src='" + personList[i].imgUrl + "'></a></li>";
																		}
																		$("#aid_" + praiseId + " #praisedPerson_list").html(
																			_plist);
																		//更多
																		if (data['hasMore'])
																			$("#aid_" + praiseId + " #praisedPerson_list")
																			.append(
																				"<li><a><div id='show_more' class='praise_more' onclick='listAllPraiser(\"" +
																				praiseId + "\")'>" +
																				"</div></a></li>");
																		//没人点赞过
																		if (personList.length <= 0) {
																			hasPraiser = false;
																		}
																	},
																	error: function () {

																	}
																});
															return hasPraiser;
														}
														//翻页
														var pageno = 1;
														//上一页
														function prePage(objId) {
															var data_mark = $("#aid_" + objId + " #btn_preno").attr(
																"data-praise-mark");
															if (data_mark == 1) {
																getPraisers(objId, --pageno, true, true);
															}
														}
														//下一页
														function nextPage(objId) {
															var data_mark = $("#aid_" + objId + " #btn_nextno").attr(
																"data-praise-mark");
															if (data_mark == 1) {
																getPraisers(objId, ++pageno, true, true);
															}
														}

														function updatePraiseStatus(praiseModelIds, fdModelName) {
															//对已赞过的换用“已赞”图标
															var fdModelIds = praiseModelIds.join(",");
															LUI.$
																.ajax({
																	type: "POST",
																	url: Com_Parameter.ContextPath +
																		"sys/praise/sys_praise_main/sysPraiseMain.do?method=checkPraisedByIds",
																	data: {
																		fdModelIds: fdModelIds,
																		fdModelName: fdModelName
																	},
																	dataType: 'json',
																	async: false,
																	success: function (data) {
																		for (var i = 0; i < data["praiseIds"].length; i++) {
																			var praisedId = data["praiseIds"][i];
																			$("#check_" + praisedId).val("true");
																			$(
																				"span[data-lui-id='" + praisedId +
																				"'] #praise_icon").attr("class",
																				"sys_unpraise");
																			$("span[data-lui-id='" + praisedId + "']")
																				.attr("title",
																					"取消赞");
																		}
																	},
																	error: function () {

																	}
																});
														}
													</script>
													<script type="text/javascript">
														seajs.use(['/ekp/sys/praise/style/view.css']);
														seajs.use(['lui/topic', 'lui/jquery'], function (topic, $) {
															var listChannel = "eval_chl";
															var praiseAreaName = "main_view";
															topic.channel(listChannel).subscribe("list.loaded", function (evt) {
																var praiseModelIds = [];
																var fdModelName = $("#" + praiseAreaName + " .eval_praise_reply").attr(
																	"eval-view-modelname");
																$("#" + praiseAreaName + " .eval_reply_infos").each(function () {
																	praiseModelIds.push($(this).attr("id"));
																});
																if (praiseModelIds.length > 0) {
																	//调用点赞中方法，对已赞过的换用“已赞”图标
																	updatePraiseStatus(praiseModelIds, fdModelName);
																}
															});
														});
													</script>


























													<script type="text/javascript">
														// 一个页面多次引入这个，粗暴解决
														if (!praOpened) {
															Com_AddEventListener(window, "load", initLoad);
														}

														var praOpened = "false"; //人员列表框是否显示

														function initPraise() {

															$("span[praise-data-modelid]").on("mouseenter", function (praiseObj) {
																closeListBox(praiseObj);
																if (praOpened == "false") {
																	praOpened = "true";
																	var _praiseId = $(praiseObj.currentTarget).attr("praise-data-modelid");
																	$("#aid_" + _praiseId + " #praisedPerson_list").css("height", "0px");
																	var hasPraiser = getPraisers(_praiseId, 1, false, false);
																	if (hasPraiser == true) {
																		$(praiseObj.currentTarget).parent().find("div[class='lui_praise_layer']").show();
																	}
																}
															});

															$("span[praise-data-modelid]").on("mouseleave", function (obj) {
																if (praOpened == "true") {
																	praOpened = "false";
																}
																setTimeout(function () {
																	if (praOpened == "false") {
																		$(obj.currentTarget).parent().find("div[class='lui_praise_layer']")
																			.hide();
																	}
																}, 1000);
															});
														}

														function initLoad() {
															initPraise();

															//关闭人员列表框
															$("#praise_close").delegate('.lui_praise_close_s', 'click', function (evt) {
																closeListBox(evt);
															});

															$(document).on("click", function (evt) {
																closeListBox(evt);
															});
														}

														//点赞
														function sysPraise(praiseId) {
															var praiseObj = $("#aid_" + praiseId);
															var fdModelId = praiseObj.attr("praise-data-modelid");
															var fdModelName = praiseObj.attr("praise-data-modelname");
															LUI.$.ajax({
																type: "POST",
																url: "/ekp/sys/praise/sys_praise_main/sysPraiseMain.do?method=executePraise",
																data: {
																	fdModelId: fdModelId,
																	fdModelName: fdModelName
																},
																dataType: 'text',
																async: false,
																success: function (data) {
																	var praiseCount = parseInt($("#aid_" + praiseId + ' #praise_count')[0].innerHTML);
																	var isPraised = $("#check_" + praiseId).val();
																	var totalCount = 0;
																	if ("true" == isPraised) {
																		$("#check_" + praiseId).val("false");
																		totalCount = praiseCount - 1;
																		if (totalCount < 0) {
																			totalCount = 0;
																		}
																		$("#aid_" + praiseId + ' #praise_count')[0].innerHTML = totalCount;

																		$("#aid_" + praiseId + ' #praise_icon').removeClass("sys_unpraise").addClass(
																			"sys_praise");
																		$("span[data-lui-id='" + fdModelId + "']").attr("title", "赞");
																	} else {
																		$("#check_" + praiseId).val("true");
																		totalCount = praiseCount + 1;
																		if (totalCount < 0) {
																			totalCount = 0;
																		}
																		$("#aid_" + praiseId + ' #praise_count')[0].innerHTML = totalCount;

																		$("#aid_" + praiseId + ' #praise_icon').removeClass("sys_praise").addClass(
																			"sys_unpraise");
																		$("span[data-lui-id='" + fdModelId + "']").attr("title", "取消赞");
																	}

																	seajs.use('lui/topic', function (topic) {
																		topic.publish('praise.change', {
																			count: totalCount
																		});
																	});

																},
																error: function () {

																}
															});
														}

														//列出所有点赞人
														function listAllPraiser(praiseId) {
															var personListBox = $("#aid_" + praiseId + " #praisedPerson_list");
															praOpened = ""; //不为true也不为false
															personListBox.empty();
															personListBox.animate({
																height: "80px"
															}, "normal", function () {
																$("#aid_" + praiseId + " #praise_page_list").show();
																$("#aid_" + praiseId + " #praise_close").show();
																getPraisers(praiseId, 1, true, true);
															});
														}

														//根据当前事件，选择性关闭人员列表框
														function closeListBox(evt) {
															if ($(evt.target).length > 0) {
																var targetId = $(evt.target)[0].id; //“更多”按钮
																var closeBtn = $(evt.target)[0].className; //人员列表框关闭按钮
																var clickObj = $(evt.target).parents(".lui_praise_layer").length;
																//当点击的区域为人员列表框外，则隐藏列表框
																if ((targetId != "show_more" && clickObj <= 0) ||
																	closeBtn == "lui_praise_close_s") {
																	praOpened = "false";
																	$(".lui_praise_layer").hide();
																	//$('.person_list').attr({style:"height:35px;"});
																	$(".praise_page_list").hide(); //翻页
																	$(".lui_praise_close_d").hide(); //关闭按钮
																}
															}
														}

														//翻页列出点赞人
														function getPraisers(praiseId, _pageno, _isShowAll, _isShowPage) {
															var _praiseObj = $("#aid_" + praiseId);
															var fdModelId = _praiseObj.attr("praise-data-modelid");
															var fdModelName = _praiseObj.attr("praise-data-modelname");
															var _showPraiserCount = $("input[name='showPraiserCount']").val();

															var hasPraiser = true;
															if (_showPraiserCount == "") {
																_showPraiserCount = '3';
															}
															LUI.$
																.ajax({
																	type: "POST",
																	url: "/ekp/sys/praise/sys_praise_main/sysPraiseMain.do?method=getPraisedPersons",
																	data: {
																		fdModelId: fdModelId,
																		fdModelName: fdModelName,
																		pageno: _pageno,
																		isShowAll: _isShowAll,
																		isShowPage: _isShowPage,
																		showPraiserCount: _showPraiserCount
																	},
																	dataType: 'json',
																	async: false,
																	success: function (data) {
																		$("#praisedPerson_list").empty();
																		//翻页
																		if (_isShowPage) {
																			var nextPage = data['nextPage'];
																			var prePage = data['prePage'];
																			if (prePage) {
																				$("#aid_" + praiseId + " #btn_preno").parent()
																					.show();
																				$("#aid_" + praiseId + " #btn_nextno").parent()
																					.show();
																				if (nextPage) {
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"class", "praise_icon_l");
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"data-praise-mark", "1");

																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("class", "praise_icon_r");
																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("data-praise-mark", "1");
																				} else {
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"class", "praise_icon_l");
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"data-praise-mark", "1");

																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("class", "praise_icon_r_gray");
																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("data-praise-mark", "0");
																				}
																			} else {
																				if (nextPage) {
																					$("#aid_" + praiseId + " #btn_preno")
																						.parent().show();
																					$("#aid_" + praiseId + " #btn_nextno")
																						.parent().show();

																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"class", "praise_icon_l_gray");
																					$("#aid_" + praiseId + " #btn_preno").attr(
																						"data-praise-mark", "0");

																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("class", "praise_icon_r");
																					$("#aid_" + praiseId + " #btn_nextno")
																						.attr("data-praise-mark", "1");
																				} else {
																					$("#aid_" + praiseId + " #praise_page_list")
																						.hide();
																				}
																			}

																		}

																		var personList = data['personsList'];
																		$("#aid_" + praiseId + " #praisedPerson_list").empty();
																		var _plist = "";
																		for (var i = 0; i < personList.length; i++) {
																			_plist += "<li><a href='" +
																				Com_Parameter.ContextPath +
																				"sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" +
																				personList[i].personId +
																				"' target='_blank'><img title='" + personList[i].personName +
																				"' width='30' height='30' src='" + personList[i].imgUrl + "'></a></li>";
																		}
																		$("#aid_" + praiseId + " #praisedPerson_list").html(
																			_plist);
																		//更多
																		if (data['hasMore'])
																			$("#aid_" + praiseId + " #praisedPerson_list")
																			.append(
																				"<li><a><div id='show_more' class='praise_more' onclick='listAllPraiser(\"" +
																				praiseId + "\")'>" +
																				"</div></a></li>");
																		//没人点赞过
																		if (personList.length <= 0) {
																			hasPraiser = false;
																		}
																	},
																	error: function () {

																	}
																});
															return hasPraiser;
														}
														//翻页
														var pageno = 1;
														//上一页
														function prePage(objId) {
															var data_mark = $("#aid_" + objId + " #btn_preno").attr(
																"data-praise-mark");
															if (data_mark == 1) {
																getPraisers(objId, --pageno, true, true);
															}
														}
														//下一页
														function nextPage(objId) {
															var data_mark = $("#aid_" + objId + " #btn_nextno").attr(
																"data-praise-mark");
															if (data_mark == 1) {
																getPraisers(objId, ++pageno, true, true);
															}
														}

														function updatePraiseStatus(praiseModelIds, fdModelName) {
															//对已赞过的换用“已赞”图标
															var fdModelIds = praiseModelIds.join(",");
															LUI.$
																.ajax({
																	type: "POST",
																	url: Com_Parameter.ContextPath +
																		"sys/praise/sys_praise_main/sysPraiseMain.do?method=checkPraisedByIds",
																	data: {
																		fdModelIds: fdModelIds,
																		fdModelName: fdModelName
																	},
																	dataType: 'json',
																	async: false,
																	success: function (data) {
																		for (var i = 0; i < data["praiseIds"].length; i++) {
																			var praisedId = data["praiseIds"][i];
																			$("#check_" + praisedId).val("true");
																			$(
																				"span[data-lui-id='" + praisedId +
																				"'] #praise_icon").attr("class",
																				"sys_unpraise");
																			$("span[data-lui-id='" + praisedId + "']")
																				.attr("title",
																					"取消赞");
																		}
																	},
																	error: function () {

																	}
																});
														}
													</script>
													<script type="text/javascript">
														seajs.use(['/ekp/sys/praise/style/view.css']);
														seajs.use(['lui/topic', 'lui/jquery'], function (topic, $) {
															var listChannel = "eval_my";
															var praiseAreaName = "my_view";
															topic.channel(listChannel).subscribe("list.loaded", function (evt) {
																var praiseModelIds = [];
																var fdModelName = $("#" + praiseAreaName + " .eval_praise_reply").attr(
																	"eval-view-modelname");
																$("#" + praiseAreaName + " .eval_reply_infos").each(function () {
																	praiseModelIds.push($(this).attr("id"));
																});
																if (praiseModelIds.length > 0) {
																	//调用点赞中方法，对已赞过的换用“已赞”图标
																	updatePraiseStatus(praiseModelIds, fdModelName);
																}
															});
														});
													</script>

													<div id="eval_my_title" class="lui_evaluation_title"
														style="display: none;"></div>
													<div id="my_view" data-lui-type="lui/listview/listview!ListView"
														style="" class="listview" data-lui-cid="my_view"
														data-lui-parse-init="26">



														<div data-lui-type="lui/listview/rowtable!RowTable" style=""
															id="lui-id-33" class="rowTable" data-lui-cid="lui-id-33"
															data-lui-parse-init="28"></div>

													</div>


													<div id="eval_chl_title" class="lui_evaluation_title"
														style="display: none;"></div>
													<div id="main_view" data-lui-type="lui/listview/listview!ListView"
														style="" class="listview" data-lui-cid="main_view"
														data-lui-parse-init="31">



														<div data-lui-type="lui/listview/rowtable!RowTable" style=""
															id="lui-id-37" class="rowTable" data-lui-cid="lui-id-37"
															data-lui-parse-init="33">


															<div class="prompt_container" style="text-align: left;">
																很抱歉，未找到符合条件的记录！</div>
														</div>

													</div>

													<div id="lui_evalNote_hidden" style="display: none">
														<div style="width:600px;height:400px;padding-bottom:10px">
															<div class="lui_eval_eva_title">点评对象
															</div>
															<div class="lui_form_summary_frame lui_eval_eva_object"
																id="lui_evalNote_subject">
																<p>subject</p>
															</div>
															<div class="lui_eval_eva_title ">点评语
															</div>
															<div class="lui_eval_eva_content">
																<textarea onkeyup="checkWordsCount(this)"
																	id="eval_eva_content"
																	style="width:98.5%"></textarea>
															</div>
															<span class="eval_reply_tip">还可以输入
																<font
																	style="font-family: Constantia, Georgia; font-size: 20px;">
																	300
																</font>字
															</span>
															<div style="clear:both">
															</div>

															<div class="lui_eval_eva_content">
																通知对象：


																<label class="eval_notify eval_summary_color">
																	<input name="isNotify1" type="checkbox"
																		value="docAuthor"
																		onclick="var isNotify1 = document.getElementsByName('isNotify1');if(isNotify1[0].checked){isNotify1[0].value='docAuthor'}else{isNotify1[0].value=''}">
																	作者
																</label>

																&nbsp;&nbsp;


																<label class="eval_notify eval_summary_color2">
																	<input name="isNotify2" type="checkbox"
																		value="docCreator"
																		onclick="var isNotify2 = document.getElementsByName('isNotify2');if(isNotify2[0].checked){isNotify2[0].value='docCreator'}else{isNotify2[0].value=''}">
																	创建者
																</label>



																&nbsp;&nbsp;
																<label class="eval_notify eval_summary_color3">
																	<input name="isNotify3" type="checkbox"
																		value="docAlteror"
																		onclick="var isNotify3 = document.getElementsByName('isNotify3');if(isNotify3[0].checked){isNotify3[0].value='docAlteror'}else{isNotify3[0].value=''}">
																	最后更新者
																</label>


																<br>
																<label class="eval_notify eval_summary_color">
																	通知方式：<input
																		id="__notify_type_173bdccbf53a458b8b1e3434e1aa086c_fdNotifyType"
																		type="hidden" name="fdNotifyType" value="todo">
																	<label><input type="checkbox"
																			name="__notify_type_173bdccbf53a458b8b1e3434e1aa086c"
																			value="todo" checked=""
																			onclick="var fields=document.getElementsByName('__notify_type_173bdccbf53a458b8b1e3434e1aa086c');var values='';for(var i=0; i<fields.length; i++) if(fields[i].checked) values+=';'+fields[i].value;if(values!='') document.getElementById('__notify_type_173bdccbf53a458b8b1e3434e1aa086c_fdNotifyType').value=values.substring(1);else document.getElementById('__notify_type_173bdccbf53a458b8b1e3434e1aa086c_fdNotifyType').value='';">待办</label>

																</label>
																<span class="eval_prompt" style="display: none;">还可以输入
																	<font
																		style="font-family: Constantia, Georgia; font-size: 20px;">
																		989</font>字</span>
															</div>
														</div>
													</div>

													<div data-lui-type="lui/listview/paging!Paging"
														style="display:none;" id="lui-id-40" class="lui-component"
														data-lui-cid="lui-id-40" data-lui-parse-init="36"></div>
												</div>




											</div>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">
										<div id="intr_label_title" name="introviewnames">推荐</div>
									</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-42"
											class="lui-component" data-lui-cid="lui-id-42" data-lui-parse-init="38">


											<script>
												Com_IncludeFile("form.js");
												var intr_lang = {
													'intr_star_2': '值得一看',
													'intr_star_1': '重点推荐',
													'intr_star_0': '强力推荐',
													'intr_prompt_1': '还可以输入',
													'intr_prompt_2': '已经超过',
													'intr_prompt_3': '字',
													'intr_prompt_sucess': '您的推荐已经提交成功！',
													'intr_type_select': '请至少选择一种推荐类型！',
													'intr_select_person': '请选择推荐对象!',
													'intr_repetition_message': '不能重复推荐相同的对象!',
													'intr_introfalse_message': '推荐失败！请查询默认流程等相关设置',
													'intr_introcheckfalse_message': '推荐失败！文档已存在于本模块精华库！'
												};
												seajs.use(['/ekp/sys/introduce/import/resource/intr.css']);
												Com_IncludeFile("intr.js", "/ekp/sys/introduce/import/resource/", "js", true);
											</script>
											<script id="form_js" src="/ekp/resource/js/form.js?s_cache=1596422898836">
											</script>
											<script id="intr_js"
												src="/ekp/sys/introduce/import/resource/intr.js?s_cache=1596422898836">
											</script>
											<script id="validator_jsp"
												src="/ekp/resource/js/validator.jsp?s_cache=1596422898836"></script>


											<script>
												if (window.intr_opt == null) {
													window.intr_opt = new IntroduceOpt("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",
														"16cbcbab019d6f75788c5af41b880edb", "mainDoc", intr_lang);
												}
											</script>




























											<div id="intr_editMain" class="intr_editMain">
												<input type="hidden" name="fdKey" value="mainDoc">
												<input type="hidden" name="fdModelId"
													value="16cbcbab019d6f75788c5af41b880edb">
												<input type="hidden" name="fdModelName"
													value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge">
												<input type="hidden" name="docSubject" value="协同协作Word插件配置">
												<input type="hidden" name="fdCateModelId"
													value="16b9bb71038524ef376696447adad081">
												<input type="hidden" name="fdCateModelName"
													value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory">
												<select name="fdIntroduceGrade" class="intr_hidden">
													<option value="0" selected="">强力推荐</option>
													<option value="1">重点推荐</option>
													<option value="2">值得一看</option>
												</select>
												<table class="tb_simple intr_opt_table" width="100%" border="0"
													cellspacing="0" cellpadding="0">
													<tbody>
														<tr>
															<td width="80%" colspan="2" class="intr_oper">
																<span class="intr_title intr_title_color">推荐级别</span>
																<span>
																	<ul class="intr_star">
																		<li id="intr_star_2" star="2"></li>
																		<li id="intr_star_1" star="1"></li>
																		<li id="intr_star_0" star="0"></li>
																	</ul>
																</span>
																<span id="intr_level" class="intr_level"></span>
																<span id="intr_optarea" class="intr_optarea">
																	<span class="intr_optType">

																		<label>
																			<input type="checkbox"
																				name="fdIntroduceToEssence" value="0"
																				subject="" validate="intr_select">
																			推荐到本模块精华库
																		</label>


																		<label style="display: none;">
																			<input type="checkbox"
																				name="fdIntroduceToNews" value="2">
																			推荐到新闻
																		</label>


																		<label>
																			<input type="checkbox"
																				name="fdIntroduceToPerson" value="1"
																				checked="checked"
																				validate="intr_select">
																			推荐给个人
																		</label>

																	</span>
																</span>
															</td>
														</tr>
														<tr group="intr_person" valign="top">
															<td valign="top" width="85%" colspan="2">
																<div
																	class="intr_peson_opt intr_bolder intr_title_color">
																	推荐对象</div>
																<div id="_fdIntroduceGoal" _xform_type="address">

																	<script>
																		Com_IncludeFile('dialog.js|data.js|jquery.js');
																	</script>
																	<script id="dialog_js"
																		src="/ekp/resource/js/dialog.js?s_cache=1596422898836">
																	</script>
																	<script id="treeview_js"
																		src="/ekp/resource/js/treeview.js?s_cache=1596422898836">
																	</script>
																	<script id="rightmenu_js"
																		src="/ekp/resource/js/rightmenu.js?s_cache=1596422898836">
																	</script>
																	<script id="popwin_js"
																		src="/ekp/resource/js/popwin.js?s_cache=1596422898836">
																	</script>

																	<style>
																		.TVN_TreeNode_Current a,
																		.TVN_TreeNode_Current a:hover {
																			color: #37a8f5;
																		}
																	</style>
																	<script id="tree_page_js"
																		src="/ekp/resource/style/default/tree/tree_page.js?s_cache=1596422898836">
																	</script>
																	<link rel="stylesheet"
																		href="/ekp/resource/style/default/tree/tree_page.css">
																	<style>
																		body {
																			scrollbar-face-color: #f2f2f2;
																			scrollbar-arrow-color: #cccccc;
																			scrollbar-highlight-color: #fdfdfd;
																			scrollbar-3dlight-color: #e5e5e3;
																			scrollbar-shadow-color: #959794;
																			scrollbar-darkshadow-color: #ffffff;
																			scrollbar-track-color: #eeeeee;
																		}

																		.treediv {
																			padding: 10px 0 0 10px;
																		}
																	</style>



																	<script>
																		Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js',
																			'js/jquery-plugin/manifest/');
																	</script>
																	<link rel="stylesheet"
																		href="/ekp/resource/js/jquery-plugin/manifest/styles.css?s_cache=1596422898836">
																	<script id="jquery_ui_widget_js"
																		src="/ekp/resource/js/jquery-plugin/manifest/jquery.ui.widget.js?s_cache=1596422898836">
																	</script>
																	<script id="jquery_marcopolo_js"
																		src="/ekp/resource/js/jquery-plugin/manifest/jquery.marcopolo.js?s_cache=1596422898836">
																	</script>
																	<script id="jquery_manifest_js"
																		src="/ekp/resource/js/jquery-plugin/manifest/jquery.manifest.js?s_cache=1596422898836">
																	</script>
																	<script>
																		$(document).ready(function () {
																			Address_QuickSelection("fdIntroduceGoalIds", "fdIntroduceGoalNames", ";", ORG_TYPE_ALL,
																				true, [], null, null, "", null);
																		});
																	</script>
																	<div class="inputselectsgl"
																		style="width: 90%; height: 24px;"><input
																			name="fdIntroduceGoalIds"
																			xform-name="fdIntroduceGoalNames" value=""
																			type="hidden">
																		<div class="input" style="overflow:visible;">
																			<input style="display:none;"
																				xform-type="newAddressHidden"
																				xform-name="fdIntroduceGoalNames"
																				subject="推荐对象" type="text"
																				name="fdIntroduceGoalNames" value=""
																				callback="null"
																				validate="required repetitionValidator(fdIntroduceGoalIds)"
																				_edit="true">
																			<div class="mf_container">
																				<ol class="mf_list" aria-atomic="false"
																					aria-live="polite"
																					aria-multiselectable="true"
																					id="mf_1596617180670_list"
																					role="listbox"></ol><input
																					xform-type="newAddress"
																					xform-name="mf_fdIntroduceGoalNames"
																					subject="推荐对象"
																					data-propertyid="fdIntroduceGoalIds"
																					data-propertyname="fdIntroduceGoalNames"
																					data-splitchar=";"
																					data-orgtype="ORG_TYPE_ALL"
																					data-ismulti="true" type="text"
																					validate="required repetitionValidator(fdIntroduceGoalIds)"
																					class="mf_input mp_input"
																					aria-autocomplete="list"
																					aria-owns="mp_1596617180673_list mf_1596617180670_list"
																					autocomplete="off" role="combobox"
																					aria-required="true"
																					style="width: 59px;"><span
																					class="mf_measure"
																					style="font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Helvetica Neue&quot;, Arial, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Heiti SC&quot;, &quot;Segoe UI&quot;, sans-serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: 400; left: -9999px; letter-spacing: 0px; position: absolute; text-transform: none; top: -9999px; white-space: nowrap; width: auto; word-spacing: 0px;">-------</span>
																			</div>
																		</div>
																		<ol class="mp_list" aria-atomic="true"
																			aria-busy="false" aria-live="polite"
																			id="mp_1596617180673_list" role="listbox"
																			style="display: none;"></ol>
																		<div onclick="Dialog_Address(true,$(this).attr('__id'),$(this).attr('__name'),';',ORG_TYPE_ALL,null,null,null,null,null,null,null,null);"
																			__id="fdIntroduceGoalIds"
																			__name="fdIntroduceGoalNames"
																			class="orgelement"></div>
																	</div><span class="txtstrong">*</span>
																</div>
															</td>
														</tr>
														<tr>
															<td valign="top" width="88%">
																<div class="intr_content_frame">
																	<textarea name="fdIntroduceReason"
																		class="intr_content">					 	</textarea>
																</div>
															</td>
															<td align="center" valign="top">
																<input id="intr_button" class="intr_button"
																	type="button" value="提交">
															</td>
														</tr>
														<tr>
															<td colspan="2">
																<label class="intr_notify intr_summary_color">
																	<input name="fdIsNotify" type="checkbox" value="1"
																		checked="checked"
																		onclick="var isNotify = document.getElementsByName('fdIsNotify');if(isNotify[0].checked){isNotify[0].value='1'}else{isNotify[0].value='0'}">
																	通知文档创建者
																</label>
																&nbsp;&nbsp;&nbsp;&nbsp;
																<label class="intr_notify intr_summary_color">
																	通知方式：<input
																		id="__notify_type_173bdccbf5af5db237b56a64f0196ee6_fdNotifyType"
																		type="hidden" name="fdNotifyType" value="todo">
																	<label><input type="checkbox"
																			name="__notify_type_173bdccbf5af5db237b56a64f0196ee6"
																			value="todo" checked=""
																			onclick="var fields=document.getElementsByName('__notify_type_173bdccbf5af5db237b56a64f0196ee6');var values='';for(var i=0; i<fields.length; i++) if(fields[i].checked) values+=';'+fields[i].value;if(values!='') document.getElementById('__notify_type_173bdccbf5af5db237b56a64f0196ee6_fdNotifyType').value=values.substring(1);else document.getElementById('__notify_type_173bdccbf5af5db237b56a64f0196ee6_fdNotifyType').value='';">待办</label>

																</label>
																<span class="intr_prompt"></span>
															</td>
														</tr>
													</tbody>
												</table>
											</div>

											<div id="intr_viewMain">

												<div class="intr_title intr_title_color">推荐记录</div>
												<div id="introduceContent">
													<div data-lui-type="lui/listview/listview!ListView"
														style="display:none;" id="lui-id-44" class="listview"
														data-lui-cid="lui-id-44" data-lui-parse-init="40">



														<div data-lui-type="lui/listview/rowtable!RowTable"
															style="display:none;" id="lui-id-46" class="rowTable"
															data-lui-cid="lui-id-46" data-lui-parse-init="42">



															<div data-lui-type="lui/listview/template!Template"
																style="display:none;" id="lui-id-48"
																class="lui-component" data-lui-cid="lui-id-48"
																data-lui-parse-init="44">
																<script type="text/code">
																	{$
											<div class="intr_record_msg"><div class="intr_record_content intr_title_color">{%row['fdIntroduceReason']%}</div>
											<div class="intr_summary">
												<span class="intr_evaler">{%row['fdIntroducer.fdName']%}</span>
												<span class="intr_summary_color">|&nbsp;{%row['fdIntroduceTime']%}</span>
												<span><ul class="intr_summary_star">$}
													for(var m=0;m<3;m++){
														var flag = 2- parseInt(row['fdIntroduceGrade']);
														var className = 'lui_icon_s_bad'
														if(m <= flag){
															className = 'lui_icon_s_good';
														}
														{$<li class='{%className%}'></li>$}
													}
												{$</ul></span>
											</div>
											<div class="intr_summary_detail intr_summary_color">
												<span>{%row['introduceType']%}</span>
												<span>{%row['introduceGoalNames'].replace(/;/gi,' , ')%}</span>
											</div>
											</div>
										$}
		</script>
															</div>
														</div>

													</div>
													<div data-lui-type="lui/listview/paging!Paging"
														style="display:none;" id="lui-id-49" class="lui-component"
														data-lui-cid="lui-id-49" data-lui-parse-init="45">

													</div>
												</div>

											</div>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">纠错记录(1)</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-51"
											class="lui-component" data-lui-cid="lui-id-51" data-lui-parse-init="47">


											<div id="all_error" data-lui-type="lui/listview/listview!ListView"
												style="display:none;" class="listview" data-lui-cid="all_error"
												data-lui-parse-init="49">



												<div data-lui-type="lui/listview/columntable!ColumnTable"
													style="display:none;" id="lui-id-55" class="columnTable"
													data-lui-cid="lui-id-55" data-lui-parse-init="51">



													<div data-lui-type="lui/listview/columntable!SerialColumn"
														style="display:none;" id="lui-id-57" class="lui-component"
														data-lui-cid="lui-id-57" data-lui-parse-init="53">

													</div>
													<div data-lui-type="lui/listview/columntable!HtmlColumn"
														style="display:none;" id="lui-id-58" class="lui-component"
														data-lui-cid="lui-id-58" data-lui-parse-init="54">

														<script type="text/code">
															{$ {%row['docDescription']%} $}
		</script>
													</div>
													<div data-lui-type="lui/listview/columntable!HtmlColumn"
														style="display:none;" id="lui-id-59" class="lui-component"
														data-lui-cid="lui-id-59" data-lui-parse-init="55">

														<script type="text/code">
															{$ {%row['docStatus']%} $}
		</script>
													</div>
													<div data-lui-type="lui/listview/columntable!HtmlColumn"
														style="display:none;" id="lui-id-60" class="lui-component"
														data-lui-cid="lui-id-60" data-lui-parse-init="56">

														<script type="text/code">
															{$ {%row['docCreator.fdName']%} $}
		</script>
													</div>
													<div data-lui-type="lui/listview/columntable!HtmlColumn"
														style="display:none;" id="lui-id-61" class="lui-component"
														data-lui-cid="lui-id-61" data-lui-parse-init="57">

														<script type="text/code">
															{$ {%row['docCreateTime']%} $}
		</script>
													</div>

													<div data-lui-type="lui/listview/columntable!HtmlColumn"
														style="display:none;" id="lui-id-62" class="lui-component"
														data-lui-cid="lui-id-62" data-lui-parse-init="58">

														<script type="text/code">
															{$  <a href="javascript:void(0)"
									onclick="delErrorCorrection( '{%row['fdId']%}' );">删除</a> $}
		</script>
													</div>

												</div>
											</div>
											<div style="height: 15px;"></div>
											<div data-lui-type="lui/listview/paging!Paging" style="display:none;"
												id="lui-id-63" class="lui-component" data-lui-cid="lui-id-63"
												data-lui-parse-init="59">

											</div>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">访问统计</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-65"
											class="lui-component" data-lui-cid="lui-id-65" data-lui-parse-init="61">
















											<style>
												#_readlog_container .lui_tabpanel_navs_item_title {
													font-size: 13px !important;
												}

												#_readlog_container .lui_tabpanel_light_content_c {
													overflow: hidden !important;
												}
											</style>
											<div id="_readlog_container">
												<div id="tabAccess" data-lui-type="lui/panel!TabPanel"
													style="display:none;" class="lui-component" data-lui-cid="tabAccess"
													data-lui-parse-init="63">


													<div data-lui-type="lui/panel!Content" style="" id="lui-id-68"
														class="lui-component" data-lui-cid="lui-id-68"
														data-lui-parse-init="65">




														<table width="100%">
															<tbody>
																<tr>
																	<td>
																		<iframe id="logContent0" width="100%"
																			height="1000" frameborder="0"
																			scrolling="no"></iframe></td>

																</tr>
															</tbody>
														</table>
													</div>

													<div data-lui-type="lui/panel!Content" style="" id="lui-id-70"
														class="lui-component" data-lui-cid="lui-id-70"
														data-lui-parse-init="67">




														<iframe id="logContent1" width="100%" height="1000"
															frameborder="0" scrolling="no"></iframe>
														<table width="100%">
															<tbody>
																<tr>

																</tr>
															</tbody>
														</table>
													</div>

													<div data-lui-type="lui/panel!Content" style="" id="lui-id-72"
														class="lui-component" data-lui-cid="lui-id-72"
														data-lui-parse-init="69">




														<table width="100%">
															<tbody>
																<tr>
																	<td>
																		<iframe id="logContent2" width="100%"
																			height="1000" frameborder="0"
																			scrolling="no"></iframe>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<script>
												function showTab() {
													var tab = LUI('tabAccess');
													tab.on('indexChanged', function (data) {
														seajs.use(['lui/jquery'], function ($) {
															//火狐浏览器下在iframe未显示出来之前获取body高度始终是0，所以这里只能需要添加延时，确保iframe已经显示出来
															setTimeout(function () {
																var $frame = $('#logContent' + data.index.after);
																var _window = $frame[0].contentWindow;
																var fHeight = $frame.height(); //iframe的高度
																var bHeight = $(_window.document.body).height(); //body的高度
																if (fHeight < bHeight) {
																	if (_window.setBodyHeight)
																		_window.setBodyHeight();
																}
															}, 100);
														});
													});
												}
											</script>


										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">权限</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-74"
											class="lui-component" data-lui-cid="lui-id-74" data-lui-parse-init="71">


											<table class="tb_normal" width="100%">
















												<style>
													.div_img {}

													.div_img span {
														position: relative;
														top: 6px
													}
												</style>


												<tbody>
													<tr>
														<td class="td_normal_title" width="15%">
															可阅读者
														</td>
														<td width="85%">


															所有内部人员




														</td>
													</tr>




													<tr>
														<td class="td_normal_title">可编辑者</td>
														<td>

															只有相关人员可以编辑


														</td>
													</tr>



													<tr>
														<td class="td_normal_title" width="15%">附件权限</td>
														<td width="85%">

															可拷贝者
															：

															<input type="hidden" name="authAttNocopy" value="false">



															&lt;所有内部人员&gt;



															<br>




															可下载者
															：

															<input type="hidden" name="authAttNodownload" value="false">



															&lt;所有内部人员&gt;



															<br>




															可打印者
															：

															<input type="hidden" name="authAttNoprint" value="false">



															&lt;所有内部人员&gt;




															<div class="div_img">
																支持的附件类型：
																<span><img src="/ekp/sys/right/resource/images/Word.png"
																		height="26" width="26" title="MS Word "></span>
																<span><img
																		src="/ekp/sys/right/resource/images/Excel.png"
																		height="26" width="26" title="MS Excel"></span>
																<span><img
																		src="/ekp/sys/right/resource/images/PowerPoint.png"
																		height="26" width="26"
																		title="MS PowerPoint"></span>
																<span><img
																		src="/ekp/sys/right/resource/images/Project.png"
																		height="26" width="26"
																		title="MS Project"></span>
																<span><img
																		src="/ekp/sys/right/resource/images/Visio.png"
																		height="26" width="26" title="MS Visio"></span>

															</div>
														</td>
													</tr>


												</tbody>
											</table>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">版本</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-76"
											class="lui-component" data-lui-cid="lui-id-76" data-lui-parse-init="73">





											<script language="JavaScript">
												function edition_SelectVersion() {
													seajs.use(
														['lui/dialog'],
														function (dialog) {

															dialog
																.iframe(
																	'/sys/edition/sys_edition_main/sysEditionMain.do?method=newVersion&mainVersion=1&auxiVersion=0&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&fdModelId=16cbcbab019d6f75788c5af41b880edb',
																	"新版本",
																	function () {
																		var version = top.returnValue;
																		if (version != null) {
																			var href = assemblyHref();
																			href = href + "&version=" + version;
																			window.location.href = href;
																		}
																	}, {
																		width: '477',
																		height: '250'
																	});
														});
												}

												function assemblyHref() {
													var href = window.location.href;
													var reg = /method=\w*/;
													href = href.replace(reg, "method=newEdition");
													var reg1 = /fdId/;
													href = href.replace(reg1, "originId");
													return href;
												}
											</script>








											<iframe id="editionContent" width="100%" height="1000" frameborder="0"
												scrolling="no">
											</iframe>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">发布</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-79"
											class="lui-component" data-lui-cid="lui-id-79" data-lui-parse-init="76">

















											<script language="JavaScript">
												Com_IncludeFile("dialog.js");

												function SNP_manual_publish() {
													var url =
														"/sys/news/sys_news_publish_main/sysNewsPublishMain_viewManualFrame.jsp?fdModelNameParam=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&fdModelIdParam=16cbcbab019d6f75788c5af41b880edb&fdKeyParam=mainDoc";
													seajs.use(['lui/dialog', 'lang!sys-news:news.tree.publishNews'], function (dialog, lang) {
														dialog.iframe(url, lang['news.tree.publishNews'], null, {
															width: 700,
															height: 310
														});
													});
												}
											</script>




											<table width="100%">
												<tbody>
													<tr>
														<td>
															<iframe id="publishRecord" width="100%" height="100%"
																frameborder="0" scrolling="no"></iframe>
														</td>
													</tr>
												</tbody>
											</table>
											<script>
												function loadPublishData() {
													var fdModelName = 'com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge';
													var fdModelId = '16cbcbab019d6f75788c5af41b880edb';
													var fdKey = 'mainDoc';
													var norecodeLayout = '';
													document.getElementById('publishRecord').src = (
														"/ekp/sys/news/sys_news_publish_main/sysNewsPublishMain_viewAllPublish.jsp?method=viewAllPublish&fdModelNameParam=" +
														fdModelName + "&fdModelIdParam=" + fdModelId + "&fdKeyParam=" + fdKey + "&norecodeLayout=" +
														norecodeLayout);
												}
											</script>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">发布到课件</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div data-lui-type="lui/panel!Content" style="" id="lui-id-83"
											class="lui-component" data-lui-cid="lui-id-83" data-lui-parse-init="82">


											<div data-lui-type="lui/listview/listview!ListView" style="display:none;"
												id="lui-id-85" class="listview" data-lui-cid="lui-id-85"
												data-lui-parse-init="84">



												<div data-lui-type="lui/listview/columntable!ColumnTable"
													style="display:none;" id="lui-id-88" class="columnTable"
													data-lui-cid="lui-id-88" data-lui-parse-init="86">


													<div data-lui-type="lui/listview/columntable!Columns"
														style="display:none;" id="lui-id-90" class="lui-component"
														data-lui-cid="lui-id-90" data-lui-parse-init="88">

													</div>
												</div>
											</div>
											<div data-lui-type="lui/listview/paging!Paging" style="display:none;"
												id="lui-id-91" class="lui-component" data-lui-cid="lui-id-91"
												data-lui-parse-init="89">

											</div>
										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
				<div class="lui_tabpage_float_content" style="position: absolute; top: -999em;">
					<div class="lui_tabpage_float_header_l">
						<div class="lui_tabpage_float_header_r">
							<div class="lui_tabpage_float_header_c">
								<div class="lui_tabpage_float_header_title">
									<div class="lui_tabpage_float_header_text">流程处理</div>
									<div class="lui_tabpage_float_header_close" title="最小化"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_content_l">
						<div class="lui_tabpage_float_content_r">
							<div class="lui_tabpage_float_content_c">
								<div>
									<div data-lui-mark="panel.content.inside" class="lui_panel_content_inside">
										<div id="process_review_tabcontent" data-lui-type="lui/panel!Content" style=""
											class="lui-component" data-lui-cid="process_review_tabcontent"
											data-lui-parse-init="91">






















											<link type="text/css" rel="stylesheet"
												href="/ekp/sys/lbpmservice/common/css/process_tab_main.css?s_cache=1596422898836">










											<script type="text/javascript">
												Com_IncludeFile("jquery.js|json2.js");
												Com_IncludeFile(
													"docutil.js|optbar.js|validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|data.js", null,
													"js");
												Com_IncludeFile("dialog.js");
											</script>
											<script id="json2_js" src="/ekp/resource/js/json2.js?s_cache=1596422898836">
											</script>
											<script id="docutil_js"
												src="/ekp/resource/js/docutil.js?s_cache=1596422898836"></script>
											<script id="optbar_js"
												src="/ekp/resource/js/optbar.js?s_cache=1596422898836"></script>
											<link rel="stylesheet"
												href="/ekp/resource/style/default/optbar/optbar.css?s_cache=1596422898836">


											<link rel="stylesheet" type="text/css"
												href="/ekp/sys/lbpmservice/resource/jNotify.jquery.css?s_cache=1596422898836"
												media="screen">
											<script type="text/javascript"
												src="/ekp/sys/lbpmservice/resource/jNotify.jquery.js?s_cache=1596422898836">
											</script>
















											<script language="JavaScript">
												var _isLangSuportEnabled = true;
												var _langJson = {
													"official": {
														"text": "中文",
														"value": "zh-CN"
													},
													"support": [{
															"text": "中文(简体)",
															"value": "zh-CN"
														},
														{
															"text": "English",
															"value": "en-US"
														},
														{
															"text": "中文(香港)",
															"value": "zh-HK"
														},
														{
															"text": "日本語",
															"value": "ja-JP"
														}
													]
												};
												var _userLang = "zh-CN";


												var lbpm = new Object();
												lbpm.globals = new Object(); //所有函数集合
												lbpm.operations = new Object(); //所有操作集合
												lbpm.nodes = new Object(); //所有节点集合
												lbpm.nodesInit = new Object(); //最初的节点集合
												lbpm.lines = new Object(); //所有连线集合
												lbpm.modifys = null; //所有修改的数据集合
												lbpm.events = {}; //所有事件集合
												lbpm.flowcharts = {}; //流程图所要的属性集合

												lbpm.nodeDescMap = {};
												lbpm.nodedescs = {}; //所有节点描述集合
												lbpm.jsfilelists = new Array(); //所有include的的JS文件路径集合

												lbpm.modelName = "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge";
												lbpm.modelId = "16cbcbab019d6f75788c5af41b880edb";
												lbpm.isFreeFlow = ("" == "4"); //模板类型
												lbpm.isSubForm = ("false" == "true"); // 是否为多表单模式
												lbpm.isShowSubBut = ("false" == "true"); // 是否显示切换表单按钮
												if (lbpm.isSubForm) {
													lbpm.nowSubFormId = "";
												}
												lbpm.defaultTaskId = ''; // 指定默认选中的事务
												(function (lbpm) {
													var constant = lbpm.constant = {};
													//工作项常量
													lbpm.workitem = {};
													lbpm.workitem.constant = {};
													//节点常量
													lbpm.node = {};
													lbpm.node.constant = {};
													constant.opt = {}; //操作常量
													constant.evt = {}; //事件常量
													//址本本选择
													constant.ADDRESS_SELECT_FORMULA = "formula";
													constant.ADDRESS_SELECT_ORG = "org";
													constant.ADDRESS_SELECT_MATRIX = "matrix";
													constant.ADDRESS_SELECT_RULE = "rule";
													constant.ADDRESS_SELECT_POSTPERSONROLE = "ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE";
													constant.ADDRESS_SELECT_ALLROLE = "ORG_TYPE_ALL | ORG_TYPE_ROLE";
													constant.SELECTORG = "选择"; //选择
													constant.PROCESSTYPE_SERIAL = "0"; //串行
													constant.PROCESSTYPE_ALL = "2"; //会审/会签
													constant.PROCESSTYPE_SINGLE = "1"; //并行

													constant.METHOD = "view";
													constant.METHODEDIT = "edit";
													constant.METHODVIEW = "view";
													constant.IDENTITY_PROCESSOR = "processor";

													constant.DOCSTATUS = '30'; //文档状态
													//是否为新建的流程
													constant.ISINIT = ("false" == "true");
													if (!constant.ISINIT) {
														var method = Com_GetUrlParameter(window.location.href, 'method');
														constant.ISINIT = (method == "add" || method == 'saveadd');
													}
													constant.STATE_COMPLETED = '30'; //流程结束
													constant.STATE_ACTIVATED = '20'; //流程中
													constant.STATE_CREATED = '10'; //流程创建

													constant.STATUS_UNINIT = 0; //状态：未初始化
													constant.STATUS_NORMAL = 1; //状态：普通
													constant.STATUS_PASSED = 2; //状态：曾经流过
													constant.STATUS_RUNNING = 3; //状态：当前

													constant.ROLETYPE = ""; //角色类型
													constant.FDKEY = "mainDoc";

													constant.PRIVILEGERFLAG = 'true';
													constant.IS_ADMIN = constant.PRIVILEGERFLAG;
													constant.IS_HANDER = 'false';
													constant.SHOWHISTORYOPERS = '';

													//处理人身份标识，起草人
													constant.DRAFTERROLETYPE = "drafter";
													constant.HANDLER_IDENTITY_DRAFT = 1;
													//处理人身份标识，处理人
													constant.PROCESSORROLETYPE = "processor";
													constant.HANDLER_IDENTITY_HANDLER = 2;
													//处理人身份标识，特权人
													constant.AUTHORITYROLETYPE = "authority";
													constant.HANDLER_IDENTITY_SPECIAL = 3;
													//处理人身份标识，已处理人
													constant.HISTORYHANDLERROLETYPE = "historyhandler";
													constant.HANDLER_IDENTITY_HISTORYHANDLER = 4;

													constant.SUCCESS = "success";
													constant.FAILURE = "failure";
													constant.VALIDATEISNULL = "不允许为空";
													constant.VALIDATEISNAN = "必须输入数字";

													//节点类型
													constant.NODETYPE_START = "startNodeDesc"; //开始节点类型
													constant.NODETYPE_END = "endNodeDesc"; //结束节点类型
													constant.NODETYPE_STARTSUBPROCESS = "startSubProcessNodeDesc"; //开始启动子流程节点类型
													constant.NODETYPE_DRAFT = "draftNodeDesc"; //起草节点类型
													constant.NODETYPE_JOIN = "joinNodeDesc"; //并发分支结束节点类型
													constant.NODETYPE_SIGN = "signNodeDesc"; //签字节点类型
													constant.NODETYPE_SPLIT = "isSplitNode"; //并发分支开始节点类型 	
													constant.NODETYPE_REVIEW = "isReviewNode"; //审批节点类型 	
													constant.NODETYPE_HANDLER = "isHandler"; //带处理人的节点类型
													constant.NODETYPE_SEND = "isSendNode"; //抄送节点类型
													constant.NODETYPE_CANREFUSE = "canRefuse"; //可以被驳回节点类型
													constant.NODETYPE_AUTOBRANCH = "isAutoBranch"; //自动分支节点类型
													constant.NODETYPE_MANUALBRANCH = "isManualBranch"; //人工分支节点类型
													constant.NODETYPE_RECOVERSUBPROCESS = "isRecoverSubProcess"; //结束子流程节点类型
													constant.NODETYPE_ROBOT = "isRobot"; //机器人节点类型
													//事件常量定义
													constant.EVENT_MODIFYNODEATTRIBUTE = "modifyNodeAttribute"; //修改节点属性
													constant.EVENT_MODIFYPROCESS = "modifyProcess"; //修改流程图
													constant.EVENT_SELECTEDMANUAL = "selectedManual"; //选择了人工分支(起草人选择后续分支)
													constant.EVENT_SELECTEDFUTURENODE = "selectedFutureNode"; //选择即将流向分支
													constant.EVENT_FILLLBPMOBJATTU = "fillLbpmObjAttu"; //允许项目定制修改节点属性（像美的代理节点）
													constant.EVENT_CHANGEWORKITEM = "changeWorkitem"; //当用户有多个工作项时切换工作项
													constant.EVENT_validateMustSignYourSuggestion = "validateMustSignYourSuggestion"; // 校验是否填写审批意见
													constant.EVENT_HANDLERTYPECHANGE = "handlerChange"; //处理人身份发生改变
													constant.EVENT_CHANGEOPERATION = "changeOperation"; // 操作发生变更事件
													constant.EVENT_SETOPERATIONPARAM = "setOperationParam"; // 提交时设置操作参数事件
													constant.EVENT_BEFORELBPMSUBMIT = "beforeLbpmSubmit"; //流程提交前事件
													//流程图常量
													constant.COMMONNODEHANDLERPARSEERROR = '暂时无法计算处理人';
													constant.COMMONNODEHANDLERPROCESSTYPESERIAL = '串行';
													constant.COMMONNODEHANDLERPROCESSTYPEALL = '会审/会签';
													constant.COMMONNODEHANDLERPROCESSTYPESINGLE = '并行';
													constant.COMMONNODENAME = '节点名称';
													constant.COMMONNODEHANDLER = '节点处理人';
													constant.COMMONNODEHANDLERHINT = '提示:实际计算结果可能因表单数据变化而改变';
													//JS提示
													constant.CHKNEXTNODENOTNULL = "请选择流程的一个即将流向节点！";
													constant.LOADINGMSG = "数据加载中...";
													constant.CONCURRENCYBRANCHSELECT = "请选择分支";
													constant.CONCURRENCYBRANCHTITLE = "并行分支";
													constant.VALIDATENOTIFYTYPEISNULL = "请选择流程的通知方式！";
													constant.VALIDATEOPERATIONTYPEISNULL = "请选择流程下一步操作！";
													constant.ERRORMAXLENGTH = "{0} 超出 {1} 个英文字符限制";
													constant.OPINION = "处理意见";
													constant.ERROR_FDUSAGECONTENT_MAXLENGTH = '{name} 超出 {maxLength} 个英文字符限制，建议以附件方式提交意见';
													constant.CREATEDRAFTCOMMONUSAGES = "常用意见";
													constant.MUSTMODIFYHANDLERNODEIDSISNULL = "节点为必须修改的节点，请设置该节点的处理人后再进行提交操作！";
													constant.MUSTMODIFYHANDLERNODEIDSPARSEISNULL = "节点为必须修改的节点，是否修改？";
													constant.MUSTMODIFYHANDLERNODEIDSPARSEPOSTEMPTY = "为必须修改的节点，存在岗位未设置成员，请重新设置该节点的处理人再进行提交操作！";
													constant.MUSTMODIFYHANDLERNODEIDSPARSEELEMENTDISABLED = "为必须修改的节点，存在无效的组织架构对象，请重新设置该节点的处理人再进行提交操作！";
													constant.VALIDATENEXTNODEHANDLERISNULL = "请选择流程的即将流向节点处理人！";
													constant.FLOWCONTENTMUSTMODIFYNODENEXTHANDLER = "{0}.{1} 是 {2}.{3} 的后续审批节点，因此必须设置该节点的处理人！";
													constant.COMMONNODEHANDLERORGEMPTY = '未设处理人';
													constant.COMMONNODEHANDLERORGNULL = '处理人为空';

													constant.opt.EnvironmentUnsupportOperation = '当前环境不支持此操作';
													constant.opt.MustSignYourSuggestion = '请填写处理意见！';

													constant.NOCALCBRANCHCRESULT = '(无法计算出符合条件的分支流向)';
													constant.MODIFYRETURNBACKHANDLER = '驳回返回的节点处理人已发生变更，提交后将默认返回到节点，并重新计算节点处理人！';
													constant.BTNSAVEDRAFT = '暂存';

													constant.OPRSUCCESS = '操作成功！';
													constant.OPRFAILURE = '操作失败！';

													constant.FREEFLOW = '自由流 ';
													constant.FREEFLOW_MUSTAPPENDNODE = '请设置下一环节的节点与处理人！';
													constant.FREEFLOW_DELETENODEMSG = '确定删除当前节点吗？';
													constant.FREEFLOW_TIELE_ADD = '新增';
													constant.FREEFLOW_TIELE_EDIT = '编辑';
													constant.FREEFLOW_TIELE_DELETE = '删除';
													constant.FREEFLOW_TIELE_DRAG = '点击进行拖拽';
													constant.FREEFLOW_TIELE_FIXED = '固定节点';
													constant.FREEFLOW_TIELE_PASSED = '已完成';
													constant.FREEFLOW_TIELE_RUNNING = '正在进行中';
													constant.FREEFLOW_TIELE_ADDHANDLER = '点击添加';
													constant.FREEFLOW_TIELE_NODE = '节点';
													constant.FREEFLOW_TIELE_NOHANDLER = '未设置处理人！';
													constant.FREEFLOW_TIELE_SAVECONFIRM = '确定保存为默认模板？';

													constant.SETNEXTSTEP = '设置下一步';
													constant.UPDATENEXTSTEP = '更新下一步';
												})(lbpm);
												lbpm.globals.includeFile = function (fileList) {
													fileList = fileList.split("|");
													for (var i = 0; i < fileList.length; i++) {
														if (Com_ArrayGetIndex(lbpm.jsfilelists, fileList[i]) == -1) {
															lbpm.jsfilelists[lbpm.jsfilelists.length] = fileList[i];
														}
													}
												};
											</script>



											<script type="text/javascript">
												lbpm.nodeDescMap["startSubProcessNode"] = "startSubProcessNodeDesc";
												lbpm.nodedescs["startSubProcessNodeDesc"] = {};
												lbpm.nodedescs["startSubProcessNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].isSubProcess = function () {
													return true
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].isConcurrent = function () {
													return true
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["startSubProcessNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["shareReviewNode"] = "shareReviewNodeDesc";
												lbpm.nodedescs["shareReviewNodeDesc"] = {};
												lbpm.nodedescs["shareReviewNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["shareReviewNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["shareReviewNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["shareReviewNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["shareReviewNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["shareReviewNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["shareReviewNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["shareReviewNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["shareReviewNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["shareReviewNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["sendNode"] = "sendNodeDesc";
												lbpm.nodedescs["sendNodeDesc"] = {};
												lbpm.nodedescs["sendNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["sendNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["sendNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["sendNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["sendNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["sendNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["sendNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["sendNodeDesc"].getSortName = function () {
													return '抄送'
												};
												lbpm.nodedescs["sendNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["sendNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["robotNode"] = "robotNodeDesc";
												lbpm.nodedescs["robotNodeDesc"] = {};
												lbpm.nodedescs["robotNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["robotNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["robotNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["robotNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["robotNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["robotNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["robotNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["robotNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["robotNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["robotNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["recoverSubProcessNode"] = "recoverSubProcessNodeDesc";
												lbpm.nodedescs["recoverSubProcessNodeDesc"] = {};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isSubProcess = function () {
													return true
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["recoverSubProcessNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["voteNode"] = "voteNodeDesc";
												lbpm.nodedescs["voteNodeDesc"] = {};
												lbpm.nodedescs["voteNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["voteNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["voteNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["voteNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["voteNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["voteNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["voteNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["voteNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["voteNodeDesc"].uniqueMark = function () {
													return "voteNodeDesc"
												};
												lbpm.nodedescs["voteNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["groupEndNode"] = "groupEndNodeDesc";
												lbpm.nodedescs["groupEndNodeDesc"] = {};
												lbpm.nodedescs["groupEndNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["groupEndNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["groupEndNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["groupEndNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["groupEndNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["groupEndNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["groupEndNodeDesc"].isSub = function () {
													return true
												};
												lbpm.nodedescs["groupEndNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["groupEndNodeDesc"].uniqueMark = function () {
													return "groupEndNodeDesc"
												};
												lbpm.nodedescs["groupEndNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["joinNode"] = "joinNodeDesc";
												lbpm.nodedescs["joinNodeDesc"] = {};
												lbpm.nodedescs["joinNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["joinNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["joinNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["joinNodeDesc"].isConcurrent = function () {
													return true
												};
												lbpm.nodedescs["joinNodeDesc"].isBranch = function () {
													return true
												};
												lbpm.nodedescs["joinNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["joinNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["joinNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["joinNodeDesc"].uniqueMark = function () {
													return "joinNodeDesc"
												};
												lbpm.nodedescs["joinNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["embeddedSubFlowNode"] = "embeddedSubFlowNodeDesc";
												lbpm.nodedescs["embeddedSubFlowNodeDesc"] = {};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isGroup = function () {
													return true
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].uniqueMark = function () {
													return "embeddedSubFlowNodeDesc"
												};
												lbpm.nodedescs["embeddedSubFlowNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["checkNode"] = "checkNodeDesc";
												lbpm.nodedescs["checkNodeDesc"] = {};
												lbpm.nodedescs["checkNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["checkNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["checkNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["checkNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["checkNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["checkNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["checkNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["checkNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["checkNodeDesc"].uniqueMark = function () {
													return "checkNodeDesc"
												};
												lbpm.nodedescs["checkNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["signNode"] = "signNodeDesc";
												lbpm.nodedescs["signNodeDesc"] = {};
												lbpm.nodedescs["signNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["signNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["signNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["signNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["signNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["signNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["signNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["signNodeDesc"].getSortName = function () {
													return '签字'
												};
												lbpm.nodedescs["signNodeDesc"].uniqueMark = function () {
													return "signNodeDesc"
												};
												lbpm.nodedescs["signNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["splitNode"] = "splitNodeDesc";
												lbpm.nodedescs["splitNodeDesc"] = {};
												lbpm.nodedescs["splitNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["splitNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["splitNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["splitNodeDesc"].isConcurrent = function () {
													return true
												};
												lbpm.nodedescs["splitNodeDesc"].isBranch = function () {
													return true
												};
												lbpm.nodedescs["splitNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["splitNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["splitNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["splitNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["splitNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["startNode"] = "startNodeDesc";
												lbpm.nodedescs["startNodeDesc"] = {};
												lbpm.nodedescs["startNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["startNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["startNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["startNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["startNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["startNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["startNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["startNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["startNodeDesc"].uniqueMark = function () {
													return "startNodeDesc"
												};
												lbpm.nodedescs["startNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["autoBranchNode"] = "autoBranchNodeDesc";
												lbpm.nodedescs["autoBranchNodeDesc"] = {};
												lbpm.nodedescs["autoBranchNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["autoBranchNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["autoBranchNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["autoBranchNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["autoBranchNodeDesc"].isBranch = function () {
													return true
												};
												lbpm.nodedescs["autoBranchNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["autoBranchNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["autoBranchNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["autoBranchNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["autoBranchNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["manualBranchNode"] = "manualBranchNodeDesc";
												lbpm.nodedescs["manualBranchNodeDesc"] = {};
												lbpm.nodedescs["manualBranchNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["manualBranchNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["manualBranchNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["manualBranchNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["manualBranchNodeDesc"].isBranch = function () {
													return true
												};
												lbpm.nodedescs["manualBranchNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["manualBranchNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["manualBranchNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["manualBranchNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["manualBranchNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["draftNode"] = "draftNodeDesc";
												lbpm.nodedescs["draftNodeDesc"] = {};
												lbpm.nodedescs["draftNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["draftNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["draftNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["draftNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["draftNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["draftNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["draftNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["draftNodeDesc"].getSortName = function () {
													return '起草'
												};
												lbpm.nodedescs["draftNodeDesc"].uniqueMark = function () {
													return "draftNodeDesc"
												};
												lbpm.nodedescs["draftNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["groupStartNode"] = "groupStartNodeDesc";
												lbpm.nodedescs["groupStartNodeDesc"] = {};
												lbpm.nodedescs["groupStartNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["groupStartNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["groupStartNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["groupStartNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["groupStartNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["groupStartNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["groupStartNodeDesc"].isSub = function () {
													return true
												};
												lbpm.nodedescs["groupStartNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["groupStartNodeDesc"].uniqueMark = function () {
													return "groupStartNodeDesc"
												};
												lbpm.nodedescs["groupStartNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["reviewNode"] = "reviewNodeDesc";
												lbpm.nodedescs["reviewNodeDesc"] = {};
												lbpm.nodedescs["reviewNodeDesc"].isHandler = function () {
													return true
												};
												lbpm.nodedescs["reviewNodeDesc"].isAutomaticRun = function () {
													return false
												};
												lbpm.nodedescs["reviewNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["reviewNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["reviewNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["reviewNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["reviewNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["reviewNodeDesc"].getSortName = function () {
													return '审批'
												};
												lbpm.nodedescs["reviewNodeDesc"].uniqueMark = function () {
													return null
												};
												lbpm.nodedescs["reviewNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["endNode"] = "endNodeDesc";
												lbpm.nodedescs["endNodeDesc"] = {};
												lbpm.nodedescs["endNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["endNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["endNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["endNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["endNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["endNodeDesc"].isGroup = function () {
													return false
												};
												lbpm.nodedescs["endNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["endNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["endNodeDesc"].uniqueMark = function () {
													return "endNodeDesc"
												};
												lbpm.nodedescs["endNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["freeSubFlowNode"] = "freeSubFlowNodeDesc";
												lbpm.nodedescs["freeSubFlowNodeDesc"] = {};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isGroup = function () {
													return true
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].uniqueMark = function () {
													return "freeSubFlowNodeDesc"
												};
												lbpm.nodedescs["freeSubFlowNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												lbpm.nodeDescMap["adHocSubFlowNode"] = "adHocSubFlowNodeDesc";
												lbpm.nodedescs["adHocSubFlowNodeDesc"] = {};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isHandler = function () {
													return false
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isAutomaticRun = function () {
													return true
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isSubProcess = function () {
													return false
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isConcurrent = function () {
													return false
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isBranch = function () {
													return false
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isGroup = function () {
													return true
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].isSub = function () {
													return false
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].getSortName = function () {
													return ''
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].uniqueMark = function () {
													return "adHocSubFlowNodeDesc"
												};
												lbpm.nodedescs["adHocSubFlowNodeDesc"].getLines = function (nodeObj, nextNodeObj) {
													return nodeObj.endLines;
												};

												if (window.dojo) {
													lbpm.globals.includeFile("/sys/lbpm/engine/node/splitnode/node_desc.js");
												} else {
													document.writeln(
														"<script src='/ekp/sys/lbpm/engine/node/splitnode/node_desc.js?s_cache=1596422898836'></scr" +
														"ipt>");
												}

												if (window.dojo) {
													lbpm.globals.includeFile("/sys/lbpmservice/node/autobranchnode/node_desc.js");
												} else {
													document.writeln(
														"<script src='/ekp/sys/lbpmservice/node/autobranchnode/node_desc.js?s_cache=1596422898836'></scr" +
														"ipt>");
												}

												if (window.dojo) {
													lbpm.globals.includeFile("/sys/lbpmservice/node/manualbranchnode/node_desc.js");
												} else {
													document.writeln(
														"<script src='/ekp/sys/lbpmservice/node/manualbranchnode/node_desc.js?s_cache=1596422898836'></scr" +
														"ipt>");
												}
											</script>
											<script
												src="/ekp/sys/lbpm/engine/node/splitnode/node_desc.js?s_cache=1596422898836">
											</script>
											<script
												src="/ekp/sys/lbpmservice/node/autobranchnode/node_desc.js?s_cache=1596422898836">
											</script>
											<script
												src="/ekp/sys/lbpmservice/node/manualbranchnode/node_desc.js?s_cache=1596422898836">
											</script>

											<script type="text/javascript">
												var Lbpm_SettingInfo = lbpm.settingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService")
													.GetHashMapArray()[0]; //统一在此获取流程默认值与功能开关等配置
											</script>
											<script src="/ekp/sys/lbpm/flowchart/js/workflow.js?s_cache=1596422898836">
											</script>
											<script
												src="/ekp/sys/lbpmservice/include/syslbpmprocess.js?s_cache=1596422898836">
											</script>
											<script id="formula_js"
												src="/ekp/resource/js/formula.js?s_cache=1596422898836"></script>
											<script
												src="/ekp/sys/lbpmservice/include/syslbpmprocess_event.js?s_cache=1596422898836">
											</script>
											<script
												src="/ekp/sys/lbpmservice/include/syslbpmprocess_submit.js?s_cache=1596422898836">
											</script>
											<script
												src="/ekp/sys/lbpmservice/include/syslbpmprocess_nodes_filter.js?s_cache=1596422898836">
											</script>
											<script
												src="/ekp/sys/lbpmservice/include/syslbpmbuildnextnodehandler.js?s_cache=1596422898836">
											</script>

											<script
												src="/ekp/sys/lbpmservice/resource/address_builder.js?s_cache=1596422898836">
											</script>
											<link rel="stylesheet" type="text/css"
												href="/ekp/sys/lbpmservice/resource/review.css?s_cache=1596422898836">


											<script type="text/javascript">
												lbpm.drafterOperationsReviewJs = new Array();
												lbpm.adminOperationsReviewJs = new Array();
												lbpm.historyhandlerOperationsReviewJs = new Array();



												lbpm.myAddedNodes = new Array();
											</script>

											<link rel="stylesheet" type="text/css"
												href="/ekp/component/locker/resource/jNotify.jquery.css" media="screen">
											<script type="text/javascript"
												src="/ekp/component/locker/resource/jNotify.jquery.js"></script>

											<script type="text/javascript">
												lbpm.load_Frame = function () {
													// 审批日志
													lbpm.globals.load_Frame('historyInfoTableTD',
														'/ekp/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=listNote' +
														'&fdModelId=16cbcbab019d6f75788c5af41b880edb&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&formBeanName=kmsMultidocKnowledgeForm' +
														'&showPersonal=true&approveType=');
												};
												lbpm.flow_chart_load_Frame = function () {

													if (lbpm.isFreeFlow) { //自由流

														//IE8浏览器自由流有问题，提示警告，
														var DEFAULT_VERSION = 8.0;
														var ua = navigator.userAgent.toLowerCase();
														var isIE = ua.indexOf("msie") > -1;
														var safariVersion;
														if (isIE) {
															safariVersion = ua.match(/msie ([\d.]+)/)[1];
														}
														if (safariVersion <= DEFAULT_VERSION) {
															jNotify("自由流配置页面不支持IE8浏览器，请使用更高版本IE浏览器或其他浏览器。", {
																TimeShown: 50000,
																autoHide: false,
																VerticalPosition: 'top',
																HorizontalPosition: 'right',
																ShowOverlay: false
															});
														};


														if (!($("#workflowInfoTD").closest("div").hasClass("process_body_checked_false") ||
																$("#workflowInfoTD").closest("div").hasClass("process_body_checked_true"))) {
															$("#workflowInfoTD").closest("div").addClass("process_body_checked_false");
														}

														if (lbpm.nowProcessorInfoObj) {
															if (!$("#WF_IFrame").attr('src')) {
																var fdIsModify = $("input[name='sysWfBusinessForm.fdIsModify']")[0];
																if (fdIsModify == null || fdIsModify.value != "1") {
																	var processXml = lbpm.globals.getProcessXmlString();
																	document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value = processXml;
																}
															}
														}
														var url =
															'/ekp/sys/lbpm/flowchart/page/freeflowPanel.jsp?edit=true&extend=oa&template=false&contentField=sysWfBusinessForm.fdFlowContent&statusField=sysWfBusinessForm.fdTranProcessXML&modelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&modelId=16cbcbab019d6f75788c5af41b880edb&hasParentProcess=false&hasSubProcesses=false&showBar=true&isNotShowBar=true&freeflowPanelImg=true&flowType=1&deployApproval=0';
														url += "&flowPopedom=" + lbpm.nowNodeFlowPopedom;
														lbpm.globals.load_Frame('workflowInfoTD', url);
														$('#flowGraphicTemp').hide();
														$('#flowNodeDIV').show();
													} else {
														lbpm.globals.load_Frame('workflowInfoTD',
															'/ekp/sys/lbpm/flowchart/page/panel.html?edit=false&extend=oa&template=false&contentField=sysWfBusinessForm.fdFlowContent&statusField=sysWfBusinessForm.fdTranProcessXML&modelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&modelId=16cbcbab019d6f75788c5af41b880edb&hasParentProcess=false&hasSubProcesses=false'
														);
													}
													lbpm.globals.flowChartLoaded = true;
												};
												lbpm.flow_table_load_Frame = function () {
													lbpm.globals.load_Frame('workflowTableTD', '/ekp/sys/lbpmservice/include/sysLbpmTable_view.jsp' +
														'?edit=false&extend=oa&template=false&contentField=sysWfBusinessForm.fdFlowContent&statusField=sysWfBusinessForm.fdTranProcessXML' +
														'&modelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge&modelId=16cbcbab019d6f75788c5af41b880edb&IdPre=&fdKey=mainDoc'
													);
												};
												lbpm.flow_log_load_Frame = function () {
													lbpm.globals.load_Frame('flowLogTableTD',
														'/ekp/sys/lbpmservice/support/lbpm_audit_note_ui/lbpmAuditNote_flowLog_index.jsp' +
														'?fdModelId=16cbcbab019d6f75788c5af41b880edb&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge'
													);
												};

												lbpm.process_status_load_Frame = function () {
													lbpm.globals.load_Frame('processStatusTD',
														'/ekp/sys/lbpmservice/support/lbpm_process_status/index.jsp' +
														'?fdModelId=16cbcbab019d6f75788c5af41b880edb&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge'
													);
												};
												lbpm.process_restart_Log_Frame = function () {
													lbpm.globals.load_Frame('lbpmProcessRestartLogTD',
														'/ekp/sys/lbpmservice/support/lbpm_process_restart_log/lbpmProcessRestartLog_index.jsp' +
														'?fdModelId=16cbcbab019d6f75788c5af41b880edb&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge'
													);
												};

												if (window.seajs) {
													seajs.use(['lui/imageP/preview'], function (preview) {
														window.previewImage = preview;
													});
												};

												var lbpmPinArray = [];

												//判断是否给元素绑定了卡片弹出框
												___hasLbpmPin = function (curObj) {
													var isBind = false;
													for (var i = 0; i < lbpmPinArray.length; i++) {
														if (lbpmPinArray[i] && lbpmPinArray[i].obj == curObj) {
															isBind = true;
															break;
														}
													}
													return isBind;
												};

												lbpm.person = function (event, element, type) {
													seajs.use(['lui/jquery', 'lui/parser', 'lui/pinwheel'], function ($, parser, p) {
														p($, parser);
														if (!___hasLbpmPin(element)) {
															lbpmPinArray.push({
																obj: element
															});
															if (type == "historyInfoTableIframe") {
																if ($(element).parents("div#auditNoteTable").length > 0) {
																	type = "auditNoteRight";
																}
																//审批记录内的个人卡片弹出框
																$(element).pinwheel({
																	"top": $(element).offset().top + $("#" + type).offset().top,
																	"left": $(element).offset().left + $("#" + type).offset().left
																});
															} else if (type == "flowNodeUL") {
																//自由流个人卡片弹出框
																$(element).pinwheel({
																	"top": $(element).offset().top,
																	"left": $(element).offset().left
																});
															}
														}
													});
												};
											</script>




											<script>
												if (window.LUI) {
													LUI.ready(initLbpmFollow);
												} else {
													$(document).ready(initLbpmFollow);
												}

												function initLbpmFollow() {
													var followOptButton = document.getElementById("followOptButton");
													var cancelFollowOptButton = document.getElementById("cancelFollowOptButton");

													if (followOptButton == null && cancelFollowOptButton == null) {
														return;
													}

													/* if (lbpm.nowProcessorInfoObj) {
														return;
													} */

													var url = Com_Parameter.ContextPath +
														'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=getIsFollowed';
													url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
													var kmssData = new KMSSData();
													var followInfo = {};

													window.lbpmFollowRecordTypeFun = function (src) {
														var val = src.value;
														var nodeObjs = $("[name='node']", $(".lbpmFollow"));
														if (val === "1") {
															nodeObjs.prop("checked", false);
															nodeObjs.prop("disabled", true);
														} else {
															nodeObjs.prop("disabled", false);
														}
													};

													//构建跟踪节点html
													var buildReocrdHtml = function () {
														var html = [];
														var isAll = followInfo.nodeIds ? false : true;
														html.push("<div class='lbpmFollow'>");
														html.push(
															"<div class='recordType'><label><input type='radio' onclick='lbpmFollowRecordTypeFun(this);' name='recordType' " +
															(isAll ? "checked='true'" : "") + " value='1'/>全部节点</label><br/>");
														html.push(
															"<label><input type='radio'  onclick='lbpmFollowRecordTypeFun(this);' name='recordType'" +
															(isAll ? "" : "checked='true'") + " value='0'/>指定节点</label></div>");
														var nodes = lbpm.nodes;
														html.push("<div class='nodeIds'>")
														for (var key in nodes) {
															var nodeObj = nodes[key];
															if ('signNode' == nodeObj.XMLNODENAME ||
																'reviewNode' == nodeObj.XMLNODENAME) {

																html.push("<label><input type='checkBox' name='node' value='" + nodeObj.id + "'");
																if (!isAll) {
																	var followIdArr = followInfo.nodeIds.split(";");
																	for (var i = 0; i < followIdArr.length; i++) {
																		var nodeId = followIdArr[i];
																		if (key === nodeId) {
																			html.push(" checked='true'");
																			break;
																		}
																	}
																} else {
																	html.push(" disabled ");
																}
																html.push("/>" + nodeObj.name + "</label><br/>");
															}
														}
														html.push("</div>");
														html.push("</div>");
														return html.join("");
													};
													//取消跟踪
													var cancelText = '取消跟踪';
													//跟踪流程
													var followText = '流程跟踪';
													//取消/跟踪流程
													var followCancelText = '跟踪/取消跟踪';

													var followUrl = Com_Parameter.ContextPath +
														"sys/lbpmservice/support/lbpm_follow/lbpmFollowConfirm.jsp?isFollow=true";
													var cancelUrl = Com_Parameter.ContextPath +
														"sys/lbpmservice/support/lbpm_follow/lbpmFollowConfirm.jsp?isFollow=false";
													//跟踪函数
													var followFun = function () {
														if (typeof (seajs) != 'undefined') {
															seajs.use(['lui/dialog'], function (dialog) {
																var config = {
																	config: {
																		width: 400,
																		cahce: false,
																		title: followText,
																		height: 600,
																		content: {
																			type: "common",
																			html: buildReocrdHtml(),
																			iconType: '',
																			buttons: [{
																				name: "确定",
																				value: true,
																				focus: true,
																				fn: function (value, dialog) {
																					followCallback();
																					dialog.hide(value);
																				}
																			}, {
																				name: "取消",
																				value: false,
																				styleClass: 'lui_toolbar_btn_gray',
																				fn: function (value, dialog) {
																					dialog.hide(value);
																				}
																			}]
																		}
																	}
																};

																dialog.build(config).on('show', function () {
																	this.element.find(".lui_dialog_common_content_right").css(
																		"max-width", "100%");
																	this.element.find(".lui_dialog_common_content_right").css(
																		"margin-left", "0px");
																}).show();
															});
														} else {
															var lbpmInfo = {
																"lbpm": lbpm,
																"followInfo": followInfo,
																"newFollow": true
															};
															LbpmFollow_PopupWindow(followUrl, lbpmInfo, followCallback);
														}
													};

													//跟踪回调
													var followCallback = function (rtnData) {
														//跟踪类型 1:全部节点;0:指定节点
														var recordType = $("[name='recordType']:checked").val();
														//有返回值,说明用的是旧UI
														var oldUINodeIds;
														if (rtnData) {
															var value = rtnData.GetHashMapArray()[0];
															recordType = value.recordType;
															if (!value.follow) {
																return;
															}
															if (value.nodeIds) {
																oldUINodeIds = value.nodeIds;
															}
														}
														//新UI可以从 $("[name='recordType']:checked").val()获取值,如果获取不到,又没有rtnData,则说明使用旧UI直接关掉了窗口导致没有返回值
														if (!recordType) {
															return;
														}
														var url = Com_Parameter.ContextPath +
															'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=recordFollow';
														url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value + "&modelName=" + $(
															"[name='sysWfBusinessForm.fdModelName']")[0].value;
														url += "&recordType=" + recordType;
														//指定节点
														var nodeIds = [];
														if (recordType === "0") {
															var nodeObjs = $("[name='node']:checked");
															nodeObjs.each(function (index, obj) {
																nodeIds.push(obj.value);
															});
															url += "&nodeIds=" + (nodeIds.join(";") || (oldUINodeIds ? oldUINodeIds : ""));
														} else {
															url += "&nodeIds=";
														}
														var kmssData = new KMSSData();
														kmssData.SendToUrl(url, function (http_request) {
															var responseText = http_request.responseText;
															var json = eval("(" + responseText + ")");
															followInfo.nodeIds = json.nodeIds;
															if (json.result == "true") {
																//cancel标识是跟踪还是取消
																//isAll标识跟踪类型是全部节点还是指定节点
																if (json.isCancel === "true") {
																	//显示跟踪
																	lbpm.globals.hiddenObject(followOptButton, false);
																	//隐藏取消
																	lbpm.globals.hiddenObject(cancelFollowOptButton, true);
																} else {
																	//隐藏跟踪
																	lbpm.globals.hiddenObject(followOptButton, true);
																	//显示取消
																	lbpm.globals.hiddenObject(cancelFollowOptButton, false);
																	if (json.isAll === "true") {
																		//如果全部节点或者流程结束则显示"取消跟踪",
																		if ($("#cancelFollowOptButton")[0].tagName.toLowerCase() == "a") {
																			$("#cancelFollowOptButton").text(cancelText);
																		} else {
																			$("#cancelFollowOptButton").attr("title", cancelText);
																			var cancelFollowLUI = LUI("cancelFollowOptButton");
																			if (cancelFollowLUI != null) {
																				cancelFollowLUI.textContent.text(cancelText);
																			}
																		}
																		cancelFollowOptButton.onclick = cancelFollowFun;
																	} else {
																		//如果指定节点则显示"跟踪/取消跟踪"
																		if ($("#cancelFollowOptButton")[0].tagName.toLowerCase() == "a") {
																			$("#cancelFollowOptButton").text(followCancelText);
																		} else {
																			$("#cancelFollowOptButton").attr("title", followCancelText);
																			var cancelFollowLUI = LUI("cancelFollowOptButton");
																			if (cancelFollowLUI != null) {
																				cancelFollowLUI.textContent.text(followCancelText);
																			}
																		}
																		cancelFollowOptButton.onclick = followFun;
																	}
																}
															}
														});
													};

													//旧交互跟踪函数
													var oldFollowFun = function () {
														if (typeof (seajs) != 'undefined') {
															seajs.use(['lui/dialog'], function (dialog) {
																dialog.confirm('确认跟踪该流程吗？', oldFollowFunCallback);
															});
														} else {
															var lbpmInfo = {
																"lbpm": lbpm,
																"followInfo": followInfo,
																"oldFollow": true
															};
															var oldFollowUrl = followUrl + "&oldFollow=true";
															LbpmFollow_PopupWindow(followUrl, lbpmInfo, oldFollowFunCallback);
														}
													};

													//旧交互跟踪回调
													var oldFollowFunCallback = function (rtnData) {
														if (!rtnData) return;
														var flag;
														if (jQuery.type(rtnData) === "boolean") {
															flag = rtnData;
														} else {
															var value = rtnData.GetHashMapArray()[0];
															flag = value.follow;
														}
														if (flag == true) {
															var url = Com_Parameter.ContextPath +
																'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=recordFollow';
															url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value +
																"&modelName=" + $("[name='sysWfBusinessForm.fdModelName']")[0].value;
															var kmssData = new KMSSData();
															kmssData.SendToUrl(url, function (http_request) {
																var responseText = http_request.responseText;
																var json = eval("(" + responseText + ")");
																if (json.result == "true") {
																	lbpm.globals.hiddenObject(followOptButton, true);
																	lbpm.globals.hiddenObject(cancelFollowOptButton, false);
																}
															});
														} else {
															return;
														}
													};

													//取消跟踪函数
													var cancelFollowFun = function () {
														if (typeof (seajs) != 'undefined') { //
															seajs.use(['lui/dialog'], function (dialog) {
																dialog.confirm('确认取消跟踪该流程吗？', cancelFollowFunCallback);
															});
														} else {
															var lbpmInfo = {
																"lbpm": lbpm,
																"followInfo": followInfo,
																"cancel": true
															};
															var winOpen = LbpmFollow_PopupWindow(cancelUrl, lbpmInfo, cancelFollowFunCallback);
														}
													};
													//取消跟踪回调
													var cancelFollowFunCallback = function (rtnData) {
														if (!rtnData) return;
														var flag;
														//新UI返回boolean类型的值
														if (jQuery.type(rtnData) === "boolean") {
															flag = rtnData;
														} else { //旧UI返回kmssdialog对象
															var value = rtnData.GetHashMapArray()[0];
															flag = value.cancel;
														}
														if (flag == true) {
															var url = Com_Parameter.ContextPath +
																'sys/lbpmservice/support/lbpm_follow/lbpmFollow.do?method=cancelFollow';
															url += "&processId=" + $("[name='sysWfBusinessForm.fdProcessId']")[0].value;
															var kmssData = new KMSSData();
															kmssData.SendToUrl(url, function (http_request) {
																var responseText = http_request.responseText;
																var json = eval("(" + responseText + ")");
																if (json.result == "true") {
																	lbpm.globals.hiddenObject(cancelFollowOptButton, true);
																	lbpm.globals.hiddenObject(followOptButton, false);
																}
															});
														} else {
															return;
														}
													};

													if (followOptButton != null) {
														//设置流程跟踪按钮
														//'确认跟踪该流程吗？'
														if (window != window.top) {
															followOptButton.onclick = oldFollowFun
														} else {
															followOptButton.onclick = followFun;
														}
													}

													if (cancelFollowOptButton != null) {
														//设置取消跟踪按钮
														cancelFollowOptButton.onclick = cancelFollowFun;
													}

													kmssData.SendToUrl(url, function (http_request) {
														var responseText = http_request.responseText;
														followInfo = eval("(" + responseText + ")");

														if (followInfo.isFollowed == "true") {
															if (followInfo.nodeIds) {
																if ($("#cancelFollowOptButton")[0].tagName.toLowerCase() == "a") {
																	$("#cancelFollowOptButton").text(followCancelText);
																} else {
																	$("#cancelFollowOptButton").attr("title", followCancelText);
																	var cancelFollowLUI = LUI("cancelFollowOptButton");
																	if (cancelFollowLUI != null) {
																		cancelFollowLUI.textContent.text(followCancelText);
																	}
																}
																cancelFollowOptButton.onclick = followFun;
															}
															//流程是否结束,若结束显示取消跟踪
															if (followInfo.isPassed) {
																if ($("#cancelFollowOptButton")[0].tagName.toLowerCase() == "a") {
																	$("#cancelFollowOptButton").text(cancelText);
																} else {
																	$("#cancelFollowOptButton").attr("title", cancelText);
																	var cancelFollowLUI = LUI("cancelFollowOptButton");
																	if (cancelFollowLUI != null) {
																		cancelFollowLUI.textContent.text(cancelText);
																	}
																}
																cancelFollowOptButton.onclick = cancelFollowFun;
															}
															lbpm.globals.hiddenObject(cancelFollowOptButton, false);
														} else {
															lbpm.globals.hiddenObject(followOptButton, false);
															if (followInfo.isPassed) {
																followOptButton.onclick = oldFollowFun;
															}
														}
													});
												}

												//旧UI弹窗
												function LbpmFollow_PopupWindow(url, lbpmInfo, action) {
													var dialog = new KMSSDialog();
													var lbpmInfo = lbpmInfo;
													dialog.parameter = lbpmInfo;
													dialog.BindingField(null, null);
													dialog.SetAfterShow(function (value) {
														action(value);
													});
													dialog.URL = url;
													dialog.Show(window.screen.width * 400 / 1366, 150);
												}
											</script>

											<script>
												$(document).ready(function () {
													//判断当前流程是否重启过，重启过则显示重启日志标签
													var liProcess_restart_Log_Frame = $("#liProcess_restart_Log_Frame");
													if (liProcess_restart_Log_Frame) {
														var lbpmProcessRestartLogData = new KMSSData().AddBeanData(
																"lbpmProcessRestartLogService&processId=16cbcbab019d6f75788c5af41b880edb")
															.GetHashMapArray();
														if (lbpmProcessRestartLogData.length > 0) {
															if (lbpmProcessRestartLogData[0].logType == "false") {
																liProcess_restart_Log_Frame.hide();
															}

														}
													}
												});
											</script>


											<!--引入暂存按钮-->


















											<!-- 表单暂存开始 -->

											<!-- 表单暂存结束 -->

											<!-- 表单数据暂存脚本开始 -->
											<script type="text/javascript">
												Com_IncludeFile("optbar.js");
												$(function () {
													//使用新UI
													if (typeof LUI !== "undefined" && !document.getElementById("optBarDiv")) {
														LUI.ready(function () {
															var currentHandler = lbpm.processorInfoObj;
															if (currentHandler && currentHandler.length <= 0) {
																var button = LUI("saveFormData");
																if (button) {
																	LUI("toolbar").removeButton(button);
																}
															}
														});
													} else {
														//老UI
														if (document.getElementById("optBarDiv")) {
															if (document.getElementById("saveFormData")) {
																document.getElementById("saveFormData").style.display = "none";
															}
															if (document.getElementById("_saveFormDataBtnDiv")) {
																OptBar_AddOptBar("_saveFormDataBtnDiv");
															}
														}
													}
												})


												function _saveFormData() {
													//#48135 增加EKP审批过程中暂存表单的功能（已测试过，提交时把sysWfBusinessForm.canStartProcess字段值置为false即可实现这个功能，希望纳入产品）
													var canStartProcess = document.getElementById("sysWfBusinessForm.canStartProcess");
													var docStatus = "30";
													var formName = "kmsMultidocKnowledgeForm";
													$(canStartProcess).prop("value", "false");
													//触发流程查看页面提交按钮
													/* $("#process_review_button").click(); */
													/* var _reviewValdate = $KMSSValidation(document.forms[0]);
													_reviewValdate.removeElements(document.forms[0],'required'); */
													if (lbpm) {
														lbpm.saveFormData = true;
													}
													if (docStatus === "11") {
														if (beforeLbpmSubmit) {
															beforeLbpmSubmit();
														}
														Com_Submit(document[formName] || document.forms[0], 'update');
													}
													if (docStatus === "20") {
														$("#process_review_button").click();
													}
												}
											</script>

											<!--引入切换表单按钮-->




















											<!-- 多表单模式下，显示切换表单按钮 -->


											<script type="text/javascript">
												//切换表单
												function Subform_switchForm() {
													var subFormInfoObj = lbpm.subFormInfoObj;
													if (subFormInfoObj && subFormInfoObj.length > 0) {
														var msg = '<div style="text-align:left;">'
														var isExit = false;
														for (var i = 0; i < subFormInfoObj.length; i++) {
															if (subFormInfoObj[i]["id"] == "default") {
																isExit = true;
															}
															msg += '<label><input type="radio" name="subform_other" value="' + subFormInfoObj[i]["id"] +
																'"';
															if (subFormInfoObj[i]["id"] == lbpm.nowSubFormId) {
																msg += ' checked="checked">' + subFormInfoObj[i]["name"] + '(当前表单)';
															} else if (subFormInfoObj[i]["task"] == "1") {
																msg += ' task="1">' + subFormInfoObj[i]["name"] + '(有待处理事务)';
															} else {
																msg += ' task="0">' + subFormInfoObj[i]["name"];
															}
															msg += '</input></label><br><br>';
														}
														if (!isExit) {
															if (lbpm.nowSubFormId == "default") {
																msg +=
																	'<label><input type="radio" name="subform_other" value="default" checked="checked">默认表单(当前表单)</input></label><br><br>';
															} else {
																msg +=
																	'<label><input type="radio" name="subform_other" value="default">默认表单</input></label><br><br>';
															}
														}
														msg += '</div>';
														seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
															dialog.build({
																config: {
																	width: 420,
																	cahce: false,
																	title: "切换表单",
																	content: {
																		type: "common",
																		html: msg,
																		iconType: 'question',
																		buttons: [{
																			name: "确定",
																			value: true,
																			focus: true,
																			fn: function (value, dialog) {
																				Subform_switchForm_ok();
																				dialog.hide(value);
																			}
																		}, {
																			name: "取消",
																			value: false,
																			styleClass: 'lui_toolbar_btn_gray',
																			fn: function (value, dialog) {
																				dialog.hide(value);
																			}
																		}]
																	}
																}
															}).show();
														});
													}
												}

												function Subform_switchForm_ok() {
													var subFormCheckObj = $("input[name='subform_other']:checked");
													var subFormId = subFormCheckObj.val();
													if (lbpm.nowSubFormId == subFormId) {
														return
													}
													var url = window.location.href;
													url = Com_SetUrlParameter(url, "s_xform", subFormId);
													if (lbpm.nowSubFormId == subFormId || subFormCheckObj.attr("task") == "1") {
														setTimeout(function () {
															window.location.href = url;
														}, 200);
													} else {
														setTimeout(function () {
															window.open(url);
														}, 200);
													}
												}

												//多表单模式下是否开启打印模板，缓存到页面，以免每次点击打印按钮都调用后台去查
												var subform_isCanPrint = '';

												//打印
												function subform_print_BySubformId(url) {
													var subFormInfoObj = lbpm.subFormInfoObj;
													if (subFormInfoObj && subFormInfoObj.length > 0) {
														var printIds = [];
														var printNames = [];
														var nodes = [];
														for (var i = 0; i < subFormInfoObj.length; i++) {
															if (subFormInfoObj[i]["id"] == lbpm.nowSubFormId) {
																nodes = subFormInfoObj[i]["nodes"];
															}
														}
														if (nodes.length > 0) {
															for (var j = 0; j < nodes.length; j++) {
																if (printIds.join(",").indexOf(nodes[j]["subFormPrintId"]) < 0) {
																	printIds.push(nodes[j]["subFormPrintId"]);
																	printNames.push(nodes[j]["subFormPrintName"]);
																}
															}
														}
														var isExit = false;
														if (printIds.length == 0 || (printIds.length == 1 && printIds[0] == "default")) {
															subform_print_default(url);
														} else {
															if (!subform_isCanPrint) {
																var checkUrl = Com_Parameter.ContextPath +
																	"sys/lbpmservice/support/lbpmSubFormAction.do?method=checkIsCanPrint";
																var data = {
																	"modelId": "16cbcbab019d6f75788c5af41b880edb",
																	"modelName": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"
																};
																$.ajax({
																	type: "POST",
																	data: data,
																	url: checkUrl,
																	async: false,
																	success: function (json) {
																		if (json) {
																			subform_isCanPrint = json;
																		}
																	}
																});
															}
															if (subform_isCanPrint == "true") {
																var msg = '<div style="text-align:left;">';
																for (var k = 0; k < printIds.length; k++) {
																	if (printIds[k] == "default") {
																		isExit = true;
																	}
																	msg += '<label><input type="radio" name="switch_print" value="' + printIds[k] + '"';
																	if (k == 0) {
																		msg += ' checked="checked">';
																	} else {
																		msg += '>';
																	}
																	msg += printNames[k] + '</input></label><br><br>';
																}
																if (!isExit) {
																	msg +=
																		'<label><input type="radio" name="switch_print" value="default">默认打印模板</input></label><br><br>';
																}
																msg += '</div>';
																seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
																	dialog.build({
																		config: {
																			width: 420,
																			cahce: false,
																			title: "选择打印模板",
																			content: {
																				type: "common",
																				html: msg,
																				iconType: 'question',
																				buttons: [{
																					name: "确定",
																					value: true,
																					focus: true,
																					fn: function (value, dialog) {
																						subform_switchPrint_ok(url);
																						dialog.hide(value);
																					}
																				}, {
																					name: "取消",
																					value: false,
																					styleClass: 'lui_toolbar_btn_gray',
																					fn: function (value, dialog) {
																						dialog.hide(value);
																					}
																				}]
																			}
																		}
																	}).show();
																});
															} else {
																subform_print_default(url);
															}
														}
													} else {
														subform_print_default(url);
													}
												}

												function subform_print_default(url) {
													if (lbpm.subFormInfoObj && lbpm.subFormInfoObj.length > 0 && lbpm.nowSubFormId) {
														if (!(lbpm.subFormInfoObj.length == 1 && lbpm.subFormInfoObj[0]["id"] == "default")) {
															url = Com_SetUrlParameter(url, "s_xform", lbpm.nowSubFormId);
														}
													}
													Com_OpenWindow(url);
												}

												function subform_switchPrint_ok(url) {
													var subPrintCheckObj = $("input[name='switch_print']:checked");
													url = Com_SetUrlParameter(url, "s_print", subPrintCheckObj.val());
													url = Com_SetUrlParameter(url, "s_xform", lbpm.nowSubFormId);
													Com_OpenWindow(url);
												}

												//编辑
												function subform_edit_BySubformId(url, target) {
													if (!target) {
														target = "_self";
													}
													if (lbpm.subFormInfoObj && lbpm.subFormInfoObj.length > 0 && lbpm.nowSubFormId) {
														if (!(lbpm.subFormInfoObj.length == 1 && lbpm.subFormInfoObj[0]["id"] == "default")) {
															url = Com_SetUrlParameter(url, "s_xform", lbpm.nowSubFormId);
														}
													}
													Com_OpenWindow(url, target);
												}
											</script>





											<form name="sysWfProcessForm" method="POST"
												action="/ekp/sys/lbpmservice/support/lbpm_process/lbpmProcess.do">


												<input type="hidden" id="sysWfBusinessForm.fdParameterJson"
													name="sysWfBusinessForm.fdParameterJson" value="">
												<input type="hidden" id="sysWfBusinessForm.fdAdditionsParameterJson"
													name="sysWfBusinessForm.fdAdditionsParameterJson" value="">
												<input type="hidden" id="sysWfBusinessForm.fdIsModify"
													name="sysWfBusinessForm.fdIsModify" value="">
												<input type="hidden" id="sysWfBusinessForm.fdCurHanderId"
													name="sysWfBusinessForm.fdCurHanderId"
													value="1183b0b84ee4f581bba001c47a78b2d9">
												<input type="hidden" id="sysWfBusinessForm.fdCurNodeSavedXML"
													name="sysWfBusinessForm.fdCurNodeSavedXML"
													value="<reviewing><tasks></tasks></reviewing>">
												<input type="hidden" id="sysWfBusinessForm.fdFlowContent"
													name="sysWfBusinessForm.fdFlowContent" value="<process anonymousVote=&quot;false&quot; dayOfNotifyDrafter=&quot;0&quot; dayOfNotifyPrivileger=&quot;15&quot; descriptionLangJson=&quot;[{&amp;quot;lang&amp;quot;:&amp;quot;zh-CN&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;en-US&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;zh-HK&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;ja-JP&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;&amp;quot;}]&quot; fdId=&quot;16a009e97b11686d4c180f449e9accb8&quot; hiddenCommunicateNoteEnabled=&quot;true&quot; hourOfNotifyDrafter=&quot;0&quot; hourOfNotifyPrivileger=&quot;0&quot; iconType=&quot;2&quot; ignoreOnFutureHandlerSame=&quot;false&quot; ignoreOnFutureHandlerSamePlus=&quot;false&quot; isDetail=&quot;true&quot; laneRolesIndex=&quot;0&quot; laneStagesIndex=&quot;0&quot; linesIndex=&quot;2&quot; minuteOfNotifyDrafter=&quot;0&quot; minuteOfNotifyPrivileger=&quot;0&quot; multiCommunicateEnabled=&quot;true&quot; nodesIndex=&quot;3&quot; notifyDraftOnFinish=&quot;true&quot; notifyOnFinish=&quot;true&quot; notifyType=&quot;todo&quot; orgAttribute=&quot;privilegerIds:privilegerNames&quot; privilegerIds=&quot;&quot; privilegerNames=&quot;&quot; recalculateHandler=&quot;true&quot; rejectReturn=&quot;false&quot;>
		<nodes>
		<draftNode canAddAuditNoteAtt=&quot;true&quot; id=&quot;N2&quot; langs=&quot;{&amp;quot;nodeName&amp;quot;:[{&amp;quot;lang&amp;quot;:&amp;quot;zh-CN&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;起草节点&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;en-US&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;起草节点&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;zh-HK&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;起草節點&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;ja-JP&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;起草节点&amp;quot;}]}&quot; name=&quot;起草节点&quot; subFormMobileId=&quot;default&quot; subFormMobileName=&quot;默认表单&quot; x=&quot;400&quot; y=&quot;140&quot;/>
		<endNode id=&quot;N3&quot; langs=&quot;{&amp;quot;nodeName&amp;quot;:[{&amp;quot;lang&amp;quot;:&amp;quot;zh-CN&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;结束节点&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;en-US&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;结束节点&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;zh-HK&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;結束節點&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;ja-JP&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;结束节点&amp;quot;}]}&quot; name=&quot;结束节点&quot; subFormMobileId=&quot;default&quot; subFormMobileName=&quot;默认表单&quot; x=&quot;400&quot; y=&quot;380&quot;/>
		<startNode id=&quot;N1&quot; langs=&quot;{&amp;quot;nodeName&amp;quot;:[{&amp;quot;lang&amp;quot;:&amp;quot;zh-CN&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;开始节点&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;en-US&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;开始节点&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;zh-HK&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;開始節點&amp;quot;},{&amp;quot;lang&amp;quot;:&amp;quot;ja-JP&amp;quot;,&amp;quot;value&amp;quot;:&amp;quot;开始节点&amp;quot;}]}&quot; name=&quot;开始节点&quot; subFormMobileId=&quot;default&quot; subFormMobileName=&quot;默认表单&quot; x=&quot;400&quot; y=&quot;60&quot;/>
		</nodes>
		<lines>
		<line endNodeId=&quot;N2&quot; endPosition=&quot;1&quot; id=&quot;L1&quot; points=&quot;400,80;400,120&quot; startNodeId=&quot;N1&quot; startPosition=&quot;3&quot;/>
		<line endNodeId=&quot;N3&quot; endPosition=&quot;1&quot; id=&quot;L2&quot; points=&quot;400,160;400,360&quot; startNodeId=&quot;N2&quot; startPosition=&quot;3&quot;/>
		</lines>
		<laneRoles/>
		<laneStages/>
		</process>
		">
												<input type="hidden" id="sysWfBusinessForm.fdProcessId"
													name="sysWfBusinessForm.fdProcessId"
													value="16cbcbab019d6f75788c5af41b880edb">
												<input type="hidden" id="sysWfBusinessForm.fdKey"
													name="sysWfBusinessForm.fdKey" value="mainDoc">
												<input type="hidden" id="sysWfBusinessForm.fdModelId"
													name="sysWfBusinessForm.fdModelId"
													value="16cbcbab019d6f75788c5af41b880edb">
												<input type="hidden" id="sysWfBusinessForm.fdModelName"
													name="sysWfBusinessForm.fdModelName"
													value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge">
												<input type="hidden" id="sysWfBusinessForm.fdCurNodeXML"
													name="sysWfBusinessForm.fdCurNodeXML"
													value="<reviewing></reviewing>">
												<input type="hidden" id="sysWfBusinessForm.fdTemplateModelName"
													name="sysWfBusinessForm.fdTemplateModelName" value="">
												<input type="hidden" id="sysWfBusinessForm.fdTemplateKey"
													name="sysWfBusinessForm.fdTemplateKey" value="">
												<input type="hidden" id="sysWfBusinessForm.canStartProcess"
													name="sysWfBusinessForm.canStartProcess" value="">
												<input type="hidden" id="sysWfBusinessForm.fdTranProcessXML"
													name="sysWfBusinessForm.fdTranProcessXML"
													value="<process-status><runningNodes></runningNodes><historyNodes><node id=&quot;N1&quot; routeType=&quot;NORMAL&quot; targetId=&quot;N2&quot; targetName=&quot;起草节点&quot; modelId=&quot;16cbcbab15d6dbfdac6eb9f4053bc2c9&quot;/><node id=&quot;N2&quot; routeType=&quot;NORMAL&quot; targetId=&quot;N3&quot; targetName=&quot;结束节点&quot; modelId=&quot;16cbcbab1b258b9fb149aef4985b65f6&quot;/><node id=&quot;N3&quot; routeType=&quot;NORMAL&quot; targetId=&quot;&quot; targetName=&quot;&quot; modelId=&quot;16cbcbd022f7f9efecbc13e4fcdb3f5a&quot;/></historyNodes></process-status>">
												<input type="hidden" id="sysWfBusinessForm.fdDraftorId"
													name="sysWfBusinessForm.fdDraftorId"
													value="1708492377949409d66096d4db38c71b">
												<input type="hidden" id="sysWfBusinessForm.fdDraftorName"
													name="sysWfBusinessForm.fdDraftorName" value="郑颖玉">
												<input type="hidden" id="sysWfBusinessForm.fdHandlerRoleInfoIds"
													name="sysWfBusinessForm.fdHandlerRoleInfoIds"
													value="1183b0b84ee4f581bba001c47a78b2d9;1721c66ee788a726281390a4e409a52b">
												<input type="hidden" id="sysWfBusinessForm.fdHandlerRoleInfoNames"
													name="sysWfBusinessForm.fdHandlerRoleInfoNames" value="冯友友;前端工程师">
												<input type="hidden" id="sysWfBusinessForm.fdAuditNoteFdId"
													name="sysWfBusinessForm.fdAuditNoteFdId"
													value="173bdccbfef30b2b0a1e7ac42cd82f6d">
												<input type="hidden" id="docStatus" name="docStatus" value="30">
												<input type="hidden" id="sysWfBusinessForm.fdIdentityId"
													name="sysWfBusinessForm.fdIdentityId"
													value="1708492377949409d66096d4db38c71b">
												<input type="hidden" id="sysWfBusinessForm.fdProcessStatus"
													name="sysWfBusinessForm.fdProcessStatus" value="">
												<input type="hidden" id="sysWfBusinessForm.fdSubFormXML"
													name="sysWfBusinessForm.fdSubFormXML" value="">
												<!-- 留下其他信息处理的域，为了兼容特权人修改流程图信息时保存其他设计的信息 -->
												<input type="hidden" id="sysWfBusinessForm.fdOtherContentInfo"
													name="sysWfBusinessForm.fdOtherContentInfo" value="{}">
												<script>
													$(document).ready(function () {
														$("li[name='process_head_tab']").click(function () {
															$("li[name='process_head_tab']").attr("class", "");
															$(this).attr("class", "active");

															//兼容有些模块下无法触发onresize事件的问题
															var isClick = $(this).attr("data-isClick");
															var dataLoad = $(this).attr("data-load");
															if (!isClick) {
																$(this).attr("data-isClick", "true");
																if (dataLoad) {
																	lbpm[dataLoad]();
																}
															}
															$("div[name='process_body']").attr("class", "process_body_checked_false");
															var lis = $(this).parent().children();
															for (var i = 0; i < lis.length; i++) {
																var classValue = $(lis[i]).attr("class");
																if (classValue == "active") {
																	var process_bodys = $("div[name='process_body']");
																	$(process_bodys[i]).attr("class", "process_body_checked_true");
																}
															}
														});
													});
												</script>
												<!--begin 选项卡头部 -->
												<div class="lui_flowstate_tab_heading">
													<ul class="lui_flowstate_tabhead">
														<li name="process_head_tab" class="active"><a
																href="javascript:void(0);">流程处理</a></li>
														<li name="process_head_tab"
															data-load="process_status_load_Frame"><a
																href="javascript:void(0);">流程状态</a></li>
														<li name="process_head_tab" data-load="flow_chart_load_Frame"><a
																href="javascript:void(0);">流程图</a></li>
														<li name="process_head_tab" data-load="flow_table_load_Frame"><a
																href="javascript:void(0);">流程表格</a></li>
														<li name="process_head_tab" data-load="flow_log_load_Frame"><a
																href="javascript:void(0);">流程日志</a></li>
														<li name="process_head_tab"
															data-load="process_restart_Log_Frame"
															id="liProcess_restart_Log_Frame" style="display: none;"><a
																href="javascript:void(0);">重启日志</a></li>
													</ul>
												</div>
												<!--end 选项卡头部 -->

												<!--begin 流程处理  -->
												<script
													src="/ekp/sys/lbpmservice/common/process_view_dialog.js?s_cache=1596422898836">
												</script>




												<table class="tb_normal process_review_panel" width="100%"
													style="display:none" id="processDev">

													<!--update by wubing date 2016-03-17,开放驳回到起草时，起草人可以进入处理-->



												</table>


												<!--end 流程处理  -->
												<div name="process_body" class="process_body_checked_true">
													<table class="tb_normal process_review_panel" width="100%">

														<tbody>
															<tr>
																<td class="td_normal_title" width="15%">
																	流程说明
																</td>
																<td colspan="3">
																	<span id="fdFlowDescription"></span>
																</td>
															</tr>


															<tr class="tr_normal_title">
																<td align="left" colspan="4">
																	<label><input type="checkbox" value="true"
																			checked=""
																			onclick="lbpm.globals.showHistoryDisplay(this);">
																		显示审批记录</label>
																</td>
															</tr>
															<tr id="historyTableTR">
																<td colspan="4" id="historyInfoTableTD"
																	_onresize="lbpm.load_Frame();" style="padding: 0;">
																	<iframe id="historyInfoTableIframe" width="100%"
																		style="margin-bottom: -3px;border: none;"
																		scrolling="no" frameborder="0"></iframe>
																</td>
															</tr>





															<tr class="tr_normal_title" style="display:none">
																<td align="left" colspan="4">
																	<label><input type="checkbox" value="true"
																			onclick="lbpm.globals.showDetails(checked);">
																		更多信息</label>wangx-log
																</td>
															</tr>
															<tr id="showDetails" style="display:none">
																<td colspan="4">
																	<table id="Label_Tabel_Workflow_Info" width="100%"
																		class="tb_label">

																		<tbody>
																			<tr>
																				<td class="td_label0"></td>
																			</tr>
																		</tbody>
																	</table>
																</td>
															</tr>


															<tr id="nodeCanViewCurNodeTR" style="display:none;">
																<td class="td_normal_title" width="15%">可查看本节点意见的节点</td>
																<td>
																	<input type="hidden"
																		name="wf_nodeCanViewCurNodeIds">
																	<textarea name="wf_nodeCanViewCurNodeNames"
																		style="width:85%" readonly=""></textarea>
																	<a href="javascript:;" class="com_btn_link"
																		onclick="lbpm.globals.selectNotionNodes();">选择</a>
																</td>
															</tr>
															<tr id="otherCanViewCurNodeTR" style="display:none;">
																<td class="td_normal_title" width="15%">本节点意见的其他可阅读者
																</td>
																<td>
																	<input type="hidden"
																		name="wf_otherCanViewCurNodeIds">
																	<textarea name="wf_otherCanViewCurNodeNames"
																		style="width:85%" readonly=""></textarea>
																	<a href="javascript:;" class="com_btn_link"
																		onclick="Dialog_Address(true,'wf_otherCanViewCurNodeIds','wf_otherCanViewCurNodeNames', ';',ORG_TYPE_ALL,function myFunc(rtv){lbpm.globals.updateXml(rtv,'otherCanViewCurNode');});">选择</a>
																</td>
															</tr>

														</tbody>
													</table>
												</div>
												<!-- begin流程状态 -->
												<div name="process_body" class="process_body_checked_false">
													<table width="100%">
														<tbody>
															<tr>
																<td id="processStatusTD"
																	_onresize="lbpm.process_status_load_Frame();">
																	<iframe width="100%" height="100%" scrolling="no"
																		frameborder="0"></iframe>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<!-- end流程状态 -->

												<!-- begin流程图 -->
												<div name="process_body" class="process_body_checked_false">
													<table class="tb_normal process_review_panel" width="100%">


														<tbody>
															<tr class="tr_normal_title" style="display:none">
																<td align="left" colspan="4">
																	<label><input type="checkbox"
																			id="flowGraphicShowCheckbox" value="true"
																			onclick="this.checked ? $('#flowGraphic').show() : $('#flowGraphic').hide();">
																		流程图</label>
																</td>
															</tr>
															<tr id="flowGraphic">
																<td id="workflowInfoTD"
																	_onresize="lbpm.flow_chart_load_Frame();"
																	colspan="4">
																	<iframe width="100%" height="100%" scrolling="no"
																		id="WF_IFrame"></iframe>
																	<script>
																		$("#WF_IFrame").ready(function () {
																			var lbpm_panel_reload = function () {
																				$("#WF_IFrame").attr('src', function (i, val) {
																					return val;
																				});
																			};
																			lbpm.events.addListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE, lbpm_panel_reload);
																			lbpm.events.addListener(lbpm.constant.EVENT_MODIFYPROCESS, lbpm_panel_reload);
																			lbpm.events.addListener(lbpm.constant.EVENT_SELECTEDMANUAL, lbpm_panel_reload);
																		});
																		$('#flowGraphicShowCheckbox').bind('click', function () {
																			$('#workflowInfoTD').each(function () {
																				var str = this.getAttribute('onresize');
																				if (str) {
																					(new Function(str))();
																				}
																			});
																		});
																		$('#relationProcessCheckBox').bind('click', function () {
																			if (this.checked == true) {
																				if ($('#parentFlowTR')) {
																					$('#parentFlowTR').show();
																				}
																				if ($('#subFlowTR')) {
																					$('#subFlowTR').show();
																				}
																			} else {
																				if ($('#parentFlowTR')) {
																					$('#parentFlowTR').hide();
																				}
																				if ($('#subFlowTR')) {
																					$('#subFlowTR').hide();
																				}
																			}

																		});
																	</script>
																</td>
															</tr>

														</tbody>
													</table>
												</div>
												<!-- end流程图 -->

												<!-- begin流程表格 -->
												<div name="process_body" class="process_body_checked_false">
													<table width="100%">

														<tbody>
															<tr lks_labelname="表格">
																<td id="workflowTableTD"
																	_onresize="lbpm.flow_table_load_Frame();">
																	<iframe width="100%" height="100%" scrolling="no"
																		id="WF_TableIFrame" frameborder="0"></iframe>
																</td>
															</tr>

														</tbody>
													</table>
												</div>
												<!-- end流程表格 -->

												<!-- begin流程日志 -->
												<div name="process_body" class="process_body_checked_false">
													<table width="100%">
														<tbody>
															<tr lks_labelname="流转日志">
																<td id="flowLogTableTD"
																	_onresize="lbpm.flow_log_load_Frame();">
																	<iframe width="100%" height="100%" scrolling="no"
																		frameborder="0"></iframe>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<!-- end流程日志 -->

												<!-- begin重启日志 -->
												<div name="process_body" class="process_body_checked_false">
													<table width="100%">
														<tbody>
															<tr lks_labelname="流转日志">
																<td id="lbpmProcessRestartLogTD"
																	_onresize="lbpm.process_restart_Log_Frame();">
																	<iframe width="100%" height="100%" scrolling="no"
																		frameborder="0"></iframe>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
												<!-- end重启日志 -->



											</form>



											<script>
												if (lbpm.adminOperationsReviewJs.length > 0) {
													lbpm.globals.includeFile("/sys/lbpmservice/node/node_common_review.js", "/ekp");
												}
											</script>




										</div>
									</div>
									<div data-lui-mark="panel.content.operation"
										class="lui_portlet_operations clearfloat"> </div>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_tabpage_float_footer_l">
						<div class="lui_tabpage_float_footer_r">
							<div class="lui_tabpage_float_footer_c"></div>
						</div>
					</div>
				</div>
			</div><iframe frameborder="0" class="lui_tabpage_float_navs_mark" scrolling="no"></iframe>
			<div class="lui_tabpage_float_navs">
				<div class="lui_tabpage_float_navs_l">
					<div class="lui_tabpage_float_navs_r">
						<div class="lui_tabpage_float_navs_c" style="max-width: 1200px;">
							<div class="lui_tabpage_float_nav_item selected">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">
											<div id="eval_label_title">点评</div>
										</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">
											<div id="intr_label_title" name="introviewnames">推荐</div>
										</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">纠错记录(1)</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">访问统计</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">权限</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">版本</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">发布</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">发布到课件</div>
									</div>
								</div>
							</div>
							<div class="lui_tabpage_float_nav_item">
								<div class="lui_tabpage_float_nav_item_l">
									<div class="lui_tabpage_float_nav_item_r">
										<div class="lui_tabpage_float_nav_item_c">流程处理</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="lui_tabpage_float_collapse  lui_tabpage_uncollapse lui_tabpage_collapsed" title="收起"><a
					class="txt">收起</a></div>
		</div>

	</div>
</template:replace>