<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ include file="/sys/ui/jsp/common.jsp"%>
  <%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
    <template:include ref="mobile.list" compatibleMode="true">
      <template:replace name="title">

      </template:replace>
      <template:replace name="head">
      	<c:import url="/sys/mobile/template/com_head.jsp"></c:import>
      	<mui:cache-file name="mui-review.js" cacheType="md5"/>
	   	<mui:cache-file name="mui-review-list.css" cacheType="md5"/>
	   	<mui:cache-file name="sys-lbpm.css" cacheType="md5"/>
      </template:replace>
      <template:replace name="content">
      <%-- <c:out value="${param}"></c:out> --%>
      <div style="width:100%">
	        <!-- 流程状态数据统计列表 Starts -->
	        <ul class="mui_flowstate_card">
	        <!-- 总人次 -->
	          <li class="mui_flowstate_card_item" onclick="filterList(-1);">
	            <a class="mui_flowstate_card_pane" href="javascript:void(0);">
	              <div class="icnbox"><i class="mui mui-memberTotal"></i></div>
	              <div class="card_content">
	                <span class="card_num" id="personCount">0</span>
	                <span class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.personCount') }</span>
	              </div>
	            </a>
	          </li>
	          <!-- 未查看 -->
	          <li class="mui_flowstate_card_item" onclick="filterList(0);">
	            <a class="mui_flowstate_card_pane" href="javascript:void(0);">
	              <div class="icnbox"><i class="mui mui-notViewed"></i></div>
	              <div class="card_content">
	                <span class="card_num" id="notLook">0</span>
	                <span class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.notLook') }</span>
	              </div>
	            </a>
	          </li>
	          <!-- 已查看 -->
	          <li class="mui_flowstate_card_item" onclick="filterList(1);">
	            <a class="mui_flowstate_card_pane" href="javascript:void(0);">
	              <div class="icnbox"><i class="mui mui-checkedView"></i></div>
	              <div class="card_content">
	                <span class="card_num" id="isLook">0</span>
	                <span class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isLook') }</span>
	              </div>
	            </a>
	          </li>
	          <!-- 未提交 -->
	          <li class="mui_flowstate_card_item" onclick="filterList(2);">
	            <a class="mui_flowstate_card_pane" href="javascript:void(0);">
	              <div class="icnbox"><i class="mui mui-notSubmitted"></i></div>
	              <div class="card_content">
	                <span class="card_num" id="notFinish">0</span>
	                <span class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.notFinish') }</span>
	              </div>
	            </a>
	          </li>
	          <!-- 已提交 -->
	          <li class="mui_flowstate_card_item" onclick="filterList(3);">
	            <a class="mui_flowstate_card_pane" href="javascript:void(0);">
	              <div class="icnbox"><i class="mui mui-submitted"></i></div>
	              <div class="card_content">
	                <span class="card_num" id="isFinish">0</span>
	                <span class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isFinish') }</span>
	              </div>
	            </a>
	          </li>
	          <!-- 总催办次数 -->
	          <li class="mui_flowstate_card_item">
	            <a class="mui_flowstate_card_pane" href="javascript:void(0);" onclick="">
	              <div class="icnbox"><i class="mui mui-pressTodo"></i></div>
	              <div class="card_content">
	                <span class="card_num" id="pressCount">0</span>
	                <span class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.pressCount') }</span>
	              </div>
	            </a>
	          </li>
	        </ul>
        </div>
        <!-- 流程状态数据统计列表 Ends -->
        
        <!-- 流程状态数据卡片 Starts -->
        <div id="relevance_cate"
			data-dojo-type="mui/list/StoreScrollableView"
			data-dojo-mixins="sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/js/ProcessStatusScrollableViewMixin">
			
			<ul id="processStatuslist" style="border-bottom-width: 0px" data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/js/ProcessStatusList"
				data-dojo-mixins="sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/js/ProcessStatusItemListMixin"
				data-dojo-props="url:'/sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=findList&fdModelId=${param.processId}',lazy:false">
			</ul>
		</div>
        <!-- 流程状态数据卡片 Ends -->

        <script type="text/javascript">
		require(['dojo/topic','dijit/registry','dojo/query'],function(topic,registry,query){
			topic.subscribe("/mui/list/toTop",function(srcObj){
				srcObj.hide();
			});
    	});
      //列表筛选
    	function filterList(type){
    		//type:0未查看、1已查看、2未提交、3已提交
    		require(['dojo/topic','dijit/registry','dojo/query'],function(topic,registry,query){
    		
        		registry.byId("processStatuslist")._setUrlAttr('/sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=findList&fdModelId=${param.processId}&type='+type);
        		registry.byId("processStatuslist").reload();
        		var self=this;
        		//刷新列表后，置顶列表
        		//top组件配置x
        		setTimeout(function(){
        			topic.publish("/mui/top/viewChanged",registry.byId("relevance_cate"));
        			topic.publish('/sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/toTop',self);
        		}, 200);
        		
        		
        	});
    	}
        
        $(function(){
        	processStatusData();
        });
        var press_currentTime;
         //催办
        function press(id) {
        	var event = event || window.event;
        	if (event.preventDefault) {
        		event.preventDefault();
        	}
        	if (event.stopPropagation) {
        		event.stopPropagation();
        	}
        	//连续点击不能超过一秒,不知道为啥ios只点了一次会进来两次这个事件
        	var nowTime = new Date().getTime();
        	var clickTime = press_currentTime;
        	if (clickTime != 'undefined' && (nowTime - clickTime < 500)) {
        		return false;
        	}
        	press_currentTime = nowTime;
            $.ajax({
              url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=press",
              async: false,
              data: { workItemId: id },
              type: "POST",
              dataType: 'json',
              success: function (data) {
                if (data.type) {
                	//催办成功
                	require(['dojo/ready','mui/dialog/Tip','dojo/window'],function(ready,Tip,win){	
                		Tip.success({
      						text:'${lfn:message("sys-lbpmservice-support:lbpm.process.status.pressSuccess")}' ,
      						width:win.getBox().w * 0.5
                	});
                  });
                  processStatusData();//刷新统计数据
                }
                else {
                	//催办失败
                	require(['dojo/ready','mui/dialog/Tip','dojo/window'],function(ready,Tip,win){	
                		var msg = "${ lfn:message('sys-lbpmservice-support:lbpm.process.status.pressfailure') }";
                        if (data.msg) {
                          msg = data.msg;
                        }
                		Tip.success({
      						text:msg ,
      						width:win.getBox().w * 0.5
                		});
                  	});
                }
              },
              error: function (er) {
                if (console) {
                  console.log(er);
                }
              }
            });
          }
          //获取流程状态统计数据
          function processStatusData() {
        	var processId=Com_GetUrlParameter(location.href,"processId");
            $.ajax({
              url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=processStatus",
              async: false,
              data: { fdModelId: processId },
              type: "POST",
              dataType: 'json',
              success: function (data) {
                if (data.type) {
                  var card_nums = $(".card_num");
                  for (var i = 0; i < card_nums.length; i++) {
                    var vId = $(card_nums[i]).attr("id");
                    if (data.data[vId]) {
                      $("#" + vId).html(data.data[vId]);
                    }
                  }
                }
              },
              error: function (er) {
                if (console) {
                  console.log(er);
                }
              }
            });
          }
        </script>
      </template:replace>
    </template:include>