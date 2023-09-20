

seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lang!hr-staff','lui/framework/router/router-utils'],
		function(Module, jquery, dialog, topic, lang, routerUtils){
	
	var module = Module.find('hrStaff');
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/overview',
			routes : [
			  {
				path : '/overview', 
				action : { 
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContent' : { title : $lang['hrStaffOverview'], route:{ path: '/overview' }, selected : true }
						}
					}
				}
			},{
				path : '/management', //所有流程
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentManage' : { title : lang['hr.staff.nav.attendance.management'], route:{ path: '/management' }, cri :{'cri.q':'_fdStatus:official','_type':'manage'} , selected : true }
						}
					}
				}
			},{
				path : '/benefits',
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentBenefits' : { title : lang['hr.staff.nav.benefits'], route:{ path: '/benefits' }, cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
					path : '/security',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentSecurity' : { title : lang['hr.staff.nav.social.security'], route:{ path: '/security' }, cri :{'cri.q':'_fdStatus:official'} , selected : true }
							}
						}
					}
				},{
					path : '/accumulationFund',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentAccumulationFund' : { title : lang['hr.staff.nav.accumulation.fund'], route:{ path: '/accumulationFund' }, cri :{'cri.q':'_fdStatus:official'} , selected : true }
							}
						}
					}
				},{
					path : '/Ekp_H14_M_performance',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentEkp_H14_M_performance' : { title : lang['hr.staff.nav.Ekp_H14_M_performance'], route:{ path: '/Ekp_H14_M_performance' }, cri :{} , selected : true }
							}
						}
					}
				},{
					path : '/Ekp_H14_S',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentEkp_H14_S' : { title : lang['hr.staff.nav.Ekp_H14_S'], route:{ path: '/Ekp_H14_S' }, cri :{} , selected : true }
							}
						}
					}
				},{
					path : '/Ekp_H14_S1',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentEkp_H14_S1' : { title : lang['hr.staff.nav.Ekp_H14_S1'], route:{ path: '/Ekp_H14_S1' }, cri :{} , selected : true }
							}
						}
					}
				},{
					path : '/Ekp_H14_S2',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentEkp_H14_S2' : { title : lang['hr.staff.nav.Ekp_H14_S2'], route:{ path: '/Ekp_H14_S2' }, cri :{} , selected : true }
							}
						}
					}
				},{
					path : '/ekp_H14_Intern',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'ekp_H14_Intern' : { title : lang['hr.staff.nav.ekp_H14_Intern'], route:{ path: '/ekp_H14_Intern' }, cri :{'cri.q':'_fdStatus:official'} , selected : true }
							}
						}
					}
				},
				{
					path : '/performanceContractImport',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentPerformanceContractImport' : { title : lang['hr.staff.nav.performance。contract.import'], route:{ path: '/performanceContractImport' }, cri :{} , selected : true }
							}
						}
					}
				},
				{
					path : '/performanceSearch',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'hrStaffPanel',
							contents : {
								'hrStaffContentPerformanceSearch' : { title : lang['hr.staff.nav.performance。search'], route:{ path: '/performanceSearch' }, cri :{} , selected : true }
							}
						}
					}
				},
				{
				path : '/payroll', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentPayroll' : { title : lang['hr.staff.nav.payroll'], route:{ path: '/payroll' }, selected : true }
						}
					}
				}
			},{
				path : '/ownerFile',
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentOwnerFile' : { title : lang['hr.staff.nav.staff.owner.file'], route:{ path: '/ownerFile' }, selected : true }
						}
					}
				}
			}
			,{
				path : '/entryStatus1', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'entryStatus1' : { title : lang['table.hrStaffEntry.status1'], route:{ path: '/entryStatus1' }, selected : true }
						}
					}
				}
			},{
				path : '/entryStatus2', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'entryStatus2' : { title : lang['table.hrStaffEntry.status2'], route:{ path: '/entryStatus2' }, selected : true }
						}
					}
				}
			},{
				path : '/informationIn', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentIn' : { title : lang['hr.staff.nav.employee.information.in'], route:{ path: '/informationIn' }, selected : true }
						}
					}
				}
			},{
				path : '/informationQuit', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentQuit' : { title : lang['hr.staff.nav.employee.information.quit'], route:{ path: '/informationQuit' }, selected : true }
						}
					}
				}
			},{
				path : '/contract', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentContractIndex' : { title : lang['hrStaffPersonExperience.type.contract'], route:{ path: '/contract' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/work', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentWork' : { title : lang['hrStaffPersonExperience.type.work'], route:{ path: '/work' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/trackrecord', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffTrackRecord' : { title : lang['hrStaffTrackRecord.record'], route:{ path: '/trackrecord' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/moverecord',
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffMoveRecord' : { title : lang['hrStaffMoveRecord.record'], route:{ path: '/moverecord' }, cri :{'cri.q':'_fdStatus:official'},selected : true }
						}
					}
				}
			},{
				path : '/familyInfo', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffPersonFamily' : { title : lang['hrStaffPerson.family'], route:{ path: '/familyInfo' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/education', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentEducation' : { title : lang['hrStaffPersonExperience.type.education'], route:{ path: '/education' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/training', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentTraining' : { title : lang['hrStaffPersonExperience.type.training'], route:{ path: '/training' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/qualification', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentQualification' : { title : lang['hrStaffPersonExperience.type.qualification'], route:{ path: '/qualification' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/bonusMalus', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentBonusMalus' : { title : lang['hrStaffPersonExperience.type.bonusMalus'], route:{ path: '/bonusMalus' },cri :{'cri.q':'_fdStatus:official'} , selected : true }
						}
					}
				}
			},{
				path : '/reportStaffNum', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportStaffNum' : { title : lang['hrStaffPersonReport.type.reportStaffNum'], route:{ path: '/reportStaffNum' }, selected : true }
						}
					}
				}
			},{
				path : '/reportPersonnelMonthlyReportStaffEntryAndExit', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentreportPersonnelMonthlyReportStaffEntryAndExit' : { title : lang['hrStaffPersonReport.type.reportPersonnelMonthlyReportStaffEntryAndExit'], route:{ path: '/reportPersonnelMonthlyReportStaffEntryAndExit' }, selected : true }
						}
					}
				}
			},{
				path : '/reportAge', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportAge' : { title : lang['hrStaffPersonReport.type.reportAge'], route:{ path: '/reportAge' }, selected : true }
						}
					}
				}
			},{
				path : '/reportWorkTime', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportWorkTime' : { title : lang['hrStaffPersonReport.type.reportWorkTime'], route:{ path: '/reportWorkTime' }, selected : true }
						}
					}
				}
			},{
				path : '/reportEducation', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportEducation' : { title : lang['hrStaffPersonReport.type.reportEducation'], route:{ path: '/reportEducation' }, selected : true }
						}
					}
				}
			},{
				path : '/reportStaffingLevel', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportStaffingLevel' : { title : lang['hrStaffPersonReport.type.reportStaffingLevel'], route:{ path: '/reportStaffingLevel' }, selected : true }
						}
					}
				}
			},{
				path : '/reportStatus', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportStatus' : { title : lang['hrStaffPersonReport.type.reportStatus'], route:{ path: '/reportStatus' }, selected : true }
						}
					}
				}
			},{
				path: '/hrStaffMoveMonthReport',
				action: {
					type: 'tabpanel',
					options: {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffMoveMonthReportContent' : { title : '每月异动信息', route:{ path: '/hrStaffMoveMonthReport' }, selected : true }
						}
					}
				}
			},{
				path: '/hrStaffMoveAllReport',
				action: {
					type: 'tabpanel',
					options: {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffMoveAllReportContent' : { title : '所有异动信息', route:{ path: '/hrStaffMoveAllReport' }, selected : true }
						}
					}
				}
			},{
				path: '/hrStaffRecruitReport',
				action: {
					type: 'tabpanel',
					options: {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffRecruitReportContent' : { title : '招聘信息', route:{ path: '/hrStaffRecruitReport' }, selected : true }
						}
					}
				}
			},{
				path: '/hrStaffApprovalReport',
				action: {
					type: 'tabpanel',
					options: {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffApprovalReportContent' : { title : '流程流转时间分析表', route:{ path: '/hrStaffApprovalReport' }, selected : true }
						}
					}
				}
			},{
				path: '/hrStaffAttendReport',
				action: {
					type: 'tabpanel',
					options: {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffAttendReportContent' : { title : '异常考勤预警名单', route:{ path: '/hrStaffAttendReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrEntryMonthReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrEntryMonthReportContent' : { title : '当月新员工入职统计', route:{ path: '/hrEntryMonthReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrLeaveLevelMonthReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrLeaveLevelMonthReportContent' : { title : '按职位类别统计离职率', route:{ path: '/hrLeaveLevelMonthReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrLeaveMonthReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrLeaveMonthReportContent' : { title : '人员入离职率汇总', route:{ path: '/hrLeaveMonthReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrPersonalStructureMonthReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrPersonalStructureMonthReportContent' : { title : '人员结构分布表', route:{ path: '/hrPersonalStructureMonthReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrPersonalMonthReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrPersonalMonthReportContent' : { title : '人事月报-编制人员进出', route:{ path: '/hrPersonalMonthReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrRankMonthReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrRankMonthReportContent' : { title : '人事月报-职类人员进出', route:{ path: '/hrRankMonthReport' }, selected : true }
						}
					}
				}
			},{
				path : '/hrPersonalIncomeTaxReport', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrPersonalIncomeTaxReportContent' : { title : '个税人员信息采集表', route:{ path: '/hrPersonalIncomeTaxReport' }, selected : true }
						}
					}
				}
			}
			/**,{
				path : '/hrPersonalIncomeTaxReportCaiwu', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrPersonalIncomeTaxReportContentCaiwu' : { title : lang['hrStaffPersonReport.type.hrPersonalIncomeTaxReportCaiwu'], route:{ path: '/hrPersonalIncomeTaxReportCaiwu' }, selected : true }
						}
					}
				}
			}
			**/
			,{
				path : '/reportMarital', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentReportMarital' : { title : lang['hrStaffPersonReport.type.reportMarital'], route:{ path: '/reportMarital' }, selected : true }
						}
					}
				}
			},{
				path : '/birthday', 
				action : function(){
					 $.ajax({
	            		 url:$var.$contextPath+"/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getLastBirthday",
	            		 type:"post",
	            		 async:false,
	            		 success:function(data){
	            			 if(data!=null){
	            				 var datas = data.split("_");
	            				 var router = routerUtils.getRouter();
	            					if (router) {
	            						router.push("/birthdayShow",{
	            							cri : { 'cri.q' : 'fdBirthdayOfYear:' + datas[0]+';fdBirthdayOfYear:'+datas[1]+';_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;_fdStatus:retire','type':'warningBirthday'}
	            						});
	            					}
	            			 }
	            		 }
	            	 } );
					
				}
			},{
				path : '/birthdayShow', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentBirthday' : { title : lang['hr.staff.nav.last.birthday'], route:{ path: '/birthdayShow' }, selected : true }
						}
					}
				}
			},{
				path : '/contractExpiration', 
				action : function(){
					 $.ajax({
	            		 url:$var.$contextPath+"/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getContractExpiration",
	            		 type:"post",
	            		 async:false,
	            		 success:function(data){
	            			 if(data!=null){
	            				 var datas = data.split("_");
	            				 var router = routerUtils.getRouter();
	            					if (router) {
	            						router.push("/contractExpirationShow",{
	            							cri : { 'cri.q' : 'fdEndDate:' + datas[0]+';fdEndDate:'+datas[1]+';_fdStatus:trial;_fdStatus:official;_fdStatus:temporary;_fdStatus:trialDelay;fdContStatus:1','type':'warningContract'}
	            						});
	            					}
	            			 }
	            		 }
	            	 } );
					
				}
			},{
				path : '/contractExpirationShow', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentContract' : { title : lang['hr.staff.nav.contract.expiration'], route:{ path: '/contractExpirationShow' }, selected : true }
						}
					}
				}
			},{
				path : '/trial', 
				action : function(){
					 $.ajax({
	            		 url:$var.$contextPath+"/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getTrialExpiration",
	            		 type:"post",
	            		 async:false,
	            		 success:function(data){
	            			 if(data!=null){
	            				 var datas = data.split("_");
	            				 var router = routerUtils.getRouter();
	            					if (router) {
	            						router.push("/trialShow",{
	            							cri : { 'cri.q' : 'fdTrialExpirationTime:' + datas[0]+';fdTrialExpirationTime:'+datas[1]+';_fdStatus:trial;_fdStatus:trialDelay','type':'warningTrial'}
	            						});
	            					}
	            			 }
	            		 }
	            	 } );
					
				}
			},{
				path : '/trialShow', 
				action :{
					type : 'tabpanel',
					options : {
						panelId : 'hrStaffPanel',
						contents : {
							'hrStaffContentTrial' : { title : lang['hr.staff.nav.trial.expiration'], route:{ path: '/trialShow' }, selected : true }
						}
					}
				}
			},{
				path : '/person_info_log', 
				action : function(){
					moduleAPI.hrStaff.openSearch($var.$contextPath+'/hr/staff/import/hrStaff_person_info_log.jsp')
				}
			},{
				path : '/background', 
				action :  {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/index.jsp#app/ekp/hr/staff',
						target : '_blank'
					}
				}
			},{
				path : '/sys/subordinate', // 下属工作
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/subordinate/moduleindex.jsp?moduleMessageKey=hr-staff:module.hr.staff',
						target : '_rIframe'
					}
				}
			},{
				path : '/management1', // 后台管理
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/hr/staff/tree.jsp',
						target : '_rIframe'
					}
				}
			}]
		});
		
		$function.openSearch=function(url){
			LUI.pageOpen(url,'_rIframe');
		};
		
		$function.openPreview = function(url){
			if (seajs.data.env.isSpa) {
				topic.publish("nav.operation.clearStatus", null);
				var router = routerUtils.getRouter();
				if (router) {
					router.push(url);
				}
			}
		};
		
	});
});