
package com.aluufc.demoparkingapi.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/*
O filtro vai ter a seguinte função de capturar todas
as requisições que são enviadas para a API e verificar
se essas requisições contém um token.
Se contiver o token, ele vai capturar o token.
Então, deve-se validar o token  fazer a autenticação do
usuário na aplicação a partir das informações
contidas nesse token.
Feito isso, o filtro vai liberar a requisição para ela chegar
até um método de requisição.
Caso esse token seja inválido, então o Spring interrompe a operação,
não permitindo que a requisição
continue e envia como resposta um erro para o cliente.
* */


@Slf4j
public class JwtAuthorizationFilter  extends OncePerRequestFilter {

  @Autowired
  protected JwtUserDetailsService detailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final  String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);
        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)){
            log.info("JWT está nulo, vazio ou não iniciado com 'Bearer '.");
            filterChain.doFilter(request, response);
            return;
        }
        if(!JwtUtils.isTokenValid(token)){
          log.warn("JWT token está inválido.");
          filterChain.doFilter(request, response);
          return;
        }
        String username = JwtUtils.getUsernameFromToken(token);

        toAuthentication(request, username);
        filterChain.doFilter(request, response);

}
    public  void toAuthentication(HttpServletRequest request, String username){
        UserDetails userDetails = detailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        // Passando o objeto de requisição (request) para parte de autenticação do Spring Security
        // e assim o Spring consegue unir as operações
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    }