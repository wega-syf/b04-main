package com.burat.simpel.security;

import com.burat.simpel.model.AccountModel;
import com.burat.simpel.repository.AdminDb;
import com.burat.simpel.repository.AssessorDb;
import com.burat.simpel.repository.ExecutiveDb;
import com.burat.simpel.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminDb adminDb;

    @Autowired
    private ExecutiveDb executiveDb;

    @Autowired
    private AssessorDb assessorDb;

    @Autowired
    private UserDb userDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountModel akun = adminDb.findByUsername(username);
        if (akun == null) {
            akun = executiveDb.findByUsername(username);
            if (akun == null) {
                akun = assessorDb.findByUsername(username);
                if (akun == null) {
                    akun = userDb.findByUsername(username);
                }
            }
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(akun.getRole()));
        return new User(akun.getUsername(), akun.getPassword(), grantedAuthorities);
    }
}
