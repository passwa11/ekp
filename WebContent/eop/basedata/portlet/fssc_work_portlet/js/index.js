$(document).ready(function () {
            //选项卡切换事件
            $(".demo_fssc_work_nav_tabs_item").on("click", function (e) {
                var pageno = GetUrlParam('pageno');
                var rowsize = GetUrlParam('rowsize');

                $(this).siblings().removeClass("select");
                $(this).addClass("select");

                var workId = $(this).data("id");//获取工作台的ID，重要

                $(".demo_fssc_work_content_wrap").hide();
                if (workId == "demoFsscWork01") {
                    $("#demoFsscWorkContent01").show();
                    
                } else if (workId == "demoFsscWork02") {
                    $("#demoFsscWorkContent02").show();


                } else if (workId == "demoFsscWork03") {
                    $("#demoFsscWorkContent03").show();
                }
                
                demoGetListData(pageno, rowsize, workId);//加载数据列表
            });

            //全选事件
            $('input[name="List_all_Selected"]').click(function(){
            //当全选按钮是选中状态
                if($(this).is(':checked')){
                    //循环下面所有checkbox
                    $('input[name="List_Selected"]').each(function(){  
                        //将checkbox状态改为选中
                        $(this).prop("checked",true); 
                    });  
                }else{  
                    $('input[name="List_Selected"]').each(function(){  
                        $(this).prop("checked",false);  
                });  
                }  
            }); 
            demoGetlistParam();
        });
//获取URL参数
function GetUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");
    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;
        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");
            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}

//接口数据格式处理
function jsonGetValue(itemData, name,isSub) {
    if (itemData.length > 0) {
    	var value=itemData[0][name]?itemData[0][name]:"";
    	if(isSub){
    		return value.length>20?value.substring(0,20)+'....':value;
    	}else{
    		return value;
    	}
    }
}

// 生成百分比环形图
function renderRinglike(doneNum, todoNum, elementId, ringlikeColor) {
    var Ringlike = document.getElementById(elementId);
    var Ringlike = echarts.init(Ringlike);
    var option = null;
    option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'vertical',
            left: 10,
            data: ['已完成'],
            show: false
        },
        series: [
            {
                name: '任务完成率',
                type: 'pie',
                radius: ['80%', '100%'],
                avoidLabelOverlap: false,
                hoverAnimation: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                tooltip: {
                    show: false
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    { value: doneNum, name: '已处理', itemStyle: { color: ringlikeColor } },
                    { value: todoNum, name: '未处理', itemStyle: { color: "#F0F2F2" }, emphasis: { itemStyle: { color: "#F0F2F2" } } }
                ]
            }
        ]
    };
    if (option && typeof option === "object") {
        Ringlike.setOption(option, true);
    }
}

//填入任务统计栏的数据
function buildTask(elementId, taskData) {
    $("#" + elementId + " .demo_fssc_work_task_total_num").html("<span>" + taskData.total + "</span>"); //填入待处理总数

    $("#" + elementId + " .demo_fssc_work_task_item").remove();//清空图表

    if (taskData && taskData.datas && taskData.datas.length > 0) {
        for (var i = 0; i < taskData.datas.length; i++) {
            var els = taskData.datas[i];
            var done = els.total - els.todo;//已完成的任务数
            var ringlikeColor = ["#4285F4", "#0DC47B", "#FF943E", "#8D7AFB"]; //圆环颜色-蓝/青/黄

            //拼接任务统计栏的HTML
            var taskItemHtml = $('<div class="demo_fssc_work_task_item" style="width:' + Math.floor((100 - 19) * 100 / taskData.datas.length) / 100 + '%"></div>');
            var taskItemleft = $('<div class="demo_fssc_work_task_item_left"><div class="demo_fssc_work_task_item_sub"><span>' + els.title + '</span></div><div class="demo_fssc_work_task_item_num"><span class="demo_fssc_work_task_item_num1" style="color:'+ringlikeColor[i]+'">' + els.todo + '</span><span>/</span><span>' + els.approved + '</span></div><div class="demo_fssc_work_task_item_notes"><span>待审/已审</span></div></div>');
            var taskItemRight = $('<div class="demo_fssc_work_task_item_right" title="本月完成率"><div class="demo_fssc_work_task_item_ringlike" id="' + elementId + '_ringlike' + [i] + '"></div><div class="demo_fssc_work_task_ringlike_text"><span>完成率</span><span class="demo_fssc_work_task_ringlike_num">' + (els.total>0?Math.floor(done * 100 / els.total):100) + '%</span></div></div>');
            taskItemHtml.append(taskItemleft);
            taskItemHtml.append(taskItemRight);
            $("#" + elementId + " .demo_fssc_work_panel_content").append(taskItemHtml);
          //生成百分比环状图
            if(els.total>0){
            	renderRinglike(done, els.todo, elementId + "_ringlike" + [i], ringlikeColor[i]);
            }else{
            	renderRinglike(100, 0, elementId + "_ringlike" + [i], ringlikeColor[i]);
            }
            
        }
    }
}


