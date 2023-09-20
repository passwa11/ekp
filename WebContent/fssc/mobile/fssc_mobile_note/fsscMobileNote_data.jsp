		<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<%@ include file="/sys/ui/jsp/common.jsp" %>
		<%@ include file="/resource/jsp/view_top.jsp" %>
		<%@ include file="/fssc/mobile/resource/jsp/lang.jsp" %>
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
			<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }">
			<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }">
			<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/Mdate.css?s_cache=${LUI_Cache }">
			<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/popups.css">
			<link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/jquery.min.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/Mdate.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/iScroll.js?s_cache=${LUI_Cache }"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/dingtalk.open.js"></script>
			<script src="${LUI_ContextPath}/fssc/mobile/resource/js/popups.js"></script>
		    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/kk-1.3.10.min.js"></script>
		    <script src="//g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
		    <script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js"></script>
		    <script src="//res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
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
		        <div class="ld-notSubmit-head">
		             <div class="ld-notSubmit-head-left">
		                <ul>
		                    <li  id="dateTime" class="${expenseNoteList.dateTime}" >按时间排序</li>
		                    <li  id="expenseType" class="${expenseNoteList.expenseType}" >按类型排序</li>
		                </ul>
		            </div>
		            <div  class="ld-notSubmit-select-del" onclick="deleteNote('')">${lfn:message('button.delete')}</div>
		            <div class="ld-notSubmit-select" id="select" onclick="selectList()">${lfn:message('button.select')}</div>
		        </div>
		        <div class="ld-notSubmit-main">
		            <div class="ld-notSubmit-total">
		                <span>${lfn:message('fssc-mobile:button.total')}：</span>
		                <span>${fdMoney}</span>
		            </div>
		            <div class="ld-notSubmit-list">
		                <ul>
		                <c:if test="${not empty expenseNoteList}">
		              		<c:forEach items="${expenseNoteList.data}" var="list"  varStatus="status">
		                    <li class="ld-notSubmit-list-item note-${list.id }" >
		                       <label id='ld-label-${status.index}' >
		                            <div class="ld-checkBox">
		                            <input type="checkbox" name="noteDetail" value="${list.id}" />
		                            <span class="checkbox-label"></span>
		                        	</div>
		                        	<div class="ld-notSubmit-list-item-box" onclick="editExpenseNote('${list.id}')">
		                            <div class="ld-notSubmit-list-top">
		                                <div>
		                                  	<img src="${LUI_ContextPath}/fssc/mobile/resource/images/icon/taxi.png" alt="">
		                                	<span>${list.title}</span>
		                                </div>
		                                <div>
		                                	<i class="del-i" onclick="deleteNote('${list.id}');"></i>
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
		                    </c:if>
		                </ul>
		            </div>
		        </div>
		        
		        
		         <div class="ld-create-expense" style="display: none">
		      		<div class="ld-costDetail-footer">
		                <div class="ld-checkBox">
		                    <input type="checkbox" id="selectAll" id="ld-selectAll" value="0"/>
		                    <label class="checkbox-label" for="ld-selectAll">${lfn:message('fssc-mobile:button.selectAll')}</label>
		                </div> 
		                <div class="ld-costDetail-total">
		                    <span>${lfn:message('fssc-mobile:button.totalAmount')}：</span>
		                    <span id="fdMoney" ></span> 
		                </div>
		                <div class="ld-costDetail-btn"  id="expenseBtn" >${lfn:message('fssc-mobile:button.createExpense')}</div>
		       		 </div>
		        	</div>
		        	
		       <div class="addOne"  onclick="rememberOne();"></div>
		       <div class="backHome"  onclick="backToHome();"></div>
		    </div>
		    
		     <div class="ld-remember-modal" >
		        <div class="ld-remember-modal-main">
		           <ul>
		               <c:forEach items="${createObj}" var="list">
		           		<c:if test="${list.type =='manual'}">
		               <li>
		                   <div onclick="rememberNoteByHand();" style="width:100%;">
		                      <span class="ld-manual-invoice"></span>
		                       <div class="ld-remember-modal-text" >
		                           <p>${lfn:message('fssc-mobile:addByHand')}</p>
                           			<p>${lfn:message('fssc-mobile:addByHand.tips')}</p>
		                       </div>
		                   </div>
		                   <i></i>
		               </li>
		               </c:if>
		               <c:if test="${list.type =='qrcode'}">
		                <li>
		                   <div  onclick="scanQrCode();" style="width:100%;">
		                       <span class="ld-saomiao-invoice"></span>
		                       <div class="ld-remember-modal-text">
		                            <p>${lfn:message('fssc-mobile:fssc.mobile.index.scanCode')}</p>
                           			<p>${lfn:message('fssc-mobile:fssc.mobile.index.scanCode.tips')}</p>
		                       </div>
		                   </div>
		                    <i></i>
		               </li>
		               </c:if>
		               <c:if test="${list.type =='photo'}">
		                <li>
		                   <div  id="getPhoto" style="width:100%;">	
		                        <span class="ld-picture-invoice"></span>
		                        <div class="ld-remember-modal-text" >
		                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.photo')}</p>
                           			<p>${lfn:message('fssc-mobile:fssc.mobile.index.photo.tips')}</p>
		                        </div>
		                   </div>
		                   <i></i> 
		                   <!-- <input id="file" class="js_upFile cover1" type="file" name="cover" accept="image/*"  capture="camera" multiple/> -->
		               </li>
		               </c:if>
		               <c:if test="${list.type =='wxcard'}">
		               <li id="weixin">
		                   <div onclick="chooseByWeixin();" style="width:100%;">
		                       <span class="ld-saomiao-invoice"></span>
		                       <div class="ld-remember-modal-text">
		                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.wxcard')}</p>
                           			<p>${lfn:message('fssc-mobile:fssc.mobile.index.tips')}</p>
		                       </div>
		                   </div>
		                   <i></i>
		               </li>
		               </c:if>
		                <c:if test="${list.type =='picture'}">
		               </c:if> 
		               </c:forEach>
		               <kmss:ifModuleExist path="/fssc/didi">
		               <li id="didi">
		                   <div onclick="chooseDidiTravel();">
		                       <span class="ld-select-travel"></span>
		                       <div class="ld-remember-modal-text">
		                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.didi')}</p>
                           			<p>${lfn:message('fssc-mobile:fssc.mobile.index.didi.tips')}</p>
		                       </div>
		                   </div>
		                   <i></i>
		               </li>
		               </kmss:ifModuleExist>
		               
		           </ul>
		           <div class="ld-remember-modal-cancel">${lfn:message('button.cancel')}</div>
		        </div>
		    </div>
		    
		   <!-- 报销单类别选择 -->
		    <div class="select_box select_box"></div>
		    
		    <!-- 选择相机 -->
		    <div class="ld-turnUpCamera" id="ld-turnUpCamera" style="display: none" >
		      <div class="ld-turnUpCamera-modal">
		        <div class="ld-turnUpCamera-modal-content">
		            <div class="ld-turnUpCamera-modal-content-list">
		                <div onclick="getPhoto('camera')">
		                  <span>照相或录像</span>
		                  <img src="${LUI_ContextPath}/fssc/mobile/resource/images/turnUpCamera.png" alt="">
		                </div>
		                <div onclick="getPhoto('album')">
		                  <span>照片图库</span>
		                  <img src="${LUI_ContextPath}/fssc/mobile/resource/images/gallery.png" alt="">
		                </div>
		            </div>
		            <div class="ld-turnUpCamera-modal-content-btn">
		             	 取消
		            </div>
		        </div>
		      </div>
		    </div>
		    <!-- 上传中 -->   
		     <div class="ld-main" id="ld-main-upload" style="display: none;">
		        <div class="ld-mask">
		            <div class="ld-progress-modal">
		                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
		                <span>${lfn:message('fssc-mobile:fssc.mobile.list.uploading')}</span>
		            </div>
		        </div>
		    </div>
		    <!-- 识别中 -->
		     <div class="ld-main" id="ld-main" style="display: none;">
		        <div class="ld-mask">
		            <div class="ld-progress-modal">
		                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
		                <span>识别中...</span>
		            </div>
		        </div>
		    </div>
		    <div class="didi-order-list">
    	<div class="ld-costDetail">
		        <div class="ld-notSubmit-main">
		            <div class="ld-notSubmit-list">
		                <ul>
		                </ul>
		            </div>
		        </div>
		        
		        <div class="ld-didi-check" style="display: none">
		      		<div class="ld-costDetail-footer">
		                <div class="ld-checkBox">
		                    <input type="checkbox" id="selectAll" id="ld-selectAll" value="0"/>
		                    <label class="checkbox-label" for="ld-selectAll">全选</label>
		                </div> 
		                <div class="ld-costDetail-btn-cancel"  onclick="cancelSelectDidi()">${lfn:message('button.cancel')}</div>
		                <div class="ld-costDetail-btn"  onclick="confirmSelectDidi()">确定</div>
		       		 </div>
		        	</div>
		        	
		    </div>
    </div>
		</body>
		<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
		<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
		<fssc:checkVersion version="true">
	    	<input type="hidden" value="true" name="version" />
	    </fssc:checkVersion>
		<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
		<script src="${LUI_ContextPath}/fssc/mobile/resource/js/dyselect.js"></script>
		<script  type="text/javascript">
		   Com_IncludeFile("index.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_main/", 'js', true);
		</script>
		<script type="text/javascript">
		
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
			    	 var noteIds = "";
			        	$("input[name='noteDetail']:checkbox:checked").each(function(){
			        		noteIds +=$(this).val()+";";
						});
			        	window.open("${LUI_ContextPath}/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=add&docTemplate="+selectedVal+"&noteIds="+noteIds, '_self');
		
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
			   	   });
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
			    $('.ld-notSubmit-list-item').attr('disabled', false);
			    $(document).off('change',"input[name='noteDetail']").on('change',"input[name='noteDetail']",function(){ 
			    	var fdMoney = 0;
			    	var i=0;
					$("input[name='noteDetail']").each(function(){
						if(this.checked){
							var price = parseFloat($(this).closest("li").find(".price").text());
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
		
		//选择
		function selectList(){
			//切换选择取消，取消选中的复选框和全选
	    	$("input[name='noteDetail']").prop('checked',false);
	    	$("#selectAll").prop('checked',false);  //取消选中全选复选框
	    if( $('#select').html()=="${lfn:message('button.select')}"){
	        $('#select').html("${lfn:message('button.cancel')}");
	        $('.ld-notSubmit-list ul li .ld-checkBox').css('display','inline-block');
	        $('.ld-create-expense').css('display','inline-block');
	        $('.ld-notSubmit-select-del').css('display','inline-block') ;
	        $(".del-i").hide();//隐藏单个删除按钮
	        $(".addOne,.backHome").addClass("addOne-hidden");
	    }else{
		    	$('#select').html("${lfn:message('button.select')}");
		    	$('.ld-notSubmit-list ul li .ld-checkBox').css('display','none');
		    	$('.ld-create-expense').css('display','none');
		    	$('.ld-notSubmit-select-del').css('display','none') ;
		    	$(".del-i").show();//显示单个删除按钮
		    	$(".addOne,.backHome").removeClass("addOne-hidden");
		   }
		}
		
		//全选未报费用
		$('#selectAll').on('click',function(){
		     if(this.checked) {
		          	$("input[name='noteDetail']").prop('checked',true);
					var fdMoney = 0;
					$(".ld-notSubmit-list-item .price").each(function(i){
						var price = parseFloat($(this).text());
						fdMoney += price;
					});
					$("#fdMoney").text(fdMoney.toFixed(2));
				  //计算报销金额
		      } else {
		        $("input[name='noteDetail']").prop('checked',false);
				$("#fdMoney").text('0.00');
		      }
		});
		
		//编辑
		function editExpenseNote(id){
			var event = event ? event : window.event;
			var obj = event.srcElement ? event.srcElement : event.target;
			if(obj.tagName=='i'||obj.tagName=='I'||obj.tagName=='input'||obj.tagName=='INPUT'){
				return ; //阻止冒泡
			}
			if(id && $('#select').html()=="${lfn:message('button.select')}"){
				 window.location.href="${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=edit&fdId="+id;
		    }
		}
		
		//删除
		function deleteNote(id){
			var e = e||window.event;
	    	var ele = e.srcElement||e.target;
	    	e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
			var noteIds = "";
			if(!id){
				//批量选择 
		    		$("input[name='noteDetail']:checkbox:checked").each(function(){
		    			noteIds +=$(this).val()+";";
				});
			}else{
				noteIds = id;
			}
			if(noteIds.length==0){
				jqtoast("请选择记录后再操作")
				return
			}
			jqalert({
	            title:'提示',
	            content:'确定要删除费用？',
	            yestext:'确认',
	            notext:'取消',
	            yesfn:function () {
		            	$.ajax({
		    		        type: 'post',
		    		        url:'${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=deleteNote',
		    		        data: {"ids":noteIds},
		    		    }).success(function (data) {
		    				var rtn = JSON.parse(data);
		    				if(rtn.result =="success"){
		    				jqtoast("删除成功");
		    				$(noteIds.split(';')).each(function(){
		    					$(".note-"+this).remove();
		    				});
		    				var total = 0;
		    				$(".ld-notSubmit-list-item .price").each(function(){
		    					total= numAdd(parseFloat(this.innerHTML),total);
		    				})
		    				$(".ld-notSubmit-total>span:eq(1)").html(total.toFixed(2));
		    				$(".ld-costDetail-total>span:eq(1)").html("0.00"); 
		    			}else{
		    				jqtoast("删除失败");
		    		 	   }
		    		    }).error(function (data) {
		    		    	jqtoast("删除失败");
		    		    })
	            },
	            	nofn:function () {
	            }
	        })
		}
		
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
		
		//排序
		$('#expenseType').click(function(){
			var orderby = Com_GetUrlParameter(window.location.href,'orderby');
			orderby = orderby=='asc'?'desc':'asc';
			 window.location.href="${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data&keyword=expenseType&orderby="+orderby;
		});
		$('#dateTime').click(function(){
			var orderby = Com_GetUrlParameter(window.location.href,'orderby');
			orderby = orderby=='asc'?'desc':'asc';
			window.location.href="${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data&keyword=dateTime&orderby="+orderby;
		});
		
		function backToHome(){
			window.location.href=Com_Parameter.ContextPath+'fssc/mobile/index.jsp';
		}
		</script>
		</html>
