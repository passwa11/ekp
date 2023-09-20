<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>

<c:set var="pop_editable" value="true" />
<c:if test="${JsParam.editable != null && JsParam.editable != ''}">
	<c:set var="pop_editable" value="${JsParam.editable }" />
</c:if>
<script>
	window['pop_editable_${JsParam.fdKey }'] = '${pop_editable }';
	Com_IncludeFile("jquery.js");
	Com_IncludeFile('jquery.ui.js', 'js/jquery-ui/');
</script>

<%-- <script type="text/javascript" src="${LUI_ContextPath }/resource/js/jquery.js"></script>
<script type="text/javascript" src="${LUI_ContextPath }/resource/js/jquery-ui/jquery.ui.js"></script> --%>

<script>
var colorChooserHintInfo={
	chooseText : '<bean:message key="button.ok"/>',
	cancelText : '<bean:message key="button.cancel"/>'
};
</script>

<script type="text/javascript" src="${LUI_ContextPath }/resource/js/colorpicker/spectrum.js"></script>
<link rel="stylesheet" href="${LUI_ContextPath }/resource/js/colorpicker/css/spectrum.css" />

<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/vue.min.js"></script>

<link rel="stylesheet" href="${LUI_ContextPath }/sys/portal/pop/import/css/designer.css" />

