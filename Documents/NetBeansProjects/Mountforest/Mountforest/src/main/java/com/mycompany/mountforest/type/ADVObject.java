/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;

import java.util.*;

/**
 *
 * @author sofy8
 */
public class ADVObject 
{
    private String nome;
    private final Set<String> alias;
    private String descrizione;
    private boolean apribile;
    private boolean aperto;
    private boolean prendibile;
    
    public ADVObject(String name,String description)
    {
        this.nome = nome;
        this.descrizione = descrizione;
        this.alias = new HashSet<>();
        this.prendibile = true;
        this.apribile = false;
        this.aperto = false;
    }
    
    public String getNome()
    {
        return nome;
    }
    
    public Set<String> getAlias()
    {  
        return alias;
    }
    
    public String getDescrizione()
    {
        return descrizione;
    }
    
    public boolean getApribile()
    {
        return apribile;
    }
    
    public boolean getAperto()
    {
        return aperto;
    }
    
    public boolean getPrendibile()
    {
        return prendibile;
    }
    
    public void setNome(String nome)
    {
        this.nome=nome;
    }
    
    public void setAlias(String alias)
    {  
        this.alias.add(alias);
    }
    
    public void setDescrizione(String descrizione)
    {
        this.descrizione=descrizione;
    }
    
    public void setApribile(boolean apribile)
    {
        this.apribile = apribile;
        if (!apribile) {
            this.aperto = false;
        }
    }
    
    public void apri() {
        if (apribile) {
            this.aperto = true;
        }
    }

    public void chiudi() {
        if (apribile) {
            this.aperto = false;
        }
    }
    
    public void setAperto(boolean aperto)
    {
        if(apribile=true && aperto==false)
            this.aperto=true;
    }

    public void setPrendibile()
    {
           this.prendibile=true; 
    }
    
    public boolean puoEssereRaccolto(Inventario inventario) {
        return this.prendibile && inventario.count() <= 8;
    }
   
}
