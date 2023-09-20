<%@page import="java.util.Set"%>
<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.io.FilenameFilter"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	LinkedHashMap<String,String> langs = LoginTemplateUtil.getDesignLangs();
	pageContext.setAttribute("langs", langs);
	pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());
%>
<!-- 企业LOGO -->
<div class="option" data-hover="isLogoActive" @mouseenter="enter" @mouseleave="leave">
	<div class="title"><bean:message bundle="sys-profile" key="sys.profile.design.logo"/></div>
	<div class="content">
		<div class="limit"> png/jpg/jpeg/gif/ico</div>
		<form id="multi_logo" enctype="multipart/form-data" method="post">
			<span class="btn-file">
		        <span class="f12"><bean:message bundle="sys-profile" key="sys.profile.design.selectImg"/></span>
		        <input type="hidden" name="configKey"/>
		        <input type="hidden" name="fileName"/>
		        <input type="file" name="file" accept=".png,.jpg,.jpeg,.gif,.ico" onclick="this.value='';" onchange="uploadImg('multi_logo');"/>
		    </span>
		</form>
	</div>
	<div>
		<label>
			<input type="radio" value="logoPositionOnLogoShow" v-model="login_logo_position" />
			<bean:message bundle="sys-profile" key="sys.profile.design.logo.show" />
		</label>
		<label>
			<input type="radio" value="logoPositionOnLogoHide" v-model="login_logo_position" />
			<bean:message bundle="sys-profile" key="sys.profile.design.logo.hide" />
		</label>
	</div>
</div>
<!-- 背景图 -->
<div class="option" data-hover="isBgImageActive" @mouseenter="enter" @mouseleave="leave">
	<div class="title"><bean:message bundle="sys-profile" key="sys.profile.design.upload.bgimg"/></div>
	<div class="content">
		<div class="limit"> section1 </div>
		<form id="multi_section1_bg" enctype="multipart/form-data" method="post">
			<span class="btn-file">
		        <span class="f12"><bean:message bundle="sys-profile" key="sys.profile.design.selectImg"/></span>
		        <input type="hidden" name="configKey"/>
		        <input type="hidden" name="fileName"/>
		        <input type="file" name="file" accept=".png,.jpg,.jpeg,.gif" onclick="this.value='';" onchange="uploadImg('multi_section1_bg');"/>
		    </span>
		</form>
	</div>
	<div class="content">
		<div class="limit"> section2 </div>
		<form id="multi_section2_bg" enctype="multipart/form-data" method="post">
			<span class="btn-file">
		        <span class="f12"><bean:message bundle="sys-profile" key="sys.profile.design.selectImg"/></span>
		        <input type="hidden" name="configKey"/>
		        <input type="hidden" name="fileName"/>
		        <input type="file" name="file" accept=".png,.jpg,.jpeg,.gif" onclick="this.value='';" onchange="uploadImg('multi_section2_bg');"/>
		    </span>
		</form>
	</div>
	<div class="content">
		<div class="limit"> section3 </div>
		<form id="multi_section3_bg" enctype="multipart/form-data" method="post">
			<span class="btn-file">
		        <span class="f12"><bean:message bundle="sys-profile" key="sys.profile.design.selectImg"/></span>
		        <input type="hidden" name="configKey"/>
		        <input type="hidden" name="fileName"/>
		        <input type="file" name="file" accept=".png,.jpg,.jpeg,.gif" onclick="this.value='';" onchange="uploadImg('multi_section3_bg');"/>
		    </span>
		</form>
	</div>
