package com.example.contactmanagementsystem.contact;

import com.example.contactmanagementsystem.model.Contact;
import com.example.contactmanagementsystem.repository.ContactRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository testRepository;

    @AfterEach
    void teardown() {
        testRepository.deleteAll();
    }

    @Test
    @Description("It should find a contact by number")
    void findByPhoneNumber() {
        //given
        testRepository.save(new Contact("Test Contact", "06308889999"));
        //when
        boolean expected = testRepository.findByPhoneNumber("06308889999").isPresent();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    @Description("It should return with an empty Optional")
    void findByPhoneNumberThatDoesNotExist() {
        //given
        testRepository.save(new Contact("Test Contact", "06112223333"));
        //when
        boolean expected = testRepository.findByPhoneNumber("06223334444").isPresent();
        //then
        assertThat(expected).isFalse();
    }
}