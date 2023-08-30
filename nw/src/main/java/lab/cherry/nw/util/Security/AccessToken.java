package lab.cherry.nw.util.Security;

import lab.cherry.nw.model.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {

    private String accessToken;

    //////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Get {

        private String userSeq;
        private String userId;
        private String userName;
        private RoleEntity userRole;
        private String accessToken;
        private String refreshToken;

    }

}