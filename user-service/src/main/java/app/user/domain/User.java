package app.user.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author steve
 */
@Table(name = "user_info")
public class User {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "email")
    public String email;

    @NotNull
    @Column(name = "password")
    public String password;

    @NotNull
    @Column(name = "status")
    public UserStatus status;
}
