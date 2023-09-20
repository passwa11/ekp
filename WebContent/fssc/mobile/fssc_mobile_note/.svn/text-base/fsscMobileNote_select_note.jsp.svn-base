		<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<%@ include file="/sys/ui/jsp/common.jsp" %>
		<%@ include file="/resource/jsp/view_top.jsp" %>
		<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta charset="UTF-8">
		    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		    <meta http-equiv="X-UA-Compatible" content="ie=edge">
		    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/public.css?s_cache=${LUI_Cache }">
		    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/costDetail.css?s_cache=${LUI_Cache }">
		    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
		    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/rememberOne.css?s_cache=${LUI_Cache }" >
		    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/kk-1.2.73.min.js"></script>
		    <script src="//g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
		    <script src="${LUI_ContextPath}/fssc/common/resource/js/Number.js"></script>
		    <script>
		    	var formInitData={
			   		 'LUI_ContextPath':'${LUI_ContextPath}',
			    }
		    </script>
		     <title>未报费用</title>
		</head>
		<body>
		    <div class="ld-costDetail">
		        <div class="ld-notSubmit-head" style="z-index:99;">
		        		<div style="position:absolute;left:0.2rem;border:none;font-size:14px;">总计：${fdMoney}</div>
		            <div class="ld-notSubmit-select" style="position:absolute;right:0;border:none;" id="select" onclick="selectList()">${lfn:message('button.cancel')}</div>
		        </div>
		        <div class="ld-notSubmit-main">
		            <div class="ld-notSubmit-list">
		                <ul>
		              		<c:forEach items="${expenseNoteList.data}" var="list"  varStatus="status">
		                    <li class="ld-notSubmit-list-item"  >
		                       <label id='ld-label-${status.index}'>
		                            <div class="ld-checkBox"  style="display:block;">
		                            <input type="checkbox" name="noteDetail" value="${list.id}" />
		                            <span class="checkbox-label"></span>
		                        	</div>
		                        	<div class="ld-notSubmit-list-item-box">
		                            <div class="ld-notSubmit-list-top">
		                                <div>
		                                  	<img src="${LUI_ContextPath}/fssc/mobile/resource/images/icon/taxi.png" alt="">
		                                	<span>${list.title}</span>
		                                </div>
		                                <div>
		                                </div>
		                            </div>
		                            <div class="ld-notSubmit-list-bottom">
		                                <div class="ld-notSubmit-list-bottom-info">
		                                    <div>
		                                        <span>${list.date}</span>
		                                        <span class="ld-verticalLine"></span>
		                                         <span>${list.person}</span> 
		                                    </div>
		                                    <span id='price-${status.index}' class="price">${list.price}</span>
		                                </div>
		                                <p>${list.reason}</p>
		                            </div>
		                           </div>
		                       </label>
		                    </li> 
		                    </c:forEach>
		                </ul>
		            </div>
		        </div>
		        
		        
		         <div class="ld-create-expense">
		      		<div class="ld-costDetail-footer">
		                <div class="ld-checkBox">
		                    <input type="checkbox" id="selectAll" id="ld-selectAll" value="0"/>
		                    <label class="checkbox-label" for="ld-selectAll">全选</label>
		                </div> 
		                <div class="ld-costDetail-total">
		                    <span>合计：</span>
		                    <span id="fdMoney" ></span> 
		                </div>
		                <div class="ld-costDetail-btn"  id="expenseBtn" onclick="selectList(true)">确定</div>
		       		 </div>
		        	</div>
		    </div>
		</body>
		<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
		<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
		<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
		<script src="${LUI_ContextPath}/fssc/mobile/resource/js/dyselect.js"></script>
		<script  type="text/javascript">
		   Com_IncludeFile("index.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_main/", 'js', true);
		</script>
		<script type="text/javascript">
		
		$(function() {
			   
			    $(document).off('change',"input[name='noteDetail']").on('change',"input[name='noteDetail']",function(){ 
			    	var fdMoney = 0;
			    	var i=0;
					$("input[name='noteDetail']").each(function(){
						if(this.checked){
							var price = parseFloat($('#price-'+i).text());
							fdMoney += price;
						}
						i++;
					});
					$("#fdMoney").text(fdMoney.toFixed(2));
					if($("[name='noteDetail']:checked").length==$("[name='noteDetail']").length){  //全部选中了
						$("#selectAll").prop('checked',true);  //选中全选复选框
					}else{
						$("#selectAll").prop('checked',false);  //取消选中全选复选框
					}
		    	});
		});
		
		//全选未报费用
		$('#selectAll').on('click',function(){
		     if(this.checked) {
		          	$("input[name='noteDetail']").prop('checked',true);
					var fdMoney = 0;
					$("input[name='noteDetail']").each(function(i){
						var price = parseFloat($('.ld-notSubmit-list ul li .ld-notSubmit-list-item-box #price-'+i).text());
						fdMoney += price;
					});
					$("#fdMoney").text(fdMoney.toFixed(2));
				  //计算报销金额
		      } else {
		        $("input[name='noteDetail']").prop('checked',false);
				$("#fdMoney").text('0.00');
		      }
		});
		//重新计算未报费用列表总金额和选中金额总和
		function sumListMoney(){
			if($("#select").html()=='${lfn:message("button.cancel")}'){//若是取消，说明是选择未报费用页面，因局部刷新会将复选框隐藏，重新显示
				$(".ld-checkBox").attr('style','display: inline-block;');
				$(".del-i").hide();//隐藏单个删除按钮
			}
			var sumMoney=0.0;
			$(".price").each(function(){
				var money=$(this).html();
				if(money){
					sumMoney=numAdd(sumMoney,money);
				}
			});
			$(".ld-notSubmit-total").find('span').eq(1).html(formatFloat(sumMoney,2));
			var fdMoney = 0;
			$("input[name='noteDetail']:checked").each(function(i){
				if(this.checked){
					var price = parseFloat($('#price-'+i).text());
					fdMoney += price;
				}
			});
			$("#fdMoney").text(fdMoney.toFixed(2));
			$(".ld-notSubmit-total").find('span').eq(1).html(formatFloat(sumMoney,2));	
		}
		function selectList(flag){
			var ids = [];
			if(flag){
				$("[name=noteDetail]:checked").each(function(){
					ids.push(this.value);
				})
			}
			window.parent.selectExpense(ids.join(';'));
		}
		</script>
		</html>
