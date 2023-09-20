$(document).ready(function () {
	demoGetSumData();
        });


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
                    { value: doneNum, name: '', itemStyle: { color: ringlikeColor } },
                    { value: todoNum, name: '', itemStyle: { color: "#F0F2F2" }, emphasis: { itemStyle: { color: "#F0F2F2" } } }
                ]
            }
        ]
    };
    if (option && typeof option === "object") {
        Ringlike.setOption(option, true);
    }
}

//填入任务统计栏的数据
function buildTask(taskData) {
    $(" .demo_fssc_work_task_total_num").html("<span>" + taskData.total + "</span>"); //填入待处理总数

    $(" .demo_fssc_work_task_item").remove();//清空图表

    
    if (taskData && taskData.datas && taskData.datas.length > 0) {
        for (var i = 0; i < taskData.datas.length; i++) {
            var els = taskData.datas[i];
            var done ='0';//已完成的任务数
            var ringlikeColor = ["#4285F4", "#0DC47B", "#FF943E", "#8D7AFB"]; //圆环颜色-蓝/青/黄

            //拼接任务统计栏的HTML
            var taskItemHtml = $('<div class="demo_fssc_work_task_item" style="width:' + Math.floor((100 - 19) * 100 / taskData.datas.length) / 100 + '%"></div>');
            var taskItemleft = $('<div class="demo_fssc_work_task_item_left"><div class="demo_fssc_work_task_item_sub"><span>' + els.title 
            		+ '</span></div>');
            var taskItemRight = "";
            if(i==0){
            	 taskItemRight = $('<div class="demo_fssc_work_task_item_right" title=""><div class="demo_fssc_work_task_item_ringlike" id="' + 
                 		'ringlike' + [i] + '"></div><div class="demo_fssc_work_task_ringlike_text" style="padding-top:32px;"><span class="demo_fssc_work_task_ringlike_num">' 
                 		+ els.amount + '</span></div></div>');
            }else{
                if(els.totalAmount=='0'){
                    els.amount='0';
                }
                var taskItemRight = $('<div class="demo_fssc_work_task_item_right" title="百分比"><div class="demo_fssc_work_task_item_ringlike" id="' + 
                		'ringlike' + [i] + '"></div><div class="demo_fssc_work_task_ringlike_text"><span>百分比</span><span class="demo_fssc_work_task_ringlike_num">'
                		+ (els.amount>0?Math.round(els.amount * 100 / els.totalAmount):0) + '%</span></div></div>');

            }

            taskItemHtml.append(taskItemleft);
            taskItemHtml.append(taskItemRight);
            $(" .demo_fssc_work_panel_content").append(taskItemHtml);
          //生成百分比环状图
            if(els.amount>0){
            	console.log("(els.amount"+els.amount+"els.totalAmount"+els.totalAmount);
            	renderRinglike(els.amount, els.totalAmount-els.amount, "ringlike" + [i], ringlikeColor[i]);
            }else{
            	renderRinglike(100, 0,   "ringlike" + [i], ringlikeColor[i]);
            }
            
        }
    }
}



//统计数据获取
function demoGetSumData() {
    var dataUrl = "";
    dataUrl=formInitData['LUI_ContextPath']+"/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=listPersonSumData";//总额数据
    $.ajax({
          type: 'post',
          url:dataUrl,
          async:false,
      }).success(function (data) {
    	  console.log("data", data);
          if(data){
        	 data=JSON.parse(data);
          }

          var taskData = {
      		    total: data['totalCount'],
      		    datas: [
      		        {
      		            title: "借款金额",
      		            amount: data['loanAmount']
      		       
      		        }, {
      		            title: "已还",
      		            amount: data['repAmount'],
      		            totalAmount:data['loanAmount']
      		       
      		        }, {
      		            title: "未还",
      		            amount: data['notRepAmount'],
      		            totalAmount:data['loanAmount']
      		        }
      		    ]
      		};
          buildTask( taskData);//加载统计任务栏
      }).error(function (data) {
    	  console.log("获取数据有误");
      })
}



