package it.prova.agenda.service;

import java.util.List;

import it.prova.agenda.model.Agenda;

public interface AgendaService {

	List<Agenda> listAllElements(boolean eager);

	Agenda caricaSingoloElemento(Long id);

	Agenda caricaSingoloElementoEager(Long id);

	void aggiorna(Agenda agendaInstance);

	Agenda inserisciNuovo(Agenda agendaInstance);

	void rimuovi(Long idToRemove);

	List<Agenda> findByExample(Agenda example);

	List<Agenda> findByDescrizione(String descrizione);

}
