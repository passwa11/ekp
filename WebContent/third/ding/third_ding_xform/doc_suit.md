# 套件定义

``` java
WebContent/WEB-INF/KmssConfig/third/ding/ThirdDingXFormTemplate.json
```

# 套件目录文件含义

1. template.html : 表单源码
2. service.js : 套件在运行时的逻辑处理 JS
3. pcPreview.png : 在选择模板时的预览图
4. mobile目录 : 套件在移动端运行时对应的逻辑文件存放目录

# 套件通用的JS

1. common.js : 常用方法的JS
2. validatorUtil.js : 常用校验的JS方法，比如【开始时间】和【结束时间】的比较校验
3. ding.css : 钉钉样式，引入该文件之后，表单控件的样式会偏向钉钉方

# 模板配置注意事项

**建议模板配置时有疑问的，参考【请假套件】**

## PC端

1. 在表单模板里面引入钉钉套件之后，是不能够编辑钉钉套件的，这就需要在套件上面添加一层遮罩蒙层以让用户不能操作套件内的控件，通过直接在表单源码上添加以下代码可以实现遮罩效果

   ```html
   <div formdesign="landray" fd_type="mask" class="xform_mask" fd_values="{name:&quot;!{套件的key}&quot;}">
   	<div class="lui_pop_mask"></div>
   	<div class="content">
           <!-- 此处为正常逻辑的表单源码 -->
        </div>
   </div>
   ```

   **注意：代码中"!{套件的key}"需要替换**

2. 如果套件里面含有【结束时间】字段，由于该字段和系统字段冲突，需求上也不允许改名，这种情况可以通过修改源码在“结束时间”前面增加一个空格来避免冲突

3. 【钉钉明细表】仅开发做钉钉套件时方便使用，使用时需要把isShow设置为true

4. 需要右侧弹窗可引入`/third/ding/third_ding_xform/resource/js/ding_right.js`，（例如规则框和明细框，目前其弹窗样式仅限pc端使用，移动端待设计）

## 移动端

1. 移动表单上需要增加分隔行的，可以在单元格上增加【JSP片段】，添加以下代码

```html
<div class="ding_mobile_divide_flag"></div>
```

然后在套件的逻辑代码的初始化方法中调用以下方法，它会默认在标识上面增加一个空的TR行

```js
commonUtil.addDivdieTr();
```

2. 移动端$form组件和dojo组件的区别，$form组件是我们公司基于原生HTML之上在移动端封装的一套框架，类似于jQuery，内置常用的一些方法，比如设置值、设置必填、设置显示隐藏等，具体可以参考form.js，$form组件里面含有对应的dojo组件
3. 区别pc和移动的代码块

```jsp
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%int type = MobileUtil.getClientType(request);
	if(type > -1){%>
		<!-- 移动端代码写这 -->
	<%}else{%>
		<!-- pc端代码写这 -->
	<%}%>

```


