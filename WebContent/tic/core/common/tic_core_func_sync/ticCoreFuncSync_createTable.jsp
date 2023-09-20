<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>

<script type="text/javascript">
	function createTable(){
		var url = Com_Parameter.ContextPath
		+ "tic/core/common/tic_core_func_sync/ticCoreFuncSync.do?method=createTable";
		var dbId = '${param.dbId}';
		var sql = $("#sql").val();
		$.ajax({     
		     type:"post",   
		     url:url,
		     data:{dbId:dbId,sql:sql},
		     async:false,
		     dataType:"text",
		     success: function(data){
		    	 alert(data);
			}    
	 	});
	}
</script>

    <center>

        <div style="width:50%;align:left">
        	<span style="font-size:18px;">建表SQL(请修改后拷贝到数据库中执行):</span><br>
            <textarea id="sql" style="width:320px;height:100px;font-size:14px;">${sql }</textarea>
            <br>
            <input type="button" onclick="createTable()" value="执行SQL">
        </div>
    </center>
   
<%@ include file="/resource/jsp/edit_down.jsp" %>