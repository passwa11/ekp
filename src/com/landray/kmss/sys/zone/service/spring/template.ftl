<map name="${name}" >
	<#list areaList?reverse  as area>
		<area shape="${area.areaTemplate.shape}" title="${area.title}"
		    coords="${area.areaTemplate.coords}" href="${area.href}" data-img="${area.imgUrl}" target="_blank"/>
	</#list>
</map>
