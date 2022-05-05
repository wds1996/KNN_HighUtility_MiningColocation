package BaseDM;

import BaseClass.Instance;
import utils.Load_DataSet;

import java.util.*;

public class BasedLine {
	/**
	 * 根据k_NI得到核模式并判断是否是高效用的模式
	 * @param threshold
	 * @param k_NI
	 */
	
	private static Map<String, List<String>> highUtilityPattern = new HashMap<>(); //存储高效用的核模式;
	public Map<String, List<String>> get_HighUtilityPattern(double threshold, int k, Map<Instance, List<Instance>> k_NI) {
		/*
		 * 1、得到核元素的k-NF
		 * 2、根据k-NF生成2——k+1阶的模式
		 *   2、1对于每个生成的模式判断其效用阈值是否大于threshold
		 *   2、2 k+1
		 * 3、生成高效用的模式
		 */
		Map<String, Set<String>> k_NFMap = new HashMap<>(); //特征的k阶邻近特征集，以此得到候选模式，用完释放
		
		for (Instance instance : k_NI.keySet()) {
			Map<Instance, Set<String>> instance_kNF = new HashMap<>(); //实例的k阶邻近特征集
			Set<String> featureSet = new TreeSet<>();
			int len = k_NI.get(instance).size();
			for(int i = 0; i < len; i++) {
				String temp = k_NI.get(instance).get(i).getFeature();
				featureSet.add(temp);
//				instance_kNF.put(instance, featureSet);
			}
			//这里改动了
			instance_kNF.put(instance, featureSet);
			//到这里
			String feature = instance.getFeature();
			if (!k_NFMap.containsKey(feature)) {
				Set<String> set = new TreeSet<>(featureSet);
				k_NFMap.put(feature, set);
				
			}else {
				k_NFMap.get(feature).addAll(featureSet);
			}
		}
		Map<String, List<String>> k_NF = new HashMap<>();
		for (String c : k_NFMap.keySet()) {
			List<String> list = new ArrayList<>(k_NFMap.get(c));
			k_NF.put(c,list);
		}
		k_NFMap.clear();
		
		//测试
		/*
		for (String c : k_NF.keySet()) {
			System.out.println("核元素" + c + "的" + k + "近邻特征集为：" + k_NF.get(c));
		}*/
		
		//顺序树收集模式
		CandidataPattern cPattern = new CandidataPattern();
		Map<String, List<String>> candidataPattern= new HashMap<>();
		candidataPattern = cPattern.candidataPattern(k_NF,k);
		 
		for (String c : k_NF.keySet()) { //组成核模式
			System.out.println("核元素" + c + "的候选模式的个数:" + candidataPattern.get(c).size());
			
			Map<Instance, List<Instance>> instanceMap = new HashMap<>(); //暂存该核模式的参与实例集
			for (Instance instance : k_NI.keySet()) {
				if (instance.getFeature().equals(c)) {
					instanceMap.put(instance, k_NI.get(instance));
					//System.out.println(instanceMap);
				}
			}
			//判断是否高效
			isUtilityPattern(threshold, c, candidataPattern.get(c), instanceMap);
		}
		return highUtilityPattern;

	}
	
	//得到2-k+1阶模式的效用度
	public static void isUtilityPattern(double threshold, String c, List<String> candidataPattern, Map<Instance, List<Instance>> instanceMap) {
		Map<String, Double> featureUtilityMap = Load_DataSet.featureUtilityMap;

		int n = candidataPattern.size();//获取当前核模式的个数
		for(int i = 0; i < n; i++) { //从每个模式开始判断
			String pattern = candidataPattern.get(i);//得到一个模式
			Set<Instance>  tableInstance = new HashSet<>();//存放该模式的核表实例
			//获取二阶的核表实例
			if (pattern.length() == 1) {
				for (Instance instance : instanceMap.keySet()) {
					//拿到k-NI中每个实例
					for(int j = 0; j < instanceMap.get(instance).size(); j++) {
						if (instanceMap.get(instance).get(j).getFeature().equals(pattern)) {
							//该实例属于此模式的核表实例
							tableInstance.add(instanceMap.get(instance).get(j));
						}
					}
					
				}
			}else {
				//获取三阶及以上的核表实例
				for (Instance instance : instanceMap.keySet()) {
					String fString = "";
					int temp = 0; //核元素的实例支撑这个模式
					for(int j = 0; j < instanceMap.get(instance).size(); j++) {
						fString = fString + instanceMap.get(instance).get(j).getFeature();
					}
					
					for(int k = 0; k < pattern.length(); k++) {
						String string = pattern.charAt(k) + "";
						if (fString.contains(string)) {
							temp++;
						}
					}
					//匹配上时
					if (temp == pattern.length()) {
						for(int j = 0; j < instanceMap.get(instance).size(); j++) {
							if (pattern.contains(instanceMap.get(instance).get(j).getFeature())) {
								//一样对该模式判断是否有此特征
								tableInstance.add(instanceMap.get(instance).get(j));
							}
						}
					}
				}
			
			}
			//对于存在核表实例的候选模式计算模式的效用度
			if (!tableInstance.isEmpty()) {
				//对于不空的二阶以上模式，判断是否是高效用的模式
				double patternUI = 0; //模式的效用度
				if (pattern.length() == 1) {
					//计算效用度,Set用增强for循环
					double utility = 0;  //实例的总效用
					for (Instance instance2: tableInstance) {
						utility += instance2.getU();
					}
					patternUI = utility / featureUtilityMap.get(pattern);
					if (patternUI >= threshold) {
						System.out.println(pattern+":"+patternUI);
						if (!highUtilityPattern.containsKey(c)) {
							List<String> uList = new ArrayList<>();
							uList.add(pattern);
							highUtilityPattern.put(c, uList);
						}else {
							highUtilityPattern.get(c).add(pattern);
						}
					}
				}else {
					//二阶以上求feature的平均
					double featureNumUi = 0; //模式中每个特征的效用度之和
					for(int j = 0; j < pattern.length(); j++) {
						double utility = 0;
						double featureUtility = 0; //一个特征的效用度
						String feature = pattern.charAt(j) + "";
						
						for (Instance instance : tableInstance) {
							if (instance.getFeature().equals(feature)) {
								utility += instance.getU();
							}
						}
						featureUtility = utility / featureUtilityMap.get(feature);
						featureNumUi += featureUtility;
					}
					patternUI = featureNumUi / pattern.length();
					//大于阈值的模式
					if (patternUI >= threshold) {
						System.out.println(pattern+":"+patternUI);
						highUtilityPattern.get(c).add(pattern);
					}
//					System.out.println("模式" + pattern + "的效用度为" + patternUI);
				}
				
			}
		}

	}
	
}
