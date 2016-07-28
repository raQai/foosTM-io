package de.kongfoos.foostm.io;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import de.kongfoos.foostm.model.player.PlayerImpl;

@SpringBootApplication
public class TestMain {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TestMain.class, args);
		
		DbManager dbManager = context.getBean(DbManager.class);
		
		dbManager.openDatabase("playerTestDb", new String[]{
				PlayerImpl.class.getPackage().getName()
//				TeamImpl.class.getPackage().getName()
				});
	    
		EntityManagerFactory emf = dbManager.getEntityManagerFactory("playerTestDb");
		emf.createEntityManager();	
		
		dbManager.startWebServer("playerTestDb");
		
	}
	
}
