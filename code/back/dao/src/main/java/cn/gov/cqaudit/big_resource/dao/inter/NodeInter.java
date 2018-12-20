package cn.gov.cqaudit.big_resource.dao.inter;

import cn.gov.cqaudit.big_resource.entity.Node;;
public interface NodeInter {

	public void add(Node node);
	public Node getByKey(String key);
	public java.util.ArrayList<Node> getAll();
	
}
