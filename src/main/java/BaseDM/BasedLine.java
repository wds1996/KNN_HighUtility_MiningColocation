package BaseDM;

import BaseClass.Instance;
import utils.Load_DataSet;

import java.util.*;

public class BasedLine {
	/**
	 * ����k_NI�õ���ģʽ���ж��Ƿ��Ǹ�Ч�õ�ģʽ
	 * @param threshold
	 * @param k_NI
	 */
	
	private static Map<String, List<String>> highUtilityPattern = new HashMap<>(); //�洢��Ч�õĺ�ģʽ;
	public Map<String, List<String>> get_HighUtilityPattern(double threshold, int k, Map<Instance, List<Instance>> k_NI) {
		/*
		 * 1���õ���Ԫ�ص�k-NF
		 * 2������k-NF����2����k+1�׵�ģʽ
		 *   2��1����ÿ�����ɵ�ģʽ�ж���Ч����ֵ�Ƿ����threshold
		 *   2��2 k+1
		 * 3�����ɸ�Ч�õ�ģʽ
		 */
		Map<String, Set<String>> k_NFMap = new HashMap<>(); //������k���ڽ����������Դ˵õ���ѡģʽ�������ͷ�
		
		for (Instance instance : k_NI.keySet()) {
			Map<Instance, Set<String>> instance_kNF = new HashMap<>(); //ʵ����k���ڽ�������
			Set<String> featureSet = new TreeSet<>();
			int len = k_NI.get(instance).size();
			for(int i = 0; i < len; i++) {
				String temp = k_NI.get(instance).get(i).getFeature();
				featureSet.add(temp);
//				instance_kNF.put(instance, featureSet);
			}
			//����Ķ���
			instance_kNF.put(instance, featureSet);
			//������
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
		
		//����
		/*
		for (String c : k_NF.keySet()) {
			System.out.println("��Ԫ��" + c + "��" + k + "����������Ϊ��" + k_NF.get(c));
		}*/
		
		//˳�����ռ�ģʽ
		CandidataPattern cPattern = new CandidataPattern();
		Map<String, List<String>> candidataPattern= new HashMap<>();
		candidataPattern = cPattern.candidataPattern(k_NF,k);
		 
		for (String c : k_NF.keySet()) { //��ɺ�ģʽ
			System.out.println("��Ԫ��" + c + "�ĺ�ѡģʽ�ĸ���:" + candidataPattern.get(c).size());
			
			Map<Instance, List<Instance>> instanceMap = new HashMap<>(); //�ݴ�ú�ģʽ�Ĳ���ʵ����
			for (Instance instance : k_NI.keySet()) {
				if (instance.getFeature().equals(c)) {
					instanceMap.put(instance, k_NI.get(instance));
					//System.out.println(instanceMap);
				}
			}
			//�ж��Ƿ��Ч
			isUtilityPattern(threshold, c, candidataPattern.get(c), instanceMap);
		}
		return highUtilityPattern;

	}
	
	//�õ�2-k+1��ģʽ��Ч�ö�
	public static void isUtilityPattern(double threshold, String c, List<String> candidataPattern, Map<Instance, List<Instance>> instanceMap) {
		Map<String, Double> featureUtilityMap = Load_DataSet.featureUtilityMap;

		int n = candidataPattern.size();//��ȡ��ǰ��ģʽ�ĸ���
		for(int i = 0; i < n; i++) { //��ÿ��ģʽ��ʼ�ж�
			String pattern = candidataPattern.get(i);//�õ�һ��ģʽ
			Set<Instance>  tableInstance = new HashSet<>();//��Ÿ�ģʽ�ĺ˱�ʵ��
			//��ȡ���׵ĺ˱�ʵ��
			if (pattern.length() == 1) {
				for (Instance instance : instanceMap.keySet()) {
					//�õ�k-NI��ÿ��ʵ��
					for(int j = 0; j < instanceMap.get(instance).size(); j++) {
						if (instanceMap.get(instance).get(j).getFeature().equals(pattern)) {
							//��ʵ�����ڴ�ģʽ�ĺ˱�ʵ��
							tableInstance.add(instanceMap.get(instance).get(j));
						}
					}
					
				}
			}else {
				//��ȡ���׼����ϵĺ˱�ʵ��
				for (Instance instance : instanceMap.keySet()) {
					String fString = "";
					int temp = 0; //��Ԫ�ص�ʵ��֧�����ģʽ
					for(int j = 0; j < instanceMap.get(instance).size(); j++) {
						fString = fString + instanceMap.get(instance).get(j).getFeature();
					}
					
					for(int k = 0; k < pattern.length(); k++) {
						String string = pattern.charAt(k) + "";
						if (fString.contains(string)) {
							temp++;
						}
					}
					//ƥ����ʱ
					if (temp == pattern.length()) {
						for(int j = 0; j < instanceMap.get(instance).size(); j++) {
							if (pattern.contains(instanceMap.get(instance).get(j).getFeature())) {
								//һ���Ը�ģʽ�ж��Ƿ��д�����
								tableInstance.add(instanceMap.get(instance).get(j));
							}
						}
					}
				}
			
			}
			//���ڴ��ں˱�ʵ���ĺ�ѡģʽ����ģʽ��Ч�ö�
			if (!tableInstance.isEmpty()) {
				//���ڲ��յĶ�������ģʽ���ж��Ƿ��Ǹ�Ч�õ�ģʽ
				double patternUI = 0; //ģʽ��Ч�ö�
				if (pattern.length() == 1) {
					//����Ч�ö�,Set����ǿforѭ��
					double utility = 0;  //ʵ������Ч��
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
					//����������feature��ƽ��
					double featureNumUi = 0; //ģʽ��ÿ��������Ч�ö�֮��
					for(int j = 0; j < pattern.length(); j++) {
						double utility = 0;
						double featureUtility = 0; //һ��������Ч�ö�
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
					//������ֵ��ģʽ
					if (patternUI >= threshold) {
						System.out.println(pattern+":"+patternUI);
						highUtilityPattern.get(c).add(pattern);
					}
//					System.out.println("ģʽ" + pattern + "��Ч�ö�Ϊ" + patternUI);
				}
				
			}
		}

	}
	
}
