	<template:replace name="head">
		<template:super/>		
		<style type="text/css">
			
		</style>
		<script>
		seajs.use(['theme!icon']);
		seajs.use(['theme!form']);
		Com_IncludeFile("dialog.js");
		</script>
	</template:replace>
	<template:replace name="title">${ lfn:message('sys-portal:sysPortalPage.desgin.addWidget') }</template:replace>