<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="mobile.hr.staff.index.7"/>
	</template:replace>
	<template:replace name="head">
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/statistics.css">
	   <style>
	   	.muiCateFiledShow{
	   		text-align:center;
	   	}
	   </style>
	   <script src="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/rem/rem.js"></script>
	</template:replace>
	<template:replace name="content">
		  <div class="data-show-box">
		    <div class="data-show-content">
		      <div class="data-show-top-bg">
		        <div class="data-show-dropdowm-menu">
		          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.2"/></span>
		          <!-- <i class="data-show-dropdowm-icon"></i> -->
		        </div>
		        <div style="text-align:center;">
		        	<xform:address orgType="ORG_TYPE_DEPT" onValueChange="window.onAddress" propertyName="fdOrgName" propertyId="fdOrgId" mobile="true" showStatus="edit"></xform:address>
		        </div>
		        <div class="data-show-in-service">
		          <div class="data-show-in-service-month">
		            <span>1234</span>
		            <div><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.3"/></div>
		          </div>
		          <div class="data-show-in-service-current">
		            <span>1098</span>
		            <div><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.4"/></div>
		          </div>
		        </div>
		      </div>
		      <!-- 员工构成 -->
		      <div class="data-show-yggc">
		        <div class="data-show-yggc-title">
		          <i></i>
		          <span class="data-show-yggc-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.5"/></span>
		          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.6"/></span>
		        </div>
		        <%
		        	String[] staffTypeColor = {"dsy-i-icon-sy","dsy-i-icon-ss","dsy-i-icon-ls","dsy-i-icon-jz"};
		        %>
		        <c:set var="staffTypeColor" value="<%=staffTypeColor %>"></c:set>
		        <div class="data-show-yggc-content">
		          <div class="data-show-yggc-content-qz">
			  		<div class="data-show-yggc-content-qz" data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/template.js"
					  data-dojo-props="module:'staffStatus',url:'<%=request.getContextPath()%>/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=getHrStaffMobileStat'"
					>
						<div class="data-show-yggc-item">
			              <i class="dsy-i-icon @$color@" ></i>
			              <div class="dsy-i-text">
			                <span>@$data@</span>
			                <div>@$name@</div>
			              </div>
			            </div>
					</div>
		            <!-- 试用  dsy-i-icon-sy -->
		            <!-- 实习 dsy-i-icon-ss -->
		            <!-- 临时 dsy-i-icon-ls -->
		            <!-- 兼职  dsy-i-icon-jz-->
		          </div>
		          <!-- <div class="data-show-yggc-content-ls"></div> -->
		        </div>
		      </div>
		      <!-- 年龄分布 -->
		      <div class="data-show-yggc">
		        <div class="data-show-yggc-title">
		          <i></i>
		          <span class="data-show-yggc-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.7"/></span>
		          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.6"/></span>
		        </div>
		  		<div class="data-show-chart-line" data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/template.js"
				  data-dojo-props="module:'age',url:'<%=request.getContextPath()%>/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=getHrStaffMobileStat'"
				>
				  <div class="dsc-l-item">
		            <span>@$data@</span>
		            <div class="dsc-l-item-line" style="height:@$data/$sum*5@rem"></div>
		            <span class="dsc-l-item-text">@$name@</span>
		          </div>
				</div>
		      </div>
		      <!-- 司龄分布 -->
		      <div class="data-show-yggc">
		        <div class="data-show-yggc-title">
		          <i></i>
		          <span class="data-show-yggc-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.8"/></span>
		          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.6"/></span>
		        </div>
		        <div class="data-show-chart-row" data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/template.js"
				  data-dojo-props="module:'worktime',url:'<%=request.getContextPath()%>/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=getHrStaffMobileStat'"
				>
		          <div class="dsc-r-item">
		            <div class="dsc-r-text">
		              <span>@$name@</span>
		              <span class="dsc-r-text-num">@$data@</span>
		            </div>
		            <div class="dsc-r-shadow">
		              <i class="dsc-r-row" style="width:@$data/$sum*100@%;"></i>
		            </div>
		          </div>	
				</div>
		      </div>
		      <!-- 学历分布 -->
		      <div class="data-show-yggc">
		        <div class="data-show-yggc-title">
		          <i></i>
		          <span class="data-show-yggc-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.9"/></span>
		          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.6"/></span>
		        </div>
		        <div class="data-show-chart-line" data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/template.js"
				  data-dojo-props="module:'edution',url:'<%=request.getContextPath()%>/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=getHrStaffMobileStat'"
				>
				  <div class="dsc-l-item">
		            <span>@$data@</span>
		            <div class="dsc-l-item-line dsc-l-item-line-green" style="height:@$data/$sum*5@rem"></div>
		            <span class="dsc-l-item-text">@$name@</span>
		          </div>	
				</div>
		      </div>
		      <!-- 性别比例 -->
		      <div class="data-show-yggc">
		        <div class="data-show-yggc-title">
		          <i></i>
		          <span class="data-show-yggc-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.10"/></span>
		          <!-- <span>单位（人）</span> -->
		        </div>
		        <div class="data-show-chart-pie">
		          <div id="main" style="height: 16.2rem;"></div>
		        </div>
		      </div>
		      <!-- 婚姻状态 -->
		      <div class="data-show-yggc">
		        <div class="data-show-yggc-title">
		          <i></i>
		          <span class="data-show-yggc-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.11"/></span>
		          <span><bean:message bundle="hr-staff" key="mobile.hr.staff.search.list.6"/></span>
		        </div>
		       
				<div class="data-show-chart-line" data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/template.js"
				  data-dojo-props="module:'marital',url:'<%=request.getContextPath()%>/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=getHrStaffMobileStat'"
				>
					  <div class="dsc-l-item">
			            <span>@$data@</span>
			            <div class="dsc-l-item-line dsc-l-item-line-purple" style="height:@$data/$sum*5@rem"></div>
			            <span class="dsc-l-item-text">@$name@</span>
			          </div>
				</div>
		       
		      </div>
		    </div>
		    <div class="file-overview-footer">
		      <div onclick="window.location.href='${LUI_ContextPath}/hr/staff/mobile/newIndex.jsp?selfViewAuth=true'">
		        <i class="fof-overview-icon"></i>
		        <span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.1"/></span>
		      </div>
		      <div>
		        <i class="fof-data-icon fof-data-icon-active"></i>
		        <span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.7"/></span>
		      </div>
		      <c:choose>
			        <c:when test="${fdSelfView }">
			        	<div onclick="window.location.href='${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}'">
			          		<i class="fof-file-icon"></i>
			          		<span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.8"/></span>
			        	</div>
			        </c:when>
			        <c:otherwise>
			        	<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}">
			        		<div onclick="window.location.href='${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=${KMSS_Parameter_CurrentUserId}'">
			          			<i class="fof-file-icon"></i>
			          			<span><bean:message bundle="hr-staff" key="mobile.hr.staff.index.8"/></span>
			        		</div>
			        	</kmss:auth>
			        </c:otherwise>
		       </c:choose>
		    </div>
		  </div>
		</body>
	<script>
	  var sexUnkonw = '${statistics.staffSex.unknown}'
	  var sexMale='${statistics.staffSex.male}'
	  var female='${statistics.staffSex.female}'
	  require(["dojo/ready",
	           "dojo/request",
	           "dojo/query",
	           "dojo/on",
	           "lib/echart/echarts",
	           "dojo/topic",
	           "dojo/request"],function(ready,request,query,on,echarts,topic,request){
		  	function chartSex(CinArray){
		  		if(!CinArray){
		  			 var CinArray = [{ name: '男 '+sexMale+'人', value: sexMale}, { name: '女 '+female+'人', value: female },{name:'未知',value:sexUnkonw}];
		  		}
	  			var colorL = ['#63A8ED', '#FFCD0E','#E7E7E7'];
	  		    var NameArray = CinArray.map(function (cinarray) {
	  		      return cinarray.name;
	  		    });
	  		    var DataArray = CinArray.map(function (cinarray) {
	  		        return cinarray.value;
	  		      });
	  		    var myChart = echarts.init(document.getElementById("main"));
	  		    // 指定图表的配置项和数据
	  		    option = {
	  		      legend: {//位于右侧的属性按钮
	  		        orient: 'vertical',//竖直放置
	  		        icon: 'square',//图标为圆形，默认是方形
	  		        align: 'auto',
	  		        itemGap: 20,//两个属性的距离
	  		        // itemWidth: 8,//图标的宽度，对应有itemHeight为高度,圆形只有半径
	  		        x: '65%',//距离左侧位置
	  		        y: '30%',//距离上面位置
	  		        data: NameArray,//属性名称
	  		        align: 'left',//图标与属性名的相对位置，默认为right，即属性名在左，图标在右
	  		        selectedMode: true,//可选择
	  		        formatter: function (v) {
	  		          return v;
	  		        },
	  		        textStyle: {//属性名的字体样式设置
	  		          fontSize: 14,
	  		          color: '#50617A;'
	  		        }
	  		      },
	  		      series: [{//饼状图设置
	  		        name: name,//设置名称，跟数据无相关性
	  		        type: 'pie',//类型为饼状
	  		        radius: ['50%', '70%'],//内圈半径，外圈半径
	  		        center: ['35%', '50%'],//饼状图位置，第一个参数是左右，第二个是上下。
	  		        avoidLabelOverlap: false,
	  		        hoverAnimation: false,//鼠标悬停效果，默认是true
	  		        label: {//设置饼状图圆心属性
	  		          //normal,emphasis分别对应正常情况下与悬停效果
	  		          normal: {
	  		            show: false,
	  		            position: 'outside',
	  		          	formatter:function(v){
	  		          		console.log(v)
	  		          	}
	  		          },
	  		          emphasis: {
	  		            show: false,
	  		            textStyle: {
	  		              fontSize: '20',
	  		              fontWeight: 'bold'
	  		            }
	  		          }
	  		        },
	  		        labelLine: {
	  		          normal: {
	  		            show: true
	  		          }
	  		        },
	  		        data: CinArray,//对应数据
	  		        itemStyle: {//元素样式
	  		          normal: {
	  		            //柱状图颜色  
	  		            color: function (params) {//对每个颜色赋值
	  		              // 颜色列表  
	  		              var colorList = colorL;
	  		              //返回颜色  
	  		              return colorList[params.dataIndex];
	  		            },
	  		
	  		          },
	  		        }
	  		      }]
	  		    };
	  		    // 使用刚指定的配置项和数据显示图表。
	  		    myChart.setOption(option);
		  	}
		  	var url = dojo.config.baseUrl+"hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=getHrStaffMobileStat";

		  	function getSexData(fdId){
			  	request.post(url,{data:{fdOrgId:fdId}}).then(function(data){
			  		if(data){
			  			try{
			  				var json = JSON.parse(data);
			  				var sexData = [];
			  				Array.isArray(json['staffSex'])&&json['staffSex'].map(function(item){
			  					sexData.push({name:item.name+item['data'][0]+'人',value:item.data[0]})
			  				})
			  				chartSex(sexData);
			  				
			  			}catch(e){
			  				
			  			}
			  		}
			  	})		  		
		  	}
		  	getSexData("")
		  	topic.subscribe("hr/statistic/address/change",function(v){
		  		getSexData(v);		
		  	});
  		    window.onAddress=function(v){
  		    	topic.publish("hr/statistic/address/change",v);
  		    }
	 		function getPostData(fdId){
	 			var postUrl = dojo.config.baseUrl+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getHrStaffMobileStat"
			  	request.post(postUrl,{data:{fdIds:fdId}}).then(function(data){
			  		if(data){
			  			try{
			  				var json = JSON.parse(data);
			  				if(json[0]){
			  					document.querySelector(".data-show-in-service-current span").innerHTML=json[0].value
			  				}
			  				
			  				if(json[1]){
			  					console.log(json[1])
			  					document.querySelector(".data-show-in-service-month span").innerHTML=json[1].value
			  				}
			  			}catch(e){
			  				console.log(e)
			  			}
			  		}
			  	})	
	 		}
	 		getPostData();
	 		topic.subscribe("hr/statistic/address/change",function(v){
	 			getPostData(v);
	 		});
	  		    
	  }) 

	 	
		</script>
	</template:replace>
</template:include>