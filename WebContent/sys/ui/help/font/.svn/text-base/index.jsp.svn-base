<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title>PC-矢量图标列表</title>
		<link rel="stylesheet" href="css/demo.css" />
		<!-- 和主题包同步 -->
		<link rel="stylesheet" href="../../extend/theme/default/style/common.css" />
		<link rel="stylesheet" href="../../extend/theme/default/style/iconfont.css" />
		<link rel="shortcut icon" href="${LUI_ContextPath}/resource/customization/images/login_single_random/favicon.ico">
		<link rel="bookmark icon" href="${LUI_ContextPath}/resource/customization/images/login_single_random/favicon.ico">
		<script src="js/vue.js"></script>
		<script src="js/axios.js"></script>
		<script src="js/jquery-1.11.0.js"></script>

	</head>

	<body>
		<!--
			图标分为3类，
			1.前缀lui_iconfont_navleft_：应用在二级页面左侧导航
			2.前缀lui_iconfont_nav：应用在后台配置 系统导航。新页眉的应用以及工作台左侧导航 
			3.前缀lui_iconfont_profile:后台配置应用卡片
		-->
		<div id="app">
			<!--  Starts -->
			<div class="profile-iconList-wrap" id="app">
				<div class="profile-iconList-tabWrap">
					<div class="iconList-tabHeader">
						<ul>
							<li v-for="(item,index) in iconTab" :class="{'current':index===nowIndex}" @click="toggleTab(index)"><span>{{item.typeName}}</span></li>
						</ul>
					</div>
					<div class="iconList-tabContent">
						<div class="iconList-tabItemWrap" v-show="index===nowIndex" v-for="(item,index) in iconTab">
							<!-- 图标列表 Starts -->
							<!-- 固定在头部 Starts -->
							<div class="fixBar">
								<div class="opt">
									<!-- 操作条 类名是否显示全部 Starts --><label class="weui_switch">
									<span class="weui_switch_bd">
										<input type="checkbox" :checked="isShowClassName" v-model="isShowClassName">
									<span></span><small></small></span>
									<span name="switchText">{{isShowClassName?"显示简短类名":"显示全部类名"}}</span></label>
									<!-- 操作条 类名是否显示全部 Ends -->
									<!-- 操作条 图标名称是否显示全路径 Starts --><label class="weui_switch" style="display:none">
									<span class="weui_switch_bd">
										<input type="checkbox" :checked="isShowModuleName" v-model="isShowModuleName">
									<span></span><small></small></span><span name="switchText">{{isShowModuleName?"显示简短路径名称":"显示全部路径名称"}}</span></label></span>
									<!-- 操作条 图标名称是否显示全路径 Ends -->
									<span class="tips">前缀<i>{{iconTab[nowIndex].data.prefix}}</i></span>
								</div>
								<div class="search"><input type="text" placeholder="图标搜索" /><i class="search-icon icon-ok"></i><i class="search-icon icon-cancel"></i></div>
								<span class="_tips">点击类名即可复制</span>
							</div>
							<!-- 固定在头部 Ends -->
							<!-- 标题 Starts -->
							<div class="tab-title"> <a href="" target="_blank" class="title-span">{{iconTab[nowIndex].data.title}} <em><i>共{{iconTab[nowIndex].data.total.module}}个模块</i> <i>共{{iconTab[nowIndex].data.total.icon}}个icon图标</i></em></a> <span class="total"></span>
							</div>
							<!-- 标题 Ends -->
							<!-- 列表部分 Starts -->
							<div v-for="(module,moduleIndex) in iconTab[nowIndex].data.moduleList" class="profile-iconList-item">
								<h2 class="title"><i class="no">{{moduleIndex+1}}</i>{{module.moduleTitle}} <em>{{module.iconList.length}}个图标</em></h2>
								<ul>
									<li v-for="(icon,index) in module.iconList" :data-title="icon.name" :data-class="icon.className" :data-module="module.moduleTitle"> <span class="iconBar">
						<i :class="[iconTab[nowIndex].data.prefix+icon.className,iconTab[nowIndex].data.iconClass]"></i> </span>
										<p class="name"><span class="no">{{index+1}}</span> <span>{{icon.name}}</span> </p>
										<!-- 简短类名 -->
										<p v-if="!isShowClassName"><input readonly="" class="className" :value="icon.className"></p>
										<!-- 全部类名 -->
										<p v-else><input readonly="" class="className" :value="iconTab[nowIndex].data.prefix+icon.className"></p>
										<div class="suc-copy">复制成功</div>
								</ul>
							</div>
							<!-- 列表部分 Ends -->
							<!-- 图标列表 Ends -->
						</div>
					</div>
				</div>
				<!-- 图标扩展帮助文档 Starts -->
				<div class="profile-iconList-docBar">
					<p class="title">图标扩展帮助文档</p>
					<p class="bar">
						<a href="./doc/iconExt-Intr.pdf" target="_blank">离线下载</a>
					<!-- <a href="./doc/index.html" target="_blank">在线阅读</a> -->
					<a href="./doc/icon-ext-theme.zip" target="_blank">样例主题包下载</a>
					</p>
				</div>
				<!-- 图标扩展帮助文档 Ends -->
			</div>
			<!-- Ends -->
		</div>
		<script type="text/javascript">
			"use strict";
			new Vue({
				el: '#app',
				data: {
					isShowClassName: false,
					isShowModuleName: true,
					nowIndex: 0,
					iconClassName: '',
					nav_iconList: {},
					navLeft_iconList: {},
					profile_iconList: {},
					iconTab: []
				},
				mounted() {
					var iconArr = []
					//二级页面左侧导航图标
					axios.get('json/navLeft.json').then((res) => {
						this.navLeft_iconList = res.data
						this.navLeft_iconList['total'] = this.total(this.navLeft_iconList)
						iconArr[0] = {
							'typeName': '二级页面左侧导航图标',
							'data': this.navLeft_iconList
						}
						this.$set(this.iconTab, 0, iconArr[0])
					}).catch((err) => {
						console.log('error init' + err)
					})
					//系统导航图标
					axios.get('json/nav.json').then((res) => {
						this.nav_iconList = res.data
						this.nav_iconList['total'] = this.total(this.nav_iconList)
						iconArr[1] = {
							'typeName': '系统导航图标',
							'data': this.nav_iconList,
						}
						this.$set(this.iconTab, 1, iconArr[1])
					}).catch((err) => {
						console.log('error init' + err)
					})
					console.log('this.nav_iconList', this.nav_iconList)

					//后台管理导航图标
					axios.get('json/profile.json').then((res) => {
						this.profile_iconList = res.data
						this.profile_iconList['total'] = this.total(this.profile_iconList)
						iconArr[2] = {
							'typeName': '后台配置',
							'data': this.profile_iconList,
						}
						this.$set(this.iconTab, 2, iconArr[2])
					}).catch((err) => {
						console.log('error init' + err)
					})
					console.log('this.profile_iconList', this.profile_iconList)
				},
				methods: {
					toggleTab(index) {
						this.nowIndex = index
					},
					total(modules) {
						//返回值 module 模块数量 icon icon的数量
						let sum = 0,
							obj = {};
						if(modules.moduleList) {
							// 统计模块数 
							obj['module'] = modules.moduleList.length
							for(let i = 0; i < modules.moduleList.length; i++) {
								sum += modules.moduleList[i].iconList.length
							}
						}
						//统计icon的数量
						obj['icon'] = sum
						return obj
					},
					setArr() {}
				}
			});
			$('#app').on('mouseover', '.profile-iconList-item li', function() {
				var $classInput = $(this).find('input');
				$classInput.select()
			})

			// 点击复制功能
			$('#app').on('click', '.profile-iconList-item li', function () {
				var $classInput = $(this).find('input');
				$classInput.select();
				if (document.execCommand('copy')) {
					document.execCommand('copy');
					var sucess = $(this).find(".suc-copy");
					sucess.css("display", "block");
					setTimeout(() => {
						sucess.css("display", "none");
					}, 1000);
				}
			})

			//搜索
			function searchIcon(value, opt) {
				value = value.toLowerCase()
				//当前搜索框 所在容器
				var $item = opt.parents('.iconList-tabItemWrap');
				$item.find('.profile-iconList-item').each(function() {
					var flag;
					if(!value) {
						$(this).show();
						$('.profile-iconList-item').show();
						$('.profile-iconList-item li').show();

					} else {
						flag = false;
						$(this).find("li").each(function() {
							var $this = $(this);
							var data_title = $(this).data('title') || '';
							var data_module = $(this).data('module') || '';
							var data_class = $(this).data('class')
							var query = data_title.indexOf(value) > -1 || data_module.indexOf(value) > -1 || data_class.indexOf(value) > -1

							if(query) {
								$(this).show();
								flag = true;
							} else {
								$(this).hide();
							}
						});
						if(!flag) {
							$(this).hide();
						} else {
							$(this).show();
						}
					}
				});
			}

			$('#app').on('keyup ', '.profile-iconList-wrap .fixBar .search input', function() {
				var value = $.trim($(this).val());
				searchIcon(value, $(this));
			})
		</script>
	</body>

</html>