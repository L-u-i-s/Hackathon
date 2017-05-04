package dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, ID extends Serializable> {

	public int add(T entity);

	public int update(T entity);

	public T getById(ID id);

	public void deleteById(ID id);

	public List<T> getAll();

}
