package com.landray.kmss.sys.restservice.client;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.landray.kmss.sys.restservice.client.cloud.IEkpCloudClient;


public final class UnusableRestClient implements IRestClient,IEkpCloudClient{
    private static final String ALL_IN_ONE = "Attempt to use a unusable client!";
    private static final UnusableRestClient instance = new UnusableRestClient();
    public static final UnusableRestClient getInstance(){
        return instance;
    }
    private UnusableRestClient() {
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables)
            throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public void get(String url) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public void get() throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables)
            throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> T postForObject(String url, Class<T> responseType, Object requestBody) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> T postForObject(String url, Class<T> responseType) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public void post(String url) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType, Object requestBody)
            throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }

    @Override
    public ResponseEntity<?> postForEntity(String url) throws RestClientException {
        throw new RestClientException(ALL_IN_ONE);
    }
    @Override
    public RestOperations getRestOperations() {
        throw new RestClientException(ALL_IN_ONE);
    }
    @Override
    public <I> I getApiProxy(Class<I> clazz) {
        throw new RestClientException(ALL_IN_ONE);
    }
    @Override
    public <V> V requestVO(String apiPath, Object requestObject, Class<V> responseType) {
        throw new RestClientException(ALL_IN_ONE);
    }
    @Override
    public String requestString(String apiPath, Object requestObject) {
        throw new RestClientException(ALL_IN_ONE);
    }
    @Override
    public List<?> requestList(String apiPath, Object requestObject) {
        throw new RestClientException(ALL_IN_ONE);
    }
    @Override
    public Map<String, ?> requestMap(String apiPath, Object requestObject) {
        throw new RestClientException(ALL_IN_ONE);
    }

	@Override
	public List<String> getAppNames() {
		throw new RestClientException(ALL_IN_ONE);
	}
    
}
