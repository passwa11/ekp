<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<style type="text/css">
    
    	.lui_paragraph_title{
    		font-size: 15px;
    		color: #15a4fa;
        	padding: 15px 0px 5px 0px;
    	}
    	.lui_paragraph_title span{
    		display: inline-block;
    		margin: -2px 5px 0px 0px;
    	}
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    
</style>
<script type="text/javascript">
        window.document.title = "${ lfn:message('fssc-alitrip:table.fsscAlitripMapping') }";
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_mapping/fsscAlitripMapping.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscAlitripMapping.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_mapping/fsscAlitripMapping.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscAlitripMapping.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-alitrip:table.fsscAlitripMapping') }</p>
<center>

         <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-alitrip:py.JiBenXinXi') }">
                <td>
	           		 <table class="tb_normal" width="100%">
	            <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.fdModelId')}
	                    </td>
	                    <td width="85%">
	                        <%-- 所属模块--%>
	                        <div id="_xform_fdModelIdId" _xform_type="dialog">
	                            <xform:dialog propertyId="fdModelIdId" propertyName="fdModelIdName"  required="true" subject="${lfn:message('fssc-alitrip:fsscAlitripMapping.fdModelId')}" style="width:95%;">
	                                dialogSelect(false,'fssc_alitrip_model_fdmodel','fdModelIdId','fdModelIdName',null,FS_changgeModel);
	                            </xform:dialog>
	                        </div>
	                        <html:hidden property="fdModelName" />
	                        <html:hidden property="fdModelCate" />
	 						<html:hidden property="fdKey" />
	                    </td>
	                </tr>
	                 <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.fdCateName')}
	                    </td>
	                    <td width="85%">
	                        <%-- 所属分类/模板--%>
	                        <div id="_xform_fdCateId" _xform_type="dialog">
	                            <input type="hidden" name="fdCateName"  value="${fsscAlitripMappingForm.fdCateName}"/>
	                            <xform:dialog propertyId="fdCateId" propertyName="fdCateName"  style="width:95%;">
	                                dialogSelect(false,'fssc_alitrip_select_fdmodel','fdCateId','fdCateName',{'fdModelName':$('[name=fdModelCate]').val()});
	                            </xform:dialog>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.fdCompanyIdText')}Id
	                    </td>
	                    <td width="85%">
	                        <%-- 费用归属公司--%>
	                        <div id="_xform_fdCompanyIdText" _xform_type="text">
	                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyIdText" idValue="${fsscAlitripMappingForm.fdCompanyId}" nameValue="${fsscAlitripMappingForm.fdCompanyIdText}" required="true"  style="width:95%;">
	                                selectFormula('fdCompanyId','fdCompanyIdText');
	                            </xform:dialog>
	                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.fdCompanyId.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.corpName')}
	                    </td>
	                    <td width="85%">
	                        <%-- 企业名称--%>
	                         <div id="_xform_corpName" _xform_type="text">
	                            <xform:dialog propertyId="corpName" propertyName="corpNameText" idValue="${fsscAlitripMappingForm.corpName}" nameValue="${fsscAlitripMappingForm.corpNameText}"   style="width:95%;">
	                                selectFormula('corpName','corpNameText');
	                            </xform:dialog>
	                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.corpName.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.tripTitle')}
	                    </td>
	                    <td width="85%">
	                        <%-- 申请单标题--%>
	                        <div id="_xform_tripTitle" _xform_type="text">
	                            <xform:dialog propertyId="tripTitle" propertyName="tripTitleText" idValue="${fsscAlitripMappingForm.tripTitle}" nameValue="${fsscAlitripMappingForm.tripTitleText}" required="true"  style="width:95%;">
	                                selectFormula('tripTitle','tripTitleText');
	                            </xform:dialog>
	                             <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.tripTitle.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.applyId')}
	                    </td>
	                    <td width="85%">
	                        <%-- 申请单id--%>
	                        <div id="_xform_applyId" _xform_type="text">
	                            <xform:dialog propertyId="applyId" propertyName="applyIdText" idValue="${fsscAlitripMappingForm.applyId}" nameValue="${fsscAlitripMappingForm.applyIdText}" required="true"  style="width:95%;">
	                                selectFormula('applyId','applyIdText');
	                            </xform:dialog>
	                             <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.applyId.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.tripDay')}
	                    </td>
	                    <td width="85%">
	                        <%-- 出差天数--%>
	                        <div id="_xform_tripDay" _xform_type="text">
	                            <xform:dialog propertyId="tripDay" propertyName="tripDayText" idValue="${fsscAlitripMappingForm.tripDay}" nameValue="${fsscAlitripMappingForm.tripDayText}" required="true"  style="width:95%;">
	                                selectFormula('tripDay','tripDayText');
	                            </xform:dialog>
	                             <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.tripDay.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                
	                
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.userId')}
	                    </td>
	                    <td width="85%">
	                        <%-- 用户id--%>
	                         <div id="_xform_userId" _xform_type="text">
	                            <xform:dialog propertyId="userIsd" propertyName="userIsdText" idValue="${fsscAlitripMappingForm.userIsd}" nameValue="${fsscAlitripMappingForm.userIsdText}" required="true"  style="width:95%;">
	                                selectFormula('userIsd','userIsdText');
	                            </xform:dialog>
	                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.userIsd.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.userName')}
	                    </td>
	                    <td width="85%">
	                        <%-- 用户名称--%>
	                         <div id="_xform_userName" _xform_type="text">
	                            <xform:dialog propertyId="userName" propertyName="userNameText" idValue="${fsscAlitripMappingForm.userName}" nameValue="${fsscAlitripMappingForm.userNameText}"   style="width:95%;">
	                                selectFormula('userName','userNameText');
	                            </xform:dialog>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.departId')}
	                    </td>
	                    <td width="85%">
	                        <%-- 部门id--%>
	                         <div id="_xform_departId" _xform_type="text">
	                            <xform:dialog propertyId="departIsd" propertyName="departIsdText" idValue="${fsscAlitripMappingForm.departIsd}" nameValue="${fsscAlitripMappingForm.departIsdText}" required="true"  style="width:95%;">
	                                selectFormula('departIsd','departIsdText');
	                            </xform:dialog>
	                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.departId.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.departName')}
	                    </td>
	                    <td width="85%">
	                        <%-- 部门名称--%>
	                        <div id="_xform_departName" _xform_type="text">
	                            <xform:dialog propertyId="departName" propertyName="departNameText" idValue="${fsscAlitripMappingForm.departName}" nameValue="${fsscAlitripMappingForm.departNameText}"  style="width:95%;">
	                                selectFormula('departName','departNameText');
	                            </xform:dialog>
	                             <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.departName.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.tripCause')}
	                    </td>
	                    <td width="85%">
	                        <%-- 出差理由--%>
	                         <div id="_xform_tripCause" _xform_type="text">
	                            <xform:dialog propertyId="tripCause" propertyName="tripCauseText" idValue="${fsscAlitripMappingForm.tripCause}" nameValue="${fsscAlitripMappingForm.tripCauseText}" required="true"  style="width:95%;">
	                                selectFormula('tripCause','tripCauseText');
	                            </xform:dialog>
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.status')}
	                    </td>
	                    <td width="85%">
	                        <%-- 审批单状态--%>
	                        <div id="_xform_status" _xform_type="text">
	                            <xform:dialog propertyId="status" propertyName="statusText" idValue="${fsscAlitripMappingForm.status}" nameValue="${fsscAlitripMappingForm.statusText}" required="true"  style="width:95%;">
	                                selectFormula('status','statusText');
	                            </xform:dialog>
	                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.status.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                <%-- <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.applyShowId')}
	                    </td>
	                    <td width="85%">
	                        报表展示用的审批单id
	                         <div id="_xform_applyShowId" _xform_type="text">
	                            <xform:dialog propertyId="applyShowId" propertyName="applyShowIdText" idValue="${fsscAlitripMappingForm.applyShowId}" nameValue="${fsscAlitripMappingForm.applyShowIdText}"   style="width:95%;">
	                                selectFormula('applyShowId','applyShowIdText');
	                            </xform:dialog>
	                        </div>
	                    </td>
	                </tr> --%>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.travelerList')}
	                    </td>
	                    <td width="85%">
	                        <%-- 出行人列表--%>
	                         <div id="_xform_travelerList" _xform_type="text">
	                            <xform:dialog propertyId="travelerList" propertyName="travelerListText" idValue="${fsscAlitripMappingForm.travelerList}" nameValue="${fsscAlitripMappingForm.travelerListText}"   style="width:95%;">
	                                selectFormula('travelerList','travelerListText');
	                            </xform:dialog>
	                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.travelerList.text')}</span>
	                        </div>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.docCreator')}
	                    </td>
	                    <td width="85%">
	                        <%-- 创建人--%>
	                        <div id="_xform_docCreatorId" _xform_type="address">
	                            <ui:person personId="${fsscAlitripMappingForm.docCreatorId}" personName="${fsscAlitripMappingForm.docCreatorName}" />
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.docAlteror')}
	                    </td>
	                    <td width="85%">
	                        <%-- 修改人--%>
	                        <div id="_xform_docAlterorId" _xform_type="address">
	                            <ui:person personId="${fsscAlitripMappingForm.docAlterorId}" personName="${fsscAlitripMappingForm.docAlterorName}" />
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.docCreateTime')}
	                    </td>
	                    <td width="85%">
	                        <%-- 创建时间--%>
	                        <div id="_xform_docCreateTime" _xform_type="datetime">
	                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.docAlterTime')}
	                    </td>
	                    <td width="85%">
	                        <%-- 更新时间--%>
	                        <div id="_xform_docAlterTime" _xform_type="datetime">
	                            <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
	            </table>
	            </td>
            </tr>
            <tr LKS_LabelName="${lfn:message('fssc-alitrip:py.XingChengXinXi')}" >
                <td>
          		 	 <table class="tb_normal" width="100%">
          		 		 <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.fdIsTrue')}
		                    </td>
		                    <td width="85%">
		                        <%-- 生成条件--%>
		                        <div id="_xform_fdIsTrue" _xform_type="text">
		                            <xform:dialog propertyId="fdIsTrue" propertyName="fdIsTrueText" idValue="${fsscAlitripMappingForm.fdIsTrue}" nameValue="${fsscAlitripMappingForm.fdIsTrueText}" required="true"  style="width:95%;">
		                                selectFormula('fdIsTrue','fdIsTrueText');
		                            </xform:dialog>
		                             <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.fdIsTrue.text')}</span>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.fdListItem')}
		                    </td>
		                    <td width="85%">
		                        <%-- 对应明细--%>
		                        <div id="_xform_fdListItem" _xform_type="text">
		                            <xform:dialog propertyId="fdListItem" propertyName="fdListItemText" idValue="${fsscAlitripMappingForm.fdListItem}" nameValue="${fsscAlitripMappingForm.fdListItemText}" required="true"  style="width:95%;">
		                                selectFormula('fdListItem','fdListItemText');
		                            </xform:dialog>
		                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.fdListItem.text')}</span>
		                        </div>
		                    </td>
		                </tr>
		                <%-- <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.fdItemList')}
		                    </td>
		                    <td width="85%">
		                        费用类型
		                        <div id="_xform_fdItemList" _xform_type="text">
		                            <xform:dialog propertyId="fdItemList" propertyName="fdItemListText" idValue="${fsscAlitripMappingForm.fdItemList}" nameValue="${fsscAlitripMappingForm.fdItemListText}" required="true"  style="width:95%;">
		                                selectFormula('fdItemList','fdItemListText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr> --%>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.arrCity')}
		                    </td>
		                    <td width="85%">
		                        <%-- 到达城市--%>
		                        <div id="_xform_arrCity" _xform_type="text">
		                            <xform:dialog propertyId="arrCity" propertyName="arrCityText" idValue="${fsscAlitripMappingForm.arrCity}" nameValue="${fsscAlitripMappingForm.arrCityText}" required="true"  style="width:95%;">
		                                selectFormula('arrCity','arrCityText');
		                            </xform:dialog>
		                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.arrCity.text')}</span>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.depCity')}
		                    </td>
		                    <td width="85%">
		                        <%-- 出发城市--%>
		                         <div id="_xform_depCity" _xform_type="text">
		                            <xform:dialog propertyId="depCity" propertyName="depCityText" idValue="${fsscAlitripMappingForm.depCity}" nameValue="${fsscAlitripMappingForm.depCityText}" required="true"  style="width:95%;">
		                                selectFormula('depCity','depCityText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.trafficType')}
		                    </td>
		                    <td width="85%">
		                        <%-- 交通方式--%>
		                         <div id="_xform_trafficType" _xform_type="text">
		                            <xform:dialog propertyId="trafficType" propertyName="trafficTypeText" idValue="${fsscAlitripMappingForm.trafficType}" nameValue="${fsscAlitripMappingForm.trafficTypeText}" required="true"  style="width:95%;">
		                                selectFormula('trafficType','trafficTypeText');
		                            </xform:dialog>
		                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.trafficType.text')}</span>
		                        </div>
		                    </td>
		                </tr>
		                <%-- <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.itineraryId')}
		                    </td>
		                    <td width="85%">
		                        行程单id
		                        <div id="_xform_itineraryId" _xform_type="text">
		                            <xform:dialog propertyId="itineraryId" propertyName="itineraryIdText" idValue="${fsscAlitripMappingForm.itineraryId}" nameValue="${fsscAlitripMappingForm.itineraryIdText}" required="true"  style="width:95%;">
		                                selectFormula('itineraryId','itineraryIdText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr> --%>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.tripWay')}
		                    </td>
		                    <td width="85%">
		                        <%-- 行程类型--%>
		                         <div id="_xform_tripWay" _xform_type="text">
		                            <xform:dialog propertyId="tripWay" propertyName="tripWayText" idValue="${fsscAlitripMappingForm.tripWay}" nameValue="${fsscAlitripMappingForm.tripWayText}" required="true"  style="width:95%;">
		                                selectFormula('tripWay','tripWayText');
		                            </xform:dialog>
		                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.tripWay.text')}</span>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.arrDate')}
		                    </td>
		                    <td width="85%">
		                        <%-- 到达日期--%>
		                         <div id="_xform_arrDate" _xform_type="text">
		                            <xform:dialog propertyId="arrDate" propertyName="arrDateText" idValue="${fsscAlitripMappingForm.arrDate}" nameValue="${fsscAlitripMappingForm.arrDateText}" required="true"  style="width:95%;">
		                                selectFormula('arrDate','arrDateText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.depDate')}
		                    </td>
		                    <td width="85%">
		                        <%-- 出发日期--%>
		                         <div id="_xform_depDate" _xform_type="text">
		                            <xform:dialog propertyId="depDate" propertyName="depDateText" idValue="${fsscAlitripMappingForm.depDate}" nameValue="${fsscAlitripMappingForm.depDateText}" required="true"  style="width:95%;">
		                                selectFormula('depDate','depDateText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.invoiceId')}
		                    </td>
		                    <td width="85%">
		                       <%--  发票id --%>
		                        <div id="_xform_invoiceId" _xform_type="text">
		                            <xform:dialog propertyId="invoiceIsd" propertyName="invoiceIsdText" idValue="${fsscAlitripMappingForm.invoiceIsd}" nameValue="${fsscAlitripMappingForm.invoiceIsdText}" required="true"  style="width:95%;">
		                                selectFormula('invoiceIsd','invoiceIsdText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.costCenterId')}
		                    </td>
		                    <td width="85%">
		                     <%--    商旅成本中心id--%>
		                         <div id="_xform_costCenterId" _xform_type="text">
		                            <xform:dialog propertyId="costCenterIsd" propertyName="costCenterIsdText" idValue="${fsscAlitripMappingForm.costCenterIsd}" nameValue="${fsscAlitripMappingForm.costCenterIsdText}"   style="width:95%;">
		                                selectFormula('costCenterIsd','costCenterIsdText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr> 
		               <%--  <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.depCityCode')}
		                    </td>
		                    <td width="85%">
		                        出发城市编码
		                         <div id="_xform_depCityCode" _xform_type="text">
		                            <xform:dialog propertyId="depCityCode" propertyName="depCityCodeText" idValue="${fsscAlitripMappingForm.depCityCode}" nameValue="${fsscAlitripMappingForm.depCityCodeText}"   style="width:95%;">
		                                selectFormula('depCityCode','depCityCodeText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.arrCityCode')}
		                    </td>
		                    <td width="85%">
		                        到达城市编码
		                         <div id="_xform_arrCityCode" _xform_type="text">
		                            <xform:dialog propertyId="arrCityCode" propertyName="arrCityCodeText" idValue="${fsscAlitripMappingForm.arrCityCode}" nameValue="${fsscAlitripMappingForm.arrCityCodeText}"   style="width:95%;">
		                                selectFormula('arrCityCode','arrCityCodeText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr> --%>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.thirdpartCostCenterId')}
		                    </td>
		                    <td width="85%">
		                        <%-- 第三方成本中心id--%>
		                        <div id="_xform_thirdpartCostCenterId" _xform_type="text">
		                            <xform:dialog propertyId="thirdpartCostCenterId" propertyName="thirdpartCostCenterIdText" idValue="${fsscAlitripMappingForm.thirdpartCostCenterId}" nameValue="${fsscAlitripMappingForm.thirdpartCostCenterIdText}"   style="width:95%;">
		                                selectFormula('thirdpartCostCenterId','thirdpartCostCenterIdText');
		                            </xform:dialog>
		                            <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMapping.thirdpartCostCenterId.text')}</span>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.projectCode')}
		                    </td>
		                    <td width="85%">
		                        <%-- 项目代号--%>
		                        <div id="_xform_projectCode" _xform_type="text">
		                            <xform:dialog propertyId="projectCode" propertyName="projectCodeText" idValue="${fsscAlitripMappingForm.projectCode}" nameValue="${fsscAlitripMappingForm.projectCodeText}"   style="width:95%;">
		                                selectFormula('projectCode','projectCodeText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="td_normal_title" width="15%">
		                        ${lfn:message('fssc-alitrip:fsscAlitripMapping.projectTitle')}
		                    </td>
		                    <td width="85%">
		                        <%-- 项目名称--%>
		                        <div id="_xform_projectTitle" _xform_type="text">
		                            <xform:dialog propertyId="projectTitle" propertyName="projectTitleText" idValue="${fsscAlitripMappingForm.projectTitle}" nameValue="${fsscAlitripMappingForm.projectTitleText}"   style="width:95%;">
		                                selectFormula('projectTitle','projectTitleText');
		                            </xform:dialog>
		                        </div>
		                    </td>
		                </tr>
		            </table>
       			</td>
       		</tr>
       	</table>
    </center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/fssc/alitrip/fssc_alitrip_mapping/fsscAlitripMapping.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
