package OptimizedDM;

import BaseClass.Instance;
import utils.Get_kNI;
import utils.Load_DataSet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimizedDM {

	private static final int k = 7; // 参数k
	private static final double threshold = 0.2; // 效用度阈值
	private static final String[] cF = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"}; // 指定核元素集
	//,"B","C","D","E","F","G","H","I","J","K","L"
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		Load_DataSet iDate = new Load_DataSet();
		try {
			iDate.readInstance("Data/beijingPOI-U.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//蛮力k_NI方法
		Get_kNI get_kni = new Get_kNI();
		Map<Instance, List<Instance>> k_NI = new HashMap<>();
		try {
			k_NI = get_kni.get_kNI(k, cF, Load_DataSet.instanceList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//网格法,记得根据不同的范围调整网格d的大小
//		Grid_kNI gk = new Grid_kNI();
//		Map<Instance, List<Instance>> k_NI = new HashMap<>();
//		try {
//			k_NI = gk.grid_kNI(k, cF, Import_SpaceDate2.instanceList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
			
		//清空instanceList,减少内存
		Load_DataSet.instanceList.clear();
		System.out.println("完成:" + k_NI.size());
		Pruning p = new Pruning();
		p.get_HighUtilityPattern(threshold, k, k_NI);
		
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
	}

}
