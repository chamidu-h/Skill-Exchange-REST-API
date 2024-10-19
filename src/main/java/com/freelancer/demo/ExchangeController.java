package com.freelancer.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exchanges")

public class ExchangeController {
	
	@Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Get all exchanges
    @GetMapping
    public List<Exchange> getAllExchanges() {
        return exchangeRepository.findAll();
    }

    // Get exchange by ID
    @GetMapping("/{id}")
    public ResponseEntity<Exchange> getExchangeById(@PathVariable Long id) {
        Optional<Exchange> exchange = exchangeRepository.findById(id);
        return exchange.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new exchange
    @PostMapping
    public ResponseEntity<Exchange> createExchange(@RequestBody Exchange exchange) {
        
        Long requesterId = exchange.getRequester().getId(); 

        // Get the User from the repository
        Optional<User> userOptional = userRepository.findById(requesterId);
        
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); 
        }

        User requester = userOptional.get();
        exchange.setRequester(requester); // Set the requester in the Exchange object

        // Get the Service from the repository
        Long serviceId = exchange.getService().getId(); 
        Optional<Service> serviceOptional = serviceRepository.findById(serviceId);

        if (!serviceOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); 
        }

        Service service = serviceOptional.get();
        exchange.setService(service); // Set the service in the Exchange response

        // Set the provider in the Exchange response
        User provider = service.getProvider(); 
        exchange.setProvider(provider); // Set the provider in the Exchange rsponse

        // Add the service to the requester's details
        requester.getRequestedServices().add(serviceId); 
        userRepository.save(requester); 
        
        Exchange savedExchange = exchangeRepository.save(exchange);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExchange);
    }

    // Update existing exchange
    @PutMapping("/{id}")
    public ResponseEntity<Exchange> updateExchange(@PathVariable Long id, @RequestBody Exchange exchangeDetails) {
        Optional<Exchange> exchangeOptional = exchangeRepository.findById(id);

        if (exchangeOptional.isPresent()) {
            Exchange exchange = exchangeOptional.get();

            
            if (exchangeDetails.getRequester() != null) {
                Optional<User> requester = userRepository.findById(exchangeDetails.getRequester().getId());
                if (requester.isPresent()) {
                    exchange.setRequester(requester.get());
                } else {
                    return ResponseEntity.badRequest().body(null);  
                }
            }

            
            if (exchangeDetails.getService() != null) {
                Optional<Service> service = serviceRepository.findById(exchangeDetails.getService().getId());
                if (service.isPresent()) {
                    exchange.setService(service.get());
                } else {
                    return ResponseEntity.badRequest().body(null);  
                }
            }

            
            if (exchangeDetails.getProvider() != null) {
                Optional<User> provider = userRepository.findById(exchangeDetails.getProvider().getId());
                if (provider.isPresent()) {
                    exchange.setProvider(provider.get());
                } else {
                    return ResponseEntity.badRequest().body(null);  
                }
            } else {
                exchange.setProvider(null);  
            }

            // Update exchange status
            exchange.setStatus(exchangeDetails.getStatus());

          
            Exchange updatedExchange = exchangeRepository.save(exchange);
            return ResponseEntity.ok(updatedExchange);
        } else {
            return ResponseEntity.notFound().build();  
        }
    }

    // Delete exchange
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchange(@PathVariable Long id) {
        Optional<Exchange> exchangeOptional = exchangeRepository.findById(id);

        if (exchangeOptional.isPresent()) {
            Exchange exchange = exchangeOptional.get();
            
            // Get the requester and the service of exchange
            User requester = exchange.getRequester();
            Long serviceId = exchange.getService().getId(); 

            // Remove the service ID
            requester.getRequestedServices().remove(serviceId);

           
            userRepository.save(requester);

            
            exchangeRepository.delete(exchange);
            
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
