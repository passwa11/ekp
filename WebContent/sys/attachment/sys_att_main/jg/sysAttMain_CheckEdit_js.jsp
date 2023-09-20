<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script>
/***********************************************
  功能  判断是否只有当前用户在线编辑
 ***********************************************/
 function isFirst(fdId,fdModelId,fdModelName,fdKey) {
  	 var flag;
	 var url = Com_GetCurDnsHost() + Com_Parameter.ContextPath+"sys/attachment/sys_att_main/jg_service.jsp?method=isFirst&_addition=1";
	 $.ajax({   
	     type:"post",     
	     url:url,     
	     data:{fdId:fdId,fdModelId:fdModelId,fdModelName:fdModelName,fdKey:fdKey},    
	     async:false,
	     success:function(data){ 
	    	 var xml = JSON.parse(data);
			 	if(xml.isEdit == "0"){
			 		var $tip = $('<div>文档正在被'+xml.editOrgName+'编辑，以只读方式打开</div>').appendTo($("#warnDiv")).addClass('onlineEditTip');
			 		//alert("文档正在被"+xml.editOrgName+"编辑"+",以只读方式打开");
			 		//不是第一编辑人，控制为只读状态
			 		 flag =  false;                 
			 	}else if(xml.isEdit == "1"){
			 		//是第一编辑人，控制可编辑状态
			 		 flag = true;                        
			 	}
		   }     
      });
     return flag;
}
 
/***********************************************
	 功能  清除当前在线编辑用户信息
	 ***********************************************/
function clearEdit(fdId,fdModelId,fdModelName,fdKey) {
		 var url = Com_GetCurDnsHost() + Com_Parameter.ContextPath+"sys/attachment/sys_att_main/jg_service.jsp?method=clearEdit&_addition=1";
		 $.ajax({   
		     type:"post", 
		     url:url,     
		     data:{fdId:fdId,fdModelId:fdModelId,fdModelName:fdModelName,fdKey:fdKey},    
		     async:true,
		     success:function(data){
			  }  
	      });
}
	 
/***
* 更新时间标识
*/
function updateTime(fdId,fdModelId,fdModelName,fdKey){
	 var url = Com_GetCurDnsHost() + Com_Parameter.ContextPath+"sys/attachment/sys_att_main/jg_service.jsp?method=updateTime&_addition=1";
	 $.ajax({   
	     type:"post",     
	     url:url,     
	     data:{fdId:fdId,fdModelId:fdModelId,fdModelName:fdModelName,fdKey:fdKey},    
	     async:false,
	     success:function(data){}     
    });
}
</script>