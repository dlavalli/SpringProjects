package guru.springframework.api.domain;

import lombok.*;

import java.io.Serializable;
import java.util.List;

// Use in conjonction with:  https://apifaketory.docs.apiary.io/?#reference/users/users-collection/list-of-users?console=1
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserData implements Serializable {
    private List<User> data;
    private static final long serialVersionUID = -1410405583359046774L;
}
