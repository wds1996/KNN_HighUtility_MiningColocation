package BaseDM;

import BaseClass.TreeNode;

import java.util.*;

public class CandidatePattern {
	
	public Map<String, List<String>> candidatePattern(Map<String, List<String>> kNF, int k) {
		//1����kNF�ɺ�Ԫ�����һ�����ṹ
		//2�����к�ѡģʽ���ռ�
		Map<String, List<String>> patternMap = new HashMap<>();
		
		for (String c : kNF.keySet()) {
			List<String> list = new ArrayList<String>(); //��ź˺�ѡģʽ
			
			TreeNode root = new TreeNode(c);
			for (int i = 0; i < kNF.get(c).size(); i++) {
				TreeNode node = new TreeNode(kNF.get(c).get(i));
				root.add(node);
				//�ӵڶ��㿪ʼ�ݹ��node�ĺ�������������ȥ
				addNode(node, kNF.get(c));
				
			}
			//����
			if (kNF.get(c).size() < k) {
				k = kNF.get(c).size();
			}
			traverse(root,list,k);
			list.remove(0);
			patternMap.put(c, list);
		}
		return patternMap;
		
	}
	public void addNode(TreeNode node,List<String> list) {
		int n = node.getFeature().length();
		String s = node.getFeature();
		String temp = s.substring(n-1);
		int j = list.indexOf(temp);
		for (int i = j+1; i < list.size(); i++) {
			TreeNode newNode = new TreeNode(s + list.get(i));
			node.add(newNode);
			addNode(newNode, list);
		}
	}
	//��α���,ģʽ��߽�Ϊk
	public void traverse(TreeNode root, List<String> list, int k) {
		if (root.getChild().isEmpty()) {
			return;
		}
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		while(!queue.isEmpty()) {
			TreeNode node = queue.poll();
			if (node.getFeature().length() > k) {
				break;
			}else {
				list.add(node.getFeature());
				if (!node.getChild().isEmpty()) {
					for (int i = 0; i < node.getChild().size(); i++) {
						queue.add(node.getChild().get(i));
					}
				}
			}
		}
	}
	
}
