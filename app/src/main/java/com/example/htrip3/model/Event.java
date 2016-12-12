package com.example.htrip3.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Event implements Parcelable {

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
    public @SerializedName("id") String id;
    public @SerializedName("title") String title;
    public @SerializedName("name") String name;
    public @SerializedName("description") String description;
    public @SerializedName("type") String type;
    public @SerializedName("date") Date date;
    public @SerializedName("createdAt") Date createdAt;
    public @SerializedName("time") String time;
    public @SerializedName("location") String location;
    public @SerializedName("instructions") String instructions;
    public @SerializedName("min") int min;
    public @SerializedName("max") int max;
    public @SerializedName("deleted") boolean deleted;

    public Event() {
    }

    protected Event(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.type = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        this.time = in.readString();
        this.location = in.readString();
        this.instructions = in.readString();
        this.min = in.readInt();
        this.max = in.readInt();
        this.deleted = in.readByte() != 0;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.type);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeString(this.time);
        dest.writeString(this.location);
        dest.writeString(this.instructions);
        dest.writeInt(this.min);
        dest.writeInt(this.max);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
    }
}
