

// 切换tab面板
$(".fc-apMain-header").on('click','li',function(){
  var currentTabLi = $(this).index();
  $(".fc-apMain-header ul li").removeClass("fc-apMain-currentTab");
  $(".fc-apMain-header ul li").eq(currentTabLi).addClass("fc-apMain-currentTab");
  $(".fc-apMain-content").removeClass("displayStatus");
  $(".fc-apMain-content").eq(currentTabLi).addClass("displayStatus");
})


// 面板收缩
$(".fc-apMain").on('click','.itemHeader-icon',function(){
  var currentIconStatus = $(this);// 按钮本身
  var readyToClosedDiv = $(this).parent().parent().find(".fc-apMain-content-itemContent"); //当前按钮下要关闭的div
  var readyToClosedDivHeight = readyToClosedDiv.height(); // 拿到当前高度

  if(readyToClosedDivHeight != 0){
    readyToClosedDiv.css("max-height","0");
    readyToClosedDiv = readyToClosedDiv.css("overflow-y","hidden");
   
  }else{
    readyToClosedDiv.css("max-height",'9999px');
  }

    // 改变图标样式
  if(currentIconStatus.hasClass("itemHeader-icon-rotation")){
    currentIconStatus.removeClass("itemHeader-icon-rotation");
  }else{
    currentIconStatus.addClass("itemHeader-icon-rotation");
  }
})


// 单据相关信息 图表赋值
function getTotalApplicationLegend(){
        var totalApplicationMount = $("#totalApplicationMount").text(); // 申请总额
        var notApplicationMount =$("#notApplicationMount").text(); // 未报销
        var underApplicationMount = $("#underApplicationMount").text()// 报销中 
        var finishedApplicationMount = $("#finishedApplicationMount").text()// 已报销

        // 计算得到占比，并且给样式赋值
        var notReimbursed = getPercentByProportion(notApplicationMount,totalApplicationMount);// 未报销
        var reimbursement = getPercentByProportion(underApplicationMount,totalApplicationMount);// 报销中
        var reimbursed = getPercentByProportion(finishedApplicationMount,totalApplicationMount);// 报销中
        $("#not-reimbursed").css("width",notReimbursed);
        $("#reimbursement").css("width",reimbursement);
        $("#reimbursed").css("width",reimbursed);

        var totalApplyMount = $("#totalApplyMount").text(); //借款总额
        var notApplyMount = $("#notApplyMount").text()// 可冲抵
        var underApplyMount = $("#underApplyMount").text()// 还款中 
        var finishedApplyMount = $("#finishedApplyMount").text()// 已还款


        var notFlushable = getPercentByProportion(notApplyMount,totalApplyMount);// 可冲抵
        var replaying = getPercentByProportion(underApplyMount,totalApplyMount);// 还款中
        var replayed = getPercentByProportion(finishedApplyMount,totalApplyMount);// 已还款
        $("#not-flushable").css("width",notFlushable);
        $("#replaying").css("width",replaying);
        $("#replayed").css("width",replayed);
        
}

getTotalApplicationLegend();

// 得到百分百，保留两位小数
function getPercentByProportion(proportion,total){
    return (proportion/total*100).toFixed(2)+"%";
}

