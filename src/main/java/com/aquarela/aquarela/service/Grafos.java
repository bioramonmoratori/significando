package com.aquarela.aquarela.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

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

    public List<Sentimento> sentimentosComMaisVizinhos(){
        
        List<Sentimento> sentimentosComMaisVizinhos = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            if(index.getValue().size() > 2){
                sentimentosComMaisVizinhos.add(index.getKey());
            }
        }
        System.out.println("Sentimentos Com Mais Vizinhos:" + sentimentosComMaisVizinhos);
        
        return sentimentosComMaisVizinhos;
    }


    public List<Sentimento> sentimentosComUmVizinho(){

        List<Sentimento> sentimentosComUmVizinho = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            if(index.getValue().size() == 1){
                sentimentosComUmVizinho.add(index.getKey());
            }
        }
        System.out.println("Sentimentos Com Um Vizinhos:" + sentimentosComUmVizinho);
        
        return sentimentosComUmVizinho;
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


    public List<Sentimento> listaDeSentimentosPrincipais(){

        List<Sentimento> listaDeSentimentosPrincipais = new ArrayList<>();
        for(Entry<Sentimento, List<Sentimento>> index : this.mapaDeSentimentos.entrySet()){
            
            listaDeSentimentosPrincipais.add(index.getKey());
            
        }
        
        return listaDeSentimentosPrincipais;
    }
    
}


