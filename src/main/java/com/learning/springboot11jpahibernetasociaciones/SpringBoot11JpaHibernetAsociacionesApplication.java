package com.learning.springboot11jpahibernetasociaciones;

import com.learning.springboot11jpahibernetasociaciones.entities.Address;
import com.learning.springboot11jpahibernetasociaciones.entities.Client;
import com.learning.springboot11jpahibernetasociaciones.entities.Invoice;
import com.learning.springboot11jpahibernetasociaciones.repositories.ClientRepository;
import com.learning.springboot11jpahibernetasociaciones.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
        RemoveAddress();
    }

    @Transactional(readOnly = false)
    public void RemoveAddress(){
        Client client = Client.builder().name("Frank").lastname("Moras").build();
        Address address1 = Address.builder().street("Vasco de Gama").number(85764).build();
        Address address2 = Address.builder().street("Vergel").number(1814).build();
        client.getAddresses().add(address1);
        client.getAddresses().add(address2);
        clientRepository.save(client);
        System.out.println(client);

        Optional<Client> optionalClient = clientRepository.findById(3L);
        optionalClient.ifPresent(client1 -> {
            client1.getAddresses().remove(address1);
            clientRepository.save(client1);
            System.out.println(client1);
        });
    }

    @Transactional
    public void OneToManyFindById(){
        Optional <Client> optionalClient = clientRepository.findById(2L);
        optionalClient.ifPresent(client -> {
            client = optionalClient.get();
            Address address1 = Address.builder().street("Vasco de Gama").number(85764).build();
            Address address2 = Address.builder().street("Vergel").number(1814).build();
            client.setAddresses(Arrays.asList(address1,address2));
            clientRepository.save(client);
            System.out.println(client);
        });
    }

    @Transactional
    public void OneToMany(){
        Client client = Client.builder().name("Frank").lastname("Moras").build();
        Address address1 = Address.builder().street("Vasco de Gama").number(85764).build();
        Address address2 = Address.builder().street("Vergel").number(1814).build();
        client.getAddresses().add(address1);
        client.getAddresses().add(address2);
        clientRepository.save(client);
        System.out.println(client);
    }

    @Transactional()
    public void manyToOne(){
        Client client = Client.builder().name("Margarita").lastname("Rios").build();
        clientRepository.save(client);

        Invoice invoice = Invoice.builder().description("compras de oficina").total(145d).build();
        invoice.setClient(client);
        Invoice invoiceDb = invoiceRepository.save(invoice);
        System.out.println(invoiceDb);
    }

    @Transactional
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
