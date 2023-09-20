<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page
	import="com.landray.kmss.third.wps.model.ThirdWpsConfig"%>
<%
ThirdWpsConfig thirdWpsConfig = new ThirdWpsConfig();
String thirdWpsUrl = (String) thirdWpsConfig.getDataMap().get("thirdWpsUrl");
request.setAttribute("thirdWpsUrl", thirdWpsUrl);
%>
<script type="text/javascript">

//模拟登陆
  var $form = LUI.$("<form></form>");
      $(document.body).append($form);
      $form.attr("method", "post");
      $form.attr("action", "http://yun.test.cn/cts/v1/signin/password?_=" + new Date().getTime());
      
      $form.append($("<input type='hidden' name='username' value='_3wKVyOI_Xnd2jrHicFcpg=='></input>"));
      $form.append($("<input type='hidden' name='password' value='k-npQHuvMjGjZ1vCFHf5_g=='></input>"));
      $form.append($("<input type='hidden' name='encrypt' value='1'></input>"));
      $form.append($("<input type='hidden' name='keeponline' value='1'></input>"));
      $form.append($("<input type='hidden' name='rememberPwd' value='true'></input>"));
      $form.append($("<input type='hidden' name='csrfmiddlewaretoken' value='WnSWZCRE6rYHeER8aNtBptcQ7XtYRQiG'></input>"));
      $form.attr("target", "upload_from_return");
      $form[0].submit();

	
attachmentObject_${param.fdKey}.on("uploadSuccess",uploadWps);

function uploadWps(){
	var  file=attachmentObject_${param.fdKey}.fileList[0];
	$.ajax({
        url: "/kms/kms/wps/wps_auth/wpsAuth.do?method=createUpload",
        async: false,
        dataType: "json",
        type : "POST",
        data:{
            fileName : file.name,
            fileSize : file.size,
            method : "POST"
        },
        success: function (data) {
            if(data.code){
                alert("code:"+data.code+",message:"+data.message);
            }else{
              //  alert("创建上传事务成功:"+JSON.stringify(data));
                $("#feedback").text(data.feedback);
                $("#url").text(data.upload_requests[0].request.url);
                var form = data.upload_requests[0].request.form;
                $.each(form,function (i, val) {
                   switch (val.name) {
                       case "key":
                           $("#key").text(val.value);
                           break;
                       case "x-amz-credential":
                           $("#x-amz-credential").text(val.value);
                           break;
                       case "x-amz-date":
                           $("#x-amz-date").text(val.value);
                           break;
                       case "x-amz-algorithm":
                           $("#x-amz-algorithm").text(val.value);
                           break;
                       case "x-amz-signature":
                           $("#x-amz-signature").text(val.value);
                           break;
                       case "policy":
                           $("#policy").text(val.value);
                           break;

                   } 
                });
            }
        }
    });
}

</script>

<iframe marginwidth="0" id="wpsAddIframe" marginheight="0" width="100%" height="300"  frameborder="0"></iframe>