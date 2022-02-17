package com.contactmanagementsystem.contact;

import com.contactmanagementsystem.model.Contact;
import com.contactmanagementsystem.repository.ContactRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository underTest;

    @AfterEach
    void teardown() {
        underTest.deleteAll();
    }

    @Test
    @Description("It should find a contact by number")
    void findByPhoneNumber() {
        //given
        underTest.save(new Contact("Test Contact", "06308889999"));
        //when
        boolean expected = underTest.findByPhoneNumber("06308889999").isPresent();
        //then
        assertThat(expected).isTrue();
    }

    @Test
    @Description("It should return with an empty Optional")
    void findByPhoneNumberThatDoesNotExist() {
        //given
        underTest.save(new Contact("Test Contact", "06112223333"));
        //when
        boolean expected = underTest.findByPhoneNumber("06223334444").isPresent();
        //then
        assertThat(expected).isFalse();
    }
}