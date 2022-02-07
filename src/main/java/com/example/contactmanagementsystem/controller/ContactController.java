package com.example.contactmanagementsystem.controller;

import com.example.contactmanagementsystem.model.Contact;
import com.example.contactmanagementsystem.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping()
    public List<Contact> getContacts() {
        return contactService.getContacts();
    }

    @PostMapping()
    public void addContact(@RequestBody Contact contact) {
        contactService.addContact(contact);
    }

    @PatchMapping(path = "/name/{id}")
    public void updateContactName(
            @PathVariable(value = "id") Long id,
            @RequestParam String name) {
        contactService.updateContactName(id, name);
    }

    @PatchMapping(path = "/phoneNumber/{id}")
    public void updateContactPhoneNumber(
            @PathVariable(value = "id") Long id,
            @RequestParam String phoneNumber) {
        contactService.updateContactPhoneNumber(id, phoneNumber);
    }

    @PatchMapping(path = "/isCalled/{id}")
    public void updateContactIsCalled(
            @PathVariable(value = "id") Long id) {
        contactService.updateContactIsCalled(id);
    }

    @PatchMapping(path = "/isReached/{id}")
    public void updateContactIsReached(
            @PathVariable(value = "id") Long id) {
        contactService.updateContactIsReached(id);
    }

    @PatchMapping(path = "/isInterested/{id}")
    public void updateContactIsInterested(
            @PathVariable(value = "id") Long id) {
        contactService.updateContactIsInterested(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteContact(@PathVariable(name = "id") Long id) {
        contactService.deleteContact(id);
    }
}
