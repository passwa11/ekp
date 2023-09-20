package com.landray.kmss.third.ekp.java.notify;

import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoAppResult;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoGetContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoRemoveContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoSendContext;

public interface IEkpClientTodoService {

	public NotifyTodoAppResult sendTodo(NotifyTodoSendContext sendContext)
			throws Exception;

	public NotifyTodoAppResult setTodoDone(NotifyTodoRemoveContext removeContext)
			throws Exception;

	public NotifyTodoAppResult deleteTodo(NotifyTodoRemoveContext removeContext)
			throws Exception;

	public NotifyTodoAppResult getTodo(NotifyTodoGetContext getContext)
			throws Exception;
}
