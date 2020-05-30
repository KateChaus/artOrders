package orders.model.entities;

import java.util.Objects;

public class User {

    private int id;
    private String login;
    private String password;
    private String info;
    private String avatar;
    private boolean isArtist;

    private User(){};

    public User(int id, String login, String password, String info, String avatar, boolean isArtist) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.info = info;
        this.avatar = avatar;
        this.isArtist = isArtist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isArtist() {
        return isArtist;
    }

    public void setArtist(boolean artist) {
        isArtist = artist;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", info='" + info + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isArtist=" + isArtist +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  isArtist == user.isArtist &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(info, user.info) &&
                Objects.equals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, info, avatar, isArtist);
    }
}
