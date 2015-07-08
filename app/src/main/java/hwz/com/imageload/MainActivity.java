package hwz.com.imageload;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hwz.com.imageload.component.ImageLoaderHelper;


public class MainActivity extends ActionBarActivity
{
    @InjectView(R.id.iv_im1)
    ImageView iv_im1;

    @InjectView(R.id.iv_im2)
    ImageView iv_im2;

    @InjectView(R.id.tv_tx1)
    TextView textView;

    @InjectView(R.id.btn_toplay)
    Button btn_toplay;

    @InjectView(R.id.btn_translation)
    Button btn_translation;

    private BitmapProcessor bitmapProcessor;

    //logoBitmap
    private Bitmap logoBitmap;

    public static File cacheDir;
    private String url = "http://a.hiphotos.baidu.com/image/pic/item/3b292df5e0fe99256473e35736a85edf8db171be.jpg";

    private DisplayImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(MainActivity.this);
        //图片缓存路径
        bitmapProcessor = new BitmapProcessor()
        {
            @Override
            public Bitmap process(Bitmap bitmap)
            {
                return null;
            }
        };
        cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");
        imageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//缓存到内存
                .cacheOnDisk(true) //缓存到手机sdcard
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        //加载图片
        ImageLoaderHelper.getInstance(MainActivity.this).displayImage(url, iv_im1, imageOptions);
        iv_im1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivityForResult(intent, 1);
            }
        });

        ImageLoaderHelper.getInstance(MainActivity.this).displayImage(url, iv_im2, imageOptions);
        //加载图片二
        iv_im2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        //猜一猜游戏
        btn_toplay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
        //翻译

       btn_translation.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               Intent intent = new Intent(MainActivity.this,TranslationActivity.class);
               startActivity(intent);
           }
       });
    }

    //forresult操作类
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case 1:

                    textView.setText(data.getStringExtra("str"));
                    break;
                case 2:
                    iv_im1.setImageBitmap(logoBitmap);
                    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                    ContentResolver resolver = getContentResolver();

                    //此处的用于判断接收的Activity是不是你想要的那个

                    try
                    {
                        Uri originalUri = data.getData();        //获得图片的uri

                        logoBitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
                        iv_im1.setImageBitmap(logoBitmap);

                        //这里开始的第二部分，获取图片的路径：

                        String[] proj = {MediaStore.Images.Media.DATA};

                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        String path = cursor.getString(column_index);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
