package com.aquarela.aquarela;


import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aquarela.aquarela.model.Sentimento;
import com.aquarela.aquarela.service.Grafos;

@SpringBootApplication
public class AquarelaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AquarelaApplication.class, args);

		Grafos grafos = new Grafos();

		grafos.registrandoSentimentosPrincipais();
		grafos.sentimentosComUmVizinho();

		grafos.criandoRelacoesEntreOsSentimentos(Sentimento.ABANDONO, Sentimento.ABALO);
		grafos.criandoRelacoesEntreOsSentimentos(Sentimento.ABALO, Sentimento.ACEITACAO);
		grafos.criandoRelacoesEntreOsSentimentos(Sentimento.ABANDONO, Sentimento.ADMIRACAO);


		grafos.consultarSentimento(Sentimento.ABANDONO);

	}

}
