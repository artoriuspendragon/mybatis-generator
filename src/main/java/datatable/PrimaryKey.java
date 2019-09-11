package datatable;

import java.util.List;

public class PrimaryKey {

	private List<Column> members;

	public PrimaryKey(List<Column> members) {
		super();
		this.members = members;
	}

	public List<Column> getMembers() {
		return members;
	}

	public void setMembers(List<Column> members) {
		this.members = members;
	}

}
