
seajs.use(['lui/jquery','lui/dialog','lui/topic','lang!hr-staff'], function($, dialog , topic,lang) {
			var windowHeight= window.innerHeight||window.screen.height

			var personId = $("#personInfoId").val();
			$('.lui-personnel-file-baseInfo-main').css({
			  height:(windowHeight-0)+'px'
			})
			

		  /**************************************** 滚动事件***********************************************/
		  var titleLi =  $(".titleList li");
		  var contentHeight=[]; 
		  var content = $(".lui-personnel-file-staffInfo");
		  var curPos = 0;
		  var sumHeight=0;
		  var isCanScroll=true;
		  var isClean = false;
		  // 初始化
		  function getPartHeight(){
			  contentHeight=[];
			  sumHeight=0;
			  content.length>0&&$.map($(".lui-personnel-file-staffInfo"),function(item,index){
				  sumHeight += $(item).outerHeight(true);
				  contentHeight.push(sumHeight);
			  })
		  }
		  $(".titleList li").eq(0).addClass('lui-personnel-file-active');
		  $('.lui-personnel-file-baseInfo-main-content').on('scroll',function(){
			  $(".titleList li a").removeClass("ustitleListliafter");
			  $(".titleList li a").removeClass("titleListliafter");
			  
			  getPartHeight();
			  if(isCanScroll){
				  $(document).scrollTop(0);
				   var scrollTop=$(this).scrollTop();
					var pos = 0;
					for(var i = 0;i<contentHeight.length;i++){
						if(scrollTop<(contentHeight[i]-100)){
								pos=i;
								curPos = pos;
								break;
						}
					}
					var curActiveIndex = $(".lui-personnel-file-active").index();
					if(curActiveIndex!=pos){
					  removeLiActiveClass();
					  $(".titleList li").eq(pos).addClass('lui-personnel-file-active');
					  var __userlandArray=Com_Parameter.Lang.split('-');
					  var __userland=__userlandArray[1];
					  if(__userland=="us"){
						  $(".titleList li").eq(pos).addClass('ustitleListliafter');
					  }else{
						  $(".titleList li").eq(pos).addClass('titleListliafter');
					  }
					}
					
			  }
		  })
			
		  $(".titleList li").on("click",function(){
			  getPartHeight();
			  var oIndex = $(this).index();
			  var LiHeight  = contentHeight;
			  LiHeight.unshift(0);
			  removeLiActiveClass();
			  isCanScroll= false;
			  $(document).scrollTop(0);
			  var __userlandArray=Com_Parameter.Lang.split('-');
			  var __userland=__userlandArray[1];
			  if(__userland=="us"){
				  $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('ustitleListliafter')
				  $(".titleList li").eq(oIndex).addClass('ustitleListliafter');
			  }else{
				  $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('titleListliafter')
				  $(".titleList li").eq(oIndex).addClass('titleListliafter');
			  }
			  $('.lui-personnel-file-baseInfo-main-content').scrollTop(LiHeight[oIndex]);
			  setTimeout(function(){
				  isCanScroll = true;
			  },100)
		  })
		  window.initScorll=function(oIndex){
			  getPartHeight();
			  var LiHeight  = contentHeight;
			  LiHeight.unshift(0);
			  removeLiActiveClass();
			  isCanScroll= false;
			  $(document).scrollTop(0);
			  var __userlandArray=Com_Parameter.Lang.split('-');
			  var __userland=__userlandArray[1];
			  if(__userland=="us"){
				  $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('ustitleListliafter')
				  $(".titleList li").eq(oIndex).addClass('ustitleListliafter');
			  }else{
				  $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('titleListliafter')
				  $(".titleList li").eq(oIndex).addClass('titleListliafter');
			  }
			  $('.lui-personnel-file-baseInfo-main-content').scrollTop(LiHeight[oIndex]);
			  setTimeout(function(){
				  isCanScroll = true;
			  },100)
		  }
		  
		  function removeLiActiveClass(){
		    $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('lui-personnel-file-active')
		    
		    var __userlandArray=Com_Parameter.Lang.split('-');
			  var __userland=__userlandArray[1];
			  if(__userland=="us"){
				  $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('ustitleListliafter')
			  }else{
				  $('.lui-personnel-file-baseInfo-main-aside .titleList li').removeClass('titleListliafter')
			  }
		    
		  }
			/** **************************************薪酬************************************************ */
		  $(function () { 
			  $('.salaryAdjustRecord-action .salaryAdjustRecord-action-icon').removeClass('unfold')
			    $('.salaryAdjustRecordList').css({
			      'max-height':'0',
			      'overflow': 'hidden'
			    })
			    $('.salaryAdjustRecord-action-text').html(lang['hrStaffEmolumentWelfareDetalied.unfolder'])
		  });
		  $('.salaryAdjustRecord').click(function(){
			  var tabelHeight=$('.salaryAdjustRecordList').height();
			  if(tabelHeight==0){
			    $('.salaryAdjustRecord-action .salaryAdjustRecord-action-icon').addClass('unfold')
			    $('.salaryAdjustRecordList').css({
			      'max-height':'2000px',
			      'overflow':'inherit'
			    })
			    $('.salaryAdjustRecord-action-text').html(lang['hrStaffEmolumentWelfareDetalied.folder'])
			  }else{
			    $('.salaryAdjustRecord-action .salaryAdjustRecord-action-icon').removeClass('unfold')
			    $('.salaryAdjustRecordList').css({
			      'max-height':'0',
			      'overflow': 'hidden'
			    })
			    $('.salaryAdjustRecord-action-text').html(lang['hrStaffEmolumentWelfareDetalied.unfolder'])
			  }
			})
		  /*********************************************** 编辑标签******************************** */
		  $('.lui-personnel-file-baseInfo-tags-edit').click(function(){
		    $('.lui-personnel-file-mask').css({
		      'display':'block'
		    })
		  })
		  $('.lui-personnel-file-tags-edit-modal-header-close').click(function(){
		    $('.lui-personnel-file-mask').css({
		      'display':'none'
		    })
		  })
		  $('.lui-personnel-file-tags-edit-modal-footer-cancel').click(function(){
		    $('.lui-personnel-file-mask').css({
		      'display':'none'
		    })
		  })
		  function printpage(){
		    // 收起的内容展开
		    $('.salaryAdjustRecord-action .salaryAdjustRecord-action-icon').addClass('unfold')
		    $('.salaryAdjustRecordList').css({
		      'max-height':'2000px',
		      'transition':'max-height 0.5s linear'
		    })
		    $('.salaryAdjustRecord-action-text').html(lang['hrStaffEmolumentWelfareDetalied.folder'])
		    $('.lui-personnel-file-baseInfo-main-aside').css({
		      'display':'none'
		    })
		    $('.lui-personnel-file-baseInfo-main').css({
		      'height':'100%'
		    })
		    $('.lui-personnel-file-baseInfo-main-content').css({
		      'width':'100%!important',
		      'height':'100%',
		      'overflow':'auto'
		    })
		    window.print()
		  }
		  
			// 查看
			window.viewDetail = function(dataviewId, id){
				var _type = dataviewId.slice(0, 1).toUpperCase() + dataviewId.slice(1);
				var url = Com_Parameter.ContextPath +"hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=view&fdId="+id;
				window.open(url,'_blank');
			}
			// 增加 或 编辑
			window.addOrEdit = function(dataviewId, id) {
				var _type = dataviewId.slice(0, 1).toUpperCase() + dataviewId.slice(1);
				var iframeUrl = "/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=add&personInfoId=" +personId;
				var url = Com_Parameter.ContextPath +"hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=save";
				var title ;
				switch(dataviewId){
				case 'bonusMalus':{
					title = lang['hrStaffEntry.fdRewardsPunishments'];
					break;
				}
				case 'qualification':{
					title = lang['hrStaffEntry.fdCertificate'];
					break;
				}
				case 'training':{
					title = lang['hrStaffPersonExperience.type.training'];
					break;
				}
				case 'education':{
					title = lang['hrStaffPersonExperience.type.education'];
					break;
				}
				case 'work':{
					title = lang['hrStaffPersonExperience.type.work'];
					break;
				}
				case 'contract':{
					title = lang['hrStaffPersonExperience.type.contract'];
					break;
				}
				case 'project':{
					title = '';
					break;
				}
				case 'brief':{
					title = lang['hrStaffPersonExperience.type.brief'];
					break;
				}
				}
				if(id) {
					iframeUrl = "/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=edit&fdId=" + id+ "&personInfoId="+personId+"&type=" + dataviewId;
					url = Com_Parameter.ContextPath +"hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=update";
				}
				
				dialog.iframe(iframeUrl, title, function(data) {
					if(dataviewId=="contract"){
						dataviewId="contractInfoList";
					}
					if(dataviewId=="bonusMalus"){
						dataviewId="rewardAndPunishmentInfoList";
					}
					LUI(dataviewId).load();
					if (null != data && undefined != data) {
						if(id) {
							data.fdId = id;
						}
						$.post(url, data, function(result){
							if(result.state||result.status) {
								LUI(dataviewId).load();
							} else {
								dialog.alert(result.msg);
							}
						}, "json");
					}
				}, {
					width : 900,
					height : 400
				});
			};
			
			// 删除详情信息
			window.delDetail = function(dataviewId, id) {
				var values = [];
				if(id) {
	 				values.push(id);
		 		}
				if(values.length == 0) {
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var _type = dataviewId.slice(0, 1).toUpperCase() + dataviewId.slice(1);
				var url = Com_Parameter.ContextPath +"hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=deleteall";
				dialog.confirm('一旦选择了删除，所选记录的相关数据都会被删除，无法恢复！您确认要执行此删除操作吗？', function(value) {
					if(value == true) {
						window.del_load = dialog.loading();
						$.ajax({
							url : url,
							type : 'POST',
							data : $.param({"List_Selected" : values}, true),
							dataType : 'json',
							error : function(data) {
								if(window.del_load != null) {
									window.del_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: function(data) {
								if(window.del_load != null) {
									window.del_load.hide(); 
								}
								if(dataviewId=="bonusMalus"){
									dataviewId="rewardAndPunishmentInfoList";
								}
								if(dataviewId=="contract"){
									dataviewId="contractInfoList";
								}
								LUI(dataviewId).load();
								dialog.result(data);
							}
					   });
					}
				});
			};
	})