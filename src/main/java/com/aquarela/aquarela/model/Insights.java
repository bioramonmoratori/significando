package com.aquarela.aquarela.model;

import java.util.ArrayList;
import java.util.List;

public class Insights {

    private Integer numeroDeSentimentosTotais = 0;
    private Integer numeroDeSentimentosMapeados = 0;
    private List<Sentimento> listaDeSentimentosHub = new ArrayList<>();
    private List<Sentimento> listaDeSentimentosComUmVizinho = new ArrayList<>();
    private List<Sentimento> listaDeSentimentosComMaisDe5ConexoesProporcional = new ArrayList<>();
    private List<Sentimento> listaDeSentimentosComMaisDe5ConexoesDiscrepante = new ArrayList<>();
    
    
    public Integer getNumeroDeSentimentosTotais() {
        return numeroDeSentimentosTotais;
    }
    public void setNumeroDeSentimentosTotais(Integer numeroDeSentimentosTotais) {
        this.numeroDeSentimentosTotais = numeroDeSentimentosTotais;
    }
    public Integer getNumeroDeSentimentosMapeados() {
        return numeroDeSentimentosMapeados;
    }
    public void setNumeroDeSentimentosMapeados(Integer numeroDeSentimentosMapeados) {
        this.numeroDeSentimentosMapeados = numeroDeSentimentosMapeados;
    }
    public List<Sentimento> getListaDeSentimentosHub() {
        return listaDeSentimentosHub;
    }
    public void setListaDeSentimentosHub(List<Sentimento> listaDeSentimentosHub) {
        this.listaDeSentimentosHub = listaDeSentimentosHub;
    }
    public List<Sentimento> getListaDeSentimentosComUmVizinho() {
        return listaDeSentimentosComUmVizinho;
    }
    public void setListaDeSentimentosComUmVizinho(List<Sentimento> listaDeSentimentosComUmVizinho) {
        this.listaDeSentimentosComUmVizinho = listaDeSentimentosComUmVizinho;
    }
    public List<Sentimento> getListaDeSentimentosComMaisDe5ConexoesProporcional() {
        return listaDeSentimentosComMaisDe5ConexoesProporcional;
    }
    public void setListaDeSentimentosComMaisDe5ConexoesProporcional(
            List<Sentimento> listaDeSentimentosComMaisDe5ConexoesProporcional) {
        this.listaDeSentimentosComMaisDe5ConexoesProporcional = listaDeSentimentosComMaisDe5ConexoesProporcional;
    }
    public List<Sentimento> getListaDeSentimentosComMaisDe5ConexoesDiscrepante() {
        return listaDeSentimentosComMaisDe5ConexoesDiscrepante;
    }
    public void setListaDeSentimentosComMaisDe5ConexoesDiscrepante(
            List<Sentimento> listaDeSentimentosComMaisDe5ConexoesDiscrepante) {
        this.listaDeSentimentosComMaisDe5ConexoesDiscrepante = listaDeSentimentosComMaisDe5ConexoesDiscrepante;
    }


}
