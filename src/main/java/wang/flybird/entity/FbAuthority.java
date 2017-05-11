package wang.flybird.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import wang.flybird.entity.enums.AuthorityName;

import java.util.List;

@Entity
@Table(name = "FB_AUTHORITY")
public class FbAuthority {

    @Id
    @Column(name = "ID")
	@GeneratedValue(generator="id")
	@GenericGenerator(name = "id", strategy = "assigned")
    private String id;

    @Column(name = "NAME", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<FbUser> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public List<FbUser> getUsers() {
        return users;
    }

    public void setUsers(List<FbUser> users) {
        this.users = users;
    }
}