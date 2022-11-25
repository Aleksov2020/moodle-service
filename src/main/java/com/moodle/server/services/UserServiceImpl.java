package com.moodle.server.services;

import com.moodle.server.models.Group;
import com.moodle.server.models.Role;
import com.moodle.server.models.UserEntity;
import com.moodle.server.repository.RoleRepository;
import com.moodle.server.repository.UserRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${moodle.secretKey}")
    private String secretCode;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserEntity findUserById(Long usrId) {
        return userRepository.findById(usrId).orElse(new UserEntity());
    }

    @Override
    @Transactional
    public void deleteUsersByGroupId(Long groupId) {
        userRepository.deleteUserEntitiesByGroup_GroupId(groupId);
    }

    @Override
    public boolean registerNewUser(String firstName, String lastName, String middleName, Group group, String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setEnabled(true);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setGroup(group);
        Role roles = roleRepository.findByName("USER");
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode((password)));
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return true;
    }

    @Override
    public List<UserEntity> generateUsers(Integer count, Group group) {

        List<UserEntity> userEntityList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            UserEntity user = new UserEntity();

            user.setEnabled(true);
            Role roles = roleRepository.findByName("ROLE_USER");
            //TODO check unique
            user.setUsername(
                    "user" + String.valueOf(1000 + (int)(Math.random() * 10000))
            );
            String password = generateRandomSpecialCharacters(8);
            user.setPassword(passwordEncoder.encode((password)));
            user.setDecodePassword(password);
            user.setPasswordChanged(false);
            user.setRoles(Collections.singletonList(roles));
            user.setGroup(group);

            userEntityList.add(userRepository.save(user));
        }
        return userEntityList;
    }

    @Override
    public List<UserEntity> findByGroupId(Long groupId) {
        return userRepository.findUserEntitiesByGroup_GroupId(groupId);
    }

    @Override
    public UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity updateUser(UserEntity user, String password, String firstName, String lastName, String middleName, String email) {
        user.setPasswordChanged(true);
        user.setPassword(passwordEncoder.encode((password)));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        return new User(userEntity.getUsername(), userEntity.getPassword(), mapRolesToAuthorities(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
                .build();
        return pwdGenerator.generate(length);
    }
}
