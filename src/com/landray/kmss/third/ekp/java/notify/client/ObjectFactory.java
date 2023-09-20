
package com.landray.kmss.third.ekp.java.notify.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.landray.kmss.third.ekp.java.notify.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteTodoResponse_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "deleteTodoResponse");
    private final static QName _UpdateTodo_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "updateTodo");
    private final static QName _GetTodoResponse_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "getTodoResponse");
    private final static QName _SetTodoDone_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "setTodoDone");
    private final static QName _DeleteTodo_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "deleteTodo");
    private final static QName _GetTodo_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "getTodo");
    private final static QName _SendTodo_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "sendTodo");
    private final static QName _SendTodoResponse_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "sendTodoResponse");
    private final static QName _UpdateTodoResponse_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "updateTodoResponse");
    private final static QName _Exception_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "Exception");
    private final static QName _SetTodoDoneResponse_QNAME = new QName("http://webservice.notify.sys.kmss.landray.com/", "setTodoDoneResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.landray.kmss.third.ekp.java.notify.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateTodo }
     * 
     */
    public UpdateTodo createUpdateTodo() {
        return new UpdateTodo();
    }

    /**
     * Create an instance of {@link DeleteTodoResponse }
     * 
     */
    public DeleteTodoResponse createDeleteTodoResponse() {
        return new DeleteTodoResponse();
    }

    /**
     * Create an instance of {@link GetTodoResponse }
     * 
     */
    public GetTodoResponse createGetTodoResponse() {
        return new GetTodoResponse();
    }

    /**
     * Create an instance of {@link SetTodoDone }
     * 
     */
    public SetTodoDone createSetTodoDone() {
        return new SetTodoDone();
    }

    /**
     * Create an instance of {@link DeleteTodo }
     * 
     */
    public DeleteTodo createDeleteTodo() {
        return new DeleteTodo();
    }

    /**
     * Create an instance of {@link SendTodoResponse }
     * 
     */
    public SendTodoResponse createSendTodoResponse() {
        return new SendTodoResponse();
    }

    /**
     * Create an instance of {@link SendTodo }
     * 
     */
    public SendTodo createSendTodo() {
        return new SendTodo();
    }

    /**
     * Create an instance of {@link GetTodo }
     * 
     */
    public GetTodo createGetTodo() {
        return new GetTodo();
    }

    /**
     * Create an instance of {@link SetTodoDoneResponse }
     * 
     */
    public SetTodoDoneResponse createSetTodoDoneResponse() {
        return new SetTodoDoneResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link UpdateTodoResponse }
     * 
     */
    public UpdateTodoResponse createUpdateTodoResponse() {
        return new UpdateTodoResponse();
    }

    /**
     * Create an instance of {@link NotifyTodoGetContext }
     * 
     */
    public NotifyTodoGetContext createNotifyTodoGetContext() {
        return new NotifyTodoGetContext();
    }

    /**
     * Create an instance of {@link NotifyTodoAppResult }
     * 
     */
    public NotifyTodoAppResult createNotifyTodoAppResult() {
        return new NotifyTodoAppResult();
    }

    /**
     * Create an instance of {@link NotifyTodoSendContext }
     * 
     */
    public NotifyTodoSendContext createNotifyTodoSendContext() {
        return new NotifyTodoSendContext();
    }

    /**
     * Create an instance of {@link NotifyTodoUpdateContext }
     * 
     */
    public NotifyTodoUpdateContext createNotifyTodoUpdateContext() {
        return new NotifyTodoUpdateContext();
    }

    /**
     * Create an instance of {@link NotifyTodoRemoveContext }
     * 
     */
    public NotifyTodoRemoveContext createNotifyTodoRemoveContext() {
        return new NotifyTodoRemoveContext();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "deleteTodoResponse")
    public JAXBElement<DeleteTodoResponse> createDeleteTodoResponse(DeleteTodoResponse value) {
        return new JAXBElement<DeleteTodoResponse>(_DeleteTodoResponse_QNAME, DeleteTodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "updateTodo")
    public JAXBElement<UpdateTodo> createUpdateTodo(UpdateTodo value) {
        return new JAXBElement<UpdateTodo>(_UpdateTodo_QNAME, UpdateTodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "getTodoResponse")
    public JAXBElement<GetTodoResponse> createGetTodoResponse(GetTodoResponse value) {
        return new JAXBElement<GetTodoResponse>(_GetTodoResponse_QNAME, GetTodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetTodoDone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "setTodoDone")
    public JAXBElement<SetTodoDone> createSetTodoDone(SetTodoDone value) {
        return new JAXBElement<SetTodoDone>(_SetTodoDone_QNAME, SetTodoDone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "deleteTodo")
    public JAXBElement<DeleteTodo> createDeleteTodo(DeleteTodo value) {
        return new JAXBElement<DeleteTodo>(_DeleteTodo_QNAME, DeleteTodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "getTodo")
    public JAXBElement<GetTodo> createGetTodo(GetTodo value) {
        return new JAXBElement<GetTodo>(_GetTodo_QNAME, GetTodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendTodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "sendTodo")
    public JAXBElement<SendTodo> createSendTodo(SendTodo value) {
        return new JAXBElement<SendTodo>(_SendTodo_QNAME, SendTodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendTodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "sendTodoResponse")
    public JAXBElement<SendTodoResponse> createSendTodoResponse(SendTodoResponse value) {
        return new JAXBElement<SendTodoResponse>(_SendTodoResponse_QNAME, SendTodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "updateTodoResponse")
    public JAXBElement<UpdateTodoResponse> createUpdateTodoResponse(UpdateTodoResponse value) {
        return new JAXBElement<UpdateTodoResponse>(_UpdateTodoResponse_QNAME, UpdateTodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetTodoDoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.notify.sys.kmss.landray.com/", name = "setTodoDoneResponse")
    public JAXBElement<SetTodoDoneResponse> createSetTodoDoneResponse(SetTodoDoneResponse value) {
        return new JAXBElement<SetTodoDoneResponse>(_SetTodoDoneResponse_QNAME, SetTodoDoneResponse.class, null, value);
    }

}
