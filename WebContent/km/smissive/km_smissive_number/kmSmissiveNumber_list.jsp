<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.km.smissive.model.KmSmissiveNumber,
				 com.landray.kmss.sys.number.service.ISysNumberMainMappService,
                 com.landray.kmss.sys.number.service.ISysNumberMainFlowService,
                 com.landray.kmss.sys.number.service.ISysNumberMainService,
                 com.landray.kmss.sys.number.model.SysNumberMainMapp,
                  com.landray.kmss.sys.number.model.SysNumberMain,
                  com.landray.kmss.sys.number.model.SysNumberMainFlow,
                 com.landray.kmss.util.SpringBeanUtil,
                 com.landray.kmss.util.StringUtil,
				java.util.*,
				com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.km.smissive.service.IKmSmissiveNumberService"%>
<list:data>
	<list:data-columns var="kmSmissiveNumber" list="${queryPage.list }">
		<%
			if(pageContext.getAttribute("kmSmissiveNumber")!=null){
				KmSmissiveNumber kmSmissiveNumber = (KmSmissiveNumber)pageContext.getAttribute("kmSmissiveNumber");
				String fdNumberMainId = "";
				if(StringUtil.isNotNull(kmSmissiveNumber.getFdNumberId())){
					fdNumberMainId =kmSmissiveNumber.getFdNumberId() ;
				}
				request.setAttribute("fdNumberMainId", fdNumberMainId);
				
				String fdNumberVal ="";
				String fdFlowId = "";
				if(StringUtil.isNotNull(kmSmissiveNumber.getFdNumberValue())){
					fdNumberVal = kmSmissiveNumber.getFdNumberValue();
					fdFlowId = fdNumberVal.substring(fdNumberVal.lastIndexOf("#")+1,fdNumberVal.length());
				}
				request.setAttribute("fdNumberVal", fdNumberVal);
				
				String fdName = "";
				ISysNumberMainFlowService sysNumberMainFlowService  = (ISysNumberMainFlowService)SpringBeanUtil.getBean("sysNumberMainFlowService");
				ISysNumberMainService sysNumberMainService = (ISysNumberMainService)SpringBeanUtil.getBean("sysNumberMainService");
				SysNumberMain sysNumberMain = (SysNumberMain)sysNumberMainService.findByPrimaryKey(fdNumberMainId, SysNumberMain.class, true);
				if(sysNumberMain != null){
					String sysNumberMainName=sysNumberMain.getFdName();
					if(StringUtil.isNotNull(sysNumberMainName)){
						fdName = sysNumberMainName;
					}else{
						ISysNumberMainMappService sysNumberMainMappService= (ISysNumberMainMappService) SpringBeanUtil.getBean("sysNumberMainMappService");
						List mapps=null;
						try {
							mapps = sysNumberMainMappService.findList("fdNumber.fdId='"+fdNumberMainId+"'", null);
						} catch (Exception e) {
							System.out.println(e.getLocalizedMessage());
						}
						if(mapps!=null && mapps.size()>0)
						{
							SysNumberMainMapp mapp=(SysNumberMainMapp) mapps.get(0);
							if(mapp!=null){
								try {
									fdName =sysNumberMainFlowService.getLinkModelName(mapp.getFdModelName(), mapp.getFdModelId());
								} catch (Exception e) {
									System.out.println(e.getLocalizedMessage());
								}
							}
						}
					}
				}
				request.setAttribute("fdName", fdName);
			}
		%>
	
	
		<list:data-column property="fdId"></list:data-column>
		<%--名称--%>
		<list:data-column col="fdName" title="编号规则名字和id" escape="false"  style="text-align:center;width:400px;">
			<c:out value="${fdName}(${fdNumberMainId})"></c:out>
		</list:data-column>
		<list:data-column  col="fdNumberValue" title="编号">
			<c:out value="${fdNumberVal}"></c:out>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:deleteNum('${kmSmissiveNumber.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>