<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
{$
					<div class="lui-cate-panel-heading usual-cate-title">		     
						 <h2 class="lui-cate-panel-heading-title">{%data.title%}</h2> 
					</div>
					<ul class='lui-cate-panel-list'>
				$} 
				var _data = data.list;
				for(var i=0;i < _data.length;i++){
					{$
						<li class="lui-cate-panel-list-item">
							 <div class="link-box">
						        <div class="link-box-heading">
						          <p><span>{%_data[i].templateName%}</span></p>
						        </div>
						        <div class="link-box-body">
						          <a onclick="Com_OpenNewWindow(this)" data-href="{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}" target="_blank"  title="{%_data[i].templateName%}"><bean:message key="button.add"/></a>
						        </div>
								
						          
						$}
						
						if(_data[i].fdIsImport == "true"){
							{$
								<div class="link-box-footer canCopyFooter">
									<h6 class="link-box-title canCopyTitle">
							$}
						}else{
							{$
								<div class="link-box-footer">
									<h6 class="link-box-title">
							$}
						}
							
						          if(_data[i].templateDesc){
						           {$
						         	  <i class="icon"></i><span title="{%env.fn.formatText(_data[i].templateDesc)%}">{%env.fn.formatText(_data[i].templateDesc)%}</span>
						         	$} 
						           }
						           {$
						          </h6>
						          $}
						          if(_data[i].fdIsImport == "true"){
								  	{$
								  		<span class="iconWrap">
								  			<i class="icon unlistImg" onclick="listRelationDocument('{%_data[i].fdTemplateId%}','{%_data[i].fdModelName%}','{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}');"></i>
							  			</span>
								  	$} 
						          }
						          {$
						        </div>
						     </div>
						      <div name="relationDocument" class="relationDocument" >
						      	<div class="lui-cate-panel-search" style="float: left;margin:0px;">
									<div class="lui_category_search_box">
										<a href="javascript:;" id="searchBackBtn" class="search-back" onclick="Qsearch_rtn();"></a>
										<div class="search-input" style="width:250px;">
											<input id="relationSearchTxt" class="lui_category_search_input" style="width:100%;" type="text" placeholder="请输入关键词" onkeyup="relationSearch();"/>
										</div>
										<a href="javascript:void(0);" class="search-btn" onclick="QRelationsearch();"></a>
									</div>
								</div>
						      </div>
						</li>
					$}
				}
			 {$
				</ul>
			$}