<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
          //隐藏和显示操作       
	window.showAndHide=function(){
            var classValue = $("#showOperation").attr("class");
            if(classValue == "list_up_icon"){
         	   $('#infoDiv').slideUp();
         	   $("#showOperation").attr("class","list_down_icon");
             }else{
                $('#infoDiv').slideDown();
                $("#showOperation").attr("class","list_up_icon");
             }
    };

    window.openURL = function(id){
    	var mode = LUI.pageMode();
    	var url = "${LUI_ContextPath}/km/forum/indexCriteria.jsp?&timestamp="+new Date().getTime()+"&mode="+mode+"&categoryId="+id;
    	LUI.pageOpen(url,'_iframe');
    };
</script>