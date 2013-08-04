package com.sc.codemarathon.length;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
//import org.apache.commons.lang3.StringUtils;

public class CalEngine {

	private String inputPath;
	private String outputPath;
	private HashMap<String, Float> convert;
	private ArrayList<Float> results;
	
	public CalEngine(String inputPath, String outputPath) {
		this.inputPath = inputPath;
		this.outputPath = outputPath;
		this.convert = new HashMap<String, Float>();
		this.results = new ArrayList<Float>();
	}
	
	public void process() throws Exception {
		File f = new File(this.inputPath);  
		InputStreamReader read = new InputStreamReader (new FileInputStream(f), "UTF-8");  
		BufferedReader reader = new BufferedReader(read);  
		String line;  
		while ((line = reader.readLine()) != null) { 
			if(line.contains("=")) {
				//String[] fs = StringUtils.splitPreserveAllTokens(line, " ");
				String[] fs = line.split(" ");
				//System.out.println(fs[4]);
				if("foot".equalsIgnoreCase(fs[1])) {
					this.convert.put(fs[1], Float.valueOf(fs[3]));
					this.convert.put("feet", Float.valueOf(fs[3]));
				} else if("inch".equalsIgnoreCase(fs[1])) {
					this.convert.put(fs[1], Float.valueOf(fs[3]));
					this.convert.put("inches", Float.valueOf(fs[3]));
				} else {
					this.convert.put(fs[1], Float.valueOf(fs[3]));
					this.convert.put(fs[1] + "s", Float.valueOf(fs[3]));
				}
				continue;
			} 
			
			if(line.length() == 0) {
				continue;
			}
			//System.out.println(line);
			//String[] fs = StringUtils.splitPreserveAllTokens(line, " ");
			String[] fs = line.split(" ");
			Float curValue = .0F;
			//System.out.println(curValue);
			//Element elt = new Element("+", Float.valueOf(fs[0]), fs[1]);
			curValue = calOp(curValue, "+", Float.valueOf(fs[0]), fs[1]);
			if(fs.length > 2) {
				for(int i = 2; i < fs.length; i += 3) {
					curValue = calOp(curValue, fs[i], Float.valueOf(fs[i + 1]), fs[i + 2]);
				}
			}
			//System.out.println(curValue);
			this.results.add(curValue);
			
		}
		reader.close();
		read.close();
		
	}
	
	private Float calOp(Float lf, String op, Float rht, String unit) {
		Float rst = .0F;
		if(op.equals("+")) {
			rst = lf + rht * this.convert.get(unit);
		} else if(op.equals("-")) {
			rst = lf - rht * this.convert.get(unit);
		}
		
		return rst;
	}
	
	public void output() throws Exception {
	  	FileOutputStream outFile = null;
    	OutputStreamWriter outWt = null;
    	try{
    		outFile = new FileOutputStream(new File(this.outputPath));
    		outWt = new OutputStreamWriter(outFile, "UTF-8");
    		
    		outWt.write("luzhangyi319@126.com\n\n");
    		for(int i = 0; i < this.results.size(); i++) {
    			if(i != this.results.size() - 1) {
    				outWt.write(String.format("%.2f", this.results.get(i)) + " m\n");
    			} else {
    				outWt.write(String.format("%.2f", this.results.get(i)) + " m");
    			}
    		}
    		outWt.flush();
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		outWt.close();
    		outFile.close();
    	}
	}
	
//	class Element {
//		String operator;
//		Float value;
//		String unit;
//		
//		public Element(String operator, Float value, String unit) {
//			this.operator = operator;
//			this.value = value;
//			this.unit = unit;
//		}
//		
//		public String getOperator() {
//			return this.operator;
//		}
//		
//		public Float getValue() {
//			return this.value;
//		}
//		
//		public String getUnit() {
//			return this.unit;
//		}
//		
//	}
	public static void main(String[] args){
		CalEngine inst = new CalEngine("C:/codeM/length/input.txt"
				, "C:/codeM/length/output.txt");
		
		try {
			inst.process();
			inst.output();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