</div>
<!-- 登录框标题 -->
<div class="option" data-hover="isLoginTitleActive" @mouseenter="enter" @mouseleave="leave">
	<div class="title"><bean:message bundle="sys-profile" key="sys.profile.design.login.form.title"/></div>
	<div class="content">
		<label><!-- 显示 -->
			<input type="radio" value="loginTitleShow" v-model="login_title_show"/>
			<bean:message bundle="sys-profile" key="sys.profile.design.login.title.show"/>
		</label>
		<label><!-- 隐藏 -->
			<input type="radio" value="loginTitleHide" v-model="login_title_show"/>
			<bean:message bundle="sys-profile" key="sys.profile.design.login.title.hide"/>
		</label>	
	</div>
	<c:forEach items="${langs}" var="lang">
		<div class="content">
			<div class="input">
				<input placeholder="${lang.value}" title="${lang.value}" type="text" v-model="login_form_title_${lang.key}"/>
			</div>
		</div>
	</c:forEach>
</div>
<!-- 登录框位置 -->
<div class="option">
	<div class="title"><bean:message bundle="sys-profile" key="sys.profile.design.login.form.position"/></div>
	<div class="content">
		<label>
			<input type="radio" value="loginFormOnLeft" v-model="login_form_align" />
			<bean:message bundle="sys-profile" key="sys.profile.design.login.form.left"/>
		</label>
		<label>
			<input type="radio" value="loginFormOnCenter" v-model="login_form_align" />
			<bean:message bundle="sys-profile" key="sys.profile.design.login.form.center"/>
		</label>
		<label>
			<input type="radio" value="loginFormOnRight" v-model="login_form_align" />
			<bean:message bundle="sys-profile" key="sys.profile.design.login.form.right"/>
		</label>
	</div>
</div>
<!-- 登录按钮 -->
<div class="option" data-hover="isLoginButtonActive" @mouseenter="enter" @mouseleave="leave">
	<div class="title"><bean:message bundle="sys-profile" key="sys.profile.design.loginBtn"/></div>
	<div class="content">
		<span class="info"><bean:message bundle="sys-profile" key="sys.profile.design.loginBtn.color"/></span>
		<div data-pick="loginBtn_bgColor" class="colorpicker-component colorpicker-element">
			<span @click="showColorPicker" class="color-on"><i v-bind:style="{backgroundColor:loginBtn_bgColor}"></i></span>
		</div>
	</div>
	<div class="content">
		<span class="info"><bean:message bundle="sys-profile" key="sys.profile.design.loginBtn.colorHover"/></span>
		<div data-pick="loginBtn_bgColor_hover" class="colorpicker-component colorpicker-element">
			<span @click="showColorPicker" class="color-on"><i v-bind:style="{backgroundColor:loginBtn_bgColor_hover}"></i></span>
		</div>
	</div>
	<div class="content">
		<span class="info"><bean:message bundle="sys-profile" key="sys.profile.design.loginBtn.fontColor"/></span>
		<div data-pick="loginBtn_font_color" class="colorpicker-component colorpicker-element">
			<span @click="showColorPicker" class="color-on"><i v-bind:style="{backgroundColor:loginBtn_font_color}"></i></span>
		</div>
	</div>
	<c:forEach items="${langs }" var="lang">
		<div class="content">
			<div class="input">
				<input placeholder="${lang.value }" title="${lang.value }" type="text" v-model="loginBtn_text_${lang.key }"/>
			</div>
		</div>
	</c:forEach>
</div>
<!-- 标题栏图标与标题 -->
<div class="option">
	<div class="title"><bean:message bundle="sys-profile" key="sys.profile.design.titleAndImage"/></div>
	<div class="content">
		<img alt="" src="${LUI_ContextPath }${loginImagePath }/${param.templateName}/login_title.png"/>
	</div>
	<div class="content">
		<div class="limit">16*16 ico</div>
		<form id="title_image" enctype="multipart/form-data" method="post">
			<span class="btn-file">
		        <span class="f12"><bean:message bundle="sys-profile" key="sys.profile.design.selectImg"/></span>
		        <input type="hidden" name="configKey"/>
		        <input type="hidden" name="fileName"/>
		        <input type="hidden" name="isRandom"/>
		        <input type="file" accept=".ico" name="file" onclick="this.value='';" onchange="uploadImg('title_image');"/>
		    </span>
		</form>
	</div>
	<c:forEach items="${langs }" var="lang">
		<div class="content">
			<div class="input">
				<input placeholder="${lang.value }" title="${lang.value }" type="text" v-model="title_${lang.key }"/>
			</div>
		</div>
	</c:forEach>
