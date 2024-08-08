package com.learning.springboot11jpahibernetasociaciones;

import com.learning.springboot11jpahibernetasociaciones.entities.*;
import com.learning.springboot11jpahibernetasociaciones.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class SpringBoot11JpaHibernetAsociacionesApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ClientDetailsRepository clientDetailsRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot11JpaHibernetAsociacionesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        manyToManyFind();
    }

    @Transactional
    public void manyToManyFind(){
        Optional<Student> studentOptional1 = studentRepository.findById(1L);
        Optional<Student> studentOptional2 = studentRepository.findById(2L);
        Student student1 = studentOptional1.get();
        Student student2 = studentOptional2.get();

        Course course1 = courseRepository.findById(1L).get();
        Course course2 = courseRepository.findById(2L).get();
        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course1));
        studentRepository.saveAll(Set.of(student1, student2));

        System.out.println(student1);
        System.out.println(student2);
    }

    public void manyToMany(){
        Student student1 = Student.builder().name("Jano").lastname("Prado").build();
        Student student2 = Student.builder().name("Erba").lastname("Alanoca").build();
        Course course1 = Course.builder().name("Java Master").instructor("Andres").build();
        Course course2 = Course.builder().name("Dockers").instructor("Carlos").build();
        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course1));
        studentRepository.saveAll(Set.of(student1, student2));

        System.out.println(student1);
        System.out.println(student2);
    }

    @Transactional
    public void oneToOneBidirectionalFindById(){
        Optional<Client> clientOptional = clientRepository.findById(2L);
        clientOptional.ifPresent(client -> {
            ClientDetails clientDetails = ClientDetails.builder().premium(true).points(1200).build();
            client.setClientDetails(clientDetails);
            clientRepository.save(client);
            System.out.println(client);
        });
    }
    @Transactional
    public void oneToOneBidirectional(){
        Client client = Client.builder().name("Erba").lastname("Pura").build();
        ClientDetails clientDetails = ClientDetails.builder().premium(true).points(1200).build();
        client.setClientDetails(clientDetails);

        clientRepository.save(client);
        System.out.println(client);
    }

    @Transactional
    public void oneToOneFindById(){
        ClientDetails clientDetails = ClientDetails.builder().premium(true).points(5000).build();
        clientDetailsRepository.save(clientDetails);
        Optional<Client> clientOptional = clientRepository.findById(2L);
        clientOptional.ifPresent(client -> {
            client.setClientDetails(clientDetails);
            clientRepository.save(client);
            System.out.println(client);
        });
    }

    @Transactional
    public void oneToOne(){
        ClientDetails clientDetails = ClientDetails.builder().premium(true).points(5000).build();
        clientDetailsRepository.save(clientDetails);
        Client client = Client.builder().name("Elba").lastname("Pura").build();
        client.setClientDetails(clientDetails);
        clientRepository.save(client);

        System.out.println(client);
    }

    @Transactional
    public void removeInvoiceBidirectional(){
        Client client = Client.builder().name("Frank").lastname("Moras").build();
        Invoice invoice1 = Invoice.builder().description("compras equimamiento").total(1230d).client(client).build();
        Invoice invoice2 = Invoice.builder().description("compras mobiliario").total(5130d).client(client).build();
        client.setInvoices(Arrays.asList(invoice1,invoice2));
        clientRepository.save(client);
        System.out.println(client);
        Optional<Client> optionalClientDB = clientRepository.findById(3L);
        optionalClientDB.ifPresent(clientDB -> {
            Invoice invoice3 = Invoice.builder().description("compras equimamiento").total(1230d).client(clientDB).build();
            invoice3.setId(1L);
            Optional<Invoice> invoiceOptional = Optional.of(invoice3);
            invoiceOptional.ifPresent(invoice -> {
                clientDB.removeInvoice(invoice);
                clientRepository.save(clientDB);
                System.out.println(clientDB);
            });
        });
    }
    @Transactional
    public void removeInvoiceBidirectionalFindById(){
        Optional<Client> optionalClient = clientRepository.findById(1L);
        optionalClient.ifPresent(client -> {
            Invoice invoice1 = Invoice.builder().description("compras equimamiento").total(1230d).client(client).build();
            Invoice invoice2 = Invoice.builder().description("compras mobiliario").total(5130d).client(client).build();
            client.setInvoices(Arrays.asList(invoice1,invoice2));
            clientRepository.save(client);
            System.out.println(client);
        });
        Optional<Client> optionalClientDB = clientRepository.findById(1L);
        optionalClientDB.ifPresent(client -> {
            Invoice invoice3 = Invoice.builder().description("compras equimamiento").total(1230d).client(client).build();
            invoice3.setId(1L);
            Optional<Invoice> invoiceOptional = Optional.of(invoice3);
            invoiceOptional.ifPresent(invoice -> {
                client.removeInvoice(invoice);
//                client.getInvoices().remove(invoice);
//                invoice.setClient(null);
                clientRepository.save(client);
                System.out.println(client);
            });
        });
    }

    @Transactional
    public void oneToManyBidirectional(){
        Client client = Client.builder().name("Carlos").lastname("Arteaga").build();
        Invoice invoice1 = Invoice.builder().description("Insumos Oficina").total(123d).build();
        Invoice invoice2 = Invoice.builder().description("Material de Limpieza").total(75d).build();
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        client.setInvoices(invoices);
        invoice1.setClient(client);
        invoice2.setClient(client);
        clientRepository.save(client);
        System.out.println(client);
    }

    @Transactional
    public void removeAddressFindById(){
        Optional <Client> optionalClient = clientRepository.findById(2L);
        optionalClient.ifPresent(client -> {
            client = optionalClient.get();
            Address address1 = Address.builder().street("Vasco de Gama").number(85764).build();
            Address address2 = Address.builder().street("Vergel").number(1814).build();
            client.setAddresses(Arrays.asList(address1,address2));
            clientRepository.save(client);
            System.out.println(client);

            Optional<Client> optionalClient2 = clientRepository.findOne(2L);
            optionalClient2.ifPresent(client1 -> {
                System.out.println(client1);
                client1.getAddresses().remove(address2);
                System.out.println(client1.getAddresses());
                clientRepository.save(client1);
                System.out.println(client1);
            });
        });
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
