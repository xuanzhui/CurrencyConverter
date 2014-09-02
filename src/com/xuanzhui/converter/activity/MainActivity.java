package com.xuanzhui.converter.activity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.xuanzhui.converter.R;
import com.xuanzhui.converter.service.CurrencyConverterService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private String cycodeName="currencycode.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeWidgets();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void initializeWidgets(){
    	initializeBtnSubmit();
    	initializeConvertTip();
    	initializeSpinners();
    }
    
    public void initializeBtnSubmit(){
    	Button btnsubmit=(Button)findViewById(R.id.btnsubmit);
    	btnsubmit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View arg0) {
				Spinner spcodeFrom=(Spinner)findViewById(R.id.spcodeFrom);
		    	Spinner spcodeTo=(Spinner)findViewById(R.id.spcodeTo);
				
		    	String curCodeFrom=spcodeFrom.getSelectedItem().toString();
		    	String curCodeTo=spcodeTo.getSelectedItem().toString();
		    	
		    	curCodeFrom=curCodeFrom.substring(0, 3);
		    	curCodeTo=curCodeTo.substring(0, 3);
		    	
		    	EditText etconvAmt=(EditText)findViewById(R.id.etconvAmt);
		    	String convAmtStr=etconvAmt.getText().toString();
//		    	double convAmt=Double.valueOf(convAmtStr);
//		    	
//		    	double rate=CurrencyConverterService.getRate(curCodeFrom, curCodeTo);
//		    	
//		    	double convres=convAmt*rate;
//		    	
//		    	Toast.makeText(arg0.getContext(), 
//						"curCodeFrom : "+curCodeFrom+
//						"\tcurCodeTo : "+curCodeTo+
//						"\tconvAmt : "+convAmt+
//						"\trate : "+rate+
//						"\tconvres : "+convres, Toast.LENGTH_SHORT).show();			
				
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, ConvertResultActivity.class);
				
				intent.putExtra("curCodeFrom",curCodeFrom);
				intent.putExtra("curCodeTo",curCodeTo);
				intent.putExtra("convAmtStr",convAmtStr);

				startActivity(intent);
			}
    		
    	});
    }
    
    public void initializeConvertTip(){
    	TextView tv=(TextView)findViewById(R.id.tvconvertTip);
    	String tip=this.getString(R.string.convertAmt)+'('+
    			this.getString(R.string.currencyFrom)+')'+
    			'*'+this.getString(R.string.convertRate)+
    			'='+this.getString(R.string.resultTip)+'('+
    			this.getString(R.string.currencyTo)+')';
    	tv.setText(tip);
    }
    
    public void initializeSpinners(){
    	Spinner spcodeFrom=(Spinner)findViewById(R.id.spcodeFrom);
    	Spinner spcodeTo=(Spinner)findViewById(R.id.spcodeTo);
    	
    	List<String> cycode=readCurrencyCode();
    	
    	ArrayAdapter<String> adapterfrom = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, cycode);
    	adapterfrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spcodeFrom.setAdapter(adapterfrom);
    	//设置默认选中
    	spcodeFrom.setSelection(27, true);
    	
    	ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, cycode);
    	adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spcodeTo.setAdapter(adapterTo);
    	//设置默认选中
    	spcodeTo.setSelection(5, true);
    	
    	OnItemSelectedListener listener=new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(arg0.getContext(), 
						arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	};
    	
    	spcodeFrom.setOnItemSelectedListener(listener);
    	spcodeTo.setOnItemSelectedListener(listener);
    }
    
    private List<String> readCurrencyCode(){
    	List<String> cycode=new ArrayList<String>();
    	try {
			BufferedReader br=new BufferedReader(new InputStreamReader(getAssets().open(cycodeName),"UTF-8"));
			String line;
			while ((line=br.readLine())!=null)
				cycode.add(line);
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return cycode;
    }
}
