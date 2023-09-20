<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js"></script>
   <script type="text/javascript">
            var formInitData = {

            };
            var messageInfo = {
            		"propertys":"${columnList}"
            };
            Com_IncludeFile("data.js");
            Com_IncludeFile("echarts.js", "${LUI_ContextPath}/sys/ui/js/chart/echarts/", 'js', true);
            Com_IncludeFile("landrayblue.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
        </script>
		
		
		<div>
  		 <div style="
				    padding-top: 10px;
				    padding-left: 20px;
				" hidden="true">
					${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select name="fdCompanyId"  onChange="changgeMap();">
						</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					${lfn:message('fssc-budget:fsscBudgetMain.fdBudgetScheme')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select name="fdBudgetSchemeId" onChange="changgeMap();">
						</select>
		</div>
       <div id="container" style="height: 90%"></div>
       </div>
       <script>
		function initSelect(){
				var data = new KMSSData();
				data.UseCache = false;
				var rtn = data.AddBeanData("eopBasedataCompanyService&authCurrent=true").GetHashMapArray();
				if(rtn&&rtn.length > 0){
					var optionstring="";
					$("select[name=fdCompanyId]").html("");
					for (var j = 0; j < rtn.length;j++) {
		                           $("select[name=fdCompanyId]").append("<option value=\"" + rtn[j].value + "\">" + rtn[j].text + "</option>");
		                        }
				}
				var data1 = new KMSSData();
				data1.UseCache = false;
				var rtn1 = data1.AddBeanData("eopBasedataBudgetSchemeService&authCurrent=true&fdType=findOfCenter").GetHashMapArray();
				if(rtn1&&rtn1.length > 0){
					var optionstring="";
					$("select[name=fdBudgetSchemeId]").html("");
					for (var j = 0; j < rtn1.length;j++) {
		                           $("select[name=fdBudgetSchemeId]").append("<option value=\"" + rtn1[j].value + "\">" + rtn1[j].text + "</option>");
		                        }
				}
				
		}
		
		window.onload = function(){
			initSelect();
			changgeMap();
		}
		
		</script>
       <script type="text/javascript">
       function changgeMap(){
    	   var data1 = new KMSSData();
			data1.UseCache = false;
			var fdCompanyId= $('select[name=fdCompanyId] option:selected').val();
			var fdscheme= $('select[name=fdBudgetSchemeId] option:selected').val();
			var rtn1 = data1.AddBeanData("fsscBudgetPortalService&authCurrent=true&source=checkZhixing&fdCompanyId="+fdCompanyId+"&fdscheme="+fdscheme).GetHashMapArray();
			if(rtn1.length>0){
				var fdUseMoney=(typeof rtn1[0]["fdUseMoney"]=="undefined"||""==rtn1[0]["fdUseMoney"]?0:rtn1[0]["fdUseMoney"]);
				var fdOccupyMoney=(typeof rtn1[0]["fdOccupyMoney"]=="undefined"||""==rtn1[0]["fdOccupyMoney"]?0:rtn1[0]["fdOccupyMoney"]);
				var fdAllMoney=(typeof rtn1[0]["fdAllMoney"]=="undefined"||""==rtn1[0]["fdAllMoney"]?0:rtn1[0]["fdAllMoney"]);
				var fdAdjustMoney=(typeof rtn1[0]["fdAdjustMoney"]=="undefined"||""==rtn1[0]["fdAdjustMoney"]?0:rtn1[0]["fdAdjustMoney"]);
				var fdCanUseMoney=Number(fdAllMoney)+Number(fdAdjustMoney)-Number(fdUseMoney)-Number(fdOccupyMoney);
				var dom = document.getElementById("container");
				var myChart = echarts.init(dom,"landrayblue");
				var app = {};
				option = null;
				option = {
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['预算已使用金额','预算占用金额','预算可使用金额']
				    },
				    series : [
				        {
				            name: '预算执行',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:[
				                {value:fdUseMoney, name:'预算已使用金额'},
				                {value:fdOccupyMoney, name:'预算占用金额'},
				                {value:fdCanUseMoney, name:'预算可使用金额'}
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
				;
				if (option && typeof option === "object") {
				    myChart.setOption(option, true);
				}
			}else{
				var dom = document.getElementById("container");
				var myChart = echarts.init(dom,"landrayblue");
				var app = {};
				option = null;
				option = {
				    title : {
				        text: '预算执行',
				        subtext: '',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['预算已使用金额','预算占用金额','预算可使用金额']
				    },
				    series : [
				        {
				            name: '预算执行',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:[
				                {value:0, name:'预算已使用金额'},
				                {value:0, name:'预算占用金额'},
				                {value:0, name:'预算可使用金额'}
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
				;
				if (option && typeof option === "object") {
				    myChart.setOption(option, true);
				}
			}
			
       }

       </script>
