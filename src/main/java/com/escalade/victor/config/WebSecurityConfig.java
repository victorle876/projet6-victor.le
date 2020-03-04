package com.escalade.victor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
//                .antMatchers("/resources/**", "/webjars/**","/assets/**").permitAll()
//                .antMatchers("/edition/**", "/details/**","/save/**","/add/**").permitAll()
                .antMatchers("/editionUser/**", "/detailsUser/**","/saveUser/**","/addUser/**","/listUser/**").permitAll()
                .antMatchers("/site/editionSite/**", "/site/detailsSite/**","/site/saveSite/**","/site/addSite/**").permitAll()
                .antMatchers("/secteur/editionSecteur/**", "/secteur/detailsSecteur/**","/secteur/saveSecteur/**","/secteur/addSecteur/**").permitAll()
                .antMatchers("/commentaire/editionCommentaire/**", "/commentaire/detailsCommentaire/**","/commentaire/saveCommentaire/**","/commentaire/addCommentaire/**").permitAll()
                .antMatchers("/topologie/editionTopologie/**", "/topologie/detailsTopologie/**","/topologie/saveTopologie/**","/topologie/addTopologie/**").permitAll()
                .antMatchers("/reservation/editionReservation/**", "/reservation/detailsReservation/**","/reservation/saveReservation/**","/reservation/addReservation/**").permitAll()
                .antMatchers("/").permitAll()
         //       .antMatchers("/topologie/**","/site/**","/commentaire/**").permitAll()
                .antMatchers("/site/siteHome/**","/topologie/topologieHome/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN") //
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/connect")
                .defaultSuccessUrl("/")
                .failureUrl("/connect?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("my-remember-me-cookie")
                .permitAll()
                .and()
                .rememberMe()
                //.key("my-secure-key")
                .rememberMeCookieName("my-remember-me-cookie")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60)
                .and()
                .exceptionHandling()
        ;
    }

    PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }
}