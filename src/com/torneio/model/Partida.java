package com.torneio.model;

public class Partida {

    private int     id;
    private int     idMapa;
    private String  nomeMapa;
    private int     idEquipe1;
    private String  nomeEquipe1;
    private int     idEquipe2;
    private String  nomeEquipe2;
    private Integer idVencedor;
    private String  nomeVencedor;
    private String  status;
    private String  dataPartida;

    public Partida() {}

    public int    getId()             { return id; }
    public void   setId(int id)       { this.id = id; }

    public int    getIdMapa()         { return idMapa; }
    public void   setIdMapa(int v)    { this.idMapa = v; }

    public String getNomeMapa()            { return nomeMapa; }
    public void   setNomeMapa(String v)    { this.nomeMapa = v; }

    public int    getIdEquipe1()           { return idEquipe1; }
    public void   setIdEquipe1(int v)      { this.idEquipe1 = v; }

    public String getNomeEquipe1()          { return nomeEquipe1; }
    public void   setNomeEquipe1(String v)  { this.nomeEquipe1 = v; }

    public int    getIdEquipe2()           { return idEquipe2; }
    public void   setIdEquipe2(int v)      { this.idEquipe2 = v; }

    public String getNomeEquipe2()          { return nomeEquipe2; }
    public void   setNomeEquipe2(String v)  { this.nomeEquipe2 = v; }

    public Integer getIdVencedor()          { return idVencedor; }
    public void    setIdVencedor(Integer v) { this.idVencedor = v; }

    public String getNomeVencedor()          { return nomeVencedor; }
    public void   setNomeVencedor(String v)  { this.nomeVencedor = v; }

    public String getStatus()          { return status; }
    public void   setStatus(String v)  { this.status = v; }

    public String getDataPartida()        { return dataPartida; }
    public void   setDataPartida(String v){ this.dataPartida = v; }
}
