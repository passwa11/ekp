<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 初始化  -->
<script>
	seajs.use([ 'lui/jquery', 'lui/dialog' ], function($,dialog) {
		window.$ = $;
		window.dialog = dialog;
	});
	//多语言
	var mportalLang = {
		page: "${lfn:message('sys-mportal:sysMportal.msg.page')}",
		tab: "${lfn:message('sys-mportal:sysMportal.msg.tab')}",
		pleaseSetPage: "${lfn:message('sys-mportal:sysMportal.msg.pleaseSetPage')}",
		deleteWithExistChild: "${lfn:message('sys-mportal:sysMportal.msg.deleteWithExistChild')}",
		atMostSet5NavLayout: "${lfn:message('sys-mportal:sysMportal.msg.atMostSet5NavLayout')}",
		fdType_page: "${lfn:message('sys-mportal:sysMportalCpage.fdType.page')}",
		fdType_url: "${lfn:message('sys-mportal:sysMportalCpage.fdType.url')}",
		atLeastSetOnePageOfPublic: "${lfn:message('sys-mportal:sysMportal.msg.atLeastSetOnePageOfPublic')}",
		atLeastSetOnePage: "${lfn:message('sys-mportal:sysMportal.msg.atLeastSetOnePage')}",
		atLeastSetOneChildPage: "${lfn:message('sys-mportal:sysMportal.msg.atLeastSetOneChildPage')}",
		atLeastSetOneChildOfPublic: "${lfn:message('sys-mportal:sysMportal.msg.atLeastSetOneChildOfPublic')}",
		chooseIcon: "${lfn:message('sys-mportal:sysMportal.msg.chooseIcon')}",
	}
	
	DocList_Info.push("TABLE_DocList_type2");
	DocListFunc_Init();
	var sysMportalCompositeValidation = $KMSSValidation();
	//添加关联页面校验
	Com_Parameter.event["submit"].push(function(){
		 return validateAndInitPageMessage();  
	});
</script>

<!-- 页面相关操作  -->
<script>
	//预览
	function previewPortal(){		
		seajs.use([ 'lui/jquery', 'lui/dialog' ],function($, dialog) { 
			dialog.iframe("/sys/mportal/sys_mportal_composite/previewMportal.jsp?fdId=${sysMportalCompositeForm.fdId}","扫码预览",function(rtnData){
				
			},{
				width : 300,
				height : 275
			})
		});
	}
	
	//暂存和预览
	function saveDraftAndPerview(){
		if(sysMportalCompositeValidation.validate() && validateAndInitPageMessage()){
			$.ajax({
				type : "post",
				dataType : "json",
				url : "${ LUI_ContextPath}/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=saveDraftAndPerview",
				data :$("[name='sysMportalCompositeForm']").serialize(),
				success : function(result){
					$("input[name='fdId']").val(result.fdId);
					seajs.use([ 'lui/jquery', 'lui/dialog' ],function($, dialog) { 
						dialog.iframe("/sys/mportal/sys_mportal_composite/previewMportal.jsp?fdId="+result.fdId,"扫码预览",function(rtnData){
							
						},{
							width : 300,
							height : 275
						})
					});
				},
				error : function(result){					
					alert("预览失败");
				}
			});
		}
	}
	
	//选择logo
	function selectLogo(){
		seajs.use(['lui/dialog'],function(dialog){
			var fdLogo = $('[name="fdLogo"]').val(),
				selectUrl = '/sys/mportal/sys_mportal_logo/sysMportalLogo.do?method=select&logo=' + fdLogo;
			dialog.iframe(selectUrl,'<bean:message key="sysMportal.profile.logo.select" bundle="sys-mportal"/>',function(obj){
				if(!obj) {
					return;
				}
				var path = '${LUI_ContextPath}'+obj.src,
					img = $('<img></img>').attr('src',path);
				var imgNode = $("<div class='fdLogoImgBg'></div>").append(img);
				$('.fdLogoImg').html(imgNode);
				$('[name="fdLogo"]').val(obj.src);
			},{"width":650,"height":550});
		});
	}
