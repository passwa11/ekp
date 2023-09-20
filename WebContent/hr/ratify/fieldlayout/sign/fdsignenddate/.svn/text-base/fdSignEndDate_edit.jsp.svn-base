<%-- 合同截至日期 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%
    parse.addStyle("width", "control_width", "45%");
    boolean requiredInfo = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
%>
<div id="_fdSignEndDate" xform_type="datetime">
<xform:datetime property="fdSignEndDate" mobile="${param.mobile eq 'true'? 'true':'false'}"
                htmlElementProperties="id=fdSignEndDate"
                showStatus="edit"
                dateTimeType="date"
                validators="afterBegindate checkLongterm"
                isLoadDataDict="false"
                subject="${lfn:message('hr-ratify:hrRatifySign.fdSignEndDate')}"
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
                    validorObj._validation.addValidator('afterBegindate', "${ lfn:message('hr-ratify:hrRatifySign.fdSignEndDate.validate.afterBegindate') }", function(v,e,o){
                        var begindate = $('input[name="fdSignBeginDate"]').val();
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
                            registry.byId('fdSignEndDate')._setValueAttr("");
                        }
                    }
                });
            });
        </script>
    </c:when>
    <c:otherwise>
        <script type="text/javascript">
            var validation = $KMSSValidation();
            validation.addValidator('afterBegindate', "${ lfn:message('hr-ratify:hrRatifySign.fdSignEndDate.validate.afterBegindate') }", function(v,e,o){
                var begindate = $('input[name="fdSignBeginDate"]').val();
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
                    $('[name="fdSignEndDate"]').val('');
                }
            }
        </script>
    </c:otherwise>
</c:choose>

