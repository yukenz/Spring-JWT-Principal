package com.awan.securityjwt.service.interfaces;

import com.awan.securityjwt.model.request.AuthRequest;

public interface AuthService {

    String auth(AuthRequest request);

}
