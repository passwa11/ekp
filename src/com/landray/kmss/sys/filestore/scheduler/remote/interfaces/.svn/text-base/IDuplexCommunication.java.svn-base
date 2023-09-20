package com.landray.kmss.sys.filestore.scheduler.remote.interfaces;

import com.landray.kmss.sys.cluster.remoting.InvokeCallback;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingConnectException;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingException;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingSendRequestException;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingTimeoutException;
import com.landray.kmss.sys.cluster.remoting.netty.RequestProcessor;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;

public interface IDuplexCommunication extends RequestProcessor {

	public boolean isAlive();

	public void sendMessageToRemoteAsyn(String remoteAddress, Command sendMessage, long timeoutMill,
			InvokeCallback sendMessageCallback) throws InterruptedException, RemotingException;

	public Command sendMessageToRemoteSyn(String remoteAddress, Command sendMessage, long timeoutMill)
			throws InterruptedException, RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException;

	public void start();

	public void stop();

	public boolean isReady();
}
