package pl.ske.project1.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.entity.ExampleModel;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ExampleController {
    private final AtomicLong idCounter = new AtomicLong();
    private static final String answer = "TEST_%s!";

    @GetMapping("/test")
    public ExampleModel test(@RequestParam(value = "additional", defaultValue = "") String additional) {
        return new ExampleModel(idCounter.incrementAndGet(), String.format(answer, additional));
    }
}
