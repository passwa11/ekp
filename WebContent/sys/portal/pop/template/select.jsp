<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
 
<template:include ref="default.dialog">

	<template:replace name="head">
	
		<style>
			.pop-template-container {
			}
			
			.pop-template-list {
				padding: 16px;
			}
			
			.pop-template-item {
				position: relative;
				display: inline-block;
				padding: 6px;
				width: 152px;
				cursor: pointer;
				border: 2px solid transparent;
				border-radius: 4px;
			}
			
			.pop-template-item-btn {
				display: none;
				position: absolute;
				right: 6px;
				top: 6px;
				font-size: 0;
				width: 24px;
				height: 24px;
				border-radius: 12px;
				text-align: center;
				line-height: 24px;
			}
			
			.pop-template-item:hover .pop-template-item-btn {
				display: block;
			}
			
			
			.pop-template-item-btn i {
				display: inline-block;
				vertical-align: middle;
				width: 18px;
				height: 18px;
				background-size: 100% 100%;
				background-repeat: no-repeat;
				background-position: 50% 50%;
			}
			
			.pop-template-item-btn[data-type="remove"] {
				background-color: #E60000;
			}
			
			.pop-template-item-btn[data-type="remove"] i {
				background-image: url(../resource/images/icon_remove.png);
			}
			
			.pop-template-item-btn[data-type="edit"] {
				background-color: #4285f4;
				top: 36px;
			}
			
			.pop-template-item-btn[data-type="edit"] i {
				background-size: 70% 70%;
				background-image: url(../resource/images/icon_edit.png);
			}
			
			.pop-template-item[data-type="button"] .pop-template-preview {
				font-size: 64px;
				line-height: 112px;
				text-align: center;
			}
			
			.pop-template-item:hover {
				border-color: #4285f4;
			}
			
			.pop-template-preview {
				height: 112px;
			}
			
			.pop-template-preview img {
				max-width: 100%;
				max-height: 100%;
			}
			
			.pop-template-name {
				text-align: center;
				white-space: nowrap;
				text-overflow: ellipsis;
				overflow: hidden;
				font-size: 16px;
			}
		</style>
	
	</template:replace>

	<template:replace name="content">
	
		<div class="pop-template-container">
			<div class="pop-template-list" id="popTemplateList">
				<!--  
					<div class="pop-template-item">
						<div class="pop-template-preview"></div>
						<div class="pop-template-name"></div>
					</div>
				-->
			</div>
		</div>
	
	
		<script>
		
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
					
				function TemplateList(element, options) {
					this.element = element;
					
					this.defaultOptions = {};
					this.options = $.extend(this.defaultOptions, options || {});
					
					this.initialize();
				}
				
				TemplateList.prototype = {
					initialize: function() {
						this.render();
						this.bindEvents();
					},
					
					getData: function(cb) {
						
						var getSysTpls = $.getJSON('${LUI_ContextPath}/sys/portal/pop/template/data.json');
						var getTpls = $.getJSON('${LUI_ContextPath}/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=data&pageno=1&rowsize=150');
						
						$.when(getSysTpls, getTpls).then(function(res1, res2) {
		
							var sysTpls = [];
							try {
								$.each(res1[0] || [], function(_, d) {
									d.type = 'sys';
									sysTpls.push(d);
								});
							} catch(e) { }
							
							var tpls = [];
							try {
								$.each(res2[0].datas || [], function(_, d) {
									var t = {};
									$.each(d || [], function(__, _d) {
										if(_d.col == 'fdId') {
											t['id'] = _d.value;
										} else if (_d.col == 'docSubject') {
											t['name'] = _d.value;
										} else {
											t[_d.col] = _d.value;					
										}
									});
									t.type = 'custom';
									tpls.push(t);
								});
							} catch(e) {}
							
							var allTpls = sysTpls.concat(tpls);
							
							cb(allTpls || []);
						});
						
					},
					
					bindEvents: function() {
						
						var self = this;
						
						this.element.on('click', '.pop-template-item', function(e) {
							var action = $(e.target).attr('data-action') || '';
							var type = $(this).attr('data-type') || '';
							var id = $(this).attr('data-id') || '';
							
							if(action == 'edit') {
								Com_OpenWindow('${LUI_ContextPath}/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=edit&fdId=' + id, '_blank');
								$dialog.hide();
							} else if(action == 'remove') {
								
								dialog.confirm('<bean:message key="sys.portal.pop.confirm" bundle="sys-portal" />', function(checked) {
									
									if(checked) {
										var d = dialog.loading('<bean:message key="sys.portal.pop.deleting" bundle="sys-portal" />');
										$.get('${LUI_ContextPath}/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=delete&fdId=' + id).then(function(res) {
											d.hide();
											dialog.success('<bean:message key="sys.portal.pop.deleted" bundle="sys-portal" />');
											self.render();
										});
									}
									
								});
								
							} else {
								if(type == 'button') {
									Com_OpenWindow('${LUI_ContextPath}/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=add', '_blank');
									$dialog.hide();
								} else {
									$dialog.hide({
										type: type,
										id: id
									});
								}
							}
						});
					},

					render: function() {

						var self = this;
						
						self.element.empty();
						
						self.getData(function(data) {
							
							$.each(data || [], function(idx, item) {
								
								var popTemplateItem = $('<div/>').addClass('pop-template-item')
															.attr('data-type', item.type)
															.attr('data-id', item.id)
															.appendTo(self.element);
								
								$('<img/>').attr('src', '${LUI_ContextPath}/' + item.preview)
									.appendTo($('<div/>').addClass('pop-template-preview').appendTo(popTemplateItem));
								
								$('<div/>').addClass('pop-template-name').text(item.name).appendTo(popTemplateItem);
		
								if(item.type == 'custom') {
									$('<span/>').addClass('pop-template-item-btn').attr('data-type', 'edit')
															.append($('<i/>').attr('data-action', 'edit')).appendTo(popTemplateItem);
									
									$('<span/>').addClass('pop-template-item-btn').attr('data-type', 'remove')
															.append($('<i/>').attr('data-action', 'remove')).appendTo(popTemplateItem);
								}
								
							});
							
							var addBtn = $('<div/>').addClass('pop-template-item')
												.attr('data-type', 'button')
												.appendTo($('#popTemplateList'));

							$('<img/>').attr('src', '${LUI_ContextPath}/sys/portal/pop/template/images/pop_004.png').appendTo($('<div/>').addClass('pop-template-preview').appendTo(addBtn));
							$('<div/>').addClass('pop-template-name').text('<bean:message key="sys.portal.pop.add.template" bundle="sys-portal" />').appendTo(addBtn);

						});
					}
				};
				
				
				new TemplateList($('#popTemplateList'));
					
			});
		
		</script>
	
	
	</template:replace>
</template:include>
