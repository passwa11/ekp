<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var paging = layout.parent;
{$
			<!-- 默认分页 -->
			<div class="lui_paging_box" style="display:none;">
                <div class="lui_paging_header_left">
                    <div class="lui_paging_header_right">
                        <div class="lui_paging_header_centre">
                        </div>
                    </div>
                </div>
                <div class="lui_paging_content_left">
                    <div class="lui_paging_content_right">
                        <div class="lui_paging_content_centre">
                            <ul class="lui_paging_contentBox clearfloat">
$}
	                         	if (paging.hasPre) {
									{$
										<div class="lui_paging_next_left">
											<div class="lui_paging_next_right">
												<div class="lui_paging_next_center">
													<li class="lui_paging_pre"><a href="javascript:;" data-lui-paging-num="{% paging.currentPage-1 %}">${lfn:message('sys-ui:ui.listview.paging.pre') }</a></li>
												</div>
											</div>
										</div>
									$}
								}
								if (!paging.marginLeft && !paging.marginRight){
									for(var i = 1;i < paging.totalPage + 1; i++){
										{$
											<div class="lui_paging_num_left $} if(i == paging.currentPage){ {$ selected $} } {$">
												<div class="lui_paging_num_right">
													<div class="lui_paging_num_center">
														<li>
															<a href="javascript:;" data-lui-paging-num="{% i %}">{% i %}</a>
														</li>
													</div>
												</div>
											</div>
										$}
									}
								} else {
									if(paging.marginLeft){
										{$
											<div class="lui_paging_num_left">
												<div class="lui_paging_num_right">
													<div class="lui_paging_num_center">
														<li><a href="javascript:;" data-lui-paging-num="1">1</a></li>
													</div>
												</div>
											</div>
											<div class="lui_paging_ellipsis_left">
												<div class="lui_paging_ellipsis_right">
													<div class="lui_paging_ellipsis_center">
														<li class="lui_paging_ellipsis"><span>...</span></li>
													</div>
												</div>
											</div>
										$}
									}
									var range = paging.range;
									for(var j = range[0]; j < range[1]+1; j++){
										{$
											<div class="lui_paging_num_left $} if(j == paging.currentPage){{$ selected $}} {$">
												<div class="lui_paging_num_right">
													<div class="lui_paging_num_center">
														<li><a href="javascript:;" data-lui-paging-num="{% j %}">{% j %}</a></li>
													</div>
												</div>
											</div>
										$}
									}
									if(paging.marginRight){
										{$
											<div class="lui_paging_ellipsis_left">
												<div class="lui_paging_ellipsis_right">
													<div class="lui_paging_ellipsis_center">
														<li class="lui_paging_ellipsis"><span>...</span></li>
													</div>
												</div>
											</div>
											<div class="lui_paging_num_left">
												<div class="lui_paging_num_right">
													<div class="lui_paging_num_center">
														<li><a href="javascript:;" data-lui-paging-num="{% paging.totalPage %}">{% paging.totalPage %}</a></li>
													</div>
												</div>
											</div>
										$}
									}
								}            
								if (paging.hasNext) {
									{$
										<div class="lui_paging_next_left">
											<div class="lui_paging_next_right">
												<div class="lui_paging_next_center">
													<li class="lui_paging_next">
														<a href="javascript:;" data-lui-paging-num="{% paging.currentPage+1 %}">${lfn:message('sys-ui:ui.listview.paging.next') }
														</a>
													</li>
												</div>
											</div>
										</div>
									$}
								}
{$
								<div class="lui_paging_total_left">
									<div class="lui_paging_total_right">
										<div class="lui_paging_total_center">
                            				<li class="lui_paging_mr10 lui_paging_ml10"><span>${lfn:message('sys-ui:ui.listview.paging.total') }&nbsp;{%paging.totalSize%}&nbsp;${lfn:message('sys-ui:ui.listview.paging.item') } </span></li>
                               			</div>
                               		</div>
                               	</div>
                               	<div class="lui_paging_jump_left">
									<div class="lui_paging_jump_right">
										<div class="lui_paging_jump_center">
                                			<li class="lui_paging_mr10"><span>${lfn:message('sys-ui:ui.listview.paging.jumpto') }<input type="text" data-lui-mark="paging.pageno" value="{%paging.currentPage%}"/>${lfn:message('sys-ui:ui.listview.paging.page') }</span></li>
                                		</div>
                                	</div>
                                </div>
                                <div class="lui_paging_amount_left">
									<div class="lui_paging_amount_right">
										<div class="lui_paging_amount_center">
			                                <li>
			                                	<span>
			                                		${lfn:message('sys-ui:ui.listview.paging.display') }
			                                		<input type="text" data-lui-mark="paging.amount" value="{%paging.pageSize%}" class="default_pageSize_input"/>
			                              		  	${lfn:message('sys-ui:ui.listview.paging.item') } 
												</span>
											</li>
										</div>
									</div>
								</div>
								<div class="lui_paging_go_left">
									<div class="lui_paging_go_right">
										<div class="lui_paging_go_center">
											<li class="lui_paging_ml10"><a class="lui_paging_btn" href="javascript:;" data-lui-mark="paging.jump" title="Go">Go</a></li>
										</div>
									</div>
								</div>
								
								<div class="lui_paging_change_left" style="margin-top:5px;">
									<div class="lui_paging_change_right">
										<div class="lui_paging_change_center">
											<li class="lui_paging_ml10">
												<select class="default_change_select" >
													<option value="default">${lfn:message('sys-ui:ui.listview.paging.default') }</option>
													<option value="simple">${lfn:message('sys-ui:ui.listview.paging.simple') }</option>
												</select>
											</li>
										</div>
									</div>
								</div>
                            </ul>
                        </div>
                    </div>
                </div>
            	
                <div class="lui_paging_foot_left">
                    <div class="lui_paging_foot_right">
                        <div class="lui_paging_foot_centre">
                        </div>
                    </div>
                </div>
            </div>
$}


