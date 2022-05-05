package RandomData;


import java.io.*;
import java.util.Random;

public class Addutility {
	/**
	 * 将北京的POI数据处理一下
	 * @param args
	 * @throws IOException 
	 */
	Random random = new Random();
	public void read(String path) throws IOException {
		File file = new File(path);
		FileReader fReader = new FileReader(file);
		BufferedReader bf = new BufferedReader(fReader);
		String filename = "utilityPOI.txt";
		File file2 = new File(filename);
		if (!file2.exists()) {
			file2.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(filename);
		OutputStreamWriter outWriter = new OutputStreamWriter(out);
		BufferedWriter bufWrite = new BufferedWriter(outWriter);
		
		String line;
		while((line=bf.readLine())!=null) {
			String [] instance = line.split("\\s+");
			String feture = instance[1];
			String[] content = new String[5];
			content[0] = instance[0];
			content[1] = instance[1];
			content[2] = instance[2];
			content[3] = instance[3];
			
			int u = 0;
			switch (feture) {
			case "A":
				u = get_random(1000, 100);
				break;
			case "B":
				u = get_random(2000, 1000);
				break;
			case "C":
				u = get_random(1000, 100);
				break;
			case "D":
				u = get_random(100, 10);
				break;
			case "E":
				u = get_random(100, 10);
				break;
			case "F":
				u = get_random(10, 1);
				break;
			case "G":
				u = get_random(1000, 100);
				break;
			case "H":
				u = get_random(1000, 100);
				break;
			case "I":
				u = get_random(50, 1);
				break;
			case "J":
				u = get_random(10, 1);
				break;
			case "K":
				u = get_random(10000, 1000);
				break;
			case "L":
				u = get_random(10000, 5000);
				break;
			case "M":
				u = get_random(200, 50);
				break;
			case "N":
				u = get_random(100, 5);
				break;
			case "O":
				u = get_random(100, 20);
				break;
			case "P":
				u = get_random(50, 5);	
				break;
			default:
				System.out.println("不存在特征" + feture);
			}
			content[4] = u + "";
			for (int i = 0; i < content.length; i++) {
				if (i != content.length-1) {
					bufWrite.write(content[i] + "\t");
				}else {
					bufWrite.write(content[i] + "\r\n");
				}
				
			}
		}
			//关闭流
		fReader.close();
		bufWrite.close(); 
		outWriter.close();
		out.close();
	}
	
	public int get_random(int max, int min) {
		int s = random.nextInt(max)%(max-min) + min;
		return s;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Addutility add = new Addutility();
		System.out.println("开始写入文件：");
		try {
			add.read("POI2w.txt");
			System.out.println("完成！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}