<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
<template:replace name="head">
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link rel="stylesheet" href="${LUI_ContextPath}/third/ding/qa/qa.css"></link>
</template:replace>
<template:replace name="body">
	<div class="qaContainer">
		<h2 class="qaHeader">钉钉集成常见问题</h2>
		<div class="qaContent">
			<dl>
				<dt>1、部署钉钉集成模块后，启动EKP报“java.lang.NoClassDefFoundError”错误</dt>
				<dd>
					<p class="qaAnswerPart">
						这是因为EKP下存在两个版本的httpclient.jar导致Jar包冲突,解决方法如下:<br> 
						1、在项目下找到/WebContent/WEB-INF/lib目录<br/>
						2、删除版本较低的<span class="red">httpclient.jar</span>和<span class="red">httpcore.jar</span>
					</p>
				</dd>
			</dl>
		
			<dl>
				<dt>2、从EKP同步组织机构到钉钉时出现形如“Caused by:java.net.URISyntaxException: Illegal character in query at index”的错误</dt>
				<dd>
					<p class="qaAnswerPart">
						这是因为EKP在与第三方系统(如AD等)对接组织架构过程中登录名产生空格导致的，临时解决方案如下:<br/>
						1、如果应用数据库是sqlServer,执行如下SQL:<br/>
						<span class="red tab1">update oms_relation_model set fd_app_pk_id = ltrim(fd_app_pk_id);</span><br/>
						<span class="red tab1">update oms_relation_model set fd_app_pk_id = rtrim(fd_app_pk_id);</span><br/>
						2、如果应用数据库不是sqlServer,执行SQL: <br/>
						<span class="red tab1">update oms_relation_model set fd_app_pk_id = trim(fd_app_pk_id);</span>
					</p>
				</dd>
			</dl>
			
			<dl>
				<dt>3、从EKP同步组织机构到钉钉时出现API调用超时</dt>
				<dd>
					<p class="qaAnswerPart">
						如果有大量的部门或人员在第一次全量同步时，由于有大量的数据一次同步，有可能会出现在调用钉钉接口时出现写某一条数据时API调用超时，出现这种情况时也只会对当前超时数据同步有问题，其它同步的数据应该是正常的。这需要从后台同步日志分析是哪一条数据同步有问题后，在EKP的组织机构中对该条数据进行编辑提交后，再次同步即可，因为以后是增量同步。
					</p>
				</dd>
			</dl>
			
			<dl>
				<dt>4、部署钉钉集成模块前，钉钉与EKP均存在独立的组织架构,希望EKP与钉钉能做映射关系,以便单点、消息推送等功能能正常使用</dt>
				<dd>
					<p class="qaAnswerPart">
						原则上组织架构的维护权只能在一方,即初始化状态下只有一方存在组织架构，另一方通过接入接出操作获得组织架构。不过,为了解决部分项目在这方面的困扰,EKP提供了一个初始化映射操作让两边组织架构产生关联，当然你的环境需要符合如下要求:<br/>
						1、EKP与钉钉的组织架构保持一致<br/>
						2、组织架构避免同名部门<br/><br/>
						如果你的环境满足上述要求，则进行如下操作即可:<br/>
						1、在地址栏打开如下链接: <span class="tab1">!{项目域名}/third/ding/dingOms.do?method=generateMapping</span><br/>
						2、等待...页面出现success返回码前不要关闭窗口	
					</p>
				</dd>
			</dl>
		
			<dl>
				<dt>5、从钉钉进入EKP应用时出现EKP的登录界面</dt>
				<dd>
					<p class="qaAnswerPart">
						这是由于EKP从钉钉进行开放授权时不能获取到用户信息，即不能SSO到EKP，可能原因：<br> 
						1、钉钉应用的链接配置时，没有加<span class="red">oauth=ekp</span>的后缀参数；<br> 
						2、这可能是由于网络慢导致不能获取用户信息。这种情况很少见，但偶尔会出现，如果出现了只要再一次从点钉钉进入EKP模块就可以了； <br> 
						3、如果只是部分人员出现登陆的页面，那要查看钉钉通讯录里人员的userID，是否为ekp的登录名，如果不是登录名，则无法sso登陆到ekp。操作方法就是把钉钉通讯录里此人员的账号删掉，在ekp中先找到人员信息编辑提交一下，再执行同步的定时任务，将人员重新同步到钉钉即可。 
					</p>
				</dd>
			</dl>
			
			<dl>
				<dt>6、钉钉返回码</dt>
				<dd>
					<p class="qaAnswerPart">
						在与钉钉对接过程中不免会出现一些错误，所幸大部分操作均会得到一个全局返回码(来自钉钉),这里提供一份<a href="https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.i4XMMs&treeId=172&articleId=104965&docType=1" target="_blank">全局返回码说明</a>以便项目排查原因。
					</p>
				</dd>
			</dl>
			
		</div>
	</div>
	<script type="text/javascript">
		seajs.use(['lui/jquery'],function($){
			$('dt').on('click',function(){
				var _dd = $(this).next();
				if(_dd.is(':visible')){
					return;
				}
				$('dd').slideUp();
				_dd.slideDown();
			});
		});
	</script>
</template:replace>
</template:include>