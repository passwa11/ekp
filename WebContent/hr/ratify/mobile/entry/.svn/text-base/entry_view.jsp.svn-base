<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		员工档案
	</template:replace>
	
	<template:replace name="head">
		<style>
			ul{
				list-style: none;
			}
			.muiDialogElementDiv{
				margin-top:0!important;
				height:5rem;
				line-height:5rem;
				border-bottom:1px solid #EAEBF0!important;
			}
			.muiDialogElementTitle{
				    position: relative;
				    color:#3E4665!important;
				    left:0!important;
				    height:5rem!important;
				    line-height:5rem!important;
				    font-size:1.8rem!important;
			}
			.comfirm-content{
				color:#999FB7;
			}
			.muiConfirmDialogElement>div{
				display:block;
			}
			.muiConfirmDialogElement{
				display:block;
			}
			.comfirm-content div{
				line-height:3rem;
				display:flex;
				justify-content: space-between;
			}
			.comfirm-content div span:first-child{
				white-space: nowrap;
			}
			.comfirm-content div span:last-child{
				    white-space: nowrap;
				    text-overflow: ellipsis;
				    overflow: hidden;
			}
			.muiDialogElementButton:first-child{
				display:none!important;
			}
			.muiDialogElementButton:last-child{
				color:#fff!important;
				background:#4285F4 ;
			}
			.muiDialogElementContainer{
				border-radius:1rem!important;
			}
			.muiDialogElement .muiDialogElementButtons{
				display:flex;
				margin-bottom:1.1rem;
				margin-top:3.3rem;
			}
			.muiDialogElement .muiDialogElementButtons .muiDialogElementButton{
				flex:1;
				margin:0 1.1rem;
				height:4.8rem;
				line-height:4.8rem;
				border-radius: .552rem;
			}
			
		</style>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/css/personer.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/font/dabFont/dabFont.css"></link>
		<script src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/js/rem/rem.js"></script>
	</template:replace>
	
	<template:replace name="content">
		<div class="personal-files" style="margin-top: 122px">
		    <div style="position: fixed;left: 0;top: 0;width: 100%;z-index: 1000">
		        <div class="personal-basic-info">
		            <div class="personal-photo">
		                <img style="border-radius:.7rem" src="<%=request.getContextPath()%>/sys/person/image.jsp?personId=${param.fdId}&size=null" alt="">
		            </div>
		            <div class="personal-name-box">
		                <div class="personal-name">
		                    <span>${hrStaffEntryForm.fdName }</span>
		                    
							<c:if test="${hrStaffEntryForm.fdQRStatus eq 'true'}">
								<img src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/qrcode.png" alt="">
							</c:if>
		                </div>
		                <div class="edit-date">
		                    <span>上一次编辑：</span>
		                    <span>${hrStaffEntryForm.fdLastModifiedTime}</span>
		                    <span id="edit-detail">详情</span>
		                </div>
		            </div>
		            <div class="right-btn-edit">
		              <span><img src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/setting.png" alt=""></span>  
		            </div>
		        </div>
		       
		        <!--    基本信息-->
		        <ul class="personal-info-nav" style="background: #fff">
		            <li data-info="base">
		                <a>基本信息</a>
		                <div class="blue-line"></div>
		            </li>
		            <li data-info="connect">
		                <a>联系信息</a>
		                <div></div>
		            </li>
		            <li data-info="history">
		                <a>工作经历</a>
		                <div></div>
		            </li>
		            <li data-info="other">
		                <a>其他</a>
		                <div></div>
		            </li>
		        </ul>
		
		    </div>
		     <div id="info">
		    <div id="basic">
			    <div  class="basic-info-title">基本信息</div>
			    <div class="basic-info-item">
			        <p>曾用名</p>
			        <p class="none">${not empty hrStaffEntryForm.fdNameUsedBefore?hrStaffEntryForm.fdNameUsedBefore:"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>性别</p>
			        <p>${not empty hrStaffEntryForm.fdSex ?hrStaffEntryForm.fdSex eq "1"||hrStaffEntryForm.fdSex eq "M" ?"男":"女":"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>出生日期</p>
			        <p>${not empty hrStaffEntryForm.fdDateOfBirth?hrStaffEntryForm.fdDateOfBirth:"无"}</p>	
			    </div>
			    <div class="basic-info-item">
			        <p>籍贯</p>
			        <p>${not empty hrStaffEntryForm.fdNativePlace?hrStaffEntryForm.fdNativePlace:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>婚姻状况</p>
			        <p>${not empty hrStaffEntryForm.fdMaritalStatusName?hrStaffEntryForm.fdMaritalStatusName:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>民族</p>
			        <p>${not empty hrStaffEntryForm.fdNationName?hrStaffEntryForm.fdNationName:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>政治面貌</p>
			        <p>${not empty hrStaffEntryForm.fdPoliticalLandscapeName?hrStaffEntryForm.fdPoliticalLandscapeName:"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>健康状况</p>
			        <p>${not empty hrStaffEntryForm.fdHealthName?hrStaffEntryForm.fdHealthName:"无" }</p>
			    </div>
			
			    <div class="basic-info-item">
			        <p>现居住地</p>
			        <p>${not empty hrStaffEntryForm.fdLivingPlace?hrStaffEntryForm.fdLivingPlace:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>身份证号码 </p>
			        <p>${not empty hrStaffEntryForm.fdIdCard?hrStaffEntryForm.fdIdCard:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>学位 </p>
			        <p>${not empty hrStaffEntryForm.fdHighestDegreeName?hrStaffEntryForm.fdHighestDegreeName:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>学历 </p>
			        <p>${not empty hrStaffEntryForm.fdHighestEducationName?hrStaffEntryForm.fdHighestEducationName:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>专业</p>
			        <p>${not empty hrStaffEntryForm.fdProfession?hrStaffEntryForm.fdProfession:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>参加工作时间</p>
			        <p>${not empty hrStaffEntryForm.fdWorkTime?hrStaffEntryForm.fdWorkTime:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>入团时间 </p>
			        <p>${not empty hrStaffEntryForm.fdDateOfGroup?hrStaffEntryForm.fdDateOfGroup:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>入党时间 </p>
			        <p>${not empty hrStaffEntryForm.fdDateOfParty?hrStaffEntryForm.fdDateOfParty:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>身高（厘米）</p>
			        <p>${not empty hrStaffEntryForm.fdStature?hrStaffEntryForm.fdStature:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>体重（千克）</p>
			        <p>${not empty hrStaffEntryForm.fdWeight?hrStaffEntryForm.fdWeight:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>出生地 </p>
			        <p>${not empty hrStaffEntryForm.fdHomeplace?hrStaffEntryForm.fdHomeplace:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>户口性质 </p>
			        <p>${not empty hrStaffEntryForm.fdAccountProperties?hrStaffEntryForm.fdAccountProperties:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>户口所在地 </p>
			        <p>${not empty hrStaffEntryForm.fdRegisteredResidence?hrStaffEntryForm.fdRegisteredResidence:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>户口所在派出所</p>
			        <p>${not empty hrStaffEntryForm.fdResidencePoliceStation?hrStaffEntryForm.fdResidencePoliceStation:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>拟入职日期</p>
			        <p>${not empty hrStaffEntryForm.fdPlanEntryTime?hrStaffEntryForm.fdPlanEntryTime:"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>拟入职部门</p>
			        <p>${not empty hrStaffEntryForm.fdPlanEntryDeptName?hrStaffEntryForm.fdPlanEntryDeptName:"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>状态</p>
			        <p><sunbor:enumsShow enumsType="hrStaffEntry_fdStatus" value="${hrStaffEntryForm.fdStatus}"></sunbor:enumsShow></p>
			    </div>
			    <div class="basic-info-item">
			        <p>更新人</p>
			        <p>${not empty hrStaffEntryForm.fdAlterorName?hrStaffEntryForm.fdAlterorName:"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>确认人</p>
			        <p>${not empty hrStaffEntryForm.fdCheckerName?hrStaffEntryForm.fdCheckerName:"无"}</p>
			    </div>
			    <div class="basic-info-item">
			        <p>确认时间</p>
			        <p>${not empty hrStaffEntryForm.fdCheckDate?hrStaffEntryForm.fdCheckDate:"无"}</p>
			    </div>
		    </div>

		<!--    联系方式-->
			<div id="connect">
				<div  class="basic-info-title">联系方式</div>
			    <div class="basic-info-item">
			        <p>邮箱地址</p>
			        <p>${not empty hrStaffEntryForm.fdEmail?hrStaffEntryForm.fdEmail:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>手机号码</p>
			        <p>${not empty hrStaffEntryForm.fdMobileNo?hrStaffEntryForm.fdMobileNo:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>紧急联系人</p>
			        <p>${not empty hrStaffEntryForm.fdEmergencyContact?hrStaffEntryForm.fdEmergencyContact:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>紧急联系人电话</p>
			        <p>${not empty hrStaffEntryForm.fdEmergencyContactPhone?hrStaffEntryForm.fdEmergencyContactPhone:"无" }</p>
			    </div>
			    <div class="basic-info-item">
			        <p>其他联系方式</p>
			        <p class="none">${not empty hrStaffEntryForm.fdOtherContact?hrStaffEntryForm.fdOtherContact:"无" }</p>
			    </div>	
			</div>

		<!--    工作经历-->
			<div id="history">
			  <div  class="basic-info-title">工作经历</div>
			    <div class="work-experience">
			    	<c:forEach items="${hrStaffEntryForm.fdHistory_Form}" var="history">
				        <div class="work-experience-item">
				             <div class="company-name">
				                 <p>${history.fdName}</p>
				                 <p>${history.fdPost}</p>
				             </div>
				             <div class="date">
				                 ${history.fdStartDate}-${history.fdEndDate }
				             </div>
				        </div>
			        </c:forEach>
			        <c:if test="${hrStaffEntryForm.fdHistory_Form.size()==0}">
				     	<div class="entry_empty">
				     		<div class="entry_others_empty"></div>
				     		<p>空空如也快去完善吧~</p>
				     	</div>
				     </c:if>
			    </div>
			</div>
			<div id="others">
						<!--    其他-->
			    <div  class="basic-info-title">其他</div>
			<!--    教育信息-->
			    <div class="education-info">
			        <div class="education-info-title">
			            <span>教育信息</span>
			            <img src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/edubackground.png" alt="">
			        </div>
<!-- 			        <div class="add">
			            添加
			        </div> -->
			    </div>
			<!--    添加教育信息-->
<%-- 			    <div class="add-education-experience">
			        <div class="add-education-experience-btn">
			            <img class="add-icon" src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/add-icon.png" alt=""/>
			            <span>添加教育经历</span>
			        </div>
			    </div> --%>
			<!--    添加教育信息-->
			    <div class="work-experience">
				    <c:forEach items="${hrStaffEntryForm.fdEducations_Form}" var="education">
				        <div class="work-experience-item">
				            <div class="company-name">
				                <p>${education.fdName }</p>
				                <p>${education.fdAcadeRecordName}</p>
				            </div>
				            <div class="date">
				                ${education.fdEntranceDate }-${education.fdGraduationDate }
				            </div>
				        </div>
				     </c:forEach>
				     <c:if test="${hrStaffEntryForm.fdEducations_Form.size()==0}">
				     	<div class="entry_empty">
				     		<div class="entry_others_empty"></div>
				     		<p>空空如也快去完善吧~</p>
				     	</div>
				     </c:if>
			    </div>
			<!--    资格证书-->
			    <div class="education-info" style="padding-top: 0">
			        <div class="education-info-title">
			            <span>资格证书</span>
			            <img src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/qualification.png" alt="">
			        </div>
<!-- 			        <div class="add">
			            添加
			        </div> -->
			    </div>
			    <!--    添加资格证书-->
<%-- 			    <div class="add-education-experience">
			        <div class="add-education-experience-btn">
			            <img class="add-icon" src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/add-icon.png" alt=""/>
			            <span>添加资格证书</span>
			        </div>
			    </div> --%>
			    <!--    添加资格证书-->
			    <div class="work-experience">
			    	<c:forEach items="${hrStaffEntryForm.fdCertificate_Form}" var="certificate">
			        <div class="work-experience-item">
			            <div class="company-name">
			                <p>${certificate.fdName }</p>
			                <p>颁发日期：${certificate.fdIssueDate }</p>
			            </div>
			            <div class="date" style="text-align: right">
			                <p>${certificate.fdIssuingUnit}</p>
			                <p style="margin-top: 13px">失效日期：${certificate.fdInvalidDate }</p>
			            </div>
			        </div>
			        </c:forEach>
			        <c:if test="${hrStaffEntryForm.fdCertificate_Form.size()==0}">
				     	<div class="entry_empty">
				     		<div class="entry_others_empty"></div>
				     		<p>空空如也快去完善吧~</p>
				     	</div>
				   		
				     </c:if>	
			    </div>
			<!--    奖惩信息-->
			    <div class="education-info" style="padding-top: 0">
			        <div class="education-info-title">
			            <span>奖惩信息</span>
			            <img src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/medal.png" alt="">
			        </div>
<!-- 			        <div class="add">
			         		   添加
			        </div> -->
			    </div>
			    <!--    添加奖惩信息-->
<%-- 			    <div class="add-education-experience">
			        <div class="add-education-experience-btn">
			            <img class="add-icon" src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/images/add-icon.png" alt=""/>
			            <span>添加奖惩信息</span>
			        </div>
			    </div> --%>
			    <!--    添加奖惩信息-->
			    <div class="work-experience" style="padding-bottom: 64px">
			    <c:forEach items="${hrStaffEntryForm.fdRewardsPunishments_Form}" var="rewards">
			        <div class="work-experience-item" style="height: 70px;">
			            <div class="company-name">
			                <p>${rewards.fdName }</p>
			            </div>
			            <div class="date" style="text-align: right">
			                <p>${rewards.fdDate }</p>
			            </div>
			        </div>
				</c:forEach>
					<c:if test="${hrStaffEntryForm.fdRewardsPunishments_Form.size()==0}">
				     	<div class="entry_empty">
				     		<div class="entry_others_empty"></div>
				     		<p>空空如也快去完善吧~</p>
				     	</div>
				   		
				     </c:if>
			    </div>
			</div>
		</div>
		 <!--    <div class="personal-item-bottom-btn">
		        <div>确认到岗</div>
		    </div> -->
		</div>
		<script>
		$('.right-btn-edit span').on('click',function(){
			window.location.href= "<%=request.getContextPath()%>/hr/staff/hr_staff_entry/hrStaffEntry.do?method=edit&fdId=${param.fdId }"
		})
		$(".personal-info-nav li").on("click",function(){
			var oLineDiv = $(this).find("div")
			if(!oLineDiv.hasClass('blue-line')){
				$(".personal-info-nav li").find("div").removeClass('blue-line');
				oLineDiv.addClass('blue-line')
			}
			var othersHeight = $("#others").height();
			var histroyHeight = $("#history").height();
			var connectHeight = $("#connect").height();
			var baseHeight = $("#basic").height();
			var otherTop = histroyHeight+connectHeight+baseHeight;
			var connectTop = baseHeight;
			var baseTop = 0
			var histroyTop = baseHeight+connectHeight;
			switch($(this).data("info")){
			 case "base":
				 $(window).scrollTop(baseTop);
				 break;
			 case "connect":
				 $(window).scrollTop(connectTop);
				 break;
			 case "history":
				 $(window).scrollTop(histroyTop);
				 break;
			 case "other":
				 $(window).scrollTop(otherTop);
				 break;
			}
		})
		require(['dojo/dom-style', 'dojo/dom', 'dojo/topic','mui/dialog/Confirm'], function(domStyle, dom, topic,comfirm){
			var editDate = document.getElementById('edit-detail');
			editDate.addEventListener("click",function(){
				var comObj = "<div class='comfirm-content'><div><span>录入到待入职:</span><span>${hrStaffEntryForm.docCreatorName}</span></div><div><span>录入时间:</span><span>${hrStaffEntryForm.docCreateTime}</span></div><div><span>入职登记表提交时间:</span><span>${hrStaffEntryForm.fdQRTime}</span></div><div><span>最后修改时间:</span><span>${hrStaffEntryForm.fdLastModifiedTime}</span></div><div>"
				var move = function(e){
					e.preventDefault();
				}
				comfirm(comObj,'详情',function(){
					document.removeEventListener("touchmove",move);
				})
				document.addEventListener("touchmove",move,{ passive: false });
				$('.muiDialogElement').css("top",document.documentElement.scrollTop)

				
			})
			
		})
		</script>
	</template:replace>


</template:include>
