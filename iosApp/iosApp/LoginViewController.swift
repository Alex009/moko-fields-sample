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
        
        loginField.bindTextTwoWay(liveData: viewModel.login.data)
        loginErrorLabel.bindText(liveData: viewModel.login.error)
        loginErrorLabel.bindHidden(liveData: viewModel.login.isValid)
        
        passwordField.bindTextTwoWay(liveData: viewModel.password.data)
        passwordErrorLabel.bindText(liveData: viewModel.password.error)
        passwordErrorLabel.bindHidden(liveData: viewModel.password.isValid)
    }
    
    @IBAction private func onLoginPressed() {
        viewModel.onLoginPressed()
    }
}

extension LoginViewController: UITextFieldDelegate {
    func textFieldDidEndEditing(_ textField: UITextField, reason: UITextField.DidEndEditingReason) {
        if textField == loginField {
            viewModel.login.validate()
        }
        if textField == passwordField {
            viewModel.password.validate()
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
