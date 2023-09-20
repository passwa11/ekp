<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>"/>
<title>${lfn:message('fssc-voucher:fsscVoucherMain.fd_detail')}</title>
<style type="text/css">
    .tb_normal TD {
        border: 1px #000 solid;
    }

    .tb_normal {
        border: 1px #000 solid;
    }

    .tb_noborder {
        border: 0px;
    }

    .tb_noborder TD {
        border: 0px;
    }

    table td {
        color: #000;
    }

    .tb_normal > tbody > tr > td {
        border: 1px #000 solid !important;
    }
</style>
<style media="print" type="text/css">
    #S_OperationBar {
        display: none !important;
    }
</style>
<center>
    <div class='lui_form_title_frame'>
        <div class='lui_form_subject'>
            <p class="txttitle"><c:out value="${form.docSubject}"/></p>
        </div>
    </div>
    <!--预制凭证-->
    <kmss:ifModuleExist path="/fssc/voucher/">
        <table width='95%' class="tb_normal">
            <tr>
                <td colspan="12" width="100%">
                    <table class="tb_normal" width="100%" align="center">
                        <!-- 预制凭证-->
                        <c:import url="/fssc/voucher/fssc_voucher_main/fsscVoucherMain_modelView_file_print.jsp"
                                  charEncoding="UTF-8">
                            <c:param name="fdModelId" value="${form.fdId}"/>
                            <c:param name="fdModelName" value="${fdModelName}"/>
                        </c:import>
                    </table>
                </td>
            </tr>
        </table>
    </kmss:ifModuleExist>
</center>
<script type="text/javascript">
    Com_IncludeFile("jquery.js");
    Com_IncludeFile("document.js", 'style/default/doc/');
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
