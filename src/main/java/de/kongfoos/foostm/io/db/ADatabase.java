package de.kongfoos.foostm.io.db;

import javax.persistence.EntityManagerFactory;

import de.kongfoos.foostm.io.DBManager;

public abstract class ADatabase {

	protected EntityManagerFactory emf;
	
	public ADatabase(DBManager db, String dbName, String openMode){
		db.openDatabase(dbName, getPackagesToScan(), openMode);
		this.emf = db.getEntityManagerFactory(dbName);
	}
	
	protected abstract String[] getPackagesToScan();
	
}
