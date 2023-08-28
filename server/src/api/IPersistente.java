package api;

public interface IPersistente {
	
	public int save(boolean isUpdate);
	public int load();
	public int delete();

}