var reimbursementOption = function(){
	console.log(monthObject)
	// Ecahrts 柱状图
	var reimbursementOption = {
	    backgroundColor: '#ffffff', //整体背景色
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow',
	            shadowStyle: {
	              color: 'rgba(0,0,0,0.06)',
	            },
	            position: [20, 20]
	        },
	        backgroundColor: 'rgba(36,37,41,0.8);', // tooltip的背景色
	        formatter:function(params){
	            return params[0].name + params[0].seriesName+"：" + (params[0].value).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')+"元";
	        }
	    },
	    grid:{ //控制整体布局
	      left:'0',
	      right:'5%',
	      bottom:'0',
	      top:'18%',
	      containLabel:true
	  },
	    // x轴
	    xAxis: {
	        data: monthObject,
	        splitLine: {
	            show: false,
	        },
	        axisLine: {
	            lineStyle: {
	                color: '#DFE3E9', // x轴颜色
	                width: 1 // 粗细
	            },
	        },
	        axisLabel: {
	            color: '#999999', // x轴刻度名称颜色
	            interval:0,//代表显示所有x轴标签显示
	            // rotate:45, //代表逆时针旋转45度
	        }
	    },
	    yAxis: {
	        splitLine: {
	            show: true,
	            lineStyle: {
	                type: 'dashed',
	                color:'#EEEEEE'
	            }
	        },
	        axisLabel: {
	            color: '#999999', // y轴刻度名称颜色
	            formatter:function(value){
	                var value;
	                if(value >= 1000){
	                    value = value/1000+'K';
	                }else if(value <1000){
	        			value = value;
	        		}
	        		return value;
	            }
	        },
	        axisTick: { //y轴刻度线
	            show: false
	        },
	        axisLine: { //y轴
	            show: false
	        },
	    },
	    series: [{
	        name: '报销费用',
	        type: 'bar',
	        data: expenseObject,
	        itemStyle: {
	            normal: {
	                color: '#4285F4' // 柱状图颜色
	            }
	        },
	        barWidth : 12
	    }]
	};
	return reimbursementOption;
}



var reimbursementChart = echarts.init(document.getElementById('reimbursement-statisticsChart'));
reimbursementChart.setOption(reimbursementOption());




// Ecahrts 环状图1
var individualOption = function(){
	var individualOption = {
			  backgroundColor: '#ffffff', //整体背景色
			  tooltip: {//提示框，可以在全局也可以在
			      trigger: 'item',  //提示框的样式
			      formatter: "{b}<br/>金额：{c} <br/>占比：{d}%",
			      color:'rgba(36,37,41,0.8)', //提示框的背景色
			      textStyle:{ //提示的字体样式
			          color:"#fff",
			      }
			  },
			  series: [
			      {
			          // name:'访问来源',
			          type:'pie', //环形图的type和饼图相同
			          radius: ['34px', '55px'],//饼图的半径，第一个为内半径，第二个为外半径
			          center: ['38%', '50%'], // 位置
			          color:['#4285F4','#A1C2FA','#CED3DA'],
			          label: {
			              normal: {  
			                  show: false,
			                  position: 'right'
			              }
			          },  
			          data:[
			              {value:$("#feeByCreaObject_fdCanUseMoney").text(), name:'未报销'},
			              {value:$("#feeByCreaObject_fdUseingMoney").text(), name:'报销中'},
			              {value:$("#feeByCreaObject_fdUsedMoney").text(), name:'已报销'}
			          ]
			      }
			  ]
			};
	return individualOption;
}


var individualChart = echarts.init(document.getElementById('individual-applicationChart'));
individualChart.setOption(individualOption());


// Ecahrts 环状图2
var personalLoanOption = function(){
	var personalLoanOption = {
			  backgroundColor: '#ffffff', //整体背景色
			  tooltip: {//提示框，可以在全局也可以在
			      trigger: 'item',  //提示框的样式
			      formatter: "{b}<br/>金额：{c} <br/>占比：{d}%",
			      color:'rgba(36,37,41,0.8)', //提示框的背景色
			      textStyle:{ //提示的字体样式
			          color:"#fff",
			      }
			  },
			  series: [
			      {
			          type:'pie', //环形图的type和饼图相同
			          radius: ['34px', '55px'],//饼图的半径，第一个为内半径，第二个为外半径
			          center: ['38%', '50%'], // 位置
			          color:['#22B77D','#7AD4B1','#CED3DA'],
			          label: {
			              normal: {  
			                  show: false,
			                  position: 'left'
			              }
			          },  
			          data:[
			              {value:$("#fdLoanTotalObj_canUse").text(), name:'未还款'},
			              {value:$("#fdLoanTotalObj_useing").text(), name:'还款中'},
			              {value:$("#fdLoanTotalObj_used").text(), name:'已还款'}
			          ]
			      }
			  ]
			};
	return personalLoanOption;
}


var personalLoanChart = echarts.init(document.getElementById('personal-loanChart'));
personalLoanChart.setOption(personalLoanOption());
