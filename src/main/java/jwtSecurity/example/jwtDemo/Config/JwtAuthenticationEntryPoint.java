package jwtSecurity.example.jwtDemo.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        int status;

        if (exception != null) {
            message = exception.getMessage();

            if (message.contains("Token expiré")) {
                status = HttpServletResponse.SC_UNAUTHORIZED; // 401
            } else if (message.contains("Token mal formé") || message.contains("non supporté")) {
                status = HttpServletResponse.SC_BAD_REQUEST; // 400
            } else if (message.contains("Accès refusé")) {
                status = HttpServletResponse.SC_FORBIDDEN; // 403
            } else {
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500
                message = "Erreur interne du serveur.";
            }

        } else {
            // Cas standard : non authentifié
            status = HttpServletResponse.SC_UNAUTHORIZED;
            message = "Vous devez être authentifié pour accéder à cette ressource.";
        }

        response.setStatus(status);

        // Construire la réponse JSON
        String json = String.format(
                "{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                status,
                getReasonPhrase(status),
                message
        );

        response.getWriter().write(json);
    }

    private String getReasonPhrase(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 500 -> "Internal Server Error";
            default -> "Error";
        };
    }
}
