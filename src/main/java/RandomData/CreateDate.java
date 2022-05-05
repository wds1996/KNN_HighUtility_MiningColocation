package RandomData;

import java.io.*;

public class CreateDate {
	/**
	 * 随机生成范围2000*2000的数据集
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filename = "RandomDate2.txt";
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
		 * feature:20个，A-T
		 * instance:20000 A,4000 B,2620 C,1850 D,834 E,666 F,577 G,166 H,1210 I,385 J,660 K,621 I,1065
		 *   M,475 N,275 O,1750, P,450 Q,1130 R,266 S,990 T,10
		 */
		String[] instance = new String [20000];//一行一个实例
		
		for(int i = 0; i < 4000; i++) {
			String feature = "A," + "A"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100+1; //A效用值0-100
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i] = feature;
		}
		for(int i = 0; i < 2620; i++) {
			String feature = "B," + "B"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*200; //B效用值0-199
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+4000] = feature;
		}
		for(int i = 0; i <1850; i++) {
			String feature = "C," + "C"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值0-99
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+6620] = feature;
		}
		for(int i = 0; i < 834; i++) {
			String feature = "D," + "D"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*400; //C效用值0-399
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+8470] = feature;
		}
		for(int i = 0; i < 666; i++) {
			String feature = "E," + "E"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*300; //C效用值0-299
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+9304] = feature;
		}
		for(int i = 0; i < 577; i++) {
			String feature = "F," + "F"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*10000; //C效用值0-9999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+9970] = feature;
		}
		for(int i = 0; i < 166; i++) {
			String feature = "G," + "G"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*8000; //C效用值0-7999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+10547] = feature;
		}
		for(int i = 0; i < 1210; i++) {
			String feature = "H," + "H"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*1150; //C效用值0-1149
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+10713] = feature;
		}
		for(int i = 0; i < 385; i++) {
			String feature = "I," + "I"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*30000; //C效用值0-29999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+11923] = feature;
		}
		for(int i = 0; i < 660; i++) {
			String feature = "J," + "J"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*9999; //C效用值0-9998
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+12308] = feature;
		}
		for(int i = 0; i < 621; i++) {
			String feature = "K," + "K"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*7000; //C效用值0-6999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+12968] = feature;
		}
		for(int i = 0; i < 1065; i++) {
			String feature = "L," + "L"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*400; //C效用值0-399
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+13589] = feature;
		}
		for(int i = 0; i < 475; i++) {
			String feature = "M," + "M"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*8880; //C效用值0-799
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+14654] = feature;
		}
		for(int i = 0; i < 275; i++) {
			String feature = "N," + "N"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*66660; //C效用值0-66659
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+15129] = feature;
		}
		for(int i = 0; i < 1750; i++) {
			String feature = "O," + "O"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*50; //C效用值0-49
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+15404] = feature;
		}
		for(int i = 0; i < 450; i++) {
			String feature = "P," + "P"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*20000; //C效用值0-199
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+17154] = feature;
		}
		for(int i = 0; i < 1130; i++) {
			String feature = "Q," + "Q"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100; //C效用值99
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+17604] = feature;
		}
		for(int i = 0; i < 266; i++) {
			String feature = "R," + "R"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*100000; //C效用值0-99999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+18734] = feature;
		}
		for(int i = 0; i < 990; i++) {
			String feature = "S," + "S"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*30000; //C效用值0-299
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+19000] = feature;
		}
		for(int i = 0; i < 10; i++) {
			String feature = "T," + "T"+(i+1);
			double x = Math.random()*2000;
			double y = Math.random()*2000;
			double u = Math.random()*1000000; //C效用值0-999999
			feature = feature + "," + x + "," + y+ "," + u;
			instance[i+19990] = feature;
		}
		return instance;
	}
}
