package BaseClass;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	//����˳�����ṹ
	private final String feature;
	private List<TreeNode> child = new ArrayList<TreeNode>();

	public TreeNode(String feature) {
		this.feature = feature;
	}

	public TreeNode(String feature, List<TreeNode> child) {
		this.feature = feature;
		this.child = child;
	}

	public String getFeature() {
		return feature;
	}

	//���ӽ��ķ���
	public void add(TreeNode node) {
		this.child.add(node);
	}

	public List<TreeNode> getChild() {
		return child;
	}

}
