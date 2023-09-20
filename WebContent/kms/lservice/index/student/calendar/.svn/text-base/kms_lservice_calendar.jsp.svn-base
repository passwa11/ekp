<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">	
seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar','lui/util/env','kms/lservice/index/student/calendar/dateUtil'], function($,dialog , topic ,toolbar,env,kmcalendarDateUtil) {
	topic.subscribe('calendar.thing.click',function(arg){
		//获取位置
		var getPos=function(evt,showObj){
			var sWidth=showObj.width(),
			sHeight=showObj.height();
			var leftMargin = $(".lui_list_left_sidebar_innerframe").width(), //200是左边导航栏的宽度
				topMargin = $(".lui_portal_header").height();  //50是上边导航栏的高度
			x=evt.pageX-leftMargin; 
			y=evt.pageY-topMargin;
			if(y + sHeight + topMargin> $(window).height() + 75 ){
				y-=sHeight;
			}
			if(x + sWidth + leftMargin> $(document.body).outerWidth(true)){
				x-=sWidth;
			}
			return {"top":y,"left":x};
		};
		//题头
		$('#header_title').html('${lfn:message("kms-learn:module.kms.learn")}');
		$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
		$("#calendar_view_btn").show();//当前选择
		//主题
		if(arg.schedule.title){
        	$("#calendar_title").html(arg.schedule.title);
        	$("#calendar_title").attr("title", arg.schedule.title);
        	$("#tr_content").show();
        }else{
        	$("#tr_content").hide();
        }
		
		$.ajax({
			url: '${LUI_ContextPath}/kms/learn/kms_learn_main_personal/kmsLearnMainPersonal.do?method=getData&fdModelId='+arg.schedule.id,
			type: 'POST',
			dataType: 'json',
			async: false,
			success: function(data) {//操作成功
				//进度
				if(data.process){
                	$("#calendar_process2").html(data.process+'%');
                	$("#calendar_process1").css("width", data.process+'%');
                	$("#tr_content").show();
                }else{
                	$("#tr_content").hide();
                }
			    //讲师
			    if(data.lecturer){
                	$("#calendar_lectuer").html(data.lecturer);
                	$("#calendar_lectuer").attr("title", data.lecturer);
                	$("#tr_content").show();
			    }else{
			    	$("#tr_content").hide();
			    }
			    //课程说明
			    if(data.description){
                	$("#calendar_description").html(data.description);
                	$("#calendar_description").attr("title", data.description);
                	$("#tr_content").show();
			    }else{
			    	$("#tr_content").hide();
			    }			    
			}
		});
		
		//初始化时间
		var formatDate=Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk'||Com_Parameter['Lang']=='ja-jp')?"yyyy-MM-dd":"MM/dd/yyyy";
		if(!arg.schedule.allDay){
			formatDate+=" HH:mm";
		}
        var DateString=kmcalendarDateUtil.formatDate(arg.schedule.start,formatDate);
		if(arg.schedule.end!=null){
			DateString+="-"+kmcalendarDateUtil.formatDate(arg.schedule.end,formatDate);
		}
		$("#calendar_date").html(DateString);//初始化日期
		$("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
	});
	
	// 设置标签颜色
	window.setColor=function(schedule){
		var before="#888888";
		var training="#4285F4";
		var after="#22B77D";
		if(schedule.learnType=="2"||schedule.learnType=="4"){// 未开始
			schedule.color=before;
		}else if(schedule.learnType=="1"){// 进行中
			schedule.color=training;
		}else{// 已结束
			schedule.color=after;
		}
	};
	//初始化默认标签的颜色,针对集合
	window.setColors=function(data){
		for(var i=0;i<data.length;i++){
			setColor(data[i]);
		}
		return data;
	};
});

</script>
<ui:calendar id="calendar" showStatus="view" mode="default" layout="kms.lservice.student.calendar.default">
		<ui:dataformat>
			<ui:source type="AjaxJson">
					{url:'/kms/learn/kms_learn_acti_personal/kmsLearnActiPersonal.do?method=getLearnCalendar'}
			</ui:source>
			<ui:transform type="ScriptTransform">
					return setColors(data);
			</ui:transform>
		</ui:dataformat>
</ui:calendar>
<%@ include file="/kms/lservice/index/student/calendar/kmsLserviceCalendar_view.jsp"%>
<%@ include file="/kms/lservice/index/student/calendar/kmsLserviceCalendar_search.jsp"%>