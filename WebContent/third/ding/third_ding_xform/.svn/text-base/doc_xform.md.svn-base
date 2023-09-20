# 钉钉审批高级版
##配置
1. 开关：移动办公-钉钉入口-集成组件配置-业务应用配置，F12打开调试窗口，找到页面中的隐藏项：value(attendanceEnabled) 设置为 true
2. 代码判断是否开启钉钉审批高级版: `"true".equals(SysFormDingUtil.getEnableDing())`

## UI
1. 分类树结构：treeview.js（分类设置为treeview_ding.js） 通过判断是否开启高级版审批以及是否在钉钉浏览器中（因为影响到EKP后台中的树结构样式），
修改目录树图片路径为高级版审批的图片路径，页面如果需要在外部浏览器中保持钉钉样式，可通过以下代码实现：
    ```
    //设置为以钉钉浏览器模式打开
    request.setAttribute("dingPcForce", "true");
    ```
2. 样式：通过入口区分的页面，则在对应页面引入样式，如三级页面。公共的页面则通过判断是否开启钉钉及是否在钉钉端内等逻辑，引入样式覆盖的文件实现，如附件、图片和组织架构。
    
3. 附件、图片

    //TODO

4. 流程审批
    查看页头部：third/ding/third_ding_xform/resource/js/lbpmProcess.js
    pc审批记录页面：sys/lbpmservice/support/lbpm_audit_note/dingSuit/lbpmAuditNote_list.jsp
    移动审批记录页面：sys/lbpmservice/mobile/lbpm_audit_note/import/dingSuit/index.jsp
    电子签章页面：km/signature/km_signature_main_ui/dingSuit/kmSignatureMain_showSig.jsp
    
5. 头像
    需求：如果有钉钉头像，则使用钉钉头像,没有就生成带用户名的头像
   	jsp获取钉钉头像：<person:dingHeadimage size="s" var="handlerImg" personId='用户id' contextPath='true'/>
   	地址本中的钉钉头像：后台查询时,先获取是否有上传钉钉头像,有则返回url,没有则调用下面两个接口生成默认头像
    pc地址本默认头像：DingAddressUtil.generateDefaultDingImg()
    移动地址本获取默认头像：iconUtils.createDingIcon()
    
6. 组织架构

    //TODO
    
7. 弹出页面改为模态窗口

    //TODO
    

## 首页、列表页
1. 入口：`km/review/index_ding.jsp`

## 后台管理
1. 入口：`km/review/km_review_ui/dingSuit/moduleindex.jsp?nav=/km/review/tree_ding.jsp&showTopBar=true`
2. 跳转外部浏览器：过滤从PC版钉钉跳转到外部浏览器打开的URL，并使用单点页面跳转(DingPc2OuterBrowserFilter.java)，
页面:`WebContent/third/ding/pc/url_2outer.jsp`

## 三级页面（新建、编辑和查看）
1. 入口（通过高级版开关判断分离页面跳转）
    - 新建、编辑:`km/review/km_review_ui/kmReviewMain_edit.jsp`
    
    - 查看:`km/review/km_review_ui/kmReviewMain_view.jsp`
    
2. 关闭按钮：在查看页面中点击关闭会导致钉钉卡死闪退，需要重写windows.close方法（编辑页面已引入dingtalk-win.js所以可正常关闭）
    ```
    window.close = function(){
        dd.biz.navigation.close();
    };
    ```
3.列表页-新样式（钉钉套件列表查询页样式基于ekp基础上有所优化，引入公共css即可
	
	- css文件:`/sys/ui/extend/theme/default/style/ding_list.css`
	
    
## 调试
1. 三级页面传入参数 ddpage=true 可进入钉钉样式的查看页
2. 钉钉端内调试工具(工具与资源-开发者工具-微应用前端调试工具):`https://ding-doc.dingtalk.com/doc#/kn6zg7/qg4y64`

