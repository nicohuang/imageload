package hwz.com.imageload.HttpHelp;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jan on 15/7/6.
 */
public class HttpHelp
{
    private static AsyncHttpClient client;
    public static void getHttp(Context context ,AsyncHttpResponseHandler responseHandler)
    {
        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id","-1");
        client.addHeader("apikey","a507d53e8c0c12570391d2e8a034da04");
        client.get(context,"http://apis.baidu.com/myml/c1c/c1c",params,responseHandler);
    }
}
