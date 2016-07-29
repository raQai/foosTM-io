package de.kongfoos.foostm.io.db;

/**
 * Created by patrick on 21/06/16.
 */
public class TournamentSettings {
    //TODO use data store(?)
    //TODO change to DisplaySettings and move to UI
    //TODO create actual tournamentSettings like name, date, etc
    private boolean printPlayerWithITSF;
    private boolean printPlayerWithDTFB;

    public boolean printITSF() {
        return printPlayerWithITSF;
    }

    public void printITSF(boolean printITSF) {
        this.printPlayerWithITSF = printITSF;
    }

    public boolean printDTFB() {
        return printPlayerWithDTFB;
    }

    public void printDTFB(boolean printDTFB) {
        this.printPlayerWithDTFB = printDTFB;
    }
}
