package it.prova.agenda.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agenda.dto.AgendaDTO;
import it.prova.agenda.model.Agenda;
import it.prova.agenda.service.AgendaService;
import it.prova.agenda.service.UtenteService;
import it.prova.agenda.web.api.exception.AgendaNotFoundException;
import it.prova.agenda.web.api.exception.IdNotNullForInsertException;
import it.prova.agenda.web.api.exception.PermisNegatedException;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<AgendaDTO> getAll() {
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllElements(true));
	}

	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Agenda agenda = agendaService.caricaSingoloElemento(id);

		if (agenda == null)
			throw new AgendaNotFoundException("Agenda not found con id: " + id);

		return AgendaDTO.buildAgendaDTOFromModel(agenda);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AgendaDTO createNew(@Valid @RequestBody AgendaDTO agendaInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if(agendaInput.getId() != null)
			throw new AgendaNotFoundException("id non nullo impossibile inserire un nuovo record");
		
		Agenda result = agendaService.inserisciNuovo(agendaInput.buildAgendaModel());
		return AgendaDTO.buildAgendaDTOFromModel(result);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void aggiorna(@Valid @RequestBody AgendaDTO agenda) {
		
		if(agenda.getId() == null)
			throw new AgendaNotFoundException("Specifica l'id");
		
		agendaService.aggiorna(agenda.buildAgendaModel());
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void elimina(@PathVariable(name = "id", required = true) Long id) throws PermisNegatedException {
		agendaService.rimuovi(id);
	}

}
