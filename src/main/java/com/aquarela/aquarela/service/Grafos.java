package com.aquarela.aquarela.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.aquarela.aquarela.model.Categoria;
import com.aquarela.aquarela.model.Sentimento;

@Service
public class Grafos {
    
    private HashMap<Sentimento, List<Sentimento>> mapaDeSentimentos = new HashMap<>();

    public HashMap<Sentimento, List<Sentimento>> getMapaDeSentimentos() {
        return mapaDeSentimentos;
    }


    public void setMapaDeSentimentos(HashMap<Sentimento, List<Sentimento>> mapaDeSentimentos) {
        this.mapaDeSentimentos = mapaDeSentimentos;
    }


    public HashMap<Sentimento, List<Sentimento>> registrandoSentimentosPrincipais(){
        
        for(Sentimento sentimento : Sentimento.values()){
            this.mapaDeSentimentos.put(sentimento, new ArrayList<>());
        }

        return this.mapaDeSentimentos;

    }

    public List<Sentimento> listaDeSentimentosPrincipais(){

        List<Sentimento> listaDeSentimentosPrincipais = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            
            listaDeSentimentosPrincipais.add(index.getKey());
            
        }
        
        return listaDeSentimentosPrincipais;
    }

    public HashMap<Sentimento, List<Sentimento>> criandoRelacoesEntreOsSentimentos(Sentimento sentimentoPrincipal, Sentimento sentimentoCompativel){
        
        List<Sentimento> vertice = this.mapaDeSentimentos.get(sentimentoPrincipal);
        vertice.add(sentimentoCompativel);

        this.mapaDeSentimentos.put(sentimentoPrincipal, vertice);
        return this.mapaDeSentimentos;
    }

    public List<Sentimento> obtendoListaDeSentimentosRelacionados(Sentimento sentimentoPrincipal){
        
        List<Sentimento> vertice = this.mapaDeSentimentos.get(sentimentoPrincipal);
        return vertice;
    }

    public List<Sentimento> consultarSentimento(Sentimento sentimentoPrincipal){
        
        List<Sentimento> sentimentosRelacionadosEmPrimeiroGrau = this.mapaDeSentimentos.get(sentimentoPrincipal);
        List<Sentimento> sentimentosRelacionadosEmSegundoGrau = new ArrayList<>();
        
        for(Sentimento  sentimento : sentimentosRelacionadosEmPrimeiroGrau){

            for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            
                if(index.getKey() == sentimento){
                    for(Sentimento sentimentoDeSegundoGrau : index.getValue()){
                        sentimentosRelacionadosEmSegundoGrau.add(sentimentoDeSegundoGrau);
                    }
                }
            }
        }
        System.out.println("Sentimento de Primeiro Grau:" + sentimentosRelacionadosEmPrimeiroGrau);
        System.out.println("Sentimento de Segundo Grau:" + sentimentosRelacionadosEmSegundoGrau);

        return null;
    }

    /*
     * Metodos para processamento dos insights
     */

    public List<Sentimento> sentimentosHub(){
        
        List<Sentimento> sentimentosHub = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            if(index.getValue().size() >= 10){
                sentimentosHub.add(index.getKey());
            }
        }
        return sentimentosHub;
    }

    public List<Sentimento> sentimentosComMaisDe5Conexoes(){
        
        List<Sentimento> sentimentosMaisDe5 = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            if(index.getValue().size() >= 5){
                sentimentosMaisDe5.add(index.getKey());
            }
        }
        return sentimentosMaisDe5;
    }

    public List<Sentimento> sentimentosComMaisDe5ConexoesComRelacionamentoDiscrepante(){
        
        List<Sentimento> sentimentosMaisDe5Discrepante = new ArrayList<>();


        for(Sentimento index : this.sentimentosComMaisDe5Conexoes()){
            
            Integer sentimentosPositivos = 0;
            Integer sentimentosNegativos = 0;

            for(Sentimento sentimento : this.mapaDeSentimentos.get(index)){

                if(sentimento.getCategoria() == Categoria.POSITIVO){
                    sentimentosPositivos++;
                } else if(sentimento.getCategoria() == Categoria.NEGATIVO){
                    sentimentosNegativos++;
                }

            }

            if(sentimentosPositivos >= 3*sentimentosNegativos || sentimentosNegativos >= 3*sentimentosPositivos){
                sentimentosMaisDe5Discrepante.add(index);
            }
        }

        return sentimentosMaisDe5Discrepante;
    }

    public List<Sentimento> sentimentosComMaisDe5ConexoesProporcional(){
        
        List<Sentimento> sentimentosMaisDe5Proporcional = new ArrayList<>();


        for(Sentimento index : this.sentimentosComMaisDe5Conexoes()){
            
            Integer sentimentosPositivos = 0;
            Integer sentimentosNegativos = 0;

            for(Sentimento sentimento : this.mapaDeSentimentos.get(index)){

                if(sentimento.getCategoria() == Categoria.POSITIVO){
                    sentimentosPositivos++;
                } else if(sentimento.getCategoria() == Categoria.NEGATIVO){
                    sentimentosNegativos++;
                }
            }
            if(sentimentosPositivos >= (0.8)*sentimentosNegativos && sentimentosPositivos <= (1.2)*sentimentosNegativos 
                || sentimentosNegativos >= (0.8)*sentimentosPositivos && sentimentosNegativos <= (1.2)*sentimentosPositivos){
                    sentimentosMaisDe5Proporcional.add(index);
            }
        }

        return sentimentosMaisDe5Proporcional;
    }

    public List<Sentimento> sentimentosComUmVizinho(){

        List<Sentimento> sentimentosComUmVizinho = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            if(index.getValue().size() >= 1 && index.getValue().size() <= 2){
                sentimentosComUmVizinho.add(index.getKey());
            }
        }
        
        return sentimentosComUmVizinho;
    }
    
    public List<Sentimento> sentimentosJaMapeados(){
            
            List<Sentimento> sentimentosJaMapeados = new ArrayList<>();
            for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
                if(index.getValue().size() >= 1){
                    sentimentosJaMapeados.add(index.getKey());
                }
            }
            
            return sentimentosJaMapeados;
    }

}


