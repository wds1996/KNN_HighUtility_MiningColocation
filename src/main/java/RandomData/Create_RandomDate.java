package RandomData;

import java.io.*;

public class Create_RandomDate {
	/**
	 * 随机生成1000*1000的数据集
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filename = "2wx.txt";
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			FileOutputStream out = new FileOutputStream(filename);
			OutputStreamWriter outWriter = new OutputStreamWriter(out);
			BufferedWriter bufWrite = new BufferedWriter(outWriter);
			String[] content = getStringArray();
			for (int i = 0; i < content.length; i++) {
				bufWrite.write(content[i] + "\r\n");
			}
			//关闭流
			bufWrite.close(); 
			outWriter.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取" + filename + "出错！");
		}	
	}

	//先得到一个特征，特征实例的字符串数组，再一行一行的写出
	public static String[] getStringArray() {
		/**
		 * feature:20个，A-T, 范围2000*2000
		 * instance:40000 
		 */
		String[] instance = new String [20000];//一行一个实例
		
		for(int i = 0; i < 1000; i++) {
			String feature = "A," + "A"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //A效用值0-199
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "B," + "B"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //B效用值0-99
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+1000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "C," + "C"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-49
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+2000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "D," + "D"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-99
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+3000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "E," + "E"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-599
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+4000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "F," + "F"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+5000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "G," + "G"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-799
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+6000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "H," + "H"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-149
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+7000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "I," + "I"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-29
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+8000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "J," + "J"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-98
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+9000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "K," + "K"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-98
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+10000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "L," + "L"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-399
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+11000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "M," + "M"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-799
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+12000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "N," + "N"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-59
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+13000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "O," + "O"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-4999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+14000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "P," + "P"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-199
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+15000] = feature;
		}
		for(int i = 0; i <1000; i++) {
			String feature = "Q," + "Q"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值9999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+16000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "R," + "R"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-99999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+17000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "S," + "S"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-299
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+18000] = feature;
		}
		for(int i = 0; i < 1000; i++) {
			String feature = "T," + "T"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-999999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+19000] = feature;
		}
		return instance;
	}
}
