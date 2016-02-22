package showang.toolkit.framework;

import android.test.suitebuilder.annotation.LargeTest;

import com.citytalk.citymate.service.api.base.ApiFacade;
import com.citytalk.citymate.service.api.base.CitymateApi;

import junit.framework.Assert;

import org.junit.Before;
import org.robolectric.annotation.Config;

import showang.toolkit.api.framework.okhttp.OkhttpRequestExecutor;

@Config(manifest = Config.NONE)
@LargeTest
public class ApiTestBase {

	protected ApiFacade apiFacade;
	private static final String USER_TOKEN = "eyJpdiI6Im5JWE0wUnhvbkRGSWdoNHdXNm0xMlE9PSIsInZhbHVlIjoiQ2F" +
			"5MlFvb09YanBBUUJOQ0c1OEQyOHBUcnlSMjZqdllVelVuRCt1ODFSTT0iLCJtYWMiOiJiYWNlZTljOWQ0NzY0ZTFhODQ" +
			"wYjFiNDU3ZmMyNGE1ZTAxNTM4NjVlOGRhZWRiZjY1ZjhhY2NlMTU5NjgwODBjIn0=";

	@Before
	public void setup() {
		apiFacade = new ApiFacade("test", new OkhttpRequestExecutor(), 2);
		CitymateApi.initApiFacade(apiFacade);
	}

	protected void initUserToken() {
		apiFacade.updateUserToken(USER_TOKEN);
	}

	protected void clearUserToken() {
		apiFacade.updateUserToken("");
	}

	protected void onRequestFail(int errorCode, String message){
		System.out.printf("onRequestFail: [" + errorCode +"] " + message);
		Assert.assertTrue(false);
	}

}
