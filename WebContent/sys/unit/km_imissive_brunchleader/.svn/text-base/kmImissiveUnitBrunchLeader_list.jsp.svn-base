<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.unit.model.KmImissiveUnitBrunchLeader,com.landray.kmss.sys.unit.model.KmImissiveUnit,java.util.*,com.landray.kmss.util.*"%>
<list:data>
	<list:data-columns var="kmImissiveUnitBrunchLeader" list="${queryPage.list }">
		<list:data-column property="fdId" />
		
		<list:data-column  headerClass="width100" property="fdLeader.fdName" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.fdLeader') }">
		</list:data-column>
		<list:data-column  headerClass="width100" col="fdLeader.fdParent.fdName" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.fdDept') }">
			<c:if test="${fdLeader.fdParent != null}">
				${fdLeader.fdParent.fdName}
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.fdOrder') }">
		</list:data-column>
		<list:data-column  col="fdUnits" escape="false" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.fdUnits') }" >
		<%
			if(pageContext.getAttribute("kmImissiveUnitBrunchLeader")!=null){
		    List fdUnits=((KmImissiveUnitBrunchLeader)pageContext.getAttribute("kmImissiveUnitBrunchLeader")).getFdUnits();
			String unitName="";
				for(int i=0;i<fdUnits.size();i++){
					if(i==fdUnits.size()-1){
						unitName+=((KmImissiveUnit)fdUnits.get(i)).getFdName();	
					}else{
						unitName+=((KmImissiveUnit)fdUnits.get(i)).getFdName()+";";	
					}
				 }
				request.setAttribute("unitName",unitName);
			}
			%>
			<p title="${unitName}">	${unitName}</p>
		</list:data-column>	
		<list:data-column headerClass="width40" col="fdIsAvailable" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.fdIsAvailable') }">
		   <sunbor:enumsShow value="${kmImissiveUnitBrunchLeader.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.docCreateId') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do?method=edit&fdId=${kmImissiveUnitBrunchLeader.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImissiveUnitBrunchLeader.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do?method=delete&fdId=${kmImissiveUnitBrunchLeader.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmImissiveUnitBrunchLeader.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>