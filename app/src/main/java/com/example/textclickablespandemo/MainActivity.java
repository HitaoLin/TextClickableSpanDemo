package com.example.textclickablespandemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.king.image.imageviewer.ImageViewer;
import com.king.image.imageviewer.loader.GlideImageLoader;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> textList = new ArrayList<>();
    List<String> urlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initSpanText();

    }

    private void initData() {
        for (int i = 0; i < 40; i++) {
            textList.add("png" + i);
//            urlList.add("url"+i);
            if (i % 2 == 0)
                urlList.add(String.format("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2853553659,1775735885&fm=26&gp=0.jpg", i));
            else
                urlList.add(String.format("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3922290090,3177876335&fm=26&gp=0.jpg", i));

        }

    }

    private void initSpanText() {

        SpanTextView spannableTextView = (SpanTextView) findViewById(R.id.spanableText);
        spannableTextView.setText(createSpanTexts(), new SpanTextView.SpanClickListener() {
            @Override
            public void OnClickListener(int position) {
                Toast.makeText(MainActivity.this, "You clicked " + createSpanTexts().get(position).getContent() + ".", Toast.LENGTH_SHORT).show();
            }
        });

        spannableTextView.setText(createSpanText(), new SpanTextView.SpanClickListener() {
            @Override
            public void OnClickListener(int position) {
//                Toast.makeText(MainActivity.this, "You clicked "+position+".", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "You clicked " + urlList.get((position - 1) / 2) + ".", Toast.LENGTH_SHORT).show();
                // data 可以多张图片List或单张图片，支持的类型可以是{@link Uri}, {@code url}, {@code path},{@link File}, {@link DrawableRes resId}…等

                ImageViewer.load(urlList)//要加载的图片数据，单张或多张
                        .selection((position - 1) / 2)//当前选中位置
                        .indicator(true)//是否显示指示器，默认不显示
                        .imageLoader(new GlideImageLoader())//加载器，*必须配置，目前内置的有GlideImageLoader或PicassoImageLoader，也可以自己实现
//                      .imageLoader(new PicassoImageLoader())
                        .theme(R.style.ImageViewerTheme)//设置主题风格，默认：R.style.ImageViewerTheme
                        .orientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//设置屏幕方向,默认：ActivityInfo.SCREEN_ORIENTATION_BEHIND
                        .start(MainActivity.this);
            }
        });
    }

    private SpanTextView.ClickSpanModel spanModel;

    private List<SpanTextView.BaseSpanModel> createSpanText() {
        List<SpanTextView.BaseSpanModel> spanModels = new ArrayList<>();

        SpanTextView.TextSpanModel textSpanModel = new SpanTextView.TextSpanModel();
        textSpanModel.setContent("[");
        spanModels.add(textSpanModel);

        for (int i = 0; i < textList.size(); i++) {
            spanModel = new SpanTextView.ClickSpanModel();
            spanModel.setContent(textList.get(i));
            spanModels.add(spanModel);
            if (i < textList.size() - 1) {
                textSpanModel = new SpanTextView.TextSpanModel();
                textSpanModel.setContent(",");
                spanModels.add(textSpanModel);
            }
        }

        textSpanModel = new SpanTextView.TextSpanModel();
        textSpanModel.setContent("]");
        spanModels.add(textSpanModel);

        return spanModels;
    }

    private List<SpanTextView.BaseSpanModel> createSpanTexts() {
        List<SpanTextView.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextView.ClickSpanModel spanModel = new SpanTextView.ClickSpanModel();
        spanModel.setContent("Mary");
        spanModels.add(spanModel);

        SpanTextView.TextSpanModel textSpanModel = new SpanTextView.TextSpanModel();
        textSpanModel.setContent(" commented ");
        spanModels.add(textSpanModel);

        spanModel = new SpanTextView.ClickSpanModel();
        spanModel.setContent("Lucy");
        spanModels.add(spanModel);

        textSpanModel = new SpanTextView.TextSpanModel();
        textSpanModel.setContent("'s video，say：Your video is very nice.");
        spanModels.add(textSpanModel);
        return spanModels;
    }


}