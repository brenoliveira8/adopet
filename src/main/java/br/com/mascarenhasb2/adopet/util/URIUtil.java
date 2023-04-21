package br.com.mascarenhasb2.adopet.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class URIUtil {
    public static URI createCreatedEntityURI(String endpoint, Long id, UriComponentsBuilder uriBuilder){
        return uriBuilder.path(endpoint + "/{id}").buildAndExpand(id).toUri();
    }
}
