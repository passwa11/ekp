<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('sys-modeling-main:modeling.import.configuration') }</template:replace>
	<template:replace name="head">
		<template:super/>
		<style>
			br {
				display: none
			}

			body {
				background-color: #fff !important;
			}
		</style>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/transport/css/maincss.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/transport/css/common.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/transport/css/transport.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript" src="${LUI_ContextPath}/sys/modeling/base/transport/js/importUpload.js"></script>
		<form name="templet"
			  action="<%=request.getContextPath() %>/sys/modeling/main/modelingImportConfig.do?fdAppModelId=${modelingImportConfigForm.fdModelId}"
			  method="post">
			<input type="hidden" name="method" value="importDoc">
			<input type="hidden" name="type" value="downloadTemplet">
			<input type="hidden" name="fdId" value="${param.fdId}">
		</form>
		<!-- 第二步 -->
		<div class="import-template">
			<div class="model-step">
				<div class="model-step-wrap">
					<div class="model-step-tab">
						<c:if test="${param.changeFlow }">
							<div class="model-step-tab-item  finished">
								<div class="model-step-tab-icon"><i></i>1</div>
								<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.process')}</div>
							</div>
						</c:if>
						<div class="model-step-tab-item finished">
							<c:if test="${param.changeFlow }">
								<div class="model-step-tab-icon"><i></i>2</div>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<div class="model-step-tab-icon"><i></i>1</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.import.template')}</div>
						</div>
						<div class="model-step-tab-item active">
							<c:if test="${param.changeFlow }">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<div class="model-step-tab-icon"><i></i>2</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.upload.template')}</div>
						</div>
						<c:if test="${param.enableFlow }">
							<div class="model-step-tab-item">
								<c:if test="${param.changeFlow }">
									<div class="model-step-tab-icon"><i></i>4</div>
								</c:if>
								<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
									<div class="model-step-tab-icon"><i></i>3</div>
								</c:if>
								<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.import.setting')}</div>
							</div>
						</c:if>
						<div class="model-step-tab-item">
							<c:if test="${param.changeFlow }">
								<c:if test="${param.enableFlow }">
									<div class="model-step-tab-icon"><i></i>5</div>
								</c:if>
								<c:if test="${empty param.enableFlow or param.enableFlow ne true}">
									<div class="model-step-tab-icon"><i></i>4</div>
								</c:if>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<c:if test="${param.enableFlow }">
									<div class="model-step-tab-icon"><i></i>4</div>
								</c:if>
								<c:if test="${empty param.enableFlow or param.enableFlow ne true}">
									<div class="model-step-tab-icon"><i></i>3</div>
								</c:if>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.finish')}</div>
						</div>
					</div>

					<div class="model-step-content">
						<html:form
								action="/sys/modeling/main/modelingImportConfig.do?method=importDoc&type=importData&fdAppModelId=${modelingImportConfigForm.fdModelId}&fdId=${param.fdId}&enableFlow=${param.enableFlow }"
								enctype="multipart/form-data">
							<%--                <iframe name="file_frame" style="display:none;"></iframe>--%>
						<div class="model-step-content-wrap">
							<div class="model-download">
								<span>${lfn:message('sys-modeling-main:modeling.tips')}：</span>
								<span>${lfn:message('sys-modeling-main:modeling.import.fill.accurately')}</span>
								<ul class="model-download-opt">
									<li class="model-download-opt-item">
										<div class="model-download-opt-num">1</div>
										<div class="model-download-opt-detail">
											<p>${lfn:message('sys-modeling-main:modeling.download.template.first.fill')}</p>
											<a href="javascript:submitExportForm();">${lfn:message('sys-modeling-main:modeling.download.template')}</a>
										</div>
									</li>
									<li class="model-download-opt-item">
										<div class="model-download-opt-num">2</div>
										<div class="model-download-opt-detail">
											<p  >${lfn:message('sys-modeling-main:modeling.upload.filled-in.template')}</p>
											<div id="uploadBtn" style="display: none;">
												<input id="upload_file_temp" type="text" placeholder="请将填写好的Excel上传"
													   readonly="readonly"/>
												<input id="upload_file" type="file" name="file" accept=".xls,.xlsx" value="浏览"
													   onchange="change();">
											</div>
											<p id="upload_file_msg" style="display: none"><span id="upload_file_name"></span></p>

											<a id="upload_file_title" href="javascript:clickFile();">${lfn:message('sys-modeling-main:modeling.upload.template')}</a>

										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="toolbar-bottom">
					<c:if test="${param.enableFlow }">
						<ui:button  text="${lfn:message('sys-modeling-main:modeling.next.step')}" order="1" onclick="next();"/>
					</c:if>
					<c:if test="${empty param.enableFlow or param.enableFlow ne true}">
						<ui:button  text="${lfn:message('sys-modeling-main:modeling.ok')}" order="1" onclick="upload();"/>
					</c:if>
					<ui:button  styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-main:modeling.previous')}" order="1" onclick="goback();"/>
				</div>
				<script>
					function clickFile() {
						$("#upload_file").trigger($.Event("click"))
					}

					function goback() {
						var url = Com_Parameter.ContextPath + "sys/modeling/main/transport/import_config_dialog.jsp?" +
								"fdAppModelId=${param.fdAppModelId}&fdId=${param.fdId}" +
								"&enableFlow=${param.enableFlow}&fdAppFlowId=${param.fdAppFlowId}" +
								"&changeFlow=${param.changeFlow}"+"&goback=true";
						window.location.href = url;
					}
					function next(){
						var file = document.getElementsByName("file");
						if(file[0].value==null || file[0].value.length==0){
							seajs.use(['lui/dialog'],function(dialog){
								dialog.alert('${lfn:message('sys-modeling-main:modeling.select.file')}');
							});
							return false;
						}else{
							$(".dataStatus").css("display","block");
							$("input[name='dataStatus'][value='10']").trigger("click");
							$(".import-template").css("display","none");
						}
					}
				</script>
				</html:form>
				<%@ include file="/resource/jsp/view_down.jsp" %>
			</div>
		</div>
		<!-- 有流程文档导入阶段：第三步 -->
		<div class="dataStatus"  style="display:none;">
			<div class="model-step">
				<div class="model-step-wrap">
					<div class="model-step-tab">
						<c:if test="${param.changeFlow }">
							<div class="model-step-tab-item finished">
								<div class="model-step-tab-icon"><i></i>1</div>
								<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.process')}</div>
							</div>
						</c:if>
						<div class="model-step-tab-item finished">
							<c:if test="${param.changeFlow }">
								<div class="model-step-tab-icon"><i></i>2</div>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<div class="model-step-tab-icon"><i></i>1</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.select.import.template')}</div>
						</div>
						<div class="model-step-tab-item finished">
							<c:if test="${param.changeFlow }">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<div class="model-step-tab-icon"><i></i>2</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.upload.template')}</div>
						</div>
						<div class="model-step-tab-item active">
							<c:if test="${param.changeFlow }">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<div class="model-step-tab-icon"><i></i>3</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.import.setting')}</div>
						</div>
						<div class="model-step-tab-item">
							<c:if test="${param.changeFlow }">
								<div class="model-step-tab-icon"><i></i>5</div>
							</c:if>
							<c:if test="${empty param.changeFlow or param.changeFlow ne true }">
								<div class="model-step-tab-icon"><i></i>4</div>
							</c:if>
							<div class="model-step-tab-txt">${lfn:message('sys-modeling-main:modeling.finish')}</div>
						</div>
					</div>
					<div class="model-step-content">
						<div class="model-step-content-wrap">
							<div class="dataStatus-message">${lfn:message('sys-modeling-main:modeling.select.status.imported.data')}</div>
							<div class="model-record-wrap-dataStatus">
								<div class="dataStatus-div">
									<div><i></i><input type="radio" name="dataStatus" value="10" checked>
										<label class="dataStatus-type">${lfn:message('sys-modeling-main:modeling.drafting.node')}</label>
									</div>
									<div class="dataStatus-info">${lfn:message('sys-modeling-main:modeling.process.initiated.clicking.submit')}</div>
								</div>
								<div class="dataStatus-div">
									<div><i></i><input type="radio" name="dataStatus" value="20">
										<label class="dataStatus-type">${lfn:message('sys-modeling-main:modeling.approval.node')}</label>
									</div>
									<div class="dataStatus-info">${lfn:message('sys-modeling-main:modeling.process.participants.operate.process')}</div>
								</div>
								<!-- 如果流程图有审批节点无法直接结束，屏蔽 -->
								<!-- <div class="dataStatus-div">
                                    <div><i></i><input type="radio" name="dataStatus" value="30">
                                        <label class="dataStatus-type">流程结束</label>
                                    </div>
                                    <div class="dataStatus-info">导入数据直接生效，无需经过流程审批</div>
                                </div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="toolbar-bottom">
				<ui:button  text="${lfn:message('sys-modeling-main:modeling.ok')}" order="1" onclick="upload();"/>
				<ui:button  styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-main:modeling.previous')}" order="1" onclick="goback2();"/>
			</div>
		</div>
		<script>
			var langs = {
				"return.noRecord":"${lfn:message('return.noRecord')}",
				selectFile:"${lfn:message('sys-modeling-main:modeling.select.file')}",
				uploading:"${lfn:message('sys-modeling-main:modeling.uploading')}",
				reUpload:"${lfn:message('sys-modeling-main:modeling.re-upload')}"
			};
			function goback2() {
				$(".dataStatus").css("display","none");
				$(".import-template").css("display","block");
			}
			Com_AddEventListener(window,"load",function(){
				$("input[name='dataStatus']").on("click",function(){
					var dataStatus = $(this).val();
					var url = Com_Parameter.ContextPath +
							'sys/modeling/main/modelingImportConfig.do?method=importDoc&type=importData&fdAppModelId=${modelingImportConfigForm.fdModelId}&fdId=${param.fdId}'+'&dataStatus='+dataStatus+'&enableFlow=${param.enableFlow }'+'&fdAppFlowId=${param.fdAppFlowId}'+'&changeFlow=${param.changeFlow}';
					$("form[name='modelingImportConfigForm']").attr('action',url);
				})
				$(document).on("click",".dataStatus-type",function(){
					$(this).closest("div").find("input[name='dataStatus']").trigger("click");
				})
			})
		</script>
	</template:replace>
</template:include>