</div>
<script>
	var imagePath = "${LUI_ContextPath}${loginImagePath}/${param.templateName}/";
	$(document).ready(function() {
		//颜色选择器
		$.each($('.colorpicker-component'),function(index,item) {
			var $target = $(item);
			var pick_prop = $target.data('pick');
			var oriValue = config[pick_prop];
			$target.colorpicker({
				component:'.color-on',
				align:'cux_right',
				color:config[pick_prop],
				template:getTemplate(pick_prop)
			}).on('changeColor',function(e) {
				var _color = e.color.toString('rgba');
				$('#'+pick_prop+'_show').val(_color);
				config[pick_prop] = _color;
			});
			//确定
			$('#'+pick_prop+'_ok').on('click',function() {
				var _color = $('#'+pick_prop+'_show').val();
				$target.colorpicker('setValue', _color);
				$target.colorpicker('hide');
			});
			//取消
			$('#'+pick_prop+'_cancel').on('click',function() {
				config[pick_prop] = oriValue;
				$target.colorpicker('hide');
			});
			colorPickers[pick_prop] = $target;
		});
	});
	
	function getTemplate(picker) {
		return '<div class="colorpicker dropdown-menu">' +
	      '<div class="colorpicker-saturation"><i><b></b></i></div>' +
	      '<div class="colorpicker-hue"><i></i></div>' +
	      '<div class="colorpicker-alpha"><i></i></div>' +
	      '<div class="colorpicker-color"><div /></div>' +
	      '<div class="colorpicker-selectors"></div>' +
	      '<div class="colorpicker-custom"><div class="show-color-container"><input id="'+picker+'_show" class="show-color" type="text"/></div>' +
	      '<div class="color-opt-container"><a class="f12" id="'+picker+'_ok" href="javascript:void(0)"><bean:message key="button.ok"/></a>' + 
	      '<a class="f12" id="'+picker+'_cancel" href="javascript:void(0)"><bean:message key="button.cancel"/></a></div</div>' +
	      '</div>';
	}
	
	//上传图片
	function uploadImg(id) {
		seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
			var myForm = document.getElementById(id);
			var myFile = myForm.file;
			if(myFile.files.length == 0) {
				return false;
			}
			var fileName = myFile.files[0].name;
			var beforeName = $(myFile).data('filename');
			if(fileName == beforeName) {
				dialog.alert('<bean:message bundle="sys-attachment" key="sysAttMain.msg.fail.fileName"/>');
				return false;
			}
			var operType='';
			//文件类型校验
			var extFileNames = 'png,jpg,jpeg,gif,ico';
			if(id=='title_image'){
				extFileNames = 'ico';
				operType = "&operType=title";
			}
			if(fileName.indexOf('.') > -1){
				var ext = fileName.substring(fileName.indexOf('.')+1);
				if(extFileNames.indexOf(ext)==-1){
					var tip = '<bean:message bundle="sys-attachment" key="sysAttMain.error.onlySupportFileTypes"/>';
					tip = tip.replace("{0}", extFileNames);
					dialog.alert(tip);
					return false;
				}
			}
			$(myFile).data('filename',fileName);
			myForm.fileName.value = config[id];
			myForm.configKey.value = id;
			myForm.action="${LUI_ContextPath}/sys/profile/sysProfileCuxTemplateAction.do?method=upload&key=${param.key}" + operType;
			myForm.target = 'file_frame';
			myForm.submit();
		});
	}
	//上传图片回调函数
	function uploadCallback(name,id) {
		seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
	       	config[id] = name; 
	       	if(id.substring(0,13) == 'multi_section') {
	       		document.getElementById('preview').contentWindow.addBackgroundCover();
	       	}
            dialog.success('<bean:message bundle="sys-profile" key="sys.profile.design.upload.success"/>');
		});
	}
</script>