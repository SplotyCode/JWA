package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.core.Contact;
import de.splotycode.jwa.core.ContactStatus;
import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.packets.ContactsPacket;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ContactResponse implements Response {

    private Map<String, Contact> contacts = new HashMap<>();

    @Override
    public Packet getPacket() {
        return new ContactsPacket();
    }

    public Contact getFirstContact() {
        return getContacts().iterator().next();
    }

    public Contact getContact(String number) {
        return contacts.get(number);
    }

    public Collection<Contact> getContacts() {
        return contacts.values();
    }

    public Map<String, Contact> getContactsMap() {
        return contacts;
    }

    @Override
    public void read(ResponseContext context) {
        JSONArray contacts = context.getObject().getJSONArray("contacts");
        for (int i = 0; i < contacts.length(); i++) {
            JSONObject rawContact = contacts.getJSONObject(i);

            String number = rawContact.getString("input");
            ContactStatus status = rawContact.getEnum(ContactStatus.class, "status");

            String whatsappId = null;
            if (status == ContactStatus.VALID) {
                whatsappId = rawContact.getString("wa_id");
            }

            this.contacts.put(number, new Contact(number, whatsappId, status));
        }
     }
}
