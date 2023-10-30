package com.aquarela.aquarela.model;

import java.util.ArrayList;
import java.util.List;

public class RelacionandoSentimentoForm {
    
    private Sentimento sentimentoPrincipal;
    private List<Sentimento> listaDeSentimentos = new ArrayList<>();
    private Categoria categoria;

    public Sentimento getSentimentoPrincipal() {
        return sentimentoPrincipal;
    }
    public void setSentimentoPrincipal(Sentimento sentimentoPrincipal) {
        this.sentimentoPrincipal = sentimentoPrincipal;
    }
    public List<Sentimento> getListaDeSentimentos() {
        return listaDeSentimentos;
    }
    public void setListaDeSentimentos(List<Sentimento> listaDeSentimentos) {
        this.listaDeSentimentos = listaDeSentimentos;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
