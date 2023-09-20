<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script type="text/javascript">
	var praOpened = "false";//点赞人员列表框是否显示

	function initPraise(){
		$("span[praise-data-modelid]").on("mouseenter",function(praiseObj){
				closeListBox(praiseObj);
				if(praOpened == "false"){
					praOpened = "true";
					var _praiseId = $(praiseObj.currentTarget).attr("praise-data-modelid");
					$("#aid_"+_praiseId+" #praisedPerson_list").css("height","0px");
					var hasPraiser = getPraisers(_praiseId,1,false,false);
					if(hasPraiser == true){
						$(praiseObj.currentTarget).parent().find("div[class='lui_praise_layer']").show();
					}
				}
		});

		$("span[praise-data-modelid]").on("mouseleave",function(obj){ 
			if(praOpened == "true"){
				praOpened = "false";
			}
			setTimeout(function() {
				if(praOpened == "false"){
					$(obj.currentTarget).parent().find("div[class='lui_praise_layer']").hide();
				}
	    	}, 1000);
		});
	}
	
	//点赞
	function sysPraise(praiseId){
		var praiseObj = $("#aid_"+praiseId);
		var fdModelId = praiseObj.attr("praise-data-modelid");
		var fdModelName = praiseObj.attr("praise-data-modelname");
		LUI.$.ajax({
			type : "POST",
			url :  "<c:url value='/sys/praise/sys_praise_main/sysPraiseMain.do?method=executePraise'/>",
			data: {fdModelId: fdModelId,
				   fdModelName: fdModelName},
			dataType : 'text',
			async: false,
			success : function(data) {
					var praiseCount = parseInt($("#aid_"+praiseId+' #praise_count')[0].innerHTML);
					var isPraised = $("#check_"+praiseId).val();
					if("true" == isPraised){
						$("#check_"+praiseId).val("false");
						var totalCount = praiseCount - 1;
						if(totalCount < 0){
							totalCount = 0;
						}
						$("#aid_"+praiseId+' #praise_count')[0].innerHTML = totalCount;

						$("#aid_"+praiseId+' #praise_icon').removeClass("sys_unpraise").addClass("sys_praise");
						$("#aid_"+praiseId+' #praise_icon').attr("title","${ lfn:message('sys-praise:sysPraiseMain.praise')}");
					}else{
						$("#check_"+praiseId).val("true");
						var _totalCount = praiseCount + 1;
						if(_totalCount < 0){
							_totalCount = 0;
						}
						$("#aid_"+praiseId+' #praise_count')[0].innerHTML = _totalCount;

						$("#aid_"+praiseId+' #praise_icon').removeClass("sys_praise").addClass("sys_unpraise");
						$("#aid_"+praiseId+' #praise_icon').attr("title","${ lfn:message('sys-praise:sysPraiseMain.cancel.praise')}");
					}
			},
			error: function() {
				
			}		
		});
	}

	//列出所有点赞人
	function listAllPraiser(praiseId){
		var personListBox = $("#aid_"+praiseId+" #praisedPerson_list");
		praOpened = "";//不为true也不为false
		personListBox.empty();
		personListBox.animate({height:"80px"},"normal",function(){
			$("#aid_"+praiseId+" #praise_page_list").show();
			$("#aid_"+praiseId+" #praise_close").show();
			getPraisers(praiseId,1,true,true);
		});
	}

	//根据当前事件，选择性关闭人员列表框
	function closeListBox(evt){
	//	debugger;
		if($(evt.target).length>0){
			var targetId = $(evt.target)[0].id;//“更多”按钮
			var closeBtn = $(evt.target)[0].className;//人员列表框关闭按钮
			var clickObj = $(evt.target).parents(".lui_praise_layer").length;
			//当点击的区域为人员列表框外，则隐藏列表框
			if((targetId!="show_more" && clickObj<=0) || closeBtn == "lui_praise_close_s"){
				praOpened = "false";
				$(".lui_praise_layer").hide();
				//$('.person_list').attr({style:"height:35px;"});
				$(".praise_page_list").hide();//翻页
				$(".lui_praise_close_d").hide();//关闭按钮
			}
		}
	}
	
	//翻页列出点赞人
	function getPraisers(praiseId,_pageno,_isShowAll,_isShowPage){
		var _praiseObj = $("#aid_"+praiseId);
		var fdModelId = _praiseObj.attr("praise-data-modelid");
		var fdModelName = _praiseObj.attr("praise-data-modelname");
		var _showPraiserCount = $("input[name='showPraiserCount']").val();
		
		var hasPraiser = true;
		if(_showPraiserCount==""){
			_showPraiserCount = '3';
		}
		LUI.$.ajax({
			type : "POST",
			url :  "<c:url value='/sys/praise/sys_praise_main/sysPraiseMain.do?method=getPraisedPersons'/>",
			data: {fdModelId: fdModelId,
				   fdModelName: fdModelName,
				   pageno:_pageno,
				   isShowAll:_isShowAll,
				   isShowPage:_isShowPage,
				   showPraiserCount:_showPraiserCount},
			dataType : 'json',
			async: false,
			success : function(data) {
					//隐藏踩的人员列表
					$(document).find("div[class='lui_negative_layer']").hide();	
					$("#praisedPerson_list").empty();
					//翻页
					if(_isShowPage){
						var nextPage = data['nextPage'];
						var prePage = data['prePage'];
						if(prePage){
							$("#aid_"+praiseId+" #btn_preno").parent().show();
							$("#aid_"+praiseId+" #btn_nextno").parent().show();
							if(nextPage){
								$("#aid_"+praiseId+" #btn_preno").attr("class","praise_icon_l");
								$("#aid_"+praiseId+" #btn_preno").attr("data-praise-mark","1");

								$("#aid_"+praiseId+" #btn_nextno").attr("class","praise_icon_r");
								$("#aid_"+praiseId+" #btn_nextno").attr("data-praise-mark","1");
							}else{
								$("#aid_"+praiseId+" #btn_preno").attr("class","praise_icon_l");
								$("#aid_"+praiseId+" #btn_preno").attr("data-praise-mark","1");

								$("#aid_"+praiseId+" #btn_nextno").attr("class","praise_icon_r_gray");
								$("#aid_"+praiseId+" #btn_nextno").attr("data-praise-mark","0");
							}
						}else{
							if(nextPage){
								$("#aid_"+praiseId+" #btn_preno").parent().show();
								$("#aid_"+praiseId+" #btn_nextno").parent().show();
								
								$("#aid_"+praiseId+" #btn_preno").attr("class","praise_icon_l_gray");
								$("#aid_"+praiseId+" #btn_preno").attr("data-praise-mark","0");

								$("#aid_"+praiseId+" #btn_nextno").attr("class","praise_icon_r");
								$("#aid_"+praiseId+" #btn_nextno").attr("data-praise-mark","1");
							}else{
								$("#aid_"+praiseId+" #praise_page_list").hide();
							}
						}
						
					}
					
					var personList = data['personsList'];
					$("#aid_"+praiseId+" #praisedPerson_list").empty();
					var _plist = "";
					for(var i=0;i<personList.length;i++){
						_plist += "<li><a href='"+Com_Parameter.ContextPath+
								"sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId="+personList[i].personId+
								"' target='_blank'><img title='"+personList[i].personName+
								"' width='30' height='30' src='"+ personList[i].imgUrl +"'></a></li>";
					}
					$("#aid_"+praiseId+" #praisedPerson_list").html(_plist);
					//更多
					if(data['hasMore'])
						$("#aid_"+praiseId+" #praisedPerson_list").append("<li><a><div id='show_more' class='praise_more' onclick='listAllPraiser(\""+praiseId+"\")'>"+
												"</div></a></li>");
					//没人点赞过
					if(personList.length <= 0){
						hasPraiser = false;
					}
			},
			error: function() {
				hasPraiser = false;
			}		
		});
		return hasPraiser;
	}
	//翻页(点赞)
	var pageno = 1;
	//上一页
	function prePage(objId){
		var data_mark = $("#aid_"+objId+" #btn_preno").attr("data-praise-mark");
		if(data_mark == 1){
			getPraisers(objId,--pageno,true,true);
		}
	}
	//下一页
	function nextPage(objId){
		var data_mark = $("#aid_"+objId+" #btn_nextno").attr("data-praise-mark");
		if(data_mark == 1){
			getPraisers(objId,++pageno,true,true);
		}
	}

	function updatePraiseAndNegativeStatus(praiseModelIds,fdModelName){
		//对已赞(踩)过的换用“已赞（踩）”图标
		var fdModelIds = praiseModelIds.join(",");
		LUI.$.ajax({
			type : "POST",
			url :  Com_Parameter.ContextPath + "sys/praise/sys_praise_main/sysPraiseMain.do?method=checkPraiseAndNegativeByIds",
			data: {fdModelIds: fdModelIds,
				   fdModelName: fdModelName},
			dataType : 'json',
			async: false,
			success : function(data) {
					for(var i=0;i<data["praiseIds"].length;i++){
						var praisedId = data["praiseIds"][i];
						$("#check_"+praisedId).val("true");
						$("span[data-lui-id='"+praisedId+"'] #praise_icon").attr("class","sys_unpraise");
						$("span[data-lui-id='"+praisedId+"'] #praise_icon").attr("title","${lfn:message('sys-evaluation:sysEvaluation.cancel.praise')}");
					}
					for(var a=0;a<data["negativeIds"].length;a++){
						var negativeId=data["negativeIds"][a];
						$("#checkNegative_"+negativeId).val("true");
						$("span[data-lui-id='"+negativeId+"'] #negative_icon").attr("class","sys_negative");
						$("span[data-lui-id='"+negativeId+"'] #negative_icon").attr("title","${lfn:message('sys-praise:sysPraiseMain.cancel.negative')}");
						}
			},
			error: function() {
				
			}	
		});
	}
