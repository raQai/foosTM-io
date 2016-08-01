package de.kongfoos.foostm.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import de.kongfoos.foostm.io.db.Database;
import de.kongfoos.foostm.io.repo.PlayerRepository;
import de.kongfoos.foostm.io.repo.TeamRepository;
import de.kongfoos.foostm.model.player.Player;
import de.kongfoos.foostm.model.team.Team;
import de.kongfoos.foostm.model.team.Type;

@SpringBootApplication
public class TestMain {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TestMain.class, args);
		
		DBManager dbManager = context.getBean(DBManager.class);
		
		Database db = new Database(dbManager, "playerTestDb", DBManager.CREATE_DROP);
		PlayerRepository playerRepo = db.getPlayerRepository();
		TeamRepository teamRepo = db.getTeamRepository();
		
		Player p1 = new Player();
		p1.setForename("Ferdinand");
		p1.setSurname("Pyttel");
		playerRepo.save(p1);

		Player p2 = playerRepo.findById(1);
		System.out.println(p2);
		
		Team<Player> t1 = new Team<Player>();
		t1.addPlayer(p1);
		t1.setType(Type.SINGLES);
		teamRepo.save(t1);
		
		Team<Player> t2 = teamRepo.findById(2);
		System.out.println(t2);
		
		// start debug server
		dbManager.startWebServer("playerTestDb");
	}
	
}