var listData = {
    "columns": [
        {
            "property": "fdId"
        },
        {
            "title": "标题",
            "property": "docSubject"
        },
        {
            "title": "所属模块",
            "property": "module"
        },
        {
        	"title": "单据类别",
        	"property": "type"
        },
        {
            "title": "本币金额",
            "property": "money"
        },
        {
            "title": "所属部门",
            "property": "department"
        },
        {
            "title": "申请人",
            "property": "fdName"
        },
        {
            "title": "申请时间",
            "property": "docPublishTime"
        },
        {
            "title": "状态",
            "property": "state"
        },
        {
            "title": "链接",
            "property": "url"
        }
    ],
    "datas": [],
    "page": {}
}


//列表数据获取
function demoGetListData(pageno, rowsize, workId) {
    var pageno = pageno ? pageno : 1;
    var rowsize = rowsize ? rowsize : 10;
    
    var dataUrl = "";

    //处理列表的按钮选项
    $(".demo_fssc_work_panel_btns").empty();
    if (workId == "demoFsscWork01") {
    	//审计会计岗
        $(".demo_fssc_work_panel_btns").append('<div class="demo_fssc_work_panel_btns_item"><a href="#" target="_blank">批量审核</a></div>');
        dataUrl = formInitData['LUI_ContextPath']+"/eop/basedata/eop_basedata_portlet/eopBasedataPortlet.do?method=listApproval&postType=shenhe&pageno="+pageno+"&rowsize="+rowsize;//会计制证岗
        
    } else if (workId == "demoFsscWork02") {
    	//出纳岗
        $(".demo_fssc_work_panel_btns").append('<div class="demo_fssc_work_panel_btns_item"><a href="#" target="_blank">推送网银</a></div>');
        $(".demo_fssc_work_panel_btns").append('<div class="demo_fssc_work_panel_btns_item"><a href="#" target="_blank">导出网银</a></div>');
        $(".demo_fssc_work_panel_btns").append('<div class="demo_fssc_work_panel_btns_item"><a href="#" target="_blank">批量审核</a></div>');
        dataUrl = formInitData['LUI_ContextPath']+"/eop/basedata/eop_basedata_portlet/eopBasedataPortlet.do?method=listApproval&postType=chuna&pageno="+pageno+"&rowsize="+rowsize;//出纳岗

    } else if (workId == "demoFsscWork03") {
    	//财务会计岗
        $(".demo_fssc_work_panel_btns").append('<div class="demo_fssc_work_panel_btns_item"><a href="#" target="_blank">合并记账</a></div>');
        $(".demo_fssc_work_panel_btns").append('<div class="demo_fssc_work_panel_btns_item"><a href="#" target="_blank">批量记账</a></div>');
        dataUrl = formInitData['LUI_ContextPath']+"/eop/basedata/eop_basedata_portlet/eopBasedataPortlet.do?method=listApproval&postType=zhangwu&pageno="+pageno+"&rowsize="+rowsize;//财务审核
    }
    
    $.ajax({
          type: 'post',
          url:dataUrl,
          async:false,
      }).success(function (data) {
    	  console.log("listData", data.datas);
          $("#demoFsscWorkDatalist tbody").empty();
          if(data){
        	  data=JSON.parse(data);
          }
          listData.page=data.page;
          listData.datas=data.datas;
          if (listData.datas&&listData.datas.length > 0) {
        	  $("#demoFsscWorkDatalist tbody").html();
        	  $("#titleDiv").show();
              for (var i = 0; i < listData.datas.length; i++) {
                  var trItem = listData.datas[i];
                  var trContent = '<tr fssc_fdid="' + jsonGetValue(trItem, "fdId",false) + '" onclick="window.open(\''+formInitData['LUI_ContextPath']+(jsonGetValue(trItem, "url",false)?jsonGetValue(trItem, "url",false):"#")+'\');">';
                  trContent += '<td><input type="checkbox" name="List_Selected" value="'+ jsonGetValue(trItem, "fdId",false) +'" data-lui-mark="table.content.checkbox"></input>';
                  trContent += '<td>' + (i+1) + '</td>';
                  trContent += '<td style="text-align:left;padding:0 8px;"> <span class="com_subject">' + jsonGetValue(trItem, "docSubject",true) + '</span> </td>';
                  trContent += '<td class="td_style01">' + jsonGetValue(trItem, "module",true) + '</td>';
                  trContent += '<td class="td_style01">' + jsonGetValue(trItem, "type",true) + '</td>';
                  trContent += '<td style="text-align:left;">' + jsonGetValue(trItem, "department",true) + '</td>';
                  trContent += '<td>' + jsonGetValue(trItem, "fdName",true) + '</td>';
                  trContent += '<td style="text-align:left;">' + jsonGetValue(trItem, "docPublishTime",true) + '</td>';
                  trContent += '<td style="text-align:right;"> ' + jsonGetValue(trItem, "money",true) + ' </td>';
                  trContent += '<td><span class="item_state">' + jsonGetValue(trItem, "state",true) + '</span> </td>';
                  trContent += '</tr>';
                  $("#demoFsscWorkDatalist tbody").append(trContent);
              }
              var page=data.page;
              if(page){
            	  var currentPage=page['currentPage'];
            	  var totalSize=page['totalSize'];
            	  $("[name='pageno']").val(currentPage);
                  $("[name='rowsize']").val(page['pageSize']);
                  $("#totalRowSize").html('共'+totalSize+'条');
                  var yeshu=Math.ceil(totalSize/page['pageSize']);  //向上取整
                  var pageHtml="";
                  if(currentPage!=1){
        			  pageHtml+="<div class=\"lui_paging_next_left\">";
        			  pageHtml+=" <div class=\"lui_paging_next_right\">";
        			  pageHtml+="<div class=\"lui_paging_next_center\">";
        			  pageHtml+=" <li class=\"lui_paging_next\"> <a href=\"javascript:prePage();\"";
        			  pageHtml+="\">上一页</a> </li>";
        			  pageHtml+="  </div> </div></div>";
        		  }
            	  for(var n=1;n<=yeshu;n++){
            		  pageHtml+="<div class=\"lui_paging_num_left ";
            		  if(currentPage==n){
            			  pageHtml+=" selected";
            		  }
            		  pageHtml+=" \"><div class=\"lui_paging_num_right\">";
            		  pageHtml+="<div class=\"lui_paging_num_center\">";
            		  pageHtml+=" <li><a href=\"javascript:openPage('"+n+"');\" data-lui-paging-num=\""+n+"\">"+n+"</a>";
            		  pageHtml+=" </li></div></div></div>";
            	  }
            	  if(currentPage!=yeshu&&yeshu>1){
        			  pageHtml+="<div class=\"lui_paging_next_left\">";
        			  pageHtml+=" <div class=\"lui_paging_next_right\">";
        			  pageHtml+="<div class=\"lui_paging_next_center\">";
        			  pageHtml+=" <li class=\"lui_paging_next\"> <a href=\"javascript:nextPage();\"";
        			  pageHtml+="\">下一页 </a> </li>";
        			  pageHtml+="  </div> </div></div>";
        		  }
            	  $("#pageDiv").html('');
            	  $("#pageDiv").html(pageHtml);
            	  $(".lui_paging_content_centre").attr('style','display:block;');
              }
              var approveObj=data.approve;
              var approvedObj=data.approved;
              var taskData = {
        		    total: page['totalSize'],
        		    datas: [
        		        {
        		            title: "付款单审核",
        		            todo: approveObj['payment'],
        		            approved:approvedObj['payment'],
        		            total: approveObj['payment']*1+approvedObj['payment']*1
        		        }, {
        		            title: "报销单审核",
        		            todo: approveObj['expense'],
        		            approved:approvedObj['expense'],
        		            total: approveObj['expense']*1+approvedObj['expense']*1
        		        }, {
        		            title: "借款单审核",
        		            todo: approveObj['loan'],
        		            approved:approvedObj['loan'],
        		            total: approveObj['loan']*1+approvedObj['loan']*1
        		        }
        		    ]
        		}
          } else {
              console.log("列表数据为空");
              $("#titleDiv").hide();
              $(".lui_paging_content_centre").attr('style','display:none;');
              $("#demoFsscWorkDatalist tbody").append('<tr><td colspan="20"><div class="demo_err_info"><div class="list_nullimg"></div><span>您暂时没有待处理的任务呦～</span></div></td></tr>');
              var taskData = {
          		    total: 0,
          		    datas: [
          		        {
          		            title: "付款单审核",
          		            todo: 0,
          		            approved:0,
          		            total: 0
          		        }, {
          		            title: "报销单审核",
          		            todo: 0,
          		            approved:0,
          		            total: 0
          		        }, {
          		            title: "借款单审核",
          		            todo: 0,
          		            approved:0,
          		            total: 0
          		        }
          		    ]
          		}
          }
          var workId=$("[class='demo_fssc_work_nav_tabs_item select']").attr('data-id');
          buildTask(workId.replace('demoFsscWork','demoFsscWorkContent'), taskData);//加载第一个统计任务栏
      }).error(function (data) {
    	  console.log("获取数据有误");
          $("#demoFsscWorkDatalist tbody").append('<tr><td colspan="20"><div class="demo_err_info"><div class="list_errimg"></div><span>获取数据有误</span></div></td></tr>');
      })
}
//打开对应的页数数据
function openPage(pageno){
	var workId=$("[class='demo_fssc_work_nav_tabs_item select']").attr('data-id');
	var rowsize=$("[name='rowsize']").val();
	demoGetListData(pageno, rowsize, workId);
}
//下一页
function nextPage(){
	var pageno=$("[name='pageno']").val();  //当前页码
	var workId=$("[class='demo_fssc_work_nav_tabs_item select']").attr('data-id');
	var rowsize=$("[name='rowsize']").val();
	demoGetListData(pageno*1+1, rowsize, workId);
}
//上一页
function prePage(){
	var pageno=$("[name='pageno']").val();  //当前页码
	var workId=$("[class='demo_fssc_work_nav_tabs_item select']").attr('data-id');
	var rowsize=$("[name='rowsize']").val();
	demoGetListData(pageno*1-1, rowsize, workId);
}

//初始化ID参数
function demoGetlistParam() {
    var pageno = GetUrlParam('pageno');
    var rowsize = GetUrlParam('rowsize');
    demoGetListData(pageno, rowsize,"demoFsscWork01");//加载第一个列表
}

