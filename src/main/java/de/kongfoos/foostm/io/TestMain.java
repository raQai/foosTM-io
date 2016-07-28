package de.kongfoos.foostm.io;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import de.kongfoos.foostm.model.player.IPlayer;
import de.kongfoos.foostm.model.team.ITeam;
import de.kongfoos.foostm.view.fx.model.player.FXPlayer;
import de.kongfoos.foostm.view.fx.model.player.FXPlayerBuilderFactory;
import de.kongfoos.foostm.view.fx.model.team.FXTeam;
import de.kongfoos.foostm.view.fx.model.team.FXTeamBuilderFactory;

@SpringBootApplication
public class TestMain {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TestMain.class, args);
		
		DbManager dbManager = context.getBean(DbManager.class);
		
		dbManager.openDatabase("playerTestDb", new String[]{
				IPlayer.class.getPackage().getName(),
				ITeam.class.getPackage().getName()
				});
	    
		EntityManagerFactory emf = dbManager.getEntityManagerFactory("playerTestDb");
		EntityManager em = emf.createEntityManager();	
		
		// start debug server
		new Thread(){
			public void run() {
				dbManager.startWebServer("playerTestDb");
			}
		}.start();
		
		// save some data
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		
		FXPlayer p1 = FXPlayerBuilderFactory.create("Ferdinand", "Pyttel").build();
		FXPlayer p2 = FXPlayerBuilderFactory.create("Patrick", "Bogdan").build();

		session.save(IPlayer.class.getCanonicalName(), p1);
		session.save(IPlayer.class.getCanonicalName(), p2);

		FXTeam t = FXTeamBuilderFactory.buildDoubles(p1, p2);
		
		session.save(ITeam.class.getCanonicalName(), t);
		
		em.getTransaction().commit();
		
	}
	
}
