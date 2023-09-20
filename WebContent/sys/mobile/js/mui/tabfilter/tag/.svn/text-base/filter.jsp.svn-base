<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
			<div class="tabfilterContainer">
				<div data-dojo-type="sys/mobile/js/mui/tabfilter/TabfilterSelection"
					 data-dojo-props="values:'{curValues}',texts:'{curTexts}'">
				</div>
				<div class="tabfilter-content">
					 <aside class="tabfilter-left-bar">
						<div data-dojo-type="dojox/mobile/ScrollableView"
							 data-dojo-props="scrollBar:false,threshold:100,fixedFooter:'_${param.key}_tabfilter_footer'">
							<ul 
								data-dojo-type="mui/list/JsonStoreList"
								data-dojo-mixins="mui/tabfilter/TabfilterLeftItemListMixin,
												  mui/tabfilter/TabfilterInitMixin"
								data-dojo-props="url:'/sys/tag/sys_tag_group/sysTagGroup.do?method=getTagGroupJsonByModelName&modelName=${not empty HtmlParam.modelName ? HtmlParam.modelName : 'com.landray.kmss.sys.zone.model.SysZonePersonInfo' }', lazy:false"> 
							</ul>
						</div>
					</aside>
					<div class="tabfilter-right-bar">
						<div data-dojo-type="dojox/mobile/ScrollableView"
							 data-dojo-props="scrollBar:false,threshold:100,fixedFooter:'_${param.key}_tabfilter_footer'">
							<ul 
								data-dojo-type="mui/list/JsonStoreList"
								data-dojo-mixins="mui/tabfilter/TabfilterRightItemListMixin,
												  mui/tabfilter/TabfilterRightStoreMixin"
								data-dojo-props="curTexts:'{curTexts}',curValues:'{curValues}'"
							</ul>
						</div>
					</div> 
				</div>
				<div data-dojo-type="mui/header/Header" id="_${param.key}_tabfilter_footer"
						data-dojo-props="height:'4.2rem'" fixed="bottom">
					<div data-dojo-type="mui/header/HeaderItem" class="muiTabfilterCancel"
						data-dojo-mixins="mui/tabfilter/TabfilterCancel"
						data-dojo-props="label:'${lfn:message('button.cancel')}'"></div>
					<div data-dojo-type="mui/header/HeaderItem" class="muiTabfilterOk"
						data-dojo-mixins="mui/tabfilter/TabfilterOk"
						data-dojo-props="label:'${lfn:message('button.ok') }'"></div>
				</div>
			</div>
