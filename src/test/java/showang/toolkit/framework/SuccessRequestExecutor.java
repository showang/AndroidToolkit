package showang.toolkit.framework;

import showang.toolkit.api.framework.Api;
import showang.toolkit.api.framework.RequestExecutor;

/**
 * Created by williamwang on 2015/10/28.
 */
public class SuccessRequestExecutor implements RequestExecutor {

	private String mMockResult;

	public void setMockResult(String mockResult){
		mMockResult = mockResult;
	}

	@Override
	public void request(Api api, Object tag) {
		api.onRequestSuccess(mMockResult);
	}

	@Override
	public void cancel(Object tag) {

	}

	@Override
	public void cancel(Api api) {

	}
}
