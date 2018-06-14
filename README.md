# WLBus
仿照EventBus,实现轻量级的页面间通信

#### 一、此项目实现功能：
1.根据注解，实现页面间通信；

2.根据注解中的label标签，可以给指定label发送消息；

#### 二、具体使用
```
  
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //注册WLBus
        WLBus.getDefault().register(this);

        //发送消息
        WLBus.getDefault().post("2","wanglu",2);
    }



    @Subscribe({"1","2"})
    public void test1(String name ,String type) {
        Log.e(TAG, "---------test1-------name-->"+name+"----type---->"+type);
    }


    @Subscribe({"1"})
    public void test2(String name ,String type) {
        Log.e(TAG, "---------test2-------name-->"+name+"----type---->"+type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //进行反注册
        WLBus.getDefault().unRegister(this);

        //退出app的时候，进行资源释放
        WLBus.getDefault().release();
    }
}
 ```
 
#### 三、具体逻辑流程图

 


