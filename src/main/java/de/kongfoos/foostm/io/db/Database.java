package de.kongfoos.foostm.io.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.kongfoos.foostm.io.DBManager;
import de.kongfoos.foostm.io.repo.PlayerRepository;
import de.kongfoos.foostm.io.repo.TeamRepository;

public class Database extends ADatabase {

	private PlayerRepository playerRepo;
	private TeamRepository teamRepo;
	
	public Database(DBManager db, String dbName, String openMode) {
		super(db, dbName, openMode);
	}
	
	public PlayerRepository getPlayerRepository(){
		if( playerRepo == null ){
			playerRepo = new PlayerRepository(super.emf.createEntityManager());
		}
		return playerRepo;
	}
	
	public TeamRepository getTeamRepository(){
		if( teamRepo == null ){
			teamRepo = new TeamRepository(super.emf.createEntityManager());
		}
		return teamRepo;
	}

	@Override
	protected String[] getPackagesToScan() {
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(PlayerRepository.getPackagesToScan()));
		list.addAll(Arrays.asList(TeamRepository.getPackagesToScan()));
		return list.toArray(new String[0]);
	}

	
	
}