{$
			<!-- 简单分页 -->
			<div class="lui_paging_box" >
                <div class="lui_paging_header_left">
                    <div class="lui_paging_header_right">
                        <div class="lui_paging_header_centre">
                        </div>
                    </div>
                </div>
                <div class="lui_paging_content_left">
                    <div class="lui_paging_content_right">
                        <div class="lui_paging_content_centre">
                            <ul class="lui_paging_contentBox clearfloat">
$}
	                         	if (paging.hasPre) {
									{$
										<div class="lui_paging_next_left">
											<div class="lui_paging_next_right">
												<div class="lui_paging_next_center">
													<li class="lui_paging_pre"><a href="javascript:;" data-lui-paging-num="{% paging.currentPage-1 %}">${lfn:message('sys-ui:ui.listview.paging.pre') }</a></li>
												</div>
											</div>
										</div>
									$}
									{$
										<div class="lui_paging_ellipsis_left">
											<div class="lui_paging_ellipsis_right">
												<div class="lui_paging_ellipsis_center">
													<li class="lui_paging_ellipsis"><span>...</span></li>
												</div>
											</div>
										</div>
									$}
								}
								{$
									<div class="lui_paging_num_left $} {$ selected $} {$">
										<div class="lui_paging_num_right">
											<div class="lui_paging_num_center">
												<li>
													<a href="javascript:;" data-lui-paging-num="{% paging.currentPage %}">{% paging.currentPage %}</a>
												</li>
											</div>
										</div>
									</div>
								$}
								if (paging.hasNext) {
									{$
										<div class="lui_paging_ellipsis_left">
											<div class="lui_paging_ellipsis_right">
												<div class="lui_paging_ellipsis_center">
													<li class="lui_paging_ellipsis"><span>...</span></li>
												</div>
											</div>
										</div>
									$}
									{$
										<div class="lui_paging_next_left">
											<div class="lui_paging_next_right">
												<div class="lui_paging_next_center">
													<li class="lui_paging_next">
														<a href="javascript:;" data-lui-paging-num="{% paging.currentPage+1 %}">${lfn:message('sys-ui:ui.listview.paging.next') }
														</a>
													</li>
												</div>
											</div>
										</div>	
									$}
								}
								{$
                                <div class="lui_paging_amount_left">
									<div class="lui_paging_amount_right">
										<div class="lui_paging_amount_center">
			                                <li>
			                                	<span>
			                                		${lfn:message('sys-ui:ui.listview.paging.display') }
			                                		<input type="text" data-lui-mark="paging.amount" value="{%paging.pageSize%}" class="simple_pageSize_input"/>
			                              		  	${lfn:message('sys-ui:ui.listview.paging.item') } 
												</span>
											</li>
										</div>
									</div>
								</div>
								<div class="lui_paging_go_left">
									<div class="lui_paging_go_right">
										<div class="lui_paging_go_center">
											<li class="lui_paging_ml10"><a class="lui_paging_btn" href="javascript:;" data-lui-mark="paging.jump" title="Go">Go</a></li>
										</div>
									</div>
								</div>		
								$}
								{$
									<div class="lui_paging_change_left" style="margin-top:5px;">
										<div class="lui_paging_change_right">
											<div class="lui_paging_change_center">
												<li class="lui_paging_ml10">
													<select class="simple_change_select" >
														<option value="simple">${lfn:message('sys-ui:ui.listview.paging.simple') }</option>
														<option value="default">${lfn:message('sys-ui:ui.listview.paging.default') }</option>
													</select>
												</li>
											</div>
										</div>
									</div>
								$}
								{$<input type="hidden" data-lui-mark="paging.pageno" value="{%paging.currentPage%}"/>$}
								{$<input type="hidden" data-lui-mark="paging.amount" value="{%paging.pageSize%}"/>$}
{$								
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="lui_paging_foot_left">
                    <div class="lui_paging_foot_right">
                        <div class="lui_paging_foot_centre">
                        </div>
                    </div>
                </div>
            </div>
$}
