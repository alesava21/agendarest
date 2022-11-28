package it.prova.agenda.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.agenda.model.Agenda;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgendaDTO {

	private Long id;

	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;

	@NotNull(message = "{dataOraInizio.notnull}")
	private LocalDateTime dataOraInizio;

	@NotNull(message = "{dataOraFine.notnull}")
	private LocalDateTime dataOraFine;

	public AgendaDTO() {
	}

	public AgendaDTO(Long id, String descrizione, LocalDateTime dataOraInizio, LocalDateTime dataOraFine) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
	}

	public AgendaDTO(String descrizione, LocalDateTime dataOraInizio, LocalDateTime dataOraFine) {
		super();
		this.descrizione = descrizione;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataOraInizio() {
		return dataOraInizio;
	}

	public void setDataOraInizio(LocalDateTime dataOraInizio) {
		this.dataOraInizio = dataOraInizio;
	}

	public LocalDateTime getDataOraFine() {
		return dataOraFine;
	}

	public void setDataOraFine(LocalDateTime dataOraFine) {
		this.dataOraFine = dataOraFine;
	}

	public Agenda buildAgendaModel() {
		Agenda result = new Agenda(this.id, this.descrizione, this.dataOraInizio, this.dataOraFine);
		return result;
	}

	public static AgendaDTO buildAgendaDTOFromModel(Agenda agendaModel) {
		AgendaDTO result = new AgendaDTO(agendaModel.getId(), agendaModel.getDescrizione(),
				agendaModel.getDataOraInizio(), agendaModel.getDataOraFine());
		return result;
	}

	public static List<AgendaDTO> createAgendaDTOListFromModelList(List<Agenda> modelListInput) {
		return modelListInput.stream().map(agendaEntity -> {
			return AgendaDTO.buildAgendaDTOFromModel(agendaEntity);
		}).collect(Collectors.toList());
	}

	public static Set<AgendaDTO> createFilmDTOSetFromModelSet(Set<Agenda> modelListInput) {
		return modelListInput.stream().map(agendaEntity -> {
			return AgendaDTO.buildAgendaDTOFromModel(agendaEntity);
		}).collect(Collectors.toSet());
	}

}
