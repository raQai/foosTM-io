package de.kongfoos.foostm.io.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import de.kongfoos.foostm.model.player.Player;

public class PlayerReader {

	public static List<Player> readCSV(File csvFile) throws IOException {
		List<Player> players = new ArrayList<Player>();
		
		Reader in = new FileReader(csvFile);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter('=').parse(in);
		for (CSVRecord record : records) {
			if( record.get(1).equals("") || record.get(2).equals("") ){
				continue;
			}
			
			Player p = new Player();
			p.setSurname(record.get(1));
			p.setForename(record.get(2));
			players.add(p);
		}
		
		return players;
	}

}
