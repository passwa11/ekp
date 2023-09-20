<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<title>
	${ lfn:message('eop-basedata:portlet.doc.view.flat') }
</title>
<head>
	<style>
		input{
		    border-bottom: 1px solid #000;
		    border-top: 0px;
		    border-left: 0px;
		    border-right: 0px;
		}
	</style>
	<script type="text/javascript">
		if (document.addEventListener) {//如果是Firefox      
		   	document.addEventListener("keypress", otherHandler, true);  
		  } else {  
		      document.attachEvent("onkeypress", ieHandler);  
		  }
		
		function otherHandler(evt) { 
		    if (evt.keyCode == 13) {  
		   	 	FSSC_Search();  
		     }  
		 }  
		
		function ieHandler(evt) {  
		   if (evt.keyCode == 13) {  
			   FSSC_Search();  
		   }  
		}
		//搜索单据
		function FSSC_Search(){
			var fdNumber=$("input[name='fdNumber']").val();
			var content = $("#contentDiv");
			content.show();
			if(!fdNumber){
				content.html("${lfn:message('eop-basedata:message.doc.number.isnotnull')}");
			}else{
				var params={ "fdNumber":fdNumber}
				$.ajax({
					url:Com_Parameter.ContextPath + 'eop/basedata/eop_basedata_portlet/eopBasedataPortlet.do?method=getDocData',
					data:{params:JSON.stringify(params)},
					async:false,
					success:function(rtn){
						rtn = JSON.parse(rtn);
						if(rtn.result=='failure'){
							content.html("${lfn:message('eop-basedata:message.doc.number.warning')}");
							return;
						}
						content.html("");
						seajs.use(['lui/util/env'],function(env){
							Com_OpenWindow(env.fn.formatUrl(rtn.fdUrl), '_blank');
						});
					}
				});
			}
		}
		//重置编码
		function doReset(){
			$('input[name="fdNumber"]').val("");
			$("#contentDiv").hide();
		}
	</script>
</head>
<body>
	<div style="padding:10px;">
		<div><!-- 单据编码  -->
			${ lfn:message('eop-basedata:portlet.doc.view.flat') }:
			<input name="fdNumber" style="width:50%" class="input"/>&nbsp;&nbsp;
			<input type=button value="${ lfn:message('button.ok') }" onclick="FSSC_Search();" class="btnopt">&nbsp;&nbsp;&nbsp;
			<input type=button value="${ lfn:message('button.reset') }"  onclick="doReset();" class="btnopt">
		</div><br/><br/>
		<div id="contentDiv" style="display:block;">
		</div>
	</div>
</body>
