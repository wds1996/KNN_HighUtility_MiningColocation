package utils;
import BaseClass.Instance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//�������ݼ�
public class Load_DataSet {

	public static List<Instance> instanceList; // �������ʵ��
	public static Map<String, Double> featureUtilityMap; // ��������͸���������Ч��

	public void readInstance(String path) throws IOException {
		/**
		 * ��ȡ�ļ�
		 */
		instanceList = new ArrayList<>();
		featureUtilityMap = new HashMap<>();

		FileReader fReader = new FileReader(new File(path));
		BufferedReader bReader = new BufferedReader(fReader);
		String line;
		while ((line = bReader.readLine()) != null) {
			String[] temp = line.split(",");
			String feature = temp[0];
			String id = temp[1];
			
			double x = Double.parseDouble(temp[2]);
			double y = Double.parseDouble(temp[3]);
			double u = Double.parseDouble(temp[4]);// Ч��ֵ
			
			instanceList.add(new Instance(feature, id, x, y, u));
			if (!featureUtilityMap.containsKey(feature)) {
				featureUtilityMap.put(feature, u);
			} else {
				featureUtilityMap.put(feature, featureUtilityMap.get(feature) + u);
			}
		}
		
		//����
		System.out.println("��������" + featureUtilityMap.size());
		System.out.println("ʵ������" + instanceList.size());
		/*
		for (String string : featureUtilityMap.keySet()) {
			System.out.println("����" + string + "����Ч��:" +  featureUtilityMap.get(string));
		}*/
		bReader.close();
	}

}
