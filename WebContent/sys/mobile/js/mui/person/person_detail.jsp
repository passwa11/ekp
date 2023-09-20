<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<div class="personDetailScrollableView"
	data-dojo-type="dojox/mobile/View">
	<!--  <div class="personHeader" data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
	  <div class="personHeaderReturn" >
			<i class="mui mui-back"></i>
			<span class="personHeaderReturnTxt"> 
				${lfn:message("button.back")}
			</span>
		</div>
		
		<div class="personHeaderTitle">
			{title}
		</div>
		<div></div>
	</div>
	-->	
	<div data-dojo-type="mui/list/StoreScrollableView">
		<ul data-dojo-type="mui/person/PersonDetailJsonStoreList"
			data-dojo-mixins="mui/person/PersonDetailItemListMixin"
			data-dojo-props="url:'{url}',personId:'{personId}',lazy:false">
		</ul>
	</div>
</div>