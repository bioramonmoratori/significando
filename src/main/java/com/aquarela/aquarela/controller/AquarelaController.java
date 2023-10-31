package com.aquarela.aquarela.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aquarela.aquarela.model.RelacionandoSentimentoForm;
import com.aquarela.aquarela.model.Sentimento;
import com.aquarela.aquarela.service.Grafos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Controller
@RequestMapping("/")
public class AquarelaController {
    
    @Autowired
    Grafos grafos;
    
    @GetMapping("/")
    public String inicio(){
        return "index";
    }


    @GetMapping("/sentimentos")
    public String conectandoSentimentos(Model model){

        try{
            // Ver a lista de sentimentos principais ja preenchidas
            // Enviar o mapa para a pagina para ir ao metodo post

            /* Preciso enviar:
            * Sentimento Principal nao repetido
            * Lista de Sentimentos sem o sentimento Principal
            */

            Boolean primeiroSentimentoEncontrado = false;
            
            Sentimento sentimentoPrincipal = null;
            List<Sentimento> listaDeSentimentos = new ArrayList<>();
            RelacionandoSentimentoForm relacionandoSentimentoForm = new RelacionandoSentimentoForm();

            // Confere se a lista de sentimentos gerais esta preenchida 
            if(grafos.getMapaDeSentimentos().isEmpty()){
                grafos.registrandoSentimentosPrincipais();
            }

            // Percorre a lista de sentimentos
            for(Sentimento sentimento : grafos.listaDeSentimentosPrincipais()){

                // Pega o primeiro sentimento principal ainda nao preenchido
                if(grafos.obtendoListaDeSentimentosRelacionados(sentimento).isEmpty() && primeiroSentimentoEncontrado == false){
                
                    sentimentoPrincipal = sentimento;
                    primeiroSentimentoEncontrado = true;
                }
                
            }

            for(Sentimento sentimento : grafos.listaDeSentimentosPrincipais()){

                // Preenche a lista dos outros sentimentos sem repetir o sentimento principal
                if(sentimento != sentimentoPrincipal && (grafos.obtendoListaDeSentimentosRelacionados(sentimento).contains(sentimentoPrincipal) == false)){
                    
                    listaDeSentimentos.add(sentimento);
                    
                }

            }

            relacionandoSentimentoForm.setListaDeSentimentos(listaDeSentimentos);
            relacionandoSentimentoForm.setSentimentoPrincipal(sentimentoPrincipal);
            relacionandoSentimentoForm.setCategoria(sentimentoPrincipal.getCategoria());

            model.addAttribute("relacionandosentimentoform", relacionandoSentimentoForm);
            return "form";

        }catch(Exception e){
            return "redirect:/sentimentos";
        }
    
    }

    @PostMapping("/calculandografo")
    public String calculandoGrafo(@RequestParam("sentimentos") List<Sentimento> sentimentosSelecionados, @RequestParam("sentimentoprincipal") Sentimento sentimentoPrincipal){
        if(sentimentosSelecionados.isEmpty() || sentimentoPrincipal == null){
            return "redirect:/sentimentos";
        }
        try{
            for(Sentimento sentimento : sentimentosSelecionados){
                
                grafos.criandoRelacoesEntreOsSentimentos(
                    sentimentoPrincipal, sentimento);
            }
        
        } catch(Exception e){
            return "redirect:/sentimentos";
        }
        return "redirect:/sentimentos";
    }

    @GetMapping("/insights")
    public String insights(){
    
        return "insights";
        
    }

    @GetMapping("/gerarmapa")
    public String gerarMapa(Model model) throws IOException {
        
        try{
            Graph graph = new SingleGraph("MeuGrafo");
        
            // Substitua esta parte pelo seu HashMap
            HashMap<Sentimento, List<Sentimento>> mapaDeSentimentos = grafos.getMapaDeSentimentos();
        
            // Adicione nós ao grafo
            for (Sentimento sentimento : mapaDeSentimentos.keySet()) {
                graph.addNode(sentimento.toString()).setAttribute("ui.label", sentimento.toString());
            }
        
            // Adicione arestas ao grafo
            for (Map.Entry<Sentimento, List<Sentimento>> entry : mapaDeSentimentos.entrySet()) {
                Sentimento sentimento = entry.getKey();
                List<Sentimento> relacionados = entry.getValue();
                for (Sentimento relacionado : relacionados) {
                    graph.addEdge(sentimento.toString() + "_" + relacionado.toString(), sentimento.toString(), relacionado.toString());
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode elementsArray = JsonNodeFactory.instance.arrayNode();
            
            for (Node node : graph) {
                ObjectNode nodeData = objectMapper.createObjectNode();
                nodeData.putObject("data")
                    .put("id", node.getId())
                    .put("label", node.getId())
                    .put("categoria", Sentimento.valueOf(node.getId()).getCategoria().toString());  // Você pode ajustar o label conforme necessário
                elementsArray.add(nodeData);
            }
            
            for (Edge edge : graph.getEachEdge()) {
                ObjectNode edgeData = objectMapper.createObjectNode();
                edgeData.putObject("data")
                    .put("id", edge.getId())
                    .put("source", edge.getNode0().getId())
                    .put("target", edge.getNode1().getId());
                elementsArray.add(edgeData);
            }


            String graphJsonData = objectMapper.writeValueAsString(elementsArray);

            //System.out.println(graphJsonData);

            model.addAttribute("graph", graphJsonData);
            return "grafo";
        }catch(Exception e){
            return "redirect:/sentimentos";
        }

    }

    @GetMapping("/limparmapa")
    public String limparMapa(){
        try{
            grafos.getMapaDeSentimentos().clear();
            
        }catch(Exception e){
        }
        return "redirect:/sentimentos";
    }

}
