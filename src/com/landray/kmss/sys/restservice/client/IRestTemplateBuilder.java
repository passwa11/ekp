/*
 * 文 件 名:  IRestTemplateBuilder.java
 * 版    权:  
 * 描    述:  <描述>
*/

package com.landray.kmss.sys.restservice.client;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 熔断接口
 * 
 * @author  宗志 2003798
 * @version  [1.0.0, 2021年9月23日]
 */
public interface IRestTemplateBuilder {
	RestTemplate buildRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory);
}

