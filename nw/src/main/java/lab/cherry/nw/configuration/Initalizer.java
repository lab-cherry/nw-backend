package lab.cherry.nw.configuration;


import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.OrgRepository;
import lab.cherry.nw.repository.RoleRepository;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.util.TsidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class Initalizer implements ApplicationRunner {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrgRepository orgRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        System.out.println("[#1] Init Roles");

        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {

            RoleEntity roleEntity = RoleEntity.builder()
                .id(TsidGenerator.next())
                .name("ROLE_ADMIN")
                .build();
            
            roleRepository.save(roleEntity);
        }
        

        if(roleRepository.findByName("ROLE_USER").isEmpty()) {

            RoleEntity roleEntity = RoleEntity.builder()
                .id(TsidGenerator.next())
                .name("ROLE_USER")
                .build();

            roleRepository.save(roleEntity);
        }

        System.out.println("[#2] Init Organization");
        
        if(orgRepository.findByName("DEFAULT").isEmpty()) {

            Instant instant = Instant.now();
            OrgEntity orgEntity = OrgEntity.builder()
                    .id(TsidGenerator.next())
                    .name("DEFAULT")
                    .biznum("123-45-67890")
                    .contact("02-0000-0000")
                    .enabled(true)
                    .created_at(Timestamp.from(instant))
                    .build();

            orgRepository.save(orgEntity);
        }
        
        System.out.println("[#3] Init Users");
        
        if(userRepository.findByUserId("admin").isEmpty()) {

            RoleEntity roleEntity = roleRepository.findByName("ROLE_ADMIN").get();
            
            userRepository.save(UserEntity.builder()
                .id(TsidGenerator.next())
                .userid("admin")
                .username("관리자")
                .password(passwordEncoder.encode("admin"))
                .email("admin@test.com")
                .role(roleEntity)
                .enabled(true)
                .build());
        }
        
        // TODO: 최종 개발 완료 후 삭제 처리 예정
        if(userRepository.findByUserId("cherrylab").isEmpty()) {

            RoleEntity roleEntity = roleRepository.findByName("ROLE_USER").get();
        
            userRepository.save(UserEntity.builder()
                .id(TsidGenerator.next())
                .userid("cherrylab")
                .username("체리랩")
                .password(passwordEncoder.encode("cherrylab"))
                .email("cherrylab@test.com")
                .role(roleEntity)
                .enabled(true)
                .build());
        }
    }
}