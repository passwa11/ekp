<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ul>
	<li>
		<div data-dojo-type="mui/form/Address" 
				id="_cir_address"
				data-dojo-props="isMul:true,type: ORG_TYPE_ALL,idField:'receivedCirCulatorIds',placeholder:'<bean:message bundle="sys-circulation" key="sysCirculationMain.mobile.selectCirculors"/>',nameField:'receivedCirCulatorNames'">
		</div>
	</li>
	
	<li style="display: none;">
		<input type="hidden" name="receivedCirCulatorNames" value=""/>
		<input type="hidden" name="receivedCirCulatorIds" value=""/>
	</li>
	
	<li class="muiCirRemark">
		<textarea name="fdRemark" placeholder="<bean:message bundle="sys-circulation" key="sysCirculationMain.mobile.description"/>" class="muiCirMaskText"></textarea>
	</li>
	
	<li class="muiCirGradeBar" >
		<div class="muiCirGradeLeft">
			
		</div>
		<div class="muiCirGradeRight">
			<div class="muiCirSubmit"><bean:message bundle="sys-circulation" key="sysCirculationMain.mobile.submit"/></div>
		</div>
	</li>
	<li class="muiCirHideMask"><div class="mui-down-n mui"></div></li>
</ul>