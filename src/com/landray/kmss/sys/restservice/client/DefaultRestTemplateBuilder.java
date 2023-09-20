/*
 * 文 件 名:  HystrixRestTemplateBuilder.java
 * 版    权:  
 * 描    述:  <描述>
*/

package com.landray.kmss.sys.restservice.client;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 默认实现 IRestTemplateBuilder 接口
 * 和 DefaultFusableConnectionOperator配对使用 @see DefaultFusableConnectionOperator 
 * 
 * @author  宗志 2003798
 * @version  [1.0.0, 2021年9月23日]
 */
public class DefaultRestTemplateBuilder implements IRestTemplateBuilder {

	@Override
	public RestTemplate buildRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		RestTemplate template = new RestTemplate(clientHttpRequestFactory);
		return template;
	}

}

