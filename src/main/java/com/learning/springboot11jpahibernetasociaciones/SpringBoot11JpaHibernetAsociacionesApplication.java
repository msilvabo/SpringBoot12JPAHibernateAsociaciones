package com.learning.springboot11jpahibernetasociaciones;

import com.learning.springboot11jpahibernetasociaciones.entities.Client;
import com.learning.springboot11jpahibernetasociaciones.entities.Invoice;
import com.learning.springboot11jpahibernetasociaciones.repositories.ClientRepository;
import com.learning.springboot11jpahibernetasociaciones.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class SpringBoot11JpaHibernetAsociacionesApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot11JpaHibernetAsociacionesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        manyToOneFindById();
    }

    public void manyToOne(){
        Client client = Client.builder().name("Margarita").lastname("Rios").build();
        clientRepository.save(client);

        Invoice invoice = Invoice.builder().description("compras de oficina").total(145d).build();
        invoice.setClient(client);
        Invoice invoiceDb = invoiceRepository.save(invoice);
        System.out.println(invoiceDb);
    }
    public void manyToOneFindById() {
        Optional<Client> optionalClient = clientRepository.findById(1L);
        Client client = null;
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
        }

        Invoice invoice = Invoice.builder().description("compras de comercio minorista").total(1145.89d).build();
        invoice.setClient(client);
        Invoice invoiceDb = invoiceRepository.save(invoice);
        System.out.println(invoiceDb);
    }
}
