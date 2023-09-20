package com.landray.kmss.sys.restservice.client;

import java.io.IOException;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestExecutor;

/**
 * 针对大文件上传做定制，在对端有验证的情况下，等到的校验结果再发送文件
 *
 * @author 陈进科
 * 2019-01-28
 */
public class DefaultRequestExecutor extends HttpRequestExecutor {

    private int waitForContinue=3000;
    private static final String UNAUTH_KEY = "DefaultRequestExecutor#UNAUTH_KEY";
    public DefaultRequestExecutor(int waitForContinue){
        if(waitForContinue>0){
            this.waitForContinue = waitForContinue;
        }
    }
    
    @Override
    protected HttpResponse doSendRequest(
            final HttpRequest request,
            final HttpClientConnection conn,
            final HttpContext context) throws IOException, HttpException {

        HttpResponse response = null;

        context.setAttribute(HttpCoreContext.HTTP_CONNECTION, conn);
        context.setAttribute(HttpCoreContext.HTTP_REQ_SENT, Boolean.FALSE);

        conn.sendRequestHeader(request);
        RequestConfig config = (RequestConfig)context.getAttribute(HttpClientContext.REQUEST_CONFIG);
        boolean isAuthenticationEnabled = false ; 
        if (config != null) {
            isAuthenticationEnabled = config.isAuthenticationEnabled();
        }
        if(isAuthenticationEnabled){
            //isAuthenticationEnabled: flush header immediately
            conn.flush();
        }
        
        if (request instanceof HttpEntityEnclosingRequest) {
            // Check for expect-continue handshake. We have to flush the
            // headers and wait for an 100-continue response to handle it.
            // If we get a different response, we must not send the entity.
            boolean sendentity = true;
            final ProtocolVersion ver =
                request.getRequestLine().getProtocolVersion();
            if (((HttpEntityEnclosingRequest) request).expectContinue() &&
                !ver.lessEquals(HttpVersion.HTTP_1_0)) {
                // As suggested by RFC 2616 section 8.2.3, we don't wait for a
                // 100-continue response forever. On timeout, send the entity.
                //DefaultBHttpClientConnection.isResponseAvailable
                if (conn.isResponseAvailable(this.waitForContinue)) {
                    response = conn.receiveResponseHeader();
                    if (canResponseHaveBody(request, response)) {
                        conn.receiveResponseEntity(response);
                    }
                    final int status = response.getStatusLine().getStatusCode();
                    if (status < 200) {
                        if (status != HttpStatus.SC_CONTINUE) {
                            throw new ProtocolException(
                                    "Unexpected response: " + response.getStatusLine());
                        }
                        // discard 100-continue
                        response = null;
                    } else {
                        sendentity = false;
                    }
                }
            }else{
                if (conn.isResponseAvailable(this.waitForContinue)) {
                    response = conn.receiveResponseHeader();
                    if (canResponseHaveBody(request, response)) {
                        conn.receiveResponseEntity(response);
                    }
                    final int status = response.getStatusLine().getStatusCode();
                    //only 401
                    if(status == HttpStatus.SC_UNAUTHORIZED){
                        
                        if(context.getAttribute(UNAUTH_KEY)!=null){
                            // if second time, send
                            sendentity = true;
                        }else{
                            //if first time 
                            context.setAttribute(UNAUTH_KEY, "");
                            sendentity = false;
                        }
                    }
                }
            }
            if (sendentity) {
                context.removeAttribute(UNAUTH_KEY);
                conn.sendRequestEntity((HttpEntityEnclosingRequest) request);
            }
        }
        conn.flush();
        context.setAttribute(HttpCoreContext.HTTP_REQ_SENT, Boolean.TRUE);
        return response;
    }
}
