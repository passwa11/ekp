<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.number.service.ISysNumberMainMappService,
                 com.landray.kmss.sys.number.service.ISysNumberMainFlowService,
                 com.landray.kmss.sys.number.service.ISysNumberMainService,
                 com.landray.kmss.sys.number.model.SysNumberMainMapp,
                 com.landray.kmss.sys.number.model.SysNumberMain,
                 com.landray.kmss.util.SpringBeanUtil,
                 com.landray.kmss.util.StringUtil,
                 java.util.*" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysNumberMainFlow" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdNumberMain.fdName" title="${ lfn:message('sys-number:sysNumberMainFlow.fdNumberMain') }" escape="false" style="text-align:left;min-width:180px">
		    <c:set var="sysNumberMainName" value="${sysNumberMainFlow.fdNumberMain.fdName}"/>
			<c:set var="sysNumberMainId" value="${sysNumberMainFlow.fdNumberMain.fdId}"/>
			<%
				String sysNumberMainName=(String)pageContext.getAttribute("sysNumberMainName");
				String sysNumberMainId=(String)pageContext.getAttribute("sysNumberMainId");
				if(StringUtil.isNotNull(sysNumberMainName))
				{
					out.print(sysNumberMainName);
				}else{
					ISysNumberMainMappService sysNumberMainMappService= (ISysNumberMainMappService) SpringBeanUtil.getBean("sysNumberMainMappService");
					List mapps=null;
					try {
						mapps = sysNumberMainMappService.findList("fdNumber.fdId='"+sysNumberMainId+"'", null);
					} catch (Exception e) {
						System.out.println(e.getLocalizedMessage());
					}
					if(mapps!=null && mapps.size()>0)
					{
						SysNumberMainMapp mapp=(SysNumberMainMapp) mapps.get(0);
						if(mapp!=null)
						{
							try {
								String fdName = ((ISysNumberMainFlowService)SpringBeanUtil.getBean("sysNumberMainFlowService")).getLinkModelName(mapp.getFdModelName(), mapp.getFdModelId());
								SysNumberMain fdNumberMain = mapp.getFdNumber();
								if(fdNumberMain != null){
									fdNumberMain.setFdName(fdName);
									ISysNumberMainService sysNumberMainService = (ISysNumberMainService)SpringBeanUtil.getBean("sysNumberMainService");
									sysNumberMainService.update(fdNumberMain);
								}
								out.print(""+ fdName);
							} catch (Exception e) {
								System.out.println(e.getLocalizedMessage());
							}
						}
					}
				}
			%> 
		  
		</list:data-column>
		<list:data-column headerClass="width160" property="fdVirtualNumberValue" title="${ lfn:message('sys-number:sysNumberMainFlow.fdVirtualNumberValue') }">
		</list:data-column>
		<list:data-column headerClass="width80"  property="fdFlowNum" title="${ lfn:message('sys-number:sysNumberMainFlow.fdFlowNum') }">
		</list:data-column>
		<list:data-column headerClass="width160" property="fdLimitsValue" title="${ lfn:message('sys-number:sysNumberMainFlow.fdLimitsValue') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/number/sys_number_main_flow/sysNumberMainFlow.do?method=edit&fdId=${sysNumberMainFlow.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysNumberMainFlow.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>