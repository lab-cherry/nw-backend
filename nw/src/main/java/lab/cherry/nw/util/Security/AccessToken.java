package lab.cherry.nw.util.Security;

import java.util.Map;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private String userRole;
        private Map<String, String> info;
        private String accessToken;

    }

}