<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<center id="echart_area_chartset_adv" >
	<div>
		<table class="tb_normal"  width=100%>
			<%@ include file="config_chart_adv.jsp"%>
		</table>		
	</div>
</center>
<script>
function _doCallback4Other(_data,_fields){
}

$(document).on("table-add-new",function(event,argu){
	var id = argu.table.id;
	if(!(id=="outputs_DocList")){
		return;
	}	
	if(!_data["fields"]){
		var code = LUI.$('[name="fdCode"]').val();
		 _data = code==''?{}:LUI.toJSON(code);
		 if(_fields==null){
			_fields = _data["fields"];
		 }
	}
	var $select = $(argu.row).find(".key");
	var optArray = _data["fields"];
	if(!optArray){
		return ;
	}
	_createOptions4Select($select,optArray);
});

</script>