</script>

<!-- 关联页面相关操作  -->
<script>
	//校验和初始化关联页面信息
	function validateAndInitPageMessage(){
		var layoutType = getLayoutType();
		  $("#__mportlet_pages_info").html("");
		  //底部导航
		  if(layoutType == "2"){	
			  var currentRow = getBottomLayoutLevel1Row();
			  if(currentRow > 5 ){
				dialog.alert(mportalLang["atMostSet5NavLayout"]);
				return;
			  }				  
			  
			  var $allTr = $("#TABLE_DocList_type2 tr[mportal_page_level='1']");				  
			  if($allTr.length <= 0){
				  dialog.alert(mportalLang["atLeastSetOnePage"]);
				  return false;
			  }
			  //判断1级目录是否存在非外部链接页面
			  var hadPage = false;
			  //1:至少应该选中一个关联页面 
			  //2:至少应该存在 非打开新窗口的外部链接页面 或者 公共门户
			  var childErrorObj = null;
			  $allTr.each(function(index,item){
				  var fdId = $(item).find(".td_fdId_hidden_input").val();
				  var fdName = $(item).find("input[name$='fdName']").val();
				  var fdType = $(item).find("input[name$='fdType']").val();
				  var fdCpageType = $(item).find("input[name$='fdCpageType']").val();
				  if(fdCpageType == 1 || fdType == 2){
					  hadPage = true;
					  if(fdType == 2 && !childErrorObj){
						  var $childTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + fdId + "']");
						  if($childTrs.length <= 0){
							  childErrorObj = {};
							  childErrorObj.errorType = 1;
							  childErrorObj.fdName = fdName;
						  }/* else{
							  var childPageHadPublic = false;
							  //判断子页面否存在非外部链接页面 todo
							  $childTrs.each(function(childIndex,childItem){
								  var childFdId = $(childItem).find(".td_fdId_hidden_input").val();
								  var childFdType = $(childItem).find("input[name$='fdType']").val();
								  var childFdCpageType = $(childItem).find("input[name$='fdCpageType']").val();
								  if(childFdCpageType == 1 && !childPageHadPublic){
									  childPageHadPublic = true;
								  }
							  });
							  if(!childPageHadPublic){
								  childErrorObj = {};
								  childErrorObj.errorType = 2;
								  childErrorObj.fdName = fdName;
							  }
						  } */
					  }
				  }
			  });
			  if(!hadPage){
				  dialog.alert(mportalLang["atLeastSetOnePageOfPublic"]);
				  return false;
			  }
			  
			  /* if(childErrorObj){
				  if(childErrorObj.errorType == 1){
					 var message =  mportalLang["atLeastSetOneChildPage"];
					 message = message.replace("%name%",childErrorObj.fdName)
					 dialog.alert(message); 
				  }
				  if(childErrorObj.errorType == 2){
					  var message =  mportalLang["atLeastSetOneChildOfPublic"];
					  message = message.replace("%name%",childErrorObj.fdName)
					  dialog.alert(message); 
				  }
				  return false;
			  }			  */				  
			  				  
			  var tbInfo = DocList_TableInfo["TABLE_DocList_type2"];
			  for(var i=0; i < tbInfo.DOMElement.rows.length; i++){
				  $(tbInfo.DOMElement.rows[i]).find(".td_fdOrder_hidden_input").val(i);
			  }
			  $("#TABLE_DocList_type2").find("input").each(function(index, item){
				    var name = item.name.replace(/.*_pages/,"pages");
				    var value = $(item).val();
					var input = "<input type='hidden' name='" + name + "' value='" + value + "'/>";
				  	$("#__mportlet_pages_info").append(input);
				});
		  }else{ //顶部导航
			  var hadPage = false;
			  var $pages = $("#TABLE_DocList tr[mportal_page_level='1']");
			  if($pages.length <= 0 ){
				  dialog.alert(mportalLang["atLeastSetOnePage"]);
				  return false;
			  }
			  $pages.each(function(index,item){
				  var fdType = $(item).find("input[name$='fdType']").val();
				  var fdCpageType = $(item).find("input[name$='fdCpageType']").val();
				  if(fdCpageType == 1){
					  hadPage = true;
				  }
			  });
			  if(!hadPage){
				  dialog.alert(mportalLang["atLeastSetOnePageOfPublic"]);
				  return false;
			  }
			  var tbInfo = DocList_TableInfo["TABLE_DocList"];
			  for(var i=0; i < tbInfo.DOMElement.rows.length; i++){
				  $(tbInfo.DOMElement.rows[i]).find(".td_fdOrder_hidden_input").val(i);
			  }
			  $("#TABLE_DocList").find("input").each(function(index, item){
				    var name = item.name.replace(/.*_pages/,"pages");
				    var value = $(item).val();
					var input = "<input type='hidden' name='" + name + "' value='" + value + "'/>";
				  	$("#__mportlet_pages_info").append(input);
			 });
		  }
		  if($("#__mportlet_pages_info input").length < 1){
			  dialog.alert(mportalLang["pleaseSetPage"]);
			  return false;
		  }
		  return true;
	}
	
	//变更图标
	function __changeIcon(evt){
		seajs.use([ 'lui/jquery', 'lui/dialog' ],function($, dialog) {
			      var layoutType = getLayoutType();//导航布局
			      var iconTypeRange="2"//（iconTypeRange=2,3 表示弹出框中只显示字体图标和文字选择页签）
			      if(layoutType=="1"){//顶部
			    	  iconTypeRange="2,3"
			      }
                  var $target = $(evt);
                  var dialogUrl = "/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange="+iconTypeRange; 
				  dialog.iframe(dialogUrl,"${lfn:message('sys-mportal:sysMportalCard.select.icons')}",function(returnData) {
					  if (!returnData){
							return;
						}
                      var iconType = returnData.iconType; // 1、图片图标      2、字体图标     3、文字
                      var claz2 = iconType==2 ? returnData.className : "";
                      var text = iconType==3 ? returnData.text : "";
                      $target.html("");
                      if(returnData.url){ //素材库
                          var tUrl = returnData.url;
                          if(tUrl.indexOf("/") == 0){
                              tUrl = tUrl.substring(1);
                          }
                          tUrl = Com_Parameter.ContextPath + tUrl;
                          $target.css({
                              "background": "url('"+tUrl+"') no-repeat center",
                              "background-size": "contain"
                          })
                          var claz1 = $target.attr('claz');
                          $target.removeClass(claz1);
                          $target.addClass("mui");
                          $target.addClass('imgBox');
                          $target.parent().find(".td_icon_hidden_input").val(""); //设置icon为空
                          $target.parent().find(".td_img_hidden_input").val(returnData.url); //设置img
                      }else {
                          var claz1 = $target.attr('claz');
                          $target.removeClass(claz1);
                          $target.addClass("mui");
                          $target.removeClass("td_icon_none");
                          $target.removeAttr("style");
                          if (iconType == 2) {  // 字体图标
                              $target.addClass(claz2);
                              $target.attr('claz', claz2);
                          } else if (iconType == 3) {  // 文字
                              $target.text(text);
                              $target.attr('claz', text);
                          }
                          // 赋值图标隐藏hidden
                          $target.parent().find(".td_icon_hidden_input").val((iconType == 3) ? text : claz2);
                          $target.parent().find(".td_img_hidden_input").val("");
                      }
				},
				{
					width : 600,
					height : 550
				});
		});
	}
	
	
	/*****************************************
	 *     底部页签操作  Starts
	 *     mportal_page_level:1 一级页面
	 *	   mportal_page_level:2 子级页面
	 *     mportal_page_parent_id 父级别页签id
	 */
	
	function __move_up(evt,type){
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var level = $currentTr.attr("mportal_page_level");
		if(level == "2"){
			__moveUp_sub(evt);
		}else if(level == "1"){
			__moveUp_top(evt);
		}
	}
	
	/**
	 * 下移
	 */
	function __move_down(evt){
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var level = $currentTr.attr("mportal_page_level");
		if(level == "2"){
			__moveDown_sub(evt);
		}else if(level == "1"){
			__moveDown_top(evt);
		}
	}
	
	/**
	 * 删除行
	 */
	function __delete_row(evt){
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var parentId = $currentTr.find(".td_fdId_hidden_input").val();
		var childTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + parentId + "']");
		if(childTrs.length > 0){
			if(dialog){
				dialog.confirm(mportalLang["deleteWithExistChild"], function(value) {
					if(value == true) {
						childTrs.each(function(index, item){
							DocList_DeleteRow(item);
						});		
						DocList_DeleteRow($currentTr[0]);
					}
				});
			}			
		}else{
			DocList_DeleteRow($currentTr[0]);
		}
	}
	
	//页面、页签上移
	function __moveUp_top(evt){			
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var $allTr = $("#TABLE_DocList_type2 tr[mportal_page_level='1']");
		var currentIndex = 0;
		var $preTr = null;
		//获取当前页签位置和上一页签元素
		$allTr.each(function(index, item){
			if(item == $currentTr[0]){
				currentIndex = index;
			}
		});
		if((currentIndex < $allTr.length) && (currentIndex >= 1)){
			$preTr = $($allTr[currentIndex-1]);
		}		
		if($preTr != null){
			//1.获取当前页签的子页面数量
			var parentId = $currentTr.find(".td_fdId_hidden_input").val();
			var childTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + parentId + "']");
			//2.获取上一个页签的子页面数量
			var preParentId = $preTr.find(".td_fdId_hidden_input").val();
			var preChildTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + preParentId + "']");
			//3.计算需要移动的距离
			var distance = preChildTrs.length + 1;
			//4.移动页签		
			for( var i=0; i < distance; i++){
				DocList_MoveRow(-1,$currentTr[0]);
			}	
			//5.移动页签下的子页面
			childTrs.each(function(index, item){
				for( var i=0; i < distance; i++){
					DocList_MoveRow(-1,item);
				}	
			});				
		}			
	}		
	
	//页面、页签下移
	function __moveDown_top(evt){			
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var $allTr = $("#TABLE_DocList_type2 tr[mportal_page_level='1']");
		var currentIndex = 0;
		var $nextTr = null;
		//获取当前页签位置和下一页签元素
		$allTr.each(function(index, item){
			if(item == $currentTr[0]){
				currentIndex = index;
			}
		});
		if(currentIndex < $allTr.length-1){
			$nextTr = $($allTr[currentIndex+1]);
		}	
		if($nextTr != null){
			//1.获取当前页签的子页面数量
			var parentId = $currentTr.find(".td_fdId_hidden_input").val();
			var childTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + parentId + "']");
			//2.获取下一个页签的子页面数量
			var nextParentId = $nextTr.find(".td_fdId_hidden_input").val();
			var nextChildTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + nextParentId + "']");
			//3.计算需要移动的距离
			var distance = childTrs.length + 1;
			//4.移动页签			
			for( var i=0; i < distance; i++){
				DocList_MoveRow(-1,$nextTr[0]);
			}	
			//5.移动页签下的子页面
			nextChildTrs.each(function(index, item){
				for( var i=0; i < distance; i++){
					DocList_MoveRow(-1,item);
				}					
			});				
		}
		
	}
	
	//子页面下移
	function __moveDown_sub(evt){
		var indexInfo = getBottomLayoutSubRowIndex(evt);
		var size = indexInfo.size;
		var index = indexInfo.index;
		if(index < size - 1){
			DocList_MoveRow(1);
		}
	}
	
	//子页面上移
	function __moveUp_sub(evt){
		var indexInfo = getBottomLayoutSubRowIndex(evt);
		var size = indexInfo.size;
		var index = indexInfo.index
		var moveDiff = index - 1;
		if(moveDiff >= 0){
			DocList_MoveRow(-1);
		}			
	}
	
	//获取子页面下标
	function getBottomLayoutSubRowIndex(evt){
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var parentId = $currentTr.attr("mportal_page_parent_id");
		var $allTr = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + parentId + "']");
		var currentIndex = 0;
		$allTr.each(function(index, item){
			if(item == $currentTr[0]){
				currentIndex = index;
			}
		});
		return {
			index: currentIndex,
			size: $allTr.length
		}
	}
	
	//获取顶级页面下标
	function getBottomLayoutTopRowIndex(evt){
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var $allTr = $("#TABLE_DocList_type2 tr[mportal_page_level='1']");
		var currentIndex = 0;
		$allTr.each(function(index, item){
			if(item == $currentTr[0]){
				currentIndex = index;
			}
		});
		return {
			index: currentIndex,
			size: $allTr.length
		}
	}
	
	 function getBottomLayoutLevel1Row(){
		var levle1 = $("#TABLE_DocList_type2 tr[mportal_page_level=1]");
		return levle1.length;
	}
	 
	/**
	 *    底部导航操作  Ends
	 ****************************************/		 
	 
	
	/***********************
	 * 关联页面 Starts
	 */
	 
	function selectPage(evt,type){
		var layoutType = getLayoutType();
		if(type != "addSub" && layoutType != "1"){
			 var currentRow = getBottomLayoutLevel1Row();
			 if(currentRow >=5 ){
				dialog.alert(mportalLang["atMostSet5NavLayout"]);
				return;
			 }		
		}
		
		seajs.use(['lui/dialog'],function(dialog){
			dialog.iframe('/sys/mportal/sys_mportal_cpage/sysMportalCpage_dialog.jsp',
					"${ lfn:message('sys-mportal:sysMportal.btn.relationPage') }",function(val) {
				if(val != null && val != false && val.length && val.length > 0){
					addPage(val, evt, type);
				}
			},{"width":650,"height":550}); 
		});
	}
	
	//添加页面
	function addPage(data, evt, type) {
		var layoutType = getLayoutType();
		if(type == "addSub"){
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				addBottomLayoutSubRow(row, evt);
			}
			//设置页签的默认标题
			setDefaultTabName(evt);
		}else{
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				if(layoutType == "1"){
				    //导航布局-顶部
					addTopLayoutRow(row, evt);
				}else{
				    //底部
					addBottomLayoutRow(row, evt);
				}	
			}
		}			
	}
	
	/**
	  *设置默认标签名称
	  */
	function setDefaultTabName(evt){
		var $target = $(evt);
		var $currentTr = $target.closest("tr");
		var parentId = $currentTr.find(".td_fdId_hidden_input").val();
		var childTrs = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + parentId + "']");
		if(childTrs.length > 0){
			//该页签的名称
			var tabName=$currentTr.find('input[name$="fdName"]').val();
			if(tabName==''){
				//将页签第一个页面标题设置为页签的默认标题
				var firstPageName=$(childTrs[0]).find('input[name$="fdName"]').val();
				$currentTr.find('input[name$="fdName"]').val(firstPageName);
			}
		}
	}
	
	/**
	 * 增加顶部布局行
	 */
	function addTopLayoutRow(row, evt){
        seajs.use(['lui/dialog','lui/util/str'],function(dialog,str){
		var rowData = {
				'type1_pages[!{index}].sysMportalCompositeId' : '${sysMportalCompositeForm.fdId}',
				'type1_pages[!{index}].sysMportalCpageId' : row.id,
				'type1_pages[!{index}].fdId' : row.temporaryFdId,
				'type1_pages[!{index}].fdName' : row.name,
				'type1_pages[!{index}].fdIcon' : row.icon,
                'type1_pages[!{index}].fdImg' : row.img,
				'type1_pages[!{index}].fdType' : 1,
				'type1_pages[!{index}].fdCpageType' : row.type
			};
		var newRow = DocList_AddRow('TABLE_DocList', null, rowData);
		LUI.$(newRow).attr("mportal_page_level",1);
		LUI.$(newRow).find(".lui_oname").text("[" + row.name + "]");
		if(row.icon) {
            LUI.$(newRow).find(".td_icon").addClass(row.icon);
            LUI.$(newRow).find(".td_icon").attr("claz", row.icon);
            LUI.$(newRow).find(".td_icon").html(row.icon);
        }else{ //素材库的图标
            LUI.$(newRow).find(".td_icon").addClass("imgBox");
            var tUrl = row.img;
            if(tUrl.indexOf("/") == 0){
                tUrl = tUrl.substring(1);
            }
            tUrl = Com_Parameter.ContextPath + tUrl;
            //对url解码
            tUrl = str.decodeHTML(tUrl);

            LUI.$(newRow).find(".td_icon").css({
                "background": "url('"+tUrl+"') no-repeat center",
                "background-size": "contain"
            });
        }
		var typeName = "";
		if(row.type == 1){
			typeName = mportalLang["fdType_page"];
		}else{
			typeName = mportalLang["fdType_url"];
		}
		LUI.$(newRow).find(".td_type").html(mportalLang["page"] + "：" + typeName);
        });
	}
	
	/**
	 * 增加底部布局行
	 */
	function addBottomLayoutRow(row, evt){
        seajs.use(['lui/dialog','lui/util/str'],function(dialog,str){
		var rowData = {
				'type2_pages[!{index}].sysMportalCompositeId' : '${sysMportalCompositeForm.fdId}',
				'type2_pages[!{index}].sysMportalCpageId' : row.id,
				'type2_pages[!{index}].fdId' : row.temporaryFdId,
				'type2_pages[!{index}].fdName' : row.name,
				'type2_pages[!{index}].fdIcon' : row.icon,
                'type2_pages[!{index}].fdImg' : row.img,
				'type2_pages[!{index}].fdType' : 1,
				'type2_pages[!{index}].fdCpageType' : row.type
			};
			var newRow = DocList_AddRow('TABLE_DocList_type2', null, rowData);
			LUI.$(newRow).attr("mportal_page_level",1);
			LUI.$(newRow).find(".lui_oname").text("[" + row.name + "]");
			debugger;
            if(row.icon) {
                LUI.$(newRow).find(".td_icon").addClass(row.icon);
                LUI.$(newRow).find(".td_icon").attr("claz",row.icon);
                LUI.$(newRow).find(".td_icon").html(row.icon);
            }else { //素材库的图标
                LUI.$(newRow).find(".td_icon").addClass("imgBox");
                var tUrl = row.img;
                if(tUrl.indexOf("/") == 0){
                    tUrl = tUrl.substring(1);
                }
                tUrl = Com_Parameter.ContextPath + tUrl;
                //对url解码
                tUrl = str.decodeHTML(tUrl);
                LUI.$(newRow).find(".td_icon").css({
                    "background": "url('"+tUrl+"') no-repeat center",
                    "background-size": "contain"
                });
            }
			LUI.$(newRow).find(".td_addSubPage").remove();
			var typeName = "";
			if(row.type == 1){
				typeName = mportalLang["fdType_page"];
			}else{
				typeName = mportalLang["fdType_url"];
			}
			LUI.$(newRow).find(".td_type").html(mportalLang["page"] + "：" + typeName);
        });
	}
	
	/**
	 * 增加底部布局子行
	 */
	function addBottomLayoutSubRow(row, evt){
        seajs.use(['lui/dialog','lui/util/str'],function(dialog,str) {
            var $target = $(evt);
            var parentTr = $target.closest("tr");
            var parentId = parentTr.find(".td_fdId_hidden_input").val();
            var rowData = {
                'type2_pages[!{index}].fdParentId': parentId,
                'type2_pages[!{index}].sysMportalCpageId': row.id,
                'type2_pages[!{index}].fdId': row.temporaryFdId,
                'type2_pages[!{index}].fdName': row.name,
                'type2_pages[!{index}].fdIcon': row.icon,
                'type2_pages[!{index}].fdImg': row.img,
                'type2_pages[!{index}].fdType': 3,
                'type2_pages[!{index}].fdCpageType': row.type
            };

            var typeName = "";
            if (row.type == 1) {
                typeName = mportalLang["fdType_page"];
            } else {
                typeName = mportalLang["fdType_url"];
            }

            var $target = $(evt);
            var $currentTr = $target.closest("tr");
            var parentId = $currentTr.find(".td_fdId_hidden_input").val();
            var $allTr = $("#TABLE_DocList_type2 tr[mportal_page_parent_id='" + parentId + "']");
            var childrenSize = $allTr.length;
            var newRow = DocList_AddRow('TABLE_DocList_type2', null, rowData);

            //获取当前所在行位置
            //获取子页面所在行位置
            //计算需要上移位置
            //进行上移操作
            var tbInfo = DocList_TableInfo["TABLE_DocList_type2"];
            var parentIndex = DocList_GetRowIndex(tbInfo, $currentTr[0]);
            var index = DocList_GetRowIndex(tbInfo, newRow);
            var diff = index - parentIndex - 1 - childrenSize;
            if (diff > 0) {
                for (var i = 0; i < diff; i++) {
                    DocList_MoveRow(-1, newRow);
                }
            }

            LUI.$(newRow).attr("mportal_page_level", 2);
            LUI.$(newRow).attr("mportal_page_parent_id", parentId);
            LUI.$(newRow).find(".lui_oname").text("[" + row.name + "]");
            if (row.icon) {
                LUI.$(newRow).find(".td_icon").addClass(row.icon);
                LUI.$(newRow).find(".td_icon").attr("claz", row.icon);
                LUI.$(newRow).find(".td_icon").html(row.icon);
            } else { //素材库图标
                LUI.$(newRow).find(".td_icon").addClass("imgBox");
                var tUrl = row.img;
                if (tUrl.indexOf("/") == 0) {
                    tUrl = tUrl.substring(1);
                }
                tUrl = Com_Parameter.ContextPath + tUrl;
                //对url解码
                tUrl = str.decodeHTML(tUrl);
                LUI.$(newRow).find(".td_icon").css({
                    "background": "url('" + tUrl + "') no-repeat center",
                    "background-size": "contain"
                });
            }
            LUI.$(newRow).find(".td_type").html(typeName);
            //变更样式
            LUI.$(newRow).find(".td_addSubPage").remove();
            LUI.$(newRow).css("border", "none");
            LUI.$(newRow).find("td").css("border", "none");
            $(LUI.$(newRow).find("td")[0]).css("padding-left", "20px");
        })
	}

   	//增行生成id
    function getGenerateID(rtnData, newRow) {
	     if (rtnData.GetHashMapArray().length >= 1) {
	     		var obj = rtnData.GetHashMapArray()[0];
	     		var fdId = obj['fdId'];
	     		var tbInfo = DocList_TableInfo["TABLE_DocList"];
	     		var index = DocList_GetRowIndex(tbInfo, newRow);	     		
	     		$(newRow).find(".td_fdId_hidden_input").val(fdId);	
	     }
    }
	
	/**
	 * 关联页面 Ends
	 **********************/
	 
	 
	 //添加页签
	 function __addTab(){
		 var currentRow = getBottomLayoutLevel1Row();
		 if(currentRow >=5 ){
			dialog.alert(mportalLang["atMostSet5NavLayout"]);
			return;
		 }
		 var rowData = {
					'type2_pages[!{index}].sysMportalCompositeId' : '${sysMportalCompositeForm.fdId}',						
					'type2_pages[!{index}].fdType' : 2,
		  };
		 var newRow = DocList_AddRow('TABLE_DocList_type2', null, rowData);	
		 LUI.$(newRow).attr("mportal_page_level",1);
		 LUI.$(newRow).css("border-bottom-style","dotted");
		 LUI.$(newRow).find(".td_icon").addClass("td_icon_none");
		 LUI.$(newRow).find(".td_icon").removeClass("mui");
		 LUI.$(newRow).find(".td_icon").html(mportalLang["chooseIcon"]);
		 LUI.$(newRow).find(".td_type").html(mportalLang["tab"]);	
		//服务器生产ID
	    var url = "sysMportalGenerateId";//获取ID
	    var data = new KMSSData();
	    data.SendToBean(url, function(rtnData){
	    	 getGenerateID(rtnData, newRow);
	    });
	}
	 
	
	
	
	//隐藏顶部导航校验
	function hideTopLayoutValidate(){
		var tbInfo = DocList_TableInfo["TABLE_DocList"];
		 for(var i=1; i < tbInfo.DOMElement.rows.length; i++){
			 var $nameInput = $(tbInfo.DOMElement.rows[i]).find("input[name$='fdName']");					
			 if($nameInput.length > 0){
				 $nameInput.attr("validate","");					
			 }
		 }
	}
	
	//展示顶部导航校验
	function showTopLayoutValidate(){
		var tbInfo = DocList_TableInfo["TABLE_DocList"];
		 for(var i=1; i < tbInfo.DOMElement.rows.length; i++){
			 var $nameInput = $(tbInfo.DOMElement.rows[i]).find("input[name$='fdName']");
			 if($nameInput.length > 0){
				 $nameInput.attr("validate","required maxLength(100)")
			 }
		 }
	}
	
	//隐藏底部导航校验
	function hideBottomLayoutValidate(){
		 var tbInfo = DocList_TableInfo["TABLE_DocList_type2"];
		 for(var i=1; i < tbInfo.DOMElement.rows.length; i++){
			 var $nameInput = $(tbInfo.DOMElement.rows[i]).find("input[name$='fdName']");
			 var $iconInput = $(tbInfo.DOMElement.rows[i]).find(".td_icon_validate");				 
			 if($nameInput.length > 0){
				 $nameInput.attr("validate","");
				 $iconInput.attr("validate","");
			 }
		 }
	}
	
	//展示底部导航校验
	function showBottomLayoutValidate(){
		 var tbInfo = DocList_TableInfo["TABLE_DocList_type2"];
		 for(var i=1; i < tbInfo.DOMElement.rows.length; i++){
			 var $nameInput = $(tbInfo.DOMElement.rows[i]).find("input[name$='fdName']");
			 var $iconInput = $(tbInfo.DOMElement.rows[i]).find(".td_icon_validate");
			 if($nameInput.length > 0){
				 $nameInput.attr("validate","required maxLength(100)");
				 $iconInput.attr("validate","required");
			 }
		 }
	}

	//获取导航布局类型
	function getLayoutType(){
		return $("input[name='fdNavLayout']:checked").val();
	}
	
	//导航布局变更
	function __changeNavLayout(value){
		if (value == "1") {
			$("#__mportlet_pages_fdNavLayout_type1").show();
			$("#__mportlet_pages_fdNavLayout_type2").hide();
			$("#__mportlet_readers").show();
			$("#__mportlet_editors").show();
			hideBottomLayoutValidate();
			showTopLayoutValidate();
		} else if (value == "2") {
			$("#__mportlet_pages_fdNavLayout_type2").show();
			$("#__mportlet_pages_fdNavLayout_type1").hide();
			$("#__mportlet_readers").show();
			$("#__mportlet_editors").show();
			showBottomLayoutValidate();
			hideTopLayoutValidate();
		}
	}

</script>
	