package hwz.com.imageload;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hwz.com.imageload.HttpHelp.HttpHelp;


public class GameActivity extends ActionBarActivity
{

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.btn_tosee)
    Button btn_tosee;

    @InjectView(R.id.btn_tonest)
    Button btn_tonest;

    @InjectView(R.id.tv_answer)
    TextView tv_answer;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.inject(GameActivity.this);
        toPlay();
        //下一个按钮
        btn_tonest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                toPlay();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        //显示答案
        btn_tosee.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv_answer.setVisibility(View.VISIBLE);
            }
        });

    }
    public void toPlay()
    {

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                String result = new String(responseBody);
                try
                {
                    JSONObject object = new JSONObject(result);
                    String answer = object.getString("Answer");
                    String title = object.getString("Title");
                    tv_answer.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    tv_answer.setText(answer);
                    tv_title.setText(title);
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
        HttpHelp.getHttp(this,responseHandler);
    }

}
