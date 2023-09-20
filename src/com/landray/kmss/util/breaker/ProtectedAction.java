package com.landray.kmss.util.breaker;

public interface ProtectedAction {
	Object execute() throws Exception;

	boolean isBreakException(Exception e);
}
