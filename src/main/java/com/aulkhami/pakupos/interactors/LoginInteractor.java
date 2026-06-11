/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aulkhami.pakupos.interactors;

import com.aulkhami.pakupos.models.LoginModel;
import com.aulkhami.pakupos.services.UserService;

/**
 *
 * @author Rakha
 */
public class LoginInteractor extends FormInteractor {

    UserService service = new UserService();

    public LoginInteractor(LoginModel model) {
        super(model);
    }

    @Override
    public void submitForm() {
        LoginModel model = (LoginModel) getModel();
        service.loginUser(model.getEmail(), model.getPassword());
        model.successProperty().set(true);
    }
}
