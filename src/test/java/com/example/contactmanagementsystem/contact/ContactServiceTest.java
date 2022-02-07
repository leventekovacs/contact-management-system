package com.example.contactmanagementsystem.contact;

import com.example.contactmanagementsystem.model.Contact;
import com.example.contactmanagementsystem.repository.ContactRepository;
import com.example.contactmanagementsystem.service.ContactService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService underTest;

    Contact contact;

    @BeforeEach
    private void setUp() {
        contact = new Contact("test","06001112222");
    }

    @AfterEach
    private void tearDown() {
        contact = null;
    }

    @Test
    void canGetContacts() {
        //when
        underTest.getContacts();
        //then
        verify(contactRepository).findAll();
    }

    @Test
    void canAddContact() {
        //when
        underTest.addContact(contact);
        //then
        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactRepository).save(contactArgumentCaptor.capture());
        assertThat(contactArgumentCaptor.getValue()).isEqualTo(contact);
    }

    @Test
    void willThrowWhenPhoneNumberIsTaken() {
        //given
        given(contactRepository.findByPhoneNumber(contact.getPhoneNumber()))
                .willReturn(Optional.of(contact));
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidPhoneNumber("06001112222"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Phone number is already in the database");

        verify(contactRepository, never()).save(any());
    }

    @Test
    void willThrowWhenPhoneNumberDoesNotSuitSyntax() {
        //when
        //then
        assertThatThrownBy( () -> underTest.isValidPhoneNumber("060011122223"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Phone number does not suit syntax");

        assertThatThrownBy( () -> underTest.isValidPhoneNumber("06AA1112222"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Phone number does not suit syntax");

        verify(contactRepository, never()).save(any());
    }

    @Test
    void canUpdateContactName() {
        //given
        given(contactRepository.findById(1L))
                .willReturn(Optional.of(contact));
        String contactNameBeforeUpdate = contact.getName();
        //when
        underTest.updateContactName(1L,"update succeed");
        //then
        assertThat(contactNameBeforeUpdate).isNotEqualTo(contact.getName());
    }

    @Test
    void updateContactPhoneNumber() {
        //given
        given(contactRepository.findById(1L))
                .willReturn(Optional.of(contact));
        String contactPhoneNumberBeforeUpdate = contact.getPhoneNumber();
        //when
        underTest.updateContactPhoneNumber(1L,"06009998888");
        //then
        assertThat(contactPhoneNumberBeforeUpdate).isNotEqualTo(contact.getPhoneNumber());
    }

    @Test
    void updateContactIsCalled() {
        //given
        given(contactRepository.findById(1L))
                .willReturn(Optional.of(contact));
        boolean contactIsCalledBeforeUpdate = contact.isCalled();
        //when
        underTest.updateContactIsCalled(1L);
        //then
        assertThat(contactIsCalledBeforeUpdate).isNotEqualTo(contact.isCalled());
    }

    @Test
    void updateContactIsReached() {
        //given
        given(contactRepository.findById(1L))
                .willReturn(Optional.of(contact));
        boolean contactIsReachedBeforeUpdate = contact.isReached();
        //when
        underTest.updateContactIsReached(1L);
        //then
        assertThat(contactIsReachedBeforeUpdate).isNotEqualTo(contact.isReached());
    }

    @Test
    void updateContactIsInterested() {
        //given
        given(contactRepository.findById(1L))
                .willReturn(Optional.of(contact));
        boolean contactIsInterestedBeforeUpdate = contact.isInterested();
        //when
        underTest.updateContactIsInterested(1L);
        //then
        assertThat(contactIsInterestedBeforeUpdate).isNotEqualTo(contact.isInterested());
    }

    @Test
    void canDeleteContact() {
        //give
        given(contactRepository.findById(1L))
                .willReturn(Optional.of(contact));
        //when
        underTest.deleteContact(1L);
        //then
        verify(contactRepository).delete(contact);
    }

    @Test
    void willThrownWhenIdIsNotFound() {
        //when
        //then
        assertThatThrownBy( () -> underTest.findContactById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id does not match any id");

        verify(contactRepository, never()).delete(any());
    }
}