import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {
    private ContactService service;
    private Contact contact;

    @BeforeEach
    public void setUp() {
        service = new ContactService();
        contact = new Contact("001", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);
    }

    @Test
    public void testAddContactSuccessfully() {
        Contact newContact = new Contact("002", "Jane", "Smith", "0987654321", "456 Elm St");
        service.addContact(newContact);
        assertDoesNotThrow(() -> service.updateAddress("002", "789 Pine St"));
    }

    @Test
    public void testAddDuplicateContactFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addContact(new Contact("001", "Duplicate", "User", "1112223333", "Dup St"));
        });
    }

    @Test
    public void testDeleteContactSuccessfully() {
        service.deleteContact("001");
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName("001", "NewName");
        });
    }

    @Test
    public void testUpdateContactFieldsSuccessfully() {
        service.updateFirstName("001", "NewFirst");
        service.updateLastName("001", "NewLast");
        service.updatePhone("001", "5555555555");
        service.updateAddress("001", "999 New St");

        assertEquals("NewFirst", contact.getFirstName());
        assertEquals("NewLast", contact.getLastName());
        assertEquals("5555555555", contact.getPhone());
        assertEquals("999 New St", contact.getAddress());
    }

    @Test
    public void testUpdateInvalidContactFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhone("999", "1231231234");
        });
    }
}
