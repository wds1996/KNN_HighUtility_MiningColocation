package OptimizedDM;

import BaseClass.Instance;
import utils.Get_kNI;
import utils.Load_DataSet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimizedDM {

	private static final int k = 7; // ����k
	private static final double threshold = 0.2; // Ч�ö���ֵ
	private static final String[] cF = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"}; // ָ����Ԫ�ؼ�
	//,"B","C","D","E","F","G","H","I","J","K","L"
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		Load_DataSet iDate = new Load_DataSet();
		try {
			iDate.readInstance("Data/beijingPOI-U.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//����k_NI����
		Get_kNI get_kni = new Get_kNI();
		Map<Instance, List<Instance>> k_NI = new HashMap<>();
		try {
			k_NI = get_kni.get_kNI(k, cF, Load_DataSet.instanceList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//����,�ǵø��ݲ�ͬ�ķ�Χ��������d�Ĵ�С
//		Grid_kNI gk = new Grid_kNI();
//		Map<Instance, List<Instance>> k_NI = new HashMap<>();
//		try {
//			k_NI = gk.grid_kNI(k, cF, Import_SpaceDate2.instanceList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
			
		//���instanceList,�����ڴ�
		Load_DataSet.instanceList.clear();
		System.out.println("���:" + k_NI.size());
		Pruning p = new Pruning();
		p.get_HighUtilityPattern(threshold, k, k_NI);
		
		long endTime = System.currentTimeMillis();
		System.out.println("��������ʱ�䣺" + (endTime - startTime) + "ms");
	}

}
