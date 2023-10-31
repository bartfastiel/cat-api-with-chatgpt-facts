package com.example.reactaxiosexampleserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

@RestController
@RequestMapping("/api/cats")
@RequiredArgsConstructor
public class CatController {

    private final CatFactService catFactService;

    private List<Cat> cats = List.of(
            new Cat("58c5ac6c-dc3e-426d-928e-4351b4b12827", "Mittens", "Tabby", 3),
            new Cat("057535b1-82ea-4fd4-be1b-bb552801d1f4", "Fluffy", "Persian", 5),
            new Cat("461e935b-0383-4934-b90d-cc28a619dd45", "Paws", "Siamese", 2)
    );

    @GetMapping
    public List<Cat> getCats() {
        return cats;
    }

    @PostMapping
    public Cat addCat(@RequestBody NewCat newCat) {
        var cat = newCat.toCat(UUID.randomUUID().toString());
        cats = concat(cats.stream(), Stream.of(cat))
                .toList();
        return cat;
    }

    @DeleteMapping("/{id}")
    public void deleteCat(@PathVariable String id) {
        cats = cats.stream()
                .filter(c -> !c.id().equals(id))
                .toList();
    }

    @PutMapping("/{id}")
    public Cat insertOrUpdateCat(@PathVariable String id, @RequestBody Cat cat) {
        var found = new AtomicBoolean(false);
        cats = cats.stream()
                .map(c -> {
                    if (c.id().equals(id)) {
                        found.set(true);
                        return cat;
                    } else {
                        return c;
                    }
                })
                .toList();
        if (!found.get()) {
            cats = concat(cats.stream(), Stream.of(cat))
                    .toList();
        }
        return cat;
    }

    @GetMapping("/{id}")
    public Cat getCat(@PathVariable String id) {
        return cats.stream()
                .filter(c -> c.id().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @GetMapping("/{id}/story")
    public String getStory(@PathVariable String id) {
        return catFactService.getStory(getCat(id));
    }

    @GetMapping("/fact")
    public CatFact getCatFact() {
        return catFactService.getCatFact();
    }
}
