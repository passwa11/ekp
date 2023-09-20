<%-- 收文日期 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%"); %>
<div id="_fdChangeEndDate" xform_type="datetime">
<xform:datetime property="fdChangeEndDate" mobile="${param.mobile eq 'true'? 'true':'false'}"
				htmlElementProperties="id=fdChangeEndDate"
                showStatus="edit"
				validators="afterBegindate checkLongterm"
                dateTimeType="date" 
                subject="${lfn:message('hr-ratify:hrRatifyChange.fdChangeEndDate')}"
                style="<%=parse.getStyle()%>" />
<xform:checkbox property="fdIsLongtermContract" htmlElementProperties="id=fdIsLongtermContract" onValueChange="cancelEndDate" mobile="${param.mobile eq 'true'? 'true':'false'}" showStatus="edit">
	<xform:simpleDataSource value="true"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdIsLongtermContract.1"/></xform:simpleDataSource>
</xform:checkbox>
</div>
<c:choose>
	<c:when test="${param.mobile eq 'true'}">
		<script>
			require(['dojo/ready','dijit/registry'],function(ready,registry){
				//校验对象
				var validorObj=null;
				ready(function(){
					validorObj=registry.byId('scrollView');
					// 验证开始时间和结束时间
					validorObj._validation.addValidator('afterBegindate', "${ lfn:message('hr-ratify:hrRatifySign.fdSignEndDate.validate.afterBegindate') }", function(v,e,o){
						var begindate = $('input[name="fdChangeBeginDate"]').val();
						if(begindate && v) {
							return begindate <= v;
						} else {
							return true;
						}
					});
					// 勾选长期有效校验
					validorObj._validation.addValidator('checkLongterm', "${ lfn:message("hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.error") }", function(v, e, o) {
						var result = true;
						var longtermContract = $('[name="fdIsLongtermContract"]').val();
						if(v){
							if(longtermContract == 'true'){
								result = false;
							}
						}
						return result;
					});
					// 长期有效勾选清空到期时间
					window.cancelEndDate = function(value) {
						if (value == 'true') {
							registry.byId('fdChangeEndDate')._setValueAttr("");
						}
					}
				});
			});
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			// 验证开始时间和结束时间
			validation.addValidator('afterBegindate', "${ lfn:message('hr-ratify:hrRatifySign.fdSignEndDate.validate.afterBegindate') }", function(v,e,o){
				var begindate = $('input[name="fdChangeBeginDate"]').val();
				if(begindate && v) {
					return begindate <= v;
				} else {
					return true;
				}
			});

			// 勾选长期有效校验
			validation.addValidator('checkLongterm', "${ lfn:message("hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.error") }", function(v, e, o) {
				var result = true;
				var longtermContract = $('[name="fdIsLongtermContract"]').val();
				if(v){
					if(longtermContract == 'true'){
						result = false;
					}
				}
				return result;
			});

			// 长期有效勾选清空到期时间
			window.cancelEndDate = function() {
				var longtermContract = $('[name="fdIsLongtermContract"]').val();
				if(longtermContract == 'true'){
					$('[name="fdChangeEndDate"]').val('');
				}
			}
		</script>
	</c:otherwise>
</c:choose>