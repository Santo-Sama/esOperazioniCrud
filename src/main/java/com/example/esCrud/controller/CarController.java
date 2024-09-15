package com.example.esCrud.controller;


import com.example.esCrud.carRepository.CarRepository;
import com.example.esCrud.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Car create(@RequestBody Car car){
        Car carSaved = carRepository.saveAndFlush(car);
        return carSaved;
    }

    @GetMapping
    public List<Car> get(){
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    @GetMapping("/{id}")
    public Car getOne(@PathVariable long id){
        if (carRepository.existsById(id)) {
            Car car = carRepository.getById(id);
            return car;
        }
        else {
            return new Car();
        }
    }

    @PutMapping("/{id}")
    public Car updateType(@PathVariable long id, @RequestParam(required = true) String type){
        if (carRepository.existsById(id)){
            Car car = carRepository.getById(id);
            car.setType(type);
            carRepository.saveAndFlush(car);
            return car;
        }
        else {
            return new Car();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable long id){
        if (carRepository.existsById(id)){
            carRepository.deleteById(id);
        }
        else {
            throw new HttpStatusCodeException(HttpStatusCode.valueOf(409), "Car non esistente") {};
        }
    }

    @DeleteMapping
    public void deleteAllCars(){
        carRepository.deleteAll();
    }
}
