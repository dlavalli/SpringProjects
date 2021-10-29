package guru.springframework.api.domain;

import lombok.*;

import java.util.List;

// Use in conjonction with:  https://apifaketory.docs.apiary.io/?#reference/users/users-collection/list-of-users?console=1
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserData {
    private List<User> data;
}
