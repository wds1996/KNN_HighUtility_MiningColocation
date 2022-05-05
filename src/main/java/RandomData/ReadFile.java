package RandomData;

import java.io.*;

public class ReadFile {
	/**
	 * 将晓璇师姐的数据处理成我的格式
	 * @param args
	 * @throws IOException 
	 */
	public void read(String path) throws IOException {
		File file = new File(path);
		FileReader fReader = new FileReader(file);
		BufferedReader bf = new BufferedReader(fReader);
		String filename = "Px.txt";
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
			if (feture.equals("P")) {
				String[] content = new String[4];
				content[0] = feture;
				content[1] = instance[1];
				content[2] = instance[2];
				content[3] = instance[3];
				for (int i = 0; i < content.length; i++) {
					if (i == 2) {
						bufWrite.write(content[i] + "\r\n");
					}
				}
			}
			
		}
			//关闭流
		fReader.close();
		bufWrite.close(); 
		outWriter.close();
		out.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile readFile = new ReadFile();
		System.out.println("开始写入文件：");
		try {
			readFile.read("beijingPOI2w.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
