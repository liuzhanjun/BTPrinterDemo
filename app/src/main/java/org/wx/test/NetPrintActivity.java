package org.wx.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.wx.test.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by yunniu on 2017/6/10.
 */

public class NetPrintActivity extends Activity {


    private EditText e_ipaddress;
    private EditText e_port;
    private static final String TAG="NetPrintActivity";
    private ConnHandlerThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_conn);

        e_ipaddress= (EditText) findViewById(R.id.ip_address);
        e_port= (EditText) findViewById(R.id.port);

        thread=new ConnHandlerThread("conn");
        thread.start();
    }


    /**
     * 打印
     * @param view
     */
    public void printMsg(View view){

        thread.getHandler().sendEmptyMessage(1);
    }

    /**
     * 连接打印机
     * @param view
     */
    public void connect(View view){
        thread.getHandler().sendEmptyMessage(0);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.quit();
    }

    public static class ConnHandlerThread extends HandlerThread{


        private Socket socket;

        public ConnHandlerThread(String name) {
            super(name);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            handler=new Handler(getLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    int w=msg.what;
                    switch (w){
                        case 0:
                            conn();
                            break;
                        case 1:
                            printMsg();
                            break;
                    }
                }
            };

        }

        public Handler getHandler() {
            return handler;
        }

        private Handler handler;
        public void printMsg(){
            Log.i(TAG, "printMsg: currentid="+Thread.currentThread().getId());
            LabelCommand tsc = new LabelCommand();
            tsc.addSize(60, 60); // 设置标签尺寸，按照实际尺寸设置
            tsc.addGap(0); // 设置标签间隙，按照实际尺寸设置，如果为无间隙纸则设置为0
            tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, LabelCommand.MIRROR.NORMAL);// 设置打印方向
            tsc.addReference(0, 0);// 设置原点坐标
            tsc.addTear(EscCommand.ENABLE.ON); // 撕纸模式开启
            tsc.addCls();// 清除打印缓冲区
            // 绘制简体中文
            tsc.addText(20, 20, LabelCommand.FONTTYPE.SIMPLIFIED_CHINESE, LabelCommand.ROTATION.ROTATION_0, LabelCommand.FONTMUL.MUL_1, LabelCommand.FONTMUL.MUL_1,
                    "Welcome to use SMARNET printer!");
            // 绘制图片

            tsc.addQRCode(250, 80, LabelCommand.EEC.LEVEL_L, 5, LabelCommand.ROTATION.ROTATION_0, " www.smarnet.cc");
            // 绘制一维条码
            tsc.add1DBarcode(20, 250, LabelCommand.BARCODETYPE.CODE128, 100, LabelCommand.READABEL.EANBEL, LabelCommand.ROTATION.ROTATION_0, "SMARNET");
            tsc.addPrint(1, 1); // 打印标签
            tsc.addSound(2, 100); // 打印标签后 蜂鸣器响
            Vector<Byte> datas = tsc.getCommand(); // 发送数据
            byte[] bytes = GpUtils.ByteTo_byte(datas);
            try {
                Log.i(TAG, "printMsg: 开始打印信息");
               OutputStream os= socket.getOutputStream();
                os.write(bytes,0,bytes.length);
                os.flush();
                os.close();
                Log.i(TAG, "printMsg: 打印完毕");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * 连接设备
         */
        public void conn(){
            Log.i(TAG, "run: id============"+Thread.currentThread().getId());
            try {

                SocketAddress ipe = new InetSocketAddress("192.168.1.208",9100);
                socket = new Socket();  //Socket(ipaddress, netport,true);
                socket.connect(ipe);
                socket.setSoTimeout(300);
                //socket.SendTimeout = Net_SendTimeout;

                Log.i(TAG, "connect: 连接成功=============");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
