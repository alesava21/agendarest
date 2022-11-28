package it.prova.agenda.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agenda.dto.AgendaDTO;
import it.prova.agenda.service.AgendaService;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {
	
	@Autowired
	private AgendaService agendaService;
	
	@GetMapping
	public List<AgendaDTO> getAll() {
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllElements(true));
	}

}
