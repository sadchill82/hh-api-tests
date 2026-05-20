package guru.qa.hhapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vacancy {
    private String id;
    private String name;
    private String url;
    private String alternateUrl;
    private Salary salary;
    private Employer employer;
    private Area area;
    private Boolean archived;
}
