package hwz.com.imageload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity2 extends ActionBarActivity
{

    @InjectView(R.id.edt_input)
    EditText edt_input;

    @InjectView(R.id.btn_input)
    Button btn_input;

    @InjectView(R.id.list_item)
    ListView list_item;

    @InjectView(R.id.tv_grass)
    TextView tv_grass;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.inject(MainActivity2.this);
        btn_input.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String str = edt_input.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("str", str);
                setResult(Activity.RESULT_OK, intent);
                MainActivity2.this.finish();
                /**
                 *
                 */
            }
        });
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            items.add("name" + i);
        }
        LoadImageAdapter adapter = new LoadImageAdapter(this, items);
        list_item.setAdapter(adapter);


        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                String result = new String(responseBody);
                tv_grass.setText(result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {

            }
        };
        RequestParams params = new RequestParams();
        params.put("id","-1");
        client.addHeader("apikey","a507d53e8c0c12570391d2e8a034da04");
        client.get(this,"http://apis.baidu.com/myml/c1c/c1c",params,responseHandler);
    }

}
