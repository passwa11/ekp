<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('sys-modeling-main:modeling.import.configuration') }</template:replace>
	<template:replace name="head">
		<template:super/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/transport/css/maincss.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/transport/css/common.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/transport/css/transport.css?s_cache=${LUI_Cache }"/>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/modeling/base/transport/js/importUpload.js"></script>
		<style>
			br {
				display: none
			}

			body {
				background-color: #fff !important;
			}
		</style>
	</template:replace>
	<template:replace name="body">
		<div class="model-step  model-step-success">
			<div class="model-step-wrap">
				<div class="model-step-tab">
					<c:if test="${changeFlow }">
						<div class="model-step-tab-item finished">
							<div class="model-step-tab-icon "><i></i>1</div>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.process')}</div>
						</div>
					</c:if>
					<div class="model-step-tab-item finished">
						<c:if test="${changeFlow }">
							<div class="model-step-tab-icon "><i></i>2</div>
						</c:if>
						<c:if test="${empty changeFlow or changeFlow ne true}">
							<div class="model-step-tab-icon "><i></i>1</div>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.import.template')}</div>
					</div>
					<div class="model-step-tab-item finished">
						<c:if test="${changeFlow }">
							<div class="model-step-tab-icon"><i></i>3</div>
						</c:if>
						<c:if test="${empty changeFlow or changeFlow ne true}">
							<div class="model-step-tab-icon"><i></i>2</div>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.upload.template')}</div>
					</div>
					<c:if test="${enableFlow}">
						<div class="model-step-tab-item finished">
							<c:if test="${changeFlow }">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
							<c:if test="${empty changeFlow or changeFlow ne true}">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.import.setting')}</div>
						</div>
					</c:if>
					<div class="model-step-tab-item active">
						<c:if test="${changeFlow }">
							<c:if test="${enableFlow }">
								<div class="model-step-tab-icon"><i></i>5</div>
							</c:if>
							<c:if test="${empty enableFlow or enableFlow ne true}">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
						</c:if>
						<c:if test="${empty changeFlow or changeFlow ne true}">
							<c:if test="${enableFlow }">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
							<c:if test="${empty enableFlow or enableFlow ne true}">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.finish')}</div>
					</div>
				</div>
				<div class="model-step-content" style="border: none">
					<div class="model-step-content-wrap">
						<div class="model-finish suc">
							<div class="model-finish-wrap">
								<div class="model-finish-img"></div>
								<p>${lfn:message('sys-modeling-main:sysModelingQueueLog.fdFlag.1')}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="toolbar-bottom">
				<ui:button  text="${lfn:message('sys-modeling-main:modeling.ok')}" order="1"
							onclick="importsuccess();"/>
				<ui:button styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-main:modeling.continue.import')}" order="1" onclick="goback();"/>
			</div>
		</div>

		<div class="model-step model-step-fail">
			<div class="model-step-wrap">
				<div class="model-step-tab">
					<c:if test="${changeFlow }">
						<div class="model-step-tab-item finished">
							<div class="model-step-tab-icon "><i></i>1</div>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.process')}</div>
						</div>
					</c:if>
					<div class="model-step-tab-item finished">
						<c:if test="${changeFlow }">
							<div class="model-step-tab-icon "><i></i>2</div>
						</c:if>
						<c:if test="${empty changeFlow or changeFlow ne true}">
							<div class="model-step-tab-icon "><i></i>1</div>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.import.template')}</div>
					</div>
					<div class="model-step-tab-item finished">
						<c:if test="${changeFlow }">
							<div class="model-step-tab-icon"><i></i>3</div>
						</c:if>
						<c:if test="${empty changeFlow or changeFlow ne true}">
							<div class="model-step-tab-icon"><i></i>2</div>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.upload.template')}</div>
					</div>
					<c:if test="${enableFlow}">
						<div class="model-step-tab-item finished">
							<c:if test="${changeFlow }">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
							<c:if test="${empty changeFlow or changeFlow ne true}">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.import.setting')}</div>
						</div>
					</c:if>
					<div class="model-step-tab-item active">
						<c:if test="${changeFlow }">
							<c:if test="${enableFlow }">
								<div class="model-step-tab-icon"><i></i>5</div>
							</c:if>
							<c:if test="${empty enableFlow or enableFlow ne true}">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
						</c:if>
						<c:if test="${empty changeFlow or changeFlow ne true}">
							<c:if test="${enableFlow }">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
							<c:if test="${empty enableFlow or enableFlow ne true}">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
						</c:if>
						<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.finish')}</div>
					</div>
				</div>
				<div class="model-step-content" style="border: none">
					<div class="model-step-content-wrap">
						<div class="model-finish fail">
							<div fail-show="all" class="model-finish-img"></div>
							<p fail-show="all">${lfn:message('sys-modeling-main:sysModelingQueueError.fdFlag.1')}</p>
							<div fail-show="all" class="result-count-title">${lfn:message('sys-modeling-main:modeling.failure.details')}</div>
							<div fail-show="part" class="result-count">
								<span class="result-count-title">${lfn:message('sys-modeling-main:modeling.import.data.results')} : </span>
								<div class="result-count-number success-count">
									<span></span>
									<p>0${lfn:message('sys-modeling-main:modeling.strip')}</p>
								</div>
								<div class="result-count-number fail-count">
									<span></span>
									<p>0${lfn:message('sys-modeling-main:modeling.strip')}</p>
								</div>
								<div class = "templateError" style="color:red;">${lfn:message('sys-modeling-main:modeling.upload.failed.upload.template.no.match')}</div>
							</div>
							<div class="model-finish-desc">

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="toolbar-bottom">
				<ui:button styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-main:modeling.cancel')}" order="1"
						   onclick="onclose();"/>
				<ui:button text="${lfn:message('sys-modeling-main:modeling.reimport')}" order="1" onclick="goback();"/>

			</div>
		</div>

		<script type="text/javascript">
			Com_IncludeFile("jquery.js");
			// 请选择要导入的文件
			var alert_upload_file_empty = "<bean:message bundle='sys-transport' key='sysTransport.error.upload.fileEmpty'/>";
			// 所选文件不能为空
			var alert_msg_file_required = "<bean:message bundle='sys-transport' key='sysTransport.import.file.required'/>";
			// 正在执行
			var upload_doing_tip_desc = "<bean:message bundle='sys-transport' key='sysTransport.title.uploadDoing'/>";
			// 其他错误
			var import_info_other_errors = "<bean:message bundle='sys-transport' key='sysTransport.import.otherErrors'/>";
			// 未执行
			var upload_not_do_desc = "<bean:message bundle='sys-transport' key='sysTransport.title.uploadNotDo'/>";

			window.onclose = function () {
				$dialog.hide(null);
			}
			window.importsuccess = function(){
				$dialog.config.opener.LUI.fire({ type: "topic", name: "importPage" });
				$dialog.hide(null);
			}
			//excel文件上传完毕,显示操作信息
			function buildErrorMsg(result, msg) {
				var $errDesc = $(".model-finish-desc");
				$errDesc.html("");
				//概述
				$errDesc.append(createTable(result, msg));
			}

			function createTable(result, msg) {
				var json;
				try {
					json = JSON.parse(result);
				} catch (err) {
					return result;
				}
				if (!json) return "";
				//其他错误
				var otherStr = "<div>";
				var otherErrors = json.otherErrors;
				if (otherErrors && otherErrors.length > 0) {
					otherStr += "<p><b>" + import_info_other_errors + ":</b></p>";
					$.each(otherErrors, function (index, item) {
						otherStr += "<p style='color:#FF0000;padding-left:50px ;'>" + item + "</p>";
					});
					otherStr += "</div>";
				}

				var htmlStr = "<table class='tb_normal' style='width:100%;'><thead style='background:#F8F8F8;'><tr style='height: 40px;line-height: 40px;'>";
				//标题
				var titles = json.titles;
				var colLength = titles.length;

				$.each(titles, function (index, item) {
					var thWidth=item.length*16;
					if(item==="错误详情"){
						thWidth=200;
					}
					htmlStr += "<th style='min-width:"+thWidth+"px;padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;'>" + item + "</th>";
				});

				htmlStr += "</tr></thead><tbody>";
				//内容
				var rows = json.rows;
				$.each(rows, function (index, item) {
					var errColNumbers = $.map(item.errColNumbers.split(","), function (item, index) {
						return parseInt(item);
					});
					htmlStr += "<tr>";
					htmlStr += "<td>" + item.errRowNumber + "</td>";
					var contents = item.contents;
					$.each(contents, function (idx, it) {
						if ($.inArray(idx, errColNumbers) > -1)
							htmlStr += "<td style='color:#FF0000;'>" + formatNull(it) + "</td>";
						else
							htmlStr += "<td>" + formatNull(it) + "</td>";
					});
					htmlStr += "<td style='color:#FF0000'>" + item.errInfos + "</td>";
					htmlStr += "</tr>";
				});
				htmlStr += "</tbody></table>";
				if (rows.length<=0) {
					htmlStr ="";
				}

				return otherStr + htmlStr;
			}

			function formatNull(val) {
				if (val === null || val === undefined || val === "null" || val === "NULL") {
					return "";
				}
				return val;
			}
		</script>
		<script type="text/javascript">

			var __errorResult = '${errorResult}';
			var successResult = "${successResult}";
			var successCount = '${successCount}';
			var errorCount = "${errorCount}";

			function goback() {
				var url = Com_Parameter.ContextPath + "sys/modeling/main/transport/import_config_dialog.jsp?" +
						"fdAppModelId=${param.fdAppModelId}&fdId=${param.fdId}"+"&enableFlow=${enableFlow}"+"&changeFlow=${changeFlow}";
				window.location.href = url;
			}

			function init() {
				console.log("结束",new Date());
				$(".model-step-fail").hide();
				$(".model-step-success").hide();
				$(".templateError").hide();
				if (__errorResult && __errorResult != "null") {
					if(errorCount == "" && successCount ==""){
						buildtemplateError();
					}
					else if (!(errorCount && successCount)  || successCount != "0") {
						buildPartOfFailed()
					} else {
						buildAllFailed();
					}
				} else {
					buildAllSuccess();
				}
			}

			init();

			function buildAllSuccess() {
				$(".model-step-fail").hide();
				$(".model-step-success").show();
			}

			/**上传文件与模板格式不符**/
			function buildtemplateError(){
				$(".success-count").hide();
				$(".fail-count").hide();
				$(".templateError").show();
				$("[fail-show=\"all\"]").hide();
				$("[fail-show=\"part\"]").show();
				buildFailed();
				$(".model-finish-desc").find("p").css("color","#999999");
			}

			function buildAllFailed() {
				$("[fail-show=\"all\"]").show();
				$("[fail-show=\"part\"]").hide();

				//  $("div.result-count-title").append(successResult);
				buildFailed()
			}

			function buildPartOfFailed() {

				$("[fail-show=\"all\"]").hide();
				$("[fail-show=\"part\"]").show();
				$(".success-count").find("p").html(successCount + "${lfn:message('sys-modeling-main:modeling.strip')}");
				$(".fail-count").find("p").html(errorCount + "${lfn:message('sys-modeling-main:modeling.strip')}");
				buildFailed()
			}

			function buildFailed() {
				$(".model-step-fail").show();
				buildErrorMsg(__errorResult, successResult)
			}
		</script>

	</template:replace>
</template:include>
