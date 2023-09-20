<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no" showQrcode="false">
  <template:replace name="head">
    <style> 
      @font-face {
        font-family: 'FontMui';
        src:url("<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.woff");
      }
    </style>
    <link rel="stylesheet" type="text/css"
      href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_menu/css/iconList.css"></link>
  </template:replace>
  <template:replace name="title">
    <c:choose>
      <c:when test="${ sysMportalMenuForm.method_GET == 'add' }">
        新建 - 移动门户菜单
      </c:when>
      <c:otherwise>
        <c:out value="${sysMportalMenuForm.docSubject}"/> - 编辑
      </c:otherwise>
    </c:choose>
  </template:replace>
  <template:replace name="path">
    <ui:menu layout="sys.ui.menu.nav">
      <ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp"
        icon="lui_icon_s_home" />
      <ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item1') }" />
      <ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item2') }" />
      <ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item3') }" />
      <ui:menu-item text="${lfn:message('sys-mportal:sysMportal.moudle.fast') }" />
    </ui:menu>
  </template:replace>
  <template:replace name="toolbar">
    <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"
      var-navwidth="95%">
      <c:choose>
        <c:when test="${ sysMportalMenuForm.method_GET == 'edit' }">
          <ui:button text="${ lfn:message('button.update') }"
            onclick="Com_Submit(document.sysMportalMenuForm, 'update');"></ui:button>
        </c:when>
        <c:when test="${ sysMportalMenuForm.method_GET == 'add' }">
          <ui:button text="${ lfn:message('button.save') }"
            onclick="Com_Submit(document.sysMportalMenuForm, 'save');"></ui:button>
        </c:when>
      </c:choose>
      <ui:button text="${ lfn:message('button.close') }"
        onclick="Com_CloseWindow();"></ui:button>
    </ui:toolbar>
  </template:replace>

  <template:replace name="content">
    <html:form action="/sys/mportal/sys_mportal_menu/sysMportalMenu.do">

      <div class="lui_content_form_frame" style="padding: 20px 0px">
        <table class="tb_normal" width=95%>
          <tr>
            <td class="td_normal_title" width=15%><bean:message key="sysMportalMenu.docSubject" bundle="sys-mportal"/></td>
            <td width="85%" colspan="3"><xform:text property="docSubject" style="width:85%" /></td>
          </tr>
          <tr>
            <td colspan="4">
              <table  class="tb_normal" width="100%" id="TABLE_DocList" style="TABLE-LAYOUT: fixed;WORD-BREAK: break-all;">
                <tr>
                  <td width="5%" KMSS_IsRowIndex="1" class="td_normal_title"><bean:message key="page.serial" /></td>
                  <td width="50px" class="td_normal_title" align="center"><bean:message key="sysMportalMenuItem.fdIcon" bundle="sys-mportal"/></td>
                  <td width="25%" class="td_normal_title"><bean:message key="sysMportalMenuItem.fdName" bundle="sys-mportal"/></td>
                  <td width="50%" class="td_normal_title"><bean:message key="sysMportalMenuItem.fdUrl" bundle="sys-mportal"/></td>
                  <td width="110px;" align="center" class="td_normal_title">
                    <a href="javascript:void(0);" onclick="selectModule();" class="com_btn_link ">
                      <bean:message key="sysPersonSysNavLink.fromSys" bundle="sys-person"/>
                    </a>&nbsp
                    <a href="javascript:void(0);" onclick="selectCustom();" class="com_btn_link">
                      <bean:message key="sysPersonSysNavLink.fromInput" bundle="sys-person"/>
                    </a>
                  </td>
                </tr>
                
                <!--基准行-->
                <tr KMSS_IsReferRow="1" style="display:none">
                  <td width="5%" KMSS_IsRowIndex="1"></td>
                  <td width="50px" align="center" valign="middle" class="icon">
                    <div class="mui mui-approval" claz="mui-approval"></div>
                    <input name="fdSysMportalMenuItemForms[!{index}].fdIconType" class="inputsgl" type="hidden" value="2" />
                    <input name="fdSysMportalMenuItemForms[!{index}].fdIcon" class="inputsgl" type="hidden" value="mui-approval" />
                  </td>
                  <td width="25%">
                    <input type="hidden" name="fdSysMportalMenuItemForms[!{index}].fdId"/>
                    <input name="fdSysMportalMenuItemForms[!{index}].fdName" validate="required" class="inputsgl" style="width: 90%" />
                    <span class="txtstrong">*</span>
                  </td>
                  <td width="50%">
                  	<xform:text property="fdSysMportalMenuItemForms[!{index}].fdUrl" showStatus="edit" required="true" style="width:95%;" validators="maxLength(1000)"/>
                  </td>
                  <td align="center" width="85px;">
                    <a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0"  title="<bean:message key="button.delete"/>"/></a>
                    <a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0"  title="<bean:message key="button.moveup"/>"/></a>
                    <a href="javascript:void(0);" onclick="DocList_MoveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0"  title="<bean:message key="button.movedown"/>"/></a>
                  </td>
                </tr>
                
                <!--内容行-->
                <c:if test="${sysMportalMenuForm.method_GET=='edit'}">
                  <c:forEach items="${sysMportalMenuForm.fdSysMportalMenuItemForms}" var="itemForm" varStatus="vstatus">
                    <tr KMSS_IsContentRow="1">
                      <td width="10%" KMSS_IsRowIndex="1" id="KMSS_IsRowIndex_Edit">${vstatus.index+1}</td>
                      <td width="50px"  align="center" valign="middle" class="${itemForm.fdIconType=='1'?'imgContainer':'icon'}">
                        <c:choose>
                          <c:when test="${ not empty itemForm.fdIcon && fn:indexOf(itemForm.fdIcon, 'mui')<0 }">
                            <c:choose>
                            <c:when test="${itemForm.fdIconType eq '4'}">
                              <div class="mui imgBox" style="background: url('${LUI_ContextPath}${itemForm.fdIcon}') no-repeat center;background-size: contain">
                              </div>
                            </c:when>
                            <c:otherwise>
                              <div class="mui" claz="${itemForm.fdIcon}">
                                  ${itemForm.fdIcon}
                              </div>
                            </c:otherwise>
                            </c:choose>
                          </c:when>
                          <c:otherwise>
                              <div class="mui ${itemForm.fdIcon} ${itemForm.fdIconType=='1'?'imgBox':''}" claz="${itemForm.fdIcon}">
                              </div>
                          </c:otherwise>
                        </c:choose>
                        <input name="fdSysMportalMenuItemForms[${vstatus.index}].fdIconType" type="hidden" value="${itemForm.fdIconType}"/>
                        <input name="fdSysMportalMenuItemForms[${vstatus.index}].fdIcon" type="hidden" value="${itemForm.fdIcon}"/>
                      </td>
                      <td width="25%">
                        <input type="hidden" value="${itemForm.fdId}" name="fdSysMportalMenuItemForms[${vstatus.index}].fdId"  />
                        <input name="fdSysMportalMenuItemForms[${vstatus.index}].fdName" validate="required" class="inputsgl" style="width: 90%" value="${itemForm.fdName}"/>
                        <span class="txtstrong">*</span>
                      </td>
                      <td width="50%">
                        <xform:text property="fdSysMportalMenuItemForms[${vstatus.index}].fdUrl" showStatus="edit" required="true" style="width:95%;" validators="maxLength(1000)"/>
                      </td>
                      <td align="center" width="85px;">
                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" title="<bean:message key="button.delete"/>" /></a>
                        <a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0" title="<bean:message key="button.moveup"/>" /></a>
                        <a href="javascript:void(0);" onclick="DocList_MoveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0" title="<bean:message key="button.movedown"/>" /></a>
                      </td>
                    </tr>
                  </c:forEach>
                </c:if>
              </table>
            </td>
          </tr>
          <tr>
            <td class="td_normal_title" width=15%>
              <bean:message bundle="sys-mportal" key="sysMportal.msg.editors" />
            </td>
            <td width="85%" colspan="3">
              <xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds" propertyName="fdEditorNames" style="width:100%;height:90px;" ></xform:address>
            </td>
          </tr>
          <tr>
            <td class="td_normal_title" width=15%>
                  <bean:message bundle="sys-mportal" key="sysMportalMenu.docCreator" />
            </td>
            <td width="35%" >
                  <xform:text property="docCreatorName" showStatus="view" />
            </td>
            <td class="td_normal_title" width=15%>
                  <bean:message bundle="sys-mportal" key="sysMportalMenu.docCreateTime" />
            </td>
            <td width="35%" >
                  <xform:datetime property="docCreateTime" showStatus="view" />
            </td>
          </tr>
        </table>
      </div>
      <html:hidden property="fdId" />
      <html:hidden property="method_GET" />
      <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
      
      <style>
        .icon{
          background-color: #1d9d74;
          color: #fff;
        }
        .icon>div{
          text-align: center;
          cursor: pointer;
        }
        .icon .mui{
          font-size: 36px;
        }
      </style>
      <script>
        Com_IncludeFile("doclist.js");
      
        $KMSSValidation();
        
        seajs.use([ 'lui/jquery', 'lui/dialog' ],function(jquery, dialog) {
                  $('#TABLE_DocList').on('click', function(evt) {
                    var $target = $(evt.target);
                    if ($target.hasClass('mui')) {
                      icon($target);
                    }
                  });

                  function icon($target) {
                    dialog.iframe("/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon", "<bean:message key='sysMportalCard.select.icons' bundle='sys-mportal'/>",   function(returnData) {         
                      if (!returnData)
                        return;
                      else {
                    	var $targetParentNode = $($target[0].parentNode);
                    	var iconType = returnData.iconType; // 1、图片图标      2、字体图标     3、文字      4、素材库图标
                        var type = returnData.type; //素材库图标
                        var url = returnData.url;

                        var claz1 = $target.attr('claz');
                    	var claz2 = (iconType==1||iconType==2) ? returnData.className : "";
                    	var text = (iconType==3) ? returnData.text : "";
                        $target.text('');
                        $target.removeClass(claz1);
                        $targetParentNode.removeClass('icon');
                        $targetParentNode.removeClass('imgContainer');
                        $target.addClass('mui');
                        //素材库图标存在
                        console.log(returnData);
                        if(type == 2){
                          iconType=4;
                        }
                        if(iconType != 4){
                          $target.attr("style","");
                        }
                        if(iconType == 1){  // 图片图标
                        	
                        	$targetParentNode.addClass('imgContainer');
                        	$target.addClass('imgBox');  
                        	$target.addClass(claz2);
                        	$target.attr('claz', claz2);
                        	
                        } else if(iconType == 4){ // 素材库
                            $targetParentNode.addClass('imgContainer');
                            $target.addClass('imgBox');
                            var tUrl = url;
                            if(tUrl.indexOf("/") == 0){
                              tUrl = tUrl.substring(1);
                            }
                            tUrl = Com_Parameter.ContextPath + tUrl;
                            $target.css({
                              "background": "url('"+tUrl+"') no-repeat center",
                              "background-size": "contain"
                            })
                        }else {
                        	$targetParentNode.removeClass('imgContainer');  
                        	$targetParentNode.addClass('icon');  
                        	$target.removeClass('imgBox');  

                        	if(iconType == 2){  // 字体图标 
	                        	
	                        	$target.addClass(claz2);
	                        	$target.attr('claz', 'mui ' + claz2);
	                        	
	                        }else if(iconType == 3){  // 文字
	                        	
	                        	$target.text(text);
	                        	$target.attr('claz', text);
	                        	
	                        }
                        }
                        
                        // 赋值隐藏hidden
                        $target.parent().find("input[type='hidden']").eq(0).val(iconType);  // 图标类型
                        $target.parent().find("input[type='hidden']").eq(1).val((iconType==3)?text:(iconType==4?url:claz2)); // 图标
                        
                      }
                    }, 
                    {
                      width : 600,
                      height : 550
                    });
                  }
                });
        
        // 自定义链接
        function selectCustom() {
          DocList_AddRow(
              $('#TABLE_DocList')[0]);
        }

        // 模块选择
        function selectModule() { 
            seajs.use([ 'lui/dialog', 'lui/jquery' ],function(dialog) {
                    dialog.iframe("/sys/mportal/sys_mportal_menu/sysMportalMenu_dialog.jsp","<bean:message key='sysMportal.moudle.urlCheck' bundle='sys-mportal'/>",function(val) {
                              if (!val) {
                                return;
                              } else {
                                for (var i = 0; i < val.length; i ++) {
                                  var _val = val[i];
                                  var rowData = {
                                      "fdSysMportalMenuItemForms[!{index}].fdName" : _val.name,
                                      "fdSysMportalMenuItemForms[!{index}].fdUrl" : _val.link
                                  };
                                  DocList_AddRow('TABLE_DocList', null, rowData);
                                }
                              }
                            }, {
                              width : 600,
                              height : 550
                    });
            });
        }
      </script>
      
  <script language="javascript" type="text/javascript">   
	//禁用Enter键表单自动提交         
	 document.onkeydown = function(event) 
	 {             
		 var target, code, tag;             
		 if (!event) 
		 {                  
			 event = window.event; //针对ie浏览器                 
			 target = event.srcElement;                 
			 code = event.keyCode;                 
			 if (code == 13)
			 {                    
				 tag = target.tagName;                      
				 if (tag == "TEXTAREA")  //其它情况，如Enter换行可行
				 { 
					return true; 
				 }                     
				 else  //禁止提交
				 { 
					return false;
				 }                 
			 }             
		 }             
		 else 
		 {                  
			 target = event.target; //针对遵循w3c标准的浏览器，如Firefox                 
			 code = event.keyCode;                  
			 if (code == 13) 
			 {                     
					tag = target.tagName;                    
					if (tag == "INPUT") //禁止提交
				 { 
					return false; 
				 }                    
				 else  //其它情况，如Enter换行可行
				 {
					return true; 
				 }                 
			 }             
		 }          
 }; 
 </script>
      
      <html:hidden property="docCreatorId"/>
      <html:hidden property="docCreateTime"/>
    </html:form>

  </template:replace>
</template:include>