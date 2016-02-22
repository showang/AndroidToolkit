package showang.toolkit.api.framework.okhttp;


import android.support.v4.util.ArrayMap;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import showang.toolkit.api.framework.Api;
import showang.toolkit.api.framework.HttpMethod;
import showang.toolkit.api.framework.RequestExecutor;

/**
 * Created by williamwang on 2015/12/5.
 */
public class OkhttpRequestExecutor implements RequestExecutor {
	OkHttpClient okHttpClient = new OkHttpClient();


	@Override
	public void request(Api api, Object tag) {
		Request.Builder requestBuilder = new Request.Builder();
		switch (api.getHttpMethod()) {
			case HttpMethod.GET:
				requestBuilder.get();
				break;
			case HttpMethod.POST:
				requestBuilder.post(createRequestBody(api));
				break;
			case HttpMethod.PUT:
				requestBuilder.put(createRequestBody(api));
				break;
			case HttpMethod.DELETE:
				requestBuilder.delete(createRequestBody(api));
				break;
		}
		doRequest(requestBuilder, api);

	}

	private RequestBody createRequestBody(Api api) {
		if (api.getContentType().contains("json")) {
			return RequestBody.create(MediaType.parse(api.getContentType()), api.getRequestBody());
		} else {
			FormEncodingBuilder builder = new FormEncodingBuilder();
			Map<String, String> paraMap = new ArrayMap<>();
			api.getParameter(paraMap);
			for (String key : paraMap.keySet()) {
				builder.add(key, paraMap.get(key));
			}
			return builder.build();
		}
	}

	private void doRequest(Request.Builder builder, Api api) {
		Map<String, String> headers = new ArrayMap<>();
		api.getHeaders(headers);
		Request request = builder
				.url(api.getHttpMethod() == com.android.volley.Request.Method.GET ? getGetUrlString(api) : api.getUrl())
				.headers(Headers.of(headers))
				.build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			String body = response.body().string();
//			System.out.println("code: " + response.code());
//			System.out.println("body: " + body);
			if (response.code() <= 200) {
				api.onRequestSuccess(body);
			} else {
				api.onRequestFail(RequestError.SERVER_ERROR, "");
			}
		} catch (IOException e) {
			e.printStackTrace();
			api.onRequestFail(RequestError.SERVER_ERROR, "");
		}
	}

	private String getGetUrlString(Api api) {
		String url = api.getUrl() + "?";
		Map<String, String> paramsMap = new ArrayMap<>();
		api.getParameter(paramsMap);
		try {
			for (String key : paramsMap.keySet()) {
				url += key + "=" + URLEncoder.encode(paramsMap.get(key), "UTF-8") + "&";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return url;
	}

	@Override
	public void cancel(Object tag) {

	}

	@Override
	public void cancel(Api api) {

	}
}
