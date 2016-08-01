package de.kongfoos.foostm.io.repo;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import de.kongfoos.foostm.model.player.Player;

public class PlayerRepository {

	private EntityManager em;
	
	public PlayerRepository(EntityManager em){
		this.em = em;
	}
	
	public void save(Player player){
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		session.save(Player.class.getCanonicalName(), player);
		em.getTransaction().commit();
	}
	
	public Player findById(long id){
		return em.find(Player.class, id);
	}
	
	public static String[] getPackagesToScan(){
		return new String[]{Player.class.getPackage().getName()};
	}
	
}
