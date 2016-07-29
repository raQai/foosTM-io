package de.kongfoos.foostm.io.db;

import de.kongfoos.foostm.model.player.APlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlayerDB<T extends APlayer> {
    private final HashSet<T> players = new HashSet<>();

    public void addPlayer(T newPlayer) {
        this.players.add(newPlayer);
    }

    public List<T> getPlayers() {
        return new ArrayList<>(players);
    }
}
