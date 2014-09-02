package com.xuanzhui.converter.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class CurrencyConverterService {
	public static double getRate(String curCodeFrom, String curCodeTo){
		String urlreq="http://download.finance.yahoo.com/d/quotes.csv?s="+curCodeFrom+curCodeTo+"=X&f=l1&e=.cs";
		
		URL url;
		float rate;
		try {
			url = new URL(urlreq);
			InputStream ins=url.openStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(ins));

			String rateStr=br.readLine();

			ins.close();
			br.close();
			
			rate=Float.valueOf(rateStr);

		} catch (MalformedURLException e) {
			rate=0;
		} catch (IOException e) {
			rate=0;
		}
		
		return rate;
	}
	
	public static double convert(String curCodeFrom, String curCodeTo, double convAmt){
		double res=0;
		if (convAmt==0)
			res = 0;
		
		res=convAmt*getRate(curCodeFrom,curCodeTo);
		
		return res;
	}
}
