$(document).ready(function(){
	getPersonInfo();//获取人员信息
	getTodoCount();  //获取待办条数
	getNotExpenseAcount();  //获取未报费用
	getExpensedAcount();  //获取已报费用
	getLinkConfig();  //获取常用链接
	getCreateObj();  //获取记一笔方式
	bindEvent();
});

function bindEvent(){
	$(".ld-footer-home").click(function(){
		$(this).addClass("active");
		$(".ld-footer-mine").removeClass("active");
		$(".ld-feesCharged-mine").addClass("ld-feesCharged-hidden");
		$(".ld-feesCharged-home-main").removeClass("ld-feesCharged-hidden");
	})
	$(".ld-footer-mine").click(function(){
		$(this).addClass("active");
		$(".ld-footer-home").removeClass("active");
		$(".ld-feesCharged-mine").removeClass("ld-feesCharged-hidden");
		$(".ld-feesCharged-home-main").addClass("ld-feesCharged-hidden");
	})
}

function editMobileLink(){
	window.location.href=Com_Parameter.ContextPath+"fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=editMobileLink";
}

function getPersonInfo(){
	$.ajax({
		url:formInitData['LUI_ContextPath'] + '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getPersonInfo',
		async:false,
		success:function(data){
			var rtn = JSON.parse(data);
			$(".ld-feesCharged-mine-header-info>h3").html(rtn.name);
			$(".ld-feesCharged-mine-header-info>p").html(rtn.dept);
		}
	});
}

//获取待办条数
function getTodoCount(){
    $.ajax({
		url:formInitData['LUI_ContextPath'] + '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getNewApprovalList&getTotalOnly=getTotalOnly',
		async:false,
		success:function(data){
			var rtn = JSON.parse(data);
			if(rtn.result=='success'&&rtn.count){
				$("#count").html(rtn.count);
			} else {
				jqtoast(rtn.message);
			}
		}
	});
}
//获取未报费用
function getNotExpenseAcount(){
	$.ajax({
		url:formInitData['LUI_ContextPath'] + '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getIndexData&dataSource=getNotExpenseAcount',
		async:false,
		success:function(data){
			var rtn = JSON.parse(data);
			if(rtn.result=='success'&&rtn.fdMoney){
				$("#fdMoney").html(rtn.fdMoney);
			} else {
				jqtoast(rtn.message);
			}
		}
	});
}
//获取已报费用
function getExpensedAcount(){
	$.ajax({
		url:formInitData['LUI_ContextPath'] + '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getIndexData&dataSource=getExpensedAcount',
		async:false,
		success:function(data){
			var rtn = JSON.parse(data);
			if(rtn.result=='success'&&rtn.fdMoney){
				$("#fdExpensedMoney").html(rtn.fdMoney);
			} else {
				jqtoast(rtn.message);
			}
		}
	});
}
//获取常用链接
function getLinkConfig(){
	$.ajax({
		url:formInitData['LUI_ContextPath'] + '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getIndexData&dataSource=getLinkConfig',
		async:false,
		success:function(data){
			var rtn = JSON.parse(data);
			console.log(rtn);
			if(rtn.result=='success'){
				var personal=rtn['personal'];  //常用链接
				if(personal&&personal.length>0){
					$(".ld-feesCharged-commonUsedLink").attr('style','display:block');
					var personal_html="";
					for(var i=0;i<personal.length;i++){
						personal_html+='<li class="swiper-slide">';
						personal_html+='<div class="'+personal[i]['fdIcon']+'"  onclick="personJump(\''+personal[i]['fdUrl']+'\');" style="background-image:url(\''+Com_Parameter.ContextPath+'fssc/mobile/resource/images/icon/'+personal[i]['fdIcon']+'\');background-size:100%;"  ></div>';
						personal_html+='<span>'+personal[i]['fdName']+'</span></li>';
					}
					$("[class='swiper-wrapper'][id='personal']").html(personal_html);
					$("#person_line").attr('style','display:block;');
				}
				var app=rtn['app'];  //应用导航
				if(app&&app.length>0){
					$(".ld-feesCharged-shortcut").attr('style','display:block');
					var app_html="";
					for(var i=0;i<app.length;i++){
						app_html+='<li><div  onclick="shortcutJump(\''+app[i]['fdUrl']+'\');"  class="'+app[i]['fdIcon']+'"   style="background-image:url(\''+Com_Parameter.ContextPath+'fssc/mobile/resource/images/icon/'+app[i]['fdIcon']+'\');background-size:100%;"  ></div>';
						app_html+='<span>'+app[i]['fdName']+'</span></li>';
					}
					$("[class='swiper-wrapper'][id='app']").html(app_html);
					$("#app_line").attr('style','display:block;');
				}
				var third=rtn['third'];  //第三方应用
				if(third&&third.length>0){
					$(".ld-feesCharged-otheruse").attr('style','display:block');
					var third_html="";
					for(var i=0;i<third.length;i++){
						third_html+='<li class="swiper-slide"><div  onclick="thirdJump(\''+third[i]['fdUrl']+'\');"  class="'+third[i]['fdIcon']+'"  alt="" style="background-image:url(\''+Com_Parameter.ContextPath+'fssc/mobile/resource/images/icon/'+third[i]['fdIcon']+'\');background-size:100%;"  >';
						third_html+='</div><span>'+third[i]['fdName']+'</span></li>';
					}
					$("[class='swiper-wrapper'][id='third']").html(third_html);
				}
			} else {
				jqtoast(rtn.message);
			}
		}
	});
}
//获取待办条数
function getCreateObj(){
    $.ajax({
		url:formInitData['LUI_ContextPath'] + '/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getIndexData&dataSource=getCreateObj',
		async:false,
		success:function(data){
			var rtn = JSON.parse(data);
			if(rtn.result=='success'){
				for(var i=0;i<rtn.data.length;i++){
					var type=rtn.data[i]['type'];
					if(type){
						$("#"+type).attr('style','display:block;');
					}
				}
			} else {
				jqtoast(rtn.message);
			}
		}
	});
}
    function logout(){
    		jqalert({
            title:'提示',
            content:'该操作将退出系统，是否继续？',
            yestext:'确认',
            notext:'取消',
            yesfn:function () {
            		location.href=Com_Parameter.ContextPath+'logout.jsp?logoutUrl='+Com_Parameter.ContextPath+"fssc/mobile/index.jsp";
            },
            	nofn:function () {
            }
        })
    }
