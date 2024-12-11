package pl.lodz.p.ias.io.mapy.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.mapy.model.MapPoint;
import pl.lodz.p.ias.io.mapy.service.MapService;

@RestController
@RequestMapping("/map")
@AllArgsConstructor
public class MapController {
    private MapService mapService;

    @GetMapping
    public ResponseEntity<Object> getMapPoints() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapService.getPoints());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Map Points not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMapPoint(@PathVariable("id") long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapService.getPoint(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Map Point not found");
        }
    }

    @PostMapping
    public ResponseEntity<Object> addMapPoint(@RequestBody MapPoint mapPoint) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mapService.addPoint(mapPoint));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Map Point not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMapPoint(@PathVariable("id") long id) {
        try {
            mapService.removePoint(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Map Point not found");
        }
    }

    @PutMapping("status/{id}")
    public ResponseEntity<Object> updateMapPoint(@PathVariable("id") long id, @RequestBody boolean status) {
        try {
            mapService.changeStatus(id, status);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Map Point not found");
        }
    }
}
