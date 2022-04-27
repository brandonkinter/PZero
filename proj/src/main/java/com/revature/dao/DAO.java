package com.revature.dao;

public interface DAO<T, U, V> {
	public V create(T t);
	
	public T retrieve(U u);
	
	public void update(T t);
	
	public void delete(T t);
}
