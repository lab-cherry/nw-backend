package lab.cherry.nw.configuration;


import java.time.Instant;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.OrgRepository;
import lab.cherry.nw.repository.RoleRepository;
import lab.cherry.nw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class Initalizer implements ApplicationRunner {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrgRepository orgRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {

            RoleEntity roleEntity = RoleEntity.builder()
                .name("ROLE_ADMIN")
                .build();
            
            roleRepository.save(roleEntity);
        }
        

        if(roleRepository.findByName("ROLE_USER").isEmpty()) {

            RoleEntity roleEntity = RoleEntity.builder()
                .name("ROLE_USER")
                .build();

            roleRepository.save(roleEntity);
        }
        
        if(orgRepository.findByName("DEFAULT").isEmpty()) {

            Instant instant = Instant.now();
            OrgEntity orgEntity = OrgEntity.builder()
                    .name("DEFAULT")
                    .biznum("123-45-67890")
                    .contact("02-0000-0000")
                    .address("파인에비뉴")
                    .enabled(true)
                    .created_at(instant)
                    .build();

            orgRepository.save(orgEntity);
        }
        
        if(userRepository.findByuserid("admin").isEmpty()) {

            RoleEntity roleEntity = roleRepository.findByName("ROLE_ADMIN").get();
            
            userRepository.save(UserEntity.builder()
                .userid("admin")
                .username("관리자")
                .password(passwordEncoder.encode("admin"))
                .email("admin@localhost.com")
				.type("org")
                .role(roleEntity)
                .enabled(true)
                .build());
        }
        
        // TODO: 최종 개발 완료 후 삭제 처리 예정
        if(userRepository.findByuserid("cherrylab").isEmpty()) {

            RoleEntity roleEntity = roleRepository.findByName("ROLE_USER").get();
        
            userRepository.save(UserEntity.builder()
                .userid("cherrylab")
                .username("체리랩")
                .password(passwordEncoder.encode("cherrylab"))
                .email("cherrylab@test.com")
				.type("user")
                .role(roleEntity)
                .enabled(true)
                .build());
        }
    }
}