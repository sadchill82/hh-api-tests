package guru.qa.hhapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacanciesResponse {
    private List<Vacancy> items;
    private Integer found;
    private Integer pages;
    private Integer page;
    private Integer perPage;
}
