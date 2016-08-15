package de.kongfoos.foostm.io.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.kongfoos.foostm.model.player.Gender;
import de.kongfoos.foostm.model.player.Player;

public class DTFBCrawler {

	public static final String URL_DTFB_CLUBS = "http://dtfb.de/index.php/eigenprofil/vereine";
	
	public DTFBCrawler(){
	}

	public List<Player> getAllPlayer() throws IOException{
		List<Player> playerList = new ArrayList<Player>();

		Map<String, String> clubs = getAllClubs();
		for(String club : clubs.keySet()){
			Map<String, String> playerURLs = getAllPlayerURLs(clubs.get(club));
			for(String playerName : playerURLs.keySet()){
				List<String> playerInfo = getPlayerInfos(playerURLs.get(playerName));
				String[] name = playerName.split(",");
				Player p = new Player();
				p.setSurname(name[0].trim());
				p.setForename(name[1].trim());
				p.setClub(club.trim());
				p.setGender(parseGender(playerInfo.get(0)));
				p.setDtfb(playerInfo.get(3).trim());
				if( playerInfo.size() > 4 ){
					p.setItsf(playerInfo.get(4).trim());
				}
				playerList.add(p);
			}
		}
		
		return playerList;
	}

	/**
	 * @return map with all club names as key and links as values
	 * @throws IOException 
	 * */
	private Map<String, String> getAllClubs() throws IOException{
		Map<String, String> clubs = new HashMap<String, String>();
		
		Document doc = getDocumentFromURL(URL_DTFB_CLUBS);
		Elements links = doc.select("*[class^=sectiontableentry] a:last-of-type");
		
		for(Element el : links){
			clubs.put(el.text(), el.absUrl("href"));
		}
		
		return clubs;
	}
	
	private Map<String, String> getAllPlayerURLs(String clubURL) throws IOException{
		Map<String, String> player = new HashMap<String, String>();
		
		Document doc = getDocumentFromURL(clubURL);
		Elements links = doc.select("a[href*=spieler_details]");
		
		for(Element el : links){
			player.put(el.text(), el.absUrl("href"));
		}
		
		return player;
	}
	
	private List<String> getPlayerInfos(String playerURL) throws IOException{
		List<String> infos = new ArrayList<String>();
		
		Document doc = getDocumentFromURL(playerURL);
		Elements infoElements = doc.select("*[class^=contentpaneopen_tsl] td[align^=right]");
		
		for(Element el : infoElements){
			infos.add(el.text());
		}
		
		return infos;
	}
	
	private Gender parseGender(String gender){
		if( gender.contains("Herren") ){
			return Gender.MALE;
		}
		else if( gender.contains("Damen") ){
			return Gender.MALE;
		}
		// TODO is there a default?
		return Gender.MALE;
	}
	
	private Document getDocumentFromURL(String url) throws IOException{
		return Jsoup.connect(url)
				  .userAgent("Mozilla")
				  .timeout(3000)
				  .get();
	}
	
}
