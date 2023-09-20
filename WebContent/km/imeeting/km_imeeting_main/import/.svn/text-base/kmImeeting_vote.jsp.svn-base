<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<style>
			.lui_imeeting_criterions {
			  background: #f7f7f7;
			  padding: 16px 16px 1px;
			  padding-top: 10px;
			}
			.lui_imeeting_criterion_item {
			  overflow: hidden;
			  margin: 10px 0;
			  line-height: 26px;
			}
			.lui_imeeting_criterion_title {
			  width: 80px;
			  text-align: right;
			  float: left;
			  color: #666;
			}
			.lui_imeeting_criterion_selectBox {
			  margin-left: 85px;
			}
			.lui_imeeting_criterion_selectBox ul {
			}
			.lui_imeeting_criterion_selectBox li {
			  list-style: none;
			  display: inline-block;
			  cursor: pointer;
			  padding: 1px 5px;
			  margin-bottom: 5px;
			  border-radius: 3px;
			}
			.lui_imeeting_criterion_selectBox li.lui_imeeting_criterion_dropdown {
			  margin-right: 10px;
			}
			.lui_imeeting_criterion_selectBox li.lui_imeeting_criterion_dropdown span {
			  display: inline-block;
			  height: 26px;
			  line-height: 26px;
			  background: #fff;
			  padding-left: 10px;
			  padding-right: 10px;
			}
			.lui_imeeting_criterion_selectBox .lui_imeeting_criterion_dropdown span {
			}
			.lui_imeeting_criterion_selectBox .lui_imeeting_criterion_selectItem {
			  margin-right: 15px;
			}
			.sclpitl_item_active {
			  border: 1px solid;
			}

			.lui_result_tb {
				padding-top:10px;
				overflow:auto;
			}
			
			.lui_result_tb th {
			  background: #f7f7f7;
			  border: 1px solid #dfdfdf;
			  white-space: nowrap;
			  line-height: 20px;
			  font-weight: normal;
			  color: #354052;
			  padding-top: 10px;
			  padding-bottom: 10px;
			  padding-left: 10px;
			  padding-right: 10px;
			}
			
			.lui_result_tb td {
			  padding: 10px;
			  border: 1px solid #dfdfdf;
			  font-size: 12px;
			  min-width: 50px;
			  color:#666666;
			}
		</style>
		
		<ui:dataview>
			<ui:source type="AjaxJson">
				{url: "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=getVotes&fdMainId=${JsParam.fdId}"}
			</ui:source>
			<ui:render type="Template">
				<c:import url="/km/imeeting/km_imeeting_main/import/criteria_vote.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
			<ui:event event="load">
				var firstVote = document.getElementById("firstVote");
				if(firstVote!=undefined){
					firstVote.click();
				}else{
					document.getElementById("voteDataView").style.display = "none";
				}
			</ui:event>
	    </ui:dataview>
	    
		<ui:dataview id="voteDataView">
			<ui:source type="AjaxJson">
			</ui:source>
			<ui:render type="Template">
				<c:import url="/km/imeeting/km_imeeting_main/import/result_vote.jsp" charEncoding="UTF-8"></c:import>
			</ui:render>
			<ui:event event="load">
				seajs.use(['lui/jquery'],function($){
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						window.frameElement.style.height =  ($(document.body).height() + 100 )+ "px";
					}
				});
			</ui:event>
		</ui:dataview>
		
		<script>
			function voteChange(val,node){
				var newSrc = "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=getVoteResult&fdMainId=${JsParam.fdId}&fdVoteId="+val;
				LUI("voteDataView").source.setUrl(newSrc);
				LUI("voteDataView").source.get();
				$(node).addClass('sclpitl_item_active lui_text_primary').siblings().removeClass('sclpitl_item_active lui_text_primary');
			}
		</script>
		
	</template:replace>
</template:include>	


