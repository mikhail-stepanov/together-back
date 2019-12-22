package ru.together.database.entities.auto;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import ru.together.database.entities.UserPastEvent;
import ru.together.database.entities.UserSession;
import ru.together.database.entities.UserTicket;

/**
 * Class _User was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _User extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<LocalDateTime> CREATED_DATE = Property.create("createdDate", LocalDateTime.class);
    public static final Property<LocalDateTime> DELETED_DATE = Property.create("deletedDate", LocalDateTime.class);
    public static final Property<String> EMAIL = Property.create("email", String.class);
    public static final Property<Boolean> IS_VERIFIED = Property.create("isVerified", Boolean.class);
    public static final Property<LocalDateTime> MODIFIED_DATE = Property.create("modifiedDate", LocalDateTime.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<String> PHONE = Property.create("phone", String.class);
    public static final Property<Integer> USER_ID = Property.create("userId", Integer.class);
    public static final Property<List<UserPastEvent>> USER_TO_PAST = Property.create("userToPast", List.class);
    public static final Property<List<UserSession>> USER_TO_SESSION = Property.create("userToSession", List.class);
    public static final Property<List<UserTicket>> USER_TO_TICKET1 = Property.create("userToTicket1", List.class);

    public void setCreatedDate(LocalDateTime createdDate) {
        writeProperty("createdDate", createdDate);
    }
    public LocalDateTime getCreatedDate() {
        return (LocalDateTime)readProperty("createdDate");
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        writeProperty("deletedDate", deletedDate);
    }
    public LocalDateTime getDeletedDate() {
        return (LocalDateTime)readProperty("deletedDate");
    }

    public void setEmail(String email) {
        writeProperty("email", email);
    }
    public String getEmail() {
        return (String)readProperty("email");
    }

    public void setIsVerified(boolean isVerified) {
        writeProperty("isVerified", isVerified);
    }
	public boolean isIsVerified() {
        Boolean value = (Boolean)readProperty("isVerified");
        return (value != null) ? value.booleanValue() : false;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        writeProperty("modifiedDate", modifiedDate);
    }
    public LocalDateTime getModifiedDate() {
        return (LocalDateTime)readProperty("modifiedDate");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setPhone(String phone) {
        writeProperty("phone", phone);
    }
    public String getPhone() {
        return (String)readProperty("phone");
    }

    public void setUserId(int userId) {
        writeProperty("userId", userId);
    }
    public int getUserId() {
        Object value = readProperty("userId");
        return (value != null) ? (Integer) value : 0;
    }

    public void addToUserToPast(UserPastEvent obj) {
        addToManyTarget("userToPast", obj, true);
    }
    public void removeFromUserToPast(UserPastEvent obj) {
        removeToManyTarget("userToPast", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<UserPastEvent> getUserToPast() {
        return (List<UserPastEvent>)readProperty("userToPast");
    }


    public void addToUserToSession(UserSession obj) {
        addToManyTarget("userToSession", obj, true);
    }
    public void removeFromUserToSession(UserSession obj) {
        removeToManyTarget("userToSession", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<UserSession> getUserToSession() {
        return (List<UserSession>)readProperty("userToSession");
    }


    public void addToUserToTicket1(UserTicket obj) {
        addToManyTarget("userToTicket1", obj, true);
    }
    public void removeFromUserToTicket1(UserTicket obj) {
        removeToManyTarget("userToTicket1", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<UserTicket> getUserToTicket1() {
        return (List<UserTicket>)readProperty("userToTicket1");
    }


}
