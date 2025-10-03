package com.webservice.auth_service.services;

import com.webservice.auth_service.entity.Users;
import com.webservice.auth_service.exception.NotFound;
import com.webservice.auth_service.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = usersRepository.findByUsername(username)
      .orElseThrow(() -> new NotFound("User not found."));

    return new User(
      user.getUsername(),
      user.getPassword(),
      user.isEnable(),
      true,
      true,
      true,
      user.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList())
    );
  }
}
