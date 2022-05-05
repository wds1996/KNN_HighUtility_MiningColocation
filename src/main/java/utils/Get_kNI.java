package utils;
import BaseClass.Instance;

import java.util.*;
import java.util.Map.Entry;

public class Get_kNI {
	/**
	 * 计算实例之间的距离并排序
	 * @throws Exception 
	 */
	public Map<Instance,List<Instance>> get_kNI(int k, String[] cF, List<Instance> instanceList) throws Exception {
		Map<Instance,List<Instance>> k_NI = new HashMap<>(); //存放核实例的k_NI
		
		for (int i = 0; i < cF.length; i++) { //核元素个数
			
			for (int j = 0; j < instanceList.size(); j++) {
				
				if (cF[i].equals(instanceList.get(j).getFeature())) {  //计算核元素和其他特征实例之间的距离
					Instance instance1 = instanceList.get(j);
					
					Map<Instance, Double> map= new HashMap<>(); 
					for (int m = 0; m < instanceList.size(); m++) {
						
						if (!instanceList.get(j).getFeature().equals(instanceList.get(m).getFeature())) {//不是同一个特征
							Instance instance2 = instanceList.get(m);
							
							double distance = Math.sqrt(Math.pow((instance1.getX() - instance2.getX()), 2) 
									+ Math.pow((instance1.getY() - instance2.getY()), 2));
							map.put(instance2, distance);
						}
					}
					
					//测试
					/*
					System.out.println("核实例" + instance1 + "的k-NI:");
					for (Instance instance : map.keySet()) {
						System.out.println(instance + "：" + map.get(instance));
					}*/
					
					List<Instance> sortList = sortInstanceValue(map);//按值排序
					
					if (k > map.size()) {
						throw new Exception("k值过大！");
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
		 * map按照value排序
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
 	            //按照从小到大的顺序排列
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
