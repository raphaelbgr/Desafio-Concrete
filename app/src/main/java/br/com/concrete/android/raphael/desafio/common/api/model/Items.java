package br.com.concrete.android.raphael.desafio.common.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class Items implements Comparable, Parcelable {

    public int id;
    public String name;
    public String description;
    public Owner owner;
    public String forks_count;
    public String stargazers_count;
    public String full_name;


    protected Items(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        forks_count = in.readString();
        stargazers_count = in.readString();
        full_name = in.readString();
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    public String getDescription() {
        return description != null ? description : "Descrição não disponível.";
    }


    public Owner getOwner() {
        return owner != null ? owner : new Owner();
    }

    public String getForks_count() {
        return forks_count != null ? forks_count : "N/D";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name != null ? name : "N/D";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStargazers_count() {
        return stargazers_count != null ? stargazers_count : "N/D";
    }

    public String getFull_name() {
        return full_name != null ? full_name : "N/D";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Items items = (Items) o;

        if (id != items.id) return false;
        if (!name.equals(items.name)) return false;
        if (description != null ? !description.equals(items.description) : items.description != null)
            return false;
        if (!owner.equals(items.owner)) return false;
        if (!forks_count.equals(items.forks_count)) return false;
        return stargazers_count.equals(items.stargazers_count) && full_name.equals(items.full_name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + owner.hashCode();
        result = 31 * result + forks_count.hashCode();
        result = 31 * result + stargazers_count.hashCode();
        result = 31 * result + full_name.hashCode();
        return result;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(forks_count);
        parcel.writeString(stargazers_count);
        parcel.writeString(full_name);
    }
}
