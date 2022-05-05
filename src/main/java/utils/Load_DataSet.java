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

//加载数据集
public class Load_DataSet {

	public static List<Instance> instanceList; // 存放所有实例
	public static Map<String, Double> featureUtilityMap; // 存放特征和该特征的总效用

	public void readInstance(String path) throws IOException {
		/**
		 * 读取文件
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
			double u = Double.parseDouble(temp[4]);// 效用值
			
			instanceList.add(new Instance(feature, id, x, y, u));
			if (!featureUtilityMap.containsKey(feature)) {
				featureUtilityMap.put(feature, u);
			} else {
				featureUtilityMap.put(feature, featureUtilityMap.get(feature) + u);
			}
		}
		
		//测试
		System.out.println("特征数：" + featureUtilityMap.size());
		System.out.println("实例数：" + instanceList.size());
		/*
		for (String string : featureUtilityMap.keySet()) {
			System.out.println("特征" + string + "的总效用:" +  featureUtilityMap.get(string));
		}*/
		bReader.close();
	}

}
