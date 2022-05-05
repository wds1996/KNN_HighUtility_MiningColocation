package utils;
import BaseClass.Instance;

import java.util.*;
import java.util.Map.Entry;

public class Get_kNI {
	/**
	 * ����ʵ��֮��ľ��벢����
	 * @throws Exception 
	 */
	public Map<Instance,List<Instance>> get_kNI(int k, String[] cF, List<Instance> instanceList) throws Exception {
		Map<Instance,List<Instance>> k_NI = new HashMap<>(); //��ź�ʵ����k_NI
		
		for (int i = 0; i < cF.length; i++) { //��Ԫ�ظ���
			
			for (int j = 0; j < instanceList.size(); j++) {
				
				if (cF[i].equals(instanceList.get(j).getFeature())) {  //�����Ԫ�غ���������ʵ��֮��ľ���
					Instance instance1 = instanceList.get(j);
					
					Map<Instance, Double> map= new HashMap<>(); 
					for (int m = 0; m < instanceList.size(); m++) {
						
						if (!instanceList.get(j).getFeature().equals(instanceList.get(m).getFeature())) {//����ͬһ������
							Instance instance2 = instanceList.get(m);
							
							double distance = Math.sqrt(Math.pow((instance1.getX() - instance2.getX()), 2) 
									+ Math.pow((instance1.getY() - instance2.getY()), 2));
							map.put(instance2, distance);
						}
					}
					
					//����
					/*
					System.out.println("��ʵ��" + instance1 + "��k-NI:");
					for (Instance instance : map.keySet()) {
						System.out.println(instance + "��" + map.get(instance));
					}*/
					
					List<Instance> sortList = sortInstanceValue(map);//��ֵ����
					
					if (k > map.size()) {
						throw new Exception("kֵ����");
					}
					for (int l = 0; l < k; l++) {
						if (!k_NI.containsKey(instance1)) {
							List<Instance> list = new ArrayList<>();
							list.add(sortList.get(l));
							k_NI.put(instance1,list);
						}else {
							k_NI.get(instance1).add(sortList.get(l));
						}
					}
				}
			}
		}
		return k_NI;
 }
	
	public static  List<Instance> sortInstanceValue(Map<Instance, Double> map) {
		/**
		 * map����value����
		 */
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<Instance> sortList = new ArrayList<>();
        List<Entry<Instance, Double>> entryList = new LinkedList<Entry<Instance, Double>>(
                map.entrySet());
        Collections.sort(entryList, new Comparator<Entry<Instance, Double>>() {
        	 @Override
 	        public int compare(Entry<Instance, Double> o1, Entry<Instance, Double> o2) {
 	            //���մ�С�����˳������
 	            return o1.getValue().compareTo(o2.getValue());
 	        }
        });
 
        Iterator<Entry<Instance, Double>> iter = entryList.iterator();
        Entry<Instance, Double> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortList.add(tmpEntry.getKey());
        }
        return sortList;
    }
}
