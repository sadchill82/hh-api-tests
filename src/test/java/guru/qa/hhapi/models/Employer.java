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
public class Employer {
    private String id;
    private String name;
    private String url;
    private String alternateUrl;
    private Boolean trusted;
    private String type;
}
