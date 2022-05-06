package OptimizedDM;

import BaseClass.Instance;
import BaseDM.BasedLine;
import utils.Load_DataSet;
import java.util.*;

public class Pruning {
	/**
	 * 根据k_NI得到核模式并判断是否是高效用的模式
	 * 
	 * @param threshold
	 * @param k_NI
	 */

	static Map<String, List<String>> highUtilityPattern = new HashMap<>(); // 存储高效用的核模式;

	public void get_HighUtilityPattern(double threshold, int k, Map<Instance, List<Instance>> k_NI) {
		/*
		 * 1、得到核元素的k-NF
		 * 2、根据k-NF生成2——k+1阶的模式
		 * 	 2、1对于每个生成的模式判断其效用阈值是否大于threshold
		 * 	 2、2 k+1
		 * 3、生成高效用的模式
		 */
		Map<String, Set<String>> k_NFMap = new HashMap<>(); // 特征的k阶邻近特征集，以此得到候选模式，用完释放

		for (Instance instance : k_NI.keySet()) {
			Map<Instance, Set<String>> instance_kNF = new HashMap<>(); // 实例的k阶邻近特征集
			Set<String> featureSet = new TreeSet<>();
			for (int i = 0; i < k_NI.get(instance).size(); i++) {
				String temp = k_NI.get(instance).get(i).getFeature();
				featureSet.add(temp);
				instance_kNF.put(instance, featureSet);
			}

			String feature = instance.getFeature();
			if (!k_NFMap.containsKey(feature)) {
				Set<String> set = new TreeSet<>();
				set.addAll(featureSet);
				k_NFMap.put(feature, set);

			} else {
				k_NFMap.get(feature).addAll(featureSet);
			}
		}
		
		Map<String, List<String>> k_NF = new HashMap<>();
		for (String c : k_NFMap.keySet()) {
			List<String> list = new ArrayList<>();
			list.addAll(k_NFMap.get(c));
			k_NF.put(c, list);
		}
		k_NFMap.clear();

		// 测试
		for (String c : k_NF.keySet()) { 
			System.out.println("核元素" + c + "的" + k + "近邻特征集为：" + k_NF.get(c)); 
			}

		// 顺序树收集模式
		CandidataPattern cPattern = new CandidataPattern();
		Map<String, List<String>> candidatePatternmMap = new HashMap<>();
		candidatePatternmMap = cPattern.candidataPattern(k_NF, k);
		
		for (String c : k_NF.keySet()) { // 组成核模式
			List<String> k_NS = new ArrayList<String>(); // 存放k-NS,去除那些不存在该模式和它的超集的模式
			Map<Instance, List<Instance>> instanceMap = new HashMap<>(); // 暂存该核模式的核实例
			Map<String, Double> onefeature = new HashMap<>(); //存放一阶模式的效用度
			
			
			for (Instance instance : k_NI.keySet()) {
				if (instance.getFeature().equals(c)) {
					instanceMap.put(instance, k_NI.get(instance));
				}
			}

			for (Instance instance : instanceMap.keySet()) { // 组合模式,排序
				String string = "";
				Set<String> set = new TreeSet<>();
				for (int j = 0; j < instanceMap.get(instance).size(); j++) {
					String feature = instanceMap.get(instance).get(j).getFeature();
					set.add(feature);
				}

				for (String s : set) {
					string += s;
				}
				k_NS.add(string);
			}

			// 存放核表实例不为空的模式
			List<String> candidatePattern = new ArrayList<>();
			System.out.println("筛选前的模式个数" + candidatePatternmMap.get(c).size());
			
			double tempUi = 0;  //如果所有的低阶的特征效用度都小于阈值直接跳出循环
			for (String pattern : candidatePatternmMap.get(c)) { // 拿到属于c的每一个核候选
				if (pattern.length() == 1) { 
					Set<Instance> cInstance = new HashSet<>();
					//求出一阶模式的效用度
					for (Instance instance : instanceMap.keySet()) {
						for (int i = 0; i < instanceMap.get(instance).size(); i++) {
							Instance instance2 = instanceMap.get(instance).get(i);//拿到核元素的k-NI
							if (instance2.getFeature().equals(pattern)) {
								cInstance.add(instance2);
							}
						}
					}
					// 对每个模式调用效用度函数
					double patternUI = getUtilityPattern(cInstance, pattern);
					onefeature.put(pattern, patternUI);
					if (patternUI > tempUi) {
						tempUi = patternUI;
					}
					
					if (patternUI >= threshold) {
						if (!highUtilityPattern.containsKey(c)) {
							List<String> patternliList = new ArrayList<String>();
							patternliList.add(pattern);
							highUtilityPattern.put(c, patternliList);
						} else {
							highUtilityPattern.get(c).add(pattern);
						}
					}
					
				}else {//如果都小于效用度阈值直接跳出循环
					if (tempUi < threshold) { //一阶的都不满足
						break;
					}
					
					// 从二阶开始把不存在的模式去除
					for (int i = 0; i < k_NS.size(); i++) { // 对比
						int temp = 0;
						for (int j = 0; j < pattern.length(); j++) {
							String s = pattern.charAt(j) + "";
							if (k_NS.get(i).contains(s)) {
								temp++;
							}
						}
						if (temp == pattern.length()) {
							candidatePattern.add(pattern);
							break;
						} 
					}
				}

			}
			System.out.println("筛选后的核模式个数：" + candidatePattern.size());
			k_NS.clear();
//			candidatePatternmMap.clear();
			
			//遍历候选判断效用
			for (int i = 0; i < candidatePattern.size(); i++) {
				Set<Instance> cInstance = new HashSet<>(); // 存储当前模式的核表实例
				String pattern = candidatePattern.get(i);  //获得模式
				
				double patternUtility = 0;
				double UI = 0;
				for (int j = 0; j < pattern.length(); j++) {
					String feature = pattern.charAt(j) + "";
					double utility = onefeature.get(feature);
					patternUtility += utility;
					UI = patternUtility / pattern.length();
				}
				
				if (UI >= threshold) { // 获取三阶及以上的核表实例
					// 再优化，拿到k-1模式的效用度，判断是否有子模式，如果有的话跟1阶的特征效用度求平均值大于阈值的时候再查找核表实例
					for (Instance instance : instanceMap.keySet()) {
						String fString = "";
						int temp = 0; // 核元素的实例支撑这个模式
						for (int j = 0; j < instanceMap.get(instance).size(); j++) {
							fString = fString + instanceMap.get(instance).get(j).getFeature();
						}

						for (int m = 0; m < pattern.length(); m++) {
							String string = pattern.charAt(m) + "";
							if (fString.contains(string)) {
								temp++;
							}
						}
						// 匹配上时
						if (temp == pattern.length()) {
							for (int j = 0; j < instanceMap.get(instance).size(); j++) {
								if (pattern.contains(instanceMap.get(instance).get(j).getFeature())) {
									// 一样对该模式判断是否有此特征
									cInstance.add(instanceMap.get(instance).get(j));
								}
							}
						}
					}
				}
				if (!cInstance.isEmpty()) {
					double patternUI = getUtilityPattern(cInstance, pattern);
					if (patternUI >= threshold) {
						highUtilityPattern.get(c).add(pattern);
						
					}else {
						//对该模式将剩余的模式的所有超集求平均剪去
						for (int j = i+1; j < candidatePattern.size(); j++) {
							String pattern2 = candidatePattern.get(j);
							//求差集
							int temp = 0;
							for (int n = 0; n < pattern.length(); n++) {
								String s = pattern.charAt(n) + "";
								if (pattern2.contains(s)) {
									temp ++;
								}
							}
							if (temp == pattern.length()) { //pattern2是pattern的超集,求差集
								
								Set<String> feature = new HashSet<>();
								for (int m = 0; m < pattern2.length(); m++) {
									String s2 = pattern2.charAt(m) + "";
									if (!pattern.contains(s2)) {
										feature.add(s2);
									}
								}
								
								double tempUI = 0; //被剪枝的模式的效用度
								double featureUi = 0;
								for (String string : feature) {
									featureUi += onefeature.get(string);
								}
								tempUI = (patternUI + featureUi) / (feature.size()+1);
								if (tempUI < threshold) {
									candidatePattern.remove(j); //去除这个模式
								}
							}
						}
						
					}
				}
			}
			if (highUtilityPattern.get(c) != null) {
				System.out.println("核元素" + c + "的高效用模式个数:" + highUtilityPattern.get(c).size());
			}
			
		}
	}

	public double getUtilityPattern(Set<Instance> set, String pattern) {
		Map<String, Double> featureUtilityMap = Load_DataSet.featureUtilityMap; // 用的时候导入即可
	
		double patternUI = 0; // 模式的效用度
		if (pattern.length() == 1) {
			double utility = 0; // 实例的总效用
			for (Instance instance : set) {
				utility += instance.getU();
			}
			patternUI = utility / featureUtilityMap.get(pattern);
		} else {
			// 二阶以上求feature的平均
			patternUI = getPatternUI(set, pattern, featureUtilityMap);
		}
		return patternUI;
	}

	public static double getPatternUI(Set<Instance> set, String pattern, Map<String, Double> featureUtilityMap) {
		double patternUI;
		double patternUI = BasedLine.getPatternUI(featureUtilityMap, pattern, set);
		return patternUI;
	}

}
