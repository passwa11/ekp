<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 性能方面考虑，默认未点赞，js+ajax再一次性处理已点赞记录 -->
	<input type="hidden" name="isPraised" value="false" id="check_${HtmlParam.fdModelId}">

	<span id="aid_${HtmlParam.fdModelId}" praise-data-modelid="${HtmlParam.fdModelId}" 
			praise-data-modelname="${HtmlParam.fdModelName}" style="position:relative;">
	<span onclick="sysPraise('${JsParam.fdModelId}');" class="tmpl_cur" data-lui-id='${HtmlParam.fdModelId}'>
		<span id="praise_icon" class="sys_praise" title="${lfn:message('sys-praise:sysPraiseMain.praise') }"></span>
		<span id="praise_count" class="praise_count">
			${(not empty param.docPraiseCount) ? (param.docPraiseCount) : '0'}
		</span>
	</span>
	<div class="lui_praise_layer" style="display:none;">
		<html:hidden property="showPraiserCount" value="${HtmlParam.showPraiserCount}"/>
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

<input type="hidden" name="isNegative" value="false" id="checkNegative_${HtmlParam.fdModelId}">
<span id="aidNegative_${HtmlParam.fdModelId}" 
		negative-data-modelid="${HtmlParam.fdModelId}" negative-data-modelname="${HtmlParam.fdModelName}" style="position:relative;">
	<span onclick="sysPraiseNegative('${JsParam.fdModelId}');" class="cur" data-lui-id='${HtmlParam.fdModelId}' >
		<span id="negative_icon" class="sys_unNegative" title="${lfn:message('sys-praise:sysPraiseMain.negative') }"></span>
		<span id="negative_count" class="negative_count">
			${(not empty param.docNegativeCount) ? (param.docNegativeCount) : 0}
		</span>
	</span>		
<div class="lui_negative_layer" style="display: none;">
<html:hidden property="showNegativeCount" value="${HtmlParam.showNegativeCount}"/>
<div class="bg">
			<table class="lui_negative_table">
				<tr>
					<td>
					<div class="content layer_person">
							<div class="lui_negative_close_d" id="negative_close">
								<span class="lui_negative_close_s"></span>
							</div>
							<ul class="person_list clearfix" id="negativePerson_list">
							</ul>
							<div id="negative_page_list" class="negative_page_list">
								<a class="negative_page_set">
									<span>
										<em onclick="prePage('${JsParam.fdModelId}');" data-negative-mark="1" class="praise_icon_l" id="btn_negative_preno"></em>
									</span>
									<span>
										<em onclick="nextPage('${JsParam.fdModelId}');" data-negative-mark="1" class="praise_icon_r" id="btn_negative_nextno"></em>
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