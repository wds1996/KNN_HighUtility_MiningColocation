package utils;

import BaseClass.Instance;
import java.util.*;
import java.util.Map.Entry;

public class Grid_kNI {
	/**
	 * 用网格的方式计算核实例的k-NI
	 * @throws Exception 
	 */
	public Map<Instance,List<Instance>> grid_kNI(int k, String[] cF, List<Instance> instanceList) throws Exception {
		//1.定义list网格，将数据映射到网格中
		//2.以d为圆心，将周围9个格子的实例和核实例的距离计算，当小于d的实例的个数>=k,即k-NI就在这些格子中，加入到k_NI的map中
		Map<String, List<Instance>> map = new HashMap<>(); //坐标用x,y来表示
		Map<Instance,List<Instance>> k_NI = new HashMap<>(); //存放核实例的k_NI
		
		int d = 400; //网格的边长 = 数据范围/d
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
		//测试
		/*
		System.out.println(map.size());
		for (String string : map.keySet()) {
			System.out.println("坐标：" + string + "的实例" + map.get(string));
		}*/
		for (String c : cF) {
			
			for (int i = 0; i < instanceList.size(); i++) {
				Instance instance = instanceList.get(i);
				if (instance.getFeature().equals(c)) { //以这个实例为圆心
					List<Instance> list = new ArrayList<>();  //存放这个实例的邻近格子实例
					
					int x = (int) Math.floor(instance.getX()/d);
					int y = (int) Math.floor(instance.getY()/d);
					String s = x + "," + y;
					list.addAll(map.get(s));
					//把邻近八个格子的实例放进去
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
					
					//计算距离
					Map<Instance, Double> distanceMap= new HashMap<>(); //暂存当前实例和它的网格邻近实例及距离
					for (int j = 0; j < list.size(); j++) {
						Instance instance2 = list.get(j);
						String feature = instance2.getFeature(); //拿到每个实例的特征
						if (!instance.getFeature().equals(feature)) {
							double distance = Math.sqrt(Math.pow((instance.getX() - instance2.getX()), 2) 
									+ Math.pow((instance.getY() - instance2.getY()), 2));
							distanceMap.put(instance2, distance);
						}
					}
					//把map按照距离排序
					List<Instance> sortList = sortInstanceValue(distanceMap);
					//检查实例个数
					if (sortList.size() < k) {
						throw new Exception("实例个数不足k个");
					} 
					//取前k个
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
		//用完map释放
		map.clear();
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
