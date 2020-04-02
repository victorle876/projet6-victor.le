package com.escalade.victor.service;

import com.escalade.victor.model.*;
import com.escalade.victor.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository UtilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = UtilisateurRepository.findByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " not found"));
        System.out.println(utilisateur.getId());
        return new org.springframework.security.core.userdetails.User(utilisateur.getMail(), utilisateur.getPassword(),
                getAuthorities(utilisateur));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Utilisateur utilisateur) {
        String[] userRoles = utilisateur.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}

