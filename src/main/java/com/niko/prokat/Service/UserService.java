package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.TulipDto;
import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Model.entity.User;
import com.niko.prokat.Model.enums.UserRole;
import com.niko.prokat.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Log4j2
@SuppressWarnings("deprecation")
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final DtoMapper mapper;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null)
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return Collections.singletonList(
                            new SimpleGrantedAuthority(user.getRole().toString()));
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getUsername();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return user.getToken()==null;
                }
            };

        throw new UsernameNotFoundException("User " + username + " not found in DB");
    }

    public UserDto getUserByEmail(String email) {
        return mapper.toUserDto(userRepo.findByEmail(email));
    }

    public TulipDto<Long,String> saveUser(UserDto userDto) {
        if (userRepo.findByUsername(userDto.getUsername()) == null &&
                userRepo.findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(encoder().encode(userDto.getPassword()));
            User user = mapper.toUser(userDto);
            String token = encoder().encode(String.valueOf(new Date().getTime()))
                    .replaceAll("/","");
            user.setToken(token);
            user.setRole(UserRole.USER);
            userRepo.save(user);
            return new TulipDto<>(user.getId(),token);
        } else
            log.error("User " + userDto.getUsername() + " already exists!");
        return null;
    }

    public UserDto getUser(String username) {
        return mapper.toUserDto(userRepo.findByUsername(username));
    }

    public void saveAdmin(UserDto userDto) {
        if (userRepo.findByUsername(userDto.getUsername()) == null &&
                userRepo.findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(encoder().encode(userDto.getPassword()));
            User user = mapper.toUser(userDto);
            user.setRole(UserRole.ADMIN);
            userRepo.save(user);
        } else
            log.error("User " + userDto.getUsername() + " already exists!");
    }

    public void updateUser(UserDto userDto, String username) {
        userDto.setPassword(encoder().encode(userDto.getPassword()));
        User oldUser = userRepo.findByUsername(username);
        User newUser = mapper.toUser(userDto);
        if (oldUser.getRole() == UserRole.USER){
            newUser.setRole(UserRole.USER);
        } else {
            newUser.setRole(UserRole.ADMIN);
        }
        newUser.setId(oldUser.getId());
        newUser.setVersion(oldUser.getVersion());
        userRepo.save(newUser);
    }

    public Boolean activateUser(Long id, String token){
        User user = userRepo.findById(id).get();
        if (user.getToken().equals(token)){
            user.setToken(null);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }
}
