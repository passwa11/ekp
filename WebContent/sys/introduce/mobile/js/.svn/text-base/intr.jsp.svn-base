<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ul>
	<li>
		<div data-dojo-type="mui/form/Address" 
				id="_intr_address"
				data-dojo-props="isMul:true,type: ORG_TYPE_ALL,idField:'fdIntroduceGoalIds',
								 subject:'<bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.selectIntroduce"/>',
								 nameField:'fdIntroduceGoalNames'">
		</div>
	</li>
	
	<li style="display: none;">
		<input type="hidden" name="fdIntroduceGoalNames" value=""/>
		<input type="hidden" name="fdIntroduceGoalIds" value=""/>
		<input type="hidden" name="fdIntroduceGrade" value="0">
		<input type="hidden" name="fdIntroduceToPerson" value="0"/>
	</li>
	
	<li class="muiIntrReason">
		<textarea name="fdIntroduceReason" placeholder='<bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.reply"/>' class="muiIntrMaskText"></textarea>
	</li>
	<li>
		<label>
			<div class="fdIntroduceToEssenceClass"
				 data-dojo-type="sys/introduce/mobile/js/checkBoxEssence"
				 data-dojo-props="text:'<bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.fdIntroduceToEssence"/>',name:'fdIntroduceToEssence',value:'1'"></div>
		</label>
	</li>
	<li class="muiIntrGradeBar" >
		<div class="muiIntrGradeLeft">
			<span><bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.introduceGrade"/>:</span>
			<span id="intr_praise_opt" 
				data-dojo-type="!{starklass}" 
				data-dojo-props="value:3,editable:true,numStars:3,icon:'mui mui-2 mui-praise'">
			</span>
			<span id="contentLength" style="color: red;"></span>
		</div>
		<div class="muiIntrGradeRight">
			<div class="muiIntrSubmit"><bean:message bundle="sys-introduce" key="sysIntroduceMain.mobile.introduce.submit"/></div>
		</div>
	</li>
	<li class="muiIntrHideMask"><div class="mui-down-n mui"></div></li>
</ul>