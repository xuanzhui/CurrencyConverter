package com.xuanzhui.converter.activity;

import com.xuanzhui.converter.R;
import com.xuanzhui.converter.service.CurrencyConverterService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConvertResultActivity extends Activity{

	private TextView tvrateTip;
	private TextView tvres;
	
	private Handler uihd=new Handler(){
        public void handleMessage (Message msg) {//此方法在ui线程运行
        	
        	Bundle bd=msg.getData();
        	tvrateTip.setText(bd.getDouble("rate")+"");
        	tvres.setText(bd.getDouble("convres")+"");
        	
        }
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_result);
    	tvrateTip=(TextView)findViewById(R.id.tvrateTip);
    	tvres=(TextView)findViewById(R.id.tvres);
        initializeWidgets();
    }
    
    public void initializeWidgets(){
    	initializeBtnRet();
    	setConvertResult();
    }
    
    public void initializeBtnRet(){
    	Button btnret=(Button)findViewById(R.id.btnret);
    	btnret.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
//				Intent intent=new Intent();
//				intent.setClass(ConvertResultActivity.this, MainActivity.class);
//				startActivity(intent);
				finish();
			}
    		
    	});
    }
    
    public void setConvertResult(){
    	Intent intent=this.getIntent();
    	final String curCodeFrom=intent.getStringExtra("curCodeFrom");
    	final String curCodeTo=intent.getStringExtra("curCodeTo");
    	String convAmtStr=intent.getStringExtra("convAmtStr");
    	final double convAmt=Double.valueOf(convAmtStr);
	
    	new Thread(new Runnable(){

			@Override
			public void run() {	
		    	
		    	double rate=CurrencyConverterService.getRate(curCodeFrom, curCodeTo);
//		    	tvrateTip.setText(rate+"");
		    	double convres=convAmt*rate;
//		    	tvres.setText(convres+"");
		    	
		    	Bundle bd=new Bundle();
		    	bd.putDouble("rate", rate);
		    	bd.putDouble("convres", convres);
		    	Message mes=uihd.obtainMessage();
		    	mes.setData(bd);
		    	mes.sendToTarget();
			}
    		
    	}).start();
    }
}
