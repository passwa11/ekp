/*
 * 文 件 名:  HystrixRestTemplateBuilder.java
 * 版    权:  
 * 描    述:  <描述>
*/

package com.landray.kmss.sys.restservice.client.hystrix;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.landray.kmss.sys.restservice.client.IRestTemplateBuilder;
import com.landray.kmss.sys.restservice.client.hystrix.command.CommandFactory;
import com.netflix.hystrix.HystrixCommand;

/**
 * 实现熔断接口
 * 
 * @author  宗志 2003798
 * @version  [1.0.0, 2021年9月23日]
 */
public class HystrixRestTemplateBuilder implements IRestTemplateBuilder {

	@Override
	public RestTemplate buildRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		RestTemplate template = new RestTemplate(clientHttpRequestFactory){
			
			private <T>T doExecuteInternal(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor){
				return super.doExecute(url, method, requestCallback, responseExtractor);
			}
		
			@Override
			protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
				if(logger.isInfoEnabled()){
					logger.info("begin create command for restclient");
				}
				HystrixCommand command = CommandFactory.getInstance().createCommand(new CommandAction<T>() {
					@Override
					public T execute() throws Exception {
						return doExecuteInternal(url, method, requestCallback, responseExtractor);
					}
				});
				if(logger.isInfoEnabled()){
					logger.info("end create command for restclient");
				}
				return (T) command.execute();
			}
		};
		return template;
	}

}

