<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdFeishuDeptNoMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdFeishuName" title="${lfn:message('third-feishu:thirdFeishuDeptNoMapping.fdFeishuName')}" />
        <list:data-column property="fdFeishuId" title="${lfn:message('third-feishu:thirdFeishuDeptNoMapping.fdFeishuId')}" />
        <list:data-column property="fdFeishuPath" title="${lfn:message('third-feishu:thirdFeishuDeptNoMapping.fdFeishuPath')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-feishu:thirdFeishuDeptNoMapping.docAlterTime')}">
            <kmss:showDate value="${thirdFeishuDeptNoMapping.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
				<div>
				 	<a id="feishu${thirdFeishuDeptNoMapping.fdId }" style="cursor: pointer;text-decoration: underline;color: blue;font-size: 12px;" onclick="window.feishuDel('${thirdFeishuDeptNoMapping.fdId}','${thirdFeishuDeptNoMapping.fdFeishuId}','1');"><bean:message bundle="third-feishu" key="thirdFeishuOmsInit.handle.delete"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
					
					<div class='inputselectsgl' onclick="Dialog_AddressSimple(false, 'fdEkpId${thirdFeishuDeptNoMapping.fdId }','fdEkpName${thirdFeishuDeptNoMapping.fdId }', ';',ORG_TYPE_ORGORDEPT,null, null);"  style="width:220px"><input name="fdEkpId${thirdFeishuDeptNoMapping.fdId }" value="" type="hidden" /><div class="input" ><input id='ekp${thirdFeishuDeptNoMapping.fdId }' name="fdEkpName${thirdFeishuDeptNoMapping.fdId }" value="" type="text" id='ekp${thirdFeishuDeptNoMapping.fdId }' readOnly /></div><div  class="orgelement" ></div></div>
				</div>
				
				<script type="text/javascript">
							$(function(){
								var id = '${thirdFeishuDeptNoMapping.fdId }';
								$("input[name='fdEkpId"+id+"']").change(function(){
									window.ekpHandle(id,'1');
								});
							});
						</script>
						
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
