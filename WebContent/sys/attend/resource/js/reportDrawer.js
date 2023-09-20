
seajs.use(['lui/jquery','lui/dialog','lang!sys-attend'], function($,dialog,Msg){


	window.loadReportData =function(_fdReportLogType){
		setTimeout("getExportNumber('"+_fdReportLogType+"')",200);
		setTimeout("getExportList('"+_fdReportLogType+"')",500);
	}
	var loadLog =false;
	var lui_dialog_report_log_mask =null;
	window.exportLog =function(_fdReportLogType){
		let topList = window.top.document.getElementsByClassName("lui_log_list");
		$(topList).toggleClass("lui_log_list_init");
		$(topList).toggleClass("lui_log_list_open");
		loadLog =!loadLog;
		if(loadLog){
			let maxZindex = getMaxZIndex();
			maxZindex =maxZindex-1;
			setTimeout("loadReportData('"+_fdReportLogType+"')", 200);
			lui_dialog_report_log_mask = document.createElement("div");
			lui_dialog_report_log_mask.setAttribute("class","lui_dialog_mask");
			lui_dialog_report_log_mask.setAttribute("id","lui_dialog_mask_report");
			lui_dialog_report_log_mask.setAttribute("style","top: 0px; left: 0px; position: fixed; z-index: "+maxZindex+"; background-color: rgb(255, 255, 255); opacity: 0.6;");
			window.top.document.body.appendChild(lui_dialog_report_log_mask);
		}else{
			if(lui_dialog_report_log_mask){
				window.top.document.body.removeChild(lui_dialog_report_log_mask);
			}
		}
	}
	var isDown=false;
	var currentExportNumber = 0;

	// 获取当前页面最大z-index元素值
	function getMaxZIndex() {
		var maxZ = Math.max.apply(null, $.map($('body *'), function(e,n) {
				if ($(e).css('position') != 'static') {
					return parseInt($(e).css('z-index')) || -1;
				}
			}
		));
		return maxZ;
	}

	window.exportCommon =function(url,param,_fdReportLogType){
		if(!isDown){
			isDown =true;
			$.ajax({
				url: url,
				type: 'POST',
				data: param,
				dataType: 'json',
				error: function(data){
					isDown=false;
				},
				success: function(data){
					currentExportNumber = 0;
					if(data.data=='success'){
						//dialog.alert('${ lfn:message('sys-attend:sysAttendReportLog.submit.tip') }');
						if(!loadLog) {
							exportLog(_fdReportLogType);
						}else{
							setTimeout("loadReportData('"+_fdReportLogType+"')", 200);
						}
					}
					isDown =false;
				}
			});
		}
	}

	window.getExportNumber =function(_fdReportLogType){
		if(!loadLog){
			//导出记录关闭以后。停止刷新
			return;
		}
		$.ajax({
			url: Com_Parameter.ContextPath+'sys/attend/sys_attend_report_log/sysAttendReportLog.do?method=getDownLoadNumber',
			type: 'POST',
			data:{fdType:_fdReportLogType},
			dataType: 'json',
			error: function(data){

			},
			success: function(data){
				if(data.exportNumber || data.exportNumber ==0 ){
					if(data.exportNumber > 0){
						if(currentExportNumber !=data.exportNumber && currentExportNumber > 0) {
							//Time一直刷新
							setTimeout("loadReportData('"+_fdReportLogType+"')", 5000);
						}else{
							setTimeout("getExportNumber('"+_fdReportLogType+"')", 5000);
						}
					}else{
						//没有正在生成的，重新加载列表 停止Time
						setTimeout("getExportList('"+_fdReportLogType+"')",500);
					}
					currentExportNumber = data.exportNumber ;
				}
			}
		});
	}

	window.initAttendReport =function(_fdReportLogType){
		let isHave = window.top.document.getElementsByClassName("lui_log_list");
		//如果存在，则不在生成
		if(isHave &&  isHave.length > 0) {
			window.top.document.body.removeChild(isHave[0]);
		}
		let maxZindex = getMaxZIndex();
		maxZindex =maxZindex+2;

		let lui_log_list = document.createElement("div");

		lui_log_list.setAttribute("class", "lui_drawer_wrapper lui_log_list");
		lui_log_list.setAttribute("style", "z-index:"+maxZindex);


		let lui_drawer_header = document.createElement("div");
		lui_drawer_header.setAttribute("class", "lui_drawer_header");
		let lui_drawer_title = document.createElement("span");
		lui_drawer_title.setAttribute("class", "lui_drawer_title");
		lui_drawer_title.innerHTML = Msg['sysAttendReportLog.list'];
		lui_drawer_header.appendChild(lui_drawer_title);

		let lui_drawer_btn_close = document.createElement("span");
		lui_drawer_btn_close.setAttribute("class", "lui_drawer_btn_close");
		lui_drawer_btn_close.setAttribute("title", "关闭");
		lui_drawer_btn_close.setAttribute("onclick", "exportLog('" + _fdReportLogType + "')");

		lui_drawer_header.appendChild(lui_drawer_btn_close);
		lui_log_list.appendChild(lui_drawer_header);
		let lui_drawer_content = document.createElement("div");
		lui_drawer_content.setAttribute("class", "lui_drawer_content");
		lui_drawer_content.setAttribute("id", "report_log_content");
		lui_log_list.appendChild(lui_drawer_content);
		window.top.document.body.appendChild(lui_log_list);

		let topList = window.top.document.getElementsByClassName("lui_log_list");
		$(topList).toggleClass("lui_log_list_init");
	}

	window.getExportList =function(_fdReportLogType){
		$.ajax({
			url: Com_Parameter.ContextPath+'sys/attend/sys_attend_report_log/sysAttendReportLog.do?method=getDownLoadList',
			type: 'POST',
			data:{fdType:_fdReportLogType},
			dataType: 'json',
			error: function(data){

			},
			success: function(data){
				let content=window.top.document.getElementById("report_log_content");
				content.innerHTML=("");
				if(data && data.length > 0) {

					let fileList = document.createElement("div");
					fileList.setAttribute("class","lui_export_file_list");
					let ul = document.createElement("ul");
					fileList.appendChild(ul);
					for (let i = 0; i < data.length; i++) {
						let dataInfo = data[i];
						let status = dataInfo.fdStatus;
						let li = document.createElement("li");

						let fileIcon = document.createElement("div");
						fileIcon.setAttribute("class","lui_export_file_icon");
						let icon = document.createElement("i");
						icon.setAttribute("class","lui_attach_file_icon lui_attach_file_icon_excel");
						fileIcon.appendChild(icon);
						let content = document.createElement("div");
						content.setAttribute("class","lui_export_file_content");
						let p1 = document.createElement("p");
						p1.setAttribute("class","lui_export_file_title");


						p1.innerHTML = dataInfo.fileName;
						content.appendChild(p1);
						let operate = document.createElement("div");
						operate.setAttribute("class","lui_export_file_opt");

						let p2 = document.createElement("p");

						if(status > 0 ) {
							p2.setAttribute("class","lui_export_file_time");
							p2.innerHTML = dataInfo.docCreateTime;
							//操作按钮
							let operateSpan=document.createElement("span");
							operateSpan.setAttribute("class","lui_export_file_more");

							let operateSpanUl=document.createElement("ul");
							let operateSpanUlLi1=document.createElement("li");
							let operateSpanUlLi2=document.createElement("li");

							let downLoad=document.createElement("span");
							downLoad.innerHTML ="下载";
							operateSpanUlLi1.setAttribute("onclick", "downReportFile('"+dataInfo.fileId+"')");
							let remove=document.createElement("span");
							remove.innerHTML ="删除";
							operateSpanUlLi2.appendChild(remove);
							operateSpanUlLi2.setAttribute("onclick", "delReportFile('"+dataInfo.fdId+"','" + _fdReportLogType + "')");
							if(dataInfo.fileId) {
								operateSpanUlLi1.appendChild(downLoad);
								operateSpanUl.appendChild(operateSpanUlLi1);
							}


							operateSpanUl.appendChild(operateSpanUlLi2);
							operateSpan.appendChild(operateSpanUl);
							operate.appendChild(operateSpan);
						} else {
							p2.setAttribute("class","lui_export_file_status lui_export_file_status_doing");
							p2.innerHTML = "生成中";
						}

						content.appendChild(p2);

						li.appendChild(fileIcon);
						li.appendChild(content);


						if(status > 1) {
							//失败
							let failt1 = document.createElement("div");
							failt1.setAttribute("class", "lui_export_status lui_export_status_error");

							let failt2 = document.createElement("span");
							failt2.setAttribute("class", "lui_export_tooltip");

							let failt2_1 = document.createElement("span");
							failt2_1.setAttribute("class", "lui_export_tooltip_title");
							failt2_1.innerHTML=dataInfo.desc;

							let failt2_2 = document.createElement("em");
							failt2_2.setAttribute("class", "lui_export_tooltip_trig");

							failt2.appendChild(failt2_1);
							failt2.appendChild(failt2_2);

							let failt3 = document.createElement("i");
							let failt4 = document.createElement("span");
							failt4.innerHTML = "失败";

							failt1.appendChild(failt2);
							failt1.appendChild(failt3);
							failt1.appendChild(failt4);
							li.appendChild(failt1);
						}

						li.appendChild(operate);

						ul.appendChild(li);
					}
					content.appendChild(fileList);
				}else{
					content.innerHTML=("<div class=\"lui_export_noData_iframe\"><div class=\"lui_export_noData_icon\"></div><p class=\"lui_export_noData_tip\">暂无相关导出记录！</p></div>");
				}
			}
		});
	}
	window.downReportFile =function(fdId){
		if(fdId) {
			var downloadUrl = Com_Parameter.ContextPath
				+ "sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
				+ fdId + "&downloadType=manual&downloadFlag="
				+ (new Date()).getTime();
			Com_OpenWindow(downloadUrl, "_blank");
		}
	}
	window.delReportFile =function(fdId,fdType){
		//系统弹出层样式的改造
		let lui_dialog_mask=window.top.document.getElementById("lui_dialog_mask_report");
		console.info(lui_dialog_mask);
		if(lui_dialog_mask){
			//自定义遮罩层隐藏
			$(lui_dialog_mask).hide();
		}

		dialog.confirm(Msg['sysAttendReportLog.confirm'],function(value){
			if(value==true){
				seajs.use(['lui/jquery','lui/dialog'], function($,_loadingDialog) {
					var del_load = _loadingDialog.loading();

					$.ajax({
						url: Com_Parameter.ContextPath+'sys/attend/sys_attend_report_log/sysAttendReportLog.do?method=deleteAjax',
						type: 'POST',
						data:{fdId:fdId},
						dataType: 'json',
						error: function(data){

						},
						success: function(data){
							del_load.hide();
							seajs.use(['lui/jquery','lui/dialog'], function($,_dialog) {

								if (data.data == 'success') {
									_dialog.alert(Msg['sysAttendReportLog.delete.ok'],function(){
										//遮罩层。还原
										if(lui_dialog_mask){
											$(lui_dialog_mask).show();
										}
									});
									getExportList(fdType);
								} else {
									_dialog.alert(Msg['sysAttendReportLog.delete.error'],function(){
										//遮罩层。还原
										if(lui_dialog_mask){
											$(lui_dialog_mask).show();
										}
									});
								}
							});
						}
					});
				});
			} else {
				//遮罩层。还原
				if(lui_dialog_mask){
					$(lui_dialog_mask).show();
				}
			}
		});



	}
});