package datatable;

import java.util.List;

public class Index {

	private String name;

	private List<Column> members;

	private boolean unique;

	public Index(String name, List<Column> members) {
		super();
		this.name = name;
		this.members = members;
	}
	public Index() {
	}

	public Index(String name, List<Column> members, boolean unique) {
		super();
		this.name = name;
		this.members = members;
		this.unique = unique;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getMembers() {
		return members;
	}

	public void setMembers(List<Column> members) {
		this.members = members;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	@Override
	public String toString() {
		return "Index [name=" + name + ", members=" + members + "]";
	}
}
