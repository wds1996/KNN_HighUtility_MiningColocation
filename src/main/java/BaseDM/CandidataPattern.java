package BaseDM;

import BaseClass.TreeNode;

import java.util.*;

public class CandidataPattern {
	
	public Map<String, List<String>> candidataPattern(Map<String, List<String>> kNF, int k) {
		//1，将kNF由核元素组成一个树结构
		//2，进行候选模式的收集
		Map<String, List<String>> patternMap = new HashMap<>();
		
		for (String c : kNF.keySet()) {
			List<String> list = new ArrayList<String>(); //存放核候选模式
			
			TreeNode root = new TreeNode(c);
			int len = kNF.get(c).size();
			for (int i = 0; i < len; i++) {
				TreeNode node = new TreeNode(kNF.get(c).get(i));
				root.add(node);
				//从第二层开始递归把node的后面的特征都存进去
				addNode(node, kNF.get(c));
			}
			//遍历
			if (len < k) {
				k = len;
			}
			//层次遍历
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
	//层次遍历,模式最高阶为k
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
					queue.addAll(node.getChild());
				}
			}
		}
	}
	
}
