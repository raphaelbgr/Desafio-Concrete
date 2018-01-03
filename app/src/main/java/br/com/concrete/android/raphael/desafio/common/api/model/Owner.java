package br.com.concrete.android.raphael.desafio.common.api.model;


import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class Owner implements Parcelable {

    String avatar_url;
    String id;
    String login;

    protected Owner(Parcel in) {
        avatar_url = in.readString();
        id = in.readString();
        login = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public Owner() {
    }

    public String getAvatar_url() {
        return avatar_url != null ? avatar_url : "http://nullpic.com";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login != null ? login : "N/D";
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        if (avatar_url != null ? !avatar_url.equals(owner.avatar_url) : owner.avatar_url != null)
            return false;
        return id.equals(owner.id) && login.equals(owner.login);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(avatar_url);
        parcel.writeString(id);
        parcel.writeString(login);
    }
}
