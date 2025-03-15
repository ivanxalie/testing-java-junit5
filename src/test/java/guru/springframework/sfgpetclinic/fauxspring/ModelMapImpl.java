package guru.springframework.sfgpetclinic.fauxspring;

import java.util.HashMap;
import java.util.Map;

public class ModelMapImpl implements Model {
    Map<String, Object> storage = new HashMap<>();

    @Override
    public void addAttribute(String key, Object o) {
        storage.put(key, o);
    }

    @Override
    public void addAttribute(Object o) {

    }

    public Map<String, Object> storage() {
        return storage;
    }
}
