<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.filestore.location.model.SysFileLocation"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>

function config_att_location_aliyun_out(key) {
    window.config_att_location_aliyun = key;
}

function testOssConfig(){
	var endpoint = $("[name='value(filestore.location.aliyun.endpoint)']").val();
    var bucket = $("[name='value(filestore.location.aliyun.bucket)']").val();
    var rootDir = $("[name='value(filestore.location.aliyun.rootDir)']").val();
    var createWhenNotExists = $("[name='value(filestore.location.aliyun.rootDir.createWhenNotExists)']").val();
    var accessKeyId = $("[name='value(filestore.location.aliyun.accessKeyId)']").val();
    var accessKeySecret = $("[name='value(filestore.location.aliyun.accessKeySecret)']").val();
    var location = window.config_att_location_aliyun;
    
    $.ajax({
        type : "POST",
        url :  Com_Parameter.ContextPath + "admin.do?method=testFileStoreConfig",
        dataType: 'json',
        data: {endpoint: endpoint,
        	   bucket: bucket,
        	   rootDir: rootDir,
        	   createWhenNotExists: createWhenNotExists,
        	   accessKeyId:accessKeyId,
        	   accessKeySecret:accessKeySecret,
        	   location:location},
        success: function(data, textStatus, xhr){
	        if(data && data.success){	
	        	alert("阿里云OSS连接成功！");
	        }else{
	        	var posi = data.errorMsg.indexOf("[RequestId]");
	        	var error = posi > -1 ? data.errorMsg.substring(0,posi) : data.errorMsg;
	            alert("阿里云OSS连接失败，请重新配置\n" + error);
	        }
        }, faild: function(){
        	alert("测试失败！");
        }
    });
	
}
</script>
<tr>
	<td class="td_normal_title" width="15%">阿里云EndPoint</td>
	<td>
		<input type="hidden" name="value(filestore.location.aliyun.isProprietaryCloud)" value="false"/>
	   <xform:text property="value(filestore.location.aliyun.endpoint)" subject="阿里云EndPoint" 
	       style="width:50%" showStatus="edit"/><br>
	   <span class="message">阿里云的地域节点，创建Bucket时根据所选区域确定，如选华南则为：http://oss-cn-shenzhen.aliyuncs.com</br>
                                有部分文件如mp4等要使用https协议，使用http协议不能正常访问
       </span>
	</td>
</tr>
<tr>
    <td class="td_normal_title" width="15%">阿里云Bucket</td>
    <td>
       <xform:text property="value(filestore.location.aliyun.bucket)" subject="阿里云Bucket" 
           style="width:50%" showStatus="edit"/><br>
       <span class="message">阿里云的存储空间，如：mybucket。可以在OSS管理控制台新建一个Bucket</span>   
    </td>
</tr>
<tr>
    <td class="td_normal_title" width="15%">阿里云根目录</td>
    <td>
       <xform:text property="value(filestore.location.aliyun.rootDir)" subject="根目录" 
           style="width:50%" showStatus="edit"/><br>
       <span class="message">存储空间下建立的根目录名，如：myRoot(按windows目录命名规则)。所有文件都是根目录的直接子级或间接子级</span><br> 
       <xform:checkbox  property="value(filestore.location.aliyun.rootDir.createWhenNotExists)" dataType="boolean" 
                value="true" subject="待办" showStatus="edit">
           <xform:simpleDataSource value="true">不存在时新建目录</xform:simpleDataSource>
       </xform:checkbox>
    </td>
</tr>
<tr>
    <td class="td_normal_title" width="15%">阿里云accessKeyId</td>
    <td>
       <xform:text property="value(filestore.location.aliyun.accessKeyId)" subject="阿里云accessKeyId" 
           style="width:50%" showStatus="edit"/><br>
       <span class="message">阿里云API的云帐号，建议使用阿里云RAM子用户AccessKey来进行API调用</span>
    </td>
</tr>
<tr>
    <td class="td_normal_title" width="15%">阿里云accessKeySecret</td>
    <td>
       <xform:text property="value(filestore.location.aliyun.accessKeySecret)" subject="阿里云accessKeySecret" 
           htmlElementProperties="type='password'" style="width:50%" showStatus="edit"/><br>
       <span class="message">与accessKeyId相匹配的密钥</span>
    </td>
</tr>

<tr>
    <td class="td_normal_title" width="15%">阿里云OSS上传下载方式</td>
    <td>
       	<xform:radio property="value(filestore.location.aliyun.uploadType)" showStatus="edit">
			<xform:simpleDataSource value="direct">直连</xform:simpleDataSource>
			<xform:simpleDataSource value="proxy">代理</xform:simpleDataSource>
		</xform:radio>
    </td>
</tr>

<tr>
    <td class="td_normal_title" width="15%">测试阿里云OSS配置</td>
    <td>    
        <input type="button" class="btnopt" value="测试" onclick="testOssConfig();"/>
    </td>
</tr>

<tr>
    <td class="td_normal_title" width="15%">注意事项</td>
    <td>    
        <div style="color:blue;">
        	1、启用阿里云对象存储须在admin.do保存成功后，将备份配置文件中的kmssconfig.properties和admin.properties文件复制到aspose或永中附件转换配置目录（\Converter\config）下，具体步骤请参看部署说明文档。<br/>
        	2、启用直连，则附件数据方式走向为：用户浏览器发起请求后，直接与阿里云OSS服务器进行通信，进行附件数据的上传和下载，不需要中转不占用ekp服务器带宽，相对代理方式效率较高，推荐使用直连。<br/>
        	3、启用代理，则附件数据方式走向为：用户浏览器发起请求后，先通过EKP服务器进行中转，由EKP服务器与阿里云OSS服务器进行通信，进行附件数据的上传和下载，会在EKP服务器产生临时文件，且占用部分EKP服务器带宽。
        </div>
    </td>
</tr>


