package com.torneio.model;

public class Equipe {

    private int    id;
    private String nome;
    private String tag;

    public Equipe() {}

    public Equipe(int id, String nome, String tag) {
        this.id   = id;
        this.nome = nome;
        this.tag  = tag;
    }

    public int    getId()           { return id; }
    public void   setId(int id)     { this.id = id; }

    public String getNome()          { return nome; }
    public void   setNome(String n)  { this.nome = n; }

    public String getTag()           { return tag; }
    public void   setTag(String t)   { this.tag = t; }

    @Override
    public String toString() { return "[" + tag + "] " + nome; }
}
