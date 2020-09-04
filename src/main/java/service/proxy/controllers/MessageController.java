package service.proxy.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import service.proxy.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@RestController
@RequestMapping("/api/v1/msg")
public class MessageController {
    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "first");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "second");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "third");
        }});
    }};

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new)
                ;
    }

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> get(@PathVariable String id) {
        return getMessage(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping
    public Map<String, String> update(@RequestBody Map<String, String> message) {
        Map<String, String> entity = getMessage(message.get("id"));
        entity.putAll(message);
        return entity;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> entity = getMessage(id);
        messages.remove(entity);
    }

    /*
    from browser console
    fetch('/msg', {method: "POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({text:'fourth'})}).then(result => console.log(result))
    fetch('/msg', {method: "POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({text:'5'})}).then(result => console.log(result))
    fetch('/msg', {method: "PUT", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({id:'5', text:'fifth'})}).then(result => console.log(result))
    fetch('/msg/4', {method: "DELETE"}).then(result => console.log(result))
     */
}
