<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<div class="inputselectsgl" onclick="selectModelNameDialog(${param.callback});" style="width: 50%">
		<input name="table.modelName" value="" type="hidden" data-dbecharts-config="fdCode">
		<div class="input">
			<input subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataSource2') }" name="table.modelNameText" type="text" data-dbecharts-config="fdCode" validate="required" readonly="">
		</div>
		<div class="selectitem"></div>
	</div>
	<span class="txtstrong">*</span>

<script>
	function selectModelNameDialog(callback){
		window.focus();
		seajs.use(['lui/dialog'], function(dialog) {
			var url = "/dbcenter/echarts/common/configure/jsp/model_list.jsp";
			<c:if  test="${param.fdModelName eq 'com.landray.kmss.sys.modeling.base.model.ModelingAppModel'}">
				url = "/dbcenter/echarts/common/configure/jsp/modeling_model_list.jsp?fdKey=${param.fdKey}";
			</c:if>

			var height = window.screen.availHeight * 0.68;
			var width = document.documentElement.clientWidth * 0.6;
			var dialog = dialog.iframe(url,"${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataSource2') }",function(rs){
				if(rs){
					if(formatModelData(rs)){
						if(callback){
							callback(rs);
						}
					}					
				}
			},{width:width,height : height});
		});
	}
	
	function formatModelData(rs){
		if(rs.modelName && rs.modelNameText){
			$("input[name='table.modelName']").val(rs.modelName);
			$("input[name='table.modelNameText']").val(rs.modelNameText);
			$KMSSValidation().validateElement($("input[name='table.modelNameText']")[0]);
			return true;
		}else{
			alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataSourceError') }");
			return false;			
		}
	}
	
</script>