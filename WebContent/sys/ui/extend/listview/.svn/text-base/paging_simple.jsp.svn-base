<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var paging = layout.parent;
{$
			<div class="lui_paging_box">
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
			                                		<input type="text" data-lui-mark="paging.amount" value="{%paging.pageSize%}"/>
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
