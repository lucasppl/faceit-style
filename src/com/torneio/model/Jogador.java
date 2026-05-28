package com.torneio.model;

public class Jogador {

    private int    id;
    private String nickname;
    private int    elo;
    private String status;

    public Jogador() {}

    public Jogador(int id, String nickname, int elo, String status) {
        this.id       = id;
        this.nickname = nickname;
        this.elo      = elo;
        this.status   = status;
    }

    public int    getId()       { return id; }
    public void   setId(int id) { this.id = id; }

    public String getNickname()           { return nickname; }
    public void   setNickname(String n)   { this.nickname = n; }

    public int    getElo()        { return elo; }
    public void   setElo(int elo) { this.elo = elo; }

    public String getStatus()          { return status; }
    public void   setStatus(String s)  { this.status = s; }

    @Override
    public String toString() { return nickname; }
}
