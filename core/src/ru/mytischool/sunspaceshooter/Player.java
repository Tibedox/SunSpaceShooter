package ru.mytischool.sunspaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Player {
    String name;
    long kills;

    public Player(String name, long kills) {
        this.name = name;
        this.kills = kills;
    }

    static void sortPlayers(Player[] players){
        //for (int i = 0; i < players.length; i++) if(players[i].kills == 0) players[i].kills = Long.MAX_VALUE;
        for (int j = 0; j < players.length; j++) {
            for (int i = 0; i < players.length-1; i++) {
                if(players[i].kills<players[i+1].kills){
                    Player c = players[i];
                    players[i] = players[i+1];
                    players[i+1] = c;
                }
            }
        }
        //for (int i = 0; i < players.length; i++) if(players[i].kills == Long.MAX_VALUE) players[i].kills = 0;
    }

    static String tableOfRecordsToString(Player[] players){
        String s = "";
        for (int i = 0; i < players.length-1; i++) {
            s += players[i].name+points(players[i].name, 13)+players[i].kills+"\n";
        }
        return s;
    }

    static void saveTableOfRecords(Player[] players){
        sortPlayers(players);
        try {
            Preferences pref = Gdx.app.getPreferences("TableOfRecords");
            for (int i = 0; i < players.length; i++) {
                pref.putString("name"+i, players[i].name);
                pref.putLong("kills"+i, players[i].kills);
            }
            pref.flush();
        } catch (Exception e){
        }
    }

    static void loadTableOfRecords(Player[] players){
        try {
            Preferences pref = Gdx.app.getPreferences("TableOfRecords");
            for (int i = 0; i < players.length; i++) {
                if(pref.contains("name"+i))	players[i].name = pref.getString("name"+i, "null");
                if(pref.contains("kills"+i))	players[i].kills = pref.getLong("kills"+i, 0);
            }
        } catch (Exception e){
        }
    }

    static void clearTableOfRecords(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].name = "Noname";
            players[i].kills = 0;
        }
    }

    static String points(String name, int length){
        int n = length-name.length();
        String s = "";
        for (int i = 0; i < n; i++) s += ".";
        return s;
    }
}
