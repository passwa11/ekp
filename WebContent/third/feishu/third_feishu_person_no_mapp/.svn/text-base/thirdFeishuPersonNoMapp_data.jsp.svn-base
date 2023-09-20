<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdFeishuPersonNoMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdFeishuName" title="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.fdFeishuName')}" />
        <list:data-column property="fdEmployeeId" title="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.fdEmployeeId')}" />
        <list:data-column property="fdFeishuMobileNo" title="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.fdFeishuMobileNo')}" />
        <list:data-column property="fdEmail" title="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.fdEmail')}" />
        <list:data-column property="fdFeishuNo" title="${lfn:message('third-feishu:thirdFeishuPersonNoMapp.fdFeishuNo')}" />
    
    	<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
				<div>
				 	<a id="feishu${thirdFeishuPersonNoMapp.fdId }" style="cursor: pointer;text-decoration: underline;color: blue;font-size: 12px;" onclick="window.feishuDel('${thirdFeishuPersonNoMapp.fdId}','${thirdFeishuPersonNoMapp.fdEmployeeId}','1');"><bean:message bundle="third-feishu" key="thirdFeishuOmsInit.handle.delete"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
					
					<div class='inputselectsgl' onclick="Dialog_AddressSimple(false, 'fdEkpId${thirdFeishuPersonNoMapp.fdId }','fdEkpName${thirdFeishuPersonNoMapp.fdId }', ';',ORG_TYPE_PERSON,null, null);"  style="width:220px"><input name="fdEkpId${thirdFeishuPersonNoMapp.fdId }" value="" type="hidden" /><div class="input" ><input id='ekp${thirdFeishuPersonNoMapp.fdId }' name="fdEkpName${thirdFeishuPersonNoMapp.fdId }" value="" type="text" id='ekp${thirdFeishuPersonNoMapp.fdId }' readOnly /></div><div  class="orgelement" ></div></div>
				</div>
				
				<script type="text/javascript">
							$(function(){
								var id = '${thirdFeishuPersonNoMapp.fdId }';
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
