/*
* @Author: liwenchang
* @Date:   2018-10-17 16:41:29
* @Last Modified by:   liwenchang
* @Last Modified time: 2018-10-17 08:58:14
*/
Com_IncludeFile("echarts.js",
		Com_Parameter.ContextPath + "sys/ui/js/chart/echarts/","js",true);
//投票节点显示控件执行脚本
(function(window){
	var voteNodeDisagree;
	var voteNodeAgree;
	var voteNodeAbstain;
	seajs.use(['lang!sys-xform-base'], function(lang) {
		voteNodeDisagree = lang["XformObject_Lang.voteNodeDisagree"];
		voteNodeAgree = lang["XformObject_Lang.voteNodeAgree"];
		voteNodeAbstain = lang["XformObject_Lang.voteNodeAbstain"];
	});
	
	var win = window,
	document = win.document,
	type = 'voteNode',
	//投票节点显示类型
	pieChart = "11",//11.饼图
	barGraph = "12";//12.柱状图
	
	//所有投票节点显示控件对象,key是id,value是控件对象
	var xformVoteNodeControls = {};
	$(function(){
		//获取明细表外的投票节点显示控件
		var voteNodes = $(document).find("[fd_type='" + type + "']:not([id*='!{index}'])");
		buildControl(voteNodes);
		//获取明细表内的投票节点显示控件
		$(document).on("table-add",function(event,source){
			tableAdd(event,source);
		});
	})

	/**
	*投票节点显示控件对象
	*/
	function VoteNodeControl(dom){
		var obj = $(dom);
		//控件属性所在dom元素
		this.dom = dom;
		//id
		this.id = obj.attr("id");
		//type 
		this.type = "voteNode";
		//类型
		this.mold = obj.attr("mold");
		//值
		this.val = obj.attr("value") || obj.attr("detail_attr_value");
		//高度
		this.height = obj.attr("_height");
		
		//宽度
		this.width = obj.attr("_width");
		//绑定文本
		this.title = obj.attr("title");
		//初始化函数
		this.init = init;
		this.getData = getData;
		this.generate = generate;
		//下载 
		this.init();
	}

	function init(){
		//获取数据
		this.getData();
	}
	
	
	//获取数据
	function getData(){
		var info = {};
		info.val = this.val;
		if (typeof lbpm != "undefined") {
			info.processId = lbpm.modelId;
		} else {
			info.processId = Com_GetUrlParameter(location.href,"fdId");
		}
		var data = new KMSSData();
		data.UseCache = false;
		data.AddHashMap(info);
		var self = this;
		//回调函数
		var action = function (rtnVal){
			var data = rtnVal.GetHashMapArray();
			if (data.length == 0){
				return;
			}else{
				self.data = data;
				self.generate();
			}
		}
		data.SendToBean("lbpmVoteNodeDataBean", action);
	}
	
	function generate(){
		$(this.dom).css("width",this.width);
		$(this.dom).css("height",this.height);
		var nodeName = [];
		var disAgreeArr = [];
		var agreeArr = [];
		var abstainArr = [];
		var disAgreeNum = 0;
		var agreeNum = 0;
		var abstainNum = 0;
		var isShowAbstain = false;
		for(var i = 0; i < this.data.length; i++){
			nodeName[i] = this.data[i]["fdFactNodeName"];
			disAgreeArr[i] = this.data[i]["disAgreeNum"];
			agreeArr[i] = this.data[i]["agreeNum"];
			abstainArr[i] = this.data[i]["abstainNum"];
			if (this.data[i]["abstainIsExit"] === "true"){
				isShowAbstain = true;
			}
			disAgreeNum += parseInt(disAgreeArr[i]);
			agreeNum += parseInt(agreeArr[i]);
			abstainNum += parseInt(abstainArr[i]);
		}
		if (this.mold == "11"){//饼图
			var option = {
				    title : {
				        text: '',
				        subtext: '',
				        x:'center'
				    },
				    color: ['#6698fa', '#5ff59b'],
				    tooltip : {
				        trigger: 'item',
				        position: 'inside',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        bottom: '25',
				        data: [voteNodeDisagree,voteNodeAgree]
				    },
				    series : [
				        {
				            name: this.title,
				            type: 'pie',
				            radius : '50%',
				            center: ['50%', '40%'],
				            label: {
				                normal: {
				                    formatter: '{b}:\n{c}({d}%)',
				                    backgroundColor: '#eee',
				                    borderColor: '#aaa',
				                    borderWidth: 1,
				                    borderRadius: 4,
				                    position : 'outside',
				                }
				            },
				            data:[
				                {value:disAgreeNum, name:voteNodeDisagree},
				                {value:agreeNum, name:voteNodeAgree}
				            ],
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
		}else if(this.mold == "12"){//柱状图
			 //指定图标的配置和数据
	        var option = {
	            title:{
	                text:''
	            },
	            color: ['#6698fa', '#5ff59b'],
	            legend:{
	                data:[voteNodeDisagree,voteNodeAgree],
	                bottom : "20"
	            },
	            xAxis:{
	                data:nodeName
	            },
	            yAxis:{
            	    minInterval: 1
	            },
	            grid:{
			    	top: '20',
			    	left:'10',
			    	containLabel: true
			    },
	            series:[
		            {
		                name:voteNodeDisagree,
		                type:'bar',
		                data:disAgreeArr,
		                label: {
		                    normal: {
		                        show: true,
		                        position: 'top'
		                    }
		                },
		            },
		            {
		                name:voteNodeAgree,
		                type:'bar',
		                data:agreeArr,
		                label: {
		                    normal: {
		                        show: true,
		                        position: 'top'
		                    }
		                },
		            }
	            ]
	        };
		}
		//是否显示弃权操作
		if (isShowAbstain){
			option.color[2] = '#f7d868';
			option.legend.data[2] = voteNodeAbstain;
			if (this.mold == "11"){
				option.series[0].data[2] = {value:abstainNum, name:voteNodeAbstain}; 
			}
			if (this.mold == "12"){
				option.series[2] = {
						                name:voteNodeAbstain,
						                type:'bar',
						                data:abstainArr,
						                label: {
						                    normal: {
						                        show: true,
						                        position: 'top'
						                    }
				                		},
		            				};
			}
		}
		 //初始化echarts实例
        var myChart = echarts.init(this.dom);
        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
        myChart.resize();
	}
	
	function processOption(option){
		if (this.mold == "11"){
			option.color[2] = '#f7d868';
			option.legend.data[2] = voteNodeAbstain;
			option.series.data[2] = {value:abstainNum, name:voteNodeAbstain}; 
		}
		if (this.mold == "12"){
			
		}
	}

	/**
	 * 明细表行增加事件,初始化的时候也会触发此事件
	 * @param event
	 * @param source
	 * @returns
	 */
	function tableAdd(event,source){
		//获取添加行内的投票节点显示控件
		var voteNodes = $(source).find("[fd_type='" + type + "']");
		buildControl(voteNodes);
	}

	function buildControl(voteNodes){
		if (voteNodes instanceof jQuery){
			for (var i = 0; i < voteNodes.length; i++){
				if(voteNodes[i] && voteNodes[i].nodeType){
					var control = new VoteNodeControl(voteNodes[i]);
					xformVoteNodeControls[control.id] = control;
					var right = $(control.dom).attr("right");
					if (right === "add" &&  $(control.dom).attr("valType") === docUrl){
						$(control["dom"]).hide();
					}
				}
			}
		}
	}
	
	
})(window);