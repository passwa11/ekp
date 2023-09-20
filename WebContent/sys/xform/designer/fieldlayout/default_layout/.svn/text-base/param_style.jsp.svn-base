<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<script>Com_IncludeFile('jquery.colorpicker.js','../sys/xform/designer/globalStyle/');</script>
<script>
FieldLayout_lang = {
		
				configFontSelect : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontSelect" />',
	configFontSongTi : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontSongTi" />',
	configFontXinSongTi : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontXinSongTi" />',
	configFontKaiTi : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontKaiTi" />',
	configFontHeiTi : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontHeiTi" />',
	configFontYouYuan : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontYouYuan" />',
	configFontYaHei : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontYaHei" />',
	configFontZhongsong : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontZhongsong" />',
	configFontFangSongGB2312 : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontFangSongGB2312" />',
	configFontFZXBSJT : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontFZXBSJT" />',
	configFontCourierNew : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontCourierNew" />',
	configFontTimesNewRoman : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontTimesNewRoman" />',
	configFontTahoma : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontTahoma" />',
	configFontArial : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontArial" />',
	configFontVerdana : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configFontVerdana" />',
	
		configSizeSelect : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configSizeSelect" />',
	configSizePx : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.configSizePx" />',
	
		controlTextLabelInfoName: '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelInfoName" />',
	controlTextLabelAttrContent : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrContent" />',
	controlTextLabelAttrFont : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrFont" />',
	controlTextLabelAttrSize : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrSize" />',
	controlTextLabelAttrColor : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrColor" />',
	controlTextLabelAttrEffect : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrEffect" />',
	controlTextLabelAttrBold : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrBold" />',
	controlTextLabelAttrItalic : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrItalic" />',
	controlTextLabelAttrUnderline : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrUnderline" />',
	controlTextLabelAttrNormal : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrNormal" />',
	
	controlTextLabelAttrDefault : '<kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault" />'
};
FieldLayout_Config={};
FieldLayout_Config.font = {
		style : [
			{text:FieldLayout_lang.configFontSelect, value:''},
			{text:FieldLayout_lang.configFontSongTi, value:FieldLayout_lang.configFontSongTi, style:'font-family:'+FieldLayout_lang.configFontSongTi},
			{text:FieldLayout_lang.configFontXinSongTi, value:FieldLayout_lang.configFontXinSongTi, style: 'font-family:'+FieldLayout_lang.configFontXinSongTi},
			{text:FieldLayout_lang.configFontKaiTi, value:FieldLayout_lang.configFontKaiTi, style: 'font-family:'+FieldLayout_lang.configFontKaiTi},
			{text:FieldLayout_lang.configFontHeiTi, value:FieldLayout_lang.configFontHeiTi, style: 'font-family:'+FieldLayout_lang.configFontHeiTi},
			{text:FieldLayout_lang.configFontYouYuan, value:FieldLayout_lang.configFontYouYuan, style: 'font-family:'+FieldLayout_lang.configFontYouYuan},
			{text:FieldLayout_lang.configFontYaHei, value:FieldLayout_lang.configFontYaHei, style: 'font-family:'+FieldLayout_lang.configFontYaHei},
			{text:FieldLayout_lang.configFontZhongsong, value:FieldLayout_lang.configFontZhongsong, style: 'font-family:'+FieldLayout_lang.configFontZhongsong},
			{text:FieldLayout_lang.configFontFangSongGB2312, value:FieldLayout_lang.configFontFangSongGB2312, style: 'font-family:'+FieldLayout_lang.configFontFangSongGB2312},
			{text:FieldLayout_lang.configFontFZXBSJT, value:FieldLayout_lang.configFontFZXBSJT, style: 'font-family:'+FieldLayout_lang.configFontFZXBSJT},
			{text:FieldLayout_lang.configFontCourierNew, value:FieldLayout_lang.configFontCourierNew, style: 'font-family:\"'+FieldLayout_lang.configFontCourierNew+'\"'},
			{text:FieldLayout_lang.configFontTimesNewRoman, value:FieldLayout_lang.configFontTimesNewRoman, style: 'font-family:\"'+FieldLayout_lang.configFontTimesNewRoman+'\"'},
			{text:FieldLayout_lang.configFontTahoma, value:FieldLayout_lang.configFontTahoma, style: 'font-family:\"'+FieldLayout_lang.configFontTahoma+'\"'},
			{text:FieldLayout_lang.configFontArial, value:FieldLayout_lang.configFontArial, style: 'font-family:\"'+FieldLayout_lang.configFontArial+'\"'},
			{text:FieldLayout_lang.configFontVerdana, value:FieldLayout_lang.configFontVerdana, style: 'font-family:\"'+FieldLayout_lang.configFontVerdana+'\"'}
		],
		size : (function() {
			var ops = [];
			ops.push({text: FieldLayout_lang.configSizeSelect, value:''});
			for (var i = 9; i < 26; i ++) {
				ops.push({text: i + FieldLayout_lang.configSizePx, value: i + 'px'});
			}
			//新加入字号
	        var sizes = [26,28,30,32,34,36,40,48,56,72];
	        for(var j in sizes){
	    		ops.push({text: sizes[j] + FieldLayout_lang.configSizePx, value: sizes[j] + 'px'});
	        }
			return ops;
		})(),
		b:[{text:FieldLayout_lang.controlTextLabelAttrDefault,value:''},{text:FieldLayout_lang.controlTextLabelAttrBold,value:'bold',style:'font-weight:bold'},{text:FieldLayout_lang.controlTextLabelAttrNormal,value:'normal',style:'font-weight:normal'}],
		i:[{text:FieldLayout_lang.controlTextLabelAttrDefault,value:''},{text:FieldLayout_lang.controlTextLabelAttrItalic,value:'italic',style:'font-style:italic'},{text:FieldLayout_lang.controlTextLabelAttrNormal,value:'normal',style:'font-style:normal'}],
		underline:[{text:FieldLayout_lang.controlTextLabelAttrDefault,value:''},{text:FieldLayout_lang.controlTextLabelAttrUnderline,value:'underline',style:'text-decoration:underline'},{text:FieldLayout_lang.controlTextLabelAttrNormal,value:'none',style:'text-decoration:none'}]
};