</script>
<script type="text/javascript">

	function sysPraiseNegative(negativeId){
		var praiseObj = $("#aidNegative_"+negativeId);
		var fdModelId = praiseObj.attr("negative-data-modelid");
		var fdModelName = praiseObj.attr("negative-data-modelname");
		LUI.$.ajax({
			type : "POST",
			url :  "<c:url value='/sys/praise/sys_praise_main/sysPraiseMain.do?method=executePraise&fdType=negative'/>",
			data: {fdModelId: fdModelId,
				   fdModelName: fdModelName},
			dataType : 'text',
			async: false,
			success : function(data) {
					   var negativeCount = parseInt($("#aidNegative_"+negativeId+' #negative_count')[0].innerHTML);
					   var isNegative = $("#checkNegative_"+negativeId).val();
					   if("true" == isNegative){
							$("#checkNegative_"+negativeId).val("false");
							$("#aidNegative_"+negativeId+' #negative_count')[0].innerHTML = negativeCount - 1;

							$("#aidNegative_"+negativeId+' #negative_icon').removeClass("sys_negative").addClass("sys_unNegative");
							$("#aidNegative_"+negativeId+' #negative_icon').attr("title","${ lfn:message('sys-praise:sysPraiseMain.negative')}");
						}else{
							$("#checkNegative_"+negativeId).val("true");
							$("#aidNegative_"+negativeId+' #negative_count')[0].innerHTML = negativeCount + 1;

							$("#aidNegative_"+negativeId+' #negative_icon').removeClass("sys_unNegative").addClass("sys_negative");
							$("#aidNegative_"+negativeId+' #negative_icon').attr("title","${ lfn:message('sys-praise:sysPraiseMain.cancel.negative')}");
						}
				   },
			error:function(data) {
			   }
			});
		}
