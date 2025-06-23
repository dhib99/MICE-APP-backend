package jwtSecurity.example.jwtDemo.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired

    private UserDetailsService userDetailsService;

    //Constructor
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    // This method is executed for every request intercepted by the filter.
    //And, it extract the token from the request header and validate the token.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.equals("/api/auth/signup") || path.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        try {
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                Long userId = jwtTokenProvider.getUserId(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                request.setAttribute("userId", userId);
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            request.setAttribute("exception", new Exception("Token expiré"));
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            request.setAttribute("exception", new Exception("Token mal formé"));
        } catch (io.jsonwebtoken.SignatureException e) {
            request.setAttribute("exception", new Exception("Signature JWT invalide"));
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            request.setAttribute("exception", new Exception("Format de token non supporté"));
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", new Exception("Token JWT vide ou invalide"));
        } catch (Exception e) {
            request.setAttribute("exception", new Exception("Erreur d'authentification : " + e.getMessage()));
        }

        filterChain.doFilter(request, response);
    }

    // Extract the token
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }
}
