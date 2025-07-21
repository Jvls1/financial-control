package com.jvls.financialcontrol.utils;

import java.net.URI;
import java.util.UUID;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URIUtil {

    public static URI getUri(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .pathSegment(id.toString())
                .build()
                .toUri();
    }

    public static URI getUri(UUID id, String... pathSegments) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .pathSegment(pathSegments)
                .pathSegment(id.toString())
                .build()
                .toUri();
    }
}
