define([ "dojo/_base/declare",  "dojo/dom-construct", 
         "dojo/dom-prop",  "sys/xform/mobile/controls/xformUtil", 
         "dijit/registry",  "mui/form/_FormBase", 
         "dojo/query","dojo/dom", "dojo/dom-class",
         "dojo/dom-attr",  "dojo/dom-style",
         "dojo/on", "dojo/_base/lang",
         "mui/chart/Chart2",
         "mui/i18n/i18n!sys-xform-base:mui",
         "mui/chart/Chart"
         ], function(declare, domConstruct, domProp, xUtil, registry,
        		 _FormBase, query,dom,domClass,domAttr,domStyle,on,lang,Chart2,Msg,Chart) {
	var claz = declare("sys.xform.mobile.controls.VoteNode", [ _FormBase], {
		
		buildRendering : function() {
			this.inherited(arguments);
			this.generate();
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		startup:function(){
			this.inherited(arguments);
		},
		
		DISAGTEEMSG : Msg["mui.voteNodeDisagree"],
		AGREEMSG : Msg["mui.voteNodeAgree"],
		ABSTAINMSG : Msg["mui.voteNodeAbstain"],
		
		generate :function (){
			var info = {};
			info.val = this.detail_attr_value;
			info.processId = _xformMainModelId;
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
					self.renderChart();
				}
			}
			data.SendToBean("lbpmVoteNodeDataBean", action);
		},
		
		renderChart : function(){
			domStyle.set(this.domNode,"width",this.width + "px");
			domStyle.set(this.domNode,"height",this.height + "px");
			var nodeName = [];
			var disAgreeArr = [];
			var agreeArr = [];
			var abstainArr = [];
			var disAgreeNum = 0;
			var agreeNum = 0;
			var abstainNum = 0;
			for(var i = 0; i < this.data.length; i++){
				nodeName[i] = this.data[i]["fdFactNodeName"];
				disAgreeArr[i] = this.data[i]["disAgreeNum"];
				agreeArr[i] = this.data[i]["agreeNum"];
				abstainArr[i] = this.data[i]["abstainNum"];
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
					    color: ['#6698fa', '#5ff59b', '#f7d868'],
					    tooltip : {
					        trigger: 'item',
					        position: 'inside',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					        bottom: '10%',
					        left: '10%',
					        data: [this.DISAGTEEMSG,this.AGREEMSG,this.ABSTAINMSG]
					    },
					    zlevel : 10,
					    series : [
					        {
					            name: this.subject,
					            type: 'pie',
					            radius : '50%',
					            center: ['50%', '50%'],
					            labelLine: {
					                normal: {
					                    lineStyle: {
					                        color: '#335'
					                    },
					                    smooth: 0.1,
					                    length: 10,
					                    length2: 10
					                }
					            },
					            label: {
					                normal: {
					                    formatter: '{b}:\n{c}({d}%)',
					                    borderWidth: 1,
					                    borderRadius: 4,
					                    position : 'outside',
					                    textShadowOffsetX : 4,
//					                    width : "40%",
					                }
					            },
					            data:[
					                {value:disAgreeNum, name:this.DISAGTEEMSG},
					                {value:agreeNum, name:this.AGREEMSG},
					                {value:abstainNum, name:this.ABSTAINMSG}
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
		            color: ['#6698fa', '#5ff59b', '#f7d868'],
		            legend:{
		            	data: [this.DISAGTEEMSG,this.AGREEMSG,this.ABSTAINMSG],
		                bottom : "20",
		                left : "10%"
		            },
		            xAxis:{
		                data:nodeName
		            },
		            yAxis:{
		            	minInterval: 1
		            },
		            grid:{
				    	top: '20',
				    	left:'10%',
				    	containLabel: true
				    },
		            series:[
		            {
		                name:this.DISAGTEEMSG,
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
		                name:this.AGREEMSG,
		                type:'bar',
		                data:agreeArr,
		                label: {
		                    normal: {
		                        show: true,
		                        position: 'top'
		                    }
		                },
		            },
		             {
		                name:this.ABSTAINMSG,
		                type:'bar',
		                data:abstainArr,
		                label: {
		                    normal: {
		                        show: true,
		                        position: 'top'
		                    }
		                },
		            },
		            ]
		        };
			}
//			 //初始化echarts实例
	        var myChart = Chart2.init(this.domNode);
	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);
	        myChart.resize();
		}
		
	});
	return claz;
});