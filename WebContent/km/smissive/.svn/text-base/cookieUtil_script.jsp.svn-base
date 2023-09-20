<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
function getValueFromCookie(key){
	var strCookie=document.cookie; 
	//console.log(strCookie);
	var arrCookie=strCookie.split("; ");
	var value; 
	for(var i=0;i<arrCookie.length;i++){ 
	  var arr=arrCookie[i].split("=");
		if(key==arr[0]){
		   value=arr[1];
		   break;
	  } 
   }
 return value;
}
function getValueFromParentCookie(key){
	var strCookie=parent.document.cookie; 
	//console.log(strCookie);
	var arrCookie=strCookie.split("; ");
	var value; 
	for(var i=0;i<arrCookie.length;i++){ 
	  var arr=arrCookie[i].split("=");
		if(key==arr[0]){
		   value=arr[1];
		   break;
	  } 
   }
 return value;
}
function delCookie(){
    var exp = new Date(); 
    exp.setTime(exp.getTime()-1);
    var strCookie=document.cookie; 
	var arrCookie=strCookie.split(";"); 
    for(var i=0;i<arrCookie.length;i++){ 
  	  var arr=arrCookie[i].split("="); 
  	  var cval=getValueFromCookie(arr[0]); 
      if(cval!=null)
       document.cookie= (arr[0] + "="+cval+";expires="+exp.toGMTString()); 
   }
}  
function delCookieByName(name){
    var exp = new Date(); 
    exp.setTime(exp.getTime()-1);
	var cval=getValueFromCookie(name); 
    if(cval!=null)
       document.cookie= (name + "="+cval+";expires="+exp.toGMTString()); 
}


function getTempNumberFromDb(fdNumberId){
	var docNum = "";
	var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_number/kmSmissiveNumber.do?method=getTempNumFromDb"; 
	 $.ajax({     
	     type:"post",    
	     url:url,   
	     data:{fdNumberId:fdNumberId},
	     async:false,    //用同步方式
		 dataType:"json",
		 success:function(results){
		    if(results['docNum']!=null){
		    	docNum = results['docNum'];
			}
		}   
   });
	return  docNum;
}

function delTempNumFromDb(fdNumberId,docBufferNum){
	var flag = false;
	var docNum = "";
	var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_number/kmSmissiveNumber.do?method=delTempNumFromDb"; 
	 $.ajax({     
	     type:"post",    
	     url:url,   
	     data:{fdNumberId:fdNumberId,docBufferNum:docBufferNum},
	     async:false,    //用同步方式 
	     success:function(data){
	    	flag = true;
		}   
   });
	 return flag;
}


</script>
