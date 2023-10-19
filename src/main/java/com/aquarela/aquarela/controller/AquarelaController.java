package com.aquarela.aquarela.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aquarela.aquarela.model.RelacionandoSentimentoForm;
import com.aquarela.aquarela.model.Sentimento;
import com.aquarela.aquarela.service.Grafos;

@Controller
@RequestMapping("/")
public class AquarelaController {
    
    @Autowired
    Grafos grafos;
    
    @GetMapping("/inicio")
    public String inicio(){
        return "index";
    }


    @GetMapping("/")
    public String conectandoSentimentos(Model model){
        
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

            // Preenche a lista dos outros sentimentos sem repetir o sentimento principal
            if(sentimento != sentimentoPrincipal){
                listaDeSentimentos.add(sentimento);
            }
        }

        relacionandoSentimentoForm.setListaDeSentimentos(listaDeSentimentos);
        relacionandoSentimentoForm.setSentimentoPrincipal(sentimentoPrincipal);

        model.addAttribute("relacionandosentimentoform", relacionandoSentimentoForm);
        return "form";
    
    }

    @PostMapping
    public String calculandoGrafo(RelacionandoSentimentoForm relacionandoSentimentoForm){

        for(Sentimento sentimento : relacionandoSentimentoForm.getListaDeSentimentos()){
            
            grafos.criandoRelacoesEntreOsSentimentos(
                relacionandoSentimentoForm.getSentimentoPrincipal(), sentimento);
        
        }
        return "redirect:/";
    }

}
