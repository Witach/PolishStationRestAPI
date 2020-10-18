package pl.polishstation.polishstationbackend.domain.petrolstation.spec;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Data
@AllArgsConstructor
public class SpecFieldResolver {
    MultiValueMap<String, String> params;
    SpecFields field;

    List<String> getFieldValue() {
        return params.get(field.toString());
    }

    Double getIntFieldValue() {
        return Double.parseDouble(getFieldValue().get(0));
    }


}
