package br.com.mascarenhasb2.adopet.infra.security;

import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var jwtToken = retrieveToken(request);
        if (jwtToken != null){
            var subject = tokenService.getSubject(jwtToken);
            var user = userRepository.findByEmail(subject);

            var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String retrieveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ","");
        }
        return null;
    }
}
