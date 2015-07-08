package hwz.com.imageload;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TranslationActivity extends ActionBarActivity
{

    @InjectView(R.id.edt_input)
    EditText edt_input;

    @InjectView(R.id.btn_input)
    Button btn_input;

    @InjectView(R.id.tv_answer)
    TextView tv_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        ButterKnife.inject(TranslationActivity.this);
        btn_input.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toTranslation();
            }
        });
    }

    public void toTranslation()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                try
                {
                    String result = new String(responseBody, "UTF-8");
                    tv_answer.setText(result);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {

            }
        };

        RequestParams params = new RequestParams();
        try
        {
            String str = new String(edt_input.getText().toString().trim().getBytes(),"UTF-8");
            params.put("query",str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        params.put("from","en");
        params.put("to","zh");
        client.addHeader("apikey","a507d53e8c0c12570391d2e8a034da04");
        client.get(this,"http://apis.baidu.com/apistore/tranlateservice/dictionary",params,responseHandler);
    }
}
