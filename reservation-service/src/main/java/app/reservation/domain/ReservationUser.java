package app.reservation.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author steve
 */
@Table(name = "reservation_users")
public class ReservationUser {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Column(name = "reservation_id")
    public String reservationId;

    @NotNull
    @Column(name = "user_id")
    public Long userId;

    @NotNull
    @NotBlank
    @Column(name = "user_name")
    public String user_name;

    @NotNull
    @NotBlank
    @Column(name = "user_email")
    public String userEmail;
}
