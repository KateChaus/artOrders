package orders.model.entities;


public class Gallery {
    private int id;
    private String site;
    private String link;
    private User user;

    public Gallery(int id, String site, String link, User user) {
        this.id = id;
        this.site = site;
        this.link = link;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "id=" + id +
                ", site='" + site + '\'' +
                ", link='" + link + '\'' +
                ", user=" + user +
                '}';
    }
}
