<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 性能方面考虑，默认未点赞，js+ajax再一次性处理已点赞记录 -->
	<input type="hidden" name="isPraised" value="false" id="check_${JsParam.fdModelId}">

	<span id="aid_${JsParam.fdModelId}" praise-data-modelid="${JsParam.fdModelId}" 
			praise-data-modelname="${JsParam.fdModelName}" style="position:relative;">
	<span onclick="sysPraise('${JsParam.fdModelId}');" class="tmpl_cur" data-lui-id='${JsParam.fdModelId}'
				title="${lfn:message('sys-praise:sysPraiseMain.praise') }">
		<span id="praise_icon" class="sys_praise" ></span>
		<span id="praise_count" class="praise_count">
			${(not empty JsParam.docPraiseCount) ? (JsParam.docPraiseCount) : '0'}
		</span>
	</span>
	<div class="lui_praise_layer" style="display:none;">
		<html:hidden property="showPraiserCount" value="${JsParam.showPraiserCount}"/>
		<div class="bg">
			<table class="lui_praise_table">
				<tr>
					<td>
						<div class="content layer_person">
							<div class="lui_praise_close_d" id="praise_close">
								<span class="lui_praise_close_s"></span>
							</div>
							<ul class="person_list clearfix" id="praisedPerson_list">
							</ul>
							<div id="praise_page_list" class="praise_page_list">
								<a class="praise_page_set">
									<span>
										<em onclick="prePage('${JsParam.fdModelId}');" data-praise-mark="1" class="praise_icon_l" id="btn_preno"></em>
									</span>
									<span>
										<em onclick="nextPage('${JsParam.fdModelId}');" data-praise-mark="1" class="praise_icon_r" id="btn_nextno"></em>
									</span>
								</a>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<div class="app_arrow app_arrow_p"></div>
		</div>
	</div>
</span>
