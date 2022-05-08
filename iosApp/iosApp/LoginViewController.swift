//
//  LoginViewController.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 08.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import shared

class LoginViewController: UIViewController {
    @IBOutlet private var loginField: UITextField!
    @IBOutlet private var loginErrorLabel: UILabel!
    @IBOutlet private var passwordField: UITextField!
    @IBOutlet private var passwordErrorLabel: UILabel!
    
    private var viewModel: LoginViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        loginField.delegate = self
        passwordField.delegate = self
        
        viewModel = LoginViewModel()
        
        loginField.bindTextTwoWay(liveData: viewModel.login)
        loginErrorLabel.bindText(liveData: viewModel.loginError)
        loginErrorLabel.bindHidden(liveData: viewModel.isLoginErrorShowed.revert())
        
        passwordField.bindTextTwoWay(liveData: viewModel.password)
        passwordErrorLabel.bindText(liveData: viewModel.passwordError)
        passwordErrorLabel.bindHidden(liveData: viewModel.isPasswordErrorShowed.revert())
    }
    
    @IBAction private func onLoginPressed() {
        viewModel.onLoginPressed()
    }
}

extension LoginViewController: UITextFieldDelegate {
    func textFieldDidEndEditing(_ textField: UITextField, reason: UITextField.DidEndEditingReason) {
        if textField == loginField {
            viewModel.isLoginInputComplete.value = true
        }
        if textField == passwordField {
            viewModel.isPasswordInputComplete.value = true
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == loginField {
            passwordField.becomeFirstResponder()
        }
        if textField == passwordField {
            passwordField.resignFirstResponder()
        }
        return true
    }
}
