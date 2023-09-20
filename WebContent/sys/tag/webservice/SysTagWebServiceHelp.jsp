<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标签接入接出接口</title>
<link href='${LUI_ContextPath }/sys/tag/webservice/marxico.css'
	rel='stylesheet'>

<style>
.note-content {
	width: 800px;
	margin: 0 auto;
}
</style>
</head>
<body>

	<div id='preview-contents' class='note-content'>

		<h2 id="标签接入接出接口">标签接入接出接口</h2>

		<div>
			<div class="toc">
				<div class="toc">
					<ul>
						<li><ul>
								<li><a href="#标签接入接出接口">标签接入接出接口</a>
									<ul>
										<li><a href="#接口说明">接口说明</a>
											<ul>
												<li><a href="#查询分类接口getcategories">查询分类接口getCategories</a>
													<ul>
														<li><a href="#参数信息">参数信息</a></li>
														<li><a href="#返回信息">返回信息</a></li>
													</ul></li>
												<li><a href="#查询标签接口gettags">查询标签接口getTags</a>
													<ul>
														<li><a href="#参数信息-1">参数信息</a></li>
														<li><a href="#返回信息-1">返回信息</a></li>
													</ul></li>
												<li><a href="#获取配置组接口getgroups">获取配置组接口getGroups</a>
													<ul>
														<li><a href="#参数信息-2">参数信息</a></li>
														<li><a href="#返回信息-2">返回信息</a></li>
													</ul></li>
												<li><a href="#根据标签数组获取是否特殊接口getisspecialbytags">根据标签数组获取是否特殊接口getIsSpecialByTags</a>
													<ul>
														<li><a href="#参数信息-3">参数信息</a></li>
														<li><a href="#返回信息-3">返回信息</a></li>
													</ul></li>
												<li><a href="#新增接口addtags">新增接口addTags</a>
													<ul>
														<li><a href="#参数信息-4">参数信息</a></li>
														<li><a href="#返回信息-4">返回信息</a></li>
													</ul></li>
											</ul></li>
										<li><a href="#样例代码">样例代码</a></li>
									</ul></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</div>

		<blockquote>
			<p>接出接口</p>

			<blockquote>
				<p>
					查询分类接口
					<code>getCategories</code>
					<br> 查询标签接口
					<code>getTags</code>
					<br> 获取配置组接口
					<code>getGroups</code>
					<br> 根据标签数组获取是否特殊接口
					<code>getIsSpecialByTags</code>
				</p>
			</blockquote>

			<p>接入接口</p>

			<blockquote>
				<p>
					新增接口
					<code>addTags</code>
				</p>
			</blockquote>
		</blockquote>

		<h3 id="接口说明">接口说明</h3>



		<h4 id="查询分类接口getcategories">
			查询分类接口
			<code>getCategories</code>
		</h4>



		<h5 id="参数信息">参数信息</h5>

		<table>
			<thead>
				<tr>
					<th>参数名</th>
					<th>类型</th>
					<th>是否允许为空</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>type</code></td>
					<td>字符串<code>String</code></td>
					<td>是</td>
					<td>决定返回信息的格式，详情见返回信息</td>
				</tr>
			</tbody>
		</table>




		<h5 id="返回信息">返回信息</h5>

		<p>
			返回
			<code>TagGetResult</code>
			对象
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>returnState</code></td>
					<td>数字<code>int</code></td>
					<td><code>0</code>代表未操作 <br> <code>1</code>代表失败<br>
						<code>2</code>代表成功</td>
				</tr>
				<tr>
					<td><code>datas</code></td>
					<td>字符串列表<code>List&lt;String&gt;</code></td>
					<td>当参数<code>type</code>为<code>1</code>时，字符串格式为：<br> <code>{"text":"标签名","value":"标签id","isAutoFetch":"0"}</code><br>当参数<code>type</code>为<code>2</code>时，字符串格式为：<br>
						<code>{"name":"标签名","value":"标签id","isAutoFetch":"0"}</code></td>
				</tr>
			</tbody>
		</table>




		<h4 id="查询标签接口gettags">
			查询标签接口
			<code>getTags</code>
		</h4>



		<h5 id="参数信息-1">参数信息</h5>

		<table>
			<thead>
				<tr>
					<th>参数名</th>
					<th>类型</th>
					<th>是否允许为空</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>tagGetTagsContext</code></td>
					<td>对象<code>TagGetTagsContext</code></td>
					<td>否</td>
				</tr>
			</tbody>
		</table>


		<p>
			对象
			<code>TagGetTagsContext</code>
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>是否允许为空</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>paramId</code></td>
					<td>字符串<code>String</code></td>
					<td>依赖<code>type</code></td>
					<td>分类<code>id</code></td>
				</tr>
				<tr>
					<td><code>key</code></td>
					<td>字符串<code>String</code></td>
					<td>依赖<code>type</code></td>
					<td>搜索关键字</td>
				</tr>
				<tr>
					<td><code>type</code></td>
					<td>字符串<code>String</code></td>
					<td>否</td>
					<td><code>getTag</code>代表根据分类<code>id</code>获取标签<br> <code>search</code>代表根据<code>key</code>查询标签名</td>
				</tr>
				<tr>
					<td><code>loginName</code></td>
					<td>字符串<code>String</code></td>
					<td>否</td>
					<td>当前用户登录名,用于过滤权限</td>
				</tr>
			</tbody>
		</table>




		<h5 id="返回信息-1">返回信息</h5>

		<p>
			返回
			<code>TagGetResult</code>
			对象
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>returnState</code></td>
					<td>数字<code>int</code></td>
					<td><code>0</code>代表未操作 <br> <code>1</code>代表失败<br>
						<code>2</code>代表成功</td>
				</tr>
				<tr>
					<td><code>datas</code></td>
					<td>字符串列表<code>List&lt;String&gt;</code></td>
					<td>字符串格式为：<br> <code>{"text":"标签名","value":"标签id",name:"标签名",id:"标签id"}</code></td>
				</tr>
			</tbody>
		</table>




		<h4 id="获取配置组接口getgroups">
			获取配置组接口
			<code>getGroups</code>
		</h4>



		<h5 id="参数信息-2">参数信息</h5>

		<table>
			<thead>
				<tr>
					<th>参数名</th>
					<th>类型</th>
					<th>是否允许为空</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>modelName</code></td>
					<td>字符串<code>String</code></td>
					<td>否</td>
					<td>模块名称</td>
				</tr>
			</tbody>
		</table>




		<h5 id="返回信息-2">返回信息</h5>

		<p>
			返回
			<code>TagResult</code>
			对象
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>returnState</code></td>
					<td>数字<code>int</code></td>
					<td><code>0</code>代表未操作 <br> <code>1</code>代表失败<br>
						<code>2</code>代表成功</td>
				</tr>
				<tr>
					<td><code>message</code></td>
					<td>字符串<code>String</code></td>
					<td>配置组信息，字符串格式为：<br> <code>[{categoryName:"标签筛选项名",tags:['标签名'...]]</code></td>
				</tr>
			</tbody>
		</table>




		<h4 id="根据标签数组获取是否特殊接口getisspecialbytags">
			根据标签数组获取是否特殊接口
			<code>getIsSpecialByTags</code>
		</h4>



		<h5 id="参数信息-3">参数信息</h5>

		<table>
			<thead>
				<tr>
					<th>参数名</th>
					<th>类型</th>
					<th>是否允许为空</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>tags</code></td>
					<td>列表<code>List&lt;String&gt;</code></td>
					<td>否</td>
					<td>需要判断的标签列表</td>
				</tr>
			</tbody>
		</table>




		<h5 id="返回信息-3">返回信息</h5>

		<p>
			返回
			<code>TagResult</code>
			对象
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>returnState</code></td>
					<td>数字<code>int</code></td>
					<td><code>0</code>代表未操作 <br> <code>1</code>代表失败<br>
						<code>2</code>代表成功</td>
				</tr>
				<tr>
					<td><code>message</code></td>
					<td>字符串<code>String</code></td>
					<td>字符串格式为：<br> <code>[{'标签名1':0},{'标签名2':1}]</code> <br>
						<code>0</code>代表非特殊标签，<code>1</code>代表特殊标签
					</td>
				</tr>
			</tbody>
		</table>




		<h4 id="新增接口addtags">
			新增接口
			<code>addTags</code>
		</h4>

		<blockquote>
			<p>内部使用接口</p>
		</blockquote>



		<h5 id="参数信息-4">参数信息</h5>

		<table>
			<thead>
				<tr>
					<th>参数名</th>
					<th>类型</th>
					<th>是否允许为空</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>context</code></td>
					<td>对象<code>TagAddContext</code></td>
					<td>否</td>
				</tr>
			</tbody>
		</table>


		<p>
			对象
			<code>TagAddContext</code>
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>是否允许为空</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>appName</code></td>
					<td>字符串<code>String</code></td>
					<td>否</td>
					<td>服务器标识，必须唯一</td>
				</tr>
				<tr>
					<td><code>tags</code></td>
					<td>字符串<code>String</code></td>
					<td>否</td>
					<td>新增内容，字符串格式为：<br> <code>[{docAlterTime:"",docCreateTime:""
							...]</code><br>各属性备注：<br> <code>docAlterTime</code>最后修改时间<br>
						<code>docCreateTime</code>创建时间<br> <code>docCreatorName</code>创建者名称<br>
						<code>docStatus</code>关联文档状态<br> <code>docSubject</code>关联文档标题<br>
						<code>fdId</code>主键<br> <code>fdKey</code>机制标志<br> <code>fdModelId</code>文档主键<br>
						<code>fdModelName</code>文档模块名<br> <code>fdQueryCondition</code>查询条件<br>
						<code>fdUrl</code>关联文档链接<br> <code>fdTagNames</code>标签信息
					</td>
				</tr>
			</tbody>
		</table>




		<h5 id="返回信息-4">返回信息</h5>

		<p>
			返回
			<code>TagResult</code>
			对象
		</p>

		<table>
			<thead>
				<tr>
					<th>属性名</th>
					<th>类型</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><code>returnState</code></td>
					<td>数字<code>int</code></td>
					<td><code>0</code>代表未操作 <br> <code>1</code>代表失败<br>
						<code>2</code>代表成功</td>
				</tr>
				<tr>
					<td><code>message</code></td>
					<td>字符串<code>String</code></td>
					<td>错误信息</td>
				</tr>
			</tbody>
		</table>


		<h3 id="样例代码">样例代码</h3>



		<pre class="prettyprint hljs-dark">
			<code class="language-java hljs">    <span
					class="hljs-keyword">private</span> String servicePath = <span
					class="hljs-string">"/sys/webservice/sysTagWebService"</span>;<br><br>    <span
					class="hljs-keyword">private</span> ISysTagWebService sysTagWebService;<br>    <span
					class="hljs-function"><span class="hljs-keyword">private</span> ISysTagWebService <span
					class="hljs-title">getService</span><span class="hljs-params">()</span> <span
					class="hljs-keyword">throws</span> Exception </span>{<br>        <span
					class="hljs-keyword">if</span> (sysTagWebService == <span
					class="hljs-keyword">null</span>) {<br>            JaxWsProxyFactoryBean factory = <span
					class="hljs-keyword">new</span> JaxWsProxyFactoryBean();<br>            factory.getInInterceptors().add(<span
					class="hljs-keyword">new</span> LoggingInInterceptor());<br>            factory.getOutInterceptors().add(<span
					class="hljs-keyword">new</span> LoggingOutInterceptor());<br>            factory.getOutInterceptors().add(<span
					class="hljs-keyword">new</span> AddSoapHeader());<br>            factory.setServiceClass(ISysTagWebService.class);<br>            factory.setAddress(<span
					class="hljs-keyword">new</span> EkpJavaConfig()<br>                    .getValue(<span
					class="hljs-string">"kmss.java.webservice.urlPrefix"</span>) + servicePath);<br>            sysTagWebService = (ISysTagWebService) factory.create();<br>        }<br>        <span
					class="hljs-keyword">return</span> sysTagWebService;<br>    }<br><br>    <span
					class="hljs-comment">// 获取标签分类信息</span><br>    <span
					class="hljs-function"><span class="hljs-keyword">public</span> TagGetResult <span
					class="hljs-title">getCategories</span><span class="hljs-params">(String type)</span> <span
					class="hljs-keyword">throws</span> Exception </span>{<br>        <span
					class="hljs-keyword">return</span> getService().getCategories(type);<br>    }<br><br>    <span
					class="hljs-comment">// 获取标签信息</span><br>    <span
					class="hljs-function"><span class="hljs-keyword">public</span> TagGetResult <span
					class="hljs-title">getTags</span><span class="hljs-params">(TagGetTagsContext context)</span> <span
					class="hljs-keyword">throws</span> Exception </span>{<br>        <span
					class="hljs-keyword">return</span> getService().getTags(context);<br>    }<br><br>    <span
					class="hljs-comment">// 同步新增标签</span><br>    <span
					class="hljs-function"><span class="hljs-keyword">public</span> TagResult <span
					class="hljs-title">addTags</span><span class="hljs-params">(TagAddContext context)</span> <span
					class="hljs-keyword">throws</span> Exception </span>{<br>        <span
					class="hljs-keyword">return</span> getService().addTags(context);<br>    }<br><br>    <span
					class="hljs-comment">// 获取标签配置组</span><br>    <span
					class="hljs-function"><span class="hljs-keyword">public</span> TagResult <span
					class="hljs-title">getGroups</span><span class="hljs-params">(String modelName)</span> <span
					class="hljs-keyword">throws</span> Exception </span>{<br>        <span
					class="hljs-keyword">return</span> getService().getGroups(modelName);<br>    }<br><br>    <span
					class="hljs-comment">//判断是否特殊标签</span><br>    <span
					class="hljs-function"><span class="hljs-keyword">public</span> TagResult <span
					class="hljs-title">getIsSpecialByTags</span><span
					class="hljs-params">(List&lt;String&gt; tags)</span> <span
					class="hljs-keyword">throws</span> Exception </span>{<br>        <span
					class="hljs-keyword">return</span> getService().getIsSpecialByTags(tags);<br>    }<br>
			</code>
		</pre>
	</div>
</body>
</html>
