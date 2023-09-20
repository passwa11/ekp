
package com.landray.kmss.third.ekp.java.notify.client.v2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.landray.kmss.third.ekp.java.notify.client.v2 package. 
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

    private final static QName _UpdateTodo_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "updateTodo");
    private final static QName _RemoveDonePersonResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "removeDonePersonResponse");
    private final static QName _GetAllTodoResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "getAllTodoResponse");
    private final static QName _ClearTodoPersonsResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "clearTodoPersonsResponse");
    private final static QName _UpdateTodoResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "updateTodoResponse");
    private final static QName _Add_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "add");
    private final static QName _ClearTodoPersons_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "clearTodoPersons");
    private final static QName _RemoveDonePerson_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "removeDonePerson");
    private final static QName _SetPersonsDone_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "setPersonsDone");
    private final static QName _GetAllTodo_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "getAllTodo");
    private final static QName _SetTodoDone_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "setTodoDone");
    private final static QName _SetPersonsDoneResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "setPersonsDoneResponse");
    private final static QName _GetAllTodoId_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "getAllTodoId");
    private final static QName _GetAllTodoIdResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "getAllTodoIdResponse");
    private final static QName _Remove_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "remove");
    private final static QName _RemoveResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "removeResponse");
    private final static QName _SetTodoDoneResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "setTodoDoneResponse");
    private final static QName _Exception_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "Exception");
    private final static QName _AddResponse_QNAME = new QName("http://ekpj.webservice.notify.sys.kmss.landray.com/", "addResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.landray.kmss.third.ekp.java.notify.client.v2
     * 
     */
    public ObjectFactory() {
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
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link Remove }
     * 
     */
    public Remove createRemove() {
        return new Remove();
    }

    /**
     * Create an instance of {@link GetAllTodoId }
     * 
     */
    public GetAllTodoId createGetAllTodoId() {
        return new GetAllTodoId();
    }

    /**
     * Create an instance of {@link GetAllTodoIdResponse }
     * 
     */
    public GetAllTodoIdResponse createGetAllTodoIdResponse() {
        return new GetAllTodoIdResponse();
    }

    /**
     * Create an instance of {@link RemoveResponse }
     * 
     */
    public RemoveResponse createRemoveResponse() {
        return new RemoveResponse();
    }

    /**
     * Create an instance of {@link GetAllTodo }
     * 
     */
    public GetAllTodo createGetAllTodo() {
        return new GetAllTodo();
    }

    /**
     * Create an instance of {@link SetTodoDone }
     * 
     */
    public SetTodoDone createSetTodoDone() {
        return new SetTodoDone();
    }

    /**
     * Create an instance of {@link SetPersonsDoneResponse }
     * 
     */
    public SetPersonsDoneResponse createSetPersonsDoneResponse() {
        return new SetPersonsDoneResponse();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link ClearTodoPersons }
     * 
     */
    public ClearTodoPersons createClearTodoPersons() {
        return new ClearTodoPersons();
    }

    /**
     * Create an instance of {@link SetPersonsDone }
     * 
     */
    public SetPersonsDone createSetPersonsDone() {
        return new SetPersonsDone();
    }

    /**
     * Create an instance of {@link RemoveDonePerson }
     * 
     */
    public RemoveDonePerson createRemoveDonePerson() {
        return new RemoveDonePerson();
    }

    /**
     * Create an instance of {@link UpdateTodoResponse }
     * 
     */
    public UpdateTodoResponse createUpdateTodoResponse() {
        return new UpdateTodoResponse();
    }

    /**
     * Create an instance of {@link ClearTodoPersonsResponse }
     * 
     */
    public ClearTodoPersonsResponse createClearTodoPersonsResponse() {
        return new ClearTodoPersonsResponse();
    }

    /**
     * Create an instance of {@link RemoveDonePersonResponse }
     * 
     */
    public RemoveDonePersonResponse createRemoveDonePersonResponse() {
        return new RemoveDonePersonResponse();
    }

    /**
     * Create an instance of {@link GetAllTodoResponse }
     * 
     */
    public GetAllTodoResponse createGetAllTodoResponse() {
        return new GetAllTodoResponse();
    }

    /**
     * Create an instance of {@link UpdateTodo }
     * 
     */
    public UpdateTodo createUpdateTodo() {
        return new UpdateTodo();
    }

    /**
     * Create an instance of {@link NotifyTodoClearContext }
     * 
     */
    public NotifyTodoClearContext createNotifyTodoClearContext() {
        return new NotifyTodoClearContext();
    }

    /**
     * Create an instance of {@link NotifyTodoGetAllContext }
     * 
     */
    public NotifyTodoGetAllContext createNotifyTodoGetAllContext() {
        return new NotifyTodoGetAllContext();
    }

    /**
     * Create an instance of {@link NotifyTodoUpdateContext }
     * 
     */
    public NotifyTodoUpdateContext createNotifyTodoUpdateContext() {
        return new NotifyTodoUpdateContext();
    }

    /**
     * Create an instance of {@link NotifyTodoSendContext }
     * 
     */
    public NotifyTodoSendContext createNotifyTodoSendContext() {
        return new NotifyTodoSendContext();
    }

    /**
     * Create an instance of {@link NotifyTodoAppResult }
     * 
     */
    public NotifyTodoAppResult createNotifyTodoAppResult() {
        return new NotifyTodoAppResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "updateTodo")
    public JAXBElement<UpdateTodo> createUpdateTodo(UpdateTodo value) {
        return new JAXBElement<UpdateTodo>(_UpdateTodo_QNAME, UpdateTodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDonePersonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "removeDonePersonResponse")
    public JAXBElement<RemoveDonePersonResponse> createRemoveDonePersonResponse(RemoveDonePersonResponse value) {
        return new JAXBElement<RemoveDonePersonResponse>(_RemoveDonePersonResponse_QNAME, RemoveDonePersonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "getAllTodoResponse")
    public JAXBElement<GetAllTodoResponse> createGetAllTodoResponse(GetAllTodoResponse value) {
        return new JAXBElement<GetAllTodoResponse>(_GetAllTodoResponse_QNAME, GetAllTodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearTodoPersonsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "clearTodoPersonsResponse")
    public JAXBElement<ClearTodoPersonsResponse> createClearTodoPersonsResponse(ClearTodoPersonsResponse value) {
        return new JAXBElement<ClearTodoPersonsResponse>(_ClearTodoPersonsResponse_QNAME, ClearTodoPersonsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "updateTodoResponse")
    public JAXBElement<UpdateTodoResponse> createUpdateTodoResponse(UpdateTodoResponse value) {
        return new JAXBElement<UpdateTodoResponse>(_UpdateTodoResponse_QNAME, UpdateTodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearTodoPersons }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "clearTodoPersons")
    public JAXBElement<ClearTodoPersons> createClearTodoPersons(ClearTodoPersons value) {
        return new JAXBElement<ClearTodoPersons>(_ClearTodoPersons_QNAME, ClearTodoPersons.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDonePerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "removeDonePerson")
    public JAXBElement<RemoveDonePerson> createRemoveDonePerson(RemoveDonePerson value) {
        return new JAXBElement<RemoveDonePerson>(_RemoveDonePerson_QNAME, RemoveDonePerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPersonsDone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "setPersonsDone")
    public JAXBElement<SetPersonsDone> createSetPersonsDone(SetPersonsDone value) {
        return new JAXBElement<SetPersonsDone>(_SetPersonsDone_QNAME, SetPersonsDone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "getAllTodo")
    public JAXBElement<GetAllTodo> createGetAllTodo(GetAllTodo value) {
        return new JAXBElement<GetAllTodo>(_GetAllTodo_QNAME, GetAllTodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetTodoDone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "setTodoDone")
    public JAXBElement<SetTodoDone> createSetTodoDone(SetTodoDone value) {
        return new JAXBElement<SetTodoDone>(_SetTodoDone_QNAME, SetTodoDone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPersonsDoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "setPersonsDoneResponse")
    public JAXBElement<SetPersonsDoneResponse> createSetPersonsDoneResponse(SetPersonsDoneResponse value) {
        return new JAXBElement<SetPersonsDoneResponse>(_SetPersonsDoneResponse_QNAME, SetPersonsDoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTodoId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "getAllTodoId")
    public JAXBElement<GetAllTodoId> createGetAllTodoId(GetAllTodoId value) {
        return new JAXBElement<GetAllTodoId>(_GetAllTodoId_QNAME, GetAllTodoId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTodoIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "getAllTodoIdResponse")
    public JAXBElement<GetAllTodoIdResponse> createGetAllTodoIdResponse(GetAllTodoIdResponse value) {
        return new JAXBElement<GetAllTodoIdResponse>(_GetAllTodoIdResponse_QNAME, GetAllTodoIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Remove }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "remove")
    public JAXBElement<Remove> createRemove(Remove value) {
        return new JAXBElement<Remove>(_Remove_QNAME, Remove.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "removeResponse")
    public JAXBElement<RemoveResponse> createRemoveResponse(RemoveResponse value) {
        return new JAXBElement<RemoveResponse>(_RemoveResponse_QNAME, RemoveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetTodoDoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "setTodoDoneResponse")
    public JAXBElement<SetTodoDoneResponse> createSetTodoDoneResponse(SetTodoDoneResponse value) {
        return new JAXBElement<SetTodoDoneResponse>(_SetTodoDoneResponse_QNAME, SetTodoDoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ekpj.webservice.notify.sys.kmss.landray.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

}
