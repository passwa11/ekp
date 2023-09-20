<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
<template:replace name="head">
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link rel="stylesheet" href="${LUI_ContextPath}/third/weixin/qa/qa.css"></link>
</template:replace>
<template:replace name="body">
	<div class="qaContainer">
		<h2 class="qaHeader">微信企业号集成常见问题</h2>
		<div class="qaContent">
			<dl>
				<dt>1、部署微信企业号集成模块后，启动EKP报“java.lang.NoSuchMethodError: org.apache.commons.codec.digest.DigestUtils.sha1Hex”错误</dt>
				<dd>
					<p class="qaAnswerPart">
						这是因为EKP下缺少或存在多个版本的commons-codec.jar导致的,解决方法如下:<br> 
						1、在项目下找到/WebContent/WEB-INF/lib目录<br/>
						2、更新<span class="red">commons-codec.jar</span>到1.9.jar的版本，或者从官网下载commons-codec-1.9以上的版本的包。<br/>
						3、如果存在低版本的commons-codec.jar请删除
					</p>
				</dd>
			</dl>
			<dl>
				<dt>2、部署微信企业号集成模块后，EKP报“Could not initialize class me.chanjar.weixin.cp.util.xml.XStreamTransformer”错误</dt>
				<dd>
					<p class="qaAnswerPart">
						解决方法如下:<br/> 
						1、在项目下找到/WebContent/WEB-INF/lib目录<br/>
						2、更新<span class="red">xstream.jar</span>到1.4.7版本，同时删除旧版本(v12需要签名)
					</p>
				</dd>
			</dl>
			<dl>
				<dt>3、微信企业号配置回调模式时，出现“echostr校验错误”的提示</dt>
				<dd>
					<p class="qaAnswerPart">
						这是因为微信使用的加密算法不在标准JDK中，需要替换成JCE无限制权限策略文件,操作如下:<br/>
						1、到官网下载相关文件：<a href="http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html" target="_blank">JDK6对应文件</a>
						&nbsp;&nbsp;&nbsp;<a href="http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html" target="_blank">JDK7对应文件</a><br/>
						2、解压并将对应jar包拷贝到JDK的\jre\lib\security目录下<br/>
						3、重启服务器
					</p>
				</dd>
			</dl>
			
			<dl>
				<dt>4、微信企业号在进行消息推送等功能时出现“60011  管理组权限不足，（user/department/agent）无权限”的提示</dt>
				<dd>
					<p class="qaAnswerPart">
						此问题是因为企业号应用没有赋予管理权限,解决方法是到微信企业号后台通过设置->权利管理赋予相关权限。
					</p>
				</dd>
			</dl>
			
			<dl>
				<dt>5、从微信进入EKP应用时出现EKP的登录界</dt>
				<dd>
					<p class="qaAnswerPart">
						企业号里点击应用弹出登陆框时，说明是单点登录不成功。需要进行排查:<br/>
						1、查看是否所有人打开应用都是这种情况。如果是部分人，那可能是人员账号问题，检查人员登录名是否正确。如果是所有人，应该是配置问题，进行第二步。<br/>
						2、如果是消息型应用，查看菜单地址，后缀是否有oauth=wx2ekp的参数。如果是主页型应用，查看应用地址，后缀是否有oauth=wx2ekp的参数。如果有后缀，进行第三步。<br/>
						3、查看ekp的后台打印日志是否有报错信息。根据报错信息进行排查。
					</p>
				</dd>
			</dl>
			
			<dl>
				<dt>6、微信企业号全局返回码</dt>
				<dd>
					<p class="qaAnswerPart">
						在与微信企业号对接过程中不免会出现一些错误，所幸大部分操作均会得到一个全局返回码(来自钉钉),这里提供一份<a href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E" target="_blank">微信全局返回码</a>以便项目排查原因。
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