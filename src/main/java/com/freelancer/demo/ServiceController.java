package com.freelancer.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")

public class ServiceController {
	
	@Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;
	
	
	// Get all services
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Get service by ID
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        Optional<Service> service = serviceRepository.findById(id);
        return service.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new service
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        
        Optional<User> provider = userRepository.findById(service.getProvider().getId());

        if (provider.isPresent()) {
            User user = provider.get();

            
            service.setProvider(user);
            Service savedService = serviceRepository.save(service);

            //add the service to the user's offeredServices
            user.getOfferedServices().add(savedService.getId());
            userRepository.save(user);

            return ResponseEntity.ok(savedService);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update existing service
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service serviceDetails) {
        // Find the existing service by id
        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            Service service = optionalService.get();

            // Update the service details
            service.setTitle(serviceDetails.getTitle());
            service.setDescription(serviceDetails.getDescription());
            service.setSkillType(serviceDetails.getSkillType());

           
            if (serviceDetails.getProvider() != null) {
                Optional<User> provider = userRepository.findById(serviceDetails.getProvider().getId());
                if (provider.isPresent()) {
                    service.setProvider(provider.get());
                } else {
                    return ResponseEntity.badRequest().body(null);  
                }
            }

            
            Service updatedService = serviceRepository.save(service);
            return ResponseEntity.ok(updatedService);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
      
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        
        if (!serviceOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Service serviceToDelete = serviceOptional.get();

        // Remove this service from all users
        List<User> users = userRepository.findAll();
        for (User user : users) {
           
            user.getOfferedServices().removeIf(offerId -> offerId.equals(id)); 
            userRepository.save(user); 
        }

     
        serviceRepository.delete(serviceToDelete);
        return ResponseEntity.noContent().build();
    }

}
