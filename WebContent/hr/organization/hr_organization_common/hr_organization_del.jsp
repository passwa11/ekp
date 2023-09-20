<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="hr_contract_batch_sign_warp">
          <input type="hidden" id="ids" value="${param.fdIds }" />
          <div class="lui_hr_criteria_wrap">
              <em class="lui_text_primary">${ lfn:message('hr-staff:hrStaffEntry.already.choose') }<c:if test="${empty param.showNum }">（<i>${fn:length(list) }</i>${ lfn:message('hr-staff:hrStaffEntry.person.index') }）</c:if>：</em>
              <ul class="lui_hr_criteria_wrap_list">
           		<c:forEach items="${list }" var="level">
           			<li class="lui_hr_status_selected ">
           				<a href="javascript:remove('${level.fdId }');" class="lui_text_primary">
           					<span class="lui_hr_name">${level.fdName }</span>
           					<i class="lui_hr_cancel"></i>
           				</a>
           			</li>
           		</c:forEach>
              </ul>
          </div>

          <div class="lui_hr_validation_msg msg_danger" style="margin: 10px; 10px; 10px; 10px;">
	      	<i class="lui_hr_validation_icon lui_hr_icon_danger_warning"></i> 
	      	${ lfn:message('hr-organization:hr.organization.info.tip.27') } 
	      </div>

          <!--弹框底部按钮 产品标准组件 Starts-->
<%--           <div class="lui_hr_footer_btnGroup">
          	<ui:button text="${lfn:message('button.ok') }" onclick="clickOk();"></ui:button>
             <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
          </div> --%>
          <!--弹框底部按钮 产品标准组件 Ends-->
</div>
<script>
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		$(document).ready(function(){
			$("li").click(function(){
				$(this).toggleClass("hover");
			});
		});
		window.remove = function(id){
			var ids = $("#ids").val().split(";");
			var i = $("em i");
			if(ids.length>1){
				var index = ids.indexOf(id);
				if(index > -1){
					ids.splice(index,1);
					$("li.hover").remove();
					i.text(parseInt(i.text())-1);
				}
				$("#ids").val(ids.join(";"));
			} else {
				dialog.alert("${ lfn:message('hr-organization:hr.organization.info.tip.28') }");
			}
		};
	});
</script>