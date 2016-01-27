package demo.ultra_pull_to_refresh_demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh);

//====================utr加载框架=====需要medule：ptrlib，xml文件为content_main.xml，单纯的下拉刷新=====================================================
        /*final int[] i = {0};
        ListView listView = (ListView) findViewById(R.id.rotate_header_list_view);
        final BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(MainActivity.this);

                textView.setText(""+ i[0]++);
                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
        final PtrFrameLayout mPtrFrame = (PtrFrameLayout) findViewById(R.id.rotate_header_list_view_frame);

        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        //MATCH_PARENT = -1;
        //WRAP_CONTENT = -2;
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        LocalDisplay.init(MainActivity.this);
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setLoadingMinTime(2000);//设置最小的加载时间
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);//不让content移动

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();
                baseAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });*/

//======================================================官方SwipeRefreshLayout，单纯的下拉刷新，xml文件：content_main2.xml===========================================================
        /*final TextView tv = (TextView) findViewById(R.id.textView1);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                tv.setText("正在刷新");
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        tv.setText("刷新完成");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });*/

//=============官方SwipeRefreshLayout的加载webview的方案,用ScrollSwipeRefreshLayout，未测试，见：http://www.tuicool.com/articles/UrQzUv3==================================================
        /*webView = (WebView) this.findViewById(R.id.webview);
        refreshLayout = (ScrollSwipeRefreshLayout) this.findViewById(R.id.refresh_layout);
        refreshLayout.setViewGroup(webView);//设置监听滚动的子view，多了这一步
        refreshLayout.setOnRefreshListener(this);
        //设置颜色
        refreshLayout.setColorScheme(R.color.green, R.color.gray, R.color.blue_50, R.color.light_white);
        webView.loadUrl("http://blog.csdn.net/spring_he/article/details/19359099");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    //设置加载完成后结束动画
                    refreshLayout.setRefreshing(false);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });*/

//====================官方SwipeRefreshLayout+上拉加载更多，需要自定义view：RefreshLayout,xml文件：refresh.xml===========================================
        // 模拟20条数据
        final List<String> datas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            datas.add("item - " + i);
        }
        final BaseAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                datas);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        // 获取RefreshLayout实例
        final RefreshLayout myRefreshListView = (RefreshLayout) findViewById(R.id.swipe_layout);
        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        myRefreshListView.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                myRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 更新数据
                        datas.add(new Date().toGMTString());
                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        // 加载监听器
        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                Toast.makeText(MainActivity.this, "load", Toast.LENGTH_SHORT).show();
                myRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(new Date().toGMTString());
                        adapter.notifyDataSetChanged();
                        //让footer消失
                        myRefreshListView.setLoading(false);
                    }
                }, 3000);
            }
        });

    }

    /*@Override
    public void onRefresh() {
        //下拉重新加载
        webView.reload();
    }*/

}
