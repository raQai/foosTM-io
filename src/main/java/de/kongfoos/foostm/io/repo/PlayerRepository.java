package de.kongfoos.foostm.io.repo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import de.kongfoos.foostm.io.reader.PlayerReader;
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
	
	public void save(List<Player> playerList){
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		for(Player p : playerList){
			session.save(Player.class.getCanonicalName(), p);
		}
		em.getTransaction().commit();
	}
	
	public Player findById(long id){
		return em.find(Player.class, id);
	}
	
	public List<Player> findAllPlayers(){
		return (List<Player>)em.createQuery("SELECT p FROM player p").getResultList();
	}
	
	public void loadPlayers(File csvFile) throws IOException{
		List<Player> players = PlayerReader.readCSV(csvFile);
		for(Player p : players){
			save(p);
		}
	}
	
	public static String[] getPackagesToScan(){
		return new String[]{Player.class.getPackage().getName()};
	}
	
}