<div class="pop-container" id="app_${JsParam.fdKey }" data-editable="${pop_editable }">
	<div class="pop-aside pop-form">
		<div class="pop-form-item">
			<div class="pop-form-title">
				${lfn:message('sys-portal:sysPortalPopTpl.selectTemplate')}
			</div>
			<div class="pop-form-field">
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.width')}</div>
				<input class="pop-form-input" type="text" v-model="pop.style['width']" />
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.height')}</div>
				<input class="pop-form-input" type="text" v-model="pop.style['height']" />
			</div>
		</div>
		
		<div class="pop-form-item">
			<div class="pop-form-title">
				${lfn:message('sys-portal:sysPortalPopTpl.pop.back.setting')}
			</div>
			<div class="pop-form-field">
				<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="fdAttType" value="pic"/>
					<c:param name="fdMulti" value="false"/>
					<c:param name="fdKey" value="${JsParam.fdKey }"/>
					<c:param name="formBeanName" value="${JsParam.formBeanName }" />
				</c:import>
				<div class="pop-form-btn pop-form-btn-primary" 
					style="display:inline-block;height: 26px;line-height:26px;padding:0 20px;font-size: 14px;vertical-align:top;" 
						@click="handleSelectSysPopBg">
						${lfn:message('sys-portal:sysPortalPopTpl.pop.back.sys')}
					</div>
				<div 
					v-if="pop.props.bgUrl" 
					class="pop-form-image" 
					:style="{'background-image': 'url(${LUI_ContextPath }/' + pop.props.bgUrl + ')', 'margin-top': '8px'}">
				</div>
			</div>
		</div>
		
		<div class="pop-form-item">
			<div class="pop-form-title">
			${lfn:message('sys-portal:sysPortalPopTpl.pop.textBox')}
			</div>
			<div class="pop-form-field">
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.fontSize')}</div>
				<input class="pop-form-input" type="number" ref="textAreaFontSize" v-model="component['textarea']['fontSize']" />
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.fontColor')}</div>
				<input class="pop-form-input" type="text" ref="textAreaColor" v-model="component['textarea']['color']" />
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle')}</div>
				<div class="pop-form-checkbox-group">
					<label class="pop-form-checkbox"><input type="checkbox" v-model="component['textarea']['useBold']"/>${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle.bold')}</label>
					<label class="pop-form-checkbox"><input type="checkbox" v-model="component['textarea']['useItalic']"/>${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle.slanted')}</label>
					<label class="pop-form-checkbox"><input type="checkbox" v-model="component['textarea']['useUnderline']"/>${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle.underlined')}</label>
				</div>
				<div style="margin-top: 8px;" class="pop-form-btn pop-form-btn-primary" @click="handleAddTextArea">
					${lfn:message('button.create')}
				</div>
			</div>
		</div>
		
		<div class="pop-form-item">
			<div class="pop-form-title">
				${lfn:message('sys-portal:sysPortalPopTpl.pop.link.button')}
			</div>
			<div class="pop-form-field">
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.link.button')}</div>
				<input class="pop-form-input" type="text" ref="linkHref" v-model="component['link']['href']" />

				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.link.back')}</div>
				<div>
					<div class="pop-form-upload" style="display: inline-block;">
						<label for="linkBgUrl">${lfn:message('sys-portal:sysPortalMaterialMain.btn.upload')}</label>
						<input id="linkBgUrl" type="file" ref="linkBgUrl" @change="onChangeLinkBgUrl" accept="image/*"/>
					</div>
					<div class="pop-form-btn pop-form-btn-primary" 
						style="display:inline-block;height:26px;line-height:26px;padding:0 20px;font-size:14px;vertical-align:top;" 
						@click="handleSelectSysButtonBg">
						${lfn:message('sys-portal:sysPortalPopTpl.pop.back.sys')}
					</div>
				</div>
				<div 
					v-if="component['link']['bgUrl']" 
					class="pop-form-image" 
					:style="{'background-image': 'url(' + component['link']['bgUrl'] + ')', 'margin-top': '8px', 'height': '100px'}">
				</div>

				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.fontSize')}</div>
				<input class="pop-form-input" type="number" ref="linkFontSize" v-model="component['link']['fontSize']" />
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.fontColor')}</div>
				<input class="pop-form-input" type="text" ref="linkColor" v-model="component['link']['color']" />
				<div class="pop-form-label">${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle')}</div>
				<div class="pop-form-checkbox-group">
					<label class="pop-form-checkbox"><input type="checkbox" v-model="component['link']['useBold']"/>${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle.bold')}</label>
					<label class="pop-form-checkbox"><input type="checkbox" v-model="component['link']['useItalic']"/>${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle.slanted')}</label>
					<label class="pop-form-checkbox"><input type="checkbox" v-model="component['link']['useUnderline']"/>${lfn:message('sys-portal:sysPortalPopTpl.pop.fontStyle.underlined')}</label>
				</div>
				
				<div style="margin-top: 8px;" class="pop-form-btn pop-form-btn-primary" @click="handleAddLink">
					${lfn:message('button.create')}
				</div>
			</div>
		</div>
		
		<div class="pop-form-item">
			<div class="pop-form-btn" 
				@click="switchMode">
			<%
				String currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
				if(currentLocaleCountry != null && currentLocaleCountry.equals("US")){
					%>
					{{ mode == 'design' ? 'Source' : 'Design' }}
					<% 
				}
				if(currentLocaleCountry != null && currentLocaleCountry.equals("CN")){
					%>
					{{ mode == 'design' ? '查看源码' : '设计模式' }}
					<% 
				}
			%>
			</div>
		</div>
		
	</div>
	<div class="pop-main">
		
		<div class="pop-source" v-show="mode == 'source'">
			<textarea v-model="source"></textarea>
		</div>
	
		<div class="pop-design" v-show="mode == 'design'">
			<div class="pop" :style="rootStyle">
				<div class="pop-component" 
					v-for="(child, idx) in pop.children"
					:style="child.style"
					:ref="'pop-component-' + idx"
					:key="'pop-component-' + idx"
					:data-component-type="child.type"
					v-on:mouseup="handleMoveChild('pop-component-' + idx, child)"
				>
				
					<!-- 操作按钮 -->		
					<span class="pop-component-btn" data-type="move"><i></i></span>
					<span class="pop-component-btn" data-type="delete" @click="handleDeleteChild(idx)"><i></i></span>
					
					<!-- 文本框 -->
					<textarea v-if="child.type == 'textarea'" 
						:readonly="'${pop_editable }' == 'false'"
						v-model="child.props.value">
					</textarea>

					<!-- 链接按钮 -->
					<span v-if="child.type == 'link'" class="pop-component-btn" data-type="edit" @click="handleEditLink(child)"><i></i></span>				
					<textarea v-if="child.type == 'link'"
						:readonly="'${pop_editable }' == 'false'"
						v-model="child.props.value"
						:style="{ 'line-height': child.style.height, 'text-align': 'center' }">
						
					</textarea>
					
				</div>
			</div>
		</div>
	</div>
	
	
</div>

<script>

	// 数据格式
	/*
	{
		type: 'root',
		props: {
			bgUrl: 'xxx'			
		},
		style: {
			width: '300px',
			height: '200px',
		},
		children: [
			{
				type: 'textarea',
				props: {
					value: 'xxx'
				},
				style: {
	
				}
			},
			{
				type: 'button',
				props: {
	
				},
				style: {
				
				}
			}
			...
		]
	}	
	*/
	
	// 数据初始化
	window['popData_${JsParam.fdKey }'] = null
	try {
		window['popData_${JsParam.fdKey }'] = JSON.parse('${JsParam.content}');
	} catch(e) {
		window['popData_${JsParam.fdKey }'] = {};
	}
	var _textBox = '${lfn:message("sys-portal:sysPortalPopTpl.pop.textBox")}';
	var _viewDetails = '${lfn:message("sys-portal:sysPortalPopTpl.pop.details")}'
	window['popData_${JsParam.fdKey }'].type = window['popData_${JsParam.fdKey }'].type || 'root';
	window['popData_${JsParam.fdKey }'].props = window['popData_${JsParam.fdKey }'].props || { bgUrl: '' };
	window['popData_${JsParam.fdKey }'].style = window['popData_${JsParam.fdKey }'].style || { width: '400px', height: '300px' };
	window['popData_${JsParam.fdKey }'].children = window['popData_${JsParam.fdKey }'].children || [];
	//#110150 门户里弹窗主表选择默认的模板，无法带出内容，有报错。
	var temFlag = Com_GetUrlParameter(location.href, "sysTplId") == null;
 	if(window['popData_${JsParam.fdKey }'] && 'root' == window['popData_${JsParam.fdKey }'].type && !temFlag){
		window['popData_${JsParam.fdKey }'].children[0].props.value = _textBox;
		window['popData_${JsParam.fdKey }'].children[1].props.value = _viewDetails;
	} 
	// 实例化
	window['app_${JsParam.fdKey }'] = new Vue({
		el: '#app_${JsParam.fdKey }',
		data: {
			mode: 'design',			
			pop: window['popData_${JsParam.fdKey }'],
			source:"",
			component: {
				textarea: {
					fontSize: 18,
					color: '#000000',
					useBold: false,
					useItalic: false,
					useUnderline: false
				},
				link: {
					bgUrl: '',
					href: 'http://',
					fontSize: 18,
					color: '#000000',
					useBold: false,
					useItalic: false,
					useUnderline: false
				}
			}
		},
		methods: {
			switchMode: function(){
				if(this.mode=="source"){
					this.pop=JSON.parse(this.source);
				}else{
					this.source=JSON.stringify(this.pop);
				}
				this.mode = (this.mode == 'design' ? 'source' : 'design');
			},
			handleAddTextArea: function() {
				var ctx = this;
				var textArea = ctx.component.textarea;
				ctx.pop.children.push({
					type: 'textarea',
					props: {
						value: '${lfn:message("sys-portal:sysPortalPopTpl.pop.textBox")}'
					},
					style: {
						width: '96px',
						height: '48px',
						left: '0px',
						top: '0px',
						color: textArea.color,
						'font-size': textArea.fontSize + 'px',
						'font-weight': textArea.useBold ? 'bold' : 'normal',
						'font-style': textArea.useItalic ? 'italic' : 'normal',
						'text-decoration': textArea.useUnderline ? 'underline' : 'none'
					}
				});
			},
			
			handleAddLink: function() {
				var ctx = this;
				var link = ctx.component.link;
				ctx.pop.children.push({
					type: 'link',
					props: {
						value: '${lfn:message("sys-portal:sysPortalPopTpl.pop.details")}',
						href: link.href
					},
					style: {
						width: '128px',
						height: '36px',
						left: '0px',
						top: '0px',
						color: link.color,
						'font-size': link.fontSize + 'px',						
						'font-weight': link.useBold ? 'bold' : 'normal',
						'font-style': link.useItalic ? 'italic' : 'normal',
						'text-decoration': link.useUnderline ? 'underline' : 'none',
						'background-image': 'url(' + link.bgUrl + ')'
					}
				});
				
			},
			
			handleSelectSysButtonBg: function() {
				
				var ctx = this;
				
				seajs.use(['lui/dialog'], function(dialog) {
					
					var d = null;

					var element = $('<div/>').addClass('bgselector-dialog');
					
					$.getJSON('${LUI_ContextPath}/sys/portal/pop/template/assets.json').then(function(res) {
						$.each(res['bg_button'] || [], function(_, bgUrl) {
							
							var item = $('<div/>').appendTo(element);
							$('<img/>').attr('src', '${LUI_ContextPath}/' + bgUrl).appendTo(item);
							
							item.click(function() {
								ctx.component.link.bgUrl = '${LUI_ContextPath}/' + bgUrl;
								d.hide();
							});
							
						});
					});
					
					d = dialog.build({
		                config: {
		                  	width: 550,
		                  	height: 350,
		                  	lock: true,
		                  	cache: false,
		                  	win: window,
		                  	content: {
		                    	type: "element",
		                    	elem: element
		                  	},
		                  	title: '${lfn:message("sys-portal:sysPortalPopTpl.pop.back.select")}'
						}
					}).show();
					
				});
			},

			handleSelectSysPopBg: function() {
				
				var ctx = this;
				
				seajs.use(['lui/dialog'], function(dialog) {
					
					var d = null;

					var element = $('<div/>').addClass('bgselector-dialog');
					
					$.getJSON('${LUI_ContextPath}/sys/portal/pop/template/assets.json').then(function(res) {
						$.each(res['bg_pop'] || [], function(_, bgUrl) {
							
							var item = $('<div/>').appendTo(element);
							$('<img/>').attr('src', '${LUI_ContextPath}/' + bgUrl).appendTo(item);
							
							item.click(function() {
								ctx.pop.props.bgUrl = bgUrl;
								d.hide();
							});
							
						});
					});
					
					d = dialog.build({
		                config: {
		                  	width: 550,
		                  	height: 350,
		                  	lock: true,
		                  	cache: false,
		                  	win: window,
		                  	content: {
		                    	type: "element",
		                    	elem: element
		                  	},
		                  	title: '${lfn:message("sys-portal:sysPortalPopTpl.pop.back.select")}'
						}
					}).show();
					
				});
			},
			
			handleEditLink: function(link) {
				
				seajs.use(['lui/dialog'], function(dialog) {
					
					var d = null;
					var element = $('<div/>').addClass('pop-dialog');
					var top = $('<div/>').addClass('pop-dialog-top').appendTo(element);
					var inputLink = $('<input/>').val(link.props.href || 'http://').appendTo(top);
					
					var bottom = $('<div/>').addClass('pop-dialog-bottom').appendTo(element);
					var checkBtn = $('<div/>')
									.text('${lfn:message("button.ok")}')
									.attr('data-type', 'check')	
									.addClass('pop-dialog-btn')
									.appendTo(bottom);
					checkBtn.click(function() {
						link.props.href = inputLink.val();
						d.hide();
					});
					
					var closeBtn = $('<div/>')
									.text('${lfn:message("button.cancel")}')
									.attr('data-type', 'close')	
									.addClass('pop-dialog-btn')
									.appendTo(bottom);
					closeBtn.click(function() {
						d.hide();
					});
					
					d = dialog.build({
						config: {
							width: 450,
							height: 128,
							lock: true,
							cache: false,
							content: {
								type : "element",
								elem : element
							},
							title: '${lfn:message("sys-portal:sysPortalPopTpl.pop.modify.link")}'
						}
						
					}).show();
					
				});
				
			},
			onChangeLinkBgUrl: function(e) {
	
				var ctx = this;
				
				var file = e.target.files[0];
				
				if(!file) {
					return;
				}
				
				var reader = new FileReader();
				
				reader.onload = function(_e) {
					ctx.component.link.bgUrl = _e.target.result;
				}
				
				reader.readAsDataURL(file);
				
			},
			
			
			handleMoveChild: function(refName, data) {
				data['style']['left'] = $(this.$refs[refName]).css('left');
				data['style']['top'] = $(this.$refs[refName]).css('top');
				data['style']['width'] = $(this.$refs[refName]).css('width');
				data['style']['height'] = $(this.$refs[refName]).css('height');
			},
			
			handleDeleteChild: function(idx) {
				
				var children = this.pop.children || [];
				
				var _children = [], i = 0, l = children.length;
				for(i; i < l; i++) {
					if(i == idx) {
						continue;
					}
					
					_children.push(children[i]);
				}				
				
				this.pop.children = _children;
				
			},
			
			initComponents: function() {
				
				// 非编辑模式不执行
				if(window['pop_editable_${JsParam.fdKey }'] != 'true') {
					return;
				}
				
				$('#app_${JsParam.fdKey } .pop-component').draggable();
				$('#app_${JsParam.fdKey } .pop-component').resizable();
			}
		},
		
		computed: {
			rootStyle: function() {
				var style = $.extend({}, this.pop.style || {}, {
					'background-image': 'url(${LUI_ContextPath }/' + this.pop.props.bgUrl + ')'
				});
				
				return style;
			}
		},
		
		updated: function() {
			this.initComponents();
		},
		mounted: function() {
			var ctx = this;
			
			ctx.initComponents();
			
			//颜色选择
			$(ctx.$refs.textAreaColor).spectrum({
				preferredFormat: 'hex',
				change: function(color) {
					ctx.component.textarea.color = color.toHexString();
				}
			});
			
			$(ctx.$refs.linkColor).spectrum({
				preferredFormat: 'hex',
				change: function(color) {
					ctx.component.link.color = color.toHexString();
				}
			});
		}
	});
	
	// 监听背景图片上传事件
	window['attachmentObject_${JsParam.fdKey }'].on('uploadSuccess', function(res){

		var url = 'sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=' + res.file.fdId;
		window['app_${JsParam.fdKey }'].$data['pop']['props']['bgUrl'] = url;
		
	});
	
</script>