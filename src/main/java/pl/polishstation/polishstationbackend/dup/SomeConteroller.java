package pl.polishstation.polishstationbackend.dup;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SomeConteroller {
    @PostMapping("/dup")
    public Dup dupMethid(@Valid @RequestBody Dup dup) {
        System.out.println(dup);
        return dup;
    }
}