function FieldLayout_loadFontStyle(ary,val){
	 var html="";
	   for (var i = 0; i < ary.length; i ++) {
			var opt = ary[i];
			html += "<option value='" + opt.value;
			if (opt.style) {
				html += "' style='" + opt.style;
			}
			//if (opt.value == value) {
			//	html += "' selected='selected";
			//}
			html += "'>" + opt.text + "</option>";
	   }
	   
	 return html;
}
</script>
<table class="tb_normal"  width="100%">
<tr>
				<td colspan="2" style="text-align: center;font-weight: bold;"><kmss:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_fontStyle" /></td>
			</tr>
<tr>
	<td class="td_normal_title" width=40%><kmss:message bundle="sys-xform-base" key="Designer_Lang.buttonsFontColor" /></td>
	<td>
		<input name="control_fontColor" class="inputsgl" style="width:120px" storage="true" />
		<script>
		   $(function(){
			   $("input[name='control_fontColor']").colorpicker({
				    fillcolor:true,
				    success:function(objs,val){
				    	$(objs[0]).css("color","");
				    },
				    reset:function(obj){
				    	$(obj).val("");
				    }
				});
		   });
		</script>
	</td>
</tr>

<tr>
	<td class="td_normal_title" width=40%><kmss:message bundle="sys-xform-base" key="Designer_Lang.buttonsFontStyle" /></td>
	<td>
		<select name="control_fontFamily" storage='true'></select>
		<script>
		   $(function(){
			   $("select[name='control_fontFamily']").html(FieldLayout_loadFontStyle(FieldLayout_Config.font.style));
		   });
		</script>
	</td>
</tr>
<tr>
	<td class="td_normal_title" width=40%><kmss:message bundle="sys-xform-base" key="Designer_Lang.buttonsFontSize" /></td>
	<td>
		<select name="control_fontSize" storage='true'></select>
		<script>
		   $(function(){
			   $("select[name='control_fontSize']").html(FieldLayout_loadFontStyle(FieldLayout_Config.font.size));
		   });
		</script>
	</td>
</tr>
<tr>
	<td class="td_normal_title" width=40%><kmss:message bundle="sys-xform-base" key="Designer_Lang.buttonsBold" /></td>
	<td>
		<label><input type='radio' name="control_fontB" value="" storage="true" checked="checked"><kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault" /></input></label>
		<label><input type='radio'  name="control_fontB" value="bold" storage="true"><kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrBold" /></input></label>
	</td>
</tr>
<tr>
	<td class="td_normal_title" width=40%><kmss:message bundle="sys-xform-base" key="Designer_Lang.buttonsItalic" /></td>
	<td>
		<label><input type='radio' name="control_fontI" value="" storage="true" checked="checked"><kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault" /></input></label>
		<label><input type='radio'  name="control_fontI" value="italic" storage="true"><kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrItalic" /></input></label>
	</td>
</tr>
<tr>
	<td class="td_normal_title" width=40%><kmss:message bundle="sys-xform-base" key="Designer_Lang.buttonsUnderline" /></td>
	<td>
		<label><input type='radio' name="control_fontU" value="" storage="true" checked="checked"><kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrDefault" /></input></label>
		<label><input type='radio'  name="control_fontU" value="underline" storage="true"><kmss:message bundle="sys-xform-base" key="Designer_Lang.controlTextLabelAttrUnderline" /></input></label>
	</td>
</tr>



</table>