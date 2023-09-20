<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
if(data.length > 0){
 
	for(var m=0;m<data.length;m++){
		if(m == 0){
			if(data[m].type == 'criteria'){
			 	{$	
			  	  <div class="forum_home_content forum_plate_content">
			   $}
	   		}else{
			   	 {$
			   	 	<div class="forum_home_content" >
			   	 $}
		   	 }
	   	}
 		{$
 		 <div class="lui_common_list_box">
            <div class="lui_common_list_4_header_left">
                <div class="lui_common_list_4_header_right">
                    <div class="lui_common_list_4_header_centre clrfix">
                        <h4>
                           <a href="javascript:openURL('{%data[m].fdId%}');">
							  {%data[m].fdName%}			   
						    </a>
                       </h4>
                       $}
                       if(data[m].type == 'criteria'){
	                       {$
			                   <div class="list_up_icon" onclick="showAndHide();" id="showOperation">
			                   </div>
	                       $}
                       }
            {$
                    </div>
                </div>
            </div>
            $}
            {$
             <div class="lui_common_list_main_body" id="infoDiv">
                <div class="lui_common_list_4_content_left">
                    <div class="lui_common_list_4_content_right">
                        <div class="lui_common_list_4_content_centre">
                            <div class="forum_list_w">
                            $}
                            	if(data[m].type && data[m].type == 'criteria'){
		                            {$
		                            	<div class="quote_w">
		                            	$}
		                            	var fdDescription = data[m].fdDescription;
		                            	if(fdDescription){
			                            	{$
			                            		<em class="quoteL"></em>{%data[m].fdDescription%}
			                                    <em class="quoteR"></em>
			                            	$}
		                            	}
		                                {$ 
		                                </div>
		                            $}		
	                            }
	                            {$
                                 <table>
                                $} 
                                 if(data[m].type && data[m].type == 'criteria'){
                                 	{$
                                 		<tr class="header">
	                                        <td colspan="2">
	                                            <span>${lfn:message('km-forum:kmForumPost.today')}：{%data[m].topicCountToday%}</span><em>|</em><span>${lfn:message('km-forum:kmForumPost.topic')}：{%data[m].topicCount%}</span><em>|</em><span>${lfn:message('km-forum:kmForumPost.replyCount')}：{%data[m].replyCount%}</span>
	                                        </td>
	                                        <td class="modlue_author">
	                                        $}
	                                        	if(data[m].authAllEditors.length > 0){
	                                        		{$${lfn:message('km-forum:kmForumCategory.forumManagers')}$}
	                                        		var __data = data[m].authAllEditors;
	                                        		for(var i=0; i<__data.length; i++){
	                                        			if(i > 0){
	                                        				{$;$}
	                                        			}
	                                        			{$
	                                        				<ui:person personId="{%__data[i].personId%}" personName="{%__data[i].personName%}"></ui:person>
	                                        			$}
	                                        		}
	                                        	}
	                                        {$
	                                        </td>
	                                  </tr> 
                                 	$}
                               	 }
                                if(data[m].children){
                                	var children = data[m].children;
                                	var x = children.length ;
                                	if(x >3 && data[m].isShowAll != 'true'){
                                	 	x = 3;
                                	}
                                	for(var j=0; j< x; j++){
                                		var child = children[j];
	                                	{$
	                                	 <tr>
		                                        <td class="th1">
		                                            <div class="module_logo">
		                                               <a href="javascript:openURL('{%child.fdId%}');">
		                                                  <img src="${LUI_ContextPath}/km/forum/resource/images/forum_list_bg.png">
		                                               </a>
		                                            </div>
		                                            <div class="module_basicInfo">
		                                                <h2>
														   <a href="javascript:openURL('{%child.fdId%}');">
																{%child.fdName%}
														   </a>
														 $}
														   if(child.todayCount > 0){
														   {$
														   	<span class="count">${lfn:message("km-forum:kmForumPost.today")}:<em>{%child.todayCount%}</em></span>
														   	$}
														   }
														{$
													    </h2>
		                                                <p class="abstract">
		                                                     {%child.fdDescription%}
		                                                </p>
		                                                <p class="moderator"><em>
		                                                $}
		                                                    if(child.authAllEditors.length > 0){
				                                        		{$${lfn:message('km-forum:kmForumCategory.forumManagers')}$}
				                                        		var ___data = child.authAllEditors;
				                                        		for(var n=0; n<___data.length; n++){
				                                        			if(n > 0){
				                                        				{$;$}
				                                        			}
				                                        			{$
				                                        				<ui:person personId="{%___data[n].personId%}" personName="{%___data[n].personName%}"></ui:person>
				                                        			$}
				                                        		}
				                                        	}
		                                                {$	
		                                                  </em>
		                                                </p>
		                                            </div>
		                                        </td>
		                                        <td class="th2">
		                                            <div class="subject_reply_num" title="${lfn:message('km-forum:kmForumPost.count.post')}:{%child.fdTopicCountVal%}，${lfn:message('km-forum:kmForumPost.count.reply')}:{%child.fdPostCountVal%}">
														 {%child.fdTopicCountVal%}
														 /
														{%child.fdPostCountVal%}
										           </div>
		                                        </td>
		                                        <td class="th3">
		                                            <div class="last_reply">
		                                            		$}
		                                            		if(child.fdTopicCountVal == '0'){
		                                            		 {$ -- $}
		                                            		}else{
		                                            		 {$
		                                            		 	<div class="author_img">
				                                                </div>
				                                               $}
				                                               if(child.fdPosterHeadurl){
					                                               {$ 
																    	<img src="{%env.fn.formatUrl(child.fdPosterHeadurl)%}"/>
																   $} 
															   }else{
															   		{$
															   		 	<img src="${ LUI_ContextPath }/km/forum/resource/images/user_anon_img.png"/>
															   	 	$}
															   }
															   
															   {$ 
															    
				                                                <p class="p1">
						                                             <a href="${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=view&fdForumId={%child.fdForumId%}&fdTopicId={%child.fdTopicId%}" title="{%Com_HtmlEscape(child.docSubject)%}" target="_blank">
																	   <span class="textEllipsis" style="width: 260px"> {%Com_HtmlEscape(child.docSubject)%}</span>
																     </a> 
				                                                 </p>
				                                                <p class="p2">
				                                                    by
				                                                    
				                                                    $}
				                                                    if(child.fdPosterId){
					                                                    {$
																	        <ui:person personId="{%child.fdPosterId%}" personName="{%child.fdPosterName%}"></ui:person>
																	    $}
																    }else{
																    	{$
																    		<bean:message bundle="km-forum" key="kmForumTopic.fdIsAnonymous.title" />
																    	$}
																    }
																    {$    
					                                                  <span>	
					                                                  	{%child.docAlterTime%}
			                                                         </span>
		                                                         </p>
		                                            		 
		                                            		 $}
		                                            		}
		                                            	{$
		                                            </div>
		                                        </td>
		                                    </tr>
	                                	$}
                                	}
                                }
                                
                                {$
                                 </table>	
                                $}
                            {$
                            </div>
                         </div>
                         $}
                         	if(data[m].type=='criteria' && data[m].isShowAll=='' && data[m].rowSize>3){
                         	 {$
                         	 	 <div class="lui_common_loadModulesM" onclick="showMore();">
								     <span>${lfn:message('km-forum:kmForumPost.loadMoreData')}</span><a title="${lfn:message('km-forum:kmForumPost.loadMoreData')}" class="lui_common_loadPic"></a>
								</div>
                         	 $}
                         	}
                         {$
                     </div>
                  </div>
              </div>
            $}
          {$
         </div>
	 $}                   
	 }
	 if(m == 0){
	 	 {$
		 </div>
		 $}
	 }
 }
