package BaseDM;
import BaseClass.Instance;
import utils.Get_kNI;
import utils.Load_DataSet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasedDM {

	private static final int k = 12; // ����k
	private static final double threshold = 0.1; // Ч�ö���ֵ
	private static final String[] cF = {"K"}; // ָ����Ԫ�ؼ�
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Load_DataSet iDate = new Load_DataSet();
		try {
			iDate.readInstance("beijingPOI-U.txt");
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
	
		//����
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
		BasedLine b = new BasedLine();
		b.get_HighUtilityPattern(threshold, k, k_NI);
		long endTime = System.currentTimeMillis();
		System.out.println("��������ʱ�䣺" + (endTime - startTime) + "ms");
	}

}
