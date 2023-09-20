<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
{$
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
						          <a  href="{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}" target="_blank"  title="{%_data[i].templateName %}"><bean:message key="button.add"/></a>
						        </div>
								<div class="link-box-footer">
						          <h6 class="link-box-title">
						$}
						          if(_data[i].cateName){
						           {$
						         	  <i class="icon"></i><span title="{%env.fn.formatText(_data[i].moduleDesc)%}">{%env.fn.formatText(_data[i].moduleDesc)%}</span>
						         	$} 
						           }
						{$
						          </h6>
						        </div>
						     </div>
						</li>
					$}
				}
			 {$
				</ul>
			$}