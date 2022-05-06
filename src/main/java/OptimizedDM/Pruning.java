package OptimizedDM;

import BaseClass.Instance;
import BaseDM.BasedLine;
import utils.Load_DataSet;
import java.util.*;

public class Pruning {
	/**
	 * ����k_NI�õ���ģʽ���ж��Ƿ��Ǹ�Ч�õ�ģʽ
	 * 
	 * @param threshold
	 * @param k_NI
	 */

	static Map<String, List<String>> highUtilityPattern = new HashMap<>(); // �洢��Ч�õĺ�ģʽ;

	public void get_HighUtilityPattern(double threshold, int k, Map<Instance, List<Instance>> k_NI) {
		/*
		 * 1���õ���Ԫ�ص�k-NF
		 * 2������k-NF����2����k+1�׵�ģʽ
		 * 	 2��1����ÿ�����ɵ�ģʽ�ж���Ч����ֵ�Ƿ����threshold
		 * 	 2��2 k+1
		 * 3�����ɸ�Ч�õ�ģʽ
		 */
		Map<String, Set<String>> k_NFMap = new HashMap<>(); // ������k���ڽ����������Դ˵õ���ѡģʽ�������ͷ�

		for (Instance instance : k_NI.keySet()) {
			Map<Instance, Set<String>> instance_kNF = new HashMap<>(); // ʵ����k���ڽ�������
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

		// ����
		for (String c : k_NF.keySet()) { 
			System.out.println("��Ԫ��" + c + "��" + k + "����������Ϊ��" + k_NF.get(c)); 
			}

		// ˳�����ռ�ģʽ
		CandidataPattern cPattern = new CandidataPattern();
		Map<String, List<String>> candidatePatternmMap = new HashMap<>();
		candidatePatternmMap = cPattern.candidataPattern(k_NF, k);
		
		for (String c : k_NF.keySet()) { // ��ɺ�ģʽ
			List<String> k_NS = new ArrayList<String>(); // ���k-NS,ȥ����Щ�����ڸ�ģʽ�����ĳ�����ģʽ
			Map<Instance, List<Instance>> instanceMap = new HashMap<>(); // �ݴ�ú�ģʽ�ĺ�ʵ��
			Map<String, Double> onefeature = new HashMap<>(); //���һ��ģʽ��Ч�ö�
			
			
			for (Instance instance : k_NI.keySet()) {
				if (instance.getFeature().equals(c)) {
					instanceMap.put(instance, k_NI.get(instance));
				}
			}

			for (Instance instance : instanceMap.keySet()) { // ���ģʽ,����
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

			// ��ź˱�ʵ����Ϊ�յ�ģʽ
			List<String> candidatePattern = new ArrayList<>();
			System.out.println("ɸѡǰ��ģʽ����" + candidatePatternmMap.get(c).size());
			
			double tempUi = 0;  //������еĵͽ׵�����Ч�öȶ�С����ֱֵ������ѭ��
			for (String pattern : candidatePatternmMap.get(c)) { // �õ�����c��ÿһ���˺�ѡ
				if (pattern.length() == 1) { 
					Set<Instance> cInstance = new HashSet<>();
					//���һ��ģʽ��Ч�ö�
					for (Instance instance : instanceMap.keySet()) {
						for (int i = 0; i < instanceMap.get(instance).size(); i++) {
							Instance instance2 = instanceMap.get(instance).get(i);//�õ���Ԫ�ص�k-NI
							if (instance2.getFeature().equals(pattern)) {
								cInstance.add(instance2);
							}
						}
					}
					// ��ÿ��ģʽ����Ч�öȺ���
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
					
				}else {//�����С��Ч�ö���ֱֵ������ѭ��
					if (tempUi < threshold) { //һ�׵Ķ�������
						break;
					}
					
					// �Ӷ��׿�ʼ�Ѳ����ڵ�ģʽȥ��
					for (int i = 0; i < k_NS.size(); i++) { // �Ա�
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
			System.out.println("ɸѡ��ĺ�ģʽ������" + candidatePattern.size());
			k_NS.clear();
//			candidatePatternmMap.clear();
			
			//������ѡ�ж�Ч��
			for (int i = 0; i < candidatePattern.size(); i++) {
				Set<Instance> cInstance = new HashSet<>(); // �洢��ǰģʽ�ĺ˱�ʵ��
				String pattern = candidatePattern.get(i);  //���ģʽ
				
				double patternUtility = 0;
				double UI = 0;
				for (int j = 0; j < pattern.length(); j++) {
					String feature = pattern.charAt(j) + "";
					double utility = onefeature.get(feature);
					patternUtility += utility;
					UI = patternUtility / pattern.length();
				}
				
				if (UI >= threshold) { // ��ȡ���׼����ϵĺ˱�ʵ��
					// ���Ż����õ�k-1ģʽ��Ч�öȣ��ж��Ƿ�����ģʽ������еĻ���1�׵�����Ч�ö���ƽ��ֵ������ֵ��ʱ���ٲ��Һ˱�ʵ��
					for (Instance instance : instanceMap.keySet()) {
						String fString = "";
						int temp = 0; // ��Ԫ�ص�ʵ��֧�����ģʽ
						for (int j = 0; j < instanceMap.get(instance).size(); j++) {
							fString = fString + instanceMap.get(instance).get(j).getFeature();
						}

						for (int m = 0; m < pattern.length(); m++) {
							String string = pattern.charAt(m) + "";
							if (fString.contains(string)) {
								temp++;
							}
						}
						// ƥ����ʱ
						if (temp == pattern.length()) {
							for (int j = 0; j < instanceMap.get(instance).size(); j++) {
								if (pattern.contains(instanceMap.get(instance).get(j).getFeature())) {
									// һ���Ը�ģʽ�ж��Ƿ��д�����
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
						//�Ը�ģʽ��ʣ���ģʽ�����г�����ƽ����ȥ
						for (int j = i+1; j < candidatePattern.size(); j++) {
							String pattern2 = candidatePattern.get(j);
							//��
							int temp = 0;
							for (int n = 0; n < pattern.length(); n++) {
								String s = pattern.charAt(n) + "";
								if (pattern2.contains(s)) {
									temp ++;
								}
							}
							if (temp == pattern.length()) { //pattern2��pattern�ĳ���,��
								
								Set<String> feature = new HashSet<>();
								for (int m = 0; m < pattern2.length(); m++) {
									String s2 = pattern2.charAt(m) + "";
									if (!pattern.contains(s2)) {
										feature.add(s2);
									}
								}
								
								double tempUI = 0; //����֦��ģʽ��Ч�ö�
								double featureUi = 0;
								for (String string : feature) {
									featureUi += onefeature.get(string);
								}
								tempUI = (patternUI + featureUi) / (feature.size()+1);
								if (tempUI < threshold) {
									candidatePattern.remove(j); //ȥ�����ģʽ
								}
							}
						}
						
					}
				}
			}
			if (highUtilityPattern.get(c) != null) {
				System.out.println("��Ԫ��" + c + "�ĸ�Ч��ģʽ����:" + highUtilityPattern.get(c).size());
			}
			
		}
	}

	public double getUtilityPattern(Set<Instance> set, String pattern) {
		Map<String, Double> featureUtilityMap = Load_DataSet.featureUtilityMap; // �õ�ʱ���뼴��
	
		double patternUI = 0; // ģʽ��Ч�ö�
		if (pattern.length() == 1) {
			double utility = 0; // ʵ������Ч��
			for (Instance instance : set) {
				utility += instance.getU();
			}
			patternUI = utility / featureUtilityMap.get(pattern);
		} else {
			// ����������feature��ƽ��
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
