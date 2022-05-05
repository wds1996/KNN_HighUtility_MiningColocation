package RandomData;

import java.io.*;

/**
 * ���ҵ����ݴ���ɽ�����ĸ�ʽ
 * @author ASUS
 *
 */
public class ReadFile2 {
	public void read(String path) throws IOException {
		File file = new File(path);
		FileReader fReader = new FileReader(file);
		BufferedReader bf = new BufferedReader(fReader);
		String filename = "beijingPOI-UPI-UPI.data"; //����ļ���
		File file2 = new File(filename);
		if (!file2.exists()) {
			file2.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(filename);
		OutputStreamWriter outWriter = new OutputStreamWriter(out);
		BufferedWriter bufWrite = new BufferedWriter(outWriter);
		
		String line;
		while((line=bf.readLine())!=null) {
			String [] strings = line.split(",");
			String feture = strings[0];
			//������ʵ��
			String instance = strings[1].substring(1);
			double x = Double.parseDouble(strings[2]);
			double y = Double.parseDouble(strings[3]);
			double u = Double.parseDouble(strings[4]);
			
			String[] temp = new String[6];
			temp[0] = feture;
			temp[1] = instance;
			temp[2] = x+"";
			temp[3] = y+"";
			temp[4] = 0.0+"";
			temp[5] = u+"";
			for (int i = 0; i < temp.length; i++) {
				bufWrite.write(temp[i] + "\t");
			}
			bufWrite.newLine();
		}
			//�ر���
		fReader.close();
		bufWrite.close(); 
		outWriter.close();
		out.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile2 readFile = new ReadFile2();
		System.out.println("��ʼд���ļ���");
		try {
			readFile.read("beijingPOI-U.txt");
			System.out.println("��ɣ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
