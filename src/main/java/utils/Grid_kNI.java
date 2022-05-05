package utils;

import BaseClass.Instance;
import java.util.*;
import java.util.Map.Entry;

public class Grid_kNI {
	/**
	 * ������ķ�ʽ�����ʵ����k-NI
	 * @throws Exception 
	 */
	public Map<Instance,List<Instance>> grid_kNI(int k, String[] cF, List<Instance> instanceList) throws Exception {
		//1.����list���񣬽�����ӳ�䵽������
		//2.��dΪԲ�ģ�����Χ9�����ӵ�ʵ���ͺ�ʵ���ľ�����㣬��С��d��ʵ���ĸ���>=k,��k-NI������Щ�����У����뵽k_NI��map��
		Map<String, List<Instance>> map = new HashMap<>(); //������x,y����ʾ
		Map<Instance,List<Instance>> k_NI = new HashMap<>(); //��ź�ʵ����k_NI
		
		int d = 400; //����ı߳� = ���ݷ�Χ/d
		for (Instance instance : instanceList) {
			int x = (int) Math.floor(instance.getX()/d);
			int y = (int) Math.floor(instance.getY()/d);
			String s = x + "," + y;
			if (map.containsKey(s)) {
				map.get(s).add(instance);
			}else {
				List<Instance> list = new ArrayList<Instance>();
				list.add(instance);
				map.put(s, list);
			}
		}
		//����
		/*
		System.out.println(map.size());
		for (String string : map.keySet()) {
			System.out.println("���꣺" + string + "��ʵ��" + map.get(string));
		}*/
		for (String c : cF) {
			
			for (int i = 0; i < instanceList.size(); i++) {
				Instance instance = instanceList.get(i);
				if (instance.getFeature().equals(c)) { //�����ʵ��ΪԲ��
					List<Instance> list = new ArrayList<>();  //������ʵ�����ڽ�����ʵ��
					
					int x = (int) Math.floor(instance.getX()/d);
					int y = (int) Math.floor(instance.getY()/d);
					String s = x + "," + y;
					list.addAll(map.get(s));
					//���ڽ��˸����ӵ�ʵ���Ž�ȥ
					Set<String> set = new HashSet<>();
					String s1 = x + "," + (y-1);
					String s2 = (x+1) + "," + (y-1);
					String s3 = (x+1) + "," + y;
					String s4 = (x+1) + "," + (y+1);
					String s5 = x + "," + (y+1);
					String s6 = (x-1) + "," + (y+1);
					String s7 = (x-1) + "," + y;
					String s8 = (x-1) + "," + (y -1);
					set.add(s1);
					set.add(s2);
					set.add(s3);
					set.add(s4);
					set.add(s5);
					set.add(s6);
					set.add(s7);
					set.add(s8);
					for (String string : set) {
						if (map.containsKey(string)) {
							list.addAll(map.get(string));
						}
					}
					
					//�������
					Map<Instance, Double> distanceMap= new HashMap<>(); //�ݴ浱ǰʵ�������������ڽ�ʵ��������
					for (int j = 0; j < list.size(); j++) {
						Instance instance2 = list.get(j);
						String feature = instance2.getFeature(); //�õ�ÿ��ʵ��������
						if (!instance.getFeature().equals(feature)) {
							double distance = Math.sqrt(Math.pow((instance.getX() - instance2.getX()), 2) 
									+ Math.pow((instance.getY() - instance2.getY()), 2));
							distanceMap.put(instance2, distance);
						}
					}
					//��map���վ�������
					List<Instance> sortList = sortInstanceValue(distanceMap);
					//���ʵ������
					if (sortList.size() < k) {
						throw new Exception("ʵ����������k��");
					} 
					//ȡǰk��
					List<Instance> kList = new ArrayList<Instance>();
					for (int j = 0; j < sortList.size(); j++) {
						if (j < k) {
							kList.add(sortList.get(j));
						}
					}
					k_NI.put(instance, kList);
				}
			}
		}
		//����map�ͷ�
		map.clear();
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
