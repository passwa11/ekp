<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
if(data!=null && data.length > 0 && data[0].length>0){
				{$
					<ul class='lui-cate-panel-list'>
				$} 
				var _data = data[0];
				for(var i=0;i < _data.length;i++){
					{$
						<li class="lui-cate-panel-list-item">
							 <div class="link-box">
						        <div class="link-box-heading">
						          <p><span>{%env.fn.formatText(_data[i].text)%}</span></p>
						        </div>
						        <div class="link-box-body">
						          <a onclick="javascript:openCreate('{%_data[i].value%}','{%_data[i].addURL%}');" target="_blank"  title="{%env.fn.formatText(_data[i].text)%}"><bean:message key="button.add"/></a>
						        </div>
								<div class="link-box-footer">
						          <h6 class="link-box-title">
						$}
						          if(_data[i].cateName){
						           {$
						         		 <i class="icon"></i><span title="{%env.fn.formatText(_data[i].moduleName)%}>>{%env.fn.formatText(_data[i].cateName)%}">{%env.fn.formatText(_data[i].moduleName)%}>>{%env.fn.formatText(_data[i].cateName)%}</span>
						         	$} 
						           }else{
							            {$
							         		  <i class="icon"></i><span title="{%env.fn.formatText(_data[i].moduleName)%}">{%env.fn.formatText(_data[i].moduleName)%}</span>
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
}
else{
	{$
						<div style="color: red;">
							<bean:message key="lbpmperson.createDoc.search.emptyResult" bundle="sys-lbpmperson"/>
						</div>
					$}
}
