package it.prova.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;
import it.prova.agenda.repository.agenda.AgendaRepository;
import it.prova.agenda.web.api.exception.AgendaNotFoundException;
import it.prova.agenda.web.api.exception.PermisNegatedException;
import it.prova.agenda.web.api.exception.UtenteNotLoggedException;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository agendaRepository;

	@Autowired
	private UtenteService utenteService;

	@Override
	public List<Agenda> listAllElements(boolean eager) {
		if (eager)
			return (List<Agenda>) agendaRepository.findAllAgendaEager();

		return (List<Agenda>) agendaRepository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		Agenda result = agendaRepository.findById(id).orElse(null);

		if (result == null) {
			throw new AgendaNotFoundException("Agenda non trovata!");
		}

		if (utenteLoggato.getRuoli().stream().filter(r -> r.getCodice().equals("ROLE_ADMIN")).findAny()
				.orElse(null) != null || utenteLoggato.getId() == result.getUtente().getId()) {
			return result;
		} else {
			throw new PermisNegatedException("Non hai i permessi per visualizzare questo elemento!");
		}

	}

	@Override
	public Agenda caricaSingoloElementoEager(Long id) {
		return agendaRepository.findSingleAgendeEager(id);
	}

	@Transactional
	public void aggiorna(Agenda agendaInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		if (utenteLoggato.getRuoli().stream().filter(r -> r.getCodice().equals("ROLE_ADMIN")).findAny()
				.orElse(null) != null
				|| (agendaInstance != null && utenteLoggato.getId() == agendaInstance.getUtente().getId())) {
			agendaRepository.save(agendaInstance);
		} else {
			throw new PermisNegatedException("Non hai i permessi per modificare questo elemento!");
		}
	}

	@Override
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		if (utenteLoggato == null) {
			throw new UtenteNotLoggedException("Nessun utente loggato!");
		}

		agendaInstance.setUtente(utenteLoggato);

		return agendaRepository.save(agendaInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		Agenda result = agendaRepository.findById(idToRemove).orElse(null);

		if (result == null) {
			throw new AgendaNotFoundException("Agenda non trovata!");
		}

		if (utenteLoggato.getRuoli().stream().filter(r -> r.getCodice().equals("ROLE_ADMIN")).findAny()
				.orElse(null) != null || utenteLoggato.getId() == result.getUtente().getId()) {
			agendaRepository.deleteById(idToRemove);
		} else {
			throw new PermisNegatedException("Non hai i permessi per rimuovere questo elemento!");
		}
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
