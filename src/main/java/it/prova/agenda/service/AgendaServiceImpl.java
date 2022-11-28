package it.prova.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.agenda.model.Agenda;
import it.prova.agenda.repository.agenda.AgendaRepository;
import it.prova.agenda.web.api.exception.AgendaNotFoundException;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository agendaRepository;

	@Override
	public List<Agenda> listAllElements(boolean eager) {
		if (eager)
			return (List<Agenda>) agendaRepository.findAllAgendaEager();

		return (List<Agenda>) agendaRepository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		return agendaRepository.findById(id).orElse(null);

	}

	@Override
	public Agenda caricaSingoloElementoEager(Long id) {
		return agendaRepository.findSingleAgendeEager(id);
	}

	@Transactional
	public Agenda aggiorna(Agenda agendaInstance) {
		return agendaRepository.save(agendaInstance);
	}

	@Override
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		return agendaRepository.save(agendaInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		agendaRepository.findById(idToRemove)
				.orElseThrow(() -> new AgendaNotFoundException("Film not found con id: " + idToRemove));
		agendaRepository.deleteById(idToRemove);

	}

	@Override
	public List<Agenda> findByExample(Agenda example) {
		return agendaRepository.findByExample(example);
	}

	@Override
	public List<Agenda> findByDescrizione(String descrizione) {
		return agendaRepository.findByDescrizione(descrizione);
	}

}
