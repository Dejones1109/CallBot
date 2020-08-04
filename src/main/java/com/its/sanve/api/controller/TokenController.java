package com.its.sanve.api.controller;


import com.its.sanve.api.communication.dto.Request;
import com.its.sanve.api.dto.GwResponseDto;
import com.its.sanve.api.exceptions.AuthenException;
import com.its.sanve.api.exceptions.ProcessErrorException;
import com.its.sanve.api.utils.Client;
import com.its.sanve.api.utils.ClientManager;
import com.its.sanve.api.utils.GwJwtTokenFactory;
import com.its.sanve.api.utils.MessageUtils;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import static netscape.security.Privilege.FORBIDDEN;

@RestController
@Logger
@RequestMapping(value = "auth/")
public class TokenController {
    private static final int PARAM_MISSING = 1;
    private static final int AUTHEN_FAIL = 2;
    private static final int FORBIDDEN = 3;

    @Autowired
    MessageUtils messageUtils;
    @Autowired
    ClientManager manager;
    @Autowired
    private GwJwtTokenFactory jwtTokenFactory;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GwResponseDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GwResponseDto.class),
            @ApiResponse(code = 500, message = "Failure", response = GwResponseDto.class)

    })
    @PostMapping("login")
    public ResponseEntity<GwResponseDto> login(@RequestBody Request request) throws
            Exception {
        if (request == null) {
            throw new ProcessErrorException(HttpStatus.BAD_REQUEST, PARAM_MISSING, "Null body not allowed");
        }
        if (isEmpty(request.getApiKey()) || isEmpty(request.getSecretKey())) {
            throw new AuthenException(PARAM_MISSING, messageUtils.getMessage(MessageUtils.LOGIN_FAIL));
        }
        String clientID = request.getApiKey();
        if (clientID == null || clientID.isEmpty()) {
            throw new AuthenException(AUTHEN_FAIL, messageUtils.getMessage(
                    MessageUtils.LOGIN_FAIL));
        }
        Client client = manager.getClient(clientID.toLowerCase());
        if (client == null) {

           throw new  AuthenException(FORBIDDEN, messageUtils.getMessage(
                   MessageUtils.ERR_HEADER_003));
        }
        String secretKey = request.getSecretKey();

        if (secretKey == null || secretKey.isEmpty()) {
            throw new AuthenException(AUTHEN_FAIL, messageUtils.getMessage(
                    MessageUtils.LOGIN_FAIL));
        }

        if (client.getSecretKey().equalsIgnoreCase(secretKey)) {
            String token = jwtTokenFactory.geterateToken(client);


            return GwResponseDto.build().withHttpStatus(HttpStatus.OK)
                    .withCode(HttpStatus.OK)
                    .withData(token)
                    .withMessage("Success")
                    .toResponseEntity();
        }
        throw new AuthenException(AUTHEN_FAIL, messageUtils.getMessage(
                MessageUtils.LOGIN_FAIL));

    }

    private boolean isEmpty(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        return false;
    }
}
