<%@page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.hr.staff.service.IHrStaffContractTypeService"%>
<%@page import="com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract"%>
<%@page import="com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService"%>
<%@page import="com.landray.kmss.hr.ratify.model.HrRatifyPositive"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.hr.staff.model.HrStaffPersonInfo"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
 
 <%
		String fdHierarchyId = UserUtil.getUser().getFdHierarchyId();
		ISysOrgElementService sysOrgElementService = (ISysOrgElementService)SpringBeanUtil.getBean("sysOrgElementService");
		SysOrgElement orgElement  = (SysOrgElement)sysOrgElementService.findByPrimaryKey(fdHierarchyId.split("x")[1]);
		request.setAttribute("orgName", orgElement.getFdName());
%> 
<list:data>
	<list:data-columns var="hrStaffPersonInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdIsAvailable">
			${hrStaffPersonInfo.fdOrgPerson.fdIsAvailable}
		</list:data-column>
		<list:data-column col="imgUrl">
			${urlJson[hrStaffPersonInfo.fdId]}
		</list:data-column>
		<list:data-column headerClass="width30" col="index" >
		  ${status+1}
		</list:data-column>
		<!-- 姓名-->
		<list:data-column col="fdName" title="${ lfn:message('hr-ratify:hrRatify.concern.person.info') }" escape="false" styleClass="hr_tb_align_left" headerClass="width250">
			<%-- <span class="com_subject"><c:out value="${hrStaffPersonInfo.fdName}" /></span> --%>
			<div class="lui_hr_list_info">
                <p class="lui_hr_list_info_main">
                	<span>${hrStaffPersonInfo.fdName}</span> 
                	<span class="mtr15 lui_hr_assist_txt">
                		工号：
                		<c:choose>
							<c:when  test="${not empty hrStaffPersonInfo.fdStaffNo}">
								${hrStaffPersonInfo.fdStaffNo}
							</c:when>
							<c:otherwise>
								暂无工号
							</c:otherwise>
						</c:choose>
                	</span>
                </p>
                <p class="lui_hr_list_info_desc">${hrStaffPersonInfo.fdOrgParentsName}</p>
            </div>
		</list:data-column>
		<!--账号-->
		<list:data-column col="fdLoginName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLoginName') }"> 
		     <c:if test="${hrStaffPersonInfo.fdOrgPerson != null}">
	    		${hrStaffPersonInfo.fdOrgPerson.fdLoginName}
	    	</c:if>
		</list:data-column>
		<!--到本单位时间-->
		<list:data-column col="fdTimeOfEnterprise" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdTimeOfEnterprise}" type="date" /> 
		</list:data-column>
		<!--部门-->
		<list:data-column col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			${hrStaffPersonInfo.fdOrgParentsName}
		</list:data-column>
		<!--工号-->
		<list:data-column property="fdStaffNo" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		</list:data-column>
		<!--员工状态-->
		<list:data-column col="fdStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" escape="false">
			<%-- <sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" /> --%>
			<div class="lui_hr_entry_status lui_hr_status_${hrStaffPersonInfo.fdStatus}"><sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" /></div>
			<div class="lui_hr_entry_time lui_hr_assist_txt">
				<c:choose>
					<c:when  test="${not empty hrStaffPersonInfo.fdEntryTime}">
						<kmss:showDate value="${hrStaffPersonInfo.fdEntryTime}" type="date"></kmss:showDate> 入职
					</c:when>
					<c:otherwise>
						${ lfn:message('hr-ratify:hrRatify.concern.zanwuruzhiriqi') }
					</c:otherwise>
				</c:choose>
			</div>
		</list:data-column>
		<!--岗位-->
		<list:data-column col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ hrStaffPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<!--入职时间-->
		<list:data-column col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }" headerClass="width160">
		    <kmss:showDate value="${hrStaffPersonInfo.fdEntryTime}" type="date" /> 
		</list:data-column>
		<%
			String fdId = null;
			HrStaffPersonInfo hrStaffPersonInfo = ((HrStaffPersonInfo)pageContext.getAttribute("hrStaffPersonInfo"));
			if(null != hrStaffPersonInfo.getFdOrgPerson()){
				fdId = hrStaffPersonInfo.getFdOrgPerson().getFdId();
			}else{
				fdId = hrStaffPersonInfo.getFdId();
			}
			IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService = (IHrStaffPersonExperienceContractService)SpringBeanUtil.getBean("hrStaffPersonExperienceContractService");
		    
		    HQLInfo hqlInfo = new HQLInfo();
		    hqlInfo.setWhereBlock("hrStaffPersonExperienceContract.fdPersonInfo.fdId =:fdId and hrStaffPersonExperienceContract.fdContStatus = '1'");
		    hqlInfo.setParameter("fdId", fdId);
			HrStaffPersonExperienceContract canPrintContract =(HrStaffPersonExperienceContract) hrStaffPersonExperienceContractService.findFirstOne(hqlInfo);
			if(null != canPrintContract){
       			request.setAttribute("hrStaffPersonExperienceContract", canPrintContract);
       			request.setAttribute("canPrint", HrStaffPersonUtil.canPrint(canPrintContract.getFdId()));
       		}else{
       			request.setAttribute("hrStaffPersonExperienceContract", null);
       			request.setAttribute("canPrint", false);
       		}
       	%>
       	<c:choose>
	       	<c:when test="${not empty hrStaffPersonExperienceContract }">
	       		<!-- 合同名称 -->
	       		<list:data-column col="contractName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
				    <c:out value="${hrStaffPersonExperienceContract.fdName}"></c:out>
				</list:data-column>
				<!-- 合同编号 -->
	       		<list:data-column col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
				</list:data-column>
				<!-- 合同类型 -->
	       		<list:data-column col="fdContType" title="合同类型">
				    <%
						IHrStaffContractTypeService service = (IHrStaffContractTypeService)SpringBeanUtil.getBean("hrStaffContractTypeService");
						HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract)request.getAttribute("hrStaffPersonExperienceContract");
						String fdContType = contract.getFdContType();
						String fdSignType = contract.getFdSignType();
						if(service.checkExist(fdContType)){
					%>
						<c:out value="${hrStaffPersonExperienceContract.fdContType }"></c:out>
					<%
						}else if(StringUtil.isNotNull(fdContType)){
							String fdRelatedProcess = contract.getFdRelatedProcess();
							String[] str = fdContType.split("\\~");
							if(str.length > 1){
								out.println(HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess));
								fdContType = HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess);
							}
						}
						request.setAttribute("fdContType", fdContType);
					%>
				</list:data-column>
				<!-- 合同期限 -->
	       		<%-- <list:data-column col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
				</list:data-column> --%>
				<!-- 合同时间 -->
	       		<list:data-column col="contDate" title="合同时间">
	       			<c:choose>
		       			<c:when test="${not empty hrStaffPersonExperienceContract.fdBeginDate || not empty hrStaffPersonExperienceContract.fdEndDate || hrStaffPersonExperienceContract.fdIsLongtermContract == true }">
					    	<kmss:showDate value="${hrStaffPersonExperienceContract.fdBeginDate}" type="date" />
							~
							<c:choose>
								<c:when test="${hrStaffPersonExperienceContract.fdIsLongtermContract == true }">
									${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1') }
								</c:when>
								<c:otherwise>
									<kmss:showDate value="${hrStaffPersonExperienceContract.fdEndDate}" type="date" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</list:data-column>
				<!-- 合同签订时间 -->
	       		<list:data-column col="fdHandleDate" title="合同签订时间">
	       			<c:choose>
		       			<c:when test="${not empty hrStaffPersonExperienceContract.fdHandleDate}">
				  			<kmss:showDate value="${hrStaffPersonExperienceContract.fdHandleDate}" type="date" />
				  		</c:when>
				  		<c:otherwise>
				  			-
				  		</c:otherwise>
				  	</c:choose>	
				</list:data-column>
				<!-- 合同签订次数 -->
	       		<%-- <list:data-column col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
				</list:data-column> --%>
				<!-- 合同信息 -->
				<list:data-column col="contInfo" title="合同信息" escape="false">
					<div class="lui_hr_list_info">
	                    <p class="lui_hr_list_info_main"><span class="lui_text_primary">${fdContType}</span>
	                    	<span class="mtl10">
	                    		<c:choose>
									<c:when test="${ hrStaffPersonExperienceContract.fdSignType eq '1' or hrStaffPersonExperienceContract.fdSignType eq '2'}">
										<sunbor:enumsShow value="${ hrStaffPersonExperienceContract.fdSignType }" enumsType="hrStaffPersonExperienceContract_fdSignType" />
									</c:when>
									<c:otherwise>
										<%
											HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract)request.getAttribute("hrStaffPersonExperienceContract");
											String fdSignType = contract.getFdSignType();
											if(StringUtil.isNotNull(fdSignType)){
												String fdRelatedProcess = contract.getFdRelatedProcess();
												String[] str = fdSignType.split("\\~");
												if(str.length > 1)
													out.println(HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess));
											}
										%>
									</c:otherwise>
								</c:choose>
	                    	</span>
	                    </p>
	                    <p class="lui_hr_list_info_desc"><span class="lui_hr_assist_txt">名称：${hrStaffPersonExperienceContract.fdName} &nbsp;合同公司：${orgName}</span></p>
	                </div>
				</list:data-column>
				<!-- 合同状态 -->
				<list:data-column col="fdContStatus" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContStatus') }">
					<c:choose>
						<c:when test="${empty hrStaffPersonExperienceContract.fdContStatus }">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContStatus.1" />
						</c:when>
						<c:otherwise>
							<sunbor:enumsShow value="${ hrStaffPersonExperienceContract.fdContStatus }" enumsType="hrStaffPersonExperienceContract_fdContStatus" />
						</c:otherwise>
					</c:choose>
				</list:data-column>
	       	</c:when>
	       	<c:otherwise>
	       		<!-- 合同信息 -->
				<list:data-column col="contInfo" title="合同信息" escape="false">
					-
				</list:data-column>
				<!-- 合同状态 -->
				<list:data-column col="fdContStatus" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContStatus') }">
					              -
				</list:data-column>
				<!-- 合同时间 -->
	       		<list:data-column col="contDate" title="合同时间">
	       			          -
				</list:data-column>
				<!-- 合同签订时间 -->
	       		<list:data-column col="fdHandleDate" title="合同签订时间">
	       			     -
				</list:data-column>
	       	</c:otherwise>
       	</c:choose>
       	
       	<!-- 合同签订情况 -->
		<list:data-column col="fdSignType" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.signedInfo') }" escape="false">
			<c:out value="${ lfn:message('hr-staff:hrStaffEntry.notSign') }"></c:out>
		</list:data-column>
		<!-- 历史签订情况 -->
		<list:data-column col="fdHistoryContract" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.historySignedInfo') }" escape="false">
			<%
				hqlInfo = new HQLInfo();
			    hqlInfo.setWhereBlock("hrStaffPersonExperienceContract.fdPersonInfo.fdId =:fdId");
			    hqlInfo.setParameter("fdId", fdId);
			    List<HrStaffPersonExperienceContract> contracts = hrStaffPersonExperienceContractService.findList(hqlInfo);
			    if(null != contracts && contracts.size() > 0){
			%>
					<div class="lui_hr_contract_list">
			<%
			    	for(HrStaffPersonExperienceContract contract : contracts){
			    		request.setAttribute("fdHistorycontract", contract);
			%>	
						<div class="lui_hr_contract_item">
                            <div class="lui_hr_list_info_main">${fdHistorycontract.fdName}</div>
                            <p class="lui_hr_list_info_desc">
                            	<span class="lui_hr_status_link status_fire">
                            		<sunbor:enumsShow enumsType="hrStaffPersonExperienceContract_fdContStatus" value="${fdHistorycontract.fdContStatus }"/>
                            	</span>
                            </p>
                        </div>
			
			<%
			    	}
			%>
					</div>
			<%
			    }else{
			%>
				-
			<%
			    }
			%>
		</list:data-column>
       	
       		<!-- 其它操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
                    <c:choose>
						<c:when test="${not empty hrStaffPersonExperienceContract }">
							<span class="lui_hr_link_item ">
		                           <a class="lui_text_primary" href="javascript:changeContract('${hrStaffPersonExperienceContract.fdId}')">${ lfn:message('hr-staff:hrStaffEntry.modify') }</a>
		                       </span>
		                       <span class="lui_hr_link_item ">
		                           <a class="lui_text_primary" href="javascript:renew('${hrStaffPersonExperienceContract.fdId}')">${ lfn:message('hr-staff:hrStaffEntry.renewal') }</a>
		                       </span>
		                       <c:if test="${canPrint }">
			                       <span class="lui_hr_link_item ">
			                       		<a class="lui_text_primary" href="javascript:Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=print&fdId=${hrStaffPersonExperienceContract.fdId}')">打印</a>
			                       </span>
		                       </c:if>
						</c:when>
						<c:otherwise>
							<span class="lui_hr_link_item ">
		                           <a class="lui_text_primary" href="javascript:signContract('${hrStaffPersonInfo.fdId}')">${ lfn:message('hr-staff:hrStaffEntry.sign') }</a>
		                       </span>
						</c:otherwise>
					</c:choose>
          	</div>
			<!--操作按钮 结束-->
		</list:data-column>
       	
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>