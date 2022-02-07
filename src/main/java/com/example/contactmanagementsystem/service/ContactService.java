package com.example.contactmanagementsystem.service;

import com.example.contactmanagementsystem.model.Contact;
import com.example.contactmanagementsystem.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public void addContact(Contact contact) {
        if (isValidPhoneNumber(contact.getPhoneNumber())) {
            contactRepository.save(contact);
        }
    }

    @Transactional
    public void updateContactName(Long id, String name) {
        Contact contact = findContactById(id);
        contact.setName(name);
    }

    @Transactional
    public void updateContactPhoneNumber(Long id, String phoneNumber) {
        Contact contact = findContactById(id);
        if (isValidPhoneNumber(phoneNumber)) {
            contact.setPhoneNumber(phoneNumber);
        }
    }

    @Transactional
    public void updateContactIsCalled(Long id) {
        Contact contact = findContactById(id);
        contact.setCalled(!contact.isCalled());
    }

    @Transactional
    public void updateContactIsReached(Long id) {
        Contact contact = findContactById(id);
        contact.setReached(!contact.isReached());
    }

    @Transactional
    public void updateContactIsInterested(Long id) {
        Contact contact = findContactById(id);
        contact.setInterested(!contact.isInterested());
    }

    public Contact findContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id does not match any id"));
    }

    public void deleteContact(Long id) {
        Contact contact = findContactById(id);
        contactRepository.delete(contact);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (contactRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("Phone number is already in the database");
        }
        if (!phoneNumber.matches("^(?:\\d{2}([-.])\\d{3}\\1\\d{3}\\1\\d{3}|\\d{11})$")) {
            throw new IllegalArgumentException("Phone number does not suit syntax");
        }
        return true;
    }

}
