package ru.together.database.entities.auto;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import ru.together.database.entities.UserPastEvent;
import ru.together.database.entities.UserTicket;

/**
 * Class _Event was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Event extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> CLOUD = Property.create("cloud", String.class);
    public static final Property<LocalDateTime> CREATED_DATE = Property.create("createdDate", LocalDateTime.class);
    public static final Property<LocalDateTime> DATE = Property.create("date", LocalDateTime.class);
    public static final Property<LocalDateTime> DELETED_DATE = Property.create("deletedDate", LocalDateTime.class);
    public static final Property<String> DESCRIPTION = Property.create("description", String.class);
    public static final Property<Boolean> IS_FUTURE = Property.create("isFuture", Boolean.class);
    public static final Property<LocalDateTime> MODIFIED_DATE = Property.create("modifiedDate", LocalDateTime.class);
    public static final Property<String> PIC_URL = Property.create("picUrl", String.class);
    public static final Property<String> PLACE = Property.create("place", String.class);
    public static final Property<String> SOUNDCLOUD = Property.create("soundcloud", String.class);
    public static final Property<String> TICKETCLOUD = Property.create("ticketcloud", String.class);
    public static final Property<String> TITLE = Property.create("title", String.class);
    public static final Property<String> YOUTUBE = Property.create("youtube", String.class);
    public static final Property<List<UserPastEvent>> EVENT_TO_PAST = Property.create("eventToPast", List.class);
    public static final Property<List<UserTicket>> EVENT_TO_TICKET = Property.create("eventToTicket", List.class);

    public void setCloud(String cloud) {
        writeProperty("cloud", cloud);
    }
    public String getCloud() {
        return (String)readProperty("cloud");
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        writeProperty("createdDate", createdDate);
    }
    public LocalDateTime getCreatedDate() {
        return (LocalDateTime)readProperty("createdDate");
    }

    public void setDate(LocalDateTime date) {
        writeProperty("date", date);
    }
    public LocalDateTime getDate() {
        return (LocalDateTime)readProperty("date");
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        writeProperty("deletedDate", deletedDate);
    }
    public LocalDateTime getDeletedDate() {
        return (LocalDateTime)readProperty("deletedDate");
    }

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setIsFuture(boolean isFuture) {
        writeProperty("isFuture", isFuture);
    }
	public boolean isIsFuture() {
        Boolean value = (Boolean)readProperty("isFuture");
        return (value != null) ? value.booleanValue() : false;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        writeProperty("modifiedDate", modifiedDate);
    }
    public LocalDateTime getModifiedDate() {
        return (LocalDateTime)readProperty("modifiedDate");
    }

    public void setPicUrl(String picUrl) {
        writeProperty("picUrl", picUrl);
    }
    public String getPicUrl() {
        return (String)readProperty("picUrl");
    }

    public void setPlace(String place) {
        writeProperty("place", place);
    }
    public String getPlace() {
        return (String)readProperty("place");
    }

    public void setSoundcloud(String soundcloud) {
        writeProperty("soundcloud", soundcloud);
    }
    public String getSoundcloud() {
        return (String)readProperty("soundcloud");
    }

    public void setTicketcloud(String ticketcloud) {
        writeProperty("ticketcloud", ticketcloud);
    }
    public String getTicketcloud() {
        return (String)readProperty("ticketcloud");
    }

    public void setTitle(String title) {
        writeProperty("title", title);
    }
    public String getTitle() {
        return (String)readProperty("title");
    }

    public void setYoutube(String youtube) {
        writeProperty("youtube", youtube);
    }
    public String getYoutube() {
        return (String)readProperty("youtube");
    }

    public void addToEventToPast(UserPastEvent obj) {
        addToManyTarget("eventToPast", obj, true);
    }
    public void removeFromEventToPast(UserPastEvent obj) {
        removeToManyTarget("eventToPast", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<UserPastEvent> getEventToPast() {
        return (List<UserPastEvent>)readProperty("eventToPast");
    }


    public void addToEventToTicket(UserTicket obj) {
        addToManyTarget("eventToTicket", obj, true);
    }
    public void removeFromEventToTicket(UserTicket obj) {
        removeToManyTarget("eventToTicket", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<UserTicket> getEventToTicket() {
        return (List<UserTicket>)readProperty("eventToTicket");
    }


}
