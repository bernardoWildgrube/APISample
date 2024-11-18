package br.edu.atitus.apisample.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.services.RegisterService;

@RestController
@RequestMapping("/registers")
public class RegisterController {

    private final RegisterService service;
    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    // Injeção de dependência do RegisterService
    public RegisterController(RegisterService service) {
        this.service = service;
    }

    // Método GET - Listar todos os registros
    @GetMapping
    public ResponseEntity<List<RegisterEntity>> getAllRegistersEntity() throws Exception {
        logger.info("Buscando todos os registros.");
        List<RegisterEntity> registers = service.findAll();
        return ResponseEntity.ok(registers);
    }

    // Método GET - Buscar registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<RegisterEntity> getRegisterById(@PathVariable UUID id) throws Exception {
        logger.info("Buscando registro com ID: {}", id);
        RegisterEntity register = service.findById(id);
        return ResponseEntity.ok(register);
    }

    // Método POST - Criar um novo registro
    @PostMapping
    public ResponseEntity<RegisterEntity> createRegister(@RequestBody RegisterEntity newRegister) throws Exception {
        validateRegisterInput(newRegister); // Validação de entrada
        logger.info("Criando novo registro: {}", newRegister);
        RegisterEntity savedRegister = service.save(newRegister); 
        return ResponseEntity.ok(savedRegister);
    }

    // Método PUT - Atualizar um registro existente
    @PutMapping("/{id}")
    public ResponseEntity<RegisterEntity> updateRegister(@PathVariable UUID id, @RequestBody RegisterEntity updatedRegister) throws Exception {
        validateRegisterInput(updatedRegister); 
        updatedRegister.setId(id); 
        logger.info("Atualizando registro com ID: {}", id);
        RegisterEntity savedRegister = service.save(updatedRegister);
        return ResponseEntity.ok(savedRegister);
    }

    // Método DELETE - Deletar um registro pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegister(@PathVariable UUID id) throws Exception {
        logger.info("Deletando registro com ID: {}", id);
        service.deleteById(id); 
        return ResponseEntity.ok("Registro deletado com sucesso!");
    }


}