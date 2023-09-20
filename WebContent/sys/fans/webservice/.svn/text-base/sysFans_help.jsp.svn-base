<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" >
	<template:replace name="title">
		用户关注信息对外接口说明
	</template:replace>
	<template:replace name="body"> 
		<script>
			seajs.use(["theme!form"]);
		</script>
		<style>
			.lui_fans_help_ul {
				list-style:none;padding:8px;font-size:14px;
			}
			.lui_fans_help_ul li {
				margin:8px 0;
			}
		</style>
		<div style="width:90%;margin:0 auto;">
			<div style="padding:10px;font-size:20px;text-align: center;">
				用户关注信息对外接口说明
			</div>
			<ui:accordionpanel>
				<ui:content title="接口说明">
					<ul class="lui_fans_help_ul">
						<li>
							1、boolean isFollowPerson(String userId,String targetUserId)
							<table class="tb_normal" style="width:100%">
								<tr>
									<td width="15%">说明：</td>
									<td width="85%">检查是否已关注某用户，已关注返回true，否则为false</td>
								</tr>
								<tr>
									<td width="15%">参数：</td>
									<td width="85%">
										userId为关注者，不可为空<br>
										targetUserId为被关注者，不可为空
									</td>
								</tr>
							</table>
						</li>
						<li>
							2、boolean followPerson(String userId,String targetUserId)
							<table class="tb_normal" style="width:100%">
								<tr>
									<td width="15%">说明：</td>
									<td width="85%">关注某用户,返回关注结果，成功为true</td>
								</tr>
								<tr>
									<td width="15%">参数：</td>
									<td width="85%">
										userId为关注者，不可为空<br>
										targetUserId为被关注者，不可为空
									</td>
								</tr>
							</table>
						</li>
					</ul>
				</ui:content>
				<ui:content title="样例代码" expand="false">
					<div>
						<pre>
							public static void main(String[] args) throws Exception {
								WebServiceConfig cfg = WebServiceConfig.getInstance();
						
								Object service = callService(cfg.getAddress(), cfg.getServiceClass());
								// 请在此处添加业务代码
								ISysFansWebService fansService = (ISysFansWebService)service;
								//关注者Id
								String userId = "111";
								//被关注者Id
								String targetId = "222";
								Boolean  isFollowed = fansService.isFollowPerson(userId, targetId);
						
								Boolean success =  fansService.followPerson(userId, targetId);

							}
						</pre>
					</div>
				</ui:content>
			</ui:accordionpanel>
		</div>
	</template:replace>
</template:include>