</script>
<script>
var negaOpened = "false";//点赞人员列表框是否显示
//列出所有踩的人
function listAllNegative(negativeId){
	var personListBox = $("#aidNegative_"+negativeId+" #negativePerson_list");
	negaOpened = "";//不为true也不为false
	personListBox.empty();
	personListBox.animate({height:"80px"},"normal",function(){
		$("#aidNegative_"+negativeId+" #negative_page_list").show();
		$("#aidNegative_"+negativeId+" #negative_close").show();
		getNegatives(negativeId,1,true,true);
	});
}

//翻页列出踩的人
 function getNegatives(negativeId,_pageno,_isShowAll,_isShowPage){
	 var _negativeObj = $("#aidNegative_"+negativeId);
		var fdModelId = _negativeObj.attr("negative-data-modelid");
		var fdModelName = _negativeObj.attr("negative-data-modelname");
		var _showNegativeCount = $("input[name='showNegativeCount']").val();
		
		var hasNegative = true;
		if(_showNegativeCount==""){
			_showNegativeCount = '3';
		}
		LUI.$.ajax({
			type : "POST",
			url :  "<c:url value='/sys/praise/sys_praise_main/sysPraiseMain.do?method=getPraisedPersons&fdType=negative'/>",
			data: {fdModelId: fdModelId,
				   fdModelName: fdModelName,
				   pageno:_pageno,
				   isShowAll:_isShowAll,
				   isShowPage:_isShowPage,
				   showPraiserCount:_showNegativeCount},
				   dataType : 'json',
					async: false,
					success : function(data) {
					 //隐藏 赞的人员列表
					   $(document).find("div[class='lui_praise_layer']").hide();
					   $("#negativePerson_list").empty();
					 //翻页
						if(_isShowPage){
							var nextPage = data['nextPage'];
							var prePage = data['prePage'];
							if(prePage){
								$("#aidNegative_"+negativeId+" #btn_negative_preno").parent().show();
								$("#aidNegative_"+negativeId+" #btn_negative_nextno").parent().show();
								if(nextPage){
									$("#aidNegative_"+negativeId+" #btn_negative_preno").attr("class","negative_icon_l");
									$("#aidNegative_"+negativeId+" #btn_negative_preno").attr("data-negative-mark","1");

									$("#aidNegative_"+negativeId+" #btn_negative_nextno").attr("class","negative_icon_r");
									$("#aidNegative_"+negativeId+" #btn_negative_nextno").attr("data-negative-mark","1");
								}else{
									$("#aidNegative_"+negativeId+" #btn_negative_preno").attr("class","negative_icon_l");
									$("#aidNegative_"+negativeId+" #btn_negative_preno").attr("data-negative-mark","1");

									$("#aidNegative_"+negativeId+" #btn_negative_nextno").attr("class","negative_icon_r_gray");
									$("#aidNegative_"+negativeId+" #btn_negative_nextno").attr("data-negative-mark","0");
								}
							}else{
								if(nextPage){
									$("#aidNegative_"+negativeId+" #btn_negative_preno").parent().show();
									$("#aidNegative_"+negativeId+" #btn_negative_nextno").parent().show();
									
									$("#aidNegative_"+negativeId+" #btn_negative_preno").attr("class","negative_icon_l_gray");
									$("#aidNegative_"+negativeId+" #btn_negative_preno").attr("data-negative-mark","0");

									$("#aidNegative_"+negativeId+" #btn_negative_nextno").attr("class","negative_icon_r");
									$("#aidNegative_"+negativeId+" #btn_negative_nextno").attr("data-negative-mark","1");
								}else{
									$("#aidNegative_"+negativeId+" #negative_page_list").hide();
								}
							}
							
						}
						var personList = data['personsList'];
						$("#aidNegative_"+negativeId+" #negativePerson_list").empty();
						var _plist = "";
						for(var i=0;i<personList.length;i++){
							_plist += "<li><a href='"+Com_Parameter.ContextPath+
									"sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId="+personList[i].personId+
									"' target='_blank'><img title='"+personList[i].personName+
									"' width='30' height='30' src='"+ personList[i].imgUrl +"'></a></li>";
						}
						$("#aidNegative_"+negativeId+" #negativePerson_list").html(_plist);
						//更多
						if(data['hasMore'])
							$("#aidNegative_"+negativeId+" #negativePerson_list").append("<li><a><div id='show_more' class='praise_more' onclick='listAllNegative(\""+negativeId+"\")'>"+
													"</div></a></li>");
						//没人点赞过
						if(personList.length <= 0){
							hasNegative = false;
						}
					},
					error: function() {
						hasNegative = false;
					}	
			});
		return hasNegative;
}

