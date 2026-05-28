package com.torneio.model;

public class Mapa {

    private int    id;
    private String nomeMapa;
    private String tipo;

    public Mapa() {}

    public Mapa(int id, String nomeMapa, String tipo) {
        this.id       = id;
        this.nomeMapa = nomeMapa;
        this.tipo     = tipo;
    }

    public int    getId()           { return id; }
    public void   setId(int id)     { this.id = id; }

    public String getNomeMapa()           { return nomeMapa; }
    public void   setNomeMapa(String n)   { this.nomeMapa = n; }

    public String getTipo()          { return tipo; }
    public void   setTipo(String t)  { this.tipo = t; }

    @Override
    public String toString() { return nomeMapa + " (" + tipo + ")"; }
}
