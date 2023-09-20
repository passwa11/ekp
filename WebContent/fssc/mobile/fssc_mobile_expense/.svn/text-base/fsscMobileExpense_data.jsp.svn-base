<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/applicationFormList.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/public.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/dyselect.js"></script>
    <title>报销列表</title>
</head>
<body>
    <div class="ld-application-form-list">
        <ul>
	        	<c:forEach items="${expenseMainList}" var="list"  varStatus="status">
             	<li onclick="showExpense('${list.id}')">
                <div class="ld-application-form-list-item-top">
                    <div style="width:60%;word-break: break-all;">
                    <c:if test="${fn:length(list.title)>40 }">${fn:substring(list.title,0,37)}...</c:if>
                    <c:if test="${fn:length(list.title)<=40 }">${list.title }</c:if>
                       
                    </div>
                     <div style="width:40%;text-align:right;">
                        ${list.count}
                    </div>
                </div>
                <div class="ld-application-form-list-item-time">
                      <div style="width:40%;float:left;">${list.date}</div>
                      <div style="width:40%;text-align:right;float:right;">
                      	<span class="ld-list-status ld-list-status-${list.clazz}">${list.status}</span>
                      </div>
                </div>
              </li>
            </c:forEach>
        </ul>
         <div class="create—expense-btn" id="expenseBtn" ></div>
         <div class="backHome" id="expenseBtn" onclick="backToHome()"></div>
    </div>
     <!-- 报销单类别选择 -->
    <div class="select_box select_box"></div>
    
<script type="text/javascript">
function backToHome(){
	window.location.href='${LUI_ContextPath}/fssc/mobile/index.jsp'
}
$(function() {
    var template='${expenseCastlist}'; 
    if(template){
    	template=JSON.parse(template);
    }
    console.log(template);
 	 var picker = new Picker({
        data:[template]
     });
     picker.on('picker.select', function (selectedVal, selectedIndex) {
         if(selectedVal==""){
             jqtoast("没有报销分类!")
             return
         }
     	window.open("${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=add&docTemplate="+selectedVal, '_self');
     });
      picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
    	console.log(selectedVal);
      });
	  var expenseBtnElement = document.getElementById('expenseBtn');
	   expenseBtnElement.addEventListener('click', function () {
		   picker.show();
      });
	   
	   $("#createExpense").click(function (e) {
       	$("[name='pick_keyword']").val('');
       	$.ajax({
	           type: 'post',
	           url:'${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=findExpenseTemplate',
	           data: {"keyword":''},
	       }).success(function (data) {
	    	   console.log('获取信息成功');
	    	   var rtn = JSON.parse(data);
	    	   console.log(rtn);
	    	   picker.refillColumn(0, rtn.data);
	       }).error(function (data) {
	    	   console.log('获取信息失败');
	       })
     	});
	   
       //回车搜索
       $("#search_input").keypress(function (e) {
           if (e.which == 13) {
           	var keyword=$("[name='pick_keyword']").val();
           	if(keyword){
           		$.ajax({
           	           type: 'post',
           	           url:'${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=findExpenseTemplate',
           	           data: {"keyword":keyword},
           	       }).success(function (data) {
           	    	   console.log('获取信息成功');
           	    	   var rtn = JSON.parse(data);
           	    	   if(rtn.result=='success'){
           	    		 picker.refillColumn(0, rtn.data);
           	    	   }
           	       }).error(function (data) {
           	    	   console.log('获取信息失败');
           	       })
           	  }
           }
   	  });
        //获取到焦点
       $("#search_input").focus(function(){
   		$(".weui-icon-clear").attr("style","display:block;");
   	   }) 
       $(".weui-icon-clear").click(function (e) {
       	$.ajax({
	           type: 'post',
	           url:'${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=findExpenseTemplate',
	           data: {"keyword":''},
	       }).success(function (data) {
	    	   console.log('获取分类信息成功');
	    	   var rtn = JSON.parse(data);
	    	   picker.refillColumn(0, rtn.data);
	    	  $("[name='pick_keyword']").val('');
	       }).error(function (data) {
	    	   console.log('获取分类信息失败');
	       })
   	}); 
});


function showExpense(fdId){
	window.location.href="${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=view&fdId="+fdId;
}


</script>
</body>
</html>