//根据当前事件，选择性关闭人员列表框
function closeNegativeListBox(evt){
	//debugger;
	if($(evt.target).length>0){
		var targetId = $(evt.target)[0].id;//“更多”按钮
		var closeBtn = $(evt.target)[0].className;//人员列表框关闭按钮
		var clickObj = $(evt.target).parents(".lui_negative_layer").length;
		//当点击的区域为人员列表框外，则隐藏列表框
		if((targetId!="show_more" && clickObj<=0) || closeBtn == "lui_negative_close_s"){
			negaOpened = "false";
			$(".lui_negative_layer").hide();
			//$('.person_list').attr({style:"height:35px;"});
			$(".negative_page_list").hide();//翻页
			$(".lui_negative_close_d").hide();//关闭按钮
		}
	}
}

function initNegative(){
//	debugger;
	$("span[negative-data-modelid]").on("mouseenter",function(negativeObj){
		closeNegativeListBox(negativeObj);
			if(negaOpened == "false"){
				negaOpened = "true";
				var _negativeId = $(negativeObj.currentTarget).attr("negative-data-modelid");
				$("#aidNegative_"+_negativeId+" #negativePerson_list").css("height","0px");
				var hasNegative = getNegatives(_negativeId,1,false,false);
				if(hasNegative == true){
					$(negativeObj.currentTarget).parent().find("div[class='lui_negative_layer']").show();
				}
			}
			
		});
	$("span[negative-data-modelid]").on("mouseleave",function(obj){ 
		if(negaOpened == "true"){
			negaOpened = "false";
		}
		setTimeout(function() {
			if(negaOpened == "false"){
				$(obj.currentTarget).parent().find("div[class='lui_negative_layer']").hide();
			}
    	}, 1000);
	});
}

//翻页(踩)
var negativPageno = 1;
//上一页
function preNegativePage(objId){
	var data_mark = $("#aid_"+objId+" #btn_preno").attr("data-praise-mark");
	if(data_mark == 1){
		getNegatives(objId,--negativPageno,true,true);
	}
}
//下一页
function nextNegativePage(objId){
	var data_mark = $("#aid_"+objId+" #btn_nextno").attr("data-praise-mark");
	if(data_mark == 1){
		getNegatives(objId,++negativPageno,true,true);
	}
}
</script>
<script>
Com_AddEventListener(window, "load", initLoad);
function initLoad() {
	initNegative();
	initPraise();
	//关闭人员列表框
	$("#negative_close").delegate('.lui_negative_close_s','click',function(evt){
		closeNegativeListBox(evt);
	});

		//关闭人员列表框
		$("#praise_close").delegate('.lui_praise_close_s','click',function(evt){
			closeListBox(evt);
		});
			
	$(document).on("click",function(evt){
		closeNegativeListBox(evt);
			closeListBox(evt);
	});
};
</script>

