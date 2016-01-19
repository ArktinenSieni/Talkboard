package talkboard.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mcsieni on 19.1.2016.
 */
@Service
public class ToolService {
    public List<String> collectErrors (BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        return errors;
    }
}
