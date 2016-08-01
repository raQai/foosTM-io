package de.kongfoos.foostm.io.repo;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import de.kongfoos.foostm.model.player.Player;
import de.kongfoos.foostm.model.team.Team;

public class TeamRepository {

	private EntityManager em;
	
	public TeamRepository(EntityManager em){
		this.em = em;
	}
	
	public void save(Team<? extends Player> team){
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		session.save(Team.class.getCanonicalName(), team);
		em.getTransaction().commit();
	}
	
	public Team<Player> findById(long id){
		return em.find(Team.class, id);
	}

	public static String[] getPackagesToScan(){
		return new String[]{Team.class.getPackage().getName()};
	}
	
